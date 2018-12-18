package gyqx.spdherp.reportAnalysis.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CenterStockOutQueryVo {
	
	/**
	 */
	private int[] arrStatus;

	/**
	 * 出库状态
	 */
	private Integer status;

	/**
	 * 是否集配 0:直销	1：集配
	 */
	private Integer provType;

	/**
	 * 产地
	 */
	private String made;

	/**
	 * ERP CODE
	 */
	private String erpCode;

	public int[] getArrStatus() {
		return arrStatus;
	}

	public void setArrStatus(int[] arrStatus) {
		this.arrStatus = arrStatus;
	}

	private java.util.Date sDate;
	public java.util.Date getsDate() {
		return sDate;
	}

	public java.util.Date geteDate() {
		return eDate;
	}

	public void setsDate(java.util.Date sDate) {
		this.sDate = sDate;
	}

	public void seteDate(java.util.Date eDate) {
		this.eDate = eDate;
	}

	/**
	 */
	private java.util.Date eDate;

	/**
	 * 出库单号
	 */
	private String billId;

	/**
	 * 单据日期
	 */
	private Date billDate;

	/**
	 * 配送单号
	 */
	private String distrBillId;

	/**
	 * 医院名称
	 */
	private String inOrgName;

	/**
	 * 医院Id
	 */
	private String inOrgId;
	/**
	 * 出库科室名
	 */
	private String outDeptName;

	/**
	 * 医院商品Id
	 */
	private String hosGoodsId;

	/**
	 * 供应商商品Id
	 */
	private String provGoodsId;

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 商品规格
	 */
	private String goodsGg;
	/**
	 * 厂家名称
	 */
	private String mfrsName;

	/**
	 * 批号
	 */
	private String batchCode;

	/**
	 * 计量单位
	 */
	private String unit;

	/**
	 * 效期
	 */
	private Date expdtEndDate;

	/**
	 * 出库数量
	 */
	private String qty;

	/**
	 * 二级供应商配送价
	 */
	private BigDecimal subSendPrice;

	/**
	 * 国药配送价
	 */
	private BigDecimal sendPrice;

	/**
	 * 二级供应商名称
	 */
	private String subProvName;

	/**
	 * 二级供应商Id
	 */
	private String subProvId;

	/**
	 * 供应商Id
	 */
	private String provId;

	/**
	 * 供应商
	 */
	private String provName;
	
	/**
	 * 出库类型 40 请购出库
	 */
	private String outStockKind;

	public String getOutStockKind() {
		return outStockKind;
	}

	public void setOutStockKind(String outStockKind) {
		this.outStockKind = outStockKind;
	}

	public String getBillId() {
		return billId;
	}

	public Date getBillDate() {
		return billDate;
	}


	public String getDistrBillId() {
		return distrBillId;
	}

	public String getInOrgName() {
		return inOrgName;
	}

	public String getInOrgId() {
		return inOrgId;
	}

	public String getOutDeptName() {
		return outDeptName;
	}

	public String getHosGoodsId() {
		return hosGoodsId;
	}

	public String getProvGoodsId() {
		return provGoodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public String getGoodsGg() {
		return goodsGg;
	}

	public String getMfrsName() {
		return mfrsName;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public String getUnit() {
		return unit;
	}

	public Date getExpdtEndDate() {
		return expdtEndDate;
	}

	public String getQty() {
		return qty;
	}

	public BigDecimal getSubSendPrice() {
		return subSendPrice;
	}

	public BigDecimal getSendPrice() {
		return sendPrice;
	}

	public String getSubProvName() {
		return subProvName;
	}

	public String getSubProvId() {
		return subProvId;
	}

	public String getProvId() {
		return provId;
	}

	public String getProvName() {
		return provName;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public void setDistrBillId(String distrBillId) {
		this.distrBillId = distrBillId;
	}

	public void setInOrgName(String inOrgName) {
		this.inOrgName = inOrgName;
	}

	public void setInOrgId(String inOrgId) {
		this.inOrgId = inOrgId;
	}

	public void setOutDeptName(String outDeptName) {
		this.outDeptName = outDeptName;
	}

	public void setHosGoodsId(String hosGoodsId) {
		this.hosGoodsId = hosGoodsId;
	}

	public void setProvGoodsId(String provGoodsId) {
		this.provGoodsId = provGoodsId;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setGoodsGg(String goodsGg) {
		this.goodsGg = goodsGg;
	}

	public void setMfrsName(String mfrsName) {
		this.mfrsName = mfrsName;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setExpdtEndDate(Date expdtEndDate) {
		this.expdtEndDate = expdtEndDate;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public void setSubSendPrice(BigDecimal subSendPrice) {
		this.subSendPrice = subSendPrice;
	}

	public void setSendPrice(BigDecimal sendPrice) {
		this.sendPrice = sendPrice;
	}

	public void setSubProvName(String subProvName) {
		this.subProvName = subProvName;
	}

	public void setSubProvId(String subProvId) {
		this.subProvId = subProvId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getProvType() {
		return provType;
	}

	public void setProvType(Integer provType) {
		this.provType = provType;
	}

	public String getMade() {
		return made;
	}

	public void setMade(String made) {
		this.made = made;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	@Override
	public String toString() { //
		return "CenterStockOutQueryVo [billDate=" + billDate + ", billId=" + billId + ", outStockKind=" + outStockKind + ", billDate=" + billDate
				+ ", distrBillId=" + distrBillId + ", inOrgName=" + inOrgName + ", inOrgId=" + inOrgId + ", outDeptName=" + outDeptName
				+ ", hosGoodsId=" + hosGoodsId + ", provGoodsId=" + provGoodsId + ", goodsName="
				+ goodsName + ", goodsGg=" + goodsGg + ", mfrsName=" + mfrsName + ", batchCode=" + batchCode + ", unit="
				+ unit + ", expdtEndDate=" + expdtEndDate  + ", qty=" + qty + ", subSendPrice=" + subSendPrice + ", sendPrice="
				+ sendPrice + ", subProvName=" + subProvName  + ", subProvId=" + subProvId + ", provId=" + provId 
				 + ", provName=" + provName  +"]";
	}

}
