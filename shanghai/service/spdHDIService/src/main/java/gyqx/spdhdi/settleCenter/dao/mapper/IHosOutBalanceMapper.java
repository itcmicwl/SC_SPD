package gyqx.spdhdi.settleCenter.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.settleCenter.vo.HosOutBalanceVo;

public interface IHosOutBalanceMapper {
	int update(HosOutBalanceVo bean);
	int insert(HosOutBalanceVo bean);
	int delById(@Param("id") String id);
	HosOutBalanceVo getById(@Param("id") String id);
	HosOutBalanceVo getByBillId(@Param("billId") String billId);
	List<HosOutBalanceVo> list(HosOutBalanceVo queryInfo) ;
	
	// 上传JDE
	List<HosOutBalanceVo> getAllBillSubmit();
}

