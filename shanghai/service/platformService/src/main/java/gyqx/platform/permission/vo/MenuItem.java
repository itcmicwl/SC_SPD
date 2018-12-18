
package gyqx.platform.permission.vo;

import java.util.*;
import javax.persistence.Column;
import common.db.meta.Title;

public class MenuItem 
{
	/**
	 * ID
	*/
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 菜单ID
	*/
	@Column(name="code")
	@Title("菜单ID")
	private String code ;
	/**
	 * 上级ID
	*/
	@Column(name="pcode")
	@Title("上级ID")
	private String pcode ;
	
	private String cname;
	
	/**
	 * 子系统编码
	*/
	@Column(name="project_code")
	@Title("子系统编码")
	private String projectCode ;
	/**
	 * 别名
	 * 中文列名
	*/
	@Column(name="project_name")
	@Title("别名")
	private String projectName ;

	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getCode ()
	{
		return code ;
	}
	
	public void setCode (String code )
	{
		this.code = code;
	}
	public String getPcode ()
	{
		return pcode ;
	}
	
	public void setPcode (String pcode )
	{
		this.pcode = pcode;
	}
	public String getProjectCode ()
	{
		return projectCode ;
	}
	
	public void setProjectCode (String projectCode )
	{
		this.projectCode = projectCode;
	}
	public String getProjectName ()
	{
		return projectName ;
	}
	
	public void setProjectName (String projectName )
	{
		this.projectName = projectName;
	}
	@Override
	public String toString() {
		return "MenuItem ["  +" id=" +  id    +", code=" +  code    +", pcode=" +  pcode    +", projectCode=" +  projectCode    +", projectName=" +  projectName   
			 + "]";
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}