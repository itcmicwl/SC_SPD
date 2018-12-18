package gyqx.spdherp.settleCenter.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysOrg;
import gyqx.spdherp.po.HosOutBalance;
import gyqx.spdherp.settleCenter.vo.HosOutBalanceDetailVo;
import gyqx.spdherp.settleCenter.vo.HosOutBalanceVo;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;

public interface IHosOutBalanceService 
{
	HosOutBalanceVo getById(String id) throws Exception ;
	HosOutBalanceVo getByBillId(String id) throws Exception ;
	List<String> createSettleBill(HosOutBalanceVo hosOutBalance) throws Exception;
	String add(HosOutBalanceVo hosOutBalance) throws Exception ;
	HosOutBalanceVo update(HosOutBalanceVo hosOutBalance) throws Exception;
	int delById(String id) throws Exception;

	List<HosOutBalanceVo> list(HosOutBalanceVo bean) throws Exception;
	
	QueryResult<HosOutBalanceVo> listByPage(QueryInfo<HosOutBalanceVo> queryInfo) throws Exception;
	List<SysOrg> getSettleSubProvList(QueryInfo<Out4Settle> queryInfo) throws Exception;
	
	QueryResult<HosOutBalanceDetailVo> getExportData(QueryInfo<HosOutBalanceVo> queryInfo) throws Exception;

}


