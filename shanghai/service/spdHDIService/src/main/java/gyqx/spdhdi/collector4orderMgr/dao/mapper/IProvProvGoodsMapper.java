package gyqx.spdhdi.collector4orderMgr.dao.mapper;

import java.util.List;

import gyqx.spdhdi.collector4orderMgr.vo.ProvProvGoodsVo;
import gyqx.spdherp.po.ProvProvGoods;
 
public interface IProvProvGoodsMapper {
	int update(ProvProvGoods bean);
	int insert(ProvProvGoods bean);
	List<ProvProvGoods> list(ProvProvGoods queryInfo) ;
	List<ProvProvGoodsVo> listVo(ProvProvGoodsVo queryInfo) ;

}

