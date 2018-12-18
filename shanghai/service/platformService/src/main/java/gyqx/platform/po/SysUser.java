
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
 * 用户表
*/
@Table(name="sys_user")
public class SysUser implements IBean
{
	/**
	 * 用户ID
	 * (guid)
	*/
	@Id
	@Size(max=36)
	@Column(name="id")
	@Title("用户ID")
	private String id ;
	/**
	 * 用户编码
	*/
	@Size(max=36)
	@Column(name="user_code")
	@Title("用户编码")
	private String userCode ;
	/**
	 * 操作员类型
	 * 
	 * [0 普通用户,1 管理员,2 配置员]
	*/
	@Size(max=1)
	@Column(name="kind")
	@Title("操作员类型")
	private String kind ;
	/**
	 * 创建时间
	*/
	@Column(name="create_datetime")
	@Title("创建时间")
	private java.util.Date createDatetime ;
	/**
	 * 创建人ID
	*/
	@Size(max=36)
	@Column(name="create_user_id")
	@Title("创建人ID")
	private String createUserId ;
	/**
	 * 最后更新时间
	*/
	@Column(name="last_update_datetime")
	@Title("最后更新时间")
	private java.util.Date lastUpdateDatetime ;
	/**
	 * 数据版本
	*/
	@Column(name="version")
	@Title("数据版本")
	private int version ;

	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getUserCode ()
	{
		return userCode ;
	}
	
	public void setUserCode (String userCode )
	{
		this.userCode = userCode;
	}
	public String getKind ()
	{
		return kind ;
	}
	
	public void setKind (String kind )
	{
		this.kind = kind;
	}
	public java.util.Date getCreateDatetime ()
	{
		return createDatetime ;
	}
	
	public void setCreateDatetime (java.util.Date createDatetime )
	{
		this.createDatetime = createDatetime;
	}
	public String getCreateUserId ()
	{
		return createUserId ;
	}
	
	public void setCreateUserId (String createUserId )
	{
		this.createUserId = createUserId;
	}
	public java.util.Date getLastUpdateDatetime ()
	{
		return lastUpdateDatetime ;
	}
	
	public void setLastUpdateDatetime (java.util.Date lastUpdateDatetime )
	{
		this.lastUpdateDatetime = lastUpdateDatetime;
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
		return "SysUser [" +" id=" +  id   +", userCode=" +  userCode   +", kind=" +  kind   +", createDatetime=" +  createDatetime   +", createUserId=" +  createUserId   +", lastUpdateDatetime=" +  lastUpdateDatetime   +", version=" +  version   
			 + "]";
	}

}