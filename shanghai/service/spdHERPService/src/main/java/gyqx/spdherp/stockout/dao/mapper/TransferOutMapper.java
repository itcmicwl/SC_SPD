package gyqx.spdherp.stockout.dao.mapper;

import gyqx.spdherp.stockout.vo.IdNameVo;
import gyqx.spdherp.stockout.vo.StockpileHzVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-9-8 下午3:33
 */
public interface TransferOutMapper {
    List<StockpileHzVo> listStockGoods(@Param("hosId") String hosId,
                                       @Param("stockId") String stockId,
                                       @Param("stocKind") Integer stocKind);

    List<IdNameVo> listOrgsByHosId(@Param("hosId") String hosId,
                                   @Param("deptId") String deptId);
}
