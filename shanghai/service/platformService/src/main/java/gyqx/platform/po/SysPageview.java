
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
 * 页面定义表
*/
@Table(name="sys_pageview")
public class SysPageview implements IBean
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
	 * 菜单ID
	*/
	@Size(max=36)
	@JoinColumn(name="menu_id",table="sys_project_menu",referencedColumnName="id")
	@Title("菜单ID")
	private String menuId ;
	/**
	 * 名称
	*/
	@Size(max=36)
	@Column(name="ename")
	@Title("名称")
	private String ename ;
	/**
	 * 页面加载所需的URLs
	 * sys_module_function_design.id 的多个id，用豆号分隔。
	*/
	@Size(max=512)
	@Column(name="func_ids")
	@Title("页面加载所需的URLs")
	private String funcIds ;
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
	public String getMenuId ()
	{
		return menuId ;
	}
	
	public void setMenuId (String menuId )
	{
		this.menuId = menuId;
	}
	public String getEname ()
	{
		return ename ;
	}
	
	public void setEname (String ename )
	{
		this.ename = ename;
	}
	public String getFuncIds ()
	{
		return funcIds ;
	}
	
	public void setFuncIds (String funcIds )
	{
		this.funcIds = funcIds;
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
		return "SysPageview [" +" id=" +  id   +", menuId=" +  menuId   +", ename=" +  ename   +", funcIds=" +  funcIds   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}