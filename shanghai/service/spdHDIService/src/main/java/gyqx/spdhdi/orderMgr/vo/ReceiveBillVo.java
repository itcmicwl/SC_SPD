package gyqx.spdhdi.orderMgr.vo;
import java.util.Date;
import java.util.List;

public class ReceiveBillVo 
{
	/**
	 * ID
	*/
	private String id ;
	/**
	 * 单号
	*/
	private String billId ;
	/**
	 * 配送单号
	 */
	private String distrBillId;
	/**
	 * fillDate
	*/
	private Date fillDate;
	private Date fillDate$begin ;
	/**
	 * fillDate
	*/
	private Date fillDate$end ;
	/**
	 * 请购机构
	*/
	private String purchaseCompanyId ;
	/**
	 * 公司/医院名称
	*/
	private String purchaseCompanyName ;
	/**
	 * 请购科室
	*/
	private String applyOrgId ;
	/**
	 * 机构别名
	*/
	private String applyOrgName ;
	/**
	 * 机构编码
	*/
	private String applyOrgCode ;
	/**
	 * 收货科室
	*/
	private String recieveOrgId ;
	/**
	 * 机构别名
	*/
	private String recieveOrgName ;
	/**
	 * 机构编码
	*/
	private String recieveOrgCode ;
	/**
	 * 配送机构
	*/
	private String provId ;
	/**
	 * 公司/医院名称
	*/
	private String provName ;
	/**
	 * 验收人
	*/
	private String revieverId ;
	/**
	 * 用户名称
	 * 在机构中显示的名称
	*/
	private String revieverName ;
	/**
	 * 制单人
	*/
	private String fillter ;
	/**
	 * 用户名称
	 * 在机构中显示的名称
	*/
	private String fillterName ;
	/**
	 * 说明
	*/
	private String memo ;
	/**
	 * 当前审批级别
	*/
	private Integer status ;
	/**
	 * 笔数
	*/
	private Integer sumRow ;
	/**
	 * 数据版本
	*/
	private Integer version ;
	private List<ReceiveBillListVo> lstDetail;
	
	private int[] arrStatus;
	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getBillId ()
	{
		return billId ;
	}
	
	public void setBillId (String billId )
	{
		this.billId = billId;
	}
	public Date getFillDate$begin ()
	{
		return fillDate$begin ;
	}
	
	public void setFillDate$begin (Date fillDate$begin )
	{
		this.fillDate$begin = fillDate$begin;
	}
	public Date getFillDate$end ()
	{
		return fillDate$end ;
	}
	
	public void setFillDate$end (Date fillDate$end )
	{
		this.fillDate$end = fillDate$end;
	}
	public String getPurchaseCompanyId ()
	{
		return purchaseCompanyId ;
	}
	
	public void setPurchaseCompanyId (String purchaseCompanyId )
	{
		this.purchaseCompanyId = purchaseCompanyId;
	}
	public String getPurchaseCompanyName ()
	{
		return purchaseCompanyName ;
	}
	
	public void setPurchaseCompanyName (String purchaseCompanyName )
	{
		this.purchaseCompanyName = purchaseCompanyName;
	}
	public String getApplyOrgId ()
	{
		return applyOrgId ;
	}
	
	public void setApplyOrgId (String applyOrgId )
	{
		this.applyOrgId = applyOrgId;
	}
	public String getApplyOrgName ()
	{
		return applyOrgName ;
	}
	
	public void setApplyOrgName (String applyOrgName )
	{
		this.applyOrgName = applyOrgName;
	}
	public String getApplyOrgCode ()
	{
		return applyOrgCode ;
	}
	
	public void setApplyOrgCode (String applyOrgCode )
	{
		this.applyOrgCode = applyOrgCode;
	}
	public String getRecieveOrgId ()
	{
		return recieveOrgId ;
	}
	
	public void setRecieveOrgId (String recieveOrgId )
	{
		this.recieveOrgId = recieveOrgId;
	}
	public String getRecieveOrgName ()
	{
		return recieveOrgName ;
	}
	
	public void setRecieveOrgName (String recieveOrgName )
	{
		this.recieveOrgName = recieveOrgName;
	}
	public String getRecieveOrgCode ()
	{
		return recieveOrgCode ;
	}
	
	public void setRecieveOrgCode (String recieveOrgCode )
	{
		this.recieveOrgCode = recieveOrgCode;
	}
	public String getProvId ()
	{
		return provId ;
	}
	
	public void setProvId (String provId )
	{
		this.provId = provId;
	}
	public String getProvName ()
	{
		return provName ;
	}
	
	public void setProvName (String provName )
	{
		this.provName = provName;
	}
	public String getRevieverId ()
	{
		return revieverId ;
	}
	
	public void setRevieverId (String revieverId )
	{
		this.revieverId = revieverId;
	}
	public String getRevieverName ()
	{
		return revieverName ;
	}
	
	public void setRevieverName (String revieverName )
	{
		this.revieverName = revieverName;
	}
	public String getFillter ()
	{
		return fillter ;
	}
	
	public void setFillter (String fillter )
	{
		this.fillter = fillter;
	}
	public String getFillterName ()
	{
		return fillterName ;
	}
	
	public void setFillterName (String fillterName )
	{
		this.fillterName = fillterName;
	}
	public String getMemo ()
	{
		return memo ;
	}
	
	public void setMemo (String memo )
	{
		this.memo = memo;
	}
	public Integer getStatus ()
	{
		return status ;
	}
	
	public void setStatus (Integer status )
	{
		this.status = status;
	}
	public Integer getSumRow ()
	{
		return sumRow ;
	}
	
	public void setSumRow (Integer sumRow )
	{
		this.sumRow = sumRow;
	}
	public Integer getVersion ()
	{
		return version ;
	}
	
	public void setVersion (Integer version )
	{
		this.version = version;
	}
	public List<ReceiveBillListVo> getLstDetail() {
		return lstDetail;
	}

	public void setLstDetail(List<ReceiveBillListVo> lstDetail) {
		this.lstDetail = lstDetail;
	}

	public Date getFillDate() {
		return fillDate;
	}

	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}

	public String getDistrBillId() {
		return distrBillId;
	}

	public void setDistrBillId(String distrBillId) {
		this.distrBillId = distrBillId;
	}

	public int[] getArrStatus() {
		return arrStatus;
	}

	public void setArrStatus(int[] arrStatus) {
		this.arrStatus = arrStatus;
	}

	@Override
	public String toString() {
		return "ReceiveBillVo ["  +" id=" +  id    +", billId=" +  billId    +", fillDate$begin=" +  fillDate$begin    +", fillDate$end=" +  fillDate$end    +", purchaseCompanyId=" +  purchaseCompanyId    +", purchaseCompanyName=" +  purchaseCompanyName    +", applyOrgId=" +  applyOrgId    +", applyOrgName=" +  applyOrgName    +", applyOrgCode=" +  applyOrgCode    +", recieveOrgId=" +  recieveOrgId    +", recieveOrgName=" +  recieveOrgName    +", recieveOrgCode=" +  recieveOrgCode    +", provId=" +  provId    +", provName=" +  provName    +", revieverId=" +  revieverId    +", revieverName=" +  revieverName    +", fillter=" +  fillter    +", fillterName=" +  fillterName    +", memo=" +  memo    +", status=" +  status    +", sumRow=" +  sumRow    +", version=" +  version   
			 + "]";
	}

}