package gyqx.spdherp.stocInfoMgr.dao.mapper;

import java.util.List;

import gyqx.spdhdi.myGoods.vo.HosGoodsInfoVo;
import gyqx.spdherp.po.StockShelf;
import gyqx.spdherp.stocInfoMgr.vo.QueryResultVo;
import gyqx.spdherp.stocInfoMgr.vo.QueryVo;
 
public interface IStocShelfGoodsInfoMapper {

	List<QueryResultVo> list(QueryVo queryInfo) ;

	List<HosGoodsInfoVo> queryStockShelfGoodsList(StockShelf stockShelf);
}

