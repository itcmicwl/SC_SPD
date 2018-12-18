package gyqx.spdherp.stockout.vo;

import java.util.Date;

public class DeptBuyMainVo {
    private String id;

    private String billId;

    private Integer buyKind;

    private String remark;

    private Integer state;

    private String deptView;

    private String fillter;

    private Date fillDate;

    private String recAddressId;

    private String recLinkman;

    private String recLinkmanPhone;

    private String recAddress;

    private Integer kindsCount;

    private Double sumQty;

    private Double undoneQty;

    private Boolean available;
    private String fillterName;

    public String getFillterName() {
		return fillterName;
	}

	public void setFillterName(String fillterName) {
		this.fillterName = fillterName;
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

    public Integer getBuyKind() {
        return buyKind;
    }

    public void setBuyKind(Integer buyKind) {
        this.buyKind = buyKind;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDeptView() {
        return deptView;
    }

    public void setDeptView(String deptView) {
        this.deptView = deptView;
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

    public Integer getKindsCount() {
        return kindsCount;
    }

    public void setKindsCount(Integer kindsCount) {
        this.kindsCount = kindsCount;
    }

    public Double getSumQty() {
        return sumQty;
    }

    public void setSumQty(Double sumQty) {
        this.sumQty = sumQty;
    }

    public Double getUndoneQty() {
        return undoneQty;
    }

    public void setUndoneQty(Double undoneQty) {
        this.undoneQty = undoneQty;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}