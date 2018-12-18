
package gyqx.spdhdi.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gyqx.spdhdi.po.ProvJxsqsZjzj;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 供应商经销授权书
 */
@JsonIgnoreProperties
public class ProvJxsqsVo {
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
    /**
     * 生产厂家
     */
    @Size(max = 36)
    private String mfrsId;
    /**
     * 是否厂家
     */
    @Size(max = 1)
    private String isMfrs;
    /**
     * 上级单位
     */
    @Size(max = 36)
    private String chiefId;
    /**
     * 授权区域
     */
    @Size(max = 300)
    private String saleArea;
    /**
     * 有效开始日期
     */
    private Date expdtBeginDate;
    /**
     * 有效结束日期
     */
    private Date expdtEndDate;
    /**
     * 是否长期有效
     */
    @Size(max = 1)
    private String isValiding;
    /**
     * 附件路径
     */
    @Size(max = 128)
    private String filePath;
    /**
     * 厂家名称
     */
    @Size(max = 36)
    private String mfrsName;
    /**
     * 上级厂家名称
     */
    @Size(max = 36)
    private String superiorMfrsName;

    private int version;

    private List<ProvJxsqsZjzj> zjzjList;

    private List<BasCompanyInfoJxsqsVo> forHospitals;

    private List<ProvRegInfoJxsqsVo> relatedCerts;

    private Set<BasCompanyInfoJxsqsVo> addHos = new HashSet<>();

    private Set<BasCompanyInfoJxsqsVo> delHos = new HashSet<>();

    private Set<ProvRegInfoJxsqsVo> addCerts = new HashSet<>();

    private Set<ProvRegInfoJxsqsVo> delCerts = new HashSet<>();

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

    public String getIsMfrs() {
        return isMfrs;
    }

    public void setIsMfrs(String isMfrs) {
        this.isMfrs = isMfrs;
    }

    public String getChiefId() {
        return chiefId;
    }

    public void setChiefId(String chiefId) {
        this.chiefId = chiefId;
    }

    public String getSaleArea() {
        return saleArea;
    }

    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMfrsName() {
        return mfrsName;
    }

    public void setMfrsName(String mfrsName) {
        this.mfrsName = mfrsName;
    }

    public String getSuperiorMfrsName() {
        return superiorMfrsName;
    }

    public void setSuperiorMfrsName(String superiorMfrsName) {
        this.superiorMfrsName = superiorMfrsName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BasCompanyInfoJxsqsVo> getForHospitals() {
        return forHospitals;
    }

    public void setForHospitals(List<BasCompanyInfoJxsqsVo> forHospitals) {
        this.forHospitals = forHospitals;
    }

    public List<ProvRegInfoJxsqsVo> getRelatedCerts() {
        return relatedCerts;
    }

    public void setRelatedCerts(List<ProvRegInfoJxsqsVo> relatedCerts) {
        this.relatedCerts = relatedCerts;
    }

    public Set<BasCompanyInfoJxsqsVo> getAddHos() {
        return addHos;
    }

    public void setAddHos(Set<BasCompanyInfoJxsqsVo> addHos) {
        this.addHos = addHos;
    }

    public Set<BasCompanyInfoJxsqsVo> getDelHos() {
        return delHos;
    }

    public void setDelHos(Set<BasCompanyInfoJxsqsVo> delHos) {
        this.delHos = delHos;
    }

    public Set<ProvRegInfoJxsqsVo> getAddCerts() {
        return addCerts;
    }

    public void setAddCerts(Set<ProvRegInfoJxsqsVo> addCerts) {
        this.addCerts = addCerts;
    }

    public Set<ProvRegInfoJxsqsVo> getDelCerts() {
        return delCerts;
    }

    public void setDelCerts(Set<ProvRegInfoJxsqsVo> delCerts) {
        this.delCerts = delCerts;
    }

    public List<ProvJxsqsZjzj> getZjzjList() {
        return zjzjList;
    }

    public void setZjzjList(List<ProvJxsqsZjzj> zjzjList) {
        this.zjzjList = zjzjList;
    }
}