package gyqx.spdhdi.uploadData.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 应急库库存查询 <br>
 * 返回数据
 * 
 * @date 2017年9月13日 下午6:22:08
 */
public class EmergencyStocResponse {

	@JSONField(name = "GUID")
	private String guid;

	@JSONField(name = "行号")
	private int rowNum;

	@JSONField(name = "JDE短项目号")
	private BigDecimal jdeItemId;

	@JSONField(name = "JDE商品号")
	private String jdeGoodsId;

	@JSONField(name = "商品名称")
	private String provGoodsName;

	@JSONField(name = "规格型号")
	private String goodsGg;

	@JSONField(name = "ERP仓库编码")
	private String erpStocCode;

	@JSONField(name = "仓库名")
	private String stoc;

	@JSONField(name = "公司")
	private String company;

	@JSONField(name = "公司名")
	private String companyName;

	@JSONField(name = "库位")
	private String stocShelf;

	@JSONField(name = "JDE批次")
	private String jdeBatchNum;

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

	@JSONField(name = "现有量")
	private BigDecimal presentNum;

	@JSONField(name = "承诺量")
	private BigDecimal promiseNum;

	@JSONField(name = "可供量")
	private BigDecimal availableNum;

	@JSONField(name = "计量单位编码")
	private String unitCode;

	@JSONField(name = "计量单位")
	private String unit;

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

	public String getGuid() {
		return guid;
	}

	public int getRowNum() {
		return rowNum;
	}

	public BigDecimal getJdeItemId() {
		return jdeItemId;
	}

	public String getJdeGoodsId() {
		return jdeGoodsId;
	}

	public String getProvGoodsName() {
		return provGoodsName;
	}

	public String getGoodsGg() {
		return goodsGg;
	}

	public String getErpStocCode() {
		return erpStocCode;
	}

	public String getStoc() {
		return stoc;
	}

	public String getCompany() {
		return company;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getStocShelf() {
		return stocShelf;
	}

	public String getJdeBatchNum() {
		return jdeBatchNum;
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

	public BigDecimal getPresentNum() {
		return presentNum;
	}

	public BigDecimal getPromiseNum() {
		return promiseNum;
	}

	public BigDecimal getAvailableNum() {
		return availableNum;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public String getUnit() {
		return unit;
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

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void setJdeItemId(BigDecimal jdeItemId) {
		this.jdeItemId = jdeItemId;
	}

	public void setJdeGoodsId(String jdeGoodsId) {
		this.jdeGoodsId = jdeGoodsId;
	}

	public void setProvGoodsName(String provGoodsName) {
		this.provGoodsName = provGoodsName;
	}

	public void setGoodsGg(String goodsGg) {
		this.goodsGg = goodsGg;
	}

	public void setErpStocCode(String erpStocCode) {
		this.erpStocCode = erpStocCode;
	}

	public void setStoc(String stoc) {
		this.stoc = stoc;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setStocShelf(String stocShelf) {
		this.stocShelf = stocShelf;
	}

	public void setJdeBatchNum(String jdeBatchNum) {
		this.jdeBatchNum = jdeBatchNum;
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

	public void setPresentNum(BigDecimal presentNum) {
		this.presentNum = presentNum;
	}

	public void setPromiseNum(BigDecimal promiseNum) {
		this.promiseNum = promiseNum;
	}

	public void setAvailableNum(BigDecimal availableNum) {
		this.availableNum = availableNum;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	@Override
	public String toString() {
		return "EmergencyStocResponse [guid=" + guid + ", rowNum=" + rowNum + ", jdeItemId=" + jdeItemId
				+ ", jdeGoodsId=" + jdeGoodsId + ", provGoodsName=" + provGoodsName + ", goodsGg=" + goodsGg
				+ ", erpStocCode=" + erpStocCode + ", stoc=" + stoc + ", company=" + company + ", companyName="
				+ companyName + ", stocShelf=" + stocShelf + ", jdeBatchNum=" + jdeBatchNum + ", batchCode=" + batchCode
				+ ", sterilizationCode=" + sterilizationCode + ", productDate=" + productDate + ", expdtEndDate="
				+ expdtEndDate + ", sterilizationInvalidDate=" + sterilizationInvalidDate + ", sterilizationDate="
				+ sterilizationDate + ", presentNum=" + presentNum + ", promiseNum=" + promiseNum + ", availableNum="
				+ availableNum + ", unitCode=" + unitCode + ", unit=" + unit + ", productKind=" + productKind
				+ ", productSpecialKind=" + productSpecialKind + ", productSubKind=" + productSubKind + ", disable="
				+ disable + ", mrfsName=" + mrfsName + ", certificateCode=" + certificateCode + ", jgh=" + jgh + "]";
	}

}
