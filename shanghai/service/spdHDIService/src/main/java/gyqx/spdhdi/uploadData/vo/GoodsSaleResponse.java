package gyqx.spdhdi.uploadData.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商品销售明细查询<br>
 * 返回数据
 * 
 * @date 2017年9月13日 下午3:20:35
 */
public class GoodsSaleResponse {
	@JSONField(name = "GUID")
	private String guid;

	@JSONField(name = "行号")
	private int rowNum;

	@JSONField(name = "订单公司")
	private String purCompany;

	@JSONField(name = "JDE订单号")
	private BigDecimal jdeBillId;

	@JSONField(name = "JDE订单类型")
	private String jdePurType;

	@JSONField(name = "JDE行号")
	private BigDecimal jdeRowNumber;

	// @JSONField(name = "状态码")
	// private String statuCode;
	//
	// @JSONField(name = "单据状态")
	// private String billStatu;

	@JSONField(name = "第二项目号")
	private String secondProNo;

	@JSONField(name = "商品名称")
	private String provGoodsName;

	// **
	@JSONField(name = "规格型号")
	private String ggCode;

	@JSONField(name = "订单日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date purBillDate;

	@JSONField(name = "实际发运日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryDate;

	@JSONField(name = "计量单位")
	private String unit;

	@JSONField(name = "计量单位名称")
	private String unitName;

	@JSONField(name = "订购数量")
	private BigDecimal purNum;

	@JSONField(name = "发运数量")
	private BigDecimal deliveryNum;

	@JSONField(name = "延交数量")
	private BigDecimal delayNum;

	@JSONField(name = "取消数量")
	private BigDecimal cancelNum;

	@JSONField(name = "含税单价")
	private BigDecimal qrPrice;

	@JSONField(name = "含税总价")
	private BigDecimal qrAmount;

	@JSONField(name = "单位成本")
	private BigDecimal unitCost;

	@JSONField(name = "总成本")
	private BigDecimal totalAmount;

	@JSONField(name = "客户号")
	private BigDecimal customerNum;

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

	@JSONField(name = "规格")
	private String goodsGg;

	@JSONField(name = "生产厂商")
	private String mrfsName;

	@JSONField(name = "医疗器械注册证号")
	private String provRegistId;

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

	@JSONField(name = "条码")
	private String barcode;

	// **
	@JSONField(name = "医疗器械医院内部编号")
	private String hosGoodsId;

	// **
	@JSONField(name = "医院内部名称")
	private String hosGoodsName;

	@JSONField(name = "院方计量单位")
	private String hosUnitQty;

	@JSONField(name = "院方数量")
	private BigDecimal hosQty;

	@JSONField(name = "院方单价")
	private BigDecimal hosPrice;

	// **
	@JSONField(name = "院方金额")
	private BigDecimal hosAmount;

	@JSONField(name = "是否唯一码管理")
	private String uniqueCode;

	// @JSONField(name = "储存条件")
	// private String stocCondition;

	@JSONField(name = "产品大类")
	private String productKind;

	@JSONField(name = "特殊分类")
	private String productSpecialKind;

	@JSONField(name = "产品分类")
	private String productSubKind;

	@JSONField(name = "备注")
	private String remark;

	public String getGuid() {
		return guid;
	}

	public int getRowNum() {
		return rowNum;
	}

	public String getPurCompany() {
		return purCompany;
	}

	public BigDecimal getJdeBillId() {
		return jdeBillId;
	}

	public String getJdePurType() {
		return jdePurType;
	}

	public BigDecimal getJdeRowNumber() {
		return jdeRowNumber;
	}

	public String getSecondProNo() {
		return secondProNo;
	}

	public String getProvGoodsName() {
		return provGoodsName;
	}

	public String getGgCode() {
		return ggCode;
	}

	public Date getPurBillDate() {
		return purBillDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public String getUnit() {
		return unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public BigDecimal getPurNum() {
		return purNum;
	}

	public BigDecimal getDeliveryNum() {
		return deliveryNum;
	}

	public BigDecimal getDelayNum() {
		return delayNum;
	}

	public BigDecimal getCancelNum() {
		return cancelNum;
	}

	public BigDecimal getQrPrice() {
		return qrPrice;
	}

	public BigDecimal getQrAmount() {
		return qrAmount;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public BigDecimal getCustomerNum() {
		return customerNum;
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

	public String getGoodsGg() {
		return goodsGg;
	}

	public String getMrfsName() {
		return mrfsName;
	}

	public String getProvRegistId() {
		return provRegistId;
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

	public String getBarcode() {
		return barcode;
	}

	public String getHosGoodsId() {
		return hosGoodsId;
	}

	public String getHosGoodsName() {
		return hosGoodsName;
	}

	public String getHosUnitQty() {
		return hosUnitQty;
	}

	public BigDecimal getHosQty() {
		return hosQty;
	}

	public BigDecimal getHosPrice() {
		return hosPrice;
	}

	public BigDecimal getHosAmount() {
		return hosAmount;
	}

	public String getUniqueCode() {
		return uniqueCode;
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

	public String getRemark() {
		return remark;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void setPurCompany(String purCompany) {
		this.purCompany = purCompany;
	}

	public void setJdeBillId(BigDecimal jdeBillId) {
		this.jdeBillId = jdeBillId;
	}

	public void setJdePurType(String jdePurType) {
		this.jdePurType = jdePurType;
	}

	public void setJdeRowNumber(BigDecimal jdeRowNumber) {
		this.jdeRowNumber = jdeRowNumber;
	}

	public void setSecondProNo(String secondProNo) {
		this.secondProNo = secondProNo;
	}

	public void setProvGoodsName(String provGoodsName) {
		this.provGoodsName = provGoodsName;
	}

	public void setGgCode(String ggCode) {
		this.ggCode = ggCode;
	}

	public void setPurBillDate(Date purBillDate) {
		this.purBillDate = purBillDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setPurNum(BigDecimal purNum) {
		this.purNum = purNum;
	}

	public void setDeliveryNum(BigDecimal deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public void setDelayNum(BigDecimal delayNum) {
		this.delayNum = delayNum;
	}

	public void setCancelNum(BigDecimal cancelNum) {
		this.cancelNum = cancelNum;
	}

	public void setQrPrice(BigDecimal qrPrice) {
		this.qrPrice = qrPrice;
	}

	public void setQrAmount(BigDecimal qrAmount) {
		this.qrAmount = qrAmount;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setCustomerNum(BigDecimal customerNum) {
		this.customerNum = customerNum;
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

	public void setGoodsGg(String goodsGg) {
		this.goodsGg = goodsGg;
	}

	public void setMrfsName(String mrfsName) {
		this.mrfsName = mrfsName;
	}

	public void setProvRegistId(String provRegistId) {
		this.provRegistId = provRegistId;
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

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setHosGoodsId(String hosGoodsId) {
		this.hosGoodsId = hosGoodsId;
	}

	public void setHosGoodsName(String hosGoodsName) {
		this.hosGoodsName = hosGoodsName;
	}

	public void setHosUnitQty(String hosUnitQty) {
		this.hosUnitQty = hosUnitQty;
	}

	public void setHosQty(BigDecimal hosQty) {
		this.hosQty = hosQty;
	}

	public void setHosPrice(BigDecimal hosPrice) {
		this.hosPrice = hosPrice;
	}

	public void setHosAmount(BigDecimal hosAmount) {
		this.hosAmount = hosAmount;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "GoodsSaleResponse [guid=" + guid + ", rowNum=" + rowNum + ", purCompany=" + purCompany + ", jdeBillId="
				+ jdeBillId + ", jdePurType=" + jdePurType + ", jdeRowNumber=" + jdeRowNumber + ", secondProNo="
				+ secondProNo + ", provGoodsName=" + provGoodsName + ", ggCode=" + ggCode + ", purBillDate="
				+ purBillDate + ", deliveryDate=" + deliveryDate + ", unit=" + unit + ", unitName=" + unitName
				+ ", purNum=" + purNum + ", deliveryNum=" + deliveryNum + ", delayNum=" + delayNum + ", cancelNum="
				+ cancelNum + ", qrPrice=" + qrPrice + ", qrAmount=" + qrAmount + ", unitCost=" + unitCost
				+ ", totalAmount=" + totalAmount + ", customerNum=" + customerNum + ", provName=" + provName
				+ ", provErpCode=" + provErpCode + ", hosCompanyName=" + hosCompanyName + ", hosCompanyErpCode="
				+ hosCompanyErpCode + ", applyOrReturnOrgName=" + applyOrReturnOrgName + ", applyOrReturnOrgErpCode="
				+ applyOrReturnOrgErpCode + ", inOrOutOrgName=" + inOrOutOrgName + ", inOrOutOrgErpCode="
				+ inOrOutOrgErpCode + ", saleType=" + saleType + ", purBillId=" + purBillId + ", applyBillId="
				+ applyBillId + ", provGoodsErpCode=" + provGoodsErpCode + ", provGoodsErpName=" + provGoodsErpName
				+ ", goodsGg=" + goodsGg + ", mrfsName=" + mrfsName + ", provRegistId=" + provRegistId + ", batchCode="
				+ batchCode + ", sterilizationCode=" + sterilizationCode + ", productDate=" + productDate
				+ ", expdtEndDate=" + expdtEndDate + ", sterilizationInvalidDate=" + sterilizationInvalidDate
				+ ", sterilizationDate=" + sterilizationDate + ", barcode=" + barcode + ", hosGoodsId=" + hosGoodsId
				+ ", hosGoodsName=" + hosGoodsName + ", hosUnitQty=" + hosUnitQty + ", hosQty=" + hosQty + ", hosPrice="
				+ hosPrice + ", hosAmount=" + hosAmount + ", uniqueCode=" + uniqueCode + ", productKind=" + productKind
				+ ", productSpecialKind=" + productSpecialKind + ", productSubKind=" + productSubKind + ", remark="
				+ remark + "]";
	}

}
