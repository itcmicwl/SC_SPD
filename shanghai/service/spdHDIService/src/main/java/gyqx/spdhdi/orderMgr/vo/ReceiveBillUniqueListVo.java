
package gyqx.spdhdi.orderMgr.vo;

import java.io.Serializable;
/**
 * 订单收货唯一码明细
*/
public class ReceiveBillUniqueListVo implements Serializable
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
	private String pid ;
	/**
	 * 行号
	*/
	private int rowNumber ;
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
	 * 是否已收货
	*/
	private String isRecieve ;
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
	public int getRowNumber ()
	{
		return rowNumber ;
	}
	
	public void setRowNumber (int rowNumber )
	{
		this.rowNumber = rowNumber;
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
	public String getIsRecieve ()
	{
		return isRecieve ;
	}
	
	public void setIsRecieve (String isRecieve )
	{
		this.isRecieve = isRecieve;
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
		return "ReceiveBillUniqueList [" +" id=" +  id   +", pid=" +  pid   +", rowNumber=" +  rowNumber   +", hosGoodsId=" +  hosGoodsId   +", provGoodsId=" +  provGoodsId   +", batchCode=" +  batchCode   +", uniqueCode=" +  uniqueCode   +", isRecieve=" +  isRecieve   +", version=" +  version   
			 + "]";
	}

}