package gyqx.spdhdi.settleCenter.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.settleCenter.dao.mapper.IHosOutBalanceDetailMapper;
import gyqx.spdhdi.settleCenter.vo.HosOutBalanceDetailVo;

@Repository
public class HosOutBalanceDetailDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IHosOutBalanceDetailMapper mapper;

	public HosOutBalanceDetailVo get(String id)
	{
		return mapper.get(id);
	}	

	public int insert(HosOutBalanceDetailVo hosOutBalanceDetail) throws Exception
	{
		return mapper.insert(hosOutBalanceDetail);
	}
	public int insertByBatch(List<HosOutBalanceDetailVo> lst) throws Exception{
		return mapper.insertByBatch(lst);
	}
	public int update(HosOutBalanceDetailVo hosOutBalanceDetail) throws Exception{
		return mapper.update(hosOutBalanceDetail);
	}
	public int updateByBatch(List<HosOutBalanceDetailVo> lst) throws Exception{
		return mapper.updateByBatch(lst);
	}
	public int delById(String id)throws Exception{
		return mapper.delById(id);
	}
	public int delByBillId(String billId) throws Exception{
		return mapper.delByBillId(billId);
	}
	public List<HosOutBalanceDetailVo> list(HosOutBalanceDetailVo qbean) throws Exception{
		return mapper.list(qbean);
	}
}


