package gyqx.spdhdi.mycerts.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;
import gyqx.mdqc.mycerts.vo.MyMfrsVo;
import gyqx.mdqc.mycerts.vo.MyProvParamVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import gyqx.spdhdi.po.ProvMfrsCertificateImage;

public interface IMyMfrsService {
	
	QueryResult<MyMfrsVo> getMyMfrsInfo(QueryInfo<MyProvParamVo> queryInfo) throws Exception;

	void addMyMfrsInfo(List<BasCompanyInfoVo> mfrsInfos) throws Exception;
	
	void updateMyMfrsFlag(MyMfrsVo vo) throws Exception;

	ProvMfrsCertificateVo addMyMfrsCerts(ProvMfrsCertificateVo certVo) throws Exception;

	void addImage(ProvMfrsCertificateImage image) throws Exception;

	void updateMfrsCerts(ProvMfrsCertificateVo cert) throws Exception;

	int deleteImage(String id) throws Exception;

	int deleteMfrsCerts(ProvMfrsCertificateVo companyCert) throws Exception;

}
