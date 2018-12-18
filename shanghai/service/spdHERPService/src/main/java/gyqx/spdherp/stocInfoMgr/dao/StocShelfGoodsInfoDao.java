package gyqx.spdherp.stocInfoMgr.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.myGoods.vo.HosGoodsInfoVo;
import gyqx.spdherp.po.StockShelf;
import gyqx.spdherp.stocInfoMgr.dao.mapper.IStocShelfGoodsInfoMapper;
import gyqx.spdherp.stocInfoMgr.vo.QueryResultVo;
import gyqx.spdherp.stocInfoMgr.vo.QueryVo;

@Repository
public class StocShelfGoodsInfoDao {
	@Resource
	private IStocShelfGoodsInfoMapper mapper;

	public List<QueryResultVo> list(QueryVo qbean) throws Exception {
		return mapper.list(qbean);
	}

	public List<HosGoodsInfoVo> queryStockShelfGoodsList(StockShelf stockShelf) {
		return mapper.queryStockShelfGoodsList(stockShelf);
	}
}
