
package gyqx.spdhdi.mycerts.vo;

import java.util.Date;

public class DlgBasRegInfoVo {
    /**
     * ID
     */
    private String id;
    /**
     * 公司/医院ID
     */
    private String mfrsId;
    /**
     * 证照类型
     */
    private String regKind;
    /**
     * 证件编号
     */
    private String certificateCode;
    /**
     * 有效开始日期
     */
    private Date expdtBeginDate;
    /**
     * 有效结束日期
     */
    private Date expdtEndDate;
    /**
     * 厂家名称
     */
    private String mfrsName;

    private String productName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMfrsId() {
        return mfrsId;
    }

    public void setMfrsId(String mfrsId) {
        this.mfrsId = mfrsId;
    }

    public String getRegKind() {
        return regKind;
    }

    public void setRegKind(String regKind) {
        this.regKind = regKind;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public Date getExpdtBeginDate() {
        return expdtBeginDate;
    }

    public void setExpdtBeginDate(Date expdtBeginDate) {
        this.expdtBeginDate = expdtBeginDate;
    }

    public Date getExpdtEndDate() {
        return expdtEndDate;
    }

    public void setExpdtEndDate(Date expdtEndDate) {
        this.expdtEndDate = expdtEndDate;
    }

    public String getMfrsName() {
        return mfrsName;
    }

    public void setMfrsName(String mfrsName) {
        this.mfrsName = mfrsName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}