package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.orderMgr.vo.ReceiveBillVo;
 
public interface IReceiveBillMapper {
	int insert(ReceiveBillVo bean);
	int update(ReceiveBillVo bean);
	ReceiveBillVo getByBillId(@Param("billId") String billId);
	ReceiveBillVo getById(@Param("id") String id);
	List<ReceiveBillVo> getReceiveBillList(ReceiveBillVo queryInfo) ;
}

