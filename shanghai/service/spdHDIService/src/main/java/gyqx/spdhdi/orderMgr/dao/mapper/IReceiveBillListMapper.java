package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;
 
public interface IReceiveBillListMapper {
	int insertBatch(List<ReceiveBillListVo> lst);
	int update(ReceiveBillListVo bean);
	int updateByBatch(List<ReceiveBillListVo> lst);
	List<ReceiveBillListVo> list(ReceiveBillListVo queryInfo);
	int deleteByBillId(@Param("billId") String billId);
	int deleteById(@Param("id") String id);
}

