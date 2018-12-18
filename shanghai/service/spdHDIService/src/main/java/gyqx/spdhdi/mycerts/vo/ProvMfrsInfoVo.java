package gyqx.spdhdi.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by liangwu on 17-6-19.
 */
@JsonIgnoreProperties
public class ProvMfrsInfoVo {
    /**
     * ID
     */
    private String id;
    /**
     * 供应商ID
     */
    private String provId;
    /**
     * 生产商ID
     */
    private String mfrsId;

    private BasMfrsInfoMyCertsVo mfrsInfo;
    /**
     * 标志
     */
    private String flag;
    /**
     * 数据版本
     */
    private int version;

    private List<ProvMfrsCertificateVo> certList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BasMfrsInfoMyCertsVo getMfrsInfo() {
        return mfrsInfo;
    }

    public void setMfrsInfo(BasMfrsInfoMyCertsVo mfrsInfo) {
        this.mfrsInfo = mfrsInfo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<ProvMfrsCertificateVo> getCertList() {
        return certList;
    }

    public void setCertList(List<ProvMfrsCertificateVo> certList) {
        this.certList = certList;
    }
}
