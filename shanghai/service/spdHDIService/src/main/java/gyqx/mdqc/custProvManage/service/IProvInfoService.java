package gyqx.mdqc.custProvManage.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;

public interface IProvInfoService {
	
	QueryResult<BasCompanyInfoVo> getProvInfoList(QueryInfo<BasCompanyInfoVo> queryInfo) throws Exception;

	List<BasCompanyInfoVo> getProvInfo(BasCompanyInfoVo queryInfo) throws Exception;
	
}
