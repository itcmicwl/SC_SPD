package gyqx.spdhdi.orderMgr.dao;


import gyqx.spdhdi.orderMgr.dao.mapper.IReturnBillMapper;
import gyqx.spdhdi.orderMgr.vo.ReturnBillVo;
import gyqx.spdhdi.po.ReturnBill;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;

@Repository
public class ReturnBillDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IReturnBillMapper mapper;

	public ReturnBill get(String id)
	{
		return dao.get(ReturnBill.class, id);	
	}	

	public ReturnBill insertAndGet(ReturnBill returnBill) throws Exception 
	{
		String id =atomUtil.newValue("return_bill_id");  
		String billId =atomUtil.newValue("return_bill_code");  //请退单号
		returnBill.setId(id);
		returnBill.setBillId(billId);
		returnBill.setFillDate(new Date());
		returnBill.setVersion(0);
		return dao.insertAndGet(returnBill);
	}

	public ReturnBill updateAndGet(ReturnBill returnBill) throws Exception {
		return dao.updateAndGet(returnBill);
	}
	public int updateFields(ReturnBill returnBill,String fieldNames) throws Exception
	{
		return dao.updateFields(returnBill, fieldNames);
	}
	public int updateNotNullFields(ReturnBill returnBill) throws Exception
	{
		return dao.updateNotNullFields(returnBill);
	}

	public void delete(ReturnBill returnBill) throws Exception {
		dao.delete(returnBill);
	}
	
	public ReturnBill getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (ReturnBill) dao.getByFieldName(ReturnBill.class, fieldName, fieldValue);
	}
	
	public List<ReturnBill> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<ReturnBill>) dao.queryBeanList(ReturnBill.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(ReturnBill.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<ReturnBill> list(ReturnBill qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
	public List<ReturnBillVo> listReturnBillVo(ReturnBillVo qbean) throws Exception{		
		
		return mapper.listReturnBillVo(qbean);
	}
	
	
}


