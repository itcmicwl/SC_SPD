package gyqx.spdhdi.uploadData.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * SPD结算接口表结算明细
 * 
 * @author LIWENKANG
 *指定供应商单位、定供应商ERP编码、指定供应商名称
 */
public class BalanceDetailJson {

	/*@JSONField(name = "配送单位")
	private String provId;
	@JSONField(name = "配送单位名称")
	private String provName;*/
	
	@JSONField(name = "指定供应商单位")
	private String subProvId;
	@JSONField(name = "指定供应商ERP编号")
	private String subProvErpCode;
	@JSONField(name = "指定供应商名称")
	private String subProvName;
	/**
	 * 销售单价（含税）,
	 * 采购单价（含税），
	 * 供应商ERP编号，SPD结算单号,启运温度，启运日期,启运时间,到达温度,到达日期,到达时间,货运方式
	 *  HH:mm:ss
	 */
	@JSONField(name = "供应商ERP编号")
	private String provErpCode;
	@JSONField(name = "采购单价")
	private BigDecimal purchasePrice;
	@JSONField(name = "销售单价")
	private BigDecimal salePrice;
	@JSONField(name = "到达日期",format = "yyyy-MM-dd")
    private Date receiveDate;
	@JSONField(name = "到达时间",format = "HH:mm:ss")
    private Date receiveTime;
	@JSONField(name = "启运日期",format = "yyyy-MM-dd")
    private Date deliveryDate;
	@JSONField(name = "启运时间",format = "HH:mm:ss")
    private Date deliveryTime;
	@JSONField(name = "启运温度")
    private BigDecimal sendTemprature;
	@JSONField(name = "到达温度")
    private BigDecimal receiveTemprature;
	@JSONField(name = "货运方式")
    private String transportType;
	
	@JSONField(name = "出库科室编号")
	private String outDeptId;
	@JSONField(name = "出库科室ERP编号")
	private String outDeptErpCode;
	@JSONField(name = "出库科室名称")
	private String outDeptName;
	@JSONField(name = "出库单号")
	private String outBillId;
	@JSONField(name = "出库日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date outDate;
	@JSONField(name = "数据类型")
	private Integer numKind; // 0 -- 表示结算机构自己的结算数据 1 -- 表示合作供应商需要通过结算供应商进行结算数据
	@JSONField(name = "出库类型")
	private Integer outKind; // 0 -- 正常出库 1 -- 出库退回 2 -- 报损出库 3 -- 其他出库
	@JSONField(name = "出库单行号")
	private Integer outRowNum;

	@JSONField(name = "医药医材编号")
	private String provGoodsId;
	@JSONField(name = "医药医材ERP编号")
	private String provGoodsErpCode;
	@JSONField(name = "医药医材名称")
	private String provGoodsErpName;

	@JSONField(name = "规格")
	private String goodsGg;
	@JSONField(name = "生产厂商")
	private String mfrsName;
	@JSONField(name = "产地")
	private String made; // 可空
	@JSONField(name = "单位")
	private String unit;
	@JSONField(name = "批号")
	private String batchCode;
	@JSONField(name = "灭菌批号")
	private String sterilizationCode;
	@JSONField(name = "库存类型")
	private String stockType; // 请购/借货/跟台手术
	@JSONField(name = "有效期至", format = "yyyy-MM-dd")
	private Date expdtEndDate;
	@JSONField(name = "生产日期", format = "yyyy-MM-dd")
	private Date produceDatetime; // 可空
	@JSONField(name = "灭菌日期", format = "yyyy-MM-dd")
	private Date sterilizationDate;
	@JSONField(name = "灭菌批号有效期至", format = "yyyy-MM-dd")
	private Date sterilizationEndDate; // 可空
	@JSONField(name = "包装")
	private BigDecimal packeage;
	@JSONField(name = "数量")
	private BigDecimal qty;

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getSubProvId() {
		return subProvId;
	}

	public void setSubProvId(String subProvId) {
		this.subProvId = subProvId;
	}

	public String getSubProvErpCode() {
		return subProvErpCode;
	}

	public void setSubProvErpCode(String subProvErpCode) {
		this.subProvErpCode = subProvErpCode;
	}

	public String getSubProvName() {
		return subProvName;
	}

	public void setSubProvName(String subProvName) {
		this.subProvName = subProvName;
	}
	public String getProvErpCode() {
		return provErpCode;
	}

	public void setProvErpCode(String provErpCode) {
		this.provErpCode = provErpCode;
	}
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public BigDecimal getSendTemprature() {
		return sendTemprature;
	}

	public void setSendTemprature(BigDecimal sendTemprature) {
		this.sendTemprature = sendTemprature;
	}

	public BigDecimal getReceiveTemprature() {
		return receiveTemprature;
	}

	public void setReceiveTemprature(BigDecimal receiveTemprature) {
		this.receiveTemprature = receiveTemprature;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	/*public String getProvId() {
		return provId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}
*/
	public String getOutDeptId() {
		return outDeptId;
	}

	public void setOutDeptId(String outDeptId) {
		this.outDeptId = outDeptId;
	}

	public String getOutDeptErpCode() {
		return outDeptErpCode;
	}

	public void setOutDeptErpCode(String outDeptErpCode) {
		this.outDeptErpCode = outDeptErpCode;
	}

	public String getOutDeptName() {
		return outDeptName;
	}

	public void setOutDeptName(String outDeptName) {
		this.outDeptName = outDeptName;
	}

	public String getOutBillId() {
		return outBillId;
	}

	public void setOutBillId(String outBillId) {
		this.outBillId = outBillId;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public Integer getNumKind() {
		return numKind;
	}

	public void setNumKind(Integer numKind) {
		this.numKind = numKind;
	}

	public Integer getOutKind() {
		return outKind;
	}

	public void setOutKind(Integer outKind) {
		this.outKind = outKind;
	}

	public Integer getOutRowNum() {
		return outRowNum;
	}

	public void setOutRowNum(Integer outRowNum) {
		this.outRowNum = outRowNum;
	}

	public String getProvGoodsId() {
		return provGoodsId;
	}

	public void setProvGoodsId(String provGoodsId) {
		this.provGoodsId = provGoodsId;
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

	public String getMfrsName() {
		return mfrsName;
	}

	public void setMfrsName(String mfrsName) {
		this.mfrsName = mfrsName;
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

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public Date getExpdtEndDate() {
		return expdtEndDate;
	}

	public void setExpdtEndDate(Date expdtEndDate) {
		this.expdtEndDate = expdtEndDate;
	}

	public Date getProduceDatetime() {
		return produceDatetime;
	}

	public void setProduceDatetime(Date produceDatetime) {
		this.produceDatetime = produceDatetime;
	}

	public Date getSterilizationDate() {
		return sterilizationDate;
	}

	public void setSterilizationDate(Date sterilizationDate) {
		this.sterilizationDate = sterilizationDate;
	}

	public Date getSterilizationEndDate() {
		return sterilizationEndDate;
	}

	public void setSterilizationEndDate(Date sterilizationEndDate) {
		this.sterilizationEndDate = sterilizationEndDate;
	}

	public BigDecimal getPackeage() {
		return packeage;
	}

	public void setPackeage(BigDecimal packeage) {
		this.packeage = packeage;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		/*return "结算明细 [provId=" + provId + ", provErpCode=" + provErpCode + ", provName=" + provName + ", outDeptId="
				+ outDeptId + ", outDeptErpCode=" + outDeptErpCode + ", outDeptName=" + outDeptName + ", outBillId="
				+ outBillId + ", outDate=" + outDate + ", numKind=" + numKind + ", outKind=" + outKind + ", outRowNum="
				+ outRowNum + ", provGoodsId=" + provGoodsId + ", provGoodsErpCode=" + provGoodsErpCode
				+ ", provGoodsErpName=" + provGoodsErpName + ", goodsGg=" + goodsGg + ", mfrsName=" + mfrsName
				+ ", made=" + made + ", unit=" + unit + ", batchCode=" + batchCode + ", sterilizationCode="
				+ sterilizationCode + ", stockType=" + stockType + ", expdtEndDate=" + expdtEndDate
				+ ", produceDatetime=" + produceDatetime + ", sterilizationDate=" + sterilizationDate
				+ ", sterilizationEndDate=" + sterilizationEndDate + ", packeage=" + packeage + ", qty=" + qty
				+ ", getProvId()=" + getProvId() + ", getProvErpCode()=" + getProvErpCode() + ", getProvName()="
				+ getProvName() + ", getOutDeptId()=" + getOutDeptId() + ", getOutDeptErpCode()=" + getOutDeptErpCode()
				+ ", getOutDeptName()=" + getOutDeptName() + ", getOutBillId()=" + getOutBillId() + ", getOutDate()="
				+ getOutDate() + ", getNumKind()=" + getNumKind() + ", getOutKind()=" + getOutKind()
				+ ", getOutRowNum()=" + getOutRowNum() + ", getHosGoodsId()=" + getProvGoodsId()
				+ ", getProvGoodsErpCode()=" + getProvGoodsErpCode() + ", getProvGoodsErpName()="
				+ getProvGoodsErpName() + ", getGoodsGg()=" + getGoodsGg() + ", getMfrsName()=" + getMfrsName()
				+ ", getMade()=" + getMade() + ", getUnit()=" + getUnit() + ", getBatchCode()=" + getBatchCode()
				+ ", getSterilizationCode()=" + getSterilizationCode() + ", getStockType()=" + getStockType()
				+ ", getExpdtEndDate()=" + getExpdtEndDate() + ", getProduceDatetime()=" + getProduceDatetime()
				+ ", getSterilizationDate()=" + getSterilizationDate() + ", getSterilizationEndDate()="
				+ getSterilizationEndDate() + ", getPackeage()=" + getPackeage() + ", getQty()=" + getQty()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";*/
		return "结算明细 [subProvId=" + subProvId + ", subProvErpCode=" + subProvErpCode + ", subProvName=" + subProvName + ", outDeptId="
		+ outDeptId + ", outDeptErpCode=" + outDeptErpCode + ", outDeptName=" + outDeptName + ", outBillId="
		+ outBillId + ", outDate=" + outDate + ", numKind=" + numKind + ", outKind=" + outKind + ", outRowNum="
		+ outRowNum + ", provGoodsId=" + provGoodsId + ", provGoodsErpCode=" + provGoodsErpCode
		+ ", provGoodsErpName=" + provGoodsErpName + ", goodsGg=" + goodsGg + ", mfrsName=" + mfrsName
		+ ", made=" + made + ", unit=" + unit + ", batchCode=" + batchCode + ", sterilizationCode="
		+ sterilizationCode + ", stockType=" + stockType + ", expdtEndDate=" + expdtEndDate
		+ ", produceDatetime=" + produceDatetime + ", sterilizationDate=" + sterilizationDate
		+ ", sterilizationEndDate=" + sterilizationEndDate + ", packeage=" + packeage + ", qty=" + qty
		+ ", getSubProvId()=" + getSubProvId() + ", getSubProvErpCode()=" + getSubProvErpCode() + ", getSubProvName()="
		+ getSubProvName() + ", getOutDeptId()=" + getOutDeptId() + ", getOutDeptErpCode()=" + getOutDeptErpCode()
		+ ", getOutDeptName()=" + getOutDeptName() + ", getOutBillId()=" + getOutBillId() + ", getOutDate()="
		+ getOutDate() + ", getNumKind()=" + getNumKind() + ", getOutKind()=" + getOutKind()
		+ ", getOutRowNum()=" + getOutRowNum() + ", getHosGoodsId()=" + getProvGoodsId()
		+ ", getProvGoodsErpCode()=" + getProvGoodsErpCode() + ", getProvGoodsErpName()="
		+ getProvGoodsErpName() + ", getGoodsGg()=" + getGoodsGg() + ", getMfrsName()=" + getMfrsName()
		+ ", getMade()=" + getMade() + ", getUnit()=" + getUnit() + ", getBatchCode()=" + getBatchCode()
		+ ", getSterilizationCode()=" + getSterilizationCode() + ", getStockType()=" + getStockType()
		+ ", getExpdtEndDate()=" + getExpdtEndDate() + ", getProduceDatetime()=" + getProduceDatetime()
		+ ", getSterilizationDate()=" + getSterilizationDate() + ", getSterilizationEndDate()="
		+ getSterilizationEndDate() + ", getPackeage()=" + getPackeage() + ", getQty()=" + getQty()
		+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
		+ "]";
	}

}
