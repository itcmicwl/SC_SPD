package gyqx.spdhdi.orderMgr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.orderMgr.dao.ReturnBillDao;
import gyqx.spdhdi.orderMgr.dao.ReturnBillListDao;
import gyqx.spdhdi.orderMgr.dao.ReturnBillUniqueListDao;
import gyqx.spdhdi.orderMgr.service.IReturnBillService;
import gyqx.spdhdi.orderMgr.vo.ReturnBillListVo;
import gyqx.spdhdi.orderMgr.vo.ReturnBillUniqueListVo;
import gyqx.spdhdi.orderMgr.vo.ReturnBillVo;
import gyqx.spdhdi.po.ReturnBill;
import gyqx.spdhdi.po.ReturnBillList;
import gyqx.spdhdi.po.ReturnBillUniqueList;

@Service
public class ReturnBillService implements IReturnBillService {
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private UserOnlineStateUtils userOnline;
	@Resource
	private ReturnBillDao  dao;
	@Resource
	private ReturnBillListDao  subDao;
	@Resource
	private ReturnBillUniqueListDao  uniqueDao;
	public ReturnBill get(String id)
	{
		return dao.get( id);	
	}	

	public ReturnBill add(ReturnBill returnBill) throws Exception 
	{
		
		return dao.insertAndGet(returnBill);
	}
		
	public ReturnBill update(ReturnBill returnBill) throws Exception {
		return dao.updateAndGet(returnBill);
	}
	public int updateFields(ReturnBill returnBill,String fieldNames) throws Exception
	{
		return dao.updateFields(returnBill, fieldNames);
	}

	public void delete(ReturnBill returnBill) throws Exception {
		dao.delete(returnBill);
	}
	
	public ReturnBill getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (ReturnBill) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<ReturnBill> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<ReturnBill>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<ReturnBill> list(ReturnBill bean) throws Exception
	{
		return (List<ReturnBill>) dao.list(bean);
	}	
	
	public QueryResult<ReturnBill> listByPage(QueryInfo<ReturnBill> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}
	
	public QueryResult<ReturnBillVo> listReturnBillVoByPage(QueryInfo<ReturnBillVo> queryInfo) throws Exception{
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.listReturnBillVo(queryInfo.getQueryObject()));
	}
	
	public void add(ReturnBill returnBill,List<ReturnBillListVo>subList) throws Exception 
	{
	
		returnBill.setSumRow(subList.size());
		returnBill = dao.insertAndGet(returnBill);
		 for(ReturnBillListVo subVo:subList){
			 if(subVo.getLstUniqueList().size()>0){
				 for(ReturnBillUniqueListVo unique:subVo.getLstUniqueList()){
					 ReturnBillUniqueList u = new ReturnBillUniqueList();
						Tx.transform(unique).to(u);
						u.setId(atomUtil.newValue("returnBill_unique_code_id"));   
						u.setPid(returnBill.getId());
						u.setReturnBillId(returnBill.getBillId());
						u.setReturnRowNumber(subVo.getRowNumber());
					 uniqueDao.insertAndGet(u);
				 }
			 }
			 ReturnBillList sub = new ReturnBillList();
				Tx.transform(subVo).to(sub);
			 sub.setStatus(1); //初始 默认状态，待确认
			 sub.setPid(returnBill.getId());
			 sub.setReturnBillId(returnBill.getBillId());
			 subDao.insertAndGet(sub);
		 }
		
	}
	
	public QueryResult<ReturnBillList> listReturnBillSubListByPage(QueryInfo<ReturnBillList> queryInfo) throws Exception{
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(subDao.list(queryInfo.getQueryObject()));
	}
	public ReturnBillVo getReturnBillByBillId(String billId)throws Exception{
		//ReturnBillVo p =  dao.getReturnBillByBillId(billId);
		ReturnBillVo rb = new ReturnBillVo();
		rb.setBillId(billId);
		rb = dao.listReturnBillVo(rb).get(0);
		ReturnBillListVo sub4Query= new ReturnBillListVo();
		sub4Query.setReturnBillId(billId);
		List<ReturnBillListVo> relst = subDao.listVo(sub4Query);
		for(ReturnBillListVo detail :relst){

			ReturnBillUniqueListVo qb = new ReturnBillUniqueListVo();
			 qb.setReturnBillId(detail.getReturnBillId());
			 qb.setPid(detail.getPid());
			 qb.setReturnRowNumber(detail.getRowNumber());
	
			List<ReturnBillUniqueListVo> uniqueVoLst = uniqueDao.listVo(qb);
			detail.setLstUniqueList(uniqueVoLst);
		
		}
		if(relst != null){
			rb.setReturnBillSubList(relst);
		}
		return rb;
	}
	public void deleteReturnBill(ReturnBillVo rbVo) throws Exception{
			ReturnBill rb = new ReturnBill();
			Tx.transform(rbVo).to(rb);
		    subDao.deleteReturnBillListByBillId(rbVo.getBillId());
		    dao.delete(rb);
	
	}
	public ReturnBillVo updateReturnBill(ReturnBillVo rbvo) throws Exception{
		ReturnBill rb = new ReturnBill();
		Tx.transform(rbvo).to(rb);
		rb=dao.updateAndGet(rb);
		  subDao.deleteReturnBillListByBillId(rbvo.getBillId()); //删除字表 
		  uniqueDao.deleteReturnBillUniqueListByBillId(rbvo.getBillId()); //删除唯一码表
	     for(ReturnBillListVo subVo:rbvo.getReturnBillSubList()){
			 for(ReturnBillUniqueListVo unique:subVo.getLstUniqueList()){
				 ReturnBillUniqueList u = new ReturnBillUniqueList();
					Tx.transform(unique).to(u);
					u.setId(atomUtil.newValue("returnBill_unique_code_id"));   
					u.setPid(rb.getId());
					u.setReturnBillId(rb.getBillId());
					u.setReturnRowNumber(subVo.getRowNumber());
				 uniqueDao.insertAndGet(u);
			 }
		     ReturnBillList sub = new ReturnBillList();
				Tx.transform(subVo).to(sub);
			 sub.setStatus(1); //初始 默认状态，待确认
			 sub.setPid(rb.getId());
			 sub.setReturnBillId(rb.getBillId());
			 subDao.insertAndGet(sub);
	     }
		return rbvo;
	}
}


