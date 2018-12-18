package gyqx.txp.runtime.status;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;

import gyqx.txp.configuration.TaskConfig;

public class TaskWorkStatus extends StatusLogItem
{
	public static final String LOG_TAG = "task" ;
	private String taskName;
	private String catalog;
	private Date lastSyncDatetime;
	private int lastSyncVersion;
	private Date lastNotifyDatetime;
	private int lastNotifyVersion;	
	private Date notifySyncDatetime;
	private int notifySyncVersion;	
	private Date lastChangeDatetime;
	private int lastChangeVersion;
	private Date startDate;
	private Date finishDate;
	private Date errorDate;	
	private int status ;   // 0 idle; 1 start; 2 finish ; -1 error
	
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public Date getLastSyncDatetime() {
		return lastSyncDatetime;
	}
	public void setLastSyncDatetime(Date lastSyncDatetime) {
		this.lastSyncDatetime = lastSyncDatetime;
	}
	public int getLastSyncVersion() {
		return lastSyncVersion;
	}
	public void setLastSyncVersion(int lastSyncVersion) {
		this.lastSyncVersion = lastSyncVersion;
	}
	public Date getLastChangeDatetime() {
		return lastChangeDatetime;
	}
	public void setLastChangeDatetime(Date lastChangeDatetime) {
		this.lastChangeDatetime = lastChangeDatetime;
	}
	public int getLastChangeVersion() {
		return lastChangeVersion;
	}
	public void setLastChangeVersion(int lastChangeVersion) {
		this.lastChangeVersion = lastChangeVersion;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String getTag() {
		return LOG_TAG;
	}
	
	
	@Override
	public void loadJson(JsonNode jnode) {
		taskName = jnode.get("taskName").asText();
		catalog = jnode.get("catalog").asText();
		lastSyncDatetime = new Date( jnode.get("lastSyncDatetime").asLong());
		lastSyncVersion = jnode.get("lastSyncVersion").asInt();
		lastNotifyDatetime = new Date( jnode.get("lastNotifyDatetime").asLong());
		lastNotifyVersion = jnode.get("lastNotifyVersion").asInt();		
		notifySyncDatetime =  new Date( jnode.get("notifySyncDatetime").asLong());
		notifySyncVersion = jnode.get("notifySyncVersion").asInt();	
		
		startDate = new Date( jnode.get("startDate").asLong());
		finishDate = new Date( jnode.get("finishDate").asLong());
		errorDate = new Date( jnode.get("errorDate").asLong());
		
		lastChangeDatetime = new Date( jnode.get("lastChangeDatetime").asLong());
		lastChangeVersion = jnode.get("lastChangeVersion").asInt();
		status = jnode.get("status").asInt();
		
	}
	private String id;
	@Override
	public String key() {
		if(StringUtils.hasText(id))
			return id;
		synchronized (this) {
			if(StringUtils.hasText(id))
				return id;			
			id = createKey(taskName,catalog);
		}
		return id;
	}
	
	public static String createKey(String taskName,String catalog)
	{
		return taskName.toLowerCase()+ "." + catalog;
	}
	
	public void copyFrom(TaskWorkStatus status)
	{
		taskName = status.taskName;
		catalog = status.catalog;
		lastSyncDatetime = status.lastSyncDatetime;
		lastSyncVersion = status.lastSyncVersion;
		lastNotifyDatetime = status.lastNotifyDatetime;
		lastNotifyVersion = status.lastNotifyVersion;		
		lastChangeDatetime = status.lastChangeDatetime;
		lastChangeVersion = status.lastChangeVersion;
		startDate = status.startDate;
		finishDate = status.finishDate;
		errorDate = status.errorDate;
		notifySyncDatetime = status.notifySyncDatetime;
		notifySyncVersion = status.notifySyncVersion;	

		this.status = status.status;
		
	}
	public boolean needSync(){
		return (lastChangeVersion >lastSyncVersion && (status == 0 || status ==2));
	}
	public boolean needNotify(){
		return lastNotifyVersion > notifySyncVersion;
	}
	public boolean needStart(){	
		return needNotify() || needSync();
	}
	public void beginSync()
	{
		status = 1;
	}
	public void syncOk(Date lastDataSyncDatetime,int lastDataSyncVersion)
	{
		this.lastSyncVersion = lastDataSyncVersion;
		this.lastSyncDatetime = lastDataSyncDatetime;

		status = 2;
	}
	public void notifyOk(Date lastDataSyncDatetime,int lastDataSyncVersion)
	{
		this.lastNotifyVersion = lastDataSyncVersion;
		this.lastNotifyDatetime = lastDataSyncDatetime;

	}
	
	public void changeTouch()
	{

		this.lastChangeDatetime = new Date();
		this.lastChangeVersion ++;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public Date getLastNotifyDatetime() {
		return lastNotifyDatetime;
	}
	public void setLastNotifyDatetime(Date lastNotifyDatetime) {
		this.lastNotifyDatetime = lastNotifyDatetime;
	}
	public int getLastNotifyVersion() {
		return lastNotifyVersion;
	}
	public void setLastNotifyVersion(int lastNotifyVersion) {
		this.lastNotifyVersion = lastNotifyVersion;
	}
	@Override
	public void setTag(String tag) {

	}
	public Date getNotifySyncDatetime() {
		return notifySyncDatetime;
	}
	public void setNotifySyncDatetime(Date notifySyncDatetime) {
		this.notifySyncDatetime = notifySyncDatetime;
	}
	public int getNotifySyncVersion() {
		return notifySyncVersion;
	}
	public void setNotifySyncVersion(int notifySyncVersion) {
		this.notifySyncVersion = notifySyncVersion;
	}
	

	
	@Override
	public String toString() {
		return "TaskWorkStatus [taskName=" + taskName + ", catalog=" + catalog + ", lastSyncDatetime="
				+ lastSyncDatetime + ", lastSyncVersion=" + lastSyncVersion + ", lastNotifyDatetime="
				+ lastNotifyDatetime + ", lastNotifyVersion=" + lastNotifyVersion + ", notifySyncDatetime="
				+ notifySyncDatetime + ", notifySyncVersion=" + notifySyncVersion + ", lastChangeDatetime="
				+ lastChangeDatetime + ", lastChangeVersion=" + lastChangeVersion + ", startDate=" + startDate
				+ ", finishDate=" + finishDate + ", errorDate=" + errorDate + ", status=" + status + "]";
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public Date getErrorDate() {
		return errorDate;
	}
	public void setErrorDate(Date errorDate) {
		this.errorDate = errorDate;
	}
	

}
