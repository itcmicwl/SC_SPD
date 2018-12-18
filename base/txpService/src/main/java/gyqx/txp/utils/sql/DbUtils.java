package gyqx.txp.utils.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gyqx.txp.configuration.DbSources;
import gyqx.txp.configuration.TableConfig;
import gyqx.txp.data.IBean;
import gyqx.txp.db.meta.DbMetaInfo;
import gyqx.txp.db.meta.TableMeta;


public class DbUtils
{	
	private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);
	TxpJdbcTemplate njt;
	JdbcTemplate jt;
	DataSource ds;
	DataSourceTransactionManager txManager = null;
	TransactionStatus status;
	boolean omitLackField ;
	public DbUtils(DataSource ds) throws SQLException
	{
		this(ds,false);
	}
	public DbUtils(DataSource ds,boolean omitLackField) throws SQLException
	{
		this.ds = ds;
		this.omitLackField = omitLackField;
		njt = new TxpJdbcTemplate(ds,omitLackField);
		jt = new JdbcTemplate(ds);
		
	}
	public void beginTransaction()
	{
		txManager = DbSources.getCurrent().getTxManager(ds);
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
	    def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);  
	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);  
	    status = txManager.getTransaction(def); 
	}
	public void commit()
	{
		txManager.commit(status);
		status = null;
		txManager = null;
	}
	public void rollback()
	{
		if(status == null)
			return;
		status.setRollbackOnly(); 
		txManager.rollback(status);
		status = null;
		txManager = null;
	}	
	
	public List<Map<String, Object>> query(String sql)
	{
		List<Map<String, Object>> dt= jt.queryForList(sql);
		return dt;
	}
	
	public <T> List<T> query(String sql,Class<T> clz)
	{
		List<T> list = jt.query (sql,new BeanPropertyRowMapper(clz));	

		return list;
	}
	
	
	public <T> List<T> query(String sql,Class<T> clz,Object ...params)
	{
		List<T> list = jt.query (sql,params,new BeanPropertyRowMapper(clz));
		return list;
	}
	
	public List<Map<String, Object>> query(String sql,Map<String,?> paramMap)
	{
		List<Map<String, Object>> list =  njt.queryForList(sql, new TxpMapSqlParameterSource(paramMap,omitLackField));
		return list;
	}
	
	public <T> List<T> query(String sql,Object params,Class<T> cls) throws Exception
	{
		List<T> to = null;
		Map<String,Object> paramMap = null;
		if(params instanceof Map<?,?> ){
			paramMap = (Map<String,Object>)params;
			to = njt.queryForList(sql,new TxpMapSqlParameterSource( paramMap,omitLackField),cls);
		}
		else
		{
			BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(params);
			to = njt.queryForList(sql, paramSource,cls);
		}
		return to;
	}
	public <T> T queryValue(String sql,Map<String,?> paramMap,Class<T> requiredType)
	{
		return njt.queryForObject(sql, new TxpMapSqlParameterSource( paramMap,omitLackField), requiredType);
	}
	public <T> List<T> queryValues(String sql,Class<T> cls)
	{
		return jt.queryForList(sql, cls);
	}
	public <T> List<T> queryValues(String sql ,Map<String,?> paramMap,Class<T> cls) throws InstantiationException, IllegalAccessException, InvocationTargetException
	{
		List<Map<String, Object>> rows = njt.queryForList(sql,new TxpMapSqlParameterSource( paramMap,omitLackField));
		List<T> vals = new ArrayList<>();
		for(int i = 0; i< rows.size();i++)
		{
			Map<String, Object> row = rows.get(i);
			if(row.size() ==1)
			{
				Object o = row.values().toArray()[0];
				if(o.getClass().equals(cls))
					vals.add((T)o);
				else
					vals.add((T)ConvertUtils.convert(o, cls));	
			}else{
				T target = cls.newInstance();
				
				BeanUtils.copyProperties(target, row);
				vals.add(target);
			}
		}
		return vals;
	}
	public  ResultSetIterator<Map<String, Object>> queryForStreamList(String sql) throws SQLException
	{
		return njt.queryForStreamList(sql);
	}
	public  ResultSetIterator<Map<String, Object>> queryForStreamList(String sql,Map<String,?> paramMap) throws SQLException
	{
		
		return njt.queryForStreamList(sql, paramMap);
	}
	
	public <T> ResultSetIterator<T> queryForStreamList(String sql,Map<String,?> paramMap,Class<T> outputClzz ) throws SQLException
	{
		return njt.queryForStreamList(sql, paramMap,outputClzz);
	}
	public int update(String sql)
	{
		return jt.update(sql);
	}
	public int update(String sql,Object obj) 
	{
		if(obj instanceof Map<?,?>)
			return njt.update(sql, new TxpMapSqlParameterSource((Map<String,Object>)obj,omitLackField));
		
		BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(obj);
		return njt.update(sql, paramSource);
		
	}
	public int updateWithParams(String sql,Object...args)
	{
		return jt.update(sql, args);
	}
	
	public void updateList(String sql,List<Map<String,Object>> rows) 
	{
		for(Map<String,Object> row:rows)
		{
			update(sql,row);
		}
	}
	public void updateOrInsertTable(String tableName,List<Map<String,Object>> rows,String defaultKeys) throws Exception
	{
		DbMetaInfo metaInfo = DbSources.getCurrent().getDbMetaInfo(ds);
		TableMeta tm = metaInfo.get(tableName);
		String sqlU = TaskSqlBuilder.buildDefaultUpdateSql(tm, defaultKeys, true);
		String sqlI = TaskSqlBuilder.buildDefaultInsertSql(tm) ;
		for(Map<String,Object> row: rows)
		{
			int ia = 0;			
			ia = update(sqlU,row);
			if(ia == 0)
				update(sqlI,row);
		}
	}
	public void insertOrUpdateToTable(TableConfig tc,List<Map<String,Object>> rows) throws Exception
	{
		String tableName = tc.getTableName();
		String compareFieldNames = tc.getCompareFieldNames();
		String versionFieldName = tc.getVersionFieldName();
		String pkFieldName = tc.getPkNames();
		DbMetaInfo metaInfo = DbSources.getCurrent().getDbMetaInfo(ds);
		TableMeta tm = metaInfo.get(tableName);
		String sqlRow = TaskSqlBuilder.buildVersionCompareRowSql(tm, compareFieldNames,pkFieldName);
		String sqlInsert = TaskSqlBuilder.buildDefaultInsertSql(tm) ;
		String sqlUpdate = TaskSqlBuilder.buildDefaultUpdateSql(tm,pkFieldName,false) ;
		logger.info("versionCompareRowSql:{}",sqlRow);
		logger.info("defaultInsertSql:{}",sqlInsert);
		logger.info("defaultUpdateSql:{}",sqlUpdate);
		for(Map<String,Object> row: rows)
		{
			List<Map<String,Object>> compareRows = this.query(sqlRow, row);// queryValue(sqlCount,row,Integer.class);
			if(compareRows.size() == 0)
				update(sqlInsert,row);
			else{
				Map<String,Object> cfrow = compareRows.get(0);
				int ov = (int) Integer.parseInt(cfrow.get(versionFieldName).toString());
				int nv = (int) Integer.parseInt(row.get(versionFieldName).toString());
				if(ov < nv)
				{
					if(row.containsKey(IBean.default_version_name))
						row.put(IBean.old_version_field_name, ov);
					update(sqlUpdate,row);
				}
			}
			
		}		
	}	
	
	public void insertToTable(String tableName,List<Map<String,Object>> rows) throws SQLException
	{
		DbMetaInfo metaInfo = DbSources.getCurrent().getDbMetaInfo(ds);
		TableMeta tm = metaInfo.get(tableName);
		String sql = TaskSqlBuilder.buildDefaultInsertSql(tm) ;
		
		updateList(sql,rows);
		
	}

}
