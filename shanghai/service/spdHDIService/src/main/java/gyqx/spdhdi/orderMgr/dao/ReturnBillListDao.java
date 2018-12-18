package gyqx.spdhdi.orderMgr.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.orderMgr.dao.mapper.IReturnBillListMapper;
import gyqx.spdhdi.orderMgr.vo.ReturnBillListVo;
import gyqx.spdhdi.po.ReturnBillList;

@Repository
public class ReturnBillListDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IReturnBillListMapper mapper;

	public ReturnBillList get(String id)
	{
		return dao.get(ReturnBillList.class, id);	
	}	

	public ReturnBillList insertAndGet(ReturnBillList returnBillList) throws Exception 
	{ 
		String id =atomUtil.newValue("return_bill_list_id");  
		returnBillList.setId(id);
		returnBillList.setVersion(0);
		return dao.insertAndGet(returnBillList);
	}

	public ReturnBillList updateAndGet(ReturnBillList returnBillList) throws Exception {
		return dao.updateAndGet(returnBillList);
	}
	public int updateFields(ReturnBillList returnBillList,String fieldNames) throws Exception
	{
		return dao.updateFields(returnBillList, fieldNames);
	}
	public int updateNotNullFields(ReturnBillList returnBillList) throws Exception
	{
		return dao.updateNotNullFields(returnBillList);
	}

	public void delete(ReturnBillList returnBillList) throws Exception {
		dao.delete(returnBillList);
	}
	
	public ReturnBillList getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (ReturnBillList) dao.getByFieldName(ReturnBillList.class, fieldName, fieldValue);
	}
	
	public List<ReturnBillList> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<ReturnBillList>) dao.queryBeanList(ReturnBillList.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(ReturnBillList.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<ReturnBillList> list(ReturnBillList qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
	
	public int deleteReturnBillListByBillId(String billId){
		return mapper.deleteReturnBillListByBillId(billId);
	}
	
	public List<ReturnBillListVo> listVo(ReturnBillListVo qbean) throws Exception{		
		
		return mapper.listVo(qbean);
	}
}


