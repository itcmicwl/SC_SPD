package gyqx.spdhdi.myGoods.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.myGoods.dao.mapper.IProvGoodsHosBatchMgrMapper;
import gyqx.spdhdi.myGoods.vo.HosProvInfoVo;

@Repository
public class ProvGoodsHosBatchMgrDao {
	
	@Resource
	IProvGoodsHosBatchMgrMapper provGoodsHosBatchMgrMapper;
	/**
	 * 获取医院供应商列表
	 * @param hosProvInfoVo
	 * @return
	 */
	public List<HosProvInfoVo> getHosProvInfoVoList(HosProvInfoVo  hosProvInfoVo) {
		return provGoodsHosBatchMgrMapper.getHosProvInfoVoList(hosProvInfoVo);
	}
}
