package gyqx.spdhdi.orderMgr.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.orderMgr.dao.mapper.IReceiveBillListVoMapper;
import gyqx.spdhdi.orderMgr.dao.mapper.IReceiveBillUniqueListMapper;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillUniqueListVo;

@Repository
public class ReceiveBillListVoDao 
{	
	@Resource
	private IReceiveBillListVoMapper mapper;
	@Resource
	private IReceiveBillUniqueListMapper uniqeMapper;
	
	public List<ReceiveBillListVo> list(ReceiveBillListVo qbean) throws Exception{		

		 List<ReceiveBillListVo> rbLst = mapper.list(qbean);
	      for(ReceiveBillListVo rb : rbLst){
	    	  ReceiveBillUniqueListVo qbv= new ReceiveBillUniqueListVo();
	    	  qbv.setPid(rb.getBillId());
	    	  qbv.setRowNumber(rb.getRowNumber());
	    	  rb.setLstUniqueList(uniqeMapper.list(qbv));
	      }
		return rbLst;
	}
}


