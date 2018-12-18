
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
 * 字典值定义表
*/
@Table(name="sys_dict_value")
public class SysDictValue implements IBean
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
	 * 字典分类ID
	*/
	@Size(max=36)
	@JoinColumn(name="dict_id",table="sys_dict",referencedColumnName="id")
	@Title("字典分类ID")
	private String dictId ;
	/**
	 * 排序
	*/
	@Column(name="ordinal")
	@Title("排序")
	private int ordinal ;
	/**
	 * 字典名称
	 * 如：男
	*/
	@Size(max=36)
	@Column(name="ename")
	@Title("字典名称")
	private String ename ;
	/**
	 * 字典编码
	 * 如：01
	*/
	@Size(max=36)
	@Column(name="val")
	@Title("字典编码")
	private String val ;
	/**
	 * 扩展信息
	*/
	@Size(max=36)
	@Column(name="ext_field")
	@Title("扩展信息")
	private String extField ;
	/**
	 * 描述
	*/
	@Size(max=255)
	@Column(name="remark")
	@Title("描述")
	private String remark ;
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
	public String getDictId ()
	{
		return dictId ;
	}
	
	public void setDictId (String dictId )
	{
		this.dictId = dictId;
	}
	public int getOrdinal ()
	{
		return ordinal ;
	}
	
	public void setOrdinal (int ordinal )
	{
		this.ordinal = ordinal;
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
	public String getExtField ()
	{
		return extField ;
	}
	
	public void setExtField (String extField )
	{
		this.extField = extField;
	}
	public String getRemark ()
	{
		return remark ;
	}
	
	public void setRemark (String remark )
	{
		this.remark = remark;
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
		return "SysDictValue [" +" id=" +  id   +", dictId=" +  dictId   +", ordinal=" +  ordinal   +", ename=" +  ename   +", val=" +  val   +", extField=" +  extField   +", remark=" +  remark   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}