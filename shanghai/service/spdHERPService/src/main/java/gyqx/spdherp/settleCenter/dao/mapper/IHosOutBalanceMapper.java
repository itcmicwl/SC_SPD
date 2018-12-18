package gyqx.spdherp.settleCenter.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.platform.po.SysOrg;
import gyqx.spdherp.settleCenter.vo.HosOutBalanceDetailVo;
import gyqx.spdherp.settleCenter.vo.HosOutBalanceVo;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;

public interface IHosOutBalanceMapper {
	int update(HosOutBalanceVo bean);
	int insert(HosOutBalanceVo bean);
	int delById(@Param("id") String id);
	HosOutBalanceVo getById(@Param("id") String id);
	HosOutBalanceVo getByBillId(@Param("billId") String billId);
	List<HosOutBalanceVo> list(HosOutBalanceVo queryInfo) ;
	List<SysOrg> getSettleSubProvList(Out4Settle queryObject);
	List<HosOutBalanceDetailVo> getExportData(HosOutBalanceVo queryInfo) ;
}

