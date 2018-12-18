
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
 * 实体属性描述表
*/
@Table(name="sys_bean_property")
public class SysBeanProperty implements IBean
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
	 * 实体对象ID
	*/
	@Size(max=36)
	@Column(name="bean_id")
	@Title("实体对象ID")
	private String beanId ;
	/**
	 * 名称
	*/
	@Size(max=255)
	@Column(name="ename")
	@Title("名称")
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
	 * 数据库数据类型
	*/
	@Size(max=36)
	@Column(name="db_type")
	@Title("数据库数据类型")
	private String dbType ;
	/**
	 * 数据库数据长度
	*/
	@Column(name="db_length")
	@Title("数据库数据长度")
	private int dbLength ;
	/**
	 * 数据库小数位数
	*/
	@Column(name="db_scale")
	@Title("数据库小数位数")
	private int dbScale ;
	/**
	 * 数据类型
	*/
	@Size(max=36)
	@Column(name="java_type")
	@Title("数据类型")
	private String javaType ;
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
	@Size(max=36)
	@Column(name="dict_kind")
	@Title("字典分类名")
	private String dictKind ;
	/**
	 * 数据验证
	*/
	@Size(max=128)
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
	public String getBeanId ()
	{
		return beanId ;
	}
	
	public void setBeanId (String beanId )
	{
		this.beanId = beanId;
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
	public String getDbType ()
	{
		return dbType ;
	}
	
	public void setDbType (String dbType )
	{
		this.dbType = dbType;
	}
	public int getDbLength ()
	{
		return dbLength ;
	}
	
	public void setDbLength (int dbLength )
	{
		this.dbLength = dbLength;
	}
	public int getDbScale ()
	{
		return dbScale ;
	}
	
	public void setDbScale (int dbScale )
	{
		this.dbScale = dbScale;
	}
	public String getJavaType ()
	{
		return javaType ;
	}
	
	public void setJavaType (String javaType )
	{
		this.javaType = javaType;
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
		return "SysBeanProperty [" +" id=" +  id   +", beanId=" +  beanId   +", ename=" +  ename   +", cname=" +  cname   +", ordinal=" +  ordinal   +", dbType=" +  dbType   +", dbLength=" +  dbLength   +", dbScale=" +  dbScale   +", javaType=" +  javaType   +", refDictValue=" +  refDictValue   +", refDictName=" +  refDictName   +", defaultValue=" +  defaultValue   +", dictKind=" +  dictKind   +", validator=" +  validator   +", remark=" +  remark   +", status=" +  status   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}