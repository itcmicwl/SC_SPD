package gyqx.spdhdi.orderMgr.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;
import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.po.ReturnconfirmList;
import gyqx.spdhdi.orderMgr.dao.ReturnconfirmListDao;
import gyqx.spdhdi.orderMgr.service.IReturnconfirmListService;

@Service
public class ReturnconfirmListService implements IReturnconfirmListService {
	
	@Resource
	private ReturnconfirmListDao  dao;
	
	public ReturnconfirmList get(String id)
	{
		return dao.get( id);	
	}	

	public ReturnconfirmList add(ReturnconfirmList returnconfirmList) throws Exception 
	{
		return dao.insertAndGet(returnconfirmList);
	}
		
	public ReturnconfirmList update(ReturnconfirmList returnconfirmList) throws Exception {
		return dao.updateAndGet(returnconfirmList);
	}
	public int updateFields(ReturnconfirmList returnconfirmList,String fieldNames) throws Exception
	{
		return dao.updateFields(returnconfirmList, fieldNames);
	}

	public void delete(ReturnconfirmList returnconfirmList) throws Exception {
		dao.delete(returnconfirmList);
	}
	
	public ReturnconfirmList getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (ReturnconfirmList) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<ReturnconfirmList> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<ReturnconfirmList>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<ReturnconfirmList> list(ReturnconfirmList bean) throws Exception
	{
		return (List<ReturnconfirmList>) dao.list(bean);
	}	
	
	public QueryResult<ReturnconfirmList> listByPage(QueryInfo<ReturnconfirmList> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

}


