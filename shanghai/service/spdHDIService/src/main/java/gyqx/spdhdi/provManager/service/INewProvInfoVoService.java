package gyqx.spdhdi.provManager.service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.myInfos.vo.CompanyInfoVo;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import gyqx.spdhdi.myInfos.vo.ProvRegistInfoPushVo;

public interface INewProvInfoVoService {

	QueryResult<ProvHosInfoVo> getNewProvInfoVoList(QueryInfo<ProvHosInfoVo> queryInfo) throws Exception;

	CompanyInfoVo getProvCompanyInfo(QueryInfo<ProvRegistInfoPushVo> queryInfo) throws Exception;

}