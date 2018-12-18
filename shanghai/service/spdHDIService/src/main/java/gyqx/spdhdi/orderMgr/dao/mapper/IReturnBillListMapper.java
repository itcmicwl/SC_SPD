package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.orderMgr.vo.ReturnBillListVo;
import gyqx.spdhdi.po.ReturnBillList;
 
public interface IReturnBillListMapper {
	int update(ReturnBillList bean);
	List<ReturnBillList> list(ReturnBillList queryInfo) ;
	int deleteReturnBillListByBillId(@Param("billId") String billId);
	
	List<ReturnBillListVo> listVo(ReturnBillListVo queryInfo) ;

}

