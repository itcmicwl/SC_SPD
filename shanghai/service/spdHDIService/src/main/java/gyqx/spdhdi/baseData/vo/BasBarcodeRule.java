package gyqx.spdhdi.baseData.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BasBarcodeRule {
    private String id;

    private Integer barcodeLen;

    private String barleader;

    private Integer consumablesPosition;

    private Integer consumablesLen;

    private String isMain;

    private Integer periodPosition;

    private String periodFormat;

    private Integer productionPosition;

    private Integer productionLen;

    private Integer sign1Position;

    private String sign1;

    private Integer sign2Position;

    private String sign2;

    private Integer tracknumPosition;

    private Integer tracknumLen;

    private String flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
        this.barleader = barleader == null ? null : barleader.trim();
    }

    public Integer getConsumablesPosition() {
        return consumablesPosition;
    }

    public void setConsumablesPosition(Integer consumablesPosition) {
        this.consumablesPosition = consumablesPosition;
    }

    public Integer getConsumablesLen() {
        return consumablesLen;
    }

    public void setConsumablesLen(Integer consumablesLen) {
        this.consumablesLen = consumablesLen;
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain == null ? null : isMain.trim();
    }

    public Integer getPeriodPosition() {
        return periodPosition;
    }

    public void setPeriodPosition(Integer periodPosition) {
        this.periodPosition = periodPosition;
    }

    public String getPeriodFormat() {
        return periodFormat;
    }

    public void setPeriodFormat(String periodFormat) {
        this.periodFormat = periodFormat == null ? null : periodFormat.trim();
    }

    public Integer getProductionPosition() {
        return productionPosition;
    }

    public void setProductionPosition(Integer productionPosition) {
        this.productionPosition = productionPosition;
    }

    public Integer getProductionLen() {
        return productionLen;
    }

    public void setProductionLen(Integer productionLen) {
        this.productionLen = productionLen;
    }

    public Integer getSign1Position() {
        return sign1Position;
    }

    public void setSign1Position(Integer sign1Position) {
        this.sign1Position = sign1Position;
    }

    public String getSign1() {
        return sign1;
    }

    public void setSign1(String sign1) {
        this.sign1 = sign1 == null ? null : sign1.trim();
    }

    public Integer getSign2Position() {
        return sign2Position;
    }

    public void setSign2Position(Integer sign2Position) {
        this.sign2Position = sign2Position;
    }

    public String getSign2() {
        return sign2;
    }

    public void setSign2(String sign2) {
        this.sign2 = sign2 == null ? null : sign2.trim();
    }

    public Integer getTracknumPosition() {
        return tracknumPosition;
    }

    public void setTracknumPosition(Integer tracknumPosition) {
        this.tracknumPosition = tracknumPosition;
    }

    public Integer getTracknumLen() {
        return tracknumLen;
    }

    public void setTracknumLen(Integer tracknumLen) {
        this.tracknumLen = tracknumLen;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    @Override
    public String toString() {
        return "BasBarcodeRule{" +
                "id='" + id + '\'' +
                ", barcodeLen=" + barcodeLen +
                ", barleader='" + barleader + '\'' +
                ", consumablesPosition=" + consumablesPosition +
                ", consumablesLen=" + consumablesLen +
                ", isMain='" + isMain + '\'' +
                ", periodPosition=" + periodPosition +
                ", periodFormat='" + periodFormat + '\'' +
                ", productionPosition=" + productionPosition +
                ", productionLen=" + productionLen +
                ", sign1Position=" + sign1Position +
                ", sign1='" + sign1 + '\'' +
                ", sign2Position=" + sign2Position +
                ", sign2='" + sign2 + '\'' +
                ", tracknumPosition=" + tracknumPosition +
                ", tracknumLen=" + tracknumLen +
                ", flag='" + flag + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BasBarcodeRule that = (BasBarcodeRule) o;

        return new EqualsBuilder()
                .append(barcodeLen, that.barcodeLen)
                .append(barleader, that.barleader)
                .append(consumablesPosition, that.consumablesPosition)
                .append(consumablesLen, that.consumablesLen)
                .append(isMain, that.isMain)
                .append(periodPosition, that.periodPosition)
                .append(periodFormat, that.periodFormat)
                .append(productionPosition, that.productionPosition)
                .append(productionLen, that.productionLen)
                .append(sign1Position, that.sign1Position)
                .append(sign1, that.sign1)
                .append(sign2Position, that.sign2Position)
                .append(sign2, that.sign2)
                .append(tracknumPosition, that.tracknumPosition)
                .append(tracknumLen, that.tracknumLen)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(barcodeLen)
                .append(barleader)
                .append(consumablesPosition)
                .append(consumablesLen)
                .append(isMain)
                .append(periodPosition)
                .append(periodFormat)
                .append(productionPosition)
                .append(productionLen)
                .append(sign1Position)
                .append(sign1)
                .append(sign2Position)
                .append(sign2)
                .append(tracknumPosition)
                .append(tracknumLen)
                .toHashCode();
    }
}