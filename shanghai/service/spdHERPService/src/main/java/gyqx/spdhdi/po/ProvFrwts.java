
package gyqx.spdhdi.po;

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
 * 法人委托书
*/
@Table(name="prov_frwts")
public class ProvFrwts implements IBean
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
	 * 供应商ID
	*/
	@Size(max=36)
	@JoinColumn(name="prov_id",table="bas_company_info",referencedColumnName="id")
	@Title("供应商ID")
	private String provId ;
	/**
	 * 医院ID
	*/
	@Size(max=36)
	@JoinColumn(name="hos_id",table="bas_company_info",referencedColumnName="id")
	@Title("医院ID")
	private String hosId ;
	/**
	 * 委托人
	*/
	@Size(max=36)
	@JoinColumn(name="wtr_id",table="sys_user",referencedColumnName="id")
	@Title("委托人")
	private String wtrId ;
	/**
	 * 委托人手机
	*/
	@Size(max=36)
	@Column(name="wtr_move_num")
	@Title("委托人手机")
	private String wtrMoveNum ;
	/**
	 * 委托书附件路径
	*/
	@Size(max=256)
	@Column(name="file_path_wts")
	@Title("委托书附件路径")
	private String filePathWts ;
	/**
	 * 委托人身份证路径
	*/
	@Size(max=256)
	@Column(name="file_path_wtr")
	@Title("委托人身份证路径")
	private String filePathWtr ;
	/**
	 * 有效开始日期
	*/
	@Column(name="expdt_begin_date")
	@Title("有效开始日期")
	private java.util.Date expdtBeginDate ;
	/**
	 * 有效结束日期
	*/
	@Column(name="expdt_end_date")
	@Title("有效结束日期")
	private java.util.Date expdtEndDate ;
	/**
	 * 操作员ID
	 * 新增或修改的操作员ID
	*/
	@Size(max=36)
	@Column(name="uxid")
	@Title("操作员ID")
	private String uxid ;
	/**
	 * 建档时间
	*/
	@Column(name="fill_date")
	@Title("建档时间")
	private java.util.Date fillDate ;
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
	public String getProvId ()
	{
		return provId ;
	}
	
	public void setProvId (String provId )
	{
		this.provId = provId;
	}
	public String getHosId ()
	{
		return hosId ;
	}
	
	public void setHosId (String hosId )
	{
		this.hosId = hosId;
	}
	public String getWtrId ()
	{
		return wtrId ;
	}
	
	public void setWtrId (String wtrId )
	{
		this.wtrId = wtrId;
	}
	public String getWtrMoveNum ()
	{
		return wtrMoveNum ;
	}
	
	public void setWtrMoveNum (String wtrMoveNum )
	{
		this.wtrMoveNum = wtrMoveNum;
	}
	public String getFilePathWts ()
	{
		return filePathWts ;
	}
	
	public void setFilePathWts (String filePathWts )
	{
		this.filePathWts = filePathWts;
	}
	public String getFilePathWtr ()
	{
		return filePathWtr ;
	}
	
	public void setFilePathWtr (String filePathWtr )
	{
		this.filePathWtr = filePathWtr;
	}
	public java.util.Date getExpdtBeginDate ()
	{
		return expdtBeginDate ;
	}
	
	public void setExpdtBeginDate (java.util.Date expdtBeginDate )
	{
		this.expdtBeginDate = expdtBeginDate;
	}
	public java.util.Date getExpdtEndDate ()
	{
		return expdtEndDate ;
	}
	
	public void setExpdtEndDate (java.util.Date expdtEndDate )
	{
		this.expdtEndDate = expdtEndDate;
	}
	public String getUxid ()
	{
		return uxid ;
	}
	
	public void setUxid (String uxid )
	{
		this.uxid = uxid;
	}
	public java.util.Date getFillDate ()
	{
		return fillDate ;
	}
	
	public void setFillDate (java.util.Date fillDate )
	{
		this.fillDate = fillDate;
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
		return "ProvFrwts [" +" id=" +  id   +", provId=" +  provId   +", hosId=" +  hosId   +", wtrId=" +  wtrId   +", wtrMoveNum=" +  wtrMoveNum   +", filePathWts=" +  filePathWts   +", filePathWtr=" +  filePathWtr   +", expdtBeginDate=" +  expdtBeginDate   +", expdtEndDate=" +  expdtEndDate   +", uxid=" +  uxid   +", fillDate=" +  fillDate   +", lastUpdateDatetime=" +  lastUpdateDatetime   +", version=" +  version   
			 + "]";
	}

}