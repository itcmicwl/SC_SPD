
package gyqx.mdqc.mycerts.vo;

import java.util.List;

import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import gyqx.spdhdi.po.ProvMfrsInfo;

public class MyMfrsVo extends ProvMfrsInfo {
	private static final long serialVersionUID = 1L;

	private List<ProvMfrsCertificateVo> provMfrsCertificateVo;

	public List<ProvMfrsCertificateVo> getProvMfrsCertificateVo() {
		return provMfrsCertificateVo;
	}

	public void setProvMfrsCertificateVo(List<ProvMfrsCertificateVo> provMfrsCertificateVo) {
		this.provMfrsCertificateVo = provMfrsCertificateVo;
	}

	/**
	 * 公司/医院编号
	 */
	private String code;
	/**
	 * 公司/医院名称
	 */
	private String cname;
	/**
	 * 公司/医院别名
	 */
	private String anotherName;
	/**
	 * 拼音码
	 */
	private String shortPinyin;
	/**
	 * 营业执照注册号
	 */
	private String regCode;
	/**
	 * 经营许可证编号
	 */
	private String jyxkCode;
	/**
	 * 纳税人识别号
	 */
	private String nsrsbCode;
	/**
	 * 机构类型
	 */
	private String kind;
	/**
	 * 生产厂家类型
	 */
	private String mfrsKind;
	/**
	 * 全国总代理
	 */
	private String agentName;
	/**
	 * 生产许可证
	 */
	private String productCode;
	/**
	 * 生产备案证
	 */
	private String recordCard;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 法人代表
	 */
	private String legal;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 联系人
	 */
	private String linkman;
	/**
	 * 联系方式
	 */
	private String contactWay;
	/**
	 * 传真
	 */
	private String contactFax;
	/**
	 * 企业logo
	 */
	private String logo;
	/**
	 * 邮政编码
	 */
	private String postCode;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 内部ERP编码
	 */
	private String erpCode;
	/**
	 * 是否供货单位
	 */
	private String isSupply;
	/**
	 * 是否三证合一
	 */
	private String isThreeInOne;
	/**
	 * 统一码
	 */
	private String masterCode;
	/**
	 * 省 手工输入
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区县
	 */
	private String area;
	/**
	 * 区域代码
	 */
	private String areaCode;

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

	public String getAnotherName() {
		return anotherName;
	}

	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}

	public String getShortPinyin() {
		return shortPinyin;
	}

	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getJyxkCode() {
		return jyxkCode;
	}

	public void setJyxkCode(String jyxkCode) {
		this.jyxkCode = jyxkCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNsrsbCode() {
		return nsrsbCode;
	}

	public void setNsrsbCode(String nsrsbCode) {
		this.nsrsbCode = nsrsbCode;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getMfrsKind() {
		return mfrsKind;
	}

	public void setMfrsKind(String mfrsKind) {
		this.mfrsKind = mfrsKind;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getRecordCard() {
		return recordCard;
	}

	public void setRecordCard(String recordCard) {
		this.recordCard = recordCard;
	}

	public String getLegal() {
		return legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getContactFax() {
		return contactFax;
	}

	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public String getIsSupply() {
		return isSupply;
	}

	public void setIsSupply(String isSupply) {
		this.isSupply = isSupply;
	}

	public String getIsThreeInOne() {
		return isThreeInOne;
	}

	public void setIsThreeInOne(String isThreeInOne) {
		this.isThreeInOne = isThreeInOne;
	}

	public String getMasterCode() {
		return masterCode;
	}

	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}