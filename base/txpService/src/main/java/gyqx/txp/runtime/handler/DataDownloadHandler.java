package gyqx.txp.runtime.handler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import common.db.SqlUtils;
import gyqx.txp.configuration.DataDownloadTaskConfig;
import gyqx.txp.configuration.TableConfig;
import gyqx.txp.data.KV;
import gyqx.txp.data.RecordSet;
import gyqx.txp.message.DataDownloadResponseBody;
import gyqx.txp.message.TxpMessage;
import gyqx.txp.runtime.status.TaskWorkStatus;
import gyqx.txp.utils.sql.DbUtils;
import gyqx.txp.utils.sql.TaskSqlBuilder;

public class DataDownloadHandler 
{
	public static RecordSet loadData(DbUtils db,DataDownloadTaskConfig tc,TxpMessage srcMessage,TaskWorkStatus tws)
	{
		RecordSet mapRecords = new RecordSet(); 
		/// 取30分钟之前为取值的下限，加载大于下限的数据
		Date limitDate = DateUtils.addMinutes(srcMessage.getDataSyncDatetime(), -30);
		for(TableConfig tcfg: tc.getTableConfigs())
		{
			String sql = TaskSqlBuilder.buildDefaultUploadDataSql(tcfg);			
			
			List<Map<String,Object>> records = db.query(sql,
					KV.from(tcfg.getLastUpdateFieldName(),limitDate)); 
			mapRecords.put(tcfg.getTableName().toLowerCase(), records);
		}
		
		return mapRecords;
	}
	public static RecordSet loadDataForSite(DbUtils db,DataDownloadTaskConfig tc,
			TxpMessage srcMessage,TaskWorkStatus tws,String siteId,
			List<String> hospIds) throws Exception
	{
		RecordSet mapRecords = new RecordSet(); 
		String sqlHospIds = SqlUtils.idListToSql(hospIds);
		
		Map<String,String> sqlMap = TaskSqlBuilder.buildSqlForDownloadTask(tc.getTableConfigs(),sqlHospIds);
		
		for(TableConfig tcfg: tc.getTableConfigs())
		{
			String sql = sqlMap.get(tcfg.getTableName().toLowerCase());
			/// 取 30分钟之前为取值的下限，加载大于下限的数据
			List<Map<String,Object>> records = db.query(sql,
					KV.from(tcfg.getLastUpdateFieldName(),DateUtils.addMinutes(srcMessage.getDataSyncDatetime(), -30)
							)); 
			mapRecords.put(tcfg.getTableName().toLowerCase(), records);
		}
		
		return mapRecords;
	}
	public static int saveDownloadedData(
			DbUtils db,DataDownloadTaskConfig taskConfig,TxpMessage srcMessage) throws Exception
	{
		DataDownloadResponseBody body = (DataDownloadResponseBody) srcMessage.getBody();
		String tableName;
		List<Map<String,Object>> rows;
		
		RecordSet rset =  body.getRecordSet();
		int cnt = 0;
		for(TableConfig tc: taskConfig.getTableConfigs())
		{
			tableName = tc.getTableName();
			rows = rset.get(tableName.toLowerCase());
			if(rows == null || rows.size() == 0)
				continue;

			db.insertOrUpdateToTable(tc, rows);	
			cnt += rows.size();
		}
		return cnt;
	}

}
