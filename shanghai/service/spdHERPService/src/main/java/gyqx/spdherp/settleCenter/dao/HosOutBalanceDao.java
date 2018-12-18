package gyqx.spdherp.settleCenter.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.platform.po.SysOrg;
import gyqx.spdherp.settleCenter.dao.mapper.IHosOutBalanceMapper;
import gyqx.spdherp.settleCenter.vo.HosOutBalanceDetailVo;
import gyqx.spdherp.settleCenter.vo.HosOutBalanceVo;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;

@Repository
public class HosOutBalanceDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IHosOutBalanceMapper mapper;

	public HosOutBalanceVo getById(String id) throws Exception{
	    return mapper.getById(id);
	}
    public HosOutBalanceVo getByBillId(String id) throws Exception{
        return mapper.getByBillId(id);
    }
	public int update(HosOutBalanceVo qbean) throws Exception{
		return  mapper.update(qbean);
	}
	public int insert(HosOutBalanceVo qbean) throws Exception{
		return  mapper.insert(qbean);
	}
	public int delById(String id) throws Exception{
		return mapper.delById(id);
	}
	public List<HosOutBalanceVo> list(HosOutBalanceVo qbean) throws Exception{
		return mapper.list(qbean);
	}
	public List<SysOrg> getSettleSubProvList(Out4Settle queryObject) {
		return mapper.getSettleSubProvList(queryObject);
	}
	public List<HosOutBalanceDetailVo> getExportData(HosOutBalanceVo qbean) throws Exception{
		return mapper.getExportData(qbean);
	}
	
}


