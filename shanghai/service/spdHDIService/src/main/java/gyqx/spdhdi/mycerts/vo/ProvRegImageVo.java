package gyqx.spdhdi.mycerts.vo;

/**
 * Created by liangwu on 17-6-16.
 */
public class ProvRegImageVo {
    /**
     * ID
     */
    private String id;
    /**
     * 证照ID
     */
    private String certificateId;
    /**
     * 附件路径
     */
    private String filePath;
    /**
     * 数据版本
     */
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
