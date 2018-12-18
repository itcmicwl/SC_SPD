package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.orderMgr.vo.ReceiveBillHumitureListVo;
 
public interface IReceiveBillHumitureListMapper {
	int update(ReceiveBillHumitureListVo bean);
	List<ReceiveBillHumitureListVo> list(ReceiveBillHumitureListVo queryInfo) ;
	ReceiveBillHumitureListVo get(@Param("id") String id);
	int insertBatch(List<ReceiveBillHumitureListVo> bean);
	int delById(@Param("id") String id);
	int delByPid(@Param("pid") String pid);
}

