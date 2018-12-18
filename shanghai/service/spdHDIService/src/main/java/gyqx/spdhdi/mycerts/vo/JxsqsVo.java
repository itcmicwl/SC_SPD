package gyqx.spdhdi.mycerts.vo;

import java.util.List;

import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;
import gyqx.spdhdi.baseData.vo.BasGoodsInfoVo;
import gyqx.spdhdi.po.ProvJxsqs;

public class JxsqsVo extends ProvJxsqs {
	private static final long serialVersionUID = 1L;

	private String provName;// 授权对象
	private String chiefName;// 授权单位
	
	private List<BasCompanyInfoVo> basHosInfos;// 适用医院，提交表单用
	private List<JxsqsToHosVo> jxsqsHosInfos;// 适用医院，返回列表用
	
	private List<BasGoodsInfoVo> basGoodsInfos;// 授权品种，提交表单用
//	private List<JxsqsToHosVo> jxsqsHosInfos;// 适用医院，返回列表用

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getChiefName() {
		return chiefName;
	}

	public void setChiefName(String chiefName) {
		this.chiefName = chiefName;
	}

	public List<BasCompanyInfoVo> getBasHosInfos() {
		return basHosInfos;
	}

	public void setBasHosInfos(List<BasCompanyInfoVo> basHosInfos) {
		this.basHosInfos = basHosInfos;
	}

	public List<JxsqsToHosVo> getJxsqsHosInfos() {
		return jxsqsHosInfos;
	}

	public void setJxsqsHosInfos(List<JxsqsToHosVo> jxsqsHosInfos) {
		this.jxsqsHosInfos = jxsqsHosInfos;
	}

	public List<BasGoodsInfoVo> getBasGoodsInfos() {
		return basGoodsInfos;
	}

	public void setBasGoodsInfos(List<BasGoodsInfoVo> basGoodsInfos) {
		this.basGoodsInfos = basGoodsInfos;
	}

}
