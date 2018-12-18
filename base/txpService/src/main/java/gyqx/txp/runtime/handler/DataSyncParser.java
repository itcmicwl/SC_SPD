package gyqx.txp.runtime.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gyqx.txp.configuration.SyncTaskConfig;
import gyqx.txp.configuration.TableConfig;
import gyqx.txp.configuration.TaskConfig;
import gyqx.txp.data.SyncDataSet;
import gyqx.txp.utils.comparision.MapEqual;

public class DataSyncParser 
{
	/**
	 * 与中心数据库同步的分析
	 * @param taskConfig
	 * @param tableName
	 * @param subRecords 下级数据库参考分析数据
	 * @param centerRecords 中心库参考数据
	 * @throws Exception
	 */
	public static SyncDataSet  syncWithCenterAnalyse(SyncTaskConfig taskConfig,String tableName,List<Map<String,Object>> subRecords,List<Map<String,Object>> centerRecords) throws Exception
	{
		TableConfig tableConfig = taskConfig.getTableConfig(tableName);
		if(tableName == null)
			throw new Exception("任务配置["+ taskConfig.getName() +"]中没有表["+ tableName +"]");
		
		String[] compareFNames = tableConfig.getCompareFieldNames().split(",");
		String[] keyFNames = tableConfig.getPkNames().split(",");		
		
		MapEqual mapequal = new MapEqual(compareFNames);
		
		MapEqual mapKeyEqual = new MapEqual(keyFNames);
		
		List<Map<String,Object>> insertList = new ArrayList<>();
		List<Map<String,Object>> updateList = new ArrayList<>();
		List<Map<String,Object>> deleteList = new ArrayList<>();
		
		for(Map<String,Object> cmap : centerRecords)
		{
			int cflag = 0;
			int updateflag = 0;
			for(Map sm : subRecords)
			{
				if(mapequal.compare(cmap, sm))
				{
					//crossList.add(sm);
					cflag = 1; /// 匹配的记录
					break;
				}else if(mapKeyEqual.compare(cmap, sm) ){
					updateflag = 1;
				}
			}
			if(updateflag == 1)
			{
				updateList.add(cmap);
			}
			if(cflag == 0 && updateflag ==0 ){
				insertList.add(cmap);
			}
		}
		
		for(Map<String,Object> sm : subRecords)
		{
			int cflag = 0;
			int updateflag = 0;
			for(Map cmap : centerRecords){
				if(mapequal.compare(sm,cmap))
				{
					//crossList.add(sm);
					cflag = 1; /// 匹配的记录
					break;
				}else if(mapKeyEqual.compare(sm,cmap) ){
					updateflag = 1;
					break;
				}
			}
			if(cflag == 0 && updateflag ==0 ){
				deleteList.add(sm);
			}
		}		
		
		SyncDataSet sds = new SyncDataSet();
		sds.setTableName(tableName);
		sds.setInsertList(insertList);
		sds.setUpdateList(updateList);
		sds.setDeleteList(deleteList);		
		
		return sds;
		
	}
	
	

}
