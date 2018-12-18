package gyqx.spdherp.settleCenter.vo;

import gyqx.spdherp.po.HosOutBalanceDetail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by moonb on 2017/9/14.
 */
public class HosOutBalanceDetailVo extends HosOutBalanceDetail implements Serializable {
    private static final long serialVersionUID = -4252276689533747736L;
    private String mfrsName;
    private String subProvName;
    private String subProvId;
    private String subProvErpCode;
    private BigDecimal receiveTemprature;//收货温度
    private BigDecimal sendTemprature;//发货温度
    private Date receiveDate;//收货日期
    private Date deliveryDate;//发货日期
    private BigDecimal purPrice;//采购价
    private String transportType;//货运方式，自运、自提、外运
    private String goodsErpCode;
    private BigDecimal settleQty;
    private int purMode;//采购模式
    
    
    public String getGoodsErpCode() {
		return goodsErpCode;
	}

	public void setGoodsErpCode(String goodsErpCode) {
		this.goodsErpCode = goodsErpCode;
	}

	public BigDecimal getSettleQty() {
		return settleQty;
	}

	public void setSettleQty(BigDecimal settleQty) {
		this.settleQty = settleQty;
	}

	public int getPurMode() {
		return purMode;
	}

	public void setPurMode(int purMode) {
		this.purMode = purMode;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public BigDecimal getPurPrice() {
		return purPrice;
	}

	public void setPurPrice(BigDecimal purPrice) {
		this.purPrice = purPrice;
	}
    
    public BigDecimal getReceiveTemprature() {
		return receiveTemprature;
	}

	public void setReceiveTemprature(BigDecimal receiveTemprature) {
		this.receiveTemprature = receiveTemprature;
	}

	public BigDecimal getSendTemprature() {
		return sendTemprature;
	}

	public void setSendTemprature(BigDecimal sendTemprature) {
		this.sendTemprature = sendTemprature;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getSubProvName() {
		return subProvName;
	}

	public void setSubProvName(String subProvName) {
		this.subProvName = subProvName;
	}

	public String getSubProvId() {
		return subProvId;
	}

	public String getSubProvErpCode() {
		return subProvErpCode;
	}

	public void setSubProvErpCode(String subProvErpCode) {
		this.subProvErpCode = subProvErpCode;
	}

	public void setSubProvId(String subProvId) {
		this.subProvId = subProvId;
	}

    public String getMfrsName() {
        return mfrsName;
    }

    public void setMfrsName(String mfrsName) {
        this.mfrsName = mfrsName;
    }
}
