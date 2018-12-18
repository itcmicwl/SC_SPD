package gyqx.spdhdi.baseData.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

/**
 * @Auther Liangwu
 * @Date 17-7-14 下午3:05
 */
public class BarcodeParseResult {
    // 主条码
    private String barcode;
    // 条码长
    private Integer barcodeLen;
    // 前导码
    private String barleader;
    // 特征码1
    private String sign1;
    // 特征码2
    private String sign2;
    // 是否主码
    private String mainFlag;
    // 对应采购目录商品ID
    private String goodsId;
    // 对应采购目录或产品列表商品名称
    private String goodsName;
    // 对应产品列表商品规格
    private String goodsSpec;
    // 条码解析的耗材码
    private String goodsNo;
    // 条码解析的批次号
    private String batchNo;
    // 条码解析的追踪码
    private String trackNo;
    // 条码解析的有效期
    private Date expiredDate;

    // 前端传入供应商ID
    private String provId;
    // 前端传入医院ID
    private String hosId;
    // 副条码
    private String slaverBarcode;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getBarcodeLen() {
        return barcodeLen;
    }

    public void setBarcodeLen(Integer barcodeLen) {
        this.barcodeLen = barcodeLen;
    }

    public String getBarleader() {
        return barleader;
    }

    public void setBarleader(String barleader) {
        this.barleader = barleader;
    }

    public String getSign1() {
        return sign1;
    }

    public void setSign1(String sign1) {
        this.sign1 = sign1;
    }

    public String getSign2() {
        return sign2;
    }

    public void setSign2(String sign2) {
        this.sign2 = sign2;
    }

    public String getMainFlag() {
        return mainFlag;
    }

    public void setMainFlag(String mainFlag) {
        this.mainFlag = mainFlag;
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

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getSlaverBarcode() {
        return slaverBarcode;
    }

    public void setSlaverBarcode(String slaverBarcode) {
        this.slaverBarcode = slaverBarcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BarcodeParseResult that = (BarcodeParseResult) o;

        return new EqualsBuilder()
                .append(goodsId, that.goodsId)
                .append(goodsNo, that.goodsNo)
                .append(batchNo, that.batchNo)
                .append(trackNo, that.trackNo)
                .append(expiredDate, that.expiredDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(goodsId)
                .append(goodsNo)
                .append(batchNo)
                .append(trackNo)
                .append(expiredDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "BarcodeParseResult{" +
                "barcode='" + barcode + '\'' +
                ", barcodeLen=" + barcodeLen +
                ", barleader='" + barleader + '\'' +
                ", sign1='" + sign1 + '\'' +
                ", sign2='" + sign2 + '\'' +
                ", mainFlag='" + mainFlag + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", goodsNo='" + goodsNo + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", trackNo='" + trackNo + '\'' +
                ", expiredDate=" + expiredDate +
                '}';
    }
}