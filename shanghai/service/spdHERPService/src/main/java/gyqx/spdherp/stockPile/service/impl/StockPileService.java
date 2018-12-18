package gyqx.spdherp.stockPile.service.impl;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;
import gyqx.spdherp.stockPile.dao.StockPileDao;
import gyqx.spdherp.stockPile.service.IStockPileService;
import gyqx.spdherp.stockPile.vo.StockPileHZVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zouliang on 2017-8-22.
 */
@Service
public class StockPileService implements IStockPileService {
    @Resource
    private StockPileDao dao;
    @Resource
    private UtilsContext utilsContext;

    public QueryResult<StockPileHZVo> stockPileHZ(QueryInfo<StockPileHZVo> bean) throws Exception {
        PageUtils.startPage(bean);
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        String corpId = user.getCorpId();
        StockPileHZVo queryInfo=bean.getQueryObject();
        queryInfo.setHosId(corpId);
        return PageUtils.endPage(dao.listStockPileHZ(queryInfo));
    }
}
