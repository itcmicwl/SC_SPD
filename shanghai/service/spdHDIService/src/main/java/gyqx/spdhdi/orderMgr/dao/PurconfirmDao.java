package gyqx.spdhdi.orderMgr.dao;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.orderMgr.dao.mapper.IPurconfirmMapper;
import gyqx.spdhdi.orderMgr.vo.PurconfirmVo;

@Repository
public class PurconfirmDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IPurconfirmMapper mapper;
	public PurconfirmVo get(String id) throws Exception 
	{
		return mapper.get(id);
	}
	public PurconfirmVo getByBillid(String billid){
		return mapper.getByBillid(billid);
	}
	public int insert(PurconfirmVo purconfirmVo) throws Exception 
	{	
		purconfirmVo.setFillDate(new Date());
		purconfirmVo.setLastUpdateDatetime(new Date());
		return mapper.insert(purconfirmVo);
	}
	public int update(PurconfirmVo purconfirmVo) throws Exception 
	{
		return mapper.update(purconfirmVo);
	}
	public int updateByBatch(List<PurconfirmVo> lst) throws Exception{
		return mapper.updateByBatch(lst);
	}
	public int delete(PurconfirmVo purconfirmVo) throws Exception {
		return mapper.delete(purconfirmVo);
	}
	public List<PurconfirmVo> list(PurconfirmVo purconfirmVo) throws Exception{		
		
		return mapper.list(purconfirmVo);
	}
	public List<PurconfirmVo> secList(PurconfirmVo purconfirmVo) throws Exception{

		return mapper.secList(purconfirmVo);
	}
	public List<PurconfirmVo> getAllBillsNotSend() {
		return mapper.getAllBillsNotSend();
	}
}


