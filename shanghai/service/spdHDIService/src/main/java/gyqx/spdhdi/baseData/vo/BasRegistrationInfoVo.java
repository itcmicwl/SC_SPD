
package gyqx.spdhdi.baseData.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import common.db.meta.Title;
import gyqx.spdhdi.po.BasRegistrationInfo;

/**
 * 注册证基本信息
 */
@Table(name = "bas_registration_info")
public class BasRegistrationInfoVo extends BasRegistrationInfo {
    /**
     * ID
     */
    @Id
    @Size(max = 36)
    @Column(name = "id")
    @Title("ID")
    private String id;
    /**
     * 注册证类别
     */
    @Size(max = 36)
    @Column(name = "reg_kind")
    @Title("注册证类别")
    private String regKind;
    /**
     * 注册证产品名称
     */
    @Size(max = 36)
    @Column(name = "product_name")
    @Title("注册证产品名称")
    private String productName;
    /**
     * 证照编码
     */
    @Size(max = 36)
    @Column(name = "certificate_code")
    @Title("证照编码")
    private String certificateCode;
    /**
     * 生产厂家
     */
    @Size(max = 36)
    @JoinColumn(name = "mfrs_id", table = "bas_mfrs_info", referencedColumnName = "id")
    @Title("生产厂家")
    private String mfrsId;
    /**
     * 有效开始日期
     */
    @Column(name = "expdt_begin_date")
    @Title("有效开始日期")
    private Date expdtBeginDate;
    /**
     * 有效结束日期
     */
    @Column(name = "expdt_end_date")
    @Title("有效结束日期")
    private Date expdtEndDate;
    /**
     * 数据版本
     */
    @Column(name = "version")
    @Title("数据版本")
    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegKind() {
        return regKind;
    }

    public void setRegKind(String regKind) {
        this.regKind = regKind;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getMfrsId() {
        return mfrsId;
    }

    public void setMfrsId(String mfrsId) {
        this.mfrsId = mfrsId;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}