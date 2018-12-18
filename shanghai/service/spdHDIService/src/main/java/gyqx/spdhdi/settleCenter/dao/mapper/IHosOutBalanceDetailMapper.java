package gyqx.spdhdi.settleCenter.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.settleCenter.vo.HosOutBalanceDetailVo;

public interface IHosOutBalanceDetailMapper {
	HosOutBalanceDetailVo get(String id);
	int update(HosOutBalanceDetailVo bean);
	int insert(HosOutBalanceDetailVo bean);
	int insertByBatch(List<HosOutBalanceDetailVo> lst);
	int updateByBatch(List<HosOutBalanceDetailVo> lst);
	int delById(@Param("id") String id);
	int delByBillId(@Param("billId") String billId);
	List<HosOutBalanceDetailVo> list(HosOutBalanceDetailVo queryInfo) ;
}

