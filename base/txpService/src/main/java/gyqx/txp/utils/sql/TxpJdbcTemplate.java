package gyqx.txp.utils.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class TxpJdbcTemplate extends NamedParameterJdbcTemplate
{
	boolean omitLackField ;

	public TxpJdbcTemplate(DataSource dataSource,boolean omitLackField) throws SQLException {
		super(dataSource);
		this.dataSource = dataSource;
		this.omitLackField = omitLackField;
		try(Connection cnn = dataSource.getConnection()){
			if(cnn.getMetaData().getDatabaseProductName().toLowerCase().indexOf("mysql")>=0)
				dbType = "mysql";
		}
	}
	private DataSource dataSource;
	private String dbType = "";
	
	public  ResultSetIterator<Map<String, Object>> queryForStreamList(String sql) throws SQLException
	{
		Connection con = DataSourceUtils.getConnection(dataSource);
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setFetchSize(Integer.MIN_VALUE);
		
		ResultSet rs = pstmt.executeQuery();
		
		RowMapper<Map<String, Object>> rowMapper = new ColumnMapRowMapper();
        return new ResultSetIterator<>(dataSource,con, pstmt, rs, rowMapper);
	}
	
	public  ResultSetIterator<Map<String, Object>> queryForStreamList(String sql,Map<String,?> paramMap) throws SQLException
	{
		Connection con = DataSourceUtils.getConnection(dataSource);
		
		PreparedStatementCreator psc =  this.getPreparedStatementCreator(sql, new TxpMapSqlParameterSource(paramMap,omitLackField));
		
		PreparedStatement pstmt = psc.createPreparedStatement(con);
		if(dbType.equals("mysql"))
			pstmt.setFetchSize(Integer.MIN_VALUE);
		
		ResultSet rs = pstmt.executeQuery();
		
		RowMapper<Map<String, Object>> rowMapper = new ColumnMapRowMapper();
        return new ResultSetIterator<>(dataSource,con, pstmt, rs, rowMapper);		
		
	}
	public  <T> ResultSetIterator<T> queryForStreamList(String sql,Map<String,?> paramMap,Class<T> outputClzz) throws SQLException
	{
		Connection con = DataSourceUtils.getConnection(dataSource);
		
		PreparedStatementCreator psc =  this.getPreparedStatementCreator(sql, new TxpMapSqlParameterSource(paramMap,omitLackField));
		
		PreparedStatement pstmt = psc.createPreparedStatement(con);
		if(dbType.equals("mysql"))
			pstmt.setFetchSize(Integer.MIN_VALUE);
		
		ResultSet rs = pstmt.executeQuery();
		
		RowMapper<T> rowMapper = new BeanPropertyRowMapper(outputClzz);
        return new ResultSetIterator<T>(dataSource,con, pstmt, rs, rowMapper);	
		
	}

}
