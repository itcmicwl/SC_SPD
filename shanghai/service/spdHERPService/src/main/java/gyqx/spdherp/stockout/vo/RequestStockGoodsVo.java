package gyqx.spdherp.stockout.vo;

import common.db.meta.Title;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Auther Liangwu
 * @Date 17-8-22 上午9:45
 */
public class RequestStockGoodsVo {
    /**
     * 请购单ID
     */
    @Column(name = "bill_id")
    @Title("请购单ID")
    private String billId;
    /**
     * ID
     */
    @Column(name = "bs_id")
    @Title("请购单明细ID")
    private String bsId;
    /**
     * 商品ID
     */
    @Column(name = "goods_id")
    @Title("商品ID")
    private String goodsId;
    /**
     * 名称
     */
    @Column(name = "goods_name")
    @Title("名称")
    private String goodsName;
    /**
     * 规格型号
     */
    @Column(name = "goods_gg")
    @Title("规格型号")
    private String goodsGg;
    /**
     * 包装
     */
    @Column(name = "packeage")
    @Title("包装")
    private Double pkg;
    /**
     * 单位
     */
    @Column(name = "unit")
    @Title("单位")
    private String unit;
    /**
     * 单价
     */
    @Column(name = "price")
    @Title("单价")
    private Double price;
    /**
     * 生产厂家ID
     */
    @Column(name = "mfrs_id")
    @Title("生产厂家ID")
    private String mfrsId;
    /**
     * 厂家名称
     */
    @Column(name = "mfrs_name")
    @Title("厂家名称")
    private String mfrsName;
    /**
     * 生产国
     */
    @Column(name = "made")
    @Title("生产国")
    private String made;
    /**
     * 请购数量
     */
    @Column(name = "req_qty")
    @Title("请购数量")
    private Double reqQty;
    /**
     * 库房可发货数量
     */
    @Column(name = "avl_qty")
    @Title("库房可发货数量")
    private Double avlQty;
    /**
     * 库房占用数量
     */
    @Column(name = "lock_qty")
    @Title("库房占用数量")
    private Double lockQty;
    /**
     * 已发货数量
     */
    @Column(name = "out_qty")
    @Title("已发货数量")
    private Double outQty;
    /**
     * 已发货数量
     */
    @Column(name = "undone_qty")
    @Title("待发货数量")
    private Double undoneQty;
    /**
     * 包装数量
     */
    @Column(name = "packet_code")
    @Title("包装数量")
    private Double packetCode;
    /**
     * 是否定数包管理
     */
    @Column(name = "is_packet")
    @Title("是否定数包管理")
    private String isPacket;
    /**
     * 是否唯一码管理
     */
    @Column(name = "is_unique")
    @Title("是否唯一码管理")
    private String isUnique;
    /**
     * 产品出厂条码
     */
    @Column(name = "mfrs_code")
    @Title("产品出厂条码")
    private String mfrsCode;
    /**
     * 制单日期
     */
    @Column(name = "request_date")
    @Title("制单日期")
    private Date requestDate;
    /**
     * 收货地址ID
     */
    @Column(name = "rec_address_id")
    @Title("收货地址ID")
    private String recAddressId;
    /**
     * 联系人
     */
    @Column(name = "rec_linkman")
    @Title("联系人")
    private String recLinkman;
    /**
     * 联系电话
     */
    @Column(name = "rec_linkman_phone")
    @Title("联系电话")
    private String recLinkmanPhone;
    /**
     * 收货地址
     */
    @Column(name = "rec_address")
    @Title("收货地址")
    private String recAddress;

    private Double qty;

    private String provId;

    private String provCode;

    private String provName;

    private String batchNo;
    
    private String certificateCode;


    public String getCertificateCode() {
		return certificateCode;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	private String batchId;

    private String uniqueCode;
    /**
     * 库存表ID
     */
    private String stockTableId;

    private String sterilizationDate;

    private String sterilizationCode;

    private String sterilizationEndDate;

    private String expdtEndDate;

    public String getStockTableId() {
        return stockTableId;
    }

    public void setStockTableId(String stockTableId) {
        this.stockTableId = stockTableId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBsId() {
        return bsId;
    }

    public void setBsId(String bsId) {
        this.bsId = bsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsGg() {
        return goodsGg;
    }

    public void setGoodsGg(String goodsGg) {
        this.goodsGg = goodsGg;
    }

    public Double getPkg() {
        return pkg;
    }

    public void setPkg(Double pkg) {
        this.pkg = pkg;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMfrsId() {
        return mfrsId;
    }

    public void setMfrsId(String mfrsId) {
        this.mfrsId = mfrsId;
    }

    public String getMfrsName() {
        return mfrsName;
    }

    public void setMfrsName(String mfrsName) {
        this.mfrsName = mfrsName;
    }

    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }

    public Double getReqQty() {
        return reqQty;
    }

    public void setReqQty(Double reqQty) {
        this.reqQty = reqQty;
    }

    public Double getAvlQty() {
        return avlQty;
    }

    public void setAvlQty(Double avlQty) {
        this.avlQty = avlQty;
    }

    public Double getLockQty() {
        return lockQty;
    }

    public void setLockQty(Double lockQty) {
        this.lockQty = lockQty;
    }

    public Double getOutQty() {
        return outQty;
    }

    public void setOutQty(Double outQty) {
        this.outQty = outQty;
    }

    public Double getUndoneQty() {
        return undoneQty;
    }

    public void setUndoneQty(Double undoneQty) {
        this.undoneQty = undoneQty;
    }

    public Double getPacketCode() {
        return packetCode;
    }

    public void setPacketCode(Double packetCode) {
        this.packetCode = packetCode;
    }

    public String getIsPacket() {
        return isPacket;
    }

    public void setIsPacket(String isPacket) {
        this.isPacket = isPacket;
    }

    public String getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(String isUnique) {
        this.isUnique = isUnique;
    }

    public String getMfrsCode() {
        return mfrsCode;
    }

    public void setMfrsCode(String mfrsCode) {
        this.mfrsCode = mfrsCode;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRecAddressId() {
        return recAddressId;
    }

    public void setRecAddressId(String recAddressId) {
        this.recAddressId = recAddressId;
    }

    public String getRecLinkman() {
        return recLinkman;
    }

    public void setRecLinkman(String recLinkman) {
        this.recLinkman = recLinkman;
    }

    public String getRecLinkmanPhone() {
        return recLinkmanPhone;
    }

    public void setRecLinkmanPhone(String recLinkmanPhone) {
        this.recLinkmanPhone = recLinkmanPhone;
    }

    public String getRecAddress() {
        return recAddress;
    }

    public void setRecAddress(String recAddress) {
        this.recAddress = recAddress;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getSterilizationDate() {
        return sterilizationDate;
    }

    public void setSterilizationDate(String sterilizationDate) {
        this.sterilizationDate = sterilizationDate;
    }

    public String getSterilizationCode() {
        return sterilizationCode;
    }

    public void setSterilizationCode(String sterilizationCode) {
        this.sterilizationCode = sterilizationCode;
    }

    public String getSterilizationEndDate() {
        return sterilizationEndDate;
    }

    public void setSterilizationEndDate(String sterilizationEndDate) {
        this.sterilizationEndDate = sterilizationEndDate;
    }

    public String getExpdtEndDate() {
        return expdtEndDate;
    }

    public void setExpdtEndDate(String expdtEndDate) {
        this.expdtEndDate = expdtEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RequestStockGoodsVo that = (RequestStockGoodsVo) o;

        return new EqualsBuilder()
                .append(goodsId, that.goodsId)
                .append(batchNo, that.batchNo)
                .append(sterilizationCode, that.sterilizationCode)
                .append(expdtEndDate, that.expdtEndDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(goodsId)
                .append(batchNo)
                .append(sterilizationCode)
                .append(expdtEndDate)
                .toHashCode();
    }
}
