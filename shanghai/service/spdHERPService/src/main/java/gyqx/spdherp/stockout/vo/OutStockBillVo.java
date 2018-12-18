package gyqx.spdherp.stockout.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import common.db.meta.Title;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-8-25 下午8:24
 */
@JsonIgnoreProperties
public class OutStockBillVo {
    /**
     * tel 记账人电话
     */
    @Id
    @Size(max = 36)
    @Column(name = "tel")
    @Title("tel")
    private String tel;
    /**
     * ID
     */
    @Id
    @Size(max = 36)
    @Column(name = "id")
    @Title("ID")
    private String id;
    /**
     * 单号
     */
    @Size(max = 36)
    @Column(name = "bill_id")
    @Title("单号")
    private String billId;
    /**
     * 出库类型
     */
    @Size(max = 10)
    @Column(name = "out_stock_kind")
    @Title("出库类型")
    private String outStockKind;
    /**
     * 出库方式
     * 10--实采，20--虚采
     */
    @Column(name = "out_stock_type")
    @Title("出库方式")
    private Integer outStockType;
    /**
     * 出库机构ID
     */
    @Size(max = 36)
    @Column(name = "out_org_id")
    @Title("出库机构ID")
    private String outOrgId;
    /**
     * 出库机构名称
     */
    @Size(max = 200)
    @Column(name = "out_org_name")
    @Title("出库机构名称")
    private String outOrgName;
    /**
     * 出库科室ID
     */
    @Size(max = 36)
    @Column(name = "out_dept_id")
    @Title("出库科室ID")
    private String outDeptId;
    /**
     * 出库科室名称
     */
    @Size(max = 200)
    @Column(name = "out_dept_name")
    @Title("出库科室名称")
    private String outDeptName;
    /**
     * 出库库房
     */
    @Size(max = 36)
    @Column(name = "out_stoc_id")
    @Title("出库库房")
    private String outStocId;
    /**
     * 入库机构ID
     */
    @Size(max = 36)
    @Column(name = "in_org_id")
    @Title("入库机构ID")
    private String inOrgId;
    /**
     * 入库机构名称
     */
    @Size(max = 200)
    @Column(name = "in_org_name")
    @Title("入库机构名称")
    private String inOrgName;
    /**
     * 入库科室ID
     */
    @Size(max = 36)
    @Column(name = "in_dept_id")
    @Title("入库科室ID")
    private String inDeptId;
    /**
     * 入库科室名称
     */
    @Size(max = 200)
    @Column(name = "in_dept_name")
    @Title("入库科室名称")
    private String inDeptName;
    /**
     * 制单日期
     */
    @Column(name = "fill_date")
    @Title("制单日期")
    private Date fillDate;
    /**
     * 制单人
     */
    @Size(max = 36)
    @Column(name = "filler")
    @Title("制单人")
    private String filler;
    /**
     * 审核人
     */
    @Size(max = 36)
    @Column(name = "auditor")
    @Title("审核人")
    private String auditor;
    /**
     * 审核时间
     */
    @Column(name = "audit_date")
    @Title("审核时间")
    private Date auditDate;
    /**
     * 记账人
     */
    @Size(max = 36)
    @Column(name = "accounter")
    @Title("记账人")
    private String accounter;
    /**
     * 记账时间
     */
    @Column(name = "account_date")
    @Title("记账时间")
    private Date accountDate;
    /**
     * 收货地址ID
     */
    @Size(max = 36)
    @JoinColumn(name = "rec_address_id", table = "receive_address", referencedColumnName = "id")
    @Title("收货地址ID")
    private String recAddressId;
    /**
     * 联系人
     */
    @Size(max = 80)
    @JoinColumn(name = "rec_linkman", table = "receive_address", referencedColumnName = "lxr")
    @Title("联系人")
    private String recLinkman;
    /**
     * 联系电话
     */
    @Size(max = 100)
    @JoinColumn(name = "rec_linkman_phone", table = "receive_address", referencedColumnName = "lxr_phone")
    @Title("联系电话")
    private String recLinkmanPhone;
    /**
     * 收货地址
     */
    @Size(max = 500)
    @JoinColumn(name = "rec_address", table = "receive_address", referencedColumnName = "address")
    @Title("收货地址")
    private String recAddress;
    /**
     * 状态
     */
    @Column(name = "status")
    @Title("状态")
    private Integer status;
    /**
     * 费用类型
     */
    @Column(name = "cost_kind")
    @Title("费用类型")
    private Integer costKind;
    /**
     * 患者住院号
     */
    @Size(max = 36)
    @Column(name = "patient_name")
    @Title("患者住院号")
    private String patientName;
    /**
     * 患者姓名
     */
    @Size(max = 36)
    @Title("患者姓名")
    private String patient;
    /**
     * 患者住院科室
     */
    @Size(max = 36)
    @Title("患者住院科室")
    private String patientDeptCode;
    /**
     * 患者床号
     */
    @Size(max = 36)
    @Title("患者床号")
    private String patientBedNum;
    /**
     * 数据版本
     */
    @Column(name = "version")
    @Title("数据版本")
    private int version;

    private int goodsCount;

    private Double goodsSum;

    private Double amount;

    private String stocLevel;
    
    /**
     * 
     */
    private String auditorName;
    
    private String accounterName;

	private List<RequestStockGoodsVo> goodsList;

    private List<OutStockBillSubVo> subList;

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public String getAccounterName() {
        return accounterName;
    }

    public void setAccounterName(String accounterName) {
        this.accounterName = accounterName;
    }

    public List<OutStockBillSubVo> getSubList() {
        return subList;
    }

    public void setSubList(List<OutStockBillSubVo> subList) {
        this.subList = subList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getOutStockKind() {
        return outStockKind;
    }

    public void setOutStockKind(String outStockKind) {
        this.outStockKind = outStockKind;
    }

    public Integer getOutStockType() {
        return outStockType;
    }

    public void setOutStockType(Integer outStockType) {
        this.outStockType = outStockType;
    }

    public String getOutOrgId() {
        return outOrgId;
    }

    public void setOutOrgId(String outOrgId) {
        this.outOrgId = outOrgId;
    }

    public String getOutOrgName() {
        return outOrgName;
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName;
    }

    public String getOutDeptId() {
        return outDeptId;
    }

    public void setOutDeptId(String outDeptId) {
        this.outDeptId = outDeptId;
    }

    public String getOutDeptName() {
        return outDeptName;
    }

    public void setOutDeptName(String outDeptName) {
        this.outDeptName = outDeptName;
    }

    public String getOutStocId() {
        return outStocId;
    }

    public void setOutStocId(String outStocId) {
        this.outStocId = outStocId;
    }

    public Date getFillDate() {
        return fillDate;
    }

    public void setFillDate(Date fillDate) {
        this.fillDate = fillDate;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAccounter() {
        return accounter;
    }

    public void setAccounter(String accounter) {
        this.accounter = accounter;
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public String getRecAddressId() {
        return recAddressId;
    }

    public void setRecAddressId(String recAddressId) {
        this.recAddressId = recAddressId;
    }

    public String getRecLinkman() {
        return recLinkman;
    }

    public void setRecLinkman(String recLinkman) {
        this.recLinkman = recLinkman;
    }

    public String getRecLinkmanPhone() {
        return recLinkmanPhone;
    }

    public void setRecLinkmanPhone(String recLinkmanPhone) {
        this.recLinkmanPhone = recLinkmanPhone;
    }

    public String getRecAddress() {
        return recAddress;
    }

    public void setRecAddress(String recAddress) {
        this.recAddress = recAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCostKind() {
		return costKind;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setCostKind(int costKind) {
		this.costKind = costKind;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @JsonIgnore
    public List<RequestStockGoodsVo> getGoodsList() {
        return goodsList;
    }

    @JsonProperty
    public void setGoodsList(List<RequestStockGoodsVo> goodsList) {
        this.goodsList = goodsList;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Double getGoodsSum() {
        return goodsSum;
    }

    public void setGoodsSum(Double goodsSum) {
        this.goodsSum = goodsSum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getInOrgId() {
        return inOrgId;
    }

    public void setInOrgId(String inOrgId) {
        this.inOrgId = inOrgId;
    }

    public String getInDeptId() {
        return inDeptId;
    }

    public void setInDeptId(String inDeptId) {
        this.inDeptId = inDeptId;
    }

    public String getInOrgName() {
        return inOrgName;
    }

    public void setInOrgName(String inOrgName) {
        this.inOrgName = inOrgName;
    }

    public String getInDeptName() {
        return inDeptName;
    }

    public void setInDeptName(String inDeptName) {
        this.inDeptName = inDeptName;
    }

    public void setCostKind(Integer costKind) {
        this.costKind = costKind;
    }

    public String getStocLevel() {
        return stocLevel;
    }

    public void setStocLevel(String stocLevel) {
        this.stocLevel = stocLevel;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getPatientDeptCode() {
        return patientDeptCode;
    }

    public void setPatientDeptCode(String patientDeptCode) {
        this.patientDeptCode = patientDeptCode;
    }

    public String getPatientBedNum() {
        return patientBedNum;
    }

    public void setPatientBedNum(String patientBedNum) {
        this.patientBedNum = patientBedNum;
    }

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
