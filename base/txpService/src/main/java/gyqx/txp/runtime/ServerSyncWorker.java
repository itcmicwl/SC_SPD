package gyqx.txp.runtime;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import gyqx.txp.configuration.DataDownloadTaskConfig;
import gyqx.txp.configuration.DataUploadTaskConfig;
import gyqx.txp.configuration.DbSources;
import gyqx.txp.configuration.FileCompareConfig;
import gyqx.txp.configuration.FileDownloadTaskConfig;
import gyqx.txp.configuration.HospInfo;
import gyqx.txp.configuration.SiteFileCompareConfig;
import gyqx.txp.configuration.SiteInfo;
import gyqx.txp.configuration.SyncTaskConfig;
import gyqx.txp.configuration.TableConfig;
import gyqx.txp.configuration.TxpServerConfig;
import gyqx.txp.data.FilePath;
import gyqx.txp.data.FilePathData;
import gyqx.txp.data.RecordSet;
import gyqx.txp.data.SyncDataSet;
import gyqx.txp.data.SyncSqlSet;
import gyqx.txp.db.meta.DbMetaInfo;
import gyqx.txp.message.FileDownloadResponseBody;
import gyqx.txp.message.FileUploadRequestBody;
import gyqx.txp.message.FileUploadResponseBody;
import gyqx.txp.message.DataDownloadResponseBody;
import gyqx.txp.message.FileDownloadRequestBody;
import gyqx.txp.message.MessageBodyKind;
import gyqx.txp.message.TablesSyncBody;
import gyqx.txp.message.TablesSyncResultBody;
import gyqx.txp.message.TxpMessage;
import gyqx.txp.message.ext.FileCompareBody;
import gyqx.txp.message.ext.FileCompareRespItem;
import gyqx.txp.message.ext.FileCompareResponse;
import gyqx.txp.runtime.handler.DataDownloadHandler;
import gyqx.txp.runtime.handler.DataSyncParser;
import gyqx.txp.runtime.handler.DataUploadHandler;
import gyqx.txp.runtime.status.TaskWorkStatus;
import gyqx.txp.runtime.status.TaskWorkStatusUtils;
import gyqx.txp.utils.comparision.file.DirCompareRequest;
import gyqx.txp.utils.comparision.file.DirCompareResult;
import gyqx.txp.utils.comparision.file.FileCompareUtil;
import gyqx.txp.utils.exception.ErrorMessager;
import gyqx.txp.utils.exception.TxpException;
import gyqx.txp.utils.sql.DbUtils;
import gyqx.txp.utils.sql.TaskSqlBuilder;

public class ServerSyncWorker 
{
	private static final Logger logger = LoggerFactory.getLogger(ServerSyncWorker.class);
	private static ServerSyncWorker current = null;
	public static ServerSyncWorker getCurrent()
	{
		return current;
	}
	public ServerSyncWorker()
	{
		current = this;
	}
	
	private TxpServerConfig config;	
	
	private String defaultDataSourceName;
	
	public void init() throws SQLException
	{
		DbMetaInfo dbMeta = DbSources.getCurrent().getDbMetaInfo(defaultDataSourceName);
		try{
			config.checkTaskConfigs(dbMeta);
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public TxpProcResult txpRequestProc(TxpMessage srcMessage) throws Exception
	{
		TxpProcResult result = new TxpProcResult();
		TxpMessage ret = null;
		try{
			config.validate(srcMessage.getFromId(), srcMessage.getTermimalSession() );
			result.setTaskName(srcMessage.getTaskName());
			logger.info("start txpRequestProc :{}",srcMessage.getTaskName());
			switch(srcMessage.getKind())
			{
			case TablesSyncCompare:
				ret = procSyncDataRequest(srcMessage);
				break;
			case DataUpload:
				ret = procUploadDataRequest(srcMessage);
				break;
			case DataDownload:
				ret = procDownloadDataRequest(srcMessage);
				break;
			case FileDownloadRequest:
				ret = procDownloadFilesRequest(srcMessage);
				break;
			case FileUploadRequest:
				break;
			case FileCompareRequest:
				ret = procFileCompareRequest(srcMessage);
				break;
			default:
				break;
			}
		}catch(Exception ex){
			logger.error(ErrorMessager.fromException(ex));
			throw ex;
		}
		result.setTxpMessage(ret);
		return result;
	}

	public TxpMessage procSyncDataRequest(TxpMessage srcMessage) throws Exception
	{
		if(!srcMessage.getKind().equals(MessageBodyKind.TablesSyncCompare))
			return null;
		
		SyncTaskConfig taskConfig = TxpServerConfig.cfg.getSyncTaskConfig( srcMessage.getTaskName());
		if(taskConfig == null)
			return null;
		DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		DbUtils db = new DbUtils(dataSource);
		
		Map<String,String> sqlMap = TaskSqlBuilder.buildLoadCompareDataSql(taskConfig);
		
		TablesSyncBody tdbody = (TablesSyncBody) srcMessage.getBody();
		
		RecordSet rset = tdbody.getData();
		
		TablesSyncResultBody body = new TablesSyncResultBody(); 
		List<SyncDataSet> syncDataSetList = new ArrayList<>();
		
		TxpMessage result = new TxpMessage();
		result.setCatalog(taskConfig.getCatalog());
		result.setBody(body);
		result.setDataSyncVersion(srcMessage.getDataSyncVersion());
		result.setFromId("");
		result.setToId(srcMessage.getFromId());
		result.setKind(MessageBodyKind.TablesSyncCompareResult);
		result.setTaskName(srcMessage.getTaskName());
		result.setIdempotentValue(srcMessage.getIdempotentValue());
		
		
		try{
			Iterator<Entry<String,List<Map<String,Object>>>> iter = rset.entrySet().iterator();
			
			while(iter.hasNext())
			{
				Entry<String,List<Map<String,Object>>> entry = iter.next();
				
				String tableName = entry.getKey();
				List<Map<String,Object>> subRecords = entry.getValue();
				
				String sql = sqlMap.get(tableName);
				List<Map<String,Object>> centerRecords =  db.query(sql);
				
				SyncDataSet syncDataSet = null;
				
				try {
					syncDataSet = DataSyncParser.syncWithCenterAnalyse(taskConfig, tableName, subRecords, centerRecords);
				} catch (Exception e) {
					throw new TxpException("syncWithCenterAnalyse 出错;  task:" + taskConfig.getName() + ";table:" + tableName + "" );
				}
				
				TableConfig tableConfig = taskConfig.getTableConfig(tableName);	
				SyncSqlSet sss = TaskSqlBuilder.buildProductSql(tableConfig, syncDataSet);
				
				syncDataSet.setTableName(tableName);
				
				List<Map<String,Object>> insertList = null,updateList = null,deleteList = null;
				
				if(sss.getInsertDataLoadSqls().size() > 0){
					insertList = new ArrayList<>();
					for(String segsql: sss.getInsertDataLoadSqls()) // 分段加载
					{
						List<Map<String,Object>> insertList0 = db.query(segsql);
						insertList.addAll(insertList0);
					}
				}
				
				if(sss.getUpdateDataLoadSqls().size() > 0)
				{
					updateList = new ArrayList<>();
					for(String segsql: sss.getUpdateDataLoadSqls()) // 分段加载
					{
						List<Map<String,Object>> insertList0 = db.query(segsql);
						updateList.addAll(insertList0);
					}
				}
				
				deleteList = syncDataSet.getDeleteList(); /// 返回需要删除数据的主键信息
				/*if(StringUtils.hasText(sss.getDeleteSql()))
					deleteList = db.query(sss.getDeleteSql());*/
				
				syncDataSet.setInsertList(insertList);
				syncDataSet.setUpdateList(updateList);
				syncDataSet.setDeleteList(deleteList);
				
				syncDataSetList.add(syncDataSet);
				
			}	
			
			body.setData(syncDataSetList);
		}catch(Exception ex)
		{
			throw ex;
		}		
		return result;
	}
	public TxpMessage procUploadDataRequest(TxpMessage srcMessage) throws Exception
	{
		if(!srcMessage.getKind().equals(MessageBodyKind.DataUpload))
			return null;
		
		DataUploadTaskConfig taskConfig = TxpServerConfig.cfg.getDataUploadTaskConfig(srcMessage.getTaskName());
		if(taskConfig == null)
			return null;
		
		DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		DbUtils db = new DbUtils(dataSource);

		TxpMessage result = new TxpMessage();
		result.setTaskName(srcMessage.getTaskName());
		result.setToId(srcMessage.getFromId());	
		result.setCatalog(srcMessage.getCatalog());
		result.setKind(MessageBodyKind.DataUploadResult);
		result.setDataSyncDatetime(srcMessage.getDataSyncDatetime());
		result.setDataSyncVersion(srcMessage.getDataSyncVersion());
		result.setHasMoreData(false);
		
		try {
			db.beginTransaction();
			DataUploadHandler.saveUploadedData(db, taskConfig, srcMessage);
			db.commit();
		} catch (Exception e)
		{
			db.rollback();
			throw e;
		}
		return result;
	}
	
	public TxpMessage procDownloadDataRequest(TxpMessage srcMessage) throws Exception
	{
		if(!srcMessage.getKind().equals(MessageBodyKind.DataDownload))
			return null;
		
		DataDownloadTaskConfig taskConfig = TxpServerConfig.cfg.getDataDownloadTaskConfig(srcMessage.getTaskName());
		if(taskConfig == null)
			return null;
		
		DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		DbUtils db = new DbUtils(dataSource);
		
		TaskWorkStatusUtils twsu = CenterRuntime.current().getTaskWorkStatusUtils();
		TaskWorkStatus tws =  twsu.getStatus(taskConfig);
		if(tws == null)
			tws = twsu.workRefresh(taskConfig,true);
		else
			twsu.workStart(taskConfig);
		
		TxpMessage result = new TxpMessage();
		result.setTaskName(srcMessage.getTaskName());
		result.setToId(srcMessage.getFromId());	
		result.setCatalog(srcMessage.getCatalog());
		result.setKind(MessageBodyKind.DataDownloadResult);
		result.setDataSyncDatetime(new Date() ); // 取服务端当前时间为加载的截止时间
		result.setDataSyncVersion(srcMessage.getDataSyncVersion());
		result.setHasMoreData(false);
		DataDownloadResponseBody body = new DataDownloadResponseBody();
		
		RecordSet mapRecords = null;
		try {
			db.beginTransaction();
			if(taskConfig.isForSingleSite()) /// 如果是针对某个站点，就只加载某站点的数据
			{
				SiteInfo si = config.validate(srcMessage.getFromId(), srcMessage.getTermimalSession() );
				List<HospInfo> hospIfs = si.getHospInfos(); // config.getMapSiteHospitalIfs().get(srcMessage.getFromId());
				if(hospIfs == null || hospIfs.size() == 0)
					throw new Exception(String.format("错误的siteId:%s,根据此站点ID没有找到对应的医院IDs",srcMessage.getFromId()));
				
				List<String> hospIds = new ArrayList<>();
				hospIfs.forEach(info->hospIds.add(info.getId()));
				
				
				mapRecords = DataDownloadHandler.loadDataForSite(db, taskConfig, srcMessage, tws,srcMessage.getFromId(),hospIds);
			}else{
				mapRecords = DataDownloadHandler.loadData(db, taskConfig, srcMessage, tws);
			}
			body.setRecordSet(mapRecords);
			result.setBody(body);
			db.commit();
		} catch (Exception e)
		{
			db.rollback();
			throw e;
		}
		
		return result;
		
	}
	public TxpMessage procDownloadFilesRequest(TxpMessage srcMessage) throws IOException
	{
		if(!srcMessage.getKind().equals(MessageBodyKind.FileDownloadRequest))
			return null;
		
		FileDownloadRequestBody body = (FileDownloadRequestBody) srcMessage.getBody();
		
		List<FilePathData> fpdataItems = new ArrayList<>();
		
		int dataLength = 0;
		boolean hasMore = false;
		FileDownloadTaskConfig fsconfig =  config.getFileDownloadTaskConfig(srcMessage.getTaskName());
		if(fsconfig == null)
			return null;
		
		Map<String,String> map = new HashMap<>();
		
		String basePath = StringUtils.hasText( fsconfig.getBasePath())? fsconfig.getBasePath():TxpServerConfig.cfg.getFileDownloadPath();
		
		for(FilePath fp: body.getFilePaths())
		{
			String fileName = basePath + "/" + fp.getRelativePath()+ "/" + fp.getFileName();
			
			if(map.get(fileName) != null)
				continue;
			
			File file = new File(fileName);
			byte[] filedata = null;
			if(file.exists()){
				filedata = FileUtils.readFileToByteArray(file);
				FilePathData fpdata = new FilePathData();
				fpdata.setData(filedata);
				fpdata.setFileName(fp.getFileName());
				fpdata.setRelativePath(fp.getRelativePath());
				fpdataItems.add(fpdata);
				map.put(fileName, fileName);
				
				dataLength += filedata.length;
			}
			if(config.getFileDownloadDataMaxLength()>0 && dataLength > config.getFileDownloadDataMaxLength())
			{
				hasMore = true;
				break;
			}
		}
		FileDownloadResponseBody fileDownloadBody = new FileDownloadResponseBody();
		
		fileDownloadBody.setFilePathDataItems(fpdataItems);		
		
		TxpMessage result = new TxpMessage();
		result.setTaskName(srcMessage.getTaskName());
		result.setCatalog(srcMessage.getCatalog());
		result.setToId(srcMessage.getFromId());	
		result.setKind(MessageBodyKind.FileDownloadResult);
		result.setHasMoreData(hasMore);
		result.setBody(fileDownloadBody);
		result.setDataSyncDatetime(new Date() ); // 取服务端当前时间为加载的截止时间
		result.setDataSyncVersion(srcMessage.getDataSyncVersion());
		return result;
	}
	public TxpMessage procUploadFilesRequest(TxpMessage srcMessage) throws IOException
	{
		if(!srcMessage.getKind().equals(MessageBodyKind.FileUploadRequest))
			return null;
		
		FileUploadRequestBody body = (FileUploadRequestBody) srcMessage.getBody();
		
		List<FilePathData> fpdataItems = body.getFilePathDataItems();

		for(FilePathData fpd:fpdataItems)
		{
			String fileName = TxpServerConfig.cfg.getFileDownloadPath() + "/" + fpd.getRelativePath()+ "/" + fpd.getFileName();
			File file = new File(fileName);
			logger.info("fileName:{} , size:{}", fileName,fpd.getData().length);
			FileUtils.writeByteArrayToFile(file, fpd.getData());
		}
		
		FileUploadResponseBody fileUploadBody = new FileUploadResponseBody();
		TxpMessage result = new TxpMessage();
		result.setTaskName(srcMessage.getTaskName());
		result.setCatalog(srcMessage.getCatalog());
		result.setToId(srcMessage.getFromId());	
		result.setKind(MessageBodyKind.FileUploadResult);
		result.setHasMoreData(false);
		result.setBody(fileUploadBody);
		result.setDataSyncDatetime(srcMessage.getDataSyncDatetime());
		result.setDataSyncVersion(srcMessage.getDataSyncVersion());
		return result;
	}
	public TxpMessage procFileCompareRequest(TxpMessage srcMessage) throws Exception
	{
		FileCompareBody body = (FileCompareBody) srcMessage.getBody();
		SiteFileCompareConfig sfcConfig = config.getSiteFileCompareConfig(srcMessage.getTaskName());
		if(sfcConfig == null)
			return null;
		FileCompareResponse response = new FileCompareResponse();
		for(DirCompareRequest request: body.getCompareRequests())
		{
			FileCompareConfig fcc =  sfcConfig.find(request.getKey());
			if(fcc == null)
				continue;
			
			DirCompareResult result = FileCompareUtil.compare(fcc, request);
			FileCompareRespItem item = new FileCompareRespItem();
			item.setKey(fcc.getKey());
			item.setResult(result);
			response.getResults().add(item);
		}
		if(response.getResults().size() == 0)
			return null;
		response.setScript(sfcConfig.getScript() ); // 由服务端决定
		TxpMessage result = new TxpMessage();
		result.setIdempotentValue(srcMessage.getIdempotentValue());
		result.setTaskName(srcMessage.getTaskName());
		result.setCatalog(srcMessage.getCatalog());
		result.setToId(srcMessage.getFromId());	
		result.setKind(MessageBodyKind.FileCompareResponse);
		result.setHasMoreData(false);
		result.setDirectCmd(srcMessage.isDirectCmd()); // 由客户决定
		result.setBody(response);
		return result;
	}
	
	public String getDefaultDataSourceName() {
		return defaultDataSourceName;
	}
	public void setDefaultDataSourceName(String defaultDataSourceName) {
		this.defaultDataSourceName = defaultDataSourceName;
	}
	public TxpServerConfig getConfig() {
		return config;
	}
	public void setConfig(TxpServerConfig config) {
		this.config = config;
	}

}
