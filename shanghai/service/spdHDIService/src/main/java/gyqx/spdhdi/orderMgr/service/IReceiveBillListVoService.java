package gyqx.spdhdi.orderMgr.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;

public interface IReceiveBillListVoService 
{
	QueryResult<ReceiveBillListVo> listByPage(QueryInfo<ReceiveBillListVo> queryInfo) throws Exception;	
}


