
package gyqx.spdhdi.orderMgr.vo;

import java.util.List;

import javax.persistence.Column;

import common.db.meta.Title;
import gyqx.spdhdi.po.ReturnBill;
import gyqx.spdhdi.po.ReturnBillList;

public class ReturnBillVo extends ReturnBill
{
	/**
	 * 公司/医院名称
	*/
	@Column(name="returnCompanyName")
	@Title("公司/医院名称")
	private String returnCompanyName ;

	/**
	 * 机构别名
	*/
	@Column(name="returnOrgName")
	@Title("机构别名")
	private String returnOrgName ;
	/**
	 * 机构编码
	*/
	@Column(name="returnOrgCode")
	@Title("机构编码")
	private String returnOrgCode ;
   /**
	 * 机构别名
	*/
	@Column(name="outOrgName")
	@Title("机构别名")
	private String outOrgName ;
	/**
	 * 机构编码
	*/
	@Column(name="outOrgCode")
	@Title("机构编码")
	private String outOrgCode ;

	/**
	 * 公司/医院名称
	*/
	@Column(name="provName")
	@Title("公司/医院名称")
	private String provName ;
	
	/**
	 * 用户别名
	*/
	@Column(name="fillterName")
	@Title("用户别名")
	private String fillterName ;	
	
	private List<ReturnBillListVo> returnBillSubList;
	


	//退货确认单相关添加字段
	
	private String returnCompanyCode;
	private String returnCompanyErpCode;	
	private String returnOrgErpCode;
	private String returnOrgScmCode;
	private String outOrgScmCode;
	private String outOrgErpCode;
	private String provCode;
	private String provErpCode;
	
	
	private String balanceOrgName;
	
	public String getReturnCompanyName() {
		return returnCompanyName;
	}

	public String getReturnOrgName() {
		return returnOrgName;
	}

	public String getReturnOrgCode() {
		return returnOrgCode;
	}

	public String getOutOrgName() {
		return outOrgName;
	}

	public String getOutOrgCode() {
		return outOrgCode;
	}

	public String getProvName() {
		return provName;
	}

	public String getFillterName() {
		return fillterName;
	}

	public List<ReturnBillListVo> getReturnBillSubList() {
		return returnBillSubList;
	}

	public void setReturnCompanyName(String returnCompanyName) {
		this.returnCompanyName = returnCompanyName;
	}

	public void setReturnOrgName(String returnOrgName) {
		this.returnOrgName = returnOrgName;
	}

	public void setReturnOrgCode(String returnOrgCode) {
		this.returnOrgCode = returnOrgCode;
	}

	public void setOutOrgName(String outOrgName) {
		this.outOrgName = outOrgName;
	}

	public void setOutOrgCode(String outOrgCode) {
		this.outOrgCode = outOrgCode;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public void setFillterName(String fillterName) {
		this.fillterName = fillterName;
	}

	public void setReturnBillSubList(List<ReturnBillListVo> returnBillSubList) {
		this.returnBillSubList = returnBillSubList;
	}

	public String getReturnCompanyCode() {
		return returnCompanyCode;
	}

	public String getReturnCompanyErpCode() {
		return returnCompanyErpCode;
	}

	public String getReturnOrgErpCode() {
		return returnOrgErpCode;
	}

	public String getReturnOrgScmCode() {
		return returnOrgScmCode;
	}

	public String getOutOrgScmCode() {
		return outOrgScmCode;
	}

	public String getOutOrgErpCode() {
		return outOrgErpCode;
	}

	public String getProvCode() {
		return provCode;
	}

	public String getProvErpCode() {
		return provErpCode;
	}

	public void setReturnCompanyCode(String returnCompanyCode) {
		this.returnCompanyCode = returnCompanyCode;
	}

	public void setReturnCompanyErpCode(String returnCompanyErpCode) {
		this.returnCompanyErpCode = returnCompanyErpCode;
	}

	public void setReturnOrgErpCode(String returnOrgErpCode) {
		this.returnOrgErpCode = returnOrgErpCode;
	}

	public void setReturnOrgScmCode(String returnOrgScmCode) {
		this.returnOrgScmCode = returnOrgScmCode;
	}

	public void setOutOrgScmCode(String outOrgScmCode) {
		this.outOrgScmCode = outOrgScmCode;
	}

	public void setOutOrgErpCode(String outOrgErpCode) {
		this.outOrgErpCode = outOrgErpCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public void setProvErpCode(String provErpCode) {
		this.provErpCode = provErpCode;
	}

	public String getBalanceOrgName() {
		return balanceOrgName;
	}

	public void setBalanceOrgName(String balanceOrgName) {
		this.balanceOrgName = balanceOrgName;
	}
	

	




}