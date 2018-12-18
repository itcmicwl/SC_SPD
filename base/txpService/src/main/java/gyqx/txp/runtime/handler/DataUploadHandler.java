package gyqx.txp.runtime.handler;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import gyqx.txp.configuration.DataUploadTaskConfig;
import gyqx.txp.configuration.TableConfig;
import gyqx.txp.data.RecordSet;
import gyqx.txp.message.DataUploadRequestBody;
import gyqx.txp.message.TxpMessage;
import gyqx.txp.utils.sql.DbUtils;

public class DataUploadHandler 
{
	public static void saveUploadedData(
			DbUtils db,DataUploadTaskConfig taskConfig,TxpMessage srcMessage) throws Exception
	{
		DataUploadRequestBody body = (DataUploadRequestBody) srcMessage.getBody();
		String tableName;
		List<Map<String,Object>> rows;
		
		RecordSet rset =  body.getRecordSet();
		
		for(TableConfig tc: taskConfig.getTableConfigs())
		{
			tableName = tc.getTableName();
			rows = rset.get(tableName.toLowerCase());
			if(rows == null || rows.size() == 0)
				continue;
			String defaultKeys = tc.getCompareFieldNames();
			if(!StringUtils.hasText(defaultKeys))
				defaultKeys = "id,version";
			
			db.insertOrUpdateToTable(tc, rows);			
		}
	}

}
