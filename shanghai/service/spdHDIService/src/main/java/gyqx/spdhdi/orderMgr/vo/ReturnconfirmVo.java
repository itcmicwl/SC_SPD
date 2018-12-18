package gyqx.spdhdi.orderMgr.vo;

import java.util.List;

import gyqx.spdhdi.po.Returnconfirm;
import gyqx.spdhdi.po.ReturnconfirmList;

public class ReturnconfirmVo extends Returnconfirm{
	
	private List<ReturnconfirmListVo> lstDetail;
	
	
	private String fillterName;


	public String getFillterName() {
		return fillterName;
	}

	public void setFillterName(String fillterName) {
		this.fillterName = fillterName;
	}

	public List<ReturnconfirmListVo> getLstDetail() {
		return lstDetail;
	}

	public void setLstDetail(List<ReturnconfirmListVo> lstDetail) {
		this.lstDetail = lstDetail;
	}



}
