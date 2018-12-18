package gyqx.txp.configuration;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import gyqx.txp.db.meta.DbMetaInfo;
import gyqx.txp.db.meta.mysql.MysqlDbMetaInfo;
import gyqx.txp.db.meta.oracle.OracleDbMetaInfo;

public class DbSources 
{
	private static DbSources current = null;
	public static DbSources getCurrent()
	{
		return current;
	}
	public DbSources()
	{
		current = this;
	}
	
	private Map<String,String> dbTypeMap;
	
	private Map<String,DataSourceTransactionManager> transacationManagerMap;
	
	private Map<String,DataSource> dbSourceMap;
	
	public Map<String, DataSource> getDbSourceMap() {
		return dbSourceMap;
	}
	public void setDbSourceMap(Map<String, DataSource> dbSourceMap) {
		this.dbSourceMap = dbSourceMap;
	}
	private Map<String ,DbMetaInfo> dbMetaInfoMap;

	public String getDbType(String name)
	{
		return dbTypeMap.get(name);
	}
	public DataSource getDbSource(String name)
	{
		return dbSourceMap.get(name);
	}
	public DbMetaInfo getDbMetaInfo(String name) throws SQLException
	{
		DataSource ds = getDbSource(name);
		
		if(ds == null)
			return null;
		if(dbMetaInfoMap == null)
			dbMetaInfoMap = new HashMap<>();
		
		DbMetaInfo dmi = dbMetaInfoMap.get(name);
		
		if(dmi != null)
			return dmi;
		
		if(ds instanceof BasicDataSource){
			BasicDataSource bds = (BasicDataSource) ds; 
	
			if(bds.getDriverClassName().indexOf("mysql") >=0)
			{
				MysqlDbMetaInfo mi = new MysqlDbMetaInfo(ds);
				dbMetaInfoMap.put(name, mi);
				return mi;
			}else{
				
			}
		}else{
			OracleDbMetaInfo mi = new OracleDbMetaInfo(ds);
			dbMetaInfoMap.put(name, mi);
			return mi;
		}
		return null;
	}
	public DataSourceTransactionManager getTxManager(String name)
	{
		return transacationManagerMap.get(name);
	}
	public DataSourceTransactionManager getTxManager(DataSource ds)
	{
		String selName=getDataSourceName(ds);
		return getTxManager(selName);
	}
	public String getDataSourceName(DataSource ds)
	{
		String selName=null;
		
		Iterator<Entry<String,DataSource>> iter = dbSourceMap.entrySet().iterator();
		while(iter.hasNext())
		{
			Entry<String,DataSource> entry = iter.next();
			
			if(entry.getValue().equals(ds))
				selName = entry.getKey();
		}
		return selName;	
	}
	
	public DbMetaInfo getDbMetaInfo(DataSource ds) throws SQLException
	{
		String selName=getDataSourceName(ds);
		
		return getDbMetaInfo(selName);	
	}
	
	public Map<String ,DbMetaInfo> getDbMetaInfoMap() {
		return dbMetaInfoMap;
	}
	public void setDbMetaInfoMap(Map<String ,DbMetaInfo> dbMetaInfoMap) {
		this.dbMetaInfoMap = dbMetaInfoMap;
	}
	public Map<String,DataSourceTransactionManager> getTransacationManagerMap() {
		return transacationManagerMap;
	}
	public void setTransacationManagerMap(Map<String,DataSourceTransactionManager> transacationManagerMap) {
		this.transacationManagerMap = transacationManagerMap;
	}
	public Map<String,String> getDbTypeMap() {
		return dbTypeMap;
	}
	public void setDbTypeMap(Map<String,String> dbTypeMap) {
		this.dbTypeMap = dbTypeMap;
	}

}
