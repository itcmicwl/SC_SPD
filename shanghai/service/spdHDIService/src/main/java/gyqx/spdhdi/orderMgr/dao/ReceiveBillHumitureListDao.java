package gyqx.spdhdi.orderMgr.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.orderMgr.dao.mapper.IReceiveBillHumitureListMapper;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillHumitureListVo;

@Repository
public class ReceiveBillHumitureListDao 
{
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IReceiveBillHumitureListMapper mapper;

	public ReceiveBillHumitureListVo get(String id)
	{
		return mapper.get(id);	
	}	

	public int insertBatch(List<ReceiveBillHumitureListVo> distrBillHumitureList) throws Exception 
	{
		return mapper.insertBatch(distrBillHumitureList);
	}

	public int update(ReceiveBillHumitureListVo distrBillHumitureList) throws Exception {
		return mapper.update(distrBillHumitureList);
	}
	
	public void delById(String id) throws Exception {
		mapper.delById(id);
	}
	public void delByPid(String pid) throws Exception {
		mapper.delByPid(pid);
	}
	
	public List<ReceiveBillHumitureListVo> list(ReceiveBillHumitureListVo qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
}


