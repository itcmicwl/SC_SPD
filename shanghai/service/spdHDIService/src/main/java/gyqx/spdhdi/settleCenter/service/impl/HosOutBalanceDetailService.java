package gyqx.spdhdi.settleCenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import gyqx.spdhdi.settleCenter.dao.HosOutBalanceDetailDao;
import gyqx.spdhdi.settleCenter.service.IHosOutBalanceDetailService;
import gyqx.spdhdi.settleCenter.vo.HosOutBalanceDetailVo;

@Service
public class HosOutBalanceDetailService implements IHosOutBalanceDetailService {
	
	@Resource
	private HosOutBalanceDetailDao  dao;
	@Resource
	private SysAtomUtil atomUtil;
	@Override
	public HosOutBalanceDetailVo get(String id) throws Exception {
		return dao.get(id);
	}

	@Override
	public HosOutBalanceDetailVo add(HosOutBalanceDetailVo hosOutBalanceDetail) throws Exception {
		String id = atomUtil.newValue("outBalanceLstId");
		hosOutBalanceDetail.setId(id);
		dao.insert(hosOutBalanceDetail);
		return this.get(id);
	}

	@Override
	public HosOutBalanceDetailVo update(HosOutBalanceDetailVo hosOutBalanceDetail) throws Exception {
		dao.update(hosOutBalanceDetail);
		return this.get(hosOutBalanceDetail.getId());
	}

	@Override
	public int insertByBatch(List<HosOutBalanceDetailVo> lst) throws Exception {
		return dao.insertByBatch(lst);
	}

	@Override
	public int updateByBatch(List<HosOutBalanceDetailVo> lst) throws Exception {
		return dao.updateByBatch(lst);
	}

	@Override
	public int delById(String id) throws Exception {
		return dao.delById(id);
	}

	@Override
	public int delByBillId(String billId) throws Exception {
		return dao.delByBillId(billId);
	}

	public List<HosOutBalanceDetailVo> list(HosOutBalanceDetailVo bean) throws Exception{
		return dao.list(bean);
	}	
	
	public QueryResult<HosOutBalanceDetailVo> listByPage(QueryInfo<HosOutBalanceDetailVo> queryInfo) throws Exception{
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

}


