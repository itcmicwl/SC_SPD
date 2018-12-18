package gyqx.platform.sys.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public class SysDictValueVo {
	/**
	 * ID
	*/
	@Id
	@Size(max=36)
	@Column(name="id")
	private String id ;
	/**
	 * 字典分类id
	*/
	@Size(max=36)
	@Column(name="dict_id")
	private String dictId ;
	/**
	 * 排序
	*/
	@Column(name="ordinal")
	private int ordinal ;
	/**
	 * 字典名称
	 * 如：男
	*/
	@Size(max=36)
	@Column(name="ename")
	private String ename ;
	/**
	 * 字典值
	 * 如：01
	*/
	@Size(max=36)
	@Column(name="val")
	private String val ;
	/**
	 * 扩展信息
	*/
	@Size(max=36)
	@Column(name="ext_field")
	private String extField ;
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
	
	/**
	 * 字典值对应字典项key
	 */
	private String dictName;
	/**
	 * 字典值对应字典项key的别名
	 */
	private String dictCname;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public int getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
	
	
	public String getExtField() {
		return extField;
	}
	public void setExtField(String extField) {
		this.extField = extField;
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
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getDictCname() {
		return dictCname;
	}
	public void setDictCname(String dictCname) {
		this.dictCname = dictCname;
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
}
