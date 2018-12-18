package gyqx.spdhdi.mycerts.vo;

import common.db.meta.Title;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liangwu on 17-6-12.
 */
public class ProvRegInfoVo {
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
    /**;
     * 注册证类别
     * <p>
     * 注册证类型
     */
    @Size(max = 36)
    private String regKind;
    /**
     * 注册证产品名称
     */
    @Size(max = 36)
    private String productName;
    /**
     * 证照编码
     */
    @Size(max = 128)
    private String certificateCode;

    private String oldCertificateCode;

    @Size(max = 36)
    private String mfrsId;
    /**
     * 生产厂家
     */
    @Title("生产厂家")
    private String mfrsName;
    /**
     * 有效开始日期
     */
    private java.util.Date expdtBeginDate;
    /**
     * 有效结束日期
     */
    private java.util.Date expdtEndDate;
    /**
     * 是否历史证件
     */
    @Size(max=1)
    @Title("是否历史证件")
    private String isOldInfo ;


    private Integer picNum;
    /**
     * 数据版本
     */
    private int version;

    private List<ProvGoodsInfo4CertVo> selectedGoods;

    private Set<String> addGoods = new HashSet<>();
    private Set<String> delGoods = new HashSet<>();

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

    public String getOldCertificateCode() {
        return oldCertificateCode;
    }

    public void setOldCertificateCode(String oldCertificateCode) {
        this.oldCertificateCode = oldCertificateCode;
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

    public Integer getPicNum() {
        return picNum;
    }

    public void setPicNum(Integer picNum) {
        this.picNum = picNum;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<ProvGoodsInfo4CertVo> getSelectedGoods() {
        return selectedGoods;
    }

    public void setSelectedGoods(List<ProvGoodsInfo4CertVo> selectedGoods) {
        this.selectedGoods = selectedGoods;
    }

    public Set<String> getAddGoods() {
        return addGoods;
    }

    public void setAddGoods(Set<String> addGoods) {
        this.addGoods = addGoods;
    }

    public Set<String> getDelGoods() {
        return delGoods;
    }

    public void setDelGoods(Set<String> delGoods) {
        this.delGoods = delGoods;
    }

    public String getIsOldInfo() {
        return isOldInfo;
    }

    public void setIsOldInfo(String isOldInfo) {
        this.isOldInfo = isOldInfo;
    }
}
