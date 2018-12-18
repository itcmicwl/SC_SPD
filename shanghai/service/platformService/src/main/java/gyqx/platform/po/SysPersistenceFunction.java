
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
 * 数据处理模块功能设计表
*/
@Table(name="sys_persistence_function")
public class SysPersistenceFunction implements IBean
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
	 * 功能ID
	*/
	@Size(max=36)
	@JoinColumn(name="persistence_id",table="sys_persistence",referencedColumnName="id")
	@Title("功能ID")
	private String persistenceId ;
	/**
	 * 参数及类型描述
	*/
	@Size(max=255)
	@Column(name="params")
	@Title("参数及类型描述")
	private String params ;
	/**
	 * 引用的SQL ids
	 * 多个sys_string.id，以逗号分隔
	*/
	@Size(max=1000)
	@Column(name="ref_sqls")
	@Title("引用的SQL ids")
	private String refSqls ;
	/**
	 * 业务处理的代码
	 * 用SQL脚本描述
	*/
	@Size(max=1000)
	@Column(name="codes")
	@Title("业务处理的代码")
	private String codes ;
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
	public String getPersistenceId ()
	{
		return persistenceId ;
	}
	
	public void setPersistenceId (String persistenceId )
	{
		this.persistenceId = persistenceId;
	}
	public String getParams ()
	{
		return params ;
	}
	
	public void setParams (String params )
	{
		this.params = params;
	}
	public String getRefSqls ()
	{
		return refSqls ;
	}
	
	public void setRefSqls (String refSqls )
	{
		this.refSqls = refSqls;
	}
	public String getCodes ()
	{
		return codes ;
	}
	
	public void setCodes (String codes )
	{
		this.codes = codes;
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
		return "SysPersistenceFunction [" +" id=" +  id   +", persistenceId=" +  persistenceId   +", params=" +  params   +", refSqls=" +  refSqls   +", codes=" +  codes   +", remark=" +  remark   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}