package gyqx.spdhdi.orderMgr.dao.mapper;

import gyqx.spdhdi.orderMgr.vo.ReturnBillVo;
import gyqx.spdhdi.po.ReturnBill;

import java.util.List;
 
public interface IReturnBillMapper {
	int update(ReturnBill bean);
	List<ReturnBill> list(ReturnBill queryInfo) ;
	List<ReturnBillVo> listReturnBillVo(ReturnBillVo queryInfo) ;
}

