package gyqx.spdhdi.orderMgr.vo;

import java.util.List;

import gyqx.spdhdi.po.ReturnBillList;

public class ReturnBillListVo extends ReturnBillList{
	private List<ReturnBillUniqueListVo> lstUniqueList;
	
	//添加 退货确认单中需要显示的字段 关联v_pro_hos_goodsinfo视图
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
	
	private String provId;
	private String returnCompanyId;
                 
	

	public List<ReturnBillUniqueListVo> getLstUniqueList() {
		return lstUniqueList;
	}

	public void setLstUniqueList(List<ReturnBillUniqueListVo> lstUniqueList) {
		this.lstUniqueList = lstUniqueList;
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

	public String getProvId() {
		return provId;
	}

	public String getReturnCompanyId() {
		return returnCompanyId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public void setReturnCompanyId(String returnCompanyId) {
		this.returnCompanyId = returnCompanyId;
	}

}
