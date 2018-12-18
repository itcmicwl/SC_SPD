package gyqx.spdhdi.orderMgr.service;


import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillVo;

public interface IReceiveBillService 
{
	ReceiveBillVo getById(String id) throws Exception ;
	ReceiveBillVo getByBillId(String id) throws Exception ;
	ReceiveBillVo add(ReceiveBillVo receiveBill) throws Exception ;
	ReceiveBillVo update(ReceiveBillVo receiveBill) throws Exception;
	List<ReceiveBillVo> list(ReceiveBillVo bean) throws Exception;
	QueryResult<ReceiveBillVo> listByPageVo(QueryInfo<ReceiveBillVo> queryInfo) throws Exception;	
}


