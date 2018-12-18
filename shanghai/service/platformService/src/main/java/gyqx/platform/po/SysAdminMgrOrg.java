
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
 * 管理员可管理机构定义表
*/
@Table(name="sys_admin_mgr_org")
public class SysAdminMgrOrg implements IBean
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
	 * 管理员ID
	*/
	@Size(max=36)
	@JoinColumn(name="admin_id",table="sys_user",referencedColumnName="id")
	@Title("管理员ID")
	private String adminId ;
	/**
	 * 机构IDs
	 * sys_org的id的结点列表，多条用分号分隔。某机构id 表示，此机构及以下。
	*/
	@Size(max=512)
	@Column(name="org_ids")
	@Title("机构IDs")
	private String orgIds ;
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
	public String getAdminId ()
	{
		return adminId ;
	}
	
	public void setAdminId (String adminId )
	{
		this.adminId = adminId;
	}
	public String getOrgIds ()
	{
		return orgIds ;
	}
	
	public void setOrgIds (String orgIds )
	{
		this.orgIds = orgIds;
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
		return "SysAdminMgrOrg [" +" id=" +  id   +", adminId=" +  adminId   +", orgIds=" +  orgIds   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}