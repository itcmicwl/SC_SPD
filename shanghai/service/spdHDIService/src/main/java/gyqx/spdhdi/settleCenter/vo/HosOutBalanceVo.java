package gyqx.spdhdi.settleCenter.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import gyqx.spdhdi.po.HosOutBalance;

/**
 * Created by moonb on 2017/9/14.
 */
public class HosOutBalanceVo extends HosOutBalance implements Serializable {
	private static final long serialVersionUID = 1306058422484128024L;
	private String fillterName;
	private String provDeptErpName;
	private List<HosOutBalanceDetailVo> lstDetail;
	private int settleType;//计算类型：入库结算10、出库结算20
    
    //开始日期
  	private Date startDate;
  	//结束日期
  	private Date endDate;

    
    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getSettleType() {
		return settleType;
	}

	public void setSettleType(int settleType) {
		this.settleType = settleType;
	}
	public List<HosOutBalanceDetailVo> getLstDetail() {
		return lstDetail;
	}

	public void setLstDetail(List<HosOutBalanceDetailVo> lstDetail) {
		this.lstDetail = lstDetail;
	}

	public String getFillterName() {
		return fillterName;
	}

	public void setFillterName(String fillterName) {
		this.fillterName = fillterName;
	}

	public String getProvDeptErpName() {
		return provDeptErpName;
	}

	public void setProvDeptErpName(String provDeptErpName) {
		this.provDeptErpName = provDeptErpName;
	}
}
