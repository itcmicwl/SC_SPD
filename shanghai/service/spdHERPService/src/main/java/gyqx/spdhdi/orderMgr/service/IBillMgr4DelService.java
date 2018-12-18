package gyqx.spdhdi.orderMgr.service;


import java.util.List;
import java.util.Map;

import gyqx.spdhdi.orderMgr.vo.DistrBillListVo;


public interface IBillMgr4DelService 
{
	Map getContextBillByPSid(String psId) throws Exception ;
	
	int deleteBillRows(List<DistrBillListVo> details)  throws Exception ;
	
	int deleteBillRow(DistrBillListVo detail)  throws Exception ;
	
	Map getBillContextIds(String id) throws Exception ;

}


