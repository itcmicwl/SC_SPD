package gyqx.spdhdi.baseData.dao;

import gyqx.spdhdi.baseData.dao.mapper.BasBarcodeRuleMapper;
import gyqx.spdhdi.baseData.vo.BasBarcodeRule;
import gyqx.spdhdi.po.ProvGoodsInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @Auther Liangwu
 * @Date 17-7-14 下午3:10
 */
@Repository
public class BasBarcodeDao {
    @Resource
    private BasBarcodeRuleMapper mapper;

    public Set<BasBarcodeRule> getByBarcodeLen(int barcodeLen) {
        return mapper.getByBarcodeLen(barcodeLen);
    }

    public Set<String> getHisNameByGoodsListId(List<ProvGoodsInfo> goodsList, String corpId) {
        return mapper.getHisNameByGoodsListId(goodsList, corpId);
    }

    public List<String> getGoodsHosId(String hosId, String provId, List<ProvGoodsInfo> goodsInfoList) {
        return mapper.getGoodsHosId(hosId, provId, goodsInfoList);
    }

    public List<String> getProvGoodsHosIds(String goodsNo, String hosId, String provId) {
        return mapper.getProvGoodsHosIds(goodsNo, hosId, provId);
    }
}
