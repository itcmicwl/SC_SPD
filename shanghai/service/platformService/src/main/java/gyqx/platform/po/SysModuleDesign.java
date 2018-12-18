
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
 * 模块设计表
*/
@Table(name="sys_module_design")
public class SysModuleDesign implements IBean
{
	/**
	 * ID
	*/
	@Id
	@Size(max=128)
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 模块ID
	*/
	@Size(max=128)
	@JoinColumn(name="module_id",table="sys_module",referencedColumnName="id")
	@Title("模块ID")
	private String moduleId ;
	/**
	 * 编码
	*/
	@Size(max=300)
	@Column(name="code")
	@Title("编码")
	private String code ;
	/**
	 * 包名
	*/
	@Size(max=128)
	@Column(name="package_name")
	@Title("包名")
	private String packageName ;
	/**
	 * 名称
	*/
	@Size(max=255)
	@Column(name="ename")
	@Title("名称")
	private String ename ;
	/**
	 * 别名
	 * 中文列名
	*/
	@Size(max=255)
	@Column(name="cname")
	@Title("别名")
	private String cname ;
	/**
	 * 描述
	*/
	@Size(max=255)
	@Column(name="remark")
	@Title("描述")
	private String remark ;
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
	public String getModuleId ()
	{
		return moduleId ;
	}
	
	public void setModuleId (String moduleId )
	{
		this.moduleId = moduleId;
	}
	public String getCode ()
	{
		return code ;
	}
	
	public void setCode (String code )
	{
		this.code = code;
	}
	public String getPackageName ()
	{
		return packageName ;
	}
	
	public void setPackageName (String packageName )
	{
		this.packageName = packageName;
	}
	public String getEname ()
	{
		return ename ;
	}
	
	public void setEname (String ename )
	{
		this.ename = ename;
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
		return "SysModuleDesign [" +" id=" +  id   +", moduleId=" +  moduleId   +", code=" +  code   +", packageName=" +  packageName   +", ename=" +  ename   +", cname=" +  cname   +", remark=" +  remark   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}