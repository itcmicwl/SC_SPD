package gyqx.spdherp.stockMgr.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdherp.stockMgr.vo.InStockListVo;

public interface IInStockListService 
{
	InStockListVo get(String id) throws Exception ;
	int insert(InStockListVo inStockList) throws Exception ;
	int insertByBatch(List<InStockListVo> lst)  throws Exception;
	int update(InStockListVo inStockList) throws Exception;
	int UpdateByBatch(List<InStockListVo> lst) throws Exception;
	int delById(String id) throws Exception;
	int delByBillId(String billId) throws Exception;
	List<InStockListVo> list(InStockListVo bean) throws Exception;
	QueryResult<InStockListVo> listByPage(QueryInfo<InStockListVo> queryInfo) throws Exception;
	List<InStockListVo> list4Instock(InStockListVo bean) throws Exception;

}


