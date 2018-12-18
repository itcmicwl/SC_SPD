package gyqx.spdhdi.baseData.dao.mapper;

import gyqx.spdhdi.baseData.vo.BasBarcodeRule;
import gyqx.spdhdi.po.ProvGoodsHos;
import gyqx.spdhdi.po.ProvGoodsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface BasBarcodeRuleMapper {
    Set<BasBarcodeRule> getByBarcodeLen(int len);

    Set<String> getHisNameByGoodsListId(@Param("goodsList") List<ProvGoodsInfo> goodsList,
                                        @Param("corpId") String corpId);

    List<String> getGoodsHosId(@Param("hosId") String hosId,
                               @Param("provId") String provId,
                               @Param("goodsInfoList") List<ProvGoodsInfo> goodsInfoList);

    List<String> getProvGoodsHosIds(@Param("goodsNo") String goodsNo,
                                    @Param("hosId") String hosId,
                                    @Param("provId") String provId);
}