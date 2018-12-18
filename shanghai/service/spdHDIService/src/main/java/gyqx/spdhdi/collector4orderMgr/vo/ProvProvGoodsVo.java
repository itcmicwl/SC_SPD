package gyqx.spdhdi.collector4orderMgr.vo;

import java.io.Serializable;

import gyqx.spdherp.po.ProvProvGoods;

public class ProvProvGoodsVo extends ProvProvGoods implements Serializable{

	private static final long serialVersionUID = 4745390301731523365L;
	private String hosName;
	private String collectorName;
	private String provName;
	
	private String provGoodsId; //v.prov_goodsid
	private String goodsGg; // v.goods_gg
	private String goodsMrfsId;// v.mfrs_id
	private String mfrsName;// v.mfrs_name
	private String made;  //v.made
	private String provGoodsErpCode;//v.prov_erp_code
	private String provGoodsErpName;//v.goods_name
	private String returnUnit;//v.his_unit	
	private String returnScale;//v.unit_rate
	private String sendUnit;  //v.unit
	private String sendPrice; //v.sale_price
	
	private String hosGoodsCode;
	private String hosGoodsName;
	private String hosGoodsId;
	public String getHosName() {
		return hosName;
	}
	public String getCollectorName() {
		return collectorName;
	}
	public String getProvName() {
		return provName;
	}
	public String getProvGoodsId() {
		return provGoodsId;
	}
	public String getGoodsGg() {
		return goodsGg;
	}
	public String getGoodsMrfsId() {
		return goodsMrfsId;
	}
	public String getMfrsName() {
		return mfrsName;
	}
	public String getMade() {
		return made;
	}
	public String getProvGoodsErpCode() {
		return provGoodsErpCode;
	}
	public String getProvGoodsErpName() {
		return provGoodsErpName;
	}
	public String getReturnUnit() {
		return returnUnit;
	}
	public String getReturnScale() {
		return returnScale;
	}
	public String getSendUnit() {
		return sendUnit;
	}
	public String getSendPrice() {
		return sendPrice;
	}
	public String getHosGoodsCode() {
		return hosGoodsCode;
	}
	public String getHosGoodsName() {
		return hosGoodsName;
	}
	public void setHosName(String hosName) {
		this.hosName = hosName;
	}
	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}
	public void setProvName(String provName) {
		this.provName = provName;
	}
	public void setProvGoodsId(String provGoodsId) {
		this.provGoodsId = provGoodsId;
	}
	public void setGoodsGg(String goodsGg) {
		this.goodsGg = goodsGg;
	}
	public void setGoodsMrfsId(String goodsMrfsId) {
		this.goodsMrfsId = goodsMrfsId;
	}
	public void setMfrsName(String mfrsName) {
		this.mfrsName = mfrsName;
	}
	public void setMade(String made) {
		this.made = made;
	}
	public void setProvGoodsErpCode(String provGoodsErpCode) {
		this.provGoodsErpCode = provGoodsErpCode;
	}
	public void setProvGoodsErpName(String provGoodsErpName) {
		this.provGoodsErpName = provGoodsErpName;
	}
	public void setReturnUnit(String returnUnit) {
		this.returnUnit = returnUnit;
	}
	public void setReturnScale(String returnScale) {
		this.returnScale = returnScale;
	}
	public void setSendUnit(String sendUnit) {
		this.sendUnit = sendUnit;
	}
	public void setSendPrice(String sendPrice) {
		this.sendPrice = sendPrice;
	}
	public void setHosGoodsCode(String hosGoodsCode) {
		this.hosGoodsCode = hosGoodsCode;
	}
	public void setHosGoodsName(String hosGoodsName) {
		this.hosGoodsName = hosGoodsName;
	}
	public String getHosGoodsId() {
		return hosGoodsId;
	}
	public void setHosGoodsId(String hosGoodsId) {
		this.hosGoodsId = hosGoodsId;
	}
	
	
}
