package gyqx.spdhdi.mycerts.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;
import gyqx.mdqc.mycerts.vo.CollectorProvCertVo;
import gyqx.mdqc.mycerts.vo.MyProvParamVo;
import gyqx.spdhdi.myInfos.vo.CollectorProvCertificateVo;
import gyqx.spdhdi.po.CollectorProvCertificate;
import gyqx.spdhdi.po.ProvCertificateImage;

public interface IMyProvService {

	QueryResult<CollectorProvCertVo> getMyProvInfo(QueryInfo<MyProvParamVo> queryInfo) throws Exception;

	void addMyProvInfo(List<BasCompanyInfoVo> provInfos) throws Exception;

	void updateMyProvFlag(CollectorProvCertVo vo) throws Exception;

	CollectorProvCertificateVo addMyProvCerts(CollectorProvCertificateVo certVo) throws Exception;

	void addImage(ProvCertificateImage image) throws Exception;

	int deleteProvCerts(CollectorProvCertificateVo companyCert) throws Exception;

	int deleteImage(String id) throws Exception;

	void updateProvCerts(CollectorProvCertificate cert) throws Exception;

}
