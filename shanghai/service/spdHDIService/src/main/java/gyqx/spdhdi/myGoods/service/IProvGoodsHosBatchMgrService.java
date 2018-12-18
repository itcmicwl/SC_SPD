package gyqx.spdhdi.myGoods.service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.myGoods.vo.HosProvInfoVo;

public interface IProvGoodsHosBatchMgrService {

	/**
	 * 查询医院供应商列表
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	QueryResult<HosProvInfoVo> getHosProvInfoVoList(QueryInfo<HosProvInfoVo> queryInfo) throws Exception;;

}
