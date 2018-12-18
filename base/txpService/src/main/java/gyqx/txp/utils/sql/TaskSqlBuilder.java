package gyqx.txp.utils.sql;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import gyqx.txp.configuration.SyncTaskConfig;
import gyqx.txp.configuration.TableConfig;
import gyqx.txp.configuration.TaskConfig;
import gyqx.txp.data.IBean;
import gyqx.txp.data.SyncDataSet;
import gyqx.txp.data.SyncSqlSet;
import gyqx.txp.db.meta.FieldMeta;
import gyqx.txp.db.meta.TableMeta;
import gyqx.txp.utils.groovy.GroovyScriptHelper;

public class TaskSqlBuilder 
{	
	/**
	 * 根据同步分析结果，生成需同步的数据的sql语句组  （insert ,update ,delete）
	 * @param config 表的配置
	 * @param mapSync 同步分析结果
	 * @return
	 */
	public static SyncSqlSet buildProductSql(TableConfig config,SyncDataSet syncData)
	{
		StringBuilder sb = new StringBuilder();
		SyncSqlSet sss = new SyncSqlSet();
		String[] pkNames = config.getPkNames().split(",");
		if(pkNames.length == 1)
		{
			String pkName = pkNames[0];
			
			List<Map<String,Object>> insertList =syncData.getInsertList();

			if(insertList != null && insertList.size() >0)
			{
				sss.setInsertDataLoadSqls( buildInIdsSqls(config.getTableName(),pkName,insertList));//"select * from " + config.getTableName() + " where " + pkName + " in (" + buildIds(insertList,pkName ) + ")" );
			}			

			
			List<Map<String,Object>> updateList =  syncData.getUpdateList(); 
			if(updateList != null && updateList.size() >0)
			{
				sss.setUpdateDataLoadSqls(buildInIdsSqls(config.getTableName(),pkName,updateList));//"select * from " + config.getTableName() + " where " + pkName + " in (" +  buildIds(updateList,pkName ) + ")" );
			}			
			
			
			List<Map<String,Object>> deleteList = syncData.getDeleteList();
			if(deleteList != null && deleteList.size() >0)
			{
				sss.setDeleteDataLoadSqls(buildInIdsSqls(config.getTableName(),pkName,deleteList)); // "select * from " + config.getTableName() + " where " + pkName + " in (" + buildIds(deleteList,pkName ) + ")" );
			}			
			
		}
		return sss;
	}
	/**
	 * 生成分段的in 语句
	 * @param table
	 * @param pkName
	 * @param rows
	 * @return
	 */
	public static List<String> buildInIdsSqls(String table,String pkName,List<Map<String,Object>> rows)
	{
		ArrayList<String> sqls = new ArrayList<>();
		int step =500,cnt = 0;
		
		if(rows.size() < step){
			sqls.add( "select * from " + table + " where " + pkName + " in (" + buildIds(rows,pkName,-1,-1 ) + ")");
		}else{
			StringBuilder sbIds = new StringBuilder();
			
			for(int i = 0; i< rows.size();i+= step,cnt++)
			{				
				if(sbIds.length() >0)
					sbIds.append(" or ");
				String ids = buildIds(rows,pkName,i,i+step);
				
				sbIds.append(pkName).append(" in (").append(ids).append(")");
				if(cnt %10 ==9 )
				{
					sqls.add( "select * from " + table + " where " + sbIds.toString() );
					sbIds.setLength(0);
				}
				
			}
			if(sbIds.length() > 0)
			{
				sqls.add( "select * from " + table + " where " + sbIds.toString() );
				sbIds = null;
			}
		}
		
		return sqls;
	}
	/**
	 * 根据记录集及主键名称，生成ID的字符串
	 * @param list
	 * @param pkName
	 * @return
	 */
	public static String buildIds(List<Map<String,Object>> list ,String pkName,int from,int cnt)
	{
		if(list.size() ==0)
			return "";
		StringBuilder sbIds = new StringBuilder();
		if(from <0)
			from = 0;
		if(cnt <0 || cnt > list.size())
			cnt = list.size();
		
		for(int i = from;i < cnt; i++)
		{
			Map row = list.get(i);
			if(sbIds.length() >0)
				sbIds.append(",");
			Object v = row.get(pkName);
			if(v.getClass() == String.class)
				sbIds.append("'").append(v.toString()).append("'");
			else
				sbIds.append(v.toString());
		}
		return sbIds.toString();
		
	}
	/**
	 * 根据表配置，生成 需要进行比较 的记录信息的sql
	 * @param config
	 * @return
	 */
	public static String buildLoadCompareDataSql(TableConfig config)
	{		
		if(!StringUtils.hasText(config.getCompareFieldNames()))
			return "";
		String[] fieldNames = config.getCompareFieldNames().split(",");
		String[] fieldPkNames =  config.getPkNames().split(",");
		
		String fields = "";
		for(String fieldName:fieldPkNames)
		{
			if(fields.length() !=0)
			{
				fields +=",";
			}
			fields += fieldName;
		}
		for(String fieldName:fieldNames)
		{
			boolean b = false;
			for(String pkn :fieldPkNames)
			{
				if(pkn.equals(fieldName))
				{
					b = true;
					break;
				}
			}
			if(b)
				continue;

			if(fields.length() !=0)
			{
				fields +=",";
			}
			fields += fieldName;
		}
		
		return "select " + fields + " from " +  config.getTableName();
	}
	
	/**
	 * 
	 * @param config
	 * @return
	 */
	public static String buildDefaultUploadDataSql(TableConfig config)
	{
		String sql ="select * from " + config.getTableName() + " where " + config.getLastUpdateFieldName() + " > :" + config.getLastUpdateFieldName() ;
		if(StringUtils.hasText(config.getCompareExpression()))
		{
			sql += " and " + config.getCompareExpression();
		}
		
		return sql;
	}
	
	/**
	 * 根据任务配置，生成加载进行比较的数据集的SQL
	 * @param task
	 * @return
	 */
	public static Map<String,String> buildLoadCompareDataSql(SyncTaskConfig task)
	{
		Map<String,String> map = new LinkedHashMap<>();
		
		List<String> rels = task.getRelations();
		if(CollectionUtils.isEmpty(rels)) /// 独立的单表
		{
			for(TableConfig tc:task.getTableConfigs())
			{
				if(StringUtils.hasText(tc.getLoadCompareDataExpression()))
				{
					String sql = (String) GroovyScriptHelper.eval(tc.getLoadCompareDataExpression());
					map.put(tc.getTableName() ,sql);
				}else{				
					String sql = buildLoadCompareDataSql(tc);
					map.put(tc.getTableName() ,sql);
				}
			}
		}else{
			
		}
		return map;		
	}
	
	public static Map<String,String> buildUploadDataSql(List<TableConfig> configs)
	{
		Map<String,String> map = new LinkedHashMap<>();
		for(TableConfig tc:configs)
		{
			String sql = buildDefaultUploadDataSql(tc);
			map.put(tc.getTableName() ,sql);
		}
		return map;
	}
	
	public static String buildDeleteSql(SyncTaskConfig task,TableConfig tab)
	{
		List<String> rels = task.getRelations();
		if(rels.size() ==0) /// 独立的单表
		{
			String[] fieldPkNames =  tab.getPkNames().split(",");	
			
			String fields = "";
			for(String fieldName:fieldPkNames)
			{
				if(fields.length() !=0)
				{
					fields +=",";
				}
				fields += fieldName + "=:" + fieldName ;
			}
			
			String sql = "delete from " + tab.getTableName() + " where " + fields;
			return sql;
		}else{
			
		}
		return null;
	}
	
	public static String buildVersionCompareRowSql(TableMeta tm,String compareFieldNames,String pkFieldNames) throws Exception
	{
		boolean useParamNamed = true;
		String tableName = tm.getName();
		String key = pkFieldNames;	
		
		if(key == null || key.isEmpty())
		{
			key = "id";
		}
		String[] keys = key.split("[,;]");

		final ArrayList<FieldMeta> keycols = new ArrayList<>();
		for(String s:keys)
		{
			FieldMeta dc = tm.get(s);
			
			if(dc == null)
				throw new Exception("column " + s + " not exists!");
			keycols.add(dc);
		}
		

		StringBuilder where= new StringBuilder(64);
		
		for(int i = 0; i < keycols.size();i++)
		{
			if(where.length() >0)
			{
				where.append(" and ");
			}
			if(useParamNamed){
				where.append( keycols.get(i).getName()).append( "=:").append(keycols.get(i).getName());
			}else
				where.append( keycols.get(i).getName()).append( "=?");
		}
		
		return String.format("select %s from %s where %s " ,compareFieldNames, tableName , where);
	}
	
	public static String buildDefaultUpdateSql(TableMeta tm,String defaultKeys,boolean forceEvalVersion) throws Exception
	{
		boolean useParamNamed = true;
		
		String tableName = tm.getName();
		String key = defaultKeys;//tab.getPkNames();		
		
		if(key == null || key.isEmpty())
		{
			key = "id";
		}
		String[] keys = key.split("[,;]");

		final ArrayList<FieldMeta> keycols = new ArrayList<>();
		for(String s:keys)
		{
			FieldMeta dc = tm.get(s);
			
			if(dc == null)
				throw new Exception("column " + s + " not exists!");
			keycols.add(dc);
		}
		boolean bHasVersion = tm.get(IBean.default_version_name) != null;
		
		StringBuilder updateSet= new StringBuilder(64);
		StringBuilder updateWhere= new StringBuilder(64);

		for(int i = 0; i < tm.getFields().size();i++)
		{
			if(updateSet.length()>0)
			{
				updateSet.append(",");
			}
			
			String columnName = tm.getFields().get(i).getName();
			String safeFieldName = tm.getFields().get(i).getSafeDbFieldName();
			
			if(useParamNamed){
				updateSet.append( safeFieldName).append("=:").append(columnName);
			}else
				updateSet.append( safeFieldName).append("=?");
			
		}
		for(int i = 0; i < keycols.size();i++)
		{
			if(updateWhere.length() >0)
			{
				updateWhere.append(" and ");
			}
			if(useParamNamed){
				updateWhere.append( keycols.get(i).getName()).append( "=:").append(keycols.get(i).getName());
			}else
				updateWhere.append( keycols.get(i).getName()).append( "=?");
		}
		if(!forceEvalVersion && bHasVersion)
		{
			if(useParamNamed){
				updateWhere.append(" and " + IBean.default_version_name + "=:" + IBean.old_version_field_name);
			}
			else
				updateWhere.append(" and " + IBean.default_version_name + "=?");
		}
		String updateSql = "update " + tableName + " set " + updateSet + " where " + updateWhere;
		return updateSql;
	}
	
	public static String buildDefaultInsertSql(TableMeta tm)
	{
		boolean useParamNamed = true;
		String tableName  = tm.getName();
		String key = null;
		
		StringBuilder fields = new StringBuilder(64);
		StringBuilder sv= new StringBuilder(64);
		
		for(int i = 0; i < tm.getFields().size();i++)
		{
			if(sv.length()>0)
			{
				fields.append(",");
				sv.append(",");
			}
			FieldMeta fm = tm.getFields().get(i);
			String columnName = fm.getName();
			fields.append(fm.getSafeDbFieldName());
			if(useParamNamed){
				sv.append(":").append(columnName);
			}else
				sv.append("?");
			
		}
		String insertSql = "insert into " + tableName + " (" + fields + ") values(" + sv + ")"; 
		return insertSql;
	}
	
	public static Map<String,String> buildSqlForDownloadTask(List<TableConfig> tableConfigs,String sqlHospIds) throws Exception
	{
		List<String> sqls = new ArrayList<>();
		Map<String,TableConfig> map = new HashMap<>();
		for(TableConfig tc: tableConfigs)
		{
			map.put(tc.getTableName().toLowerCase(),tc);
		}
		Map<String,String> sqlMap = new HashMap<>(); 
		for(TableConfig tc: tableConfigs)
		{
			String sql=null;
			if(StringUtils.hasText( tc.getLoadCompareDataExpression()))
			{
				
			}else{
				sql = buildLoadDownloadDataSql(map,null,tc,1,sqlHospIds);
			}
			sqlMap.put(tc.getTableName().toLowerCase(),sql);
		}
		
		return sqlMap;
		
	}
	private static String buildLoadDownloadDataSql(Map<String,TableConfig> map , TableConfig tc,TableConfig childTc,
			int step,String sqlHospIds) throws Exception
	{
		String sql="";
		if(tc == null){ // 第一层 需要加入 last_update_datetime >: last_update_datetime
			if(StringUtils.hasText(childTc.getSiteIdFieldName())){ // 存在站点字段{
				
				sql = String.format("select * from %s t%d where %s > :%s and %s in (%s) ",
						childTc.getTableName(),(step) ,
						childTc.getLastUpdateFieldName(),childTc.getLastUpdateFieldName(),
						childTc.getSiteIdFieldName(),
						sqlHospIds);
				return sql;
			}
			else{
				TableConfig ptc  = map.get(childTc.getParentTableName().toLowerCase());
				sql = String.format("select * from %s t%d where t%d.%s > :%s and exists ",
						childTc.getTableName(),step ,step,
						childTc.getLastUpdateFieldName(),childTc.getLastUpdateFieldName()
						);
				tc = childTc;
				String nsql = buildLoadDownloadDataSql(map,ptc,tc,(step +1),sqlHospIds);
				sql = sql + "(" + nsql + ")";	
				return sql;	
			}
		}
		if(StringUtils.hasText(tc.getSiteIdFieldName()))
		{
			sql = String.format(
					"select * from %s t%d where "
					+ " t%d.%s = t%d.%s and"
					+ " t%d.%s in (%s)",
					tc.getTableName(),(step) ,
					(step),childTc.getParentId(),(step-1),childTc.getFkName(),
					(step),tc.getSiteIdFieldName(),sqlHospIds
					);
			
		}else{// 没有找到 site_fieldname 需从上级的表中继续查询 
			TableConfig ptc  = map.get(tc.getParentTableName().toLowerCase());
			if(ptc == null)
				throw new Exception("表配置不正确，下载任务中需要配置站点对应的字段!");			
			
			sql = String.format(
					"select * from %s t%d where "
					+ " t%d.%s = t%d.%s and exists ",
					tc.getTableName(),(step) ,
					(step),childTc.getParentId(),(step-1),childTc.getFkName()
					);
			
			String nsql = buildLoadDownloadDataSql(map,ptc,tc,(step +1),sqlHospIds);
			sql = sql + "(" + nsql + ")";			
		}
		return sql;
	}

}
