package gyqx.spdhdi.orderMgr.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.po.ReturnBillList;

public interface IReturnBillListService 
{
	ReturnBillList get(String id) throws Exception ;
	ReturnBillList add(ReturnBillList returnBillList) throws Exception ;
	ReturnBillList update(ReturnBillList returnBillList) throws Exception;
	
	List<ReturnBillList> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<ReturnBillList> list(ReturnBillList bean) throws Exception;
	
	QueryResult<ReturnBillList> listByPage(QueryInfo<ReturnBillList> queryInfo) throws Exception;	

}


