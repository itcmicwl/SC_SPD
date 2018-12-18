package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.orderMgr.vo.ReceiveBillUniqueListVo;
 
public interface IReceiveBillUniqueListMapper {
	int update(ReceiveBillUniqueListVo bean);
	List<ReceiveBillUniqueListVo> list(ReceiveBillUniqueListVo queryInfo) ;
	ReceiveBillUniqueListVo get(String id);
	int insertBatch(List<ReceiveBillUniqueListVo> bean);
	int delById(@Param("id") String id);
	int delByPid(@Param("pid") String pid);
	int existUniqueCode(@Param("uniqueCode") String uniqueCode,@Param("id") String id);
}

