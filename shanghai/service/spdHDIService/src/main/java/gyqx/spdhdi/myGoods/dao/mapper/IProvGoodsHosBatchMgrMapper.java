package gyqx.spdhdi.myGoods.dao.mapper;

import java.util.List;

import gyqx.spdhdi.myGoods.vo.HosProvInfoVo;

public interface IProvGoodsHosBatchMgrMapper {

	/**
	 * 获取医院供应商列表
	 * @param hosProvInfoVo
	 * @return
	 */
	List<HosProvInfoVo> getHosProvInfoVoList(HosProvInfoVo hosProvInfoVo);

}
