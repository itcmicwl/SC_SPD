package gyqx.spdhdi.myGoods.vo;

public class ProvGoodsHosVo {

	private String goodsName;//供应商产品名称
	private String provId;//供应商ID
	private String mfrsId;
	private String hosId;//医院ID
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getProvId() {
		return provId;
	}
	public void setProvId(String provId) {
		this.provId = provId;
	}
	public String getMfrsId() {
		return mfrsId;
	}
	public void setMfrsId(String mfrsId) {
		this.mfrsId = mfrsId;
	}
	public String getHosId() {
		return hosId;
	}
	public void setHosId(String hosId) {
		this.hosId = hosId;
	}
	
}
