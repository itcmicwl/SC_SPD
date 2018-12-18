package gyqx.spdhdi.orderMgr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import gyqx.spdhdi.orderMgr.dao.ReturnBillListDao;
import gyqx.spdhdi.orderMgr.service.IReturnBillListService;
import gyqx.spdhdi.orderMgr.vo.ReturnBillListVo;
import gyqx.spdhdi.po.ReturnBillList;

@Service
public class ReturnBillListService implements IReturnBillListService {
	
	@Resource
	private ReturnBillListDao  dao;
	
	public ReturnBillList get(String id)
	{
		return dao.get( id);	
	}	

	public ReturnBillList add(ReturnBillList returnBillList) throws Exception 
	{
		return dao.insertAndGet(returnBillList);
	}
		
	public ReturnBillList update(ReturnBillList returnBillList) throws Exception {
		return dao.updateAndGet(returnBillList);
	}
	public int updateFields(ReturnBillList returnBillList,String fieldNames) throws Exception
	{
		return dao.updateFields(returnBillList, fieldNames);
	}

	public void delete(ReturnBillList returnBillList) throws Exception {
		dao.delete(returnBillList);
	}
	
	public ReturnBillList getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (ReturnBillList) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<ReturnBillList> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<ReturnBillList>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<ReturnBillList> list(ReturnBillList bean) throws Exception
	{
		return (List<ReturnBillList>) dao.list(bean);
	}	
	
	public QueryResult<ReturnBillList> listByPage(QueryInfo<ReturnBillList> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}
	
	public List<ReturnBillListVo> listVo(ReturnBillListVo bean) throws Exception
	{
		return (List<ReturnBillListVo>) dao.listVo(bean);
	}	

}


