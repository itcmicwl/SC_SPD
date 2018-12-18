
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
 * 角色定义表
*/
@Table(name="sys_role")
public class SysRole implements IBean
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
	 * 公司ID
	*/
	@Size(max=36)
	@JoinColumn(name="company_id",table="bas_company_info",referencedColumnName="id")
	@Title("公司ID")
	private String companyId ;
	/**
	 * 角色名称
	*/
	@Size(max=36)
	@Column(name="cname")
	@Title("角色名称")
	private String cname ;
	/**
	 * 编码
	*/
	@Size(max=36)
	@Column(name="code")
	@Title("编码")
	private String code ;
	/**
	 * 描述
	*/
	@Size(max=255)
	@Column(name="remark")
	@Title("描述")
	private String remark ;
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
	/**
	 * 状态
	 * 
	 * [0 无效,1 有效]
	*/
	@NotEmpty
	@Size(max=1)
	@Column(name="flag")
	@Title("状态")
	private String flag ;
	/**
	 * 角色模板ID
	 * 当角色是从全局角色模板中复制而创建时，需填全局角色ID
	*/
	@Size(max=36)
	@JoinColumn(name="role_template_id",table="sys_role",referencedColumnName="id")
	@Title("角色模板ID")
	private String roleTemplateId ;
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
	public String getCompanyId ()
	{
		return companyId ;
	}
	
	public void setCompanyId (String companyId )
	{
		this.companyId = companyId;
	}
	public String getCname ()
	{
		return cname ;
	}
	
	public void setCname (String cname )
	{
		this.cname = cname;
	}
	public String getCode ()
	{
		return code ;
	}
	
	public void setCode (String code )
	{
		this.code = code;
	}
	public String getRemark ()
	{
		return remark ;
	}
	
	public void setRemark (String remark )
	{
		this.remark = remark;
	}
	public String getKind ()
	{
		return kind ;
	}
	
	public void setKind (String kind )
	{
		this.kind = kind;
	}
	public String getFlag ()
	{
		return flag ;
	}
	
	public void setFlag (String flag )
	{
		this.flag = flag;
	}
	public String getRoleTemplateId ()
	{
		return roleTemplateId ;
	}
	
	public void setRoleTemplateId (String roleTemplateId )
	{
		this.roleTemplateId = roleTemplateId;
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
		return "SysRole [" +" id=" +  id   +", companyId=" +  companyId   +", cname=" +  cname   +", code=" +  code   +", remark=" +  remark   +", kind=" +  kind   +", flag=" +  flag   +", roleTemplateId=" +  roleTemplateId   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}