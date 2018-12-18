package gyqx.spdherp.stockPile.dao.mapper;

import java.util.List;

import gyqx.spdherp.po.HosStockpile;
 
public interface IHosStockpileMapper {
	int update(HosStockpile bean);
	int insert(HosStockpile bean);
	int insertByBatch(List<HosStockpile> lst);
	List<HosStockpile> list(HosStockpile queryInfo) ;
}

