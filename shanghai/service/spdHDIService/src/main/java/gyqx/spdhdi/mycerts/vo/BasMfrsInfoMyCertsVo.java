package gyqx.spdhdi.mycerts.vo;

/**
 * Created by liangwu on 17-6-20.
 */
public class BasMfrsInfoMyCertsVo {
    /**
     * ID
     */
    private String id;
    /**
     * 生产厂家类型
     * <p>
     * 生产厂家类型
     */
    private String mfrsKind;
    /**
     * 厂家名称
     */
    private String mfrsName;
    /**
     * 全国总代理
     */
    private String agentName;
    /**
     * 营业执照注册号
     */
    private String regCode;
    /**
     * 经营许可证编号
     */
    private String jyxkCode;
    /**
     * 纳税人识别号
     */
    private String nsrsbCode;
    /**
     * 生产许可证
     */
    private String productCode;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 是否三证合一
     */
    private String isThreeInOne;
    /**
     * 标志
     */
//    private String flag;
    /**
     * 数据版本
     */
//    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMfrsKind() {
        return mfrsKind;
    }

    public void setMfrsKind(String mfrsKind) {
        this.mfrsKind = mfrsKind;
    }

    public String getMfrsName() {
        return mfrsName;
    }

    public void setMfrsName(String mfrsName) {
        this.mfrsName = mfrsName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getJyxkCode() {
        return jyxkCode;
    }

    public void setJyxkCode(String jyxkCode) {
        this.jyxkCode = jyxkCode;
    }

    public String getNsrsbCode() {
        return nsrsbCode;
    }

    public void setNsrsbCode(String nsrsbCode) {
        this.nsrsbCode = nsrsbCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsThreeInOne() {
        return isThreeInOne;
    }

    public void setIsThreeInOne(String isThreeInOne) {
        this.isThreeInOne = isThreeInOne;
    }
}
