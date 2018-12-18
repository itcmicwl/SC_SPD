package gyqx.spdhdi.uploadData.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 退货确认单明细
 * 
 * @date 2017年9月15日 下午4:49:29
 */
public class ReturnconfirmListJson {
	@JSONField(name = "退货单行号")
	private Integer rowNumber;

	@JSONField(name = "原医院请购单号")
	private String purBillId; // 可空
	@JSONField(name = "原科室请购单号")
	private String applyBillId; // 可空

	@JSONField(name = "医药医材编号")
	private String hosGoodsId;
	@JSONField(name = "医药医材ERP编号")
	private String provGoodsErpCode;
	@JSONField(name = "医药医材名称")
	private String provGoodsErpName;

	@JSONField(name = "规格")
	private String goodsGg;
	@JSONField(name = "生产厂商")
	private String mrfsName;
	@JSONField(name = "产地") // 可空
	private String made;
	@JSONField(name = "单位")
	private String unit;

	@JSONField(name = "批号")
	private String batchCode;
	@JSONField(name = "灭菌批号")
	private String sterilizationCode;

	@JSONField(name = "库存类型")
	private Integer stockType; // **库存类型** 请购/借货/跟台手术

	@JSONField(name = "有效期至", format = "yyyy-MM-dd HH:mm:ss")
	private Date expdtEndDate;
	@JSONField(name = "生产日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date productDate; // 可空

	@JSONField(name = "包装")
	private java.math.BigDecimal packeage;
	@JSONField(name = "数量")
	private java.math.BigDecimal sendQty;
	@JSONField(name = "单价")
	private java.math.BigDecimal sendPrice;

	@JSONField(name = "入库类型")
	private Integer outKind; // **入库类型** 0-入正常库 1-入医院备用库

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getPurBillId() {
		return purBillId;
	}

	public void setPurBillId(String purBillId) {
		this.purBillId = purBillId;
	}

	public String getApplyBillId() {
		return applyBillId;
	}

	public void setApplyBillId(String applyBillId) {
		this.applyBillId = applyBillId;
	}

	public String getHosGoodsId() {
		return hosGoodsId;
	}

	public void setHosGoodsId(String hosGoodsId) {
		this.hosGoodsId = hosGoodsId;
	}

	public String getProvGoodsErpCode() {
		return provGoodsErpCode;
	}

	public void setProvGoodsErpCode(String provGoodsErpCode) {
		this.provGoodsErpCode = provGoodsErpCode;
	}

	public String getProvGoodsErpName() {
		return provGoodsErpName;
	}

	public void setProvGoodsErpName(String provGoodsErpName) {
		this.provGoodsErpName = provGoodsErpName;
	}

	public String getGoodsGg() {
		return goodsGg;
	}

	public void setGoodsGg(String goodsGg) {
		this.goodsGg = goodsGg;
	}

	public String getMrfsName() {
		return mrfsName;
	}

	public void setMrfsName(String mrfsName) {
		this.mrfsName = mrfsName;
	}

	public String getMade() {
		return made;
	}

	public void setMade(String made) {
		this.made = made;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getSterilizationCode() {
		return sterilizationCode;
	}

	public void setSterilizationCode(String sterilizationCode) {
		this.sterilizationCode = sterilizationCode;
	}

	public Integer getStockType() {
		return stockType;
	}

	public void setStockType(Integer stockType) {
		this.stockType = stockType;
	}

	public Date getExpdtEndDate() {
		return expdtEndDate;
	}

	public void setExpdtEndDate(Date expdtEndDate) {
		this.expdtEndDate = expdtEndDate;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public java.math.BigDecimal getPackeage() {
		return packeage;
	}

	public void setPackeage(java.math.BigDecimal packeage) {
		this.packeage = packeage;
	}

	public java.math.BigDecimal getSendQty() {
		return sendQty;
	}

	public void setSendQty(java.math.BigDecimal sendQty) {
		this.sendQty = sendQty;
	}

	public java.math.BigDecimal getSendPrice() {
		return sendPrice;
	}

	public void setSendPrice(java.math.BigDecimal sendPrice) {
		this.sendPrice = sendPrice;
	}

	public Integer getOutKind() {
		return outKind;
	}

	public void setOutKind(Integer outKind) {
		this.outKind = outKind;
	}

	@Override
	public String toString() {
		return "ReturnconfirmListJson [rowNumber=" + rowNumber + ", purBillId=" + purBillId + ", applyBillId="
				+ applyBillId + ", hosGoodsId=" + hosGoodsId + ", provGoodsErpCode=" + provGoodsErpCode
				+ ", provGoodsErpName=" + provGoodsErpName + ", goodsGg=" + goodsGg + ", mrfsName=" + mrfsName
				+ ", made=" + made + ", unit=" + unit + ", batchCode=" + batchCode + ", sterilizationCode="
				+ sterilizationCode + ", stockType=" + stockType + ", expdtEndDate=" + expdtEndDate + ", productDate="
				+ productDate + ", packeage=" + packeage + ", sendQty=" + sendQty + ", sendPrice=" + sendPrice
				+ ", outKind=" + outKind + "]";
	}

}
