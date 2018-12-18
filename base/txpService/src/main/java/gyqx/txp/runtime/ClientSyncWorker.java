package gyqx.txp.runtime;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import gyqx.txp.configuration.DataDownloadTaskConfig;
import gyqx.txp.configuration.DataUploadTaskConfig;
import gyqx.txp.configuration.DbSources;
import gyqx.txp.configuration.FileCompareConfig;
import gyqx.txp.configuration.FileDownloadTaskConfig;
import gyqx.txp.configuration.FileUploadTaskConfig;
import gyqx.txp.configuration.SiteFileCompareConfig;
import gyqx.txp.configuration.SyncTaskConfig;
import gyqx.txp.configuration.TableConfig;
import gyqx.txp.configuration.TaskConfig;
import gyqx.txp.configuration.TransferTaskConfig;
import gyqx.txp.configuration.TxpClientConfig;
import gyqx.txp.configuration.TxpServerConfig;
import gyqx.txp.data.FilePath;
import gyqx.txp.data.FilePathData;
import gyqx.txp.data.KV;
import gyqx.txp.data.RecordSet;
import gyqx.txp.data.SyncDataSet;
import gyqx.txp.db.meta.DbMetaInfo;
import gyqx.txp.db.meta.TableMeta;
import gyqx.txp.message.DataUploadRequestBody;
import gyqx.txp.message.FileDownloadRequestBody;
import gyqx.txp.message.FileDownloadResponseBody;
import gyqx.txp.message.FileUploadRequestBody;
import gyqx.txp.message.MessageBodyKind;
import gyqx.txp.message.TablesSyncBody;
import gyqx.txp.message.TablesSyncResultBody;
import gyqx.txp.message.TxpMessage;
import gyqx.txp.message.ext.FileCompareBody;
import gyqx.txp.message.ext.FileCompareRespItem;
import gyqx.txp.message.ext.FileCompareResponse;
import gyqx.txp.runtime.handler.DataDownloadHandler;
import gyqx.txp.runtime.handler.DataUploadHandler;
import gyqx.txp.runtime.status.TaskWorkStatus;
import gyqx.txp.runtime.status.TaskWorkStatusUtils;
import gyqx.txp.utils.comparision.file.DirCompareInfo;
import gyqx.txp.utils.comparision.file.DirCompareRequest;
import gyqx.txp.utils.comparision.file.FileCompareUtil;
import gyqx.txp.utils.exception.ErrorMessager;
import gyqx.txp.utils.exception.TxpException;
import gyqx.txp.utils.groovy.CustomGroovyShellFactory;
import gyqx.txp.utils.groovy.GroovyScriptHelper;
import gyqx.txp.utils.script.ScriptContext;
import gyqx.txp.utils.script.TxpScript;
import gyqx.txp.utils.script.TxpScriptExecutor;
import gyqx.txp.utils.sql.DbUtils;
import gyqx.txp.utils.sql.TaskSqlBuilder;

public class ClientSyncWorker 
{
	private static final Logger logger = LoggerFactory.getLogger(ClientSyncWorker.class);
	private static ClientSyncWorker current = null;
	public static ClientSyncWorker getCurrent()
	{
		return current;
	}
	public ClientSyncWorker()
	{
		current = this;
	}
	private TxpClientConfig config;	
	private String defaultDataSourceName;
	private CustomGroovyShellFactory groovyShellFactory;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss-SSS");
	
	public void init() throws SQLException
	{
		DbMetaInfo dbMeta = DbSources.getCurrent().getDbMetaInfo(defaultDataSourceName);
		try{
			config.checkTaskConfigs(dbMeta);
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
		GroovyScriptHelper.setShell( groovyShellFactory.createGroovyShell(null));
	}
	
	public TxpLoadRequestResult loadTxpDataRequest(Map<String,Object> header)
	{
		TxpLoadRequestResult result = new TxpLoadRequestResult();
		List<TxpMessage> txpMessages = null;
		try{
			String txpMethod = header.get("txpMethod") == null? "":header.get("txpMethod").toString();
			String taskNames = header.get("txpTaskNames") == null? "":header.get("txpTaskNames").toString();
			String catalog = header.get("txpCatalog") == null? "":header.get("txpCatalog").toString();
			if(txpMethod.length() ==0 )
				txpMethod = catalog;
			
			if(txpMethod.equalsIgnoreCase(SyncTaskConfig.catalog_code))
				txpMessages =  loadDbDataSyncTaskRequest(header);
			else if(txpMethod.equalsIgnoreCase(DataUploadTaskConfig.catalog_code ))
			{
				txpMessages = loadDataUploadTaskRequest(header);
			}else if(txpMethod.equalsIgnoreCase(DataDownloadTaskConfig.catalog_code ))
			{
				txpMessages = loadDataDownloadTaskRequest(header);
			}else if(txpMethod.equalsIgnoreCase(FileDownloadTaskConfig.catalog_code))
			{
				txpMessages =  loadFileDownloadTaskRequest(header);
			}else if(txpMethod.equalsIgnoreCase(FileUploadTaskConfig.catalog_code))
			{
				//txpMessages =  loadFileUploadloadTaskRequest(header);
			}else if(txpMethod.equalsIgnoreCase(TransferTaskConfig.catalog_code))
			{
				
			}else if(txpMethod.equalsIgnoreCase(SiteFileCompareConfig.catalog_code))
			{
				txpMessages = loadSiteFileCompareTaskRequest(header);
			}else if(txpMethod.equalsIgnoreCase("notify"))
			{
				notifyTxpTask(taskNames,catalog);
			}			
			
		}catch(Exception ex)
		{
			result.setCode(-1);
			String error = ErrorMessager.fromException(ex);
			result.setErrorMessage(error);
			logger.error(error);
		}
		if(txpMessages != null){
			for(TxpMessage msg:txpMessages)
			{
				msg.setTermimalSession(config.getClientId());
			}
		}
		result.setTxpMessages(txpMessages);
		return result;
	}
	
	public void closeNotify(String taskName,String catalog) throws IOException
	{
		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		if(checkConfig(taskName,catalog)){
			try {
				twsu.notifyFinish(taskName, catalog);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.toString());
			}
		}		
	}
	public void notifyTxpTask(String taskNames,String catalog) throws IOException
	{
		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		String[] tnames = taskNames.split(",");
		for(String tname : tnames){
			if(StringUtils.hasText(tname) && checkConfig(tname,catalog)){				
				try {
					twsu.notifyTask(tname, catalog);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.toString());
				}
			}
		}
	}
	
	public boolean checkConfig(String taskName,String catalog)
	{
		return null != this.config.getTaskConfig(taskName, catalog);
	}
	
	
	public List<TxpMessage> loadDbDataSyncTaskRequest(Map<String,Object> header) throws Exception
	{
		List<SyncTaskConfig> taskConfigs =  config.getSyncTaskConfigs();
		List<TxpMessage> taskSyncData = new ArrayList<>();
		String taskNames = header.get("txpTaskNames") == null? "":header.get("txpTaskNames").toString();
		if(StringUtils.hasText(taskNames)){
			String[] taskNameArray = taskNames.split("[,;]");
			for(String n:taskNameArray){
				SyncTaskConfig tc = config.getSyncTaskConfig(n);
				if(tc == null)
					continue;
				TxpMessage msg = getDbSyncRequest(tc);
				closeNotify(tc.getName(),tc.getCatalog());
				if(msg == null)
				{
					continue;
				}				
				msg.setCatalog(tc.getCatalog());
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}else{
			for(SyncTaskConfig tc: taskConfigs)
			{
				TxpMessage msg = getDbSyncRequest(tc);
				closeNotify(tc.getName(),tc.getCatalog());
				if(msg == null)
				{
					continue;
				}
				msg.setCatalog(tc.getCatalog());
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}
		
		return taskSyncData;
	}
	public List<TxpMessage> loadFileDownloadTaskRequest(Map<String,Object> header) throws Exception
	{
		List<FileDownloadTaskConfig> taskConfigs =  config.getFileDownloadTaskConfigs();
		List<TxpMessage> taskSyncData = new ArrayList<>();
		String taskNames = header.get("txpTaskNames") == null? "":header.get("txpTaskNames").toString();
		if(StringUtils.hasText(taskNames)){
			String[] taskNameArray = taskNames.split("[,;]");
			for(String n:taskNameArray){
				FileDownloadTaskConfig tc = config.getFileDownloadTaskConfig(n);
				if(tc == null)
					continue;
				TxpMessage msg = getFileDownloadRequest(tc);
				closeNotify(tc.getName(),tc.getCatalog());		
				if(msg == null)
				{
					continue;
				}
				
				msg.setCatalog(tc.getCatalog());
						
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}else{
			for(FileDownloadTaskConfig tc: taskConfigs)
			{
				TxpMessage msg = getFileDownloadRequest(tc);
				closeNotify(tc.getName(),tc.getCatalog());		
				if(msg == null)
				{
					continue;
				}
				msg.setCatalog(tc.getCatalog());
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}
		
		return taskSyncData;
	}
	
	public List<TxpMessage> loadFileUploadTaskRequest(Map<String,Object> header) throws Exception
	{
		List<FileUploadTaskConfig> taskConfigs =  config.getFileUploadTaskConfigs();
		List<TxpMessage> taskSyncData = new ArrayList<>();
		String taskNames = header.get("txpTaskNames") == null? "":header.get("txpTaskNames").toString();
		if(StringUtils.hasText(taskNames)){
			String[] taskNameArray = taskNames.split("[,;]");
			for(String n:taskNameArray){
				FileUploadTaskConfig tc = config.getFileUploadTaskConfig(n);
				if(tc == null)
					continue;
				TxpMessage msg = getFileUploadRequest(tc);
				closeNotify(tc.getName(),tc.getCatalog());
				if(msg == null)
				{
					continue;
				}
				msg.setCatalog(tc.getCatalog());
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}else{
			for(FileUploadTaskConfig tc: taskConfigs)
			{
				TxpMessage msg = getFileUploadRequest(tc);
				closeNotify(tc.getName(),tc.getCatalog());
				if(msg == null)
				{
					continue;
				}
				msg.setCatalog(tc.getCatalog());				
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}
		
		return taskSyncData;
	}
	
	public List<TxpMessage> loadDataUploadTaskRequest(Map<String,Object> header) throws Exception
	{
		List<DataUploadTaskConfig> taskConfigs = config.getDataUploadTaskConfigs();
		
		List<TxpMessage> taskSyncData = new ArrayList<>();
		String taskNames = header.get("txpTaskNames") == null? "":header.get("txpTaskNames").toString();
		if(StringUtils.hasText(taskNames)){
			String[] taskNameArray = taskNames.split("[,;]");
			for(String n:taskNameArray){
				DataUploadTaskConfig tc = config.getDataUploadTaskConfig(n);
				if(tc == null)
					continue;
				closeNotify(tc.getName(),tc.getCatalog());
				TxpMessage msg = getDataUploadTaskRequest(tc);
				if(msg == null)
				{
					continue;
				}
				msg.setCatalog(tc.getCatalog());
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}else{
			for(DataUploadTaskConfig tc: taskConfigs)
			{
				closeNotify(tc.getName(),tc.getCatalog());
				TxpMessage msg = getDataUploadTaskRequest(tc);				
				if(msg == null)
				{
					continue;
				}
				msg.setCatalog(tc.getCatalog());
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}
		
		return taskSyncData;
	}	
	
	public List<TxpMessage> loadDataDownloadTaskRequest(Map<String,Object> header) throws Exception
	{
		List<DataDownloadTaskConfig> taskConfigs = config.getDataDownloadTaskConfigs();
		
		List<TxpMessage> taskSyncData = new ArrayList<>();
		String taskNames = header.get("txpTaskNames") == null? "":header.get("txpTaskNames").toString();
		if(StringUtils.hasText(taskNames)){
			String[] taskNameArray = taskNames.split("[,;]");
			for(String n:taskNameArray){
				DataDownloadTaskConfig tc = config.getDataDownloadTaskConfig(n);
				if(tc == null)
					continue;
				TxpMessage msg = getDataDownloadTaskRequest(tc);
				closeNotify(tc.getName(),tc.getCatalog());
				if(msg == null)
				{
					continue;
				}
				msg.setCatalog(tc.getCatalog());
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}else{
			for(DataDownloadTaskConfig tc: taskConfigs)
			{
				TxpMessage msg = getDataDownloadTaskRequest(tc);
				closeNotify(tc.getName(),tc.getCatalog());
				if(msg == null)
				{
					continue;
				}
				msg.setCatalog(tc.getCatalog());
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}
		
		return taskSyncData;
	}
	
	public List<TxpMessage> loadSiteFileCompareTaskRequest(Map<String,Object> header) throws Exception
	{
		List<SiteFileCompareConfig> taskConfigs = config.getSiteFileCompareConfigs();
		
		List<TxpMessage> taskSyncData = new ArrayList<>();
		String taskNames = header.get("txpTaskNames") == null? "":header.get("txpTaskNames").toString();
		if(StringUtils.hasText(taskNames)){
			String[] taskNameArray = taskNames.split("[,;]");
			for(String n:taskNameArray){
				SiteFileCompareConfig tc = config.getSiteFileCompareConfig(n);
				if(tc == null)
					continue;
				TxpMessage msg = getSiteFileCompareRequest(tc);
				closeNotify(tc.getName(),tc.getCatalog());
				if(msg == null)
				{
					continue;
				}
				msg.setCatalog(tc.getCatalog());
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}else{
			for(SiteFileCompareConfig tc: taskConfigs)
			{
				TxpMessage msg = getSiteFileCompareRequest(tc);
				closeNotify(tc.getName(),tc.getCatalog());
				if(msg == null)
				{
					continue;
				}
				msg.setCatalog(tc.getCatalog());
				msg.setFromId(config.getSiteId());
				taskSyncData.add(msg);
			}
		}
		
		return taskSyncData;
	}
	
	private TxpMessage getDataUploadTaskRequest(DataUploadTaskConfig tc) throws IOException, SQLException
	{
		DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		DbUtils db = new DbUtils(dataSource);

		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		TaskWorkStatus tws =  twsu.getStatus(tc);
		if(tws == null)
			tws = twsu.workRefresh(tc,true);
		else{
			if(tws.getLastChangeVersion() == tws.getLastSyncVersion()) //// 防止重复调用 
				return null;
			twsu.workStart(tc);
		}

		TxpMessage msg = new TxpMessage();
		msg.setTaskName(tc.getName());
		msg.setFromId(config.getSiteId());
		msg.setCatalog(tc.getCatalog());
		msg.setKind(MessageBodyKind.DataUpload);
		msg.setDataSyncVersion(tws.getLastChangeVersion());
		msg.setDataSyncDatetime(tws.getLastChangeDatetime());

		RecordSet mapRecords = new RecordSet(); 
		int cnt = 0;
		try{
			for(TableConfig tcfg: tc.getTableConfigs())
			{
				String sql = TaskSqlBuilder.buildDefaultUploadDataSql(tcfg);
				/// 取 1小时之前为取值的下限，加载大于下限的数据，（不会一个事务三十分钟还没提交完吧？）
				List<Map<String,Object>> records = db.query(sql,
						KV.from(tcfg.getLastUpdateFieldName(),
								DateUtils.addMinutes(tws.getLastSyncDatetime(), -30))); 
				cnt+= records.size();
				mapRecords.put(tcfg.getTableName().toLowerCase(), records);
			}
			if(cnt ==0) // 没有需要上传的数据，任务完成
			{
				twsu.workFinish(msg);
				return null;
			}
		}catch(Exception ex)
		{
			twsu.workError(tc);
			throw ex;
		}
		
		DataUploadRequestBody body = new DataUploadRequestBody();
		body.setRecordSet(mapRecords);
		
		msg.setBody(body);
		
		return msg;
	}
	private TxpMessage getDataDownloadTaskRequest(DataDownloadTaskConfig tc) throws IOException, SQLException
	{
		DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		DbUtils db = new DbUtils(dataSource);
		
		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		TaskWorkStatus tws =  twsu.getStatus(tc);
		if(tws == null)
			tws = twsu.workRefresh(tc,true);
		else
			twsu.workStart(tc);
		
		TxpMessage msg = new TxpMessage();
		msg.setTaskName(tc.getName());
		msg.setFromId(config.getSiteId());
		msg.setCatalog(tc.getCatalog());
		msg.setKind(MessageBodyKind.DataDownload);
		msg.setDataSyncVersion(tws.getLastSyncVersion());
		msg.setDataSyncDatetime(tws.getLastSyncDatetime() );

		return msg;
	}
	public TxpMessage getDbSyncRequest(SyncTaskConfig stconfig) throws IOException, SQLException
	{
		DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		DbUtils db = new DbUtils(dataSource);
		String idempotentVal="";
		if(StringUtils.hasText(stconfig.getIdempotentExpression()))
			idempotentVal = GroovyScriptHelper.eval(stconfig.getIdempotentExpression()).toString();
		
		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		TaskWorkStatus tws =  twsu.getStatus(stconfig);
		if(tws == null)
			tws = twsu.workRefresh(stconfig,true);
		else
			twsu.workStart(stconfig);
		
		RecordSet mapRecords = new RecordSet(); 
		
		try{		
			if(CollectionUtils.isEmpty(stconfig.getRelations())) // 无关联的表
			{
				Map<String,String> sqlMap = TaskSqlBuilder.buildLoadCompareDataSql(stconfig);
				
				sqlMap.forEach((table,sql)->{
					List<Map<String,Object>> records =  db.query(sql);
					//if(records.size() >0 )
						mapRecords.put(table, records);	
				});
				
			}else{ // 有关联的表
			}
		}catch(Exception ex)
		{
			twsu.workError(stconfig);
			throw ex;
		}
		
		TxpMessage msg = new TxpMessage();
		msg.setFromId(config.getSiteId());
		msg.setCatalog(stconfig.getCatalog());
		msg.setTaskName(stconfig.getName());
		msg.setDataSyncDatetime(tws.getLastChangeDatetime());
		msg.setDataSyncVersion(tws.getLastChangeVersion());		
		msg.setKind(MessageBodyKind.TablesSyncCompare);	
		msg.setIdempotentValue(idempotentVal);
		
		TablesSyncBody body = new TablesSyncBody();
		body.setData(mapRecords);
		
		msg.setBody(body);
		
		return msg;	
		
	}
	
	public TxpMessage getFileDownloadRequest(FileDownloadTaskConfig fsconfig) throws IOException, SQLException
	{
		DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		DbUtils db = new DbUtils(dataSource);
		String idempotentVal="";
		if(StringUtils.hasText(fsconfig.getIdempotentExpression()))
			idempotentVal = GroovyScriptHelper.eval(fsconfig.getIdempotentExpression()).toString();
		
		String script = fsconfig.getFindFilenamesExpression();
		List<String> fileNames = (List<String>) GroovyScriptHelper.eval(script,KV.from("db",db));
		
		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		TaskWorkStatus tws =  twsu.getStatus(fsconfig);
		if(tws == null)
			tws = twsu.workRefresh(fsconfig,true);
		else
			twsu.workStart(fsconfig);
		
		Map<String,String> map = new HashMap<>();
		String basePath = StringUtils.hasText( fsconfig.getBasePath())? fsconfig.getBasePath(): config.getFileDownloadPath();
		FileDownloadRequestBody body = new FileDownloadRequestBody();
		List<FilePath> filePaths = new ArrayList<>();
		
		try{
			for(String fileName:fileNames)
			{
				if(map.get(fileName) != null) //去重
					continue;
				
				File file = new File(basePath + "/" + fileName);
				if(!file.exists()){
					FilePath fp = new FilePath();
					int idx = fileName.lastIndexOf("/");
					if(idx == -1){
						fp.setFileName(fileName);
						
					}else{
						String sfn = fileName.substring(idx+1);
						String spath = fileName.substring(0,idx);
						fp.setFileName(sfn);
						fp.setRelativePath(spath);
					}
					filePaths.add(fp);
					map.put(fileName, fileName);
				}
			}
			if(filePaths.size() == 0)
			{
				twsu.workFinish(tws);
				return null;
			}
			
			body.setFilePaths(filePaths);
		}catch(Exception ex)
		{
			twsu.workError(fsconfig);
			throw ex;
		}
		
		TxpMessage message = new TxpMessage();
		message.setFromId(config.getSiteId());
		message.setCatalog(fsconfig.getCatalog());
		message.setBody(body);
		message.setDataSyncDatetime(tws.getLastChangeDatetime());
		message.setDataSyncVersion(tws.getLastChangeVersion());
		message.setTaskName(fsconfig.getName());
		message.setKind(MessageBodyKind.FileDownloadRequest ); 		
		message.setIdempotentValue(idempotentVal);
		
		return message;
	}
	
	public TxpMessage getFileUploadRequest(FileUploadTaskConfig fsconfig) throws IOException, SQLException
	{
		DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		DbUtils db = new DbUtils(dataSource);
		String idempotentVal="";
		if(StringUtils.hasText(fsconfig.getIdempotentExpression()))
			idempotentVal = GroovyScriptHelper.eval(fsconfig.getIdempotentExpression()).toString();
		
		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		TaskWorkStatus tws =  twsu.getStatus(fsconfig);
		if(tws == null)
			tws = twsu.workRefresh(fsconfig,true);
		else
			twsu.workStart(fsconfig);
		String basePath = StringUtils.hasText( fsconfig.getBasePath())? fsconfig.getBasePath(): config.getFileDownloadPath();
		List<FilePathData> fpdataItems = new ArrayList<>();
		
		Map<String,String> map = new HashMap<>();
		int dataLength = 0;
		try{
			String script = fsconfig.getFindFilenamesExpression();
			List<String> fileNames = (List<String>) GroovyScriptHelper.eval(script,KV.from("db",db,"last_update_datetime",tws.getLastSyncDatetime()));
			for(String fileName:fileNames)
			{
				File file = new File(basePath + "/" + fileName);
				byte[] filedata = null;
				if(file.exists())
				{
					if(map.get(fileName) != null) //去重
						continue;
					
					FilePath fp = new FilePath();
					int idx = fileName.lastIndexOf("/");
					if(idx == -1){
						fp.setFileName(fileName);
						
					}else{
						String sfn = fileName.substring(idx+1);
						String spath = fileName.substring(0,idx);
						fp.setFileName(sfn);
						fp.setRelativePath(spath);
					}
					
					filedata = FileUtils.readFileToByteArray(file);
					FilePathData fpdata = new FilePathData();
					fpdata.setData(filedata);
					fpdata.setFileName(fp.getFileName());
					fpdata.setRelativePath(fp.getRelativePath());
					fpdataItems.add(fpdata);
					map.put(fileName, fileName);
					
					dataLength += filedata.length;
				}
			}
		}catch(Exception ex)
		{
			twsu.workError(fsconfig);
			throw ex;
		}
		
		
		FileUploadRequestBody body = new FileUploadRequestBody();
		body.setFilePathDataItems(fpdataItems);
		
		
		TxpMessage msg = new TxpMessage();
		msg.setTaskName(fsconfig.getName());
		msg.setFromId(config.getSiteId());
		msg.setCatalog(fsconfig.getCatalog());
		msg.setKind(MessageBodyKind.FileUploadRequest);
		msg.setDataSyncVersion(tws.getLastChangeVersion());
		msg.setDataSyncDatetime(tws.getLastChangeDatetime());
		
		if(dataLength == 0){
			twsu.workFinish(msg);
			return null;
		}		
		msg.setBody(body);
		
		return msg;
	}
	
	private TxpMessage getSiteFileCompareRequest(SiteFileCompareConfig tc) throws Exception
	{
		FileCompareBody body = new FileCompareBody();
		for(FileCompareConfig cfg: tc.getConfigs())
		{
			DirCompareInfo dciTarget= FileCompareUtil.createDirCompareInfo(cfg);
			
			DirCompareRequest request = new DirCompareRequest();
			request.setKey(cfg.getKey());
			request.setCompareInfo(dciTarget);
			body.getCompareRequests().add(request);
		}
		TxpMessage msg = new TxpMessage();
		
		msg.setIdempotentValue(formatter.format(new Date()));
		msg.setTaskName(tc.getName());
		msg.setFromId(config.getSiteId());
		msg.setCatalog(SiteFileCompareConfig.catalog_code );
		msg.setKind(MessageBodyKind.FileCompareRequest );
		msg.setDirectCmd(tc.isDirectCmd());
		msg.setBody(body);
		return msg;
	}
	
	/**
	 * 处理从中心返回的数据
	 * @param message
	 * @return
	 */
	public TxpProcResult doTxpTaskDataProc(TxpMessage message) 
	{
		try{
			if(message.isHasError())
			{
				TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
				TaskWorkStatus status = twsu.getStatus(message.getTaskName(), message.getCatalog());
				if(status != null)
					twsu.workError(status);
				throw new TxpException(ErrorMessager.fromTxpMessage(message));
			}
			
			switch(message.getKind())
			{
			case NotifyMessage:
				return doNotifyMessage(message);
			case TablesSyncCompareResult:
				return doTablesSyncCompareResult(message);
			case DataUploadResult:
				return doUploadResult(message);
			case DataDownloadResult:
				return doDownloadResult(message);
			case FileDownloadResult:
				return doFileDownload(message);
			case FileUploadResult:
				return doUploadResult(message);
			case FileCompareResponse:
				return doSiteFileCompareResult(message);
			default:
				break;
			}
		}catch(Exception ex)
		{
			TxpProcResult result = new TxpProcResult();
			result.setTaskName(message.getTaskName());
			result.setCode(-1);
			String error = ErrorMessager.fromException(ex);
			result.setErrorMessage(error);
			logger.error("任务{}:{}",message.getTaskName(),error);
			return result;
		}
		return null;
	}
	private TxpProcResult doNotifyMessage(TxpMessage message) throws Exception 
	{
		TxpProcResult result = new TxpProcResult();
		
		if(StringUtils.hasText(message.getScript()))
		{			
			result = doScriptExecute(message,message.getScript(),null);				
		}else{
			result.setEagerMethod("notify");
			result.setCatalog(message.getCatalog());
			result.setTaskName(message.getTaskName());
			result.setHasMore(true); // 为了启动对应的数据传输任务 
			if(message.getCatalog().equals(SiteFileCompareConfig.catalog_code))
			{
				result.setEagerMethod(message.getCatalog());
			}
		}
		return result;
	}
	private TxpProcResult doTablesSyncCompareResult(TxpMessage message) throws Exception
	{
		DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		
		TablesSyncResultBody resultBody = (TablesSyncResultBody) message.getBody();
		
		SyncTaskConfig taskConfig =  TxpClientConfig.cfg.getSyncTaskConfig( message.getTaskName());
		if(taskConfig == null)
			return null;
		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		DbUtils db = new DbUtils(dataSource);
		DbMetaInfo dbMetaInfo = DbSources.getCurrent().getDbMetaInfo(dataSource);
		int rowcnt = 0;
		try{
			db.beginTransaction();
			for(SyncDataSet sds: resultBody.getData())
			{
				String tableName = sds.getTableName();
				TableMeta tm = dbMetaInfo.get(tableName);
				TableConfig tableConfig =  taskConfig.getTableConfig(tableName);
				
				String expression = tableConfig.getDeleteExpression();
				List<Map<String,Object>> rows = sds.getDeleteList();
				if(!CollectionUtils.isEmpty(rows))
				{
					rowcnt += rows.size();
					if(StringUtils.hasText(expression))
					{
						GroovyScriptHelper.eval(expression, KV.from("db",db,"rows",rows));
					}else{
						String sql = TaskSqlBuilder.buildDeleteSql(taskConfig, tableConfig);
						db.updateList(sql, rows);
					}
				}
				
				expression = tableConfig.getInsertExpression();
				rows = sds.getInsertList();
				if(!CollectionUtils.isEmpty(rows))
				{
					rowcnt += rows.size();
					if(StringUtils.hasText(expression))
					{
						GroovyScriptHelper.eval(expression, KV.from("db",db,"rows",rows));
					}else{
						String sql = TaskSqlBuilder.buildDefaultInsertSql(tm);
						db.updateList(sql, rows);
					}
				}
				
				expression = tableConfig.getUpdateExpression();
				rows = sds.getUpdateList();
				if(!CollectionUtils.isEmpty(rows))
				{
					rowcnt += rows.size();
					if(StringUtils.hasText(expression))
					{
						GroovyScriptHelper.eval(expression, KV.from("db",db,"rows",rows));
					}else{
						String sql = TaskSqlBuilder.buildDefaultUpdateSql(tm, tableConfig.getPkNames(),true);
						db.updateList(sql, rows);
					}
				}
			}
			db.commit();
		}catch(Exception ex)
		{
			db.rollback();
			twsu.workError(taskConfig);
			logger.error("任务名:{},error:{}",taskConfig.getName(),ex.getMessage());
			throw ex;
		}		
		
		twsu.workFinish(message);
		
		if(rowcnt >0){
			TxpProcResult result = new TxpProcResult();
			result.setCatalog(SyncTaskConfig.catalog_code);
			result.setTaskName(message.getTaskName());
			result.setHasMore(true);//多干 一次 ，直到没有数据可共步为止。
			return result;
		}
		return null;
	}
	
	private TxpProcResult doFileDownload(TxpMessage message) throws IOException
	{
		TxpProcResult result = new TxpProcResult();
		result.setCatalog(message.getCatalog());
		result.setTaskName(message.getTaskName());
		result.setHasMore(message.isHasMoreData());
		
		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		
		try{

			FileDownloadResponseBody fileDownloadBody = (FileDownloadResponseBody) message.getBody();
			
			for(FilePathData fpd:fileDownloadBody.getFilePathDataItems())
			{
				String fileName = TxpClientConfig.cfg.getFileDownloadPath() + "/" + fpd.getRelativePath()+ "/" + fpd.getFileName();
				File file = new File(fileName);
				logger.info("fileName:{} , size:{}", fileName,fpd.getData().length);
				FileUtils.writeByteArrayToFile(file, fpd.getData());
			}
		
		}catch(Exception ex){
			twsu.workError(message);
			logger.error("任务名:{},error:{}",message.getTaskName(),ex.getMessage());
			throw ex;
		}
		
		twsu.workFinish(message);
		return result;
	}
	
	private TxpProcResult doUploadResult(TxpMessage message) throws IOException
	{
		TxpProcResult result = new TxpProcResult();
		result.setEagerMethod(message.getCatalog());		
		result.setCatalog(message.getCatalog());
		result.setTaskName(message.getTaskName());
		result.setHasMore(true); // 强制进行一次任务调用，以便激活中断的上传的任务
		
		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		
		twsu.workFinish(message);
		
		return result;
	}
	
	private TxpProcResult doDownloadResult(TxpMessage message) throws Exception
	{
		if(!message.getKind().equals(MessageBodyKind.DataDownloadResult))
			return null;
		
		DataDownloadTaskConfig taskConfig = TxpClientConfig.cfg.getDataDownloadTaskConfig(message.getTaskName());
		if(taskConfig == null)
			return null;
		
		TaskWorkStatusUtils twsu = SubsiteRuntime.current().getTaskWorkStatusUtils();
		TaskWorkStatus tws =  twsu.getStatus(taskConfig);
		
		DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
		DbUtils db = new DbUtils(dataSource);
		
		try {
			db.beginTransaction();
			int cnt = DataDownloadHandler.saveDownloadedData(db, taskConfig, message);			
			db.commit();
			twsu.workFinish(message);	
			if(cnt > 0 && taskConfig.getTrigger() != null) // 条件满足，则触发任务 
			{
				TxpProcResult result = new TxpProcResult(); //
				result.setCatalog(taskConfig.getTrigger().getCatalog());
				result.setTaskName(taskConfig.getTrigger().getTaskName());
				result.setHasMore(true);  // 启动调用
				return result;
			}
		} catch (Exception ex)
		{
			db.rollback();
			logger.error("任务名:{},error:{}",taskConfig.getName(),ex.getMessage());
			twsu.workError(message);
			
			throw ex;
		}
		
		return null;
	}
	private TxpProcResult doScriptExecute(TxpMessage message,String script,Map<String,Object> params) throws Exception
	{		
		
		TxpProcResult tpresult = null;
		if(StringUtils.hasText(script))
		{
			TxpScript tscript = TxpScript.parse(script);
			if(tscript == null)
				return null;
			DataSource dataSource = DbSources.getCurrent().getDbSource(defaultDataSourceName);
			DbUtils db = new DbUtils(dataSource);
			ScriptContext ctx = new ScriptContext();
			ctx.getParams().put("db", db);
			ctx.getParams().put("txpClientConfig", this.config);
			if(params != null && params.size() >0)
				ctx.getParams().putAll(params);
			Object result = TxpScriptExecutor.execute(ctx, tscript,message.getBody());
			
			tpresult = new TxpProcResult();
			tpresult.setEagerMethod(message.getCatalog());
			tpresult.setCatalog(message.getCatalog());
			tpresult.setTaskName(message.getTaskName());
			tpresult.setEagerNextUri(config.getReportUri() );
			TxpMessage msg = new TxpMessage();
			msg.setIdempotentValue(message.getIdempotentValue());////对应命令ID
			msg.setCatalog(message.getCatalog());
			msg.setTaskName(message.getTaskName());
			msg.setFromId(config.getSiteId());
			msg.setDirectCmd(true);
			msg.setBody(result);
			tpresult.setTxpMessage(msg);
		}
		return tpresult;
	}
	private TxpProcResult doSiteFileCompareResult(TxpMessage message) throws Exception
	{
		if(!message.getKind().equals(MessageBodyKind.FileCompareResponse))
			return null;
		FileCompareResponse response = (FileCompareResponse) message.getBody();
		SiteFileCompareConfig sfcConfig = this.config.getSiteFileCompareConfig(message.getTaskName());
		if(sfcConfig == null)
			return null;
		for(FileCompareRespItem item : response.getResults())
		{
			FileCompareConfig fcc =  sfcConfig.find(item.getKey());
			if(fcc == null)
				continue;
			FileCompareUtil.merge(fcc,  item.getResult());
		}
		//上级的script 优先
		String script = StringUtils.hasText( response.getScript())?
				response.getScript():sfcConfig.getScript();
		
		TxpProcResult tpresult = null;
		if(StringUtils.hasText(script))
		{
			tpresult = doScriptExecute(message,script,KV.from("sfcConfig",sfcConfig));			
		}
		return tpresult;
	}
	
	public TxpProcResult doDirectCmd(TxpMessage message)
	{
		return null;
	}
	
	public TxpClientConfig getConfig() {
		return config;
	}
	public void setConfig(TxpClientConfig config) {
		this.config = config;
	}
	
	public String getDefaultDataSourceName() {
		return defaultDataSourceName;
	}
	public void setDefaultDataSourceName(String defaultDataSourceName) {
		this.defaultDataSourceName = defaultDataSourceName;
	}
	public CustomGroovyShellFactory getGroovyShellFactory() {
		return groovyShellFactory;
	}
	public void setGroovyShellFactory(CustomGroovyShellFactory groovyShellFactory) {
		this.groovyShellFactory = groovyShellFactory;
	}	

}
