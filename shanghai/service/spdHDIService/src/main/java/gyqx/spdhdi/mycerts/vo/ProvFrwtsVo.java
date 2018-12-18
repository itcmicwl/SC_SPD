package gyqx.spdhdi.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by liangwu on 17-6-23.
 */
@JsonIgnoreProperties
public class ProvFrwtsVo {
    /**
     * ID
     */
    @Size(max = 36)
    private String id;
    /**
     * 供应商ID
     */
    @Size(max = 36)
    private String provId;
    private String provName;
    /**
     * 医院ID
     */
    @Size(max = 36)
    private String hosId;
    private String hosName;
    /**
     * 委托人
     */
    @Size(max = 36)
    private String wtrId;
    private String wtrName;
    private String wtrPhone;
    /**
     * 委托书附件路径
     */
    @Size(max = 256)
    private String filePathWts;
    /**
     * 委托人身份证路径
     */
    @Size(max = 256)
    private String filePathWtr;
    /**
     * 有效开始日期
     */
    private Date expdtBeginDate;
    /**
     * 有效结束日期
     */
    private Date expdtEndDate;

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

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getWtrId() {
        return wtrId;
    }

    public void setWtrId(String wtrId) {
        this.wtrId = wtrId;
    }

    public String getWtrName() {
        return wtrName;
    }

    public void setWtrName(String wtrName) {
        this.wtrName = wtrName;
    }

    public String getWtrPhone() {
        return wtrPhone;
    }

    public void setWtrPhone(String wtrPhone) {
        this.wtrPhone = wtrPhone;
    }

    public String getFilePathWts() {
        return filePathWts;
    }

    public void setFilePathWts(String filePathWts) {
        this.filePathWts = filePathWts;
    }

    public String getFilePathWtr() {
        return filePathWtr;
    }

    public void setFilePathWtr(String filePathWtr) {
        this.filePathWtr = filePathWtr;
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

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }
}
