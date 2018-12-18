package gyqx.spdherp.stocInfoMgr.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.myGoods.vo.HosGoodsInfoVo;
import gyqx.spdherp.po.GoodsPurTemplateGoods;
import gyqx.spdherp.po.StockShelf;
import gyqx.spdherp.stocInfoMgr.vo.QueryResultVo;
import gyqx.spdherp.stocInfoMgr.vo.QueryVo;
import gyqx.spdherp.stocInfoMgr.vo.StockShelfGoodsInfoVo;
import gyqx.spdherp.stocInfoMgr.vo.StockShelfGoodsVo;
import gyqx.spdherp.stocInfoMgr.vo.StockShelfVo;

public interface IStocShelfGoodsInfoService {
	
	List<StockShelfVo> queryStockShelfList(StockShelf paramObj) throws Exception;
	
	QueryResult<QueryResultVo> listByPage(QueryInfo<QueryVo> queryInfo) throws Exception;

	List<GoodsPurTemplateGoods> importStockShelfGoods(StockShelfGoodsVo param) throws Exception;

	QueryResult<HosGoodsInfoVo> queryStockShelfGoodsList(QueryInfo<StockShelfGoodsInfoVo> queryInfo) throws Exception;

	List<HosGoodsInfoVo> deleteStockShelfGoods(List<HosGoodsInfoVo> hosGoodsInfoVos) throws Exception;

}
