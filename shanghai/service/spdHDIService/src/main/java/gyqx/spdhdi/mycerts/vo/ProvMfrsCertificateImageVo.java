package gyqx.spdhdi.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Size;

/**
 * Created by liangwu on 17-7-7.
 */
public class ProvMfrsCertificateImageVo {
    @Size(max = 36)
    private String id;
    /**
     * 证照ID
     */
    @Size(max = 36)
    @JsonIgnore
    private String certificateId;
    /**
     * 附件路径
     */
    @Size(max = 128)
    private String filePath;

    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
