
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
 * 用户机构对应表
 * 用户在多个机构中
*/
@Table(name="sys_user_org")
public class SysUserOrg implements IBean
{
	/**
	 * ID
	 * (guid)
	*/
	@Id
	@Size(max=36)
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 用户ID
	*/
	@Size(max=36)
	@JoinColumn(name="user_id",table="sys_user",referencedColumnName="id")
	@Title("用户ID")
	private String userId ;
	/**
	 * 机构ID
	*/
	@Size(max=36)
	@JoinColumn(name="org_id",table="sys_org",referencedColumnName="id")
	@Title("机构ID")
	private String orgId ;
	/**
	 * 机构编码
	*/
	@Size(max=36)
	@JoinColumn(name="org_code",table="sys_org",referencedColumnName="code")
	@Title("机构编码")
	private String orgCode ;
	/**
	 * 用户名称
	 * 在机构中显示的名称
	*/
	@Size(max=36)
	@Column(name="ename")
	@Title("用户名称")
	private String ename ;
	/**
	 * 序号
	 * 用户在机构中的序号
	*/
	@Column(name="ordinal")
	@Title("序号")
	private int ordinal ;
	/**
	 * 用户别名
	*/
	@Size(max=36)
	@Column(name="cname")
	@Title("用户别名")
	private String cname ;
	/**
	 * 用户联系电话
	*/
	@Size(max=36)
	@Column(name="tel")
	@Title("用户联系电话")
	private String tel ;
	/**
	 * 邮箱
	*/
	@Size(max=36)
	@Column(name="email")
	@Title("邮箱")
	private String email ;
	/**
	 * qq号
	*/
	@Size(max=36)
	@Column(name="qq")
	@Title("qq号")
	private String qq ;
	/**
	 * 职务
	*/
	@Size(max=36)
	@Column(name="job")
	@Title("职务")
	private String job ;
	/**
	 * 其它联系方式
	*/
	@Size(max=36)
	@Column(name="others")
	@Title("其它联系方式")
	private String others ;
	/**
	 * SCM编号
	*/
	@Size(max=36)
	@Column(name="scm_code")
	@Title("SCM编号")
	private String scmCode ;
	/**
	 * ERP编号(供应商)
	*/
	@Size(max=36)
	@Column(name="erp_code")
	@Title("ERP编号(供应商)")
	private String erpCode ;
	/**
	 * 修改人ID
	*/
	@Size(max=36)
	@JoinColumn(name="uxid",table="sys_user",referencedColumnName="id")
	@Title("修改人ID")
	private String uxid ;
	/**
	 * 启用状态
	 * 
	 * [0 停用 , 1 启用]
	*/
	@Size(max=1)
	@Column(name="status")
	@Title("启用状态")
	private String status ;
	/**
	 * 最后更新时间
	*/
	@Column(name="last_update_datetime")
	@Title("最后更新时间")
	private java.util.Date lastUpdateDatetime ;
	/**
	 * 数据版本
	*/
	@Column(name="version")
	@Title("数据版本")
	private int version ;

	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getUserId ()
	{
		return userId ;
	}
	
	public void setUserId (String userId )
	{
		this.userId = userId;
	}
	public String getOrgId ()
	{
		return orgId ;
	}
	
	public void setOrgId (String orgId )
	{
		this.orgId = orgId;
	}
	public String getOrgCode ()
	{
		return orgCode ;
	}
	
	public void setOrgCode (String orgCode )
	{
		this.orgCode = orgCode;
	}
	public String getEname ()
	{
		return ename ;
	}
	
	public void setEname (String ename )
	{
		this.ename = ename;
	}
	public int getOrdinal ()
	{
		return ordinal ;
	}
	
	public void setOrdinal (int ordinal )
	{
		this.ordinal = ordinal;
	}
	public String getCname ()
	{
		return cname ;
	}
	
	public void setCname (String cname )
	{
		this.cname = cname;
	}
	public String getTel ()
	{
		return tel ;
	}
	
	public void setTel (String tel )
	{
		this.tel = tel;
	}
	public String getEmail ()
	{
		return email ;
	}
	
	public void setEmail (String email )
	{
		this.email = email;
	}
	public String getQq ()
	{
		return qq ;
	}
	
	public void setQq (String qq )
	{
		this.qq = qq;
	}
	public String getJob ()
	{
		return job ;
	}
	
	public void setJob (String job )
	{
		this.job = job;
	}
	public String getOthers ()
	{
		return others ;
	}
	
	public void setOthers (String others )
	{
		this.others = others;
	}
	public String getScmCode ()
	{
		return scmCode ;
	}
	
	public void setScmCode (String scmCode )
	{
		this.scmCode = scmCode;
	}
	public String getErpCode ()
	{
		return erpCode ;
	}
	
	public void setErpCode (String erpCode )
	{
		this.erpCode = erpCode;
	}
	public String getUxid ()
	{
		return uxid ;
	}
	
	public void setUxid (String uxid )
	{
		this.uxid = uxid;
	}
	public String getStatus ()
	{
		return status ;
	}
	
	public void setStatus (String status )
	{
		this.status = status;
	}
	public java.util.Date getLastUpdateDatetime ()
	{
		return lastUpdateDatetime ;
	}
	
	public void setLastUpdateDatetime (java.util.Date lastUpdateDatetime )
	{
		this.lastUpdateDatetime = lastUpdateDatetime;
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
		return "SysUserOrg [" +" id=" +  id   +", userId=" +  userId   +", orgId=" +  orgId   +", orgCode=" +  orgCode   +", ename=" +  ename   +", ordinal=" +  ordinal   +", cname=" +  cname   +", tel=" +  tel   +", email=" +  email   +", qq=" +  qq   +", job=" +  job   +", others=" +  others   +", scmCode=" +  scmCode   +", erpCode=" +  erpCode   +", uxid=" +  uxid   +", status=" +  status   +", lastUpdateDatetime=" +  lastUpdateDatetime   +", version=" +  version   
			 + "]";
	}

}