package gyqx.spdhdi.uploadData.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 采购确认单明细
 * 
 * @date 2017年9月15日 下午4:19:10
 */
public class PurconfirmListJson {
	@JSONField(name = "采购订单行号")
	private Integer rowNumber;
	@JSONField(name = "医疗机构申请单号")
	private String purBillId;
	@JSONField(name = "科室申请单号")
	private String applyBillId;

	@JSONField(name = "指定供应商ERP编号")
	private String provErpCode;
	@JSONField(name = "指定供应商ERP名称")
	private String provName;

	@JSONField(name = "医药医材编号")
	private String hosGoodsCode;
	@JSONField(name = "医药医材ERP编号")
	private String provGoodsErpCode;
	@JSONField(name = "医药医材名称")
	private String provGoodsErpName;

	@JSONField(name = "采购订单日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date applyDate;

	@JSONField(name = "规格")
	private String goodsGg;
	@JSONField(name = "生产厂商")
	private String mrfsName;
	@JSONField(name = "产地")
	private String made;
	@JSONField(name = "单位")
	private String unit;
	@JSONField(name = "包装")
	private BigDecimal packeage;
	@JSONField(name = "单价")
	private BigDecimal provGoodsErpPrice;
	@JSONField(name = "数量")
	private BigDecimal sendQty; // 要货数量
	@JSONField(name = "预计到货日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date esArrivalDate;

	public Integer getRowNumber() {
		return rowNumber;
	}

	public String getPurBillId() {
		return purBillId;
	}

	public String getApplyBillId() {
		return applyBillId;
	}

	public String getProvErpCode() {
		return provErpCode;
	}

	public String getProvName() {
		return provName;
	}

	public String getHosGoodsCode() {
		return hosGoodsCode;
	}

	public String getProvGoodsErpCode() {
		return provGoodsErpCode;
	}

	public String getProvGoodsErpName() {
		return provGoodsErpName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public String getGoodsGg() {
		return goodsGg;
	}

	public String getMrfsName() {
		return mrfsName;
	}

	public String getMade() {
		return made;
	}

	public String getUnit() {
		return unit;
	}

	public BigDecimal getPackeage() {
		return packeage;
	}

	public BigDecimal getProvGoodsErpPrice() {
		return provGoodsErpPrice;
	}

	public BigDecimal getSendQty() {
		return sendQty;
	}

	public Date getEsArrivalDate() {
		return esArrivalDate;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public void setPurBillId(String purBillId) {
		this.purBillId = purBillId;
	}

	public void setApplyBillId(String applyBillId) {
		this.applyBillId = applyBillId;
	}

	public void setProvErpCode(String provErpCode) {
		this.provErpCode = provErpCode;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public void setHosGoodsCode(String hosGoodsCode) {
		this.hosGoodsCode = hosGoodsCode;
	}

	public void setProvGoodsErpCode(String provGoodsErpCode) {
		this.provGoodsErpCode = provGoodsErpCode;
	}

	public void setProvGoodsErpName(String provGoodsErpName) {
		this.provGoodsErpName = provGoodsErpName;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public void setGoodsGg(String goodsGg) {
		this.goodsGg = goodsGg;
	}

	public void setMrfsName(String mrfsName) {
		this.mrfsName = mrfsName;
	}

	public void setMade(String made) {
		this.made = made;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setPackeage(BigDecimal packeage) {
		this.packeage = packeage;
	}

	public void setProvGoodsErpPrice(BigDecimal provGoodsErpPrice) {
		this.provGoodsErpPrice = provGoodsErpPrice;
	}

	public void setSendQty(BigDecimal sendQty) {
		this.sendQty = sendQty;
	}

	public void setEsArrivalDate(Date esArrivalDate) {
		this.esArrivalDate = esArrivalDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurconfirmListJson [rowNumber=");
		builder.append(rowNumber);
		builder.append(", purBillId=");
		builder.append(purBillId);
		builder.append(", applyBillId=");
		builder.append(applyBillId);
		builder.append(", provErpCode=");
		builder.append(provErpCode);
		builder.append(", provName=");
		builder.append(provName);
		builder.append(", hosGoodsCode=");
		builder.append(hosGoodsCode);
		builder.append(", provGoodsErpCode=");
		builder.append(provGoodsErpCode);
		builder.append(", provGoodsErpName=");
		builder.append(provGoodsErpName);
		builder.append(", applyDate=");
		builder.append(applyDate);
		builder.append(", goodsGg=");
		builder.append(goodsGg);
		builder.append(", mrfsName=");
		builder.append(mrfsName);
		builder.append(", made=");
		builder.append(made);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", packeage=");
		builder.append(packeage);
		builder.append(", provGoodsErpPrice=");
		builder.append(provGoodsErpPrice);
		builder.append(", sendQty=");
		builder.append(sendQty);
		builder.append(", esArrivalDate=");
		builder.append(esArrivalDate);
		builder.append("]");
		return builder.toString();
	}

}
