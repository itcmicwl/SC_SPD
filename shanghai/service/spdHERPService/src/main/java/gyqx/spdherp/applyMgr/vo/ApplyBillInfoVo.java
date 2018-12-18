package gyqx.spdherp.applyMgr.vo;

import org.omg.PortableInterceptor.ServerRequestInfo;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by moonb on 2018/4/4.
 */
public class ApplyBillInfoVo {
    private Integer rowNumber;
    private String subId;
    private String goodsId;
    private String goodsName;
    private String goodsGg;
    private BigDecimal qty;
    private Integer sendType;
    private BigDecimal je;
    private Integer uniqueKind;
    private String buyDeptId;
    private String buyDeptName;
    private String buyDeptCode;
    private String id;
    private String billId;
    private String buyKind;
    private String hosId;
    private String sourceWarehouseId;
    private String remark;
    private String state;
    private String deptState;
    private String deptView;
    private Integer sumRow;
    private Integer whSumRow;
    private Date deptDealTime;
    private String deptDealMan;
    private String fillter;
    private Date fillDate;
    private String auditor;
    private Date auditDate;
    private Date lastUpdateDatetime;
    private String recAddressId;
    private String recLinkman;
    private String recLinkmanPhone;
    private String recAddress;
    private int version;
    private boolean ifClosed;
    private Integer purMode;
    private String provId;
    private String provName;
    private String subProvId;
    private boolean isUseSubProv;
    private String sDate;
    private String eDate;
    private String unit;
    private String mfrsName;
    private String brand;
    private String goodsCode;
    private BigDecimal goodsPrice;
    private Integer subState;
    private BigDecimal unitRate;
    private BigDecimal taxRate;
    private String salemanId;
    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsGg() {
        return goodsGg;
    }

    public void setGoodsGg(String goodsGg) {
        this.goodsGg = goodsGg;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public BigDecimal getJe() {
        return je;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    public Integer getUniqueKind() {
        return uniqueKind;
    }

    public void setUniqueKind(Integer uniqueKind) {
        this.uniqueKind = uniqueKind;
    }

    public String getBuyDeptId() {
        return buyDeptId;
    }

    public void setBuyDeptId(String buyDeptId) {
        this.buyDeptId = buyDeptId;
    }

    public String getBuyDeptName() {
        return buyDeptName;
    }

    public void setBuyDeptName(String buyDeptName) {
        this.buyDeptName = buyDeptName;
    }

    public String getBuyDeptCode() {
        return buyDeptCode;
    }

    public void setBuyDeptCode(String buyDeptCode) {
        this.buyDeptCode = buyDeptCode;
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

    public String getBuyKind() {
        return buyKind;
    }

    public void setBuyKind(String buyKind) {
        this.buyKind = buyKind;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getSourceWarehouseId() {
        return sourceWarehouseId;
    }

    public void setSourceWarehouseId(String sourceWarehouseId) {
        this.sourceWarehouseId = sourceWarehouseId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeptState() {
        return deptState;
    }

    public void setDeptState(String deptState) {
        this.deptState = deptState;
    }

    public String getDeptView() {
        return deptView;
    }

    public void setDeptView(String deptView) {
        this.deptView = deptView;
    }

    public Integer getSumRow() {
        return sumRow;
    }

    public void setSumRow(Integer sumRow) {
        this.sumRow = sumRow;
    }

    public Integer getWhSumRow() {
        return whSumRow;
    }

    public void setWhSumRow(Integer whSumRow) {
        this.whSumRow = whSumRow;
    }

    public Date getDeptDealTime() {
        return deptDealTime;
    }

    public void setDeptDealTime(Date deptDealTime) {
        this.deptDealTime = deptDealTime;
    }

    public String getDeptDealMan() {
        return deptDealMan;
    }

    public void setDeptDealMan(String deptDealMan) {
        this.deptDealMan = deptDealMan;
    }

    public String getFillter() {
        return fillter;
    }

    public void setFillter(String fillter) {
        this.fillter = fillter;
    }

    public Date getFillDate() {
        return fillDate;
    }

    public void setFillDate(Date fillDate) {
        this.fillDate = fillDate;
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

    public Date getLastUpdateDatetime() {
        return lastUpdateDatetime;
    }

    public void setLastUpdateDatetime(Date lastUpdateDatetime) {
        this.lastUpdateDatetime = lastUpdateDatetime;
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

    public void setRecAddress(String redAddress) {
        this.recAddress = redAddress;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isIfClosed() {
        return ifClosed;
    }

    public void setIfClosed(boolean ifClosed) {
        this.ifClosed = ifClosed;
    }

    public Integer getPurMode() {
        return purMode;
    }

    public void setPurMode(Integer purMode) {
        this.purMode = purMode;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getSubProvId() {
        return subProvId;
    }

    public void setSubProvId(String subProvId) {
        this.subProvId = subProvId;
    }

    public boolean isUseSubProv() {
        return isUseSubProv;
    }

    public void setUseSubProv(boolean useSubProv) {
        isUseSubProv = useSubProv;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMfrsName() {
        return mfrsName;
    }

    public void setMfrsName(String mfrsName) {
        this.mfrsName = mfrsName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getSubState() {
        return subState;
    }

    public void setSubState(Integer subState) {
        this.subState = subState;
    }

    public BigDecimal getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(BigDecimal unitRate) {
        this.unitRate = unitRate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getSalemanId() {
        return salemanId;
    }

    public void setSalemanId(String salemanId) {
        this.salemanId = salemanId;
    }
}
