package gyqx.spdhdi.orderMgr.service;


import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.orderMgr.vo.ReturnconfirmVo;
import gyqx.spdhdi.po.Returnconfirm;

public interface IReturnconfirmService 
{
	Returnconfirm get(String id) throws Exception ;
	Returnconfirm add(Returnconfirm returnconfirm) throws Exception ;
	Returnconfirm update(Returnconfirm returnconfirm) throws Exception;
	
	List<Returnconfirm> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<Returnconfirm> list(Returnconfirm bean) throws Exception;
	
	QueryResult<Returnconfirm> listByPage(QueryInfo<Returnconfirm> queryInfo) throws Exception;	
	QueryResult<ReturnconfirmVo> listByPageVo (QueryInfo<ReturnconfirmVo> queryInfo) throws Exception;	
	
	ReturnconfirmVo addBill(ReturnconfirmVo billVo) throws Exception;
	//ReturnconfirmVo updateBill(ReturnconfirmVo billVo) throws Exception;
	ReturnconfirmVo getByBillId(String billId) throws Exception;
}


