package gyqx.spdherp.reportAnalysis.vo;

import java.math.BigDecimal;
import java.util.Date;

public class HosPatientGoodsQueryVo {
	
	/**
	 * 出库单号
	 */
	private String billId;

	/**
	 * 单据日期
	 */
	private Date billDate;
	

	
	/**
	 * 商品名称
	 */
	private String goodsName;
	
	/**
	 * 商品Id
	 */
	private String goodsId;
	
	/**
	 * 商品规格
	 */
	private String goodsGg;
	
	/**
	 * 计量单位
	 */
	private String unit;
	
	/**
	 * 出库部门Id
	 */
	private String outDeptId;
	
	/**
	 * 出库部门
	 */
	private String outDeptName;
	

	
	/**
	 * 出库单价
	 */
	private BigDecimal price;
	
	/**
	 * 出库数量
	 */
	private String qty;
	


	/**
	 * 出库金额
	 */
	private BigDecimal outAmount;
	
	
	/**
	 * 患者姓名
	 */
	private String patientName;
	/**
	 * 患者住院科室
	 */
	private String patientDeptName;
	
	/**
	 * 患者住院科室
	 */
	private String patientDeptId;
	
	/**
	 * 医院Id
	 */
	private String hosId;
	
	/**
	 * 患者住院号
	 */
	private String patientInHosId;
	

	public String getPatientInHosId() {
		return patientInHosId;
	}

	public void setPatientInHosId(String patientInHosId) {
		this.patientInHosId = patientInHosId;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}


	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsGg() {
		return goodsGg;
	}

	public void setGoodsGg(String goodsGg) {
		this.goodsGg = goodsGg;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOutDeptId() {
		return outDeptId;
	}

	public void setOutDeptId(String outDeptId) {
		this.outDeptId = outDeptId;
	}

	public String getOutDeptName() {
		return outDeptName;
	}

	public void setOutDeptName(String outDeptName) {
		this.outDeptName = outDeptName;
	}


	public String getPatientDeptName() {
		return patientDeptName;
	}

	public String getPatientDeptId() {
		return patientDeptId;
	}

	public void setPatientDeptName(String patientDeptName) {
		this.patientDeptName = patientDeptName;
	}

	public void setPatientDeptId(String patientDeptId) {
		this.patientDeptId = patientDeptId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}



	public BigDecimal getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}
	
	@Override
	public String toString() {
		return "HosOutStockGoodsQueryVo [billDate=" + billDate + ", billId=" + billId+ ", goodsName=" + goodsName
				+ ", goodsId=" + goodsId + ", goodsGg=" + goodsGg + ", unit=" + unit + ", outDeptId=" + outDeptId
				+ ", outDeptName=" + outDeptName + ", patientDeptName=" + patientDeptName + ", patientDeptId=" + patientDeptId + ", price="
				+ price + ", qty=" + qty + ", patientName=" + patientName + ", hosId=" + hosId+ ", patientInHosId=" + patientInHosId+ ", outAmount=" + outAmount + "]";
	}
	
	
}
