package gyqx.txp.runtime.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import gyqx.txp.configuration.DataDownloadTaskConfig;
import gyqx.txp.configuration.DataUploadTaskConfig;
import gyqx.txp.configuration.FileDownloadTaskConfig;
import gyqx.txp.configuration.FileUploadTaskConfig;
import gyqx.txp.configuration.SyncTaskConfig;

public class TaskStatusCache 
{
	private ConcurrentHashMap<String,TaskWorkStatus> map = new ConcurrentHashMap<>();	
	
	public Map<String,TaskWorkStatus> getMap()
	{
		return map;
	}
	
	public TaskWorkStatus refresh(TaskWorkStatus status)
	{
		TaskWorkStatus old = get(status.getTaskName(),status.getCatalog());
		if(old == null){
			map.put(status.key(),status);
			old = status;
		}
		else{
			old.copyFrom(status);
		}
		return old;
	}
	public TaskWorkStatus get(String taskName,String catalog)
	{
		return map.get(TaskWorkStatus.createKey(taskName,catalog));
	}
	public TaskWorkStatus get(TaskWorkStatus status)
	{
		return get(status.getTaskName(),status.getCatalog());
	}
	
	public List<TaskWorkStatus> queryNeedSync()
	{
		List<TaskWorkStatus> twss = new ArrayList<>();
		map.forEach((k,v)->{
			if(v.needSync())
				twss.add(v);
		});
		return twss;
	}
	
	public List<TaskWorkStatus> queryNeedNotify()
	{
		List<TaskWorkStatus> twss = new ArrayList<>();
		map.forEach((k,v)->{
			if(v.needNotify())
				twss.add(v);
		});
		return twss;
	}
	public List<TaskWorkStatus> queryNeedUploadAndSync(){
		List<TaskWorkStatus> twss = new ArrayList<>();
		map.forEach((k,v)->{
			if((v.getCatalog().equalsIgnoreCase(SyncTaskConfig.catalog_code) || 
					v.getCatalog().equalsIgnoreCase(DataUploadTaskConfig.catalog_code) ||
					v.getCatalog().equalsIgnoreCase(FileUploadTaskConfig.catalog_code)) && v.needStart() || v.needNotify())
				twss.add(v);
		});
		return twss;
	}

	public List<TaskWorkStatus> queryNeedDownloadAndSync() {

		List<TaskWorkStatus> twss = new ArrayList<>();
		map.forEach((k,v)->{
			if((v.getCatalog().equalsIgnoreCase(SyncTaskConfig.catalog_code) || 
					v.getCatalog().equalsIgnoreCase(DataDownloadTaskConfig.catalog_code) ||
					v.getCatalog().equalsIgnoreCase(FileDownloadTaskConfig.catalog_code)) &&  v.needNotify())
				twss.add(v);
		});
		return twss;
	}
	

}
