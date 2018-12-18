package gyqx.spdherp.stockout.dao.mapper;

import gyqx.spdherp.stockout.vo.StockpileHzVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-9-11 上午10:15
 */
public interface ManualOutMapper {
    List<StockpileHzVo> listStockGoods(@Param("hosId") String hosId,
                                       @Param("stockId") String stockId,
                                       @Param("stocKind") Integer stocKind);
}
