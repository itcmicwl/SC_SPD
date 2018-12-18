package gyqx.spdhdi.uploadData.vo;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 退货确认单
 * 
 * @date 2017年9月15日 下午4:35:45
 */
public class ReturnconfirmJson {
	@JSONField(name = "SPD系统标识")
	private String spdCode;
	@JSONField(name = "配送单位系统标识")
	private String provSystemId;

	@JSONField(name = "配送单位")
	private String provId;
	@JSONField(name = "配送单位名称")
	private String provName;
	@JSONField(name = "配送单位ERP编号")
	private String provErpCode;
	@JSONField(name = "配送单位ERP部门编号")
	private String provOrgCode;

	@JSONField(name = "退货医院")
	private String returnCompanyId;
	@JSONField(name = "退货医院名称")
	private String returnCompanyName;
	@JSONField(name = "退货医院ERP编号")
	private String returnCompanyErpCode;

	@JSONField(name = "退货科室编号")
	private String returnOrgCode;
	@JSONField(name = "退货科室名称")
	private String returnOrgName;
	@JSONField(name = "退货科室ERP编号")
	private String returnOrgErpCode;

	@JSONField(name = "出库科室编号")
	private String outOrgCode;
	@JSONField(name = "出库科室名称")
	private String outOrgName;
	@JSONField(name = "出库科室ERP编号")
	private String outOrgErpCode;

	@JSONField(name = "_date", format = "yyyy-MM-dd HH:mm:ss")
	private Date date = new Date();

	@JSONField(name = "退货单号")
	private String billId;

	@JSONField(name = "退货单日期", format = "yyyy-MM-dd HH:mm:ss")
	private Date fillDate;

	@JSONField(name = "退货单总行数")
	private Integer sumRow;

	@JSONField(name = "请退明细")
	private List<ReturnconfirmListJson> returnLists;

	public String getSpdCode() {
		return spdCode;
	}

	public String getProvSystemId() {
		return provSystemId;
	}

	public String getProvId() {
		return provId;
	}

	public String getProvName() {
		return provName;
	}

	public String getProvErpCode() {
		return provErpCode;
	}

	public String getProvOrgCode() {
		return provOrgCode;
	}

	public String getReturnCompanyId() {
		return returnCompanyId;
	}

	public String getReturnCompanyName() {
		return returnCompanyName;
	}

	public String getReturnCompanyErpCode() {
		return returnCompanyErpCode;
	}

	public String getReturnOrgCode() {
		return returnOrgCode;
	}

	public String getReturnOrgName() {
		return returnOrgName;
	}

	public String getReturnOrgErpCode() {
		return returnOrgErpCode;
	}

	public String getOutOrgCode() {
		return outOrgCode;
	}

	public String getOutOrgName() {
		return outOrgName;
	}

	public String getOutOrgErpCode() {
		return outOrgErpCode;
	}

	public Date getDate() {
		return date;
	}

	public String getBillId() {
		return billId;
	}

	public Date getFillDate() {
		return fillDate;
	}

	public Integer getSumRow() {
		return sumRow;
	}

	public List<ReturnconfirmListJson> getReturnLists() {
		return returnLists;
	}

	public void setSpdCode(String spdCode) {
		this.spdCode = spdCode;
	}

	public void setProvSystemId(String provSystemId) {
		this.provSystemId = provSystemId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public void setProvErpCode(String provErpCode) {
		this.provErpCode = provErpCode;
	}

	public void setProvOrgCode(String provOrgCode) {
		this.provOrgCode = provOrgCode;
	}

	public void setReturnCompanyId(String returnCompanyId) {
		this.returnCompanyId = returnCompanyId;
	}

	public void setReturnCompanyName(String returnCompanyName) {
		this.returnCompanyName = returnCompanyName;
	}

	public void setReturnCompanyErpCode(String returnCompanyErpCode) {
		this.returnCompanyErpCode = returnCompanyErpCode;
	}

	public void setReturnOrgCode(String returnOrgCode) {
		this.returnOrgCode = returnOrgCode;
	}

	public void setReturnOrgName(String returnOrgName) {
		this.returnOrgName = returnOrgName;
	}

	public void setReturnOrgErpCode(String returnOrgErpCode) {
		this.returnOrgErpCode = returnOrgErpCode;
	}

	public void setOutOrgCode(String outOrgCode) {
		this.outOrgCode = outOrgCode;
	}

	public void setOutOrgName(String outOrgName) {
		this.outOrgName = outOrgName;
	}

	public void setOutOrgErpCode(String outOrgErpCode) {
		this.outOrgErpCode = outOrgErpCode;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}

	public void setSumRow(Integer sumRow) {
		this.sumRow = sumRow;
	}

	public void setReturnLists(List<ReturnconfirmListJson> returnLists) {
		this.returnLists = returnLists;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReturnconfirmJson [spdCode=");
		builder.append(spdCode);
		builder.append(", provSystemId=");
		builder.append(provSystemId);
		builder.append(", provId=");
		builder.append(provId);
		builder.append(", provName=");
		builder.append(provName);
		builder.append(", provErpCode=");
		builder.append(provErpCode);
		builder.append(", provOrgCode=");
		builder.append(provOrgCode);
		builder.append(", returnCompanyId=");
		builder.append(returnCompanyId);
		builder.append(", returnCompanyName=");
		builder.append(returnCompanyName);
		builder.append(", returnCompanyErpCode=");
		builder.append(returnCompanyErpCode);
		builder.append(", returnOrgCode=");
		builder.append(returnOrgCode);
		builder.append(", returnOrgName=");
		builder.append(returnOrgName);
		builder.append(", returnOrgErpCode=");
		builder.append(returnOrgErpCode);
		builder.append(", outOrgCode=");
		builder.append(outOrgCode);
		builder.append(", outOrgName=");
		builder.append(outOrgName);
		builder.append(", outOrgErpCode=");
		builder.append(outOrgErpCode);
		builder.append(", date=");
		builder.append(date);
		builder.append(", billId=");
		builder.append(billId);
		builder.append(", fillDate=");
		builder.append(fillDate);
		builder.append(", sumRow=");
		builder.append(sumRow);
		builder.append(", returnLists=");
		builder.append(returnLists);
		builder.append("]");
		return builder.toString();
	}
	

}
