
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
 * 角色功能权限定义表
*/
@Table(name="sys_role_menu")
public class SysRoleMenu implements IBean
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
	 * 角色ID
	*/
	@Size(max=36)
	@JoinColumn(name="role_id",table="sys_role",referencedColumnName="id")
	@Title("角色ID")
	private String roleId ;
	/**
	 * 菜单编码
	 * sys_project_menu的id (叶结点)
	*/
	@Size(max=36)
	@Column(name="menu_id")
	@Title("菜单编码")
	private String menuId ;
	/**
	 * 按钮编码列表
	 * 菜单相关的页面按钮的权限列表，内容为sys_pageview_element的按钮ID列表，多条用分号分隔。如：001.001;001.002
	*/
	@Size(max=512)
	@JoinColumn(name="button_codes",table="sys_project_menu",referencedColumnName="id")
	@Title("按钮编码列表")
	private String buttonCodes ;
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
	public String getRoleId ()
	{
		return roleId ;
	}
	
	public void setRoleId (String roleId )
	{
		this.roleId = roleId;
	}
	public String getMenuId ()
	{
		return menuId ;
	}
	
	public void setMenuId (String menuId )
	{
		this.menuId = menuId;
	}
	public String getButtonCodes ()
	{
		return buttonCodes ;
	}
	
	public void setButtonCodes (String buttonCodes )
	{
		this.buttonCodes = buttonCodes;
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
		return "SysRoleMenu [" +" id=" +  id   +", roleId=" +  roleId   +", menuId=" +  menuId   +", buttonCodes=" +  buttonCodes   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}