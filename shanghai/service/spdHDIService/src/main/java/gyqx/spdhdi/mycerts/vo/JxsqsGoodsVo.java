package gyqx.spdhdi.mycerts.vo;

import gyqx.spdhdi.baseData.vo.BasGoodsInfoVo;

public class JxsqsGoodsVo extends BasGoodsInfoVo {
	
	/**
	 * 授权书关联商品ID
	 */
	private String pjgId;
	/**
	 * 授权书ID
	 */
	private String sqsId;

	public String getPjgId() {
		return pjgId;
	}

	public void setPjgId(String pjgId) {
		this.pjgId = pjgId;
	}

	public String getSqsId() {
		return sqsId;
	}

	public void setSqsId(String sqsId) {
		this.sqsId = sqsId;
	}
	
}
