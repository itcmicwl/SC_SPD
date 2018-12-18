package gyqx.spdhdi.uploadData.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description
 * 商品销售明细查询<br> 
 * 传入数据<br>
 * 其中GUID、 医疗机构、医疗机构ERP编号、SPD系统标识、配送机构、配送机构ERP编号、配送机构系统标识、开始行和截止行字段必须有值
 * 
 * @date 2017年9月13日 下午2:45:18
 */
public class GoodsSaleCondition {
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
	@JSONField(name = "订单号")
	private String billId;

	@JSONField(name = "订单类型")
	private String purType;

	@JSONField(name = "订单公司")
	private String purCompany;

	@JSONField(name = "订单行号")
	private String rowNumber;

	@JSONField(name = "商品号")
	private String provGoodsId;

	@JSONField(name = "商品名称")
	private String provGoodsName;

	@JSONField(name = "订单日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date purBillDate;

	@JSONField(name = "实际发运日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryDate;

	@JSONField(name = "客户号")
	private String customerNum;

	@JSONField(name = "配送机构名称")
	private String provName;

	@JSONField(name = "医疗机构名称")
	private String hosCompanyName;

	@JSONField(name = "请购/请退科室名称")
	private String applyOrReturnOrgName;

	@JSONField(name = "入库/出库科室名称")
	private String inOrOutOrgName;

	@JSONField(name = "业务类型")
	private String saleType;

	@JSONField(name = "采购订单号")
	private String purBillId;

	@JSONField(name = "科室申请单号")
	private String applyBillId;

	@JSONField(name = "规格")
	private String goodsGg;

	@JSONField(name = "生产厂商")
	private String mrfsName;

	@JSONField(name = "批号")
	private String batchCode;

	@JSONField(name = "灭菌批号")
	private String sterilizationCode;

	@JSONField(name = "条码")
	private String barcode;

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

	public String getProvGoodsId() {
		return provGoodsId;
	}

	public String getProvGoodsName() {
		return provGoodsName;
	}

	public Date getPurBillDate() {
		return purBillDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public String getCustomerNum() {
		return customerNum;
	}

	public String getProvName() {
		return provName;
	}

	public String getHosCompanyName() {
		return hosCompanyName;
	}

	public String getApplyOrReturnOrgName() {
		return applyOrReturnOrgName;
	}

	public String getInOrOutOrgName() {
		return inOrOutOrgName;
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

	public String getGoodsGg() {
		return goodsGg;
	}

	public String getMrfsName() {
		return mrfsName;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public String getSterilizationCode() {
		return sterilizationCode;
	}

	public String getBarcode() {
		return barcode;
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

	public void setProvGoodsId(String provGoodsId) {
		this.provGoodsId = provGoodsId;
	}

	public void setProvGoodsName(String provGoodsName) {
		this.provGoodsName = provGoodsName;
	}

	public void setPurBillDate(Date purBillDate) {
		this.purBillDate = purBillDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public void setHosCompanyName(String hosCompanyName) {
		this.hosCompanyName = hosCompanyName;
	}

	public void setApplyOrReturnOrgName(String applyOrReturnOrgName) {
		this.applyOrReturnOrgName = applyOrReturnOrgName;
	}

	public void setInOrOutOrgName(String inOrOutOrgName) {
		this.inOrOutOrgName = inOrOutOrgName;
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

	public void setGoodsGg(String goodsGg) {
		this.goodsGg = goodsGg;
	}

	public void setMrfsName(String mrfsName) {
		this.mrfsName = mrfsName;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public void setSterilizationCode(String sterilizationCode) {
		this.sterilizationCode = sterilizationCode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}

	@Override
	public String toString() {
		return "GoodsSaleCondition [guid=" + guid + ", spdCode=" + spdCode + ", hosCompanyId=" + hosCompanyId
				+ ", hosCompanyErpCode=" + hosCompanyErpCode + ", provId=" + provId + ", provErpCode=" + provErpCode
				+ ", provSystemId=" + provSystemId + ", billId=" + billId + ", purType=" + purType + ", purCompany="
				+ purCompany + ", rowNumber=" + rowNumber + ", provGoodsId=" + provGoodsId + ", provGoodsName="
				+ provGoodsName + ", purBillDate=" + purBillDate + ", deliveryDate=" + deliveryDate + ", customerNum="
				+ customerNum + ", provName=" + provName + ", hosCompanyName=" + hosCompanyName
				+ ", applyOrReturnOrgName=" + applyOrReturnOrgName + ", inOrOutOrgName=" + inOrOutOrgName
				+ ", saleType=" + saleType + ", purBillId=" + purBillId + ", applyBillId=" + applyBillId + ", goodsGg="
				+ goodsGg + ", mrfsName=" + mrfsName + ", batchCode=" + batchCode + ", sterilizationCode="
				+ sterilizationCode + ", barcode=" + barcode + ", startRowNum=" + startRowNum + ", endRowNum="
				+ endRowNum + "]";
	}

}
