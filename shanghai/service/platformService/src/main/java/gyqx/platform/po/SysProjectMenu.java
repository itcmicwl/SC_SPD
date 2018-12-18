
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
 * 子系统菜单表
*/
@Table(name="sys_project_menu")
public class SysProjectMenu implements IBean
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
	 * 上级ID
	*/
	@NotNull
	@Size(max=36)
	@JoinColumn(name="pcode",table="sys_project_menu",referencedColumnName="code")
	@Title("上级ID")
	private String pcode ;
	/**
	 * 子系统编码
	*/
	@NotEmpty
	@Size(max=36)
	@JoinColumn(name="project_code",table="sys_project",referencedColumnName="code")
	@Title("子系统编码")
	private String projectCode ;
	/**
	 * 菜单ID
	*/
	@Size(max=36)
	@Column(name="code")
	@Title("菜单ID")
	private String code ;
	/**
	 * 排序
	*/
	@Column(name="ordinal")
	@Title("排序")
	private int ordinal ;
	/**
	 * 操作员ID
	*/
	@Size(max=36)
	@Column(name="path")
	@Title("操作员ID")
	private String path ;
	/**
	 * 资源目录
	*/
	@Size(max=128)
	@Column(name="directory")
	@Title("资源目录")
	private String directory ;
	/**
	 * 名称
	*/
	@Size(max=36)
	@Column(name="cname")
	@Title("名称")
	private String cname ;
	/**
	 * 是否隐藏
	*/
	@Size(max=1)
	@Column(name="hidden")
	@Title("是否隐藏")
	private String hidden ;
	/**
	 * 分组编码
	*/
	@Size(max=1)
	@Column(name="require_auth")
	@Title("分组编码")
	private String requireAuth ;
	/**
	 * 重定向地址
	*/
	@Size(max=128)
	@Column(name="redirect")
	@Title("重定向地址")
	private String redirect ;
	/**
	 * 图标类名
	*/
	@Size(max=36)
	@Column(name="icon_cls")
	@Title("图标类名")
	private String iconCls ;
	/**
	 * 附加元素
	 * 用json字符串表达的对象
	*/
	@Size(max=128)
	@Column(name="meta")
	@Title("附加元素")
	private String meta ;
	/**
	 * 层级
	 */
	@Column(name="menu_level")
	@Title("层级")
	private Integer menuLevel ;
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
	public String getCode ()
	{
		return code ;
	}
	
	public void setCode (String code )
	{
		this.code = code;
	}
	public int getOrdinal ()
	{
		return ordinal ;
	}
	
	public void setOrdinal (int ordinal )
	{
		this.ordinal = ordinal;
	}
	public String getPath ()
	{
		return path ;
	}
	
	public void setPath (String path )
	{
		this.path = path;
	}
	public String getDirectory ()
	{
		return directory ;
	}
	
	public void setDirectory (String directory )
	{
		this.directory = directory;
	}
	public String getCname ()
	{
		return cname ;
	}
	
	public void setCname (String cname )
	{
		this.cname = cname;
	}
	public String getHidden ()
	{
		return hidden ;
	}
	
	public void setHidden (String hidden )
	{
		this.hidden = hidden;
	}
	public String getRequireAuth ()
	{
		return requireAuth ;
	}
	
	public void setRequireAuth (String requireAuth )
	{
		this.requireAuth = requireAuth;
	}
	public String getRedirect ()
	{
		return redirect ;
	}
	
	public void setRedirect (String redirect )
	{
		this.redirect = redirect;
	}
	public String getIconCls ()
	{
		return iconCls ;
	}
	
	public void setIconCls (String iconCls )
	{
		this.iconCls = iconCls;
	}
	public String getMeta ()
	{
		return meta ;
	}
	
	public void setMeta (String meta )
	{
		this.meta = meta;
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

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    @Override
	public String toString() {
		return "SysProjectMenu [" +" id=" +  id   +", pcode=" +  pcode   +", projectCode=" +  projectCode   +", code=" +  code   +", ordinal=" +  ordinal   +", path=" +  path   +", directory=" +  directory   +", cname=" +  cname   +", hidden=" +  hidden   +", requireAuth=" +  requireAuth   +", redirect=" +  redirect   +", iconCls=" +  iconCls   +", meta=" +  meta   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}