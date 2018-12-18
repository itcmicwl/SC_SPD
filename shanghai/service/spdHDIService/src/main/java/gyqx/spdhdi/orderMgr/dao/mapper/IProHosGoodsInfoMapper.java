package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import gyqx.spdhdi.orderMgr.vo.PurchaseVo;
import gyqx.spdhdi.orderMgr.vo.ProHosGoodsInfoVo;

public interface IProHosGoodsInfoMapper {
	List<ProHosGoodsInfoVo> getProHosGoodsList(ProHosGoodsInfoVo proHosGoodsInfoVo);
}
