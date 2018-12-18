
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
 * 系统资源定义表
*/
@Table(name="sys_string")
public class SysString implements IBean
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
	*/
	@NotEmpty
	@Size(max=36)
	@Column(name="ename")
	@Title("名称")
	private String ename ;
	/**
	 * 值
	*/
	@Size(max=255)
	@Column(name="val")
	@Title("值")
	private String val ;
	/**
	 * 描述
	*/
	@Size(max=255)
	@Column(name="remark")
	@Title("描述")
	private String remark ;
	/**
	 * 分类
	 * 
	 * [0 字符串,1 sql ,2 错误信息]
	*/
	@Size(max=1)
	@Column(name="kind")
	@Title("分类")
	private String kind ;
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
	 * 操作员ID
	 * 新增或修改的操作员ID
	*/
	@Size(max=36)
	@Column(name="uxid")
	@Title("操作员ID")
	private String uxid ;
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
	public String getRemark ()
	{
		return remark ;
	}
	
	public void setRemark (String remark )
	{
		this.remark = remark;
	}
	public String getKind ()
	{
		return kind ;
	}
	
	public void setKind (String kind )
	{
		this.kind = kind;
	}
	public String getStatus ()
	{
		return status ;
	}
	
	public void setStatus (String status )
	{
		this.status = status;
	}
	public String getUxid ()
	{
		return uxid ;
	}
	
	public void setUxid (String uxid )
	{
		this.uxid = uxid;
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

	public String getUid ()
	{
		return uxid ;
	}
	
	public void setUid (String uxid )
	{
		this.uxid = uxid;
	}

	@Override
	public String toString() {
		return "SysString [" +" id=" +  id   +", ename=" +  ename   +", val=" +  val   +", remark=" +  remark   +", kind=" +  kind   +", status=" +  status   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}