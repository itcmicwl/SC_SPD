package gyqx.spdhdi.orderMgr.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.po.ReturnBillUniqueList;

public interface IReturnBillUniqueListService 
{
	ReturnBillUniqueList get(String id) throws Exception ;
	ReturnBillUniqueList add(ReturnBillUniqueList returnBillUniqueList) throws Exception ;
	ReturnBillUniqueList update(ReturnBillUniqueList returnBillUniqueList) throws Exception;
	
	List<ReturnBillUniqueList> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<ReturnBillUniqueList> list(ReturnBillUniqueList bean) throws Exception;
	
	QueryResult<ReturnBillUniqueList> listByPage(QueryInfo<ReturnBillUniqueList> queryInfo) throws Exception;	

}


