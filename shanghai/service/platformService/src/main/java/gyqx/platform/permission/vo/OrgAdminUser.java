
package gyqx.platform.permission.vo;

import java.util.*;
import javax.persistence.Column;
import common.db.meta.Title;

public class OrgAdminUser 
{
	/**
	 * 用户ID
	 * (guid)
	*/
	@Column(name="id")
	@Title("用户ID")
	private String id ;
	/**
	 * 用户编码
	*/
	@Column(name="user_code")
	@Title("用户编码")
	private String userCode ;
	/**
	 * 用户别名
	*/
	@Column(name="ename")
	@Title("用户别名")
	private String ename ;
	/**
	 * 职务
	*/
	@Column(name="job")
	@Title("职务")
	private String job ;
	/**
	 * 机构别名
	*/
	@Column(name="orgName")
	@Title("机构别名")
	private String orgName ;
	/**
	 * flag
	*/
	@Column(name="flag")
	@Title("flag")
	private String flag ;

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
	public String getEname ()
	{
		return ename ;
	}
	
	public void setEname (String ename )
	{
		this.ename = ename;
	}
	public String getJob ()
	{
		return job ;
	}
	
	public void setJob (String job )
	{
		this.job = job;
	}
	public String getOrgName ()
	{
		return orgName ;
	}
	
	public void setOrgName (String orgName )
	{
		this.orgName = orgName;
	}
	public String getFlag ()
	{
		return flag ;
	}
	
	public void setFlag (String flag )
	{
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "OrgAdminUser ["  +" id=" +  id    +", userCode=" +  userCode    +", ename=" +  ename    +", job=" +  job    +", orgName=" +  orgName    +", flag=" +  flag   
			 + "]";
	}

}