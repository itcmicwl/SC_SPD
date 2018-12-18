package gyqx.spdherp.stockPile.dao;

import java.util.List;
import gyqx.spdherp.stockPile.dao.mapper.IStockPileMapper;
import gyqx.spdherp.stockPile.vo.StockPileHZVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by zouliang on 2017-8-22.
 */
@Repository
public class StockPileDao {
    @Resource
    private IStockPileMapper mapper;

    public List<StockPileHZVo> listStockPileHZ(StockPileHZVo queryInfo){
        return mapper.listStockPileHZ(queryInfo);
    }
}
