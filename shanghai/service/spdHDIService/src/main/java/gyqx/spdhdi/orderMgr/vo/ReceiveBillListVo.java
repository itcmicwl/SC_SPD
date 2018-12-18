
package gyqx.spdhdi.orderMgr.vo;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Size;

import common.db.meta.Title;

public class ReceiveBillListVo 
{
	/**
	 * ID
	*/
	private String id ;
	/**
	 * pid
	*/
	private String pid ;
	/**
	 * 单号
	*/
	private String billId ;
	/**
	 * 行号
	*/
	private Integer rowNumber ;
	/**
	 * 采购订单号
	*/
	private String purBillId ;
	private String purRowNumber;
	/**
	 * purBillDate
	*/
	private Date purBillDate ;

	/**
	 * 科室请购单号
	*/
	private String applyBillId ;
	/**
	 * 销售单号
	*/
	private String saleBillId ;
	/**
	 * 配送商产品ID
	*/
	private String provGoodsId ;
	/**
	 * 规格
	*/
	private String goodsGg ;
	/**
	 * 生产厂商
	*/
	private String goodsMrfsId ;
	/**
	 * 厂家名称
	*/
	private String mfrsName ;
	/**
	 * 产地
	*/
	private String made ;
	/**
	 * 医院产品ID
	*/
	private String hosGoodsId ;
	/**
	 * 医院内部产品编号
	*/
	private String hosGoodsCode ;
	/**
	 * 医院内部产品名称
	*/
	private String hosGoodsName ;
	/**
	 * 启用唯一码管理
	*/
	private String isUniqueCode ;
	/**
	 * 产品批次
	*/
	private String batchNum ;
	/**
	 * 批号
	*/
	private String batchCode ;
	private String sterilizationCode;
	/**
	 * 灭菌有效日期
	*/
	private java.util.Date sterilizationEndDate ;
	/**
	 * 灭菌日期
	*/
	private java.util.Date sterilizationDate ;
	/**
	 * 有效日期
	*/
	private java.util.Date expdtEndDate ;
	/**
	 * 生产日期
	*/
	private java.util.Date productDate ;
	/**
	 * 待验收数量
	*/
	private java.math.BigDecimal unrecieveQty ;
	/**
	 * 数量
	*/
	private java.math.BigDecimal qty ;
	/**
	 * 单价
	*/
	private java.math.BigDecimal price ;
	/**
	 * 验收结论
	*/
	private Integer revieveResult ;
	/**
	 * 任务总行数
	*/
	private Integer sumRows ;
	/**
	 * 当前审批级别
	*/
	private Integer status ;
	/**
	 * 说明
	*/
	private String memo ;
	private float receiveTemprature;
	private float receiveHumidity;
	/**
	 * 数据版本
	*/
	private Integer version ;
	/**
	 * 配送单号
	 */
	private String distrBillId;
	/**
	 * 配送单行号
	 */
	private Integer distrRowNumber;
	private List<ReceiveBillUniqueListVo> lstUniqueList;
	private List<ReceiveBillHumitureListVo> lstHumitureList;

	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getPid ()
	{
		return pid ;
	}
	
	public void setPid (String pid )
	{
		this.pid = pid;
	}
	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public Integer getRowNumber ()
	{
		return rowNumber ;
	}
	
	public void setRowNumber (Integer rowNumber )
	{
		this.rowNumber = rowNumber;
	}
	public String getPurBillId ()
	{
		return purBillId ;
	}
	
	public void setPurBillId (String purBillId )
	{
		this.purBillId = purBillId;
	}
	
	public String getApplyBillId ()
	{
		return applyBillId ;
	}
	
	public void setApplyBillId (String applyBillId )
	{
		this.applyBillId = applyBillId;
	}
	public String getSaleBillId ()
	{
		return saleBillId ;
	}
	
	public void setSaleBillId (String saleBillId )
	{
		this.saleBillId = saleBillId;
	}
	public String getProvGoodsId ()
	{
		return provGoodsId ;
	}
	
	public void setProvGoodsId (String provGoodsId )
	{
		this.provGoodsId = provGoodsId;
	}
	public String getGoodsGg ()
	{
		return goodsGg ;
	}
	
	public void setGoodsGg (String goodsGg )
	{
		this.goodsGg = goodsGg;
	}
	public String getGoodsMrfsId ()
	{
		return goodsMrfsId ;
	}
	
	public void setGoodsMrfsId (String goodsMrfsId )
	{
		this.goodsMrfsId = goodsMrfsId;
	}
	public String getMfrsName ()
	{
		return mfrsName ;
	}
	
	public void setMfrsName (String mfrsName )
	{
		this.mfrsName = mfrsName;
	}
	public String getMade ()
	{
		return made ;
	}
	
	public void setMade (String made )
	{
		this.made = made;
	}
	public String getHosGoodsId ()
	{
		return hosGoodsId ;
	}
	
	public void setHosGoodsId (String hosGoodsId )
	{
		this.hosGoodsId = hosGoodsId;
	}
	public String getHosGoodsCode ()
	{
		return hosGoodsCode ;
	}
	
	public void setHosGoodsCode (String hosGoodsCode )
	{
		this.hosGoodsCode = hosGoodsCode;
	}
	public String getHosGoodsName ()
	{
		return hosGoodsName ;
	}
	
	public void setHosGoodsName (String hosGoodsName )
	{
		this.hosGoodsName = hosGoodsName;
	}
	public String getIsUniqueCode ()
	{
		return isUniqueCode ;
	}
	
	public void setIsUniqueCode (String isUniqueCode )
	{
		this.isUniqueCode = isUniqueCode;
	}
	public String getBatchNum ()
	{
		return batchNum ;
	}
	
	public void setBatchNum (String batchNum )
	{
		this.batchNum = batchNum;
	}
	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public java.util.Date getSterilizationEndDate() {
		return sterilizationEndDate;
	}

	public void setSterilizationEndDate(java.util.Date sterilizationEndDate) {
		this.sterilizationEndDate = sterilizationEndDate;
	}

	public java.util.Date getSterilizationDate() {
		return sterilizationDate;
	}

	public void setSterilizationDate(java.util.Date sterilizationDate) {
		this.sterilizationDate = sterilizationDate;
	}

	public java.util.Date getExpdtEndDate() {
		return expdtEndDate;
	}

	public String getDistrBillId() {
		return distrBillId;
	}

	public void setDistrBillId(String distrBillId) {
		this.distrBillId = distrBillId;
	}

	public Integer getDistrRowNumber() {
		return distrRowNumber;
	}

	public void setDistrRowNumber(Integer distrRowNumber) {
		this.distrRowNumber = distrRowNumber;
	}

	public void setExpdtEndDate(java.util.Date expdtEndDate) {
		this.expdtEndDate = expdtEndDate;
	}

	public java.util.Date getProductDate() {
		return productDate;
	}

	public void setProductDate(java.util.Date productDate) {
		this.productDate = productDate;
	}

	public java.math.BigDecimal getUnrecieveQty ()
	{
		return unrecieveQty ;
	}
	
	public void setUnrecieveQty (java.math.BigDecimal unrecieveQty )
	{
		this.unrecieveQty = unrecieveQty;
	}
	public java.math.BigDecimal getQty ()
	{
		return qty ;
	}
	
	public void setQty (java.math.BigDecimal qty )
	{
		this.qty = qty;
	}
	public java.math.BigDecimal getPrice ()
	{
		return price ;
	}
	
	public void setPrice (java.math.BigDecimal price )
	{
		this.price = price;
	}
	public Integer getRevieveResult ()
	{
		return revieveResult ;
	}
	
	public void setRevieveResult (Integer revieveResult )
	{
		this.revieveResult = revieveResult;
	}
	public Integer getSumRows ()
	{
		return sumRows ;
	}
	
	public void setSumRows (Integer sumRows )
	{
		this.sumRows = sumRows;
	}
	public Integer getStatus ()
	{
		return status ;
	}
	
	public void setStatus (Integer status )
	{
		this.status = status;
	}
	public String getMemo ()
	{
		return memo ;
	}
	
	public void setMemo (String memo )
	{
		this.memo = memo;
	}
	public Integer getVersion ()
	{
		return version ;
	}
	
	public void setVersion (Integer version )
	{
		this.version = version;
	}
	public String getSterilizationCode() {
		return sterilizationCode;
	}

	public void setSterilizationCode(String sterilizationCode) {
		this.sterilizationCode = sterilizationCode;
	}

	@Override
	public String toString() {
		return "ReceiveBillListVo ["  +" id=" +  id    +", pid=" +  pid    +", billId=" +  billId    +", rowNumber=" +  rowNumber    +", purBillId=" +  purBillId    +", purBillDate=" +  purBillDate    +", applyBillId=" +  applyBillId    +", saleBillId=" +  saleBillId    +", provGoodsId=" +  provGoodsId    +", goodsGg=" +  goodsGg    +", goodsMrfsId=" +  goodsMrfsId    +", mfrsName=" +  mfrsName    +", made=" +  made    +", hosGoodsId=" +  hosGoodsId    +", hosGoodsCode=" +  hosGoodsCode    +", hosGoodsName=" +  hosGoodsName    +", isUniqueCode=" +  isUniqueCode    +", batchNum=" +  batchNum +", batchCode=" +  batchCode    +", sterilizationEndDate=" +  sterilizationEndDate  +", sterilizationDate=" +  sterilizationDate +", expdtEndDate=" +  expdtEndDate+", productDate=" +  productDate +", unrecieveQty=" +  unrecieveQty    +", qty=" +  qty    +", price=" +  price    +", revieveResult=" +  revieveResult    +", sumRows=" +  sumRows    +", status=" +  status    +", memo=" +  memo    +", version=" +  version   
			 + "]";
	}

	public Date getPurBillDate() {
		return purBillDate;
	}

	public String getPurRowNumber() {
		return purRowNumber;
	}

	public void setPurRowNumber(String purRowNumber) {
		this.purRowNumber = purRowNumber;
	}

	public List<ReceiveBillUniqueListVo> getLstUniqueList() {
		return lstUniqueList;
	}

	public void setLstUniqueList(List<ReceiveBillUniqueListVo> lstUniqueList) {
		this.lstUniqueList = lstUniqueList;
	}

	public List<ReceiveBillHumitureListVo> getLstHumitureList() {
		return lstHumitureList;
	}

	public void setLstHumitureList(List<ReceiveBillHumitureListVo> lstHumitureList) {
		this.lstHumitureList = lstHumitureList;
	}

	public void setPurBillDate(Date purBillDate) {
		this.purBillDate = purBillDate;
	}

	public float getReceiveTemprature() {
		return receiveTemprature;
	}

	public void setReceiveTemprature(float receiveTemprature) {
		this.receiveTemprature = receiveTemprature;
	}

	public float getReceiveHumidity() {
		return receiveHumidity;
	}

	public void setReceiveHumidity(float receiveHumidity) {
		this.receiveHumidity = receiveHumidity;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (obj instanceof DistrBillListVo) {   
			DistrBillListVo d = (DistrBillListVo) obj;   
            return this.distrBillId.equals(d.getBillId())   
                    && this.distrRowNumber.equals(d.getRowNumber());   
        }
	     if (getClass() == obj.getClass()){
	    	 DistrBillListVo dblv = (DistrBillListVo) obj;
	    	 return this.pid.equals(dblv.getPid())   
	                    && this.rowNumber.equals(dblv.getRowNumber()); 
	     }
		return super.equals(obj);
	}

}