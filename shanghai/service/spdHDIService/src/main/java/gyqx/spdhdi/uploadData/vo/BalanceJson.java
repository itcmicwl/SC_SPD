package gyqx.spdhdi.uploadData.vo;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * SPD结算接口表
 * 
 * @author LIWENKANG
 *
 */
public class BalanceJson {

	
	@JSONField(name = "SPD系统标识")
	private String spdCode;
	@JSONField(name = "结算单位系统标识")
	private String provSystemId;

	@JSONField(name = "SPD结算单号")
	private String jsBillId;
	
	@JSONField(name = "结算单位")
	private String provId;
	@JSONField(name = "结算单位ERP编号")
	private String provErpCode;
	@JSONField(name = "结算单位ERP部门编号")
	private String provDeptErpCode;
	@JSONField(name = "结算单位名称")
	private String provName;

	@JSONField(name = "出库医院")
	private String hosId;
	@JSONField(name = "出库医院ERP编号")
	private String hosErpCode;
	@JSONField(name = "出库医院名称")
	private String hosName;

	@JSONField(name = "业务部门ERP编号")
	private String saleDeptErpCode;
	@JSONField(name = "业务部门名称")
	private String saleDeptName;
	@JSONField(name = "业务员ERP编号")
	private String salemanCode;
	@JSONField(name = "业务员名称")
	private String salemanName;

	@JSONField(name = "任务总行数")
	private Integer sumRow;

	@JSONField(name = "_date", format = "yyyy-MM-dd HH:mm:ss")
	private Date date = new Date();

	@JSONField(name = "结算明细")
	private List<BalanceDetailJson> balanceDetails;

	public String getSpdCode() {
		return spdCode;
	}

	public void setSpdCode(String spdCode) {
		this.spdCode = spdCode;
	}

	public String getProvSystemId() {
		return provSystemId;
	}

	public void setProvSystemId(String provSystemId) {
		this.provSystemId = provSystemId;
	}

	
	public String getJsBillId() {
		return jsBillId;
	}

	public void setJsBillId(String jsBillId) {
		this.jsBillId = jsBillId;
	}

	public String getProvId() {
		return provId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public String getProvErpCode() {
		return provErpCode;
	}

	public void setProvErpCode(String provErpCode) {
		this.provErpCode = provErpCode;
	}

	public String getProvDeptErpCode() {
		return provDeptErpCode;
	}

	public void setProvDeptErpCode(String provDeptErpCode) {
		this.provDeptErpCode = provDeptErpCode;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getHosErpCode() {
		return hosErpCode;
	}

	public void setHosErpCode(String hosErpCode) {
		this.hosErpCode = hosErpCode;
	}

	public String getHosName() {
		return hosName;
	}

	public void setHosName(String hosName) {
		this.hosName = hosName;
	}

	public String getSaleDeptErpCode() {
		return saleDeptErpCode;
	}

	public void setSaleDeptErpCode(String saleDeptErpCode) {
		this.saleDeptErpCode = saleDeptErpCode;
	}

	public String getSaleDeptName() {
		return saleDeptName;
	}

	public void setSaleDeptName(String saleDeptName) {
		this.saleDeptName = saleDeptName;
	}

	public String getSalemanCode() {
		return salemanCode;
	}

	public void setSalemanCode(String salemanCode) {
		this.salemanCode = salemanCode;
	}

	public String getSalemanName() {
		return salemanName;
	}

	public void setSalemanName(String salemanName) {
		this.salemanName = salemanName;
	}

	public Integer getSumRow() {
		return sumRow;
	}

	public void setSumRow(Integer sumRow) {
		this.sumRow = sumRow;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<BalanceDetailJson> getBalanceDetails() {
		return balanceDetails;
	}

	public void setBalanceDetails(List<BalanceDetailJson> balanceDetails) {
		this.balanceDetails = balanceDetails;
	}

	@Override
	public String toString() {
		return "SPD结算接口表 [spdCode=" + spdCode + ", provSystemId=" + provSystemId + ", provId=" + provId
				+ ", provErpCode=" + provErpCode + ", provDeptErpCode=" + provDeptErpCode + ", provName=" + provName
				+ ", hosId=" + hosId + ", hosErpCode=" + hosErpCode + ", hosName=" + hosName + ", saleDeptErpCode="
				+ saleDeptErpCode + ", saleDeptName=" + saleDeptName + ", salemanCode=" + salemanCode + ", salemanName="
				+ salemanName + ", sumRow=" + sumRow + ", date=" + date + ", balanceDetails=" + balanceDetails + "]";
	}

}
