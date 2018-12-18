package gyqx.spdhdi.uploadData.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 应急库分类账查询 <br>
 * 返回数据
 * 
 * @date 2017年9月13日 下午4:58:00
 */
public class LedgerResponse {

	@JSONField(name = "GUID")
	private String guid;

	@JSONField(name = "行号")
	private int rowNum;

	// @JSONField(name = "配送机构ERP编号")
	// private String provErpCode;

	@JSONField(name = "商品号")
	private String provGoodsId;

	// @JSONField(name = "商品名称")
	// private String provGoodsName;

	@JSONField(name = "仓库")
	private String stoc;

	@JSONField(name = "单据号")
	private String formId;

	@JSONField(name = "单据类型")
	private String formType;

	@JSONField(name = "公司")
	private String company;

	@JSONField(name = "日记账行")
	private BigDecimal dailyNum;

	@JSONField(name = "库位")
	private String stocShelf;

	// **
	@JSONField(name = "批次")
	private String batchNum;

	@JSONField(name = "计量单位")
	private String unit;

	@JSONField(name = "计量单位说明")
	private String unitExplain;

	@JSONField(name = "业务数量")
	private BigDecimal saleNum;

	@JSONField(name = "批号")
	private String batchCode;

	@JSONField(name = "灭菌批号")
	private String sterilizationCode;

	@JSONField(name = "生产日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date productDate;

	@JSONField(name = "有效期至", format = "yyyy-MM-dd HH:mm:ss")
	private Date expdtEndDate;

	// **
	@JSONField(name = "灭菌失效日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date sterilizationInvalidDate;

	@JSONField(name = "灭菌日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date sterilizationDate;

	@JSONField(name = "订单号")
	private String billId;

	@JSONField(name = "订单类型")
	private String purType;

	@JSONField(name = "订单公司")
	private String purCompany;

	@JSONField(name = "订单行号")
	private String rowNumber;

	@JSONField(name = "产品大类")
	private String productKind;

	@JSONField(name = "特殊分类")
	private String productSpecialKind;

	@JSONField(name = "产品分类")
	private String productSubKind;

	@JSONField(name = "是否禁用")
	private String disable; // Y禁用，N启用

	@JSONField(name = "生产厂家")
	private String mrfsName;

	@JSONField(name = "证照编码")
	private String certificateCode;

	@JSONField(name = "进口/国产/合资")
	private String jgh;

	@JSONField(name = "配送机构名称")
	private String provName;

	@JSONField(name = "配送机构ERP编号")
	private String provErpCode;

	@JSONField(name = "医疗机构名称")
	private String hosCompanyName;

	@JSONField(name = "医疗结构ERP编号")
	private String hosCompanyErpCode;

	@JSONField(name = "请购/请退科室名称")
	private String applyOrReturnOrgName;

	@JSONField(name = "请购/请退科室ERP编号")
	private String applyOrReturnOrgErpCode;

	@JSONField(name = "入库/出库科室名称")
	private String inOrOutOrgName;

	@JSONField(name = "入库/出库科室ERP编号")
	private String inOrOutOrgErpCode;

	@JSONField(name = "业务类型")
	private String saleType;

	@JSONField(name = "采购订单号")
	private String purBillId;

	@JSONField(name = "科室申请单号")
	private String applyBillId;

	@JSONField(name = "医疗器械配送机构内部编号")
	private String provGoodsErpCode;

	@JSONField(name = "医疗器械配送机构内部名称")
	private String provGoodsErpName;

	public String getGuid() {
		return guid;
	}

	public int getRowNum() {
		return rowNum;
	}

	public String getProvGoodsId() {
		return provGoodsId;
	}

	public String getStoc() {
		return stoc;
	}

	public String getFormId() {
		return formId;
	}

	public String getFormType() {
		return formType;
	}

	public String getCompany() {
		return company;
	}

	public BigDecimal getDailyNum() {
		return dailyNum;
	}

	public String getStocShelf() {
		return stocShelf;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public String getUnit() {
		return unit;
	}

	public String getUnitExplain() {
		return unitExplain;
	}

	public BigDecimal getSaleNum() {
		return saleNum;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public String getSterilizationCode() {
		return sterilizationCode;
	}

	public Date getProductDate() {
		return productDate;
	}

	public Date getExpdtEndDate() {
		return expdtEndDate;
	}

	public Date getSterilizationInvalidDate() {
		return sterilizationInvalidDate;
	}

	public Date getSterilizationDate() {
		return sterilizationDate;
	}

	public String getBillId() {
		return billId;
	}

	public String getPurType() {
		return purType;
	}

	public String getPurCompany() {
		return purCompany;
	}

	public String getRowNumber() {
		return rowNumber;
	}

	public String getProductKind() {
		return productKind;
	}

	public String getProductSpecialKind() {
		return productSpecialKind;
	}

	public String getProductSubKind() {
		return productSubKind;
	}

	public String getDisable() {
		return disable;
	}

	public String getMrfsName() {
		return mrfsName;
	}

	public String getCertificateCode() {
		return certificateCode;
	}

	public String getJgh() {
		return jgh;
	}

	public String getProvName() {
		return provName;
	}

	public String getProvErpCode() {
		return provErpCode;
	}

	public String getHosCompanyName() {
		return hosCompanyName;
	}

	public String getHosCompanyErpCode() {
		return hosCompanyErpCode;
	}

	public String getApplyOrReturnOrgName() {
		return applyOrReturnOrgName;
	}

	public String getApplyOrReturnOrgErpCode() {
		return applyOrReturnOrgErpCode;
	}

	public String getInOrOutOrgName() {
		return inOrOutOrgName;
	}

	public String getInOrOutOrgErpCode() {
		return inOrOutOrgErpCode;
	}

	public String getSaleType() {
		return saleType;
	}

	public String getPurBillId() {
		return purBillId;
	}

	public String getApplyBillId() {
		return applyBillId;
	}

	public String getProvGoodsErpCode() {
		return provGoodsErpCode;
	}

	public String getProvGoodsErpName() {
		return provGoodsErpName;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void setProvGoodsId(String provGoodsId) {
		this.provGoodsId = provGoodsId;
	}

	public void setStoc(String stoc) {
		this.stoc = stoc;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setDailyNum(BigDecimal dailyNum) {
		this.dailyNum = dailyNum;
	}

	public void setStocShelf(String stocShelf) {
		this.stocShelf = stocShelf;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setUnitExplain(String unitExplain) {
		this.unitExplain = unitExplain;
	}

	public void setSaleNum(BigDecimal saleNum) {
		this.saleNum = saleNum;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public void setSterilizationCode(String sterilizationCode) {
		this.sterilizationCode = sterilizationCode;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public void setExpdtEndDate(Date expdtEndDate) {
		this.expdtEndDate = expdtEndDate;
	}

	public void setSterilizationInvalidDate(Date sterilizationInvalidDate) {
		this.sterilizationInvalidDate = sterilizationInvalidDate;
	}

	public void setSterilizationDate(Date sterilizationDate) {
		this.sterilizationDate = sterilizationDate;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public void setPurType(String purType) {
		this.purType = purType;
	}

	public void setPurCompany(String purCompany) {
		this.purCompany = purCompany;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public void setProductKind(String productKind) {
		this.productKind = productKind;
	}

	public void setProductSpecialKind(String productSpecialKind) {
		this.productSpecialKind = productSpecialKind;
	}

	public void setProductSubKind(String productSubKind) {
		this.productSubKind = productSubKind;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public void setMrfsName(String mrfsName) {
		this.mrfsName = mrfsName;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	public void setJgh(String jgh) {
		this.jgh = jgh;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public void setProvErpCode(String provErpCode) {
		this.provErpCode = provErpCode;
	}

	public void setHosCompanyName(String hosCompanyName) {
		this.hosCompanyName = hosCompanyName;
	}

	public void setHosCompanyErpCode(String hosCompanyErpCode) {
		this.hosCompanyErpCode = hosCompanyErpCode;
	}

	public void setApplyOrReturnOrgName(String applyOrReturnOrgName) {
		this.applyOrReturnOrgName = applyOrReturnOrgName;
	}

	public void setApplyOrReturnOrgErpCode(String applyOrReturnOrgErpCode) {
		this.applyOrReturnOrgErpCode = applyOrReturnOrgErpCode;
	}

	public void setInOrOutOrgName(String inOrOutOrgName) {
		this.inOrOutOrgName = inOrOutOrgName;
	}

	public void setInOrOutOrgErpCode(String inOrOutOrgErpCode) {
		this.inOrOutOrgErpCode = inOrOutOrgErpCode;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public void setPurBillId(String purBillId) {
		this.purBillId = purBillId;
	}

	public void setApplyBillId(String applyBillId) {
		this.applyBillId = applyBillId;
	}

	public void setProvGoodsErpCode(String provGoodsErpCode) {
		this.provGoodsErpCode = provGoodsErpCode;
	}

	public void setProvGoodsErpName(String provGoodsErpName) {
		this.provGoodsErpName = provGoodsErpName;
	}

	@Override
	public String toString() {
		return "LedgerResponse [guid=" + guid + ", rowNum=" + rowNum + ", provGoodsId=" + provGoodsId + ", stoc=" + stoc
				+ ", formId=" + formId + ", formType=" + formType + ", company=" + company + ", dailyNum=" + dailyNum
				+ ", stocShelf=" + stocShelf + ", batchNum=" + batchNum + ", unit=" + unit + ", unitExplain="
				+ unitExplain + ", saleNum=" + saleNum + ", batchCode=" + batchCode + ", sterilizationCode="
				+ sterilizationCode + ", productDate=" + productDate + ", expdtEndDate=" + expdtEndDate
				+ ", sterilizationInvalidDate=" + sterilizationInvalidDate + ", sterilizationDate=" + sterilizationDate
				+ ", billId=" + billId + ", purType=" + purType + ", purCompany=" + purCompany + ", rowNumber="
				+ rowNumber + ", productKind=" + productKind + ", productSpecialKind=" + productSpecialKind
				+ ", productSubKind=" + productSubKind + ", disable=" + disable + ", mrfsName=" + mrfsName
				+ ", certificateCode=" + certificateCode + ", jgh=" + jgh + ", provName=" + provName + ", provErpCode="
				+ provErpCode + ", hosCompanyName=" + hosCompanyName + ", hosCompanyErpCode=" + hosCompanyErpCode
				+ ", applyOrReturnOrgName=" + applyOrReturnOrgName + ", applyOrReturnOrgErpCode="
				+ applyOrReturnOrgErpCode + ", inOrOutOrgName=" + inOrOutOrgName + ", inOrOutOrgErpCode="
				+ inOrOutOrgErpCode + ", saleType=" + saleType + ", purBillId=" + purBillId + ", applyBillId="
				+ applyBillId + ", provGoodsErpCode=" + provGoodsErpCode + ", provGoodsErpName=" + provGoodsErpName
				+ "]";
	}

}
