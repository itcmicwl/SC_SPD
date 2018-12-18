package gyqx.spdhdi.orderMgr.service;


import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.orderMgr.vo.PurconfirmListVo;
import gyqx.spdhdi.orderMgr.vo.PurconfirmVo;
import gyqx.spdhdi.uploadData.vo.Msg;

public interface IPurconfirmService 
{
	List<String> createPurconfirmBill(PurconfirmVo purconfirm) throws Exception;
	PurconfirmVo getById(String id) throws Exception ;
	PurconfirmVo getByBillid(String billid)  throws Exception ;
	String addPurc(PurconfirmVo purconfirmVo) throws Exception ;
	PurconfirmVo update(PurconfirmVo purconfirmVo) throws Exception;
	int updateByBatch(List<PurconfirmVo> lst) throws Exception;
	int delete(PurconfirmVo purconfirmVo) throws Exception;	
	List<PurconfirmVo> list(PurconfirmVo purconfirmVo) throws Exception;
	List<PurconfirmVo> secList(PurconfirmVo purconfirmVo) throws Exception;
	QueryResult<PurconfirmVo> secListByPage(QueryInfo<PurconfirmVo> queryInfo) throws Exception;
	QueryResult<PurconfirmVo> listByPage(QueryInfo<PurconfirmVo> queryInfo) throws Exception;
	//明细表
	boolean insertDetailByBatch(List<PurconfirmListVo> lst) throws Exception;
	void delDetailById(String id) throws Exception;
	void delDetailByBillid(String billid) throws Exception;
	List<PurconfirmListVo> detailList(PurconfirmListVo bean) throws Exception;
	// JDE
	Msg purchaseAgain(String billId) throws Exception;
	List<PurconfirmVo> getAllBillsNotSend();
	boolean getCanSendToJdeByProvId(String provId) throws Exception;
	String[] getCanSendToJdeByProvIds()throws Exception;
}