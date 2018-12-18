
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
 * 系统配置信息表
*/
@Table(name="sys_config")
public class SysConfig implements IBean
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
	 * 配置项名称
	 * 用字母和数字组的组合，全大写，字母打头可用下划线连结
	*/
	@Size(max=36)
	@Column(name="ename")
	@Title("配置项名称")
	private String ename ;
	/**
	 * 值
	*/
	@Size(max=255)
	@Column(name="val")
	@Title("值")
	private String val ;
	/**
	 * 格式化信息
	*/
	@Size(max=255)
	@Column(name="format")
	@Title("格式化信息")
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
	public String getVal ()
	{
		return val ;
	}
	
	public void setVal (String val )
	{
		this.val = val;
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
		return "SysConfig [" +" id=" +  id   +", ename=" +  ename   +", val=" +  val   +", format=" +  format   +", remark=" +  remark   +", status=" +  status   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}