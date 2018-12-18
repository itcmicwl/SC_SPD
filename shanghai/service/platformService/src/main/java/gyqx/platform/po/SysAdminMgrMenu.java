
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
 * 管理员可管理功能菜单定义表
*/
@Table(name="sys_admin_mgr_menu")
public class SysAdminMgrMenu implements IBean
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
	 * 管理员ID
	*/
	@Size(max=36)
	@JoinColumn(name="admin_id",table="sys_user",referencedColumnName="id")
	@Title("管理员ID")
	private String adminId ;
	/**
	 * 项目ID
	*/
	@Size(max=36)
	@JoinColumn(name="project_id",table="sys_project",referencedColumnName="id")
	@Title("项目ID")
	private String projectId ;
	/**
	 * 菜单编码
	 * sys_project_menu的id 的列表(叶结点)
	*/
	@Size(max=2000)
	@Column(name="menu_ids")
	@Title("菜单编码")
	private String menuIds ;
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
	public String getAdminId ()
	{
		return adminId ;
	}
	
	public void setAdminId (String adminId )
	{
		this.adminId = adminId;
	}
	public String getProjectId ()
	{
		return projectId ;
	}
	
	public void setProjectId (String projectId )
	{
		this.projectId = projectId;
	}
	public String getMenuIds ()
	{
		return menuIds ;
	}
	
	public void setMenuIds (String menuIds )
	{
		this.menuIds = menuIds;
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
		return "SysAdminMgrMenu [" +" id=" +  id   +", adminId=" +  adminId   +", projectId=" +  projectId   +", menuIds=" +  menuIds   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}