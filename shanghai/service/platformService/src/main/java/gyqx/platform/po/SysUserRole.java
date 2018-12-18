
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
 * 用户的角色定义表
*/
@Table(name="sys_user_role")
public class SysUserRole implements IBean
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
	 * 组ID
	*/
	@Size(max=36)
	@JoinColumn(name="user_id",table="sys_user",referencedColumnName="id")
	@Title("组ID")
	private String userId ;
	/**
	 * 角色code id
	*/
	@Size(max=36)
	@JoinColumn(name="role_id",table="sys_role",referencedColumnName="id")
	@Title("角色code id")
	private String roleId ;
	/**
	 * 操作员ID
	 * 新增或修改的操作员ID
	*/
	@Size(max=36)
	@Column(name="uxid")
	@Title("操作员ID")
	private String uxid ;
	/**
	 * 修改时间
	*/
	@Column(name="mt")
	@Title("修改时间")
	private java.util.Date mt ;
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
	public String getUserId ()
	{
		return userId ;
	}
	
	public void setUserId (String userId )
	{
		this.userId = userId;
	}
	public String getRoleId ()
	{
		return roleId ;
	}
	
	public void setRoleId (String roleId )
	{
		this.roleId = roleId;
	}
	public String getUxid ()
	{
		return uxid ;
	}
	
	public void setUxid (String uxid )
	{
		this.uxid = uxid;
	}
	public java.util.Date getMt ()
	{
		return mt ;
	}
	
	public void setMt (java.util.Date mt )
	{
		this.mt = mt;
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
		return "SysUserRole [" +" id=" +  id   +", userId=" +  userId   +", roleId=" +  roleId   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}