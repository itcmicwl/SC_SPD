package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.orderMgr.vo.ReturnBillUniqueListVo;
import gyqx.spdhdi.po.ReturnBillUniqueList;
 
public interface IReturnBillUniqueListMapper {
	int update(ReturnBillUniqueList bean);
	int insert(ReturnBillUniqueList bean);
	List<ReturnBillUniqueList> list(ReturnBillUniqueList queryInfo) ;
	List<ReturnBillUniqueListVo> listVo(ReturnBillUniqueListVo queryInfo) ;
	int deleteReturnBillUniqueListByPid(@Param("pid") String pid);
	int deleteReturnBillUniqueListByBillId(@Param("returnBillId") String returnBillId);

}

