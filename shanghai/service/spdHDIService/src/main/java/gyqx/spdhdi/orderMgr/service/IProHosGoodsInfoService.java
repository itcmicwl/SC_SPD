package gyqx.spdhdi.orderMgr.service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.orderMgr.vo.ProHosGoodsInfoVo;

public interface IProHosGoodsInfoService {
	QueryResult<ProHosGoodsInfoVo> getProHosGoodsList(QueryInfo<ProHosGoodsInfoVo> queryInfo)  throws Exception;
}
