package gyqx.spdhdi.mycerts.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by liangwu on 17-7-7.
 */
@JsonIgnoreProperties
public class ProvMfrsCertificateVo {
    private String id;
    /**
     * PID
     */
    private String pId;
    /**
     * 生产商ID
     */
    private String mfrsId;
    private String mfrsName;
    /**
     * 证照类型
     */
    private String certKind;
    /**
     * 证件编号
     */
    private String certCode;
    /**
     * 有效开始日期
     */
    private java.util.Date expdtBeginDate;
    /**
     * 有效结束日期
     */
    private java.util.Date expdtEndDate;
    /**
     * 是否长期有效
     */
    private String isValiding;
    /**
     * 数据版本
     */
    private int version;

    private List<ProvMfrsCertificateImageVo> imageList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getMfrsId() {
        return mfrsId;
    }

    public void setMfrsId(String mfrsId) {
        this.mfrsId = mfrsId;
    }

    public String getCertKind() {
        return certKind;
    }

    public void setCertKind(String certKind) {
        this.certKind = certKind;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
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

    public String getIsValiding() {
        return isValiding;
    }

    public void setIsValiding(String isValiding) {
        this.isValiding = isValiding;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

	public List<ProvMfrsCertificateImageVo> getImageList() {
		return imageList;
	}

	public void setImageList(List<ProvMfrsCertificateImageVo> imageList) {
		this.imageList = imageList;
	}

    public String getMfrsName() {
        return mfrsName;
    }

    public void setMfrsName(String mfrsName) {
        this.mfrsName = mfrsName;
    }
}
