package gyqx.spdhdi.orderMgr.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import gyqx.spdhdi.orderMgr.dao.ReturnBillDao;
import gyqx.spdhdi.orderMgr.dao.ReturnBillListDao;
import gyqx.spdhdi.orderMgr.dao.ReturnconfirmDao;
import gyqx.spdhdi.orderMgr.dao.ReturnconfirmListDao;
import gyqx.spdhdi.orderMgr.service.IReturnconfirmService;
import gyqx.spdhdi.orderMgr.vo.ReturnconfirmListVo;
import gyqx.spdhdi.orderMgr.vo.ReturnconfirmVo;
import gyqx.spdhdi.po.ReturnBillList;
import gyqx.spdhdi.po.Returnconfirm;
import gyqx.spdhdi.po.ReturnconfirmList;

@Service
public class ReturnconfirmService implements IReturnconfirmService {
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private ReturnconfirmDao  dao;
	@Resource
	private ReturnconfirmListDao  subDao;
	
	@Resource
	private ReturnBillListDao  rbSubDao;
	@Resource
	private ReturnBillDao  rbDao;
	
	@Resource
	private SimpleDao  simpleDao;
	public Returnconfirm get(String id)
	{
		return dao.get( id);	
	}	

	public Returnconfirm add(Returnconfirm returnconfirm) throws Exception 
	{
		return dao.insertAndGet(returnconfirm);
	}
		
	public Returnconfirm update(Returnconfirm returnconfirm) throws Exception {
		return dao.updateAndGet(returnconfirm);
	}
	public int updateFields(Returnconfirm returnconfirm,String fieldNames) throws Exception
	{
		return dao.updateFields(returnconfirm, fieldNames);
	}

	public void delete(Returnconfirm returnconfirm) throws Exception {
		dao.delete(returnconfirm);
	}
	
	public Returnconfirm getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (Returnconfirm) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<Returnconfirm> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<Returnconfirm>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<Returnconfirm> list(Returnconfirm bean) throws Exception
	{
		return (List<Returnconfirm>) dao.list(bean);
	}	
	
	public QueryResult<Returnconfirm> listByPage(QueryInfo<Returnconfirm> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

	public QueryResult<ReturnconfirmVo> listByPageVo (QueryInfo<ReturnconfirmVo> queryInfo) throws Exception{
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.listVo(queryInfo.getQueryObject()));
	}
	
	public ReturnconfirmVo addBill(ReturnconfirmVo billVo) throws Exception{
		//插入确认单主表 returnconfirm_id
		//插入确认单明细表returnconfirm_list_id
		Returnconfirm main = new Returnconfirm();
		Tx.transform(billVo).to(main);
		main.setId(atomUtil.newValue("returnconfirm_id"));
		main.setBillId(main.getId());
	//	main.setReturnBillId(billVo.getReturnBillId());
		main.setFillDate(new Date());
		main.setSumRow(billVo.getLstDetail().size());
		main.setVersion(0);
		main =dao.insertAndGet(main);
		for(ReturnconfirmListVo detailVo:billVo.getLstDetail()){
			ReturnconfirmList detail = new ReturnconfirmList();
			Tx.transform(detailVo).to(detail);
			detail.setId(atomUtil.newValue("returnconfirm_list_id"));
			detail.setBillId(main.getBillId());
			detail.setPid(main.getId());
			detail=subDao.insertAndGet(detail);
			//回写退货单 (修改明细状态,检查所有明细状态,所有明细状态改变则修改主表状态)
			
			int i= reWrite2ReturnBill(detail.getReturnBillId(),detail.getReturnBillRownum());
			
		}
		return billVo;
	}
	
	public int reWrite2ReturnBill(String billId,int rownum) throws Exception{
		int i=0;
	    ReturnBillList  qbeanSub=	new ReturnBillList();
	    qbeanSub.setReturnBillId(billId);
	  //  List<ReturnBillList> detailLst = (List<ReturnBillList>) simpleDao.queryByFieldNames(ReturnBillList.class, "returnBillId,rowNumber", billId,rownum);
	     List<ReturnBillList> detailLst = rbSubDao.list(qbeanSub);
	     
	     boolean flag= true;
	     for(ReturnBillList sub:detailLst){
	    	 if(sub.getRowNumber()==rownum){
	    		 sub.setStatus(gyqx.spdhdi.orderMgr.Constance.RETRUNBILL_SUB_STATUS_DOWN); //0 改为已确认
	    		 sub = rbSubDao.updateAndGet(sub);		
	    	 }

	    	 if(sub.getStatus()==1){
	    		 flag =false;
	    	 }
	     }
	     if(flag){
	    	 //修改主表状态 30 全部确认	 
	    	 i= simpleDao.executeSql("update  return_bill set status=? where bill_id=? ", gyqx.spdhdi.orderMgr.Constance.RETRUNBILL_MAIN_STATUS_CONFIRM,billId);
	     }
	    
	   
	    
	      return i;
	    	
	    
	} 
	
//	publicReturnconfirmVo updateBill(ReturnconfirmVo billVo) throws Exception{
//		//修改确认单主表
//		//删除明细
//		//插入明细
//		//判断"保存" 还是" 提交"
//		//if(提交) 
//		//回写退货单 (修改明细状态,检查所有明细状态,所有明细状态改变则修改主表状态)
//		
//	}
	public ReturnconfirmVo getByBillId(String billId) throws Exception{
		ReturnconfirmVo rv=	dao.getByBillId(billId);
		if(rv!=null){
			ReturnconfirmListVo subv = new ReturnconfirmListVo();
			subv.setPid(rv.getId());
			List<ReturnconfirmListVo> lst = subDao.listVo(subv);
			rv.setLstDetail(lst);
		}
		return rv;
	}
}


