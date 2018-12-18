package gyqx.spdhdi.myGoods.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import gyqx.spdhdi.myGoods.dao.ProvGoodsHosBatchMgrDao;
import gyqx.spdhdi.myGoods.service.IProvGoodsHosBatchMgrService;
import gyqx.spdhdi.myGoods.vo.HosProvInfoVo;

@Service
public class ProvGoodsHosBatchMgrService implements IProvGoodsHosBatchMgrService {

	@Resource
	private ProvGoodsHosBatchMgrDao provGoodsHosBatchMgrDao;
	@Override
	public QueryResult<HosProvInfoVo> getHosProvInfoVoList(QueryInfo<HosProvInfoVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(provGoodsHosBatchMgrDao.getHosProvInfoVoList(queryInfo.getQueryObject()));
	}

}
