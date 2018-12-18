
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
 * 采购确认单
*/
@Table(name="purconfirm")
public class Purconfirm implements IBean
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
	 * 单号
	*/
	@Size(max=36)
	@Column(name="bill_id")
	@Title("单号")
	private String billId ;
	/**
	 * SPD系统标识
	*/
	@Size(max=36)
	@Column(name="spd_code")
	@Title("SPD系统标识")
	private String spdCode ;
	/**
	 * 采购订单号
	*/
	@Size(max=36)
	@Column(name="pur_bill_id")
	@Title("采购订单号")
	private String purBillId ;
	/**
	 * 采购方式
	 * 0--实物采购，1--虚拟采购
	*/
	@Column(name="pur_type")
	@Title("采购方式")
	private int purType ;
	/**
	 * 请购机构
	*/
	@Size(max=36)
	@JoinColumn(name="purchase_company_id",table="bas_company_info",referencedColumnName="id")
	@Title("请购机构")
	private String purchaseCompanyId ;
	/**
	 * 请购机构编号
	*/
	@Size(max=36)
	@JoinColumn(name="purchase_company_code",table="bas_company_info",referencedColumnName="code")
	@Title("请购机构编号")
	private String purchaseCompanyCode ;
	/**
	 * 请购机构名称
	*/
	@Size(max=36)
	@JoinColumn(name="purchase_company_name",table="bas_company_info",referencedColumnName="name")
	@Title("请购机构名称")
	private String purchaseCompanyName ;
	/**
	 * 请购机构ERP编号
	*/
	@Size(max=36)
	@JoinColumn(name="purchase_company_erp_code",table="bas_company_info",referencedColumnName="erp_code")
	@Title("请购机构ERP编号")
	private String purchaseCompanyErpCode ;
	/**
	 * 业务员ID
	*/
	@Size(max=36)
	@Column(name="sale_man")
	@Title("业务员ID")
	private String saleMan ;
	/**
	 * 请购科室
	*/
	@Size(max=36)
	@JoinColumn(name="apply_org_id",table="sys_org",referencedColumnName="id")
	@Title("请购科室")
	private String applyOrgId ;
	/**
	 * 请购科室编号
	*/
	@Size(max=36)
	@JoinColumn(name="apply_org_code",table="sys_org",referencedColumnName="code")
	@Title("请购科室编号")
	private String applyOrgCode ;
	/**
	 * 请购科室名称
	*/
	@Size(max=36)
	@JoinColumn(name="apply_org_name",table="sys_org",referencedColumnName="name")
	@Title("请购科室名称")
	private String applyOrgName ;
	/**
	 * 请购科室SCM编号
	*/
	@Size(max=36)
	@JoinColumn(name="apply_org_scm_code",table="sys_org",referencedColumnName="scm_code")
	@Title("请购科室SCM编号")
	private String applyOrgScmCode ;
	/**
	 * 请购科室ERP编号
	*/
	@Size(max=36)
	@JoinColumn(name="apply_org_erp_code",table="sys_org",referencedColumnName="erp_code")
	@Title("请购科室ERP编号")
	private String applyOrgErpCode ;
	/**
	 * 收货科室
	*/
	@Size(max=36)
	@JoinColumn(name="recieve_org_id",table="sys_org",referencedColumnName="id")
	@Title("收货科室")
	private String recieveOrgId ;
	/**
	 * 收货科室编号
	*/
	@Size(max=36)
	@JoinColumn(name="recieve_org_code",table="sys_org",referencedColumnName="code")
	@Title("收货科室编号")
	private String recieveOrgCode ;
	/**
	 * 收货科室名称
	*/
	@Size(max=36)
	@JoinColumn(name="recieve_org_name",table="sys_org",referencedColumnName="name")
	@Title("收货科室名称")
	private String recieveOrgName ;
	/**
	 * 收货科室SCM编号
	*/
	@Size(max=36)
	@JoinColumn(name="recieve_org_scm_code",table="sys_org",referencedColumnName="scm_code")
	@Title("收货科室SCM编号")
	private String recieveOrgScmCode ;
	/**
	 * 收货科室ERP编号
	*/
	@Size(max=36)
	@JoinColumn(name="recieve_org_erp_code",table="sys_org",referencedColumnName="erp_code")
	@Title("收货科室ERP编号")
	private String recieveOrgErpCode ;
	/**
	 * 配送机构
	*/
	@Size(max=36)
	@JoinColumn(name="prov_id",table="bas_company_info",referencedColumnName="id")
	@Title("配送机构")
	private String provId ;
	/**
	 * 配送机构编号
	*/
	@Size(max=36)
	@JoinColumn(name="prov_code",table="bas_company_info",referencedColumnName="code")
	@Title("配送机构编号")
	private String provCode ;
	/**
	 * 配送机构名称
	*/
	@Size(max=36)
	@JoinColumn(name="prov_name",table="bas_company_info",referencedColumnName="name")
	@Title("配送机构名称")
	private String provName ;
	/**
	 * 配送机构ERP编号
	*/
	@Size(max=36)
	@JoinColumn(name="prov_erp_code",table="bas_company_info",referencedColumnName="erp_code")
	@Title("配送机构ERP编号")
	private String provErpCode ;
	/**
	 * 配送机构ERP部门编号
	*/
	@Size(max=36)
	@JoinColumn(name="prov_org_code",table="sys_org",referencedColumnName="erp_code")
	@Title("配送机构ERP部门编号")
	private String provOrgCode ;
	/**
	 * 配送机构系统标识
	*/
	@Size(max=36)
	@Column(name="prov_system_id")
	@Title("配送机构系统标识")
	private String provSystemId ;
	/**
	 * 配送关系类型
	*/
	@Size(max=36)
	@Column(name="delivery_relation")
	@Title("配送关系类型")
	private String deliveryRelation ;
	/**
	 * 结算配送机构
	*/
	@Size(max=36)
	@Column(name="balance_org_id")
	@Title("结算配送机构")
	private String balanceOrgId ;
	/**
	 * 采购总金额
	*/
	@Column(name="sum_amount")
	@Title("采购总金额")
	private java.math.BigDecimal sumAmount ;
	/**
	 * 采购不含税总金额
	*/
	@Column(name="sum_tax_amount")
	@Title("采购不含税总金额")
	private java.math.BigDecimal sumTaxAmount ;
	/**
	 * 当前审批级别
	*/
	@Column(name="status")
	@Title("当前审批级别")
	private int status ;
	/**
	 * 制单人
	*/
	@Size(max=36)
	@Column(name="fillter")
	@Title("制单人")
	private String fillter ;
	/**
	 * 备注信息
	*/
	@Size(max=300)
	@Column(name="remark")
	@Title("备注信息")
	private String remark ;
	/**
	 * 笔数
	*/
	@Column(name="sum_row")
	@Title("笔数")
	private int sumRow ;
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
	 * 是否已上传
	*/
	@Column(name="is_trans")
	@Title("是否已上传")
	private int isTrans ;
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
	public String getBillId ()
	{
		return billId ;
	}
	
	public void setBillId (String billId )
	{
		this.billId = billId;
	}
	public String getSpdCode ()
	{
		return spdCode ;
	}
	
	public void setSpdCode (String spdCode )
	{
		this.spdCode = spdCode;
	}
	public String getPurBillId ()
	{
		return purBillId ;
	}
	
	public void setPurBillId (String purBillId )
	{
		this.purBillId = purBillId;
	}
	public int getPurType ()
	{
		return purType ;
	}
	
	public void setPurType (int purType )
	{
		this.purType = purType;
	}
	public String getPurchaseCompanyId ()
	{
		return purchaseCompanyId ;
	}
	
	public void setPurchaseCompanyId (String purchaseCompanyId )
	{
		this.purchaseCompanyId = purchaseCompanyId;
	}
	public String getPurchaseCompanyCode ()
	{
		return purchaseCompanyCode ;
	}
	
	public void setPurchaseCompanyCode (String purchaseCompanyCode )
	{
		this.purchaseCompanyCode = purchaseCompanyCode;
	}
	public String getPurchaseCompanyName ()
	{
		return purchaseCompanyName ;
	}
	
	public void setPurchaseCompanyName (String purchaseCompanyName )
	{
		this.purchaseCompanyName = purchaseCompanyName;
	}
	public String getPurchaseCompanyErpCode ()
	{
		return purchaseCompanyErpCode ;
	}
	
	public void setPurchaseCompanyErpCode (String purchaseCompanyErpCode )
	{
		this.purchaseCompanyErpCode = purchaseCompanyErpCode;
	}
	public String getSaleMan ()
	{
		return saleMan ;
	}
	
	public void setSaleMan (String saleMan )
	{
		this.saleMan = saleMan;
	}
	public String getApplyOrgId ()
	{
		return applyOrgId ;
	}
	
	public void setApplyOrgId (String applyOrgId )
	{
		this.applyOrgId = applyOrgId;
	}
	public String getApplyOrgCode ()
	{
		return applyOrgCode ;
	}
	
	public void setApplyOrgCode (String applyOrgCode )
	{
		this.applyOrgCode = applyOrgCode;
	}
	public String getApplyOrgName ()
	{
		return applyOrgName ;
	}
	
	public void setApplyOrgName (String applyOrgName )
	{
		this.applyOrgName = applyOrgName;
	}
	public String getApplyOrgScmCode ()
	{
		return applyOrgScmCode ;
	}
	
	public void setApplyOrgScmCode (String applyOrgScmCode )
	{
		this.applyOrgScmCode = applyOrgScmCode;
	}
	public String getApplyOrgErpCode ()
	{
		return applyOrgErpCode ;
	}
	
	public void setApplyOrgErpCode (String applyOrgErpCode )
	{
		this.applyOrgErpCode = applyOrgErpCode;
	}
	public String getRecieveOrgId ()
	{
		return recieveOrgId ;
	}
	
	public void setRecieveOrgId (String recieveOrgId )
	{
		this.recieveOrgId = recieveOrgId;
	}
	public String getRecieveOrgCode ()
	{
		return recieveOrgCode ;
	}
	
	public void setRecieveOrgCode (String recieveOrgCode )
	{
		this.recieveOrgCode = recieveOrgCode;
	}
	public String getRecieveOrgName ()
	{
		return recieveOrgName ;
	}
	
	public void setRecieveOrgName (String recieveOrgName )
	{
		this.recieveOrgName = recieveOrgName;
	}
	public String getRecieveOrgScmCode ()
	{
		return recieveOrgScmCode ;
	}
	
	public void setRecieveOrgScmCode (String recieveOrgScmCode )
	{
		this.recieveOrgScmCode = recieveOrgScmCode;
	}
	public String getRecieveOrgErpCode ()
	{
		return recieveOrgErpCode ;
	}
	
	public void setRecieveOrgErpCode (String recieveOrgErpCode )
	{
		this.recieveOrgErpCode = recieveOrgErpCode;
	}
	public String getProvId ()
	{
		return provId ;
	}
	
	public void setProvId (String provId )
	{
		this.provId = provId;
	}
	public String getProvCode ()
	{
		return provCode ;
	}
	
	public void setProvCode (String provCode )
	{
		this.provCode = provCode;
	}
	public String getProvName ()
	{
		return provName ;
	}
	
	public void setProvName (String provName )
	{
		this.provName = provName;
	}
	public String getProvErpCode ()
	{
		return provErpCode ;
	}
	
	public void setProvErpCode (String provErpCode )
	{
		this.provErpCode = provErpCode;
	}
	public String getProvOrgCode ()
	{
		return provOrgCode ;
	}
	
	public void setProvOrgCode (String provOrgCode )
	{
		this.provOrgCode = provOrgCode;
	}
	public String getProvSystemId ()
	{
		return provSystemId ;
	}
	
	public void setProvSystemId (String provSystemId )
	{
		this.provSystemId = provSystemId;
	}
	public String getDeliveryRelation ()
	{
		return deliveryRelation ;
	}
	
	public void setDeliveryRelation (String deliveryRelation )
	{
		this.deliveryRelation = deliveryRelation;
	}
	public String getBalanceOrgId ()
	{
		return balanceOrgId ;
	}
	
	public void setBalanceOrgId (String balanceOrgId )
	{
		this.balanceOrgId = balanceOrgId;
	}
	public java.math.BigDecimal getSumAmount ()
	{
		return sumAmount ;
	}
	
	public void setSumAmount (java.math.BigDecimal sumAmount )
	{
		this.sumAmount = sumAmount;
	}
	public java.math.BigDecimal getSumTaxAmount ()
	{
		return sumTaxAmount ;
	}
	
	public void setSumTaxAmount (java.math.BigDecimal sumTaxAmount )
	{
		this.sumTaxAmount = sumTaxAmount;
	}
	public int getStatus ()
	{
		return status ;
	}
	
	public void setStatus (int status )
	{
		this.status = status;
	}
	public String getFillter ()
	{
		return fillter ;
	}
	
	public void setFillter (String fillter )
	{
		this.fillter = fillter;
	}
	public String getRemark ()
	{
		return remark ;
	}
	
	public void setRemark (String remark )
	{
		this.remark = remark;
	}
	public int getSumRow ()
	{
		return sumRow ;
	}
	
	public void setSumRow (int sumRow )
	{
		this.sumRow = sumRow;
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
	public int getIsTrans ()
	{
		return isTrans ;
	}
	
	public void setIsTrans (int isTrans )
	{
		this.isTrans = isTrans;
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
		return "Purconfirm [" +" id=" +  id   +", billId=" +  billId   +", spdCode=" +  spdCode   +", purBillId=" +  purBillId   +", purType=" +  purType   +", purchaseCompanyId=" +  purchaseCompanyId   +", purchaseCompanyCode=" +  purchaseCompanyCode   +", purchaseCompanyName=" +  purchaseCompanyName   +", purchaseCompanyErpCode=" +  purchaseCompanyErpCode   +", saleMan=" +  saleMan   +", applyOrgId=" +  applyOrgId   +", applyOrgCode=" +  applyOrgCode   +", applyOrgName=" +  applyOrgName   +", applyOrgScmCode=" +  applyOrgScmCode   +", applyOrgErpCode=" +  applyOrgErpCode   +", recieveOrgId=" +  recieveOrgId   +", recieveOrgCode=" +  recieveOrgCode   +", recieveOrgName=" +  recieveOrgName   +", recieveOrgScmCode=" +  recieveOrgScmCode   +", recieveOrgErpCode=" +  recieveOrgErpCode   +", provId=" +  provId   +", provCode=" +  provCode   +", provName=" +  provName   +", provErpCode=" +  provErpCode   +", provOrgCode=" +  provOrgCode   +", provSystemId=" +  provSystemId   +", deliveryRelation=" +  deliveryRelation   +", balanceOrgId=" +  balanceOrgId   +", sumAmount=" +  sumAmount   +", sumTaxAmount=" +  sumTaxAmount   +", status=" +  status   +", fillter=" +  fillter   +", remark=" +  remark   +", sumRow=" +  sumRow   +", fillDate=" +  fillDate   +", lastUpdateDatetime=" +  lastUpdateDatetime   +", isTrans=" +  isTrans   +", version=" +  version   
			 + "]";
	}

}