
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
 * 科室收货地址
*/
@Table(name="receive_address")
public class ReceiveAddress implements IBean
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
	 * 医院ID
	*/
	@Size(max=36)
	@JoinColumn(name="hos_id",table="bas_company_info",referencedColumnName="id")
	@Title("医院ID")
	private String hosId ;
	/**
	 * 科室ID
	*/
	@Size(max=36)
	@JoinColumn(name="dept_id",table="sys_org",referencedColumnName="id")
	@Title("科室ID")
	private String deptId ;
	/**
	 * 联系人
	 * 手工输入
	*/
	@Size(max=80)
	@Column(name="lxr")
	@Title("联系人")
	private String lxr ;
	/**
	 * 联系人电话
	 * 手工输入
	*/
	@Size(max=100)
	@Column(name="lxr_phone")
	@Title("联系人电话")
	private String lxrPhone ;
	/**
	 * 省
	 * 手工输入
	*/
	@Size(max=36)
	@JoinColumn(name="province",table="bas_area",referencedColumnName="cname")
	@Title("省")
	private String province ;
	/**
	 * 市
	*/
	@Size(max=36)
	@JoinColumn(name="city",table="bas_area",referencedColumnName="cname")
	@Title("市")
	private String city ;
	/**
	 * 区县
	*/
	@Size(max=36)
	@JoinColumn(name="area",table="bas_area",referencedColumnName="cname")
	@Title("区县")
	private String area ;
	/**
	 * 地址
	*/
	@Size(max=500)
	@Column(name="address")
	@Title("地址")
	private String address ;
	/**
	 * 区域代码
	*/
	@Size(max=36)
	@JoinColumn(name="area_code",table="bas_area",referencedColumnName="code")
	@Title("区域代码")
	private String areaCode ;
	/**
	 * 是否默认
	*/
	@Size(max=1)
	@Column(name="is_defaulte")
	@Title("是否默认")
	private String isDefaulte ;
	/**
	 * 是否已经上传阳光采购平台
	*/
	@Size(max=1)
	@Column(name="send_smp")
	@Title("是否已经上传阳光采购平台")
	private String sendSmp ;
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
	public String getHosId ()
	{
		return hosId ;
	}
	
	public void setHosId (String hosId )
	{
		this.hosId = hosId;
	}
	public String getDeptId ()
	{
		return deptId ;
	}
	
	public void setDeptId (String deptId )
	{
		this.deptId = deptId;
	}
	public String getLxr ()
	{
		return lxr ;
	}
	
	public void setLxr (String lxr )
	{
		this.lxr = lxr;
	}
	public String getLxrPhone ()
	{
		return lxrPhone ;
	}
	
	public void setLxrPhone (String lxrPhone )
	{
		this.lxrPhone = lxrPhone;
	}
	public String getProvince ()
	{
		return province ;
	}
	
	public void setProvince (String province )
	{
		this.province = province;
	}
	public String getCity ()
	{
		return city ;
	}
	
	public void setCity (String city )
	{
		this.city = city;
	}
	public String getArea ()
	{
		return area ;
	}
	
	public void setArea (String area )
	{
		this.area = area;
	}
	public String getAddress ()
	{
		return address ;
	}
	
	public void setAddress (String address )
	{
		this.address = address;
	}
	public String getAreaCode ()
	{
		return areaCode ;
	}
	
	public void setAreaCode (String areaCode )
	{
		this.areaCode = areaCode;
	}
	public String getIsDefaulte ()
	{
		return isDefaulte ;
	}
	
	public void setIsDefaulte (String isDefaulte )
	{
		this.isDefaulte = isDefaulte;
	}
	public String getSendSmp ()
	{
		return sendSmp ;
	}
	
	public void setSendSmp (String sendSmp )
	{
		this.sendSmp = sendSmp;
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
		return "ReceiveAddress [" +" id=" +  id   +", hosId=" +  hosId   +", deptId=" +  deptId   +", lxr=" +  lxr   +", lxrPhone=" +  lxrPhone   +", province=" +  province   +", city=" +  city   +", area=" +  area   +", address=" +  address   +", areaCode=" +  areaCode   +", isDefaulte=" +  isDefaulte   +", sendSmp=" +  sendSmp   +", lastUpdateDatetime=" +  lastUpdateDatetime   +", version=" +  version   
			 + "]";
	}

}