package gyqx.platform.sys.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SysModuleVo {
	/**
	 * ID
	*/
	private String id ;
	/**
	 * 编码
	*/
	private String code ;
	/**
	 * 分组ID
	 * 从字典定义表中读取
	*/
	private String groupId ;
	/**
	 * 系统Code
	*/
	private String projectCode ;
	/**
	 * 排序
	*/
	private int ordinal ;
	/**
	 * 名称
	*/
	private String eName ;
	/**
	 * 别名
	 * 中文列名
	*/
	private String cname ;
	/**
	 * 描述
	*/
	private String remark ;
	/**
	 * 操作员ID
	 * 新增或修改的操作员ID
	*/
	private String uid ;
	/**
	 * 修改时间
	*/
	private java.util.Date mt ;
	/**
	 * 并发控制
	*/
	private int version ;
	private String projectName;
	private String groupName;
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * 
	 * projectName ,projectCode ,groupName
	 */



	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
	public String getEname() {
		return eName;
	}
	public void setEname(String eName) {
		this.eName = eName;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public java.util.Date getMt() {
		return mt;
	}
	public void setMt(java.util.Date mt) {
		this.mt = mt;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}
