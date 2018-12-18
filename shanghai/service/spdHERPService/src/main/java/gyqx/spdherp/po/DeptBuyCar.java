
package gyqx.spdherp.po;

import java.math.BigDecimal;
import java.util.*;
import common.db.IBean;
import common.db.meta.Title;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.*;
/**
 * 请购车
*/
@Table(name="dept_buy_car")
public class DeptBuyCar implements IBean
{
	/**
	 * ID
	*/
	@Id
	@Size(max=128)
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 请购类型
	 * 10--实物请购20--虚拟请购
	*/
	@Column(name="buy_kind")
	@Title("请购类型")
	private int buyKind ;
	/**
	 * 采购模式
	 * 10--实物请购20--虚拟请购 30--办公
	*/
	@Column(name="pur_mode")
	@Title("采购模式")
	private int purMode ;
	/**
	 * 商品ID
	*/
	@Size(max=36)
	@Column(name="goods_id")
	@Title("商品ID")
	private String goodsId ;
	/**
	 * 请购商品数量
	*/
	@Column(name="need_qty")
	@Title("请购商品数量")
	private BigDecimal needQty ;
	/**
	 * 部门ID
	*/
	@Size(max=36)
	@Column(name="dept_id")
	@Title("部门ID")
	private String deptId ;
	/**
	 * 医院ID
	*/
	@Size(max=36)
	@Column(name="hos_id")
	@Title("医院ID")
	private String hosId ;
	/**
	 * 最后修改时间
	*/
	@Column(name="last_update_datetime")
	@Title("最后修改时间")
	private java.util.Date lastUpdateDatetime ;
	/**
	 * 版本控制
	*/
	@Column(name="version")
	@Title("版本控制")
	private int version ;

	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public int getBuyKind ()
	{
		return buyKind ;
	}
	
	public void setBuyKind (int buyKind )
	{
		this.buyKind = buyKind;
	}
	public String getGoodsId ()
	{
		return goodsId ;
	}
	
	public void setGoodsId (String goodsId )
	{
		this.goodsId = goodsId;
	}
	public BigDecimal getNeedQty ()
	{
		return needQty ;
	}
	
	public void setNeedQty (BigDecimal needQty )
	{
		this.needQty = needQty;
	}
	public String getDeptId ()
	{
		return deptId ;
	}
	
	public void setDeptId (String deptId )
	{
		this.deptId = deptId;
	}
	public String getHosId ()
	{
		return hosId ;
	}
	
	public void setHosId (String hosId )
	{
		this.hosId = hosId;
	}
	public java.util.Date getLastUpdateDatetime ()
	{
		return lastUpdateDatetime ;
	}
	
	public void setLastUpdateDatetime (java.util.Date lastUpdateDatetime )
	{
		this.lastUpdateDatetime = lastUpdateDatetime;
	}
	public int getPurMode() {
		return purMode;
	}

	public void setPurMode(int purMode) {
		this.purMode = purMode;
	}

	public int getVersion ()
	{
		return version ;
	}
	
	public void setVersion (int version )
	{
		this.version = version;
	}


	@Override
	public String toString() { 
		return "DeptBuyCar [" +" id=" +  id   +", buyKind=" +  buyKind   +", goodsId=" +  goodsId   +", needQty=" +  needQty   +", deptId=" +  deptId   +", hosId=" +  hosId   +", lastUpdateDatetime=" +  lastUpdateDatetime +", purMode=" +  purMode  +", version=" +  version   
			 + "]";
	}

}