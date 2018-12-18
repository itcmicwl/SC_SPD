package gyqx.spdhdi.orderMgr.dao;


import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.orderMgr.dao.mapper.IDistrBillListMapper;
import gyqx.spdhdi.orderMgr.vo.DistrBillListVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class DistrBillListDao 
{
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IDistrBillListMapper mapper;

	public DistrBillListVo get(String id)
	{
		return mapper.get(id);	
	}	

	public int insertBatch(List<DistrBillListVo> distrBillList) throws Exception 
	{
		return mapper.insertBatch(distrBillList);
	}
	public int update(DistrBillListVo distrBillList) throws Exception 
	{
		return mapper.update(distrBillList);
	}
	public int updateByBatch(List<DistrBillListVo> bean) throws Exception
	{
		return mapper.updateByBatch(bean);
	}
	public int delByBillId(String billId) throws Exception {
		return mapper.delByBillId(billId);
	}
	public int delByPid(String pid) throws Exception {
		return mapper.delByPid(pid);
	}
	
	public List<DistrBillListVo> list(DistrBillListVo qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
}


