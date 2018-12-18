package gyqx.platform.sys.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Size;

public class SysModuleGroupVo {
	/**
	 * ID
	*/
	@Id
	@Size(max=36)
	@Column(name="id")
	private String id ;
	/**
	 * 子系统ID
	*/
	@Size(max=36)
	@JoinColumn(name="project_id",table="sys_project",referencedColumnName="id")
	private String projectId ;
	
	/**
	 * 分组名称
	*/
	@Size(max=255)
	@Column(name="ename")
	private String ename ;
	/**
	 * 别名
	 * 中文列名
	*/
	@Size(max=255)
	@Column(name="cname")
	private String cname ;
	/**
	 * 描述
	*/
	@Size(max=255)
	@Column(name="remark")
	private String remark ;
	/**
	 * 操作员ID
	 * 新增或修改的操作员ID
	*/
	@Size(max=36)
	@Column(name="uxid")
	private String uid ;
	/**
	 * 修改时间
	*/
	@Column(name="mt")
	private java.util.Date mt ;
	/**
	 * 并发控制
	*/
	@Column(name="version")
	private int version ;
	private int ordinal ;  
   
	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
	/**
	 * vo添加 project的 ename 和cname  code
	 * 
	 * @return
	 */
	private String projectEname;
	private String projectCname;
	private String projectCode;
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectEname() {
		return projectEname;
	}

	public void setProjectEname(String projectEname) {
		this.projectEname = projectEname;
	}

	public String getProjectCname() {
		return projectCname;
	}

	public void setProjectCname(String projectCname) {
		this.projectCname = projectCname;
	}

	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getProjectId ()
	{
		return projectId ;
	}
	
	public void setProjectId (String projectId )
	{
		this.projectId = projectId;
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
	public String getUid ()
	{
		return uid ;
	}
	
	public void setUid (String uid )
	{
		this.uid = uid;
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
		return "SysModuleGroup [" +" id=" +  id   +", projectId=" +  projectId   +", projectCode=" +  projectCode   +", ename=" +  ename   +", cname=" +  cname   +", remark=" +  remark   +", uid=" +  uid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}
