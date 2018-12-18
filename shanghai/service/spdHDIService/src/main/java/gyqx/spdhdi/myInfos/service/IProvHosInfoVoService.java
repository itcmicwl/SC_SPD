package gyqx.spdhdi.myInfos.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.myInfos.vo.HosInfoVo;
import gyqx.spdhdi.myInfos.vo.HosProvVo;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import gyqx.spdhdi.po.BasCompanyInfo;

public interface IProvHosInfoVoService {

	QueryResult<ProvHosInfoVo> getProvHosInfoVoList(QueryInfo<ProvHosInfoVo> queryInfo) throws Exception;
	
	QueryResult<HosInfoVo> getHosInfoVoList(QueryInfo<HosInfoVo> queryInfo) throws Exception;

	int insertProvHosInfo(ProvHosInfoVo provHosInfoVo) throws Exception;
	
	void insertProvHosInfoBatch(String hosId,List<BasCompanyInfo> companyInfoVos);

	int setStatus(ProvHosInfoVo provHosInfoVo) throws Exception;
	
	int setHosView(ProvHosInfoVo provHosInfoVo) throws Exception;

	int setProvHosInfo(ProvHosInfoVo provHosInfoVo) throws Exception;

	int deleteByHosId(String id) throws Exception;
	
	void deleteHosProvBatch(List<ProvHosInfoVo> provHosInfoVos) throws Exception;

	boolean existProvHosInfo(ProvHosInfoVo provHosInfoVo) throws Exception;

	ProvHosInfoVo getProvHosInfoByid(String id) throws Exception;

	QueryResult<HosProvVo> getHosProvVoList(QueryInfo<HosProvVo> queryInfo) throws Exception;

	QueryResult<ProvHosInfoVo> getHosProvCompanyInfoVoList(QueryInfo<ProvHosInfoVo> queryInfo) throws Exception;

	QueryResult<BasCompanyInfo> getNotRelatedCompanysByHosId(QueryInfo<HosInfoVo> queryInfo);
}