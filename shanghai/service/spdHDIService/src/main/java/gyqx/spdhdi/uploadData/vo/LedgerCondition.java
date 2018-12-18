package gyqx.spdhdi.uploadData.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 应急库分类账查询 <br>
 * 传入数据<br>
 * 其中GUID、 医疗机构、医疗机构ERP编号、SPD系统标识、配送机构、配送机构ERP编号、配送机构系统标识、开始行和截止行字段必须有值
 * 
 * @date 2017年9月13日 下午4:41:49
 */
public class LedgerCondition {
	@JSONField(name = "GUID")
	private String guid;

	@JSONField(name = "SPD系统标识")
	private String spdCode;

	@JSONField(name = "医疗机构")
	private String hosCompanyId;

	@JSONField(name = "医疗机构ERP编号")
	private String hosCompanyErpCode;

	@JSONField(name = "配送机构")
	private String provId;

	@JSONField(name = "配送机构ERP编号")
	private String provErpCode;

	@JSONField(name = "配送机构系统标识")
	private String provSystemId;

	// 可空
	@JSONField(name = "商品号")
	private String provGoodsId;

	@JSONField(name = "仓库")
	private String stoc;

	@JSONField(name = "库位")
	private String stocShelf;

	@JSONField(name = "批次")
	private String batchNum;

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

	// 不可空
	@JSONField(name = "起始行")
	private int startRowNum;

	@JSONField(name = "截止行")
	private int endRowNum;

	public String getGuid() {
		return guid;
	}

	public String getSpdCode() {
		return spdCode;
	}

	public String getHosCompanyId() {
		return hosCompanyId;
	}

	public String getHosCompanyErpCode() {
		return hosCompanyErpCode;
	}

	public String getProvId() {
		return provId;
	}

	public String getProvErpCode() {
		return provErpCode;
	}

	public String getProvSystemId() {
		return provSystemId;
	}

	public String getProvGoodsId() {
		return provGoodsId;
	}

	public String getStoc() {
		return stoc;
	}

	public String getStocShelf() {
		return stocShelf;
	}

	public String getBatchNum() {
		return batchNum;
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

	public int getStartRowNum() {
		return startRowNum;
	}

	public int getEndRowNum() {
		return endRowNum;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public void setSpdCode(String spdCode) {
		this.spdCode = spdCode;
	}

	public void setHosCompanyId(String hosCompanyId) {
		this.hosCompanyId = hosCompanyId;
	}

	public void setHosCompanyErpCode(String hosCompanyErpCode) {
		this.hosCompanyErpCode = hosCompanyErpCode;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public void setProvErpCode(String provErpCode) {
		this.provErpCode = provErpCode;
	}

	public void setProvSystemId(String provSystemId) {
		this.provSystemId = provSystemId;
	}

	public void setProvGoodsId(String provGoodsId) {
		this.provGoodsId = provGoodsId;
	}

	public void setStoc(String stoc) {
		this.stoc = stoc;
	}

	public void setStocShelf(String stocShelf) {
		this.stocShelf = stocShelf;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
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

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}

	@Override
	public String toString() {
		return "LedgerCondition [guid=" + guid + ", spdCode=" + spdCode + ", hosCompanyId=" + hosCompanyId
				+ ", hosCompanyErpCode=" + hosCompanyErpCode + ", provId=" + provId + ", provErpCode=" + provErpCode
				+ ", provSystemId=" + provSystemId + ", provGoodsId=" + provGoodsId + ", stoc=" + stoc + ", stocShelf="
				+ stocShelf + ", batchNum=" + batchNum + ", billId=" + billId + ", purType=" + purType + ", purCompany="
				+ purCompany + ", rowNumber=" + rowNumber + ", productKind=" + productKind + ", productSpecialKind="
				+ productSpecialKind + ", productSubKind=" + productSubKind + ", disable=" + disable + ", mrfsName="
				+ mrfsName + ", certificateCode=" + certificateCode + ", startRowNum=" + startRowNum + ", endRowNum="
				+ endRowNum + "]";
	}

}
