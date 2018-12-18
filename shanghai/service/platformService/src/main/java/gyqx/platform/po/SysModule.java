
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
 * 系统模块描述表
*/
@Table(name="sys_module")
public class SysModule implements IBean
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
	 * 编码
	*/
	@Size(max=300)
	@Column(name="code")
	@Title("编码")
	private String code ;
	/**
	 * 分组ID
	 * 模块分组描述表中的ID
	*/
	@Size(max=128)
	@JoinColumn(name="group_id",table="sys_module_group",referencedColumnName="id")
	@Title("分组ID")
	private String groupId ;
	/**
	 * 系统code
	*/
	@Size(max=128)
	@JoinColumn(name="project_code",table="sys_project",referencedColumnName="code")
	@Title("系统code")
	private String projectCode ;
	/**
	 * 排序
	*/
	@Column(name="ordinal")
	@Title("排序")
	private int ordinal ;
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
	public String getCode ()
	{
		return code ;
	}
	
	public void setCode (String code )
	{
		this.code = code;
	}
	public String getGroupId ()
	{
		return groupId ;
	}
	
	public void setGroupId (String groupId )
	{
		this.groupId = groupId;
	}
	public String getProjectCode ()
	{
		return projectCode ;
	}
	
	public void setProjectCode (String projectCode )
	{
		this.projectCode = projectCode;
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
	public String getCname ()
	{
		return cname ;
	}
	
	public void setCname (String cname )
	{
		this.cname = cname;
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
		return "SysModule [" +" id=" +  id   +", code=" +  code   +", groupId=" +  groupId   +", projectCode=" +  projectCode   +", ordinal=" +  ordinal   +", ename=" +  ename   +", cname=" +  cname   +", remark=" +  remark   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}