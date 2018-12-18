
package gyqx.platform.permission.vo;

import java.util.*;
import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import common.db.meta.Title;

public class AdminRoleConfig 
{
	/**
	 * ID
	*/
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 角色名称
	*/
	@Column(name="cname")
	@Title("角色名称")
	private String cname ;
	/**
	 * 角色分类
	 * 
	 * [01 系统全局管理员角色,02 系统全局管理员角色 , 11 单位管理员角色, 12 用户角色 ]
	*/
	@NotEmpty
	@Size(max=2)
	@Column(name="kind")
	@Title("角色分类")
	private String kind ;
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	/**
	 * 描述
	*/
	@Column(name="remark")
	@Title("描述")
	private String remark ;
	/**
	 * mt
	*/
	@Column(name="mt")
	@Title("mt")
	private Date mt;

	public Date getMt() {
		return mt;
	}

	public void setMt(Date mt) {
		this.mt = mt;
	}
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
	public String getCname ()
	{
		return cname ;
	}
	
	public void setCname (String cname )
	{
		this.cname = cname;
	}
	public String getRemark ()
	{
		return remark ;
	}
	
	public void setRemark (String remark )
	{
		this.remark = remark;
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
		return "AdminRoleConfig ["  +" id=" +  id    +", cname=" +  cname    +", remark=" +  remark    +", mt=" +  mt     +", flag=" +  flag   
			 + "]";
	}

}