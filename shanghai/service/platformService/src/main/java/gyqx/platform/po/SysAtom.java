
package gyqx.platform.po;

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
 * 原子值定义表
*/
@Table(name="sys_atom")
public class SysAtom implements IBean
{
	/**
	 * ID
	*/
	@Id
	@Size(max=36)
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 名称
	 * PK用英文字母和下划线
	*/
	@Size(max=36)
	@Column(name="ename")
	@Title("名称")
	private String ename ;
	/**
	 * 原子值类型
	 * 
	 * [0 自动加一,1 每日自动归零,2 每月自动归零,3 全局锁]
	*/
	@Column(name="kind")
	@Title("原子值类型")
	private int kind ;
	/**
	 * 当前的整数值
	*/
	@Column(name="cur_val")
	@Title("当前的整数值")
	private java.math.BigDecimal curVal ;
	/**
	 * 最大值
	 * 当前值超过最大值后自动归零
	*/
	@Column(name="max_val")
	@Title("最大值")
	private java.math.BigDecimal maxVal ;
	/**
	 * 原子值标志
	 * 
	 * [0整数值 ,1 字符串]
	*/
	@Size(max=1)
	@Column(name="val_flag")
	@Title("原子值标志")
	private String valFlag ;
	/**
	 * 前缀值
	*/
	@Size(max=64)
	@Column(name="prefix")
	@Title("前缀值")
	private String prefix ;
	/**
	 * 后缀值
	 * 字符串值(格式化后加前后缀)
	*/
	@Size(max=64)
	@Column(name="suffix")
	@Title("后缀值")
	private String suffix ;
	/**
	 * 格式化串
	*/
	@Size(max=32)
	@Column(name="format")
	@Title("格式化串")
	private String format ;
	/**
	 * 描述
	*/
	@Size(max=255)
	@Column(name="remark")
	@Title("描述")
	private String remark ;
	/**
	 * 处理状态
	 * 
	 * [0 不启用,1 启用]
	*/
	@Size(max=1)
	@Column(name="status")
	@Title("处理状态")
	private String status ;
	/**
	 * 修改时间
	*/
	@Column(name="mt")
	@Title("修改时间")
	private java.util.Date mt ;
	/**
	 * 并发控制
	*/
	@Column(name="version")
	@Title("并发控制")
	private int version ;

	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getEname ()
	{
		return ename ;
	}
	
	public void setEname (String ename )
	{
		this.ename = ename;
	}
	public int getKind ()
	{
		return kind ;
	}
	
	public void setKind (int kind )
	{
		this.kind = kind;
	}
	public java.math.BigDecimal getCurVal ()
	{
		return curVal ;
	}
	
	public void setCurVal (java.math.BigDecimal curVal )
	{
		this.curVal = curVal;
	}
	public java.math.BigDecimal getMaxVal ()
	{
		return maxVal ;
	}
	
	public void setMaxVal (java.math.BigDecimal maxVal )
	{
		this.maxVal = maxVal;
	}
	public String getValFlag ()
	{
		return valFlag ;
	}
	
	public void setValFlag (String valFlag )
	{
		this.valFlag = valFlag;
	}
	public String getPrefix ()
	{
		return prefix ;
	}
	
	public void setPrefix (String prefix )
	{
		this.prefix = prefix;
	}
	public String getSuffix ()
	{
		return suffix ;
	}
	
	public void setSuffix (String suffix )
	{
		this.suffix = suffix;
	}
	public String getFormat ()
	{
		return format ;
	}
	
	public void setFormat (String format )
	{
		this.format = format;
	}
	public String getRemark ()
	{
		return remark ;
	}
	
	public void setRemark (String remark )
	{
		this.remark = remark;
	}
	public String getStatus ()
	{
		return status ;
	}
	
	public void setStatus (String status )
	{
		this.status = status;
	}
	public java.util.Date getMt ()
	{
		return mt ;
	}
	
	public void setMt (java.util.Date mt )
	{
		this.mt = mt;
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
		return "SysAtom [" +" id=" +  id   +", ename=" +  ename   +", kind=" +  kind   +", curVal=" +  curVal   +", maxVal=" +  maxVal   +", valFlag=" +  valFlag   +", prefix=" +  prefix   +", suffix=" +  suffix   +", format=" +  format   +", remark=" +  remark   +", status=" +  status   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}