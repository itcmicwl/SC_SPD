
package gyqx.platform.po;

import java.util.*;
import common.db.IBean;
import common.db.meta.Title;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.*;
/**
 * 同步任务传输控制表
*/
@Table(name="sys_task_sync_control")
public class SysTaskSyncControl implements IBean
{
	/**
	 * ID
	*/
	@Id
	@Size(max=36)
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 任务名
	*/
	@NotNull
	@Size(max=36)
	@Column(name="task_name")
	@Title("任务名")
	private String taskName ;
	/**
	 * 最后同步时间
	*/
	@Column(name="last_sync_datetime")
	@Title("最后同步时间")
	private java.util.Date lastSyncDatetime ;
	/**
	 * 最后同步版本
	*/
	@Column(name="last_sync_version")
	@Title("最后同步版本")
	private int lastSyncVersion ;
	/**
	 * 最后变更时间
	*/
	@Column(name="last_change_datetime")
	@Title("最后变更时间")
	private java.util.Date lastChangeDatetime ;
	/**
	 * 最后变更版本
	*/
	@Column(name="last_change_version")
	@Title("最后变更版本")
	private int lastChangeVersion ;
	/**
	 * 并发控制
	*/
	@Column(name="version")
	@Title("并发控制")
	private int version ;

	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getTaskName ()
	{
		return taskName ;
	}
	
	public void setTaskName (String taskName )
	{
		this.taskName = taskName;
	}
	public java.util.Date getLastSyncDatetime ()
	{
		return lastSyncDatetime ;
	}
	
	public void setLastSyncDatetime (java.util.Date lastSyncDatetime )
	{
		this.lastSyncDatetime = lastSyncDatetime;
	}
	public int getLastSyncVersion ()
	{
		return lastSyncVersion ;
	}
	
	public void setLastSyncVersion (int lastSyncVersion )
	{
		this.lastSyncVersion = lastSyncVersion;
	}
	public java.util.Date getLastChangeDatetime ()
	{
		return lastChangeDatetime ;
	}
	
	public void setLastChangeDatetime (java.util.Date lastChangeDatetime )
	{
		this.lastChangeDatetime = lastChangeDatetime;
	}
	public int getLastChangeVersion ()
	{
		return lastChangeVersion ;
	}
	
	public void setLastChangeVersion (int lastChangeVersion )
	{
		this.lastChangeVersion = lastChangeVersion;
	}
	public int getVersion ()
	{
		return version ;
	}
	
	public void setVersion (int version )
	{
		this.version = version;
	}


	@Override
	public String toString() {
		return "SysTaskSyncControl [" +" id=" +  id   +", taskName=" +  taskName   +", lastSyncDatetime=" +  lastSyncDatetime   +", lastSyncVersion=" +  lastSyncVersion   +", lastChangeDatetime=" +  lastChangeDatetime   +", lastChangeVersion=" +  lastChangeVersion   +", version=" +  version   
			 + "]";
	}

}