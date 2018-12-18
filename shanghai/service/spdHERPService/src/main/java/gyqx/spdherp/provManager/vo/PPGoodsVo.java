package gyqx.spdherp.provManager.vo;

import gyqx.spdherp.po.ProvProvGoods;

public class PPGoodsVo extends ProvProvGoods{

	private String goodsName;
	private String goodsGg;
	private String erpCode;
	private String certificateCode;
	private String made;
	private String mfrsName;
	private String unit;
	private String shortPinyin;
	public String getShortPinyin() {
		return shortPinyin;
	}
	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getGoodsGg() {
		return goodsGg;
	}
	public String getErpCode() {
		return erpCode;
	}
	public String getCertificateCode() {
		return certificateCode;
	}
	public String getMade() {
		return made;
	}
	public String getMfrsName() {
		return mfrsName;
	}
	public String getUnit() {
		return unit;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public void setGoodsGg(String goodsGg) {
		this.goodsGg = goodsGg;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	public void setMade(String made) {
		this.made = made;
	}
	public void setMfrsName(String mfrsName) {
		this.mfrsName = mfrsName;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
