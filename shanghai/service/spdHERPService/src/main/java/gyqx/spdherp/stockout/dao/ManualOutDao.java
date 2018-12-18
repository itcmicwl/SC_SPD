package gyqx.spdherp.stockout.dao;

import gyqx.spdherp.stockout.dao.mapper.ManualOutMapper;
import gyqx.spdherp.stockout.vo.StockpileHzVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-9-11 上午10:14
 */
@Repository
public class ManualOutDao {
    @Resource
    ManualOutMapper mapper;

    public List<StockpileHzVo> listStockGoods(String hosId, String stockId, Integer stocKind) {
        return mapper.listStockGoods(hosId, stockId, stocKind);
    }
}
