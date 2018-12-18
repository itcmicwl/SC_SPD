package gyqx.spdhdi.orderMgr.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.orderMgr.dao.mapper.IProHosGoodsInfoMapper;
import gyqx.spdhdi.orderMgr.vo.ProHosGoodsInfoVo;

@Repository
public class ProHosGoodsInfoDao {
	@Resource
	IProHosGoodsInfoMapper proHosGoodsInfo;
	public List<ProHosGoodsInfoVo> getProHosGoodsList(ProHosGoodsInfoVo proHosGoodsInfoVo){
		return proHosGoodsInfo.getProHosGoodsList(proHosGoodsInfoVo);
	}
}
