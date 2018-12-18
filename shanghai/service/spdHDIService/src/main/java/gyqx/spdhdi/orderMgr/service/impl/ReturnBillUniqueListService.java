package gyqx.spdhdi.orderMgr.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;
import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.po.ReturnBillUniqueList;
import gyqx.spdhdi.orderMgr.dao.ReturnBillUniqueListDao;
import gyqx.spdhdi.orderMgr.service.IReturnBillUniqueListService;

@Service
public class ReturnBillUniqueListService implements IReturnBillUniqueListService {
	
	@Resource
	private ReturnBillUniqueListDao  dao;
	
	public ReturnBillUniqueList get(String id)
	{
		return dao.get( id);	
	}	

	public ReturnBillUniqueList add(ReturnBillUniqueList returnBillUniqueList) throws Exception 
	{
		return dao.insertAndGet(returnBillUniqueList);
	}
		
	public ReturnBillUniqueList update(ReturnBillUniqueList returnBillUniqueList) throws Exception {
		return dao.updateAndGet(returnBillUniqueList);
	}
	public int updateFields(ReturnBillUniqueList returnBillUniqueList,String fieldNames) throws Exception
	{
		return dao.updateFields(returnBillUniqueList, fieldNames);
	}

	public void delete(ReturnBillUniqueList returnBillUniqueList) throws Exception {
		dao.delete(returnBillUniqueList);
	}
	
	public ReturnBillUniqueList getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (ReturnBillUniqueList) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<ReturnBillUniqueList> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<ReturnBillUniqueList>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<ReturnBillUniqueList> list(ReturnBillUniqueList bean) throws Exception
	{
		return (List<ReturnBillUniqueList>) dao.list(bean);
	}	
	
	public QueryResult<ReturnBillUniqueList> listByPage(QueryInfo<ReturnBillUniqueList> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

}


