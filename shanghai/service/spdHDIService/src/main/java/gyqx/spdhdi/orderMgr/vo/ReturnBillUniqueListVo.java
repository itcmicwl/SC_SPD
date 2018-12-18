
package gyqx.spdhdi.orderMgr.vo;

import java.io.Serializable;

import javax.persistence.Column;

import common.db.meta.Title;
/**
 * 退货单唯一码明细
*/
public class ReturnBillUniqueListVo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8095162785680877210L;
	/**
	 * ID
	*/
	private String id ;
	/**
	 * pid
	*/
	private String returnBillId ;
	public String getReturnBillId() {
		return returnBillId;
	}

	public void setReturnBillId(String returnBillId) {
		this.returnBillId = returnBillId;
	}

	/**
	 * pid
	*/
	private String pid ;
	/**
	 * 行号
	*/
	private int returnRowNumber ;
	public int getReturnRowNumber() {
		return returnRowNumber;
	}

	public void setReturnRowNumber(int returnRowNumber) {
		this.returnRowNumber = returnRowNumber;
	}

	/**
	 * 医院产品ID
	*/
	private String hosGoodsId ;
	/**
	 * 配送商产品ID
	*/
	private String provGoodsId ;
	private String goodsName;
	/**
	 * 产品批次
	*/
	private String batchCode ;
	/**
	 * 唯一码
	*/
	private String uniqueCode ;
	/**
	 * 数据来源
	*/
	@Column(name="source_data")
	@Title("数据来源")
	private int sourceData ;

	public int getSourceData() {
		return sourceData;
	}

	public void setSourceData(int sourceData) {
		this.sourceData = sourceData;
	}

	/**
	 * 数据版本
	*/
	private int version ;

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

	public String getHosGoodsId ()
	{
		return hosGoodsId ;
	}
	
	public void setHosGoodsId (String hosGoodsId )
	{
		this.hosGoodsId = hosGoodsId;
	}
	public String getProvGoodsId ()
	{
		return provGoodsId ;
	}
	
	public void setProvGoodsId (String provGoodsId )
	{
		this.provGoodsId = provGoodsId;
	}
	public String getBatchCode ()
	{
		return batchCode ;
	}
	
	public void setBatchCode (String batchCode )
	{
		this.batchCode = batchCode;
	}
	public String getUniqueCode ()
	{
		return uniqueCode ;
	}
	
	public void setUniqueCode (String uniqueCode )
	{
		this.uniqueCode = uniqueCode;
	}

	public int getVersion ()
	{
		return version ;
	}
	
	public void setVersion (int version )
	{
		this.version = version;
	}
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Override
	public String toString() {  
		return "ReturnBillUniqueList [" +" id=" +  id   +", pid=" +  pid  +", returnBillId=" +  returnBillId   +", returnRowNumber=" +  returnRowNumber   +", hosGoodsId=" +  hosGoodsId   +", provGoodsId=" +  provGoodsId   +", batchCode=" +  batchCode   +", uniqueCode=" +  uniqueCode     +", sourceData=" +  sourceData     +", version=" +  version   
			 + "]";
	}

}