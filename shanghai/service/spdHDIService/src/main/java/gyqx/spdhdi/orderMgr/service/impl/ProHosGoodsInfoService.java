package gyqx.spdhdi.orderMgr.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import gyqx.spdhdi.orderMgr.dao.ProHosGoodsInfoDao;
import gyqx.spdhdi.orderMgr.service.IProHosGoodsInfoService;
import gyqx.spdhdi.orderMgr.vo.ProHosGoodsInfoVo;
@Service
public class ProHosGoodsInfoService implements IProHosGoodsInfoService {
	@Resource
	ProHosGoodsInfoDao proHosGoodsInfoDao;
	@Override
	public QueryResult<ProHosGoodsInfoVo> getProHosGoodsList(QueryInfo<ProHosGoodsInfoVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(proHosGoodsInfoDao.getProHosGoodsList(queryInfo.getQueryObject()));
	
	}

}
