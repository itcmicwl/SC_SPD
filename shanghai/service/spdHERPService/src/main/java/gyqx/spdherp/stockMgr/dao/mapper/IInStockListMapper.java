package gyqx.spdherp.stockMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdherp.stockMgr.vo.InStockListVo;

public interface IInStockListMapper {
	InStockListVo get(@Param("id") String id);
	int update(InStockListVo bean);
	int insert(InStockListVo bean);
	int updateByBatch(List<InStockListVo> lst);
	int insertByBatch(List<InStockListVo> lst);
	int delById(@Param("id") String id);
	int delByBillId(@Param("billId") String billId);
	List<InStockListVo> list(InStockListVo queryInfo) ;
	int updateInBillLstStatus(List<InStockListVo> lst);
	List<InStockListVo> getSettledListByBillIdAndRow(List<InStockListVo> lst);
	List<InStockListVo> list4Instock(InStockListVo qbean);
}

