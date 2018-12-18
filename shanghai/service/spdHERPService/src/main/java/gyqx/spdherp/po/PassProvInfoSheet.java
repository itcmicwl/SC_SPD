
package gyqx.spdherp.po;

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
 * 供应商信息接口历史数据
*/
@Table(name="pass_prov_info_sheet")
public class PassProvInfoSheet implements IBean
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
	 * SPD系统标识
	 * SPD系统标识
	*/
	@Size(max=50)
	@Column(name="spd_code")
	@Title("SPD系统标识")
	private String spdCode ;
	/**
	 * 配送机构系统标识
	 * ERP系统标识
	*/
	@Size(max=50)
	@Column(name="prov_sys_code")
	@Title("配送机构系统标识")
	private String provSysCode ;
	/**
	 * 配送机构ERP编号
	*/
	@Size(max=50)
	@Column(name="prov_erp_code")
	@Title("配送机构ERP编号")
	private String provErpCode ;
	/**
	 * 配送机构名称
	*/
	@Size(max=100)
	@Column(name="prov_name")
	@Title("配送机构名称")
	private String provName ;
	/**
	 * ERP供应商编号
	*/
	@Size(max=50)
	@Column(name="prov_erp_code1")
	@Title("ERP供应商编号")
	private String provErpCode1 ;
	/**
	 * ERP供应商名称
	*/
	@Size(max=100)
	@Column(name="prov_erp_name")
	@Title("ERP供应商名称")
	private String provErpName ;
	/**
	 * 简称
	*/
	@Size(max=100)
	@Column(name="prov_simple_name")
	@Title("简称")
	private String provSimpleName ;
	/**
	 * 备注
	*/
	@Size(max=200)
	@Column(name="remark")
	@Title("备注")
	private String remark ;
	/**
	 * 封存
	*/
	@Column(name="save")
	@Title("封存")
	private int save ;
	/**
	 * 最后更新时间
	*/
	@Column(name="last_update_datetime")
	@Title("最后更新时间")
	private java.util.Date lastUpdateDatetime ;
	/**
	 * 数据版本
	*/
	@Column(name="version")
	@Title("数据版本")
	private int version ;

	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getSpdCode ()
	{
		return spdCode ;
	}
	
	public void setSpdCode (String spdCode )
	{
		this.spdCode = spdCode;
	}
	public String getProvSysCode ()
	{
		return provSysCode ;
	}
	
	public void setProvSysCode (String provSysCode )
	{
		this.provSysCode = provSysCode;
	}
	public String getProvErpCode ()
	{
		return provErpCode ;
	}
	
	public void setProvErpCode (String provErpCode )
	{
		this.provErpCode = provErpCode;
	}
	public String getProvName ()
	{
		return provName ;
	}
	
	public void setProvName (String provName )
	{
		this.provName = provName;
	}
	public String getProvErpCode1 ()
	{
		return provErpCode1 ;
	}
	
	public void setProvErpCode1 (String provErpCode1 )
	{
		this.provErpCode1 = provErpCode1;
	}
	public String getProvErpName ()
	{
		return provErpName ;
	}
	
	public void setProvErpName (String provErpName )
	{
		this.provErpName = provErpName;
	}
	public String getProvSimpleName ()
	{
		return provSimpleName ;
	}
	
	public void setProvSimpleName (String provSimpleName )
	{
		this.provSimpleName = provSimpleName;
	}
	public String getRemark ()
	{
		return remark ;
	}
	
	public void setRemark (String remark )
	{
		this.remark = remark;
	}
	public int getSave ()
	{
		return save ;
	}
	
	public void setSave (int save )
	{
		this.save = save;
	}
	public java.util.Date getLastUpdateDatetime ()
	{
		return lastUpdateDatetime ;
	}
	
	public void setLastUpdateDatetime (java.util.Date lastUpdateDatetime )
	{
		this.lastUpdateDatetime = lastUpdateDatetime;
	}
	public int getVersion ()
	{
		return version ;
	}
	
	public void setVersion (int version )
	{
		this.version = version;
	}


	@Override
	public String toString() {
		return "PassProvInfoSheet [" +" id=" +  id   +", spdCode=" +  spdCode   +", provSysCode=" +  provSysCode   +", provErpCode=" +  provErpCode   +", provName=" +  provName   +", provErpCode1=" +  provErpCode1   +", provErpName=" +  provErpName   +", provSimpleName=" +  provSimpleName   +", remark=" +  remark   +", save=" +  save   +", lastUpdateDatetime=" +  lastUpdateDatetime   +", version=" +  version   
			 + "]";
	}

}