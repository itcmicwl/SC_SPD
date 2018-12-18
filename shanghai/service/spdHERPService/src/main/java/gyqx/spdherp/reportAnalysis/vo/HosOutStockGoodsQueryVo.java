package gyqx.spdherp.reportAnalysis.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class HosOutStockGoodsQueryVo {

	/**
	 * 入库部门Id 列表
	 */
	private List<String> deptIds;
	public List<String> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<String> deptIds) {
		this.deptIds = deptIds;
	}

	/**
	 * 医院Id
	 */
	private String hosId;
	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	/**
	 * 出库单号
	 */
	private String billId;

	/**
	 * 单据日期
	 */
	private Date fillDate;
	
	/**
	 * 审核日期
	 */
	private Date auditDate;

	/**
	 * 查询开始日期
	 */
	private Date sDate;
	
	/**
	 * 查询结束日期
	 */
	private Date eDate;
	
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
	 * 入库部门Id
	 */
	private String inDeptId;
	
	/**
	 * 入库部门
	 */
	private String inDeptName;
	
	/**
	 * 出库单价
	 */
	private BigDecimal price;
	
	/**
	 * 出库数量
	 */
	private String outQty;
	
	/**
	 * 出库金额
	 */
	private BigDecimal outAmount;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 出库类型
	 */
	private int outStockKind;
	
	/**
	 * 出库方式，10--实库，20--虚库
	 */
	private int outStockKType;

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
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

	public String getInDeptId() {
		return inDeptId;
	}

	public void setInDeptId(String inDeptId) {
		this.inDeptId = inDeptId;
	}

	public String getInDeptName() {
		return inDeptName;
	}

	public void setInDeptName(String inDeptName) {
		this.inDeptName = inDeptName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getOutQuantity() {
		return outQty;
	}

	public void setOutQuantity(String outQty) {
		this.outQty = outQty;
	}

	public BigDecimal getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	public Date getFillDate() {
		return fillDate;
	}

	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}

	public Date getsDate() {
		return sDate;
	}

	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}

	public Date geteDate() {
		return eDate;
	}

	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}

	public String getOutQty() {
		return outQty;
	}

	public void setOutQty(String outQty) {
		this.outQty = outQty;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOutStockKind() {
		return outStockKind;
	}

	public void setOutStockKind(int outStockKind) {
		this.outStockKind = outStockKind;
	}

	public int getOutStockKType() {
		return outStockKType;
	}

	public void setOutStockKType(int outStockKType) {
		this.outStockKType = outStockKType;
	}
	
	@Override
	public String toString() {
		return "HosOutStockGoodsQueryVo [billId=" + billId + ", fillDate=" + fillDate + ", auditDate=" + auditDate
				+ ", sDate=" + sDate + ", eDate=" + eDate + ", goodsName=" + goodsName + ", goodsId=" + goodsId
				+ ", goodsGg=" + goodsGg + ", unit=" + unit + ", outDeptId=" + outDeptId + ", outDeptName="
				+ outDeptName + ", inDeptId=" + inDeptId + ", inDeptName=" + inDeptName + ", price=" + price
				+ ", outQty=" + outQty + ", outAmount=" + outAmount + ", status=" + status + ", outStockKind="
				+ outStockKind + ", outStockKType=" + outStockKType + "]";
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
}
