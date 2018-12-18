package gyqx.spdhdi.orderMgr.dao;


import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.orderMgr.dao.mapper.IReturnconfirmMapper;
import gyqx.spdhdi.orderMgr.vo.ReturnconfirmVo;
import gyqx.spdhdi.po.Returnconfirm;

@Repository
public class ReturnconfirmDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IReturnconfirmMapper mapper;

	public Returnconfirm get(String id)
	{
		return dao.get(Returnconfirm.class, id);	
	}	

	public Returnconfirm insertAndGet(Returnconfirm returnconfirm) throws Exception 
	{
		return dao.insertAndGet(returnconfirm);
	}

	public Returnconfirm updateAndGet(Returnconfirm returnconfirm) throws Exception {
		return dao.updateAndGet(returnconfirm);
	}
	public int updateFields(Returnconfirm returnconfirm,String fieldNames) throws Exception
	{
		return dao.updateFields(returnconfirm, fieldNames);
	}
	public int updateNotNullFields(Returnconfirm returnconfirm) throws Exception
	{
		return dao.updateNotNullFields(returnconfirm);
	}

	public void delete(Returnconfirm returnconfirm) throws Exception {
		dao.delete(returnconfirm);
	}
	
	public Returnconfirm getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (Returnconfirm) dao.getByFieldName(Returnconfirm.class, fieldName, fieldValue);
	}
	
	public List<Returnconfirm> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<Returnconfirm>) dao.queryBeanList(Returnconfirm.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(Returnconfirm.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<Returnconfirm> list(Returnconfirm qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
	public List<ReturnconfirmVo> listVo(ReturnconfirmVo qbean) throws Exception{		
		
		return mapper.listVo(qbean);
	}
	public ReturnconfirmVo getByBillId(@Param("billId") String billId)throws Exception{		
		return mapper.getByBillId(billId);
	}
}


