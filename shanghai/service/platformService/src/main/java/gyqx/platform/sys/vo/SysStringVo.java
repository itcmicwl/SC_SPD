package gyqx.platform.sys.vo;

/**
 * 系统资源定义Vo
 */
public class SysStringVo {
	/**
	 * ID
	 */
	private String id;
	/**
	 * 名称
	 */
	private String ename;
	/**
	 * 值
	 */
	private String val;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * 分类
	 * [0 字符串,1 sql ,2 错误信息]
	 */
	private String kind;
	/**
	 * 处理状态
	 * [0 不启用,1 启用]
	 */
	private String status;
	/**
	 * 操作员ID 新增或修改的操作员ID
	 */
	private String uid;
	/**
	 * 修改时间
	 */
	private java.util.Date mt;
	/**
	 * 并发控制
	 */
	private int version;
	/**
	 * 字典名
	 */
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SysString [" + " id=" + id + ", ename=" + ename + ", val=" + val + ", remark=" + remark + ", kind="
				+ kind + ", status=" + status + ", uid=" + uid + ", mt=" + mt + ", version=" + version + "]";
	}

}