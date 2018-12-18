package gyqx.spdhdi.orderMgr.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.orderMgr.dao.mapper.IReceiveBillUniqueListMapper;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillUniqueListVo;
import gyqx.spdhdi.po.ReceiveBillUniqueList;

@Repository
public class ReceiveBillUniqueListDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IReceiveBillUniqueListMapper mapper;

	public ReceiveBillUniqueListVo get(String id) {
		return mapper.get(id);
	}

	public int insertBatch(List<ReceiveBillUniqueListVo> distrBillUniqueList) throws Exception {
		return mapper.insertBatch(distrBillUniqueList);
	}

	public int update(ReceiveBillUniqueListVo distrBillUniqueList) throws Exception {
		return mapper.update(distrBillUniqueList);
	}

	public int delById(String id) throws Exception {
		return mapper.delById(id);
	}

	public int delByPid(String pid) throws Exception {
		return mapper.delById(pid);
	}

	public List<ReceiveBillUniqueListVo> list(ReceiveBillUniqueListVo qbean) throws Exception {

		return mapper.list(qbean);
	}
	public boolean existUniqueCode(String uniqueCode,String id) throws Exception {
		return mapper.existUniqueCode(uniqueCode,id) > 0;
	}
}


