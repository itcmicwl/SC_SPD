package gyqx.spdhdi.uploadData.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 应急库库存查询 <br>
 * 传入数据 <br>
 * 其中GUID、医疗机构、医疗机构ERP编号、SPD系统标识、配送机构、配送机构ERP编号、配送机构系统标识、开始行和截止行字段必须有值
 * 
 * @date 2017年9月13日 下午6:21:22
 */
public class EmergencyStocCondition {
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
		return "EmergencyStocCondition [guid=" + guid + ", spdCode=" + spdCode + ", hosCompanyId=" + hosCompanyId
				+ ", hosCompanyErpCode=" + hosCompanyErpCode + ", provId=" + provId + ", provErpCode=" + provErpCode
				+ ", provSystemId=" + provSystemId + ", jdeGoodsId=" + jdeGoodsId + ", provGoodsName=" + provGoodsName
				+ ", goodsGg=" + goodsGg + ", erpStocCode=" + erpStocCode + ", stoc=" + stoc + ", company=" + company
				+ ", companyName=" + companyName + ", stocShelf=" + stocShelf + ", jdeBatchNum=" + jdeBatchNum
				+ ", batchCode=" + batchCode + ", sterilizationCode=" + sterilizationCode + ", productKind="
				+ productKind + ", productSpecialKind=" + productSpecialKind + ", productSubKind=" + productSubKind
				+ ", disable=" + disable + ", mrfsName=" + mrfsName + ", certificateCode=" + certificateCode
				+ ", startRowNum=" + startRowNum + ", endRowNum=" + endRowNum + "]";
	}

	
}
