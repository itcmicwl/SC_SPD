package gyqx.spdhdi.uploadData.vo;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 采购确认单
 * 
 * @date 2017年9月15日 下午4:00:01
 */
public class PurconfirmJson {
	@JSONField(name = "SPD系统标识")
	private String spdCode;
	@JSONField(name = "配送单位系统标识")
	private String provSystemId;
	@JSONField(name = "采购订单号")
	private String purBillId;

	@JSONField(name = "要货医院")
	private String purchaseCompanyId;
	@JSONField(name = "要货医院名称")
	private String purchaseCompanyName;
	@JSONField(name = "要货医院ERP编号")
	private String purchaseCompanyErpCode;

	@JSONField(name = "要货科室编号")
	private String applyOrgCode;
	@JSONField(name = "要货科室名称")
	private String applyOrgName;
	@JSONField(name = "要货科室ERP编号")
	private String applyOrgErpCode;

	@JSONField(name = "收货科室编号")
	private String recieveOrgCode;
	@JSONField(name = "收货科室名称")
	private String recieveOrgName;
	@JSONField(name = "收货科室ERP编号")
	private String recieveOrgErpCode;

	@JSONField(name = "配送单位")
	private String provId;
	@JSONField(name = "配送单位名称")
	private String provName;
	@JSONField(name = "配送单位ERP编号")
	private String provErpCode;
	@JSONField(name = "配送单位ERP部门编号")
	private String provOrgCode;

	@JSONField(name = "业务部门ERP编号")
	private String saleDeptErpCode;
	@JSONField(name = "业务部门名称")
	private String saleDeptName;
	@JSONField(name = "业务员ERP编号")
	private String salemanCode;
	@JSONField(name = "业务员名称")
	private String salemanName;

	@JSONField(name = "请购类型")
	private String purType;

	@JSONField(name = "订单总行数")
	private Integer billTotal;

	@JSONField(name = "_date", format = "yyyy-MM-dd HH:mm:ss")
	private Date date = new Date();

	@JSONField(name = "采购订单明细")
	private List<PurconfirmListJson> purchaseList;

	public String getSpdCode() {
		return spdCode;
	}

	public String getProvSystemId() {
		return provSystemId;
	}

	public String getPurBillId() {
		return purBillId;
	}

	public String getPurchaseCompanyId() {
		return purchaseCompanyId;
	}

	public String getPurchaseCompanyName() {
		return purchaseCompanyName;
	}

	public String getPurchaseCompanyErpCode() {
		return purchaseCompanyErpCode;
	}

	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	public String getApplyOrgName() {
		return applyOrgName;
	}

	public String getApplyOrgErpCode() {
		return applyOrgErpCode;
	}

	public String getRecieveOrgCode() {
		return recieveOrgCode;
	}

	public String getRecieveOrgName() {
		return recieveOrgName;
	}

	public String getRecieveOrgErpCode() {
		return recieveOrgErpCode;
	}

	public String getProvId() {
		return provId;
	}

	public String getProvName() {
		return provName;
	}

	public String getProvErpCode() {
		return provErpCode;
	}

	public String getProvOrgCode() {
		return provOrgCode;
	}

	public String getSaleDeptErpCode() {
		return saleDeptErpCode;
	}

	public String getSaleDeptName() {
		return saleDeptName;
	}

	public String getSalemanCode() {
		return salemanCode;
	}

	public String getSalemanName() {
		return salemanName;
	}

	public String getPurType() {
		return purType;
	}

	public Integer getBillTotal() {
		return billTotal;
	}

	public Date getDate() {
		return date;
	}

	public List<PurconfirmListJson> getPurchaseList() {
		return purchaseList;
	}

	public void setSpdCode(String spdCode) {
		this.spdCode = spdCode;
	}

	public void setProvSystemId(String provSystemId) {
		this.provSystemId = provSystemId;
	}

	public void setPurBillId(String purBillId) {
		this.purBillId = purBillId;
	}

	public void setPurchaseCompanyId(String purchaseCompanyId) {
		this.purchaseCompanyId = purchaseCompanyId;
	}

	public void setPurchaseCompanyName(String purchaseCompanyName) {
		this.purchaseCompanyName = purchaseCompanyName;
	}

	public void setPurchaseCompanyErpCode(String purchaseCompanyErpCode) {
		this.purchaseCompanyErpCode = purchaseCompanyErpCode;
	}

	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}

	public void setApplyOrgErpCode(String applyOrgErpCode) {
		this.applyOrgErpCode = applyOrgErpCode;
	}

	public void setRecieveOrgCode(String recieveOrgCode) {
		this.recieveOrgCode = recieveOrgCode;
	}

	public void setRecieveOrgName(String recieveOrgName) {
		this.recieveOrgName = recieveOrgName;
	}

	public void setRecieveOrgErpCode(String recieveOrgErpCode) {
		this.recieveOrgErpCode = recieveOrgErpCode;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public void setProvErpCode(String provErpCode) {
		this.provErpCode = provErpCode;
	}

	public void setProvOrgCode(String provOrgCode) {
		this.provOrgCode = provOrgCode;
	}

	public void setSaleDeptErpCode(String saleDeptErpCode) {
		this.saleDeptErpCode = saleDeptErpCode;
	}

	public void setSaleDeptName(String saleDeptName) {
		this.saleDeptName = saleDeptName;
	}

	public void setSalemanCode(String salemanCode) {
		this.salemanCode = salemanCode;
	}

	public void setSalemanName(String salemanName) {
		this.salemanName = salemanName;
	}

	public void setPurType(String purType) {
		this.purType = purType;
	}

	public void setBillTotal(Integer billTotal) {
		this.billTotal = billTotal;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setPurchaseList(List<PurconfirmListJson> purchaseList) {
		this.purchaseList = purchaseList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurconfirmJson [spdCode=");
		builder.append(spdCode);
		builder.append(", provSystemId=");
		builder.append(provSystemId);
		builder.append(", purBillId=");
		builder.append(purBillId);
		builder.append(", purchaseCompanyId=");
		builder.append(purchaseCompanyId);
		builder.append(", purchaseCompanyName=");
		builder.append(purchaseCompanyName);
		builder.append(", purchaseCompanyErpCode=");
		builder.append(purchaseCompanyErpCode);
		builder.append(", applyOrgCode=");
		builder.append(applyOrgCode);
		builder.append(", applyOrgName=");
		builder.append(applyOrgName);
		builder.append(", applyOrgErpCode=");
		builder.append(applyOrgErpCode);
		builder.append(", recieveOrgCode=");
		builder.append(recieveOrgCode);
		builder.append(", recieveOrgName=");
		builder.append(recieveOrgName);
		builder.append(", recieveOrgErpCode=");
		builder.append(recieveOrgErpCode);
		builder.append(", provId=");
		builder.append(provId);
		builder.append(", provName=");
		builder.append(provName);
		builder.append(", provErpCode=");
		builder.append(provErpCode);
		builder.append(", provOrgCode=");
		builder.append(provOrgCode);
		builder.append(", saleDeptErpCode=");
		builder.append(saleDeptErpCode);
		builder.append(", saleDeptName=");
		builder.append(saleDeptName);
		builder.append(", salemanCode=");
		builder.append(salemanCode);
		builder.append(", salemanName=");
		builder.append(salemanName);
		builder.append(", purType=");
		builder.append(purType);
		builder.append(", billTotal=");
		builder.append(billTotal);
		builder.append(", date=");
		builder.append(date);
		builder.append(", purchaseList=");
		builder.append(purchaseList);
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}
