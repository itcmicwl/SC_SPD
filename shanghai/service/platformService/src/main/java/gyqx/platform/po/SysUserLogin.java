
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
 * 用户登录表
*/
@Table(name="sys_user_login")
public class SysUserLogin implements IBean
{
	/**
	 * ID
	 * (guid)
	*/
	@Id
	@Size(max=36)
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 用户ID
	*/
	@Size(max=36)
	@JoinColumn(name="uxid",table="sys_user",referencedColumnName="id")
	@Title("用户ID")
	private String uxid ;
	/**
	 * 用户登录码
	*/
	@Size(max=36)
	@Column(name="user_login_code")
	@Title("用户登录码")
	private String userLoginCode ;
	/**
	 * 总登入次数
	*/
	@Column(name="login_count")
	@Title("总登入次数")
	private int loginCount ;
	/**
	 * 连续错误次数
	*/
	@Column(name="error_count")
	@Title("连续错误次数")
	private int errorCount ;
	/**
	 * 最后登入时间
	*/
	@Column(name="last_login_datetime")
	@Title("最后登入时间")
	private java.util.Date lastLoginDatetime ;
	/**
	 * 最后错误时间
	*/
	@Column(name="last_error_datetime")
	@Title("最后错误时间")
	private java.util.Date lastErrorDatetime ;
	/**
	 * 登录标志
	 * 
	 * [0 未登录,1 已登录]
	*/
	@Size(max=1)
	@Column(name="flag")
	@Title("登录标志")
	private String flag ;
	/**
	 * hash值
	 * hash(hash(密码+web_salt)+salt)
	*/
	@Size(max=128)
	@Column(name="hash")
	@Title("hash值")
	private String hash ;
	/**
	 * 盐值
	*/
	@Size(max=128)
	@Column(name="web_salt")
	@Title("盐值")
	private String webSalt ;
	/**
	 * 盐值
	*/
	@Size(max=128)
	@Column(name="salt")
	@Title("盐值")
	private String salt ;
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
	public String getUxid ()
	{
		return uxid ;
	}
	
	public void setUxid (String uxid )
	{
		this.uxid = uxid;
	}
	public String getUserLoginCode ()
	{
		return userLoginCode ;
	}
	
	public void setUserLoginCode (String userLoginCode )
	{
		this.userLoginCode = userLoginCode;
	}
	public int getLoginCount ()
	{
		return loginCount ;
	}
	
	public void setLoginCount (int loginCount )
	{
		this.loginCount = loginCount;
	}
	public int getErrorCount ()
	{
		return errorCount ;
	}
	
	public void setErrorCount (int errorCount )
	{
		this.errorCount = errorCount;
	}
	public java.util.Date getLastLoginDatetime ()
	{
		return lastLoginDatetime ;
	}
	
	public void setLastLoginDatetime (java.util.Date lastLoginDatetime )
	{
		this.lastLoginDatetime = lastLoginDatetime;
	}
	public java.util.Date getLastErrorDatetime ()
	{
		return lastErrorDatetime ;
	}
	
	public void setLastErrorDatetime (java.util.Date lastErrorDatetime )
	{
		this.lastErrorDatetime = lastErrorDatetime;
	}
	public String getFlag ()
	{
		return flag ;
	}
	
	public void setFlag (String flag )
	{
		this.flag = flag;
	}
	public String getHash ()
	{
		return hash ;
	}
	
	public void setHash (String hash )
	{
		this.hash = hash;
	}
	public String getWebSalt ()
	{
		return webSalt ;
	}
	
	public void setWebSalt (String webSalt )
	{
		this.webSalt = webSalt;
	}
	public String getSalt ()
	{
		return salt ;
	}
	
	public void setSalt (String salt )
	{
		this.salt = salt;
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

	public String getUid ()
	{
		return uxid ;
	}
	
	public void setUid (String uxid )
	{
		this.uxid = uxid;
	}

	@Override
	public String toString() {
		return "SysUserLogin [" +" id=" +  id   +", uxid=" +  uxid   +", userLoginCode=" +  userLoginCode   +", loginCount=" +  loginCount   +", errorCount=" +  errorCount   +", lastLoginDatetime=" +  lastLoginDatetime   +", lastErrorDatetime=" +  lastErrorDatetime   +", flag=" +  flag   +", hash=" +  hash   +", webSalt=" +  webSalt   +", salt=" +  salt   +", lastUpdateDatetime=" +  lastUpdateDatetime   +", version=" +  version   
			 + "]";
	}

}