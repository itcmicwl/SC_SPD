package gyqx.platform.base.service;

import java.util.List;
import java.util.Map;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.base.vo.BasCompanyInfoVo;
import gyqx.platform.base.vo.CollectorProvCertificateVo;
import gyqx.platform.base.vo.ProvVo;
import gyqx.spdhdi.po.BasCompanyInfo;

public interface IBasCompanyService {
	
	public BasCompanyInfo addBasCompany(BasCompanyInfo com) throws Exception;

	public BasCompanyInfo updateBasCompany(BasCompanyInfo com) throws Exception;

	public BasCompanyInfo getBasCompany(BasCompanyInfo com) throws Exception;

	public QueryResult<BasCompanyInfo> listBasCompany(QueryInfo<BasCompanyInfo> queryInfo) throws Exception;

	public List<BasCompanyInfo> getListBasCompany(BasCompanyInfo basCompanyInfo) throws Exception;

	public void deleteBasCompany(BasCompanyInfo com) throws Exception;

	public void saveCompanyCertsInfo(Map map) throws Exception;

	public QueryResult<BasCompanyInfoVo> getMyProvs(QueryInfo<ProvVo> queryInfo) throws Exception;

	public BasCompanyInfo insertMyProv(BasCompanyInfo company, String hosId) throws Exception;

	public void delMyProv(BasCompanyInfoVo companyVo) throws Exception;

	public List<CollectorProvCertificateVo> getMyProvCerts(String hcpId,String id) throws Exception;

}
