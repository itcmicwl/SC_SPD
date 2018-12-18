package gyqx.spdhdi.orderMgr.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.orderMgr.dao.mapper.IReturnconfirmListMapper;
import gyqx.spdhdi.orderMgr.vo.ReturnconfirmListVo;
import gyqx.spdhdi.po.ReturnconfirmList;

@Repository
public class ReturnconfirmListDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IReturnconfirmListMapper mapper;

	public ReturnconfirmList get(String id)
	{
		return dao.get(ReturnconfirmList.class, id);	
	}	

	public ReturnconfirmList insertAndGet(ReturnconfirmList returnconfirmList) throws Exception 
	{
		return dao.insertAndGet(returnconfirmList);
	}

	public ReturnconfirmList updateAndGet(ReturnconfirmList returnconfirmList) throws Exception {
		return dao.updateAndGet(returnconfirmList);
	}
	public int updateFields(ReturnconfirmList returnconfirmList,String fieldNames) throws Exception
	{
		return dao.updateFields(returnconfirmList, fieldNames);
	}
	public int updateNotNullFields(ReturnconfirmList returnconfirmList) throws Exception
	{
		return dao.updateNotNullFields(returnconfirmList);
	}

	public void delete(ReturnconfirmList returnconfirmList) throws Exception {
		dao.delete(returnconfirmList);
	}
	
	public ReturnconfirmList getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (ReturnconfirmList) dao.getByFieldName(ReturnconfirmList.class, fieldName, fieldValue);
	}
	
	public List<ReturnconfirmList> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<ReturnconfirmList>) dao.queryBeanList(ReturnconfirmList.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(ReturnconfirmList.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<ReturnconfirmList> list(ReturnconfirmList qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
	
	public List<ReturnconfirmListVo> listVo(ReturnconfirmListVo qbean) throws Exception{		
		
		return mapper.listVo(qbean);
	}
}


