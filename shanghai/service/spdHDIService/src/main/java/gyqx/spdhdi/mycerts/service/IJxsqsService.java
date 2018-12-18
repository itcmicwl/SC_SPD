package gyqx.spdhdi.mycerts.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;
import gyqx.spdhdi.mycerts.vo.JxsqsGoodsVo;
import gyqx.spdhdi.mycerts.vo.JxsqsToHosVo;
import gyqx.spdhdi.mycerts.vo.JxsqsVo;
import gyqx.spdhdi.po.ProvJxsqs;
import gyqx.spdhdi.po.ProvJxsqsGoods;
import gyqx.spdhdi.po.ProvJxsqsHos;

public interface IJxsqsService {
	
	JxsqsToHosVo getForHospitals(String sqsId) throws Exception;
	
	JxsqsVo getJxsqsById(String id) throws Exception;

	QueryResult<JxsqsVo> getJxsqsList(QueryInfo<JxsqsVo> queryInfo) throws Exception;
	
	QueryResult<JxsqsGoodsVo> getJxsqsGoodsInfo(QueryInfo<JxsqsGoodsVo> queryInfo) throws Exception;

	JxsqsVo addJxsqs(JxsqsVo jxsqsVo) throws Exception;

	JxsqsVo updateJxsqs(JxsqsVo jxsqsVo) throws Exception;

	void deleteJxsqsToHos(ProvJxsqsHos jxsqsHos) throws Exception;

	void addJxsqsToHos(List<BasCompanyInfoVo> hosInfos, String sqsId, String provName) throws Exception;

	void deleteJxsqsCert(ProvJxsqs jxsqs) throws Exception;

	void updateJxsqsCert(ProvJxsqs jxsqs) throws Exception;

	void addJxsqsGoods(JxsqsVo jxsqsVo) throws Exception;

	void deleteJxsqsGoods(ProvJxsqsGoods jxsqsGoods) throws Exception;

	void deleteJxsqs(JxsqsVo jxsqsVo) throws Exception;
}
