
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
 * 列属性描述表
*/
@Table(name="sys_table_column")
public class SysTableColumn implements IBean
{
	/**
	 * ID
	*/
	@Id
	@Size(max=64)
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 表ID
	*/
	@Size(max=36)
	@JoinColumn(name="table_id",table="sys_table",referencedColumnName="id")
	@Title("表ID")
	private String tableId ;
	/**
	 * 值
	*/
	@Size(max=255)
	@Column(name="ename")
	@Title("值")
	private String ename ;
	/**
	 * 别名
	 * 中文列名
	*/
	@Size(max=255)
	@Column(name="cname")
	@Title("别名")
	private String cname ;
	/**
	 * 序号
	*/
	@Column(name="ordinal")
	@Title("序号")
	private int ordinal ;
	/**
	 * 数据类型
	 * 针对不同的数据库 定义的统一类型， 07,08,09 为扩展类型，其中07为中间层自动生成的GUID,数据库中类型为varchar(32),08为自动增长，每次加一;
	 * [00 int,01 char,02 varchar,03 datetime,04 decimal, 05 clob,06 blob,07 guid,08 autoInc]
	*/
	@Size(max=36)
	@Column(name="data_type")
	@Title("数据类型")
	private String dataType ;
	/**
	 * 是否主键
	 * 
	 * [0 不是,1是]
	*/
	@Size(max=1)
	@Column(name="pk")
	@Title("是否主键")
	private String pk ;
	/**
	 * 外键
	 * 描述字段的外关联，以tablename.fieldname 方式表达
	*/
	@Size(max=36)
	@Column(name="fk")
	@Title("外键")
	private String fk ;
	/**
	 * 长度
	*/
	@Column(name="length")
	@Title("长度")
	private int length ;
	/**
	 * 小数位数
	*/
	@Column(name="scale")
	@Title("小数位数")
	private int scale ;
	/**
	 * 可空标志
	 * 
	 * [0 不可空,1 可空]
	*/
	@Size(max=1)
	@Column(name="null_flag")
	@Title("可空标志")
	private String nullFlag ;
	/**
	 * 引用的字典值描述
	 * 字典值所关联的表名及字段，以tablename.fieldname 方式表达
	*/
	@Size(max=36)
	@Column(name="ref_dict_value")
	@Title("引用的字典值描述")
	private String refDictValue ;
	/**
	 * 引用的字典名称描述
	 * 字典值名称所关联的表名及字段，以tablename.fieldname 方式表达
	*/
	@Size(max=36)
	@Column(name="ref_dict_name")
	@Title("引用的字典名称描述")
	private String refDictName ;
	/**
	 * 默认值
	*/
	@Size(max=36)
	@Column(name="default_value")
	@Title("默认值")
	private String defaultValue ;
	/**
	 * 字典分类名
	 * 通用字典表中的字典分类名称
	*/
	@Size(max=255)
	@Column(name="dict_kind")
	@Title("字典分类名")
	private String dictKind ;
	/**
	 * 数据验证
	*/
	@Size(max=36)
	@Column(name="validator")
	@Title("数据验证")
	private String validator ;
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
	public String getTableId ()
	{
		return tableId ;
	}
	
	public void setTableId (String tableId )
	{
		this.tableId = tableId;
	}
	public String getEname ()
	{
		return ename ;
	}
	
	public void setEname (String ename )
	{
		this.ename = ename;
	}
	public String getCname ()
	{
		return cname ;
	}
	
	public void setCname (String cname )
	{
		this.cname = cname;
	}
	public int getOrdinal ()
	{
		return ordinal ;
	}
	
	public void setOrdinal (int ordinal )
	{
		this.ordinal = ordinal;
	}
	public String getDataType ()
	{
		return dataType ;
	}
	
	public void setDataType (String dataType )
	{
		this.dataType = dataType;
	}
	public String getPk ()
	{
		return pk ;
	}
	
	public void setPk (String pk )
	{
		this.pk = pk;
	}
	public String getFk ()
	{
		return fk ;
	}
	
	public void setFk (String fk )
	{
		this.fk = fk;
	}
	public int getLength ()
	{
		return length ;
	}
	
	public void setLength (int length )
	{
		this.length = length;
	}
	public int getScale ()
	{
		return scale ;
	}
	
	public void setScale (int scale )
	{
		this.scale = scale;
	}
	public String getNullFlag ()
	{
		return nullFlag ;
	}
	
	public void setNullFlag (String nullFlag )
	{
		this.nullFlag = nullFlag;
	}
	public String getRefDictValue ()
	{
		return refDictValue ;
	}
	
	public void setRefDictValue (String refDictValue )
	{
		this.refDictValue = refDictValue;
	}
	public String getRefDictName ()
	{
		return refDictName ;
	}
	
	public void setRefDictName (String refDictName )
	{
		this.refDictName = refDictName;
	}
	public String getDefaultValue ()
	{
		return defaultValue ;
	}
	
	public void setDefaultValue (String defaultValue )
	{
		this.defaultValue = defaultValue;
	}
	public String getDictKind ()
	{
		return dictKind ;
	}
	
	public void setDictKind (String dictKind )
	{
		this.dictKind = dictKind;
	}
	public String getValidator ()
	{
		return validator ;
	}
	
	public void setValidator (String validator )
	{
		this.validator = validator;
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
		return "SysTableColumn [" +" id=" +  id   +", tableId=" +  tableId   +", ename=" +  ename   +", cname=" +  cname   +", ordinal=" +  ordinal   +", dataType=" +  dataType   +", pk=" +  pk   +", fk=" +  fk   +", length=" +  length   +", scale=" +  scale   +", nullFlag=" +  nullFlag   +", refDictValue=" +  refDictValue   +", refDictName=" +  refDictName   +", defaultValue=" +  defaultValue   +", dictKind=" +  dictKind   +", validator=" +  validator   +", remark=" +  remark   +", status=" +  status   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}