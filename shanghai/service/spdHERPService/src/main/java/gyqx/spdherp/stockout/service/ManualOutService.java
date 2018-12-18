package gyqx.spdherp.stockout.service;

import common.db.query.QueryResult;
import gyqx.spdherp.stockout.vo.StockpileHzVo;

/**
 * @Auther Liangwu
 * @Date 17-9-11 上午10:14
 */
public interface ManualOutService {
    QueryResult<StockpileHzVo> listStockGoods(String stockId, Integer stocKind, int pageNum, int pageSize);
}
