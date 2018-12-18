package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;
import common.db.query.QueryInfo;

import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;
 
public interface IReceiveBillListVoMapper {

	List<ReceiveBillListVo> list(ReceiveBillListVo queryInfo) ;
}

