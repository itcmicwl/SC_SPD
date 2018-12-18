package gyqx.spdherp.stockout.service.impl;

import common.db.query.QueryResult;
import common.utils.PageUtils;
import common.utils.UtilsContext;
import gyqx.spdherp.stockout.dao.ManualOutDao;
import gyqx.spdherp.stockout.service.ManualOutService;
import gyqx.spdherp.stockout.vo.StockpileHzVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-9-11 上午10:14
 */
@Service
public class ManualOutServiceImpl implements ManualOutService {
    @Resource
    private UtilsContext utilsContext;

    @Resource
    private ManualOutDao manualOutDao;

    @Override
    public QueryResult<StockpileHzVo> listStockGoods(String stockId, Integer stocKind, int pageNum, int pageSize) {
        String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageUtils.startPage(pageNum, pageSize);
        return PageUtils.endPage(manualOutDao.listStockGoods(hosId, stockId, stocKind));
    }
}
