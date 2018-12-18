package gyqx.spdhdi.orderMgr.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.po.ReturnconfirmList;

public interface IReturnconfirmListService 
{
	ReturnconfirmList get(String id) throws Exception ;
	ReturnconfirmList add(ReturnconfirmList returnconfirmList) throws Exception ;
	ReturnconfirmList update(ReturnconfirmList returnconfirmList) throws Exception;
	
	List<ReturnconfirmList> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<ReturnconfirmList> list(ReturnconfirmList bean) throws Exception;
	
	QueryResult<ReturnconfirmList> listByPage(QueryInfo<ReturnconfirmList> queryInfo) throws Exception;	

}


