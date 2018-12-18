package gyqx.txp.runtime.status;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import gyqx.txp.configuration.TaskConfig;
import gyqx.txp.message.TaskNotifyMessage;
import gyqx.txp.message.TxpMessage;


public class TaskWorkStatusUtils 
{
	private static final Logger logger = LoggerFactory.getLogger(TaskWorkStatusUtils.class);
	private TableRecordsCache tableRecordsCache = new TableRecordsCache();
	private TaskStatusCache taskStatusCache = new TaskStatusCache();
	private LoggerRedoWorker redoWorker;

	public TaskWorkStatusUtils(String redoLogFileName) throws IOException 
	{
		redoWorker = new LoggerRedoWorker(redoLogFileName);
	}
	
	public void init() throws IOException
	{
		logger.info("start redo task status");
		redoWorker.redo(new IRedoCallback(){
			@Override
			public void redoTaskWorkStatus(TaskWorkStatus twStatus) {
				taskStatusCache.refresh(twStatus);
			}

			@Override
			public void redoTableTxpStatus(TableTxpStatus ttStatus) {
				tableRecordsCache.refresh(ttStatus);
			}

			@Override
			public void redoTaskNotifyMessage(TaskNotifyMessage ttStatus) {	
			
			}});
		logger.info("finish redo task status");
		logger.info("start truncate task status log...");
		////////////截断 日志/////////////
		redoWorker.truncate();
		
		Iterator<TaskWorkStatus> iter= taskStatusCache.getMap().values().iterator(); 		
		while(iter.hasNext())
		{
			redoWorker.writeLog(iter.next(),true);
		}

		//tableRecordsCache   目前还没实现 ，
		redoWorker.clearTemp();
		
		logger.info("truncate task status log finished. ");

		
	}
	public TaskWorkStatus getStatus(TaskConfig taskConfig)
	{
		return taskStatusCache.get(taskConfig.getName(),taskConfig.getCatalog());
	}
	public TaskWorkStatus getStatus(TaskNotifyMessage tnm)
	{
		return taskStatusCache.get(tnm.getTaskName(),tnm.getCatalog());
	}
	public TaskWorkStatus getStatus(TxpMessage msg)
	{
		return taskStatusCache.get(msg.getTaskName(),msg.getCatalog());
	}
	public TaskWorkStatus getStatus(String taskName,String catalog)
	{
		return taskStatusCache.get(taskName,catalog);
	}
	
	public List<TaskWorkStatus> queryNeedSync()
	{
		return taskStatusCache.queryNeedSync();
	}
	public List<TaskWorkStatus> queryNeedNotify()
	{
		return taskStatusCache.queryNeedNotify();
	}
	public List<TaskWorkStatus> queryNeedDownloadAndSync()
	{
		return taskStatusCache.queryNeedDownloadAndSync();
	}
	public List<TaskWorkStatus> queryNeedUploadAndSync()
	{
		return taskStatusCache.queryNeedUploadAndSync();
	}
	
	public TaskWorkStatus workStart(TaskConfig taskConfig) throws IOException
	{
		TaskWorkStatus status = getStatus(taskConfig);
		status.setStatus(1);
		status.setFinishDate(new Date());
		redoWorker.writeLog(status);
		return status;
	}
	public TaskWorkStatus workError(TaskConfig taskConfig) throws IOException
	{
		TaskWorkStatus status = getStatus(taskConfig);
		status.setStatus(-1);
		status.setErrorDate(new Date());
		redoWorker.writeLog(status);
		return status;
	}
	public TaskWorkStatus workError(TaskWorkStatus status) throws IOException
	{
		status.setStatus(-1);
		status.setErrorDate(new Date());
		redoWorker.writeLog(status);
		return status;
	}
	public TaskWorkStatus workError(TxpMessage message) throws IOException
	{
		TaskWorkStatus status = getStatus(message.getTaskName(),message.getCatalog()); //getStatus(taskConfig);
		status.setStatus(-1);
		status.setErrorDate(new Date());
		redoWorker.writeLog(status);
		return status;
	}
	public TaskWorkStatus workRefresh(TaskConfig taskConfig,boolean bStart) throws IOException
	{
		TaskWorkStatus status = getStatus(taskConfig);
		if(status == null)
		{
			status = new TaskWorkStatus();
			status.setTaskName(taskConfig.getName());
			status.setCatalog(taskConfig.getCatalog());
			status.setLastChangeDatetime(new Date());
			status.setLastChangeVersion(0);
			status.setLastSyncDatetime(new Date(0));
			status.setLastNotifyDatetime(new Date(0));
			status.setNotifySyncDatetime(new Date(0));
			status = taskStatusCache.refresh(status);  
		}
		synchronized (status) {
			status.setLastChangeDatetime(new Date());
			status.setLastChangeVersion(status.getLastChangeVersion() +1);
		}
		if(bStart){
			status.setFinishDate(new Date());
			status.setStatus(1);
		}
		redoWorker.writeLog(status);
		return status;
		
	}
	public TaskWorkStatus workRefresh(TaskNotifyMessage tnm) throws IOException
	{
		TaskWorkStatus status = getStatus(tnm);
		if(status == null)
		{
			status = new TaskWorkStatus();
			status.setTaskName(tnm.getTaskName());
			status.setCatalog(tnm.getCatalog());
			status.setLastChangeDatetime(new Date());
			status.setLastChangeVersion(0);
			status.setLastSyncDatetime(new Date(0));
			status.setLastNotifyDatetime(new Date(0));
			status.setNotifySyncDatetime(new Date(0));
			status = taskStatusCache.refresh(status);  
		}
		synchronized (status) {
			status.setLastChangeDatetime(new Date());
			status.setLastChangeVersion(status.getLastChangeVersion() +1);
		}

		redoWorker.writeLog(status);
		return status;
	}
	
	public void notifyTask(String taskName,String catalog) throws IOException
	{
		TaskWorkStatus status = getStatus(taskName,catalog);
		if(status == null)
		{
			status = new TaskWorkStatus();
			status.setTaskName(taskName);
			status.setCatalog(catalog);
			status.setLastSyncDatetime(new Date(0));
			status.setLastNotifyDatetime(new Date(0));
			status.setNotifySyncDatetime(new Date(0));
			status.setLastChangeDatetime(new Date());
			status.setLastChangeVersion(0);

			taskStatusCache.refresh(status);  
		}
		synchronized (status) {
			status.setLastNotifyDatetime(new Date());
			status.setLastNotifyVersion(status.getLastNotifyVersion() +1);

		}
		redoWorker.writeLog(status);
	}
	
	public void notifyRefresh(TaskNotifyMessage tnm) throws IOException
	{
		TaskWorkStatus status = getStatus(tnm);
		if(status == null)
		{
			status = new TaskWorkStatus();
			status.setTaskName(tnm.getTaskName());
			status.setCatalog(tnm.getCatalog());
			status.setLastSyncDatetime(new Date(0));
			status.setLastNotifyDatetime(new Date(0));
			status.setNotifySyncDatetime(new Date(0));			
			status.setLastChangeDatetime(new Date());
			status.setLastChangeVersion(0);
			taskStatusCache.refresh(status);  
		}
		synchronized (status) {
			status.setLastChangeDatetime(new Date());
			status.setLastChangeVersion(status.getLastChangeVersion() +1);
			
			status.setLastNotifyDatetime(new Date());
			status.setLastNotifyVersion(status.getLastNotifyVersion() +1);
		}
		redoWorker.writeLog(status);
	}
	
	public void workFinish(TaskConfig taskConfig,int lastSyncVersion,Date lastSyncDatetime) throws IOException
	{
		TaskWorkStatus status = getStatus(taskConfig);
		if(status == null){
			logger.error("status == null,TaskName:{},Catalog:{}",taskConfig.getName(),taskConfig.getCatalog());
			return;
		}//throw new IOException(taskConfig.getName() + ":" + taskConfig.getCatalog());
		status.setLastSyncVersion(lastSyncVersion);
		status.setLastSyncDatetime(lastSyncDatetime);
		status.setFinishDate(new Date());
		status.setStatus(2);
		redoWorker.writeLog(status);
	}
	public void workFinish(TxpMessage message) throws IOException
	{
		TaskWorkStatus status =  getStatus(message);
		if(status == null){
			logger.error("status == null,TaskName:{},Catalog:{}",message.getTaskName(),message.getCatalog());
			return;
		}//throw new IOException(message.getTaskName() + ":" + message.getCatalog());
		status.setLastSyncDatetime(message.getDataSyncDatetime());
		status.setLastSyncVersion(message.getDataSyncVersion());
		status.setFinishDate(new Date());
		status.setStatus(2);
		redoWorker.writeLog(status);
	}
	public void workFinish(TaskWorkStatus status) throws IOException
	{

		status.setLastSyncDatetime(status.getLastChangeDatetime());
		status.setLastSyncVersion(status.getLastChangeVersion());
		status.setFinishDate(new Date());
		status.setStatus(2);
		redoWorker.writeLog(status);
	}
	public void notifyFinish(String taskName,String catalog) throws IOException
	{
		TaskWorkStatus status = getStatus(taskName,catalog);
		if(status == null)
			return;
		synchronized (status) {
			if(status.getLastNotifyVersion() != status.getNotifySyncVersion())
			{
				status.setNotifySyncDatetime(status.getLastNotifyDatetime());
				status.setNotifySyncVersion(status.getLastNotifyVersion());
				redoWorker.writeLog(status);
			}
		}
		
	}
	public void notifyFinish(TaskWorkStatus status) throws IOException
	{
		synchronized (status) {
			status.setNotifySyncDatetime(status.getLastNotifyDatetime());
			status.setNotifySyncVersion(status.getLastNotifyVersion());
		}
		redoWorker.writeLog(status);
	}
	public LoggerRedoWorker getRedoWorker() {
		return redoWorker;
	}
	public void setRedoWorker(LoggerRedoWorker redoWorker) {
		this.redoWorker = redoWorker;
	}

}
