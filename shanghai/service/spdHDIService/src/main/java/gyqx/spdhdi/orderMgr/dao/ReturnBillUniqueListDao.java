package gyqx.spdhdi.orderMgr.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.orderMgr.dao.mapper.IReturnBillUniqueListMapper;
import gyqx.spdhdi.orderMgr.vo.ReturnBillUniqueListVo;
import gyqx.spdhdi.po.ReturnBillUniqueList;

@Repository
public class ReturnBillUniqueListDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IReturnBillUniqueListMapper mapper;

	public ReturnBillUniqueList get(String id)
	{
		return dao.get(ReturnBillUniqueList.class, id);	
	}	

	public ReturnBillUniqueList insertAndGet(ReturnBillUniqueList returnBillUniqueList) throws Exception 
	{
		return dao.insertAndGet(returnBillUniqueList);
	}

	public ReturnBillUniqueList updateAndGet(ReturnBillUniqueList returnBillUniqueList) throws Exception {
		return dao.updateAndGet(returnBillUniqueList);
	}
	public int updateFields(ReturnBillUniqueList returnBillUniqueList,String fieldNames) throws Exception
	{
		return dao.updateFields(returnBillUniqueList, fieldNames);
	}
	public int updateNotNullFields(ReturnBillUniqueList returnBillUniqueList) throws Exception
	{
		return dao.updateNotNullFields(returnBillUniqueList);
	}

	public void delete(ReturnBillUniqueList returnBillUniqueList) throws Exception {
		dao.delete(returnBillUniqueList);
	}
	
	public ReturnBillUniqueList getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (ReturnBillUniqueList) dao.getByFieldName(ReturnBillUniqueList.class, fieldName, fieldValue);
	}
	
	public List<ReturnBillUniqueList> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<ReturnBillUniqueList>) dao.queryBeanList(ReturnBillUniqueList.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(ReturnBillUniqueList.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<ReturnBillUniqueList> list(ReturnBillUniqueList qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
	public List<ReturnBillUniqueListVo> listVo(ReturnBillUniqueListVo qbean) throws Exception{		
		
		return mapper.listVo(qbean);
	}
	public int deleteReturnBillUniqueListByPid(String pid){
		return mapper.deleteReturnBillUniqueListByPid(pid);
	}
	public int deleteReturnBillUniqueListByBillId(String returnBillId){
		return mapper.deleteReturnBillUniqueListByBillId(returnBillId);
	}
}


