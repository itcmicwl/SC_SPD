
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
 * 消息确认表
*/
@Table(name="sys_message_confirm")
public class SysMessageConfirm implements IBean
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
	 * 消息ID
	*/
	@NotNull
	@Size(max=36)
	@JoinColumn(name="message_id",table="sys_message",referencedColumnName="id")
	@Title("消息ID")
	private String messageId ;
	/**
	 * 读取信息的用户ID
	*/
	@Size(max=36)
	@JoinColumn(name="user_id",table="sys_user",referencedColumnName="id")
	@Title("读取信息的用户ID")
	private String userId ;
	/**
	 * 最后更新时间
	*/
	@Column(name="last_update_datetime")
	@Title("最后更新时间")
	private java.util.Date lastUpdateDatetime ;
	/**
	 * 读消息的时间
	*/
	@Column(name="read_datetime")
	@Title("读消息的时间")
	private java.util.Date readDatetime ;
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
	public String getMessageId ()
	{
		return messageId ;
	}
	
	public void setMessageId (String messageId )
	{
		this.messageId = messageId;
	}
	public String getUserId ()
	{
		return userId ;
	}
	
	public void setUserId (String userId )
	{
		this.userId = userId;
	}
	public java.util.Date getLastUpdateDatetime ()
	{
		return lastUpdateDatetime ;
	}
	
	public void setLastUpdateDatetime (java.util.Date lastUpdateDatetime )
	{
		this.lastUpdateDatetime = lastUpdateDatetime;
	}
	public java.util.Date getReadDatetime ()
	{
		return readDatetime ;
	}
	
	public void setReadDatetime (java.util.Date readDatetime )
	{
		this.readDatetime = readDatetime;
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
		return "SysMessageConfirm [" +" id=" +  id   +", messageId=" +  messageId   +", userId=" +  userId   +", lastUpdateDatetime=" +  lastUpdateDatetime   +", readDatetime=" +  readDatetime   +", version=" +  version   
			 + "]";
	}

}