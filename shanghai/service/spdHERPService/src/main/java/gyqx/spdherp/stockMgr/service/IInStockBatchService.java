package gyqx.spdherp.stockMgr.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdherp.po.InStockBatch;
import gyqx.spdherp.stockMgr.vo.InStockBatchVo;

public interface IInStockBatchService 
{
	InStockBatchVo get(String id) throws Exception ;
	int insert(InStockBatchVo inStockList) throws Exception ;
	int insertByBatch(List<InStockBatchVo> lst)  throws Exception;
	int update(InStockBatchVo inStockList) throws Exception;
	int UpdateByBatch(List<InStockBatchVo> lst) throws Exception;
	int delById(String id) throws Exception;
	int delByBillId(String billId) throws Exception;
	List<InStockBatchVo> list(InStockBatchVo bean) throws Exception;
	QueryResult<InStockBatchVo> listByPage(QueryInfo<InStockBatchVo> queryInfo) throws Exception;

}


