package gyqx.spdhdi.orderMgr.dao;


import gyqx.spdhdi.orderMgr.dao.mapper.IReceiveBillMapper;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillVo;
import gyqx.spdhdi.po.ReceiveBill;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;

@Repository
public class ReceiveBillDao 
{
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IReceiveBillMapper mapper;

	public ReceiveBillVo getById(String id)
	{
		return mapper.getById(id);	
	}
	public ReceiveBillVo getByBillId(String billId)
	{
		return mapper.getByBillId(billId);	
	}
	public int insert(ReceiveBillVo receiveBill) throws Exception 
	{
		receiveBill.setFillDate(new Date());
		return mapper.insert(receiveBill);
	}
	public int update(ReceiveBillVo receiveBill) throws Exception 
	{
		receiveBill.setFillDate(new Date());
		return mapper.update(receiveBill);
	}
	public List<ReceiveBillVo> list(ReceiveBillVo qbean) throws Exception{		
		return mapper.getReceiveBillList(qbean);
	}
}


