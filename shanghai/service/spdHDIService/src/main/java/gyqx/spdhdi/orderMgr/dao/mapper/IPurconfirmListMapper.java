package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.orderMgr.vo.DistrBillListVo;
import gyqx.spdhdi.orderMgr.vo.PurconfirmListVo;
 
public interface IPurconfirmListMapper {
	int update(PurconfirmListVo purconfirmListvo);
	int updateByBatch(List<PurconfirmListVo> lst);
	int insertByBatch(List<PurconfirmListVo> lst);
	int delByBillid(@Param("billId") String billId);
	int delById(@Param("id") String id);
	List<PurconfirmListVo> getListByDistrBillList(List<DistrBillListVo> lst);
	List<PurconfirmListVo> list(PurconfirmListVo queryInfo) ;
}

