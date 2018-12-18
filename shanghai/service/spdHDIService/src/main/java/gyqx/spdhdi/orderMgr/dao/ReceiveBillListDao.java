package gyqx.spdhdi.orderMgr.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.utils.SysConfigUtil;
import gyqx.spdhdi.orderMgr.dao.mapper.IReceiveBillListMapper;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;
import gyqx.spdhdi.po.ReceiveBillList;

@Repository
public class ReceiveBillListDao 
{
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private IReceiveBillListMapper mapper;

	public int insertBatch(List<ReceiveBillListVo> lst) throws Exception 
	{
		return mapper.insertBatch(lst);
	}
	public int update(ReceiveBillListVo receiveBillList) throws Exception {
		return mapper.update(receiveBillList);
	}
	public int updateByBatch(List<ReceiveBillListVo> lst) throws Exception
	{
		return mapper.updateByBatch(lst);
	}
	public int deleteById(String id) throws Exception
	{
		return mapper.deleteById(id);
	}
	public int deleteByBillId(String billId) throws Exception
	{
		return mapper.deleteByBillId(billId);
	}
	public List<ReceiveBillListVo> list(ReceiveBillListVo qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
}


