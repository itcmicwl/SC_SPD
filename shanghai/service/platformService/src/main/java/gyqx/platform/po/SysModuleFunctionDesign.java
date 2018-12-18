
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
 * 模块功能方法设计表
*/
@Table(name="sys_module_function_design")
public class SysModuleFunctionDesign implements IBean
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
	 * 方法ID
	*/
	@Size(max=128)
	@JoinColumn(name="function_id",table="sys_module_function",referencedColumnName="id")
	@Title("方法ID")
	private String functionId ;
	/**
	 * 名称
	*/
	@Size(max=255)
	@Column(name="ename")
	@Title("名称")
	private String ename ;
	/**
	 * URL
	*/
	@Size(max=128)
	@Column(name="url")
	@Title("URL")
	private String url ;
	/**
	 * 编码
	*/
	@Size(max=300)
	@Column(name="code")
	@Title("编码")
	private String code ;
	/**
	 * 参数及类型描述
	*/
	@Size(max=255)
	@Column(name="params")
	@Title("参数及类型描述")
	private String params ;
	/**
	 * 引用的模块IDs
	*/
	@Size(max=255)
	@Column(name="ref_sys_modules")
	@Title("引用的模块IDs")
	private String refSysModules ;
	/**
	 * 引用持久处理模块IDs
	*/
	@Size(max=255)
	@Column(name="ref_sys_persistences")
	@Title("引用持久处理模块IDs")
	private String refSysPersistences ;
	/**
	 * WEB处理的代码
	 * 脚本描述
	*/
	@Size(max=2000)
	@Column(name="web_codes")
	@Title("WEB处理的代码")
	private String webCodes ;
	/**
	 * 业务处理的代码
	 * 业务脚本描述
	*/
	@Size(max=2000)
	@Column(name="service_codes")
	@Title("业务处理的代码")
	private String serviceCodes ;
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
	public String getFunctionId ()
	{
		return functionId ;
	}
	
	public void setFunctionId (String functionId )
	{
		this.functionId = functionId;
	}
	public String getEname ()
	{
		return ename ;
	}
	
	public void setEname (String ename )
	{
		this.ename = ename;
	}
	public String getUrl ()
	{
		return url ;
	}
	
	public void setUrl (String url )
	{
		this.url = url;
	}
	public String getCode ()
	{
		return code ;
	}
	
	public void setCode (String code )
	{
		this.code = code;
	}
	public String getParams ()
	{
		return params ;
	}
	
	public void setParams (String params )
	{
		this.params = params;
	}
	public String getRefSysModules ()
	{
		return refSysModules ;
	}
	
	public void setRefSysModules (String refSysModules )
	{
		this.refSysModules = refSysModules;
	}
	public String getRefSysPersistences ()
	{
		return refSysPersistences ;
	}
	
	public void setRefSysPersistences (String refSysPersistences )
	{
		this.refSysPersistences = refSysPersistences;
	}
	public String getWebCodes ()
	{
		return webCodes ;
	}
	
	public void setWebCodes (String webCodes )
	{
		this.webCodes = webCodes;
	}
	public String getServiceCodes ()
	{
		return serviceCodes ;
	}
	
	public void setServiceCodes (String serviceCodes )
	{
		this.serviceCodes = serviceCodes;
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
		return "SysModuleFunctionDesign [" +" id=" +  id   +", moduleId=" +  moduleId   +", functionId=" +  functionId   +", ename=" +  ename   +", url=" +  url   +", code=" +  code   +", params=" +  params   +", refSysModules=" +  refSysModules   +", refSysPersistences=" +  refSysPersistences   +", webCodes=" +  webCodes   +", serviceCodes=" +  serviceCodes   +", remark=" +  remark   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}