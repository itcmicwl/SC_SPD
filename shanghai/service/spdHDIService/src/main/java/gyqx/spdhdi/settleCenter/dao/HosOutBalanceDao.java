package gyqx.spdhdi.settleCenter.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.settleCenter.dao.mapper.IHosOutBalanceMapper;
import gyqx.spdhdi.settleCenter.vo.HosOutBalanceVo;

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
	public List<HosOutBalanceVo> getAllBillSubmit() {
		return mapper.getAllBillSubmit();
	}
}


