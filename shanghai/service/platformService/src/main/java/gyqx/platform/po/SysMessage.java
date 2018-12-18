
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
 * 消息表
*/
@Table(name="sys_message")
public class SysMessage implements IBean
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
	 * 范围类型
	 * 
	 * [0 单用户,1 机构 ]
	*/
	@NotNull
	@Size(max=1)
	@Column(name="range_type")
	@Title("范围类型")
	private String rangeType ;
	/**
	 * 内容
	*/
	@NotEmpty
	@Size(max=2000)
	@Column(name="message")
	@Title("内容")
	private String message ;
	/**
	 * 消息标题
	*/
	@Size(max=36)
	@Column(name="title")
	@Title("消息标题")
	private String title ;
	/**
	 * 项目编码
	*/
	@Size(max=36)
	@Column(name="project_code")
	@Title("项目编码")
	private String projectCode ;
	/**
	 * 链接
	*/
	@Size(max=64)
	@Column(name="url")
	@Title("链接")
	private String url ;
	/**
	 * 组ID
	 * 当 range_type 为单用户时，此值为空，当range_type为机构时，此值为机构ID
	*/
	@Size(max=36)
	@Column(name="group_id")
	@Title("组ID")
	private String groupId ;
	/**
	 * 写入时间
	*/
	@Column(name="write_datetime")
	@Title("写入时间")
	private java.util.Date writeDatetime ;
	/**
	 * 写入的人员ID
	*/
	@Size(max=36)
	@Column(name="write_user_id")
	@Title("写入的人员ID")
	private String writeUserId ;
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
	public String getRangeType ()
	{
		return rangeType ;
	}
	
	public void setRangeType (String rangeType )
	{
		this.rangeType = rangeType;
	}
	public String getMessage ()
	{
		return message ;
	}
	
	public void setMessage (String message )
	{
		this.message = message;
	}
	public String getTitle ()
	{
		return title ;
	}
	
	public void setTitle (String title )
	{
		this.title = title;
	}
	public String getProjectCode ()
	{
		return projectCode ;
	}
	
	public void setProjectCode (String projectCode )
	{
		this.projectCode = projectCode;
	}
	public String getUrl ()
	{
		return url ;
	}
	
	public void setUrl (String url )
	{
		this.url = url;
	}
	public String getGroupId ()
	{
		return groupId ;
	}
	
	public void setGroupId (String groupId )
	{
		this.groupId = groupId;
	}
	public java.util.Date getWriteDatetime ()
	{
		return writeDatetime ;
	}
	
	public void setWriteDatetime (java.util.Date writeDatetime )
	{
		this.writeDatetime = writeDatetime;
	}
	public String getWriteUserId ()
	{
		return writeUserId ;
	}
	
	public void setWriteUserId (String writeUserId )
	{
		this.writeUserId = writeUserId;
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
		return "SysMessage [" +" id=" +  id   +", rangeType=" +  rangeType   +", message=" +  message   +", title=" +  title   +", projectCode=" +  projectCode   +", url=" +  url   +", groupId=" +  groupId   +", writeDatetime=" +  writeDatetime   +", writeUserId=" +  writeUserId   +", version=" +  version   
			 + "]";
	}

}