package gyqx.txp.db.meta.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import gyqx.txp.db.meta.DbMetaInfo;
import gyqx.txp.db.meta.FieldMeta;
import gyqx.txp.db.meta.TableMeta;



public class MysqlDbMetaInfo extends DbMetaInfo
{
	public MysqlDbMetaInfo()
	{
		
	}
	public MysqlDbMetaInfo(DataSource dst) throws SQLException
	{
		String dbName = "";
		BasicDataSource ds0 = (BasicDataSource) dst;
		
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(ds0.getDriverClassName());
		String url = ds0.getUrl(); //String.format("jdbc:mysql://10.6.189.20:3306/zh?useUnicode=true&characterEncoding=UTF-8", );
		Pattern pat = Pattern.compile("(^jdbc\\:mysql\\://(.)+\\:(\\d)+/)((\\w)+)(\\?(.+))$");
		Matcher mat = pat.matcher(url);
		if(mat.find()){
			
			url = mat.group(1) + "information_schema?useUnicode=true&characterEncoding=UTF-8";
			dbName=mat.group(4);
		}
		ds.setUrl(url);
		ds.setPassword(ds0.getPassword());
		ds.setUsername(ds0.getUsername());
		
		JdbcTemplate jt = new JdbcTemplate(ds);
		
		List<Map<String,Object>> dtTableSchema = jt.queryForList("select TABLE_NAME,TABLE_COMMENT from TABLES where TABLE_TYPE='BASE TABLE' and TABLE_SCHEMA='" + dbName + "' order by TABLE_NAME");

		for(Map<String,Object> row:dtTableSchema)
		{
			TableMeta tm = new TableMeta();
			tm.setName(row.get("TABLE_NAME").toString());
			tm.setRemark(row.get("TABLE_COMMENT").toString());
			tm.setCamelName(toCamelName(tm.getName(),true));
			tables.add(tm);
			map.put(tm.getName().toLowerCase().replace("_", ""), tm);
		}
		Pattern pat1 = Pattern.compile("((.)+)(\\s+(.+))?");
		
		
		List<Map<String,Object>> dtTableFieldSchema = jt.queryForList("select TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION as pos,COLUMN_KEY as keyflag,DATA_TYPE as dtype,IS_NULLABLE as nullflag,CHARACTER_MAXIMUM_LENGTH as len,NUMERIC_PRECISION as prec,NUMERIC_SCALE as scale,COLUMN_COMMENT as remark from COLUMNS where TABLE_SCHEMA = '"+ dbName + "'  ORDER BY TABLE_NAME , ORDINAL_POSITION");
		for(Map<String,Object> row:dtTableFieldSchema)
		{
			TableMeta tm = this.get(row.get("TABLE_NAME").toString());
			if(tm == null)
				continue;
			FieldMeta fm = new FieldMeta();
			fm.setName(row.get("COLUMN_NAME").toString());
			fm.setSafeDbFieldName("`" + fm.getName() + "`");
			fm.setTable(tm);
			fm.setRemark(row.get("remark").toString());
			Object v = row.get("pos");
			fm.setOrdinal(Integer.parseInt(v.toString()));
			
			fm.setType(row.get("dtype").toString());			
			

			if(row.get("len")==null || Integer.parseInt(row.get("len").toString()) ==0)
			{
				fm.setLength(row.get("prec") ==null?0: Integer.parseInt(row.get("prec").toString()));
			}else{
				fm.setLength(Integer.parseInt(row.get("len").toString()));
			}
			fm.setPk("PRI".equals(row.get("keyflag")));
			
			fm.setEnableNull("YES".equals(row.get("nullflag")));
			
			if(row.get("scale")!= null &&  Integer.parseInt(row.get("scale").toString()) != 0)
			{
				fm.setScale(Integer.parseInt(row.get("scale").toString()));
			}
			
			fm.setCamelName(toCamelName(fm.getName(),false));
			fm.setJavaType(queryBeanType(fm.getType(),fm.isEnableNull()));

			Matcher mat1 = pat1.matcher(fm.getRemark());
			if(mat1.find())
			{
				fm.setCname(mat1.group(1));
			}else{
				fm.setCname(fm.getName());
			}
			tm.append(fm);
		}
	}
	
	public String queryBeanType(String dbType,boolean enableNull)
	{
		return dbTypeToJavaType(dbType,enableNull);
	}
	
	public static String dbTypeToJavaType(String dbType,boolean enableNull)
	{
		String result = "";
		if(dbType.equalsIgnoreCase("char") || dbType.equalsIgnoreCase("varchar") || dbType.equalsIgnoreCase("text") || dbType.equalsIgnoreCase("clob"))
			result = "String";
		else if(dbType.equalsIgnoreCase("datetime") || dbType.equalsIgnoreCase("timestamp"))
			result = "java.util.Date";
		else if(dbType.equalsIgnoreCase("decimal"))
			result = "java.math.BigDecimal";
		else if(dbType.equalsIgnoreCase("int") )
			result = enableNull?"Integer":"int";
		else if(dbType.equalsIgnoreCase("bigint"))
			result = enableNull?"Long":"long";
		else if(dbType.equalsIgnoreCase("double"))
			result = enableNull?"Double":"double";
		else if(dbType.equalsIgnoreCase("date"))
			result = "Date";
		else if(dbType.equalsIgnoreCase("blob") || dbType.equalsIgnoreCase("longblob"))
			result = "byte[]";
		else		
		throw new RuntimeException("unknown type!");
		return result;
	}
	
}

