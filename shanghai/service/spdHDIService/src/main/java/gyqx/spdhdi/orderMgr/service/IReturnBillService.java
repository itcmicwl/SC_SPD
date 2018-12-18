package gyqx.spdhdi.orderMgr.service;
import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.orderMgr.vo.ReturnBillListVo;
import gyqx.spdhdi.orderMgr.vo.ReturnBillVo;
import gyqx.spdhdi.po.ReturnBill;

public interface IReturnBillService 
{
	ReturnBill get(String id) throws Exception ;
	ReturnBill add(ReturnBill returnBill) throws Exception ;
	ReturnBill update(ReturnBill returnBill) throws Exception;
	
	List<ReturnBill> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<ReturnBill> list(ReturnBill bean) throws Exception;
	
	QueryResult<ReturnBill> listByPage(QueryInfo<ReturnBill> queryInfo) throws Exception;	
	
	public void add(ReturnBill returnBill,List<ReturnBillListVo>subList) throws Exception ;
	QueryResult<ReturnBillVo> listReturnBillVoByPage(QueryInfo<ReturnBillVo> queryInfo) throws Exception;	
	ReturnBillVo getReturnBillByBillId(String billId)throws Exception;
	void deleteReturnBill(ReturnBillVo rbVo) throws Exception;
    ReturnBillVo updateReturnBill(ReturnBillVo rbvo) throws Exception;

	
	

}


