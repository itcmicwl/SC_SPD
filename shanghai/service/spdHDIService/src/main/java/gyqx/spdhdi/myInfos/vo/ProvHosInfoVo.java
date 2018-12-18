
package gyqx.spdhdi.myInfos.vo;

/**
 * 供应商医院信息Vo
 */
public class ProvHosInfoVo {
	/**
	 * 集配商或者合作商
	 */
	private String collectorOrPartner;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 供应商ID
	 */
	private String provId;
	/**
	 * 供应商name
	 */
	private String provName;
	/**
	 * 医院ID
	 */
	private String hosId;
	/**
	 * 医院name
	 */
	private String hosName;
	/**
	 * 医院联系人
	 */
	private String hosLxr;
	/**
	 * 医院联系人电话
	 */
	private String hosLxrTelphonenum;
	/**
	 * 配送商联系人
	 */
	private String provLxr;
	/**
	 * 配送商联系人电话
	 */
	private String provLxrTelphonenum;
	/**
	 * 状态
	 * 
	 * 供应商医院对应状态
	 */
	private String status;
	/**
	 * 备注信息
	 */
	private String remark;
	/**
	 * 医院备注信息
	 */
	private String hosRemark;
	/**
	 * 操作员ID 新增或修改的操作员ID
	 */
	private String uid;
	/**
	 * 建档时间
	 */
	private java.util.Date fillDate;
	/**
	 * 最后更新时间
	 */
	private java.util.Date lastUpdateDatetime;
	/**
	 * 数据版本
	 */
	private int version;
	
	/**
	 * 标志
	 */
	private String flag;
	
	/**
	 * 医院是否关注
	 */
	private String isHosView;
	
	/**
	 * 集配配商id
	 */
    private String collectId;
    /**
     * 供应商信息
     */
    private String code;//供应商编号
    private String cname;//供应商名称
    private String shortPinyin;//拼音码
    private String legal;//联系人
    private String linkman;//法人代表
    private String contactWay;//联系方式
    
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHosRemark() {
		return hosRemark;
	}

	public void setHosRemark(String hosRemark) {
		this.hosRemark = hosRemark;
	}

	public String getProvId() {
		return provId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getHosName() {
		return hosName;
	}

	public void setHosName(String hosName) {
		this.hosName = hosName;
	}

	public String getHosLxr() {
		return hosLxr;
	}

	public void setHosLxr(String hosLxr) {
		this.hosLxr = hosLxr;
	}

	public String getHosLxrTelphonenum() {
		return hosLxrTelphonenum;
	}

	public void setHosLxrTelphonenum(String hosLxrTelphonenum) {
		this.hosLxrTelphonenum = hosLxrTelphonenum;
	}

	public String getProvLxr() {
		return provLxr;
	}

	public void setProvLxr(String provLxr) {
		this.provLxr = provLxr;
	}

	public String getProvLxrTelphonenum() {
		return provLxrTelphonenum;
	}

	public void setProvLxrTelphonenum(String provLxrTelphonenum) {
		this.provLxrTelphonenum = provLxrTelphonenum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public java.util.Date getFillDate() {
		return fillDate;
	}

	public void setFillDate(java.util.Date fillDate) {
		this.fillDate = fillDate;
	}

	public java.util.Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(java.util.Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCollectorOrPartner() {
		return collectorOrPartner;
	}

	public void setCollectorOrPartner(String collectorOrPartner) {
		this.collectorOrPartner = collectorOrPartner;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIsHosView() {
		return isHosView;
	}

	public void setIsHosView(String isHosView) {
		this.isHosView = isHosView;
	}

	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getShortPinyin() {
		return shortPinyin;
	}

	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
	}

	public String getLegal() {
		return legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	@Override
	public String toString() {
		return "ProvHosInfoVo [collectorOrPartner=" + collectorOrPartner + ", id=" + id + ", provId=" + provId
				+ ", provName=" + provName + ", hosId=" + hosId + ", hosName=" + hosName + ", hosLxr=" + hosLxr
				+ ", hosLxrTelphonenum=" + hosLxrTelphonenum + ", provLxr=" + provLxr + ", provLxrTelphonenum="
				+ provLxrTelphonenum + ", status=" + status + ", remark=" + remark + ", hosRemark=" + hosRemark
				+ ", uid=" + uid + ", fillDate=" + fillDate + ", lastUpdateDatetime=" + lastUpdateDatetime
				+ ", version=" + version + ", flag=" + flag + ", isHosView=" + isHosView + ", collectId=" + collectId
				+ ", code=" + code + ", cname=" + cname + ", shortPinyin=" + shortPinyin + ", legal=" + legal
				+ ", linkman=" + linkman + ", contactWay=" + contactWay + "]";
	}

}