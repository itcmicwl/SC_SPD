package gyqx.spdhdi.orderMgr.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;
import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;

import gyqx.spdhdi.orderMgr.dao.ReceiveBillListVoDao;
import gyqx.spdhdi.orderMgr.service.IReceiveBillListVoService;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;

@Service
public class ReceiveBillListVoService implements IReceiveBillListVoService {
	
	@Resource
	private ReceiveBillListVoDao  dao;	
	
	public QueryResult<ReceiveBillListVo> listByPage(QueryInfo<ReceiveBillListVo> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

}


