package gyqx.spdherp.applyMgr.vo;

import java.util.List;

import javax.persistence.Column;

import common.db.meta.Title;

public class DeptCarGoodsStVo extends DeptGoodsStVo {

	private static final long serialVersionUID = 1324955749008942271L;
	/**
	 * carId 请购车ID
	 */
	@Column(name = "car_id")
	@Title("请购车ID")
	private String carId;
	/**
	 * buyKind 请购类型 10 实采 20 虚采
	 */
	private int buyKind;
	


	/**
	 * 请购明细id列表(三级科室请购合并时使用)
	 */
	private List<String> subIds;

	public int getBuyKind() {
		return buyKind;
	}

	public void setBuyKind(int buyKind) {
		this.buyKind = buyKind;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public List<String> getSubIds() {
		return subIds;
	}

	public void setSubIds(List<String> subIds) {
		this.subIds = subIds;
	}
	
}
