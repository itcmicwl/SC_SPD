package gyqx.spdhdi.settleCenter.service;


import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.settleCenter.vo.HosOutBalanceVo;
import gyqx.spdhdi.uploadData.vo.Msg;

public interface IHosOutBalanceService 
{
	HosOutBalanceVo getById(String id) throws Exception ;
	HosOutBalanceVo getByBillId(String id) throws Exception ;
	HosOutBalanceVo add(HosOutBalanceVo hosOutBalance) throws Exception ;
	HosOutBalanceVo update(HosOutBalanceVo hosOutBalance) throws Exception;
	int delById(String id) throws Exception;

	List<HosOutBalanceVo> list(HosOutBalanceVo bean) throws Exception;
	
	QueryResult<HosOutBalanceVo> listByPage(QueryInfo<HosOutBalanceVo> queryInfo) throws Exception;
	
	// 上传JDE
	/**
	 * 上传JDE需要查询所有状态为"已审核"的结算单，且结算单位在prov_org_inner_code中有维护
	 * @return
	 */
	List<HosOutBalanceVo> getAllBillSubmit();
	Msg balanceUp(HosOutBalanceVo hosOutBalanceVo) throws Exception;
}


