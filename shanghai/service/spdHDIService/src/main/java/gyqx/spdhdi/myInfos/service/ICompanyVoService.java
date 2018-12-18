package gyqx.spdhdi.myInfos.service;

import java.util.List;

import gyqx.spdhdi.myInfos.vo.CollectorProvCertificateVo;
import gyqx.spdhdi.myInfos.vo.CompanyCertificateVo;
import gyqx.spdhdi.myInfos.vo.CompanyInfoVo;
import gyqx.spdhdi.po.BasCompanyCertificate;
import gyqx.spdhdi.po.BasCompanyCertificateImage;
import gyqx.spdhdi.po.CollectorProvCertificate;
import gyqx.spdhdi.po.ProvCertificateImage;

public interface ICompanyVoService {

	public CompanyInfoVo getCompanyInfo(String corpId) throws Exception;

	public List<CompanyCertificateVo> getCompanyCertificateVo(String companyId) throws Exception;

	public CompanyInfoVo updateCompanyInfoVo(CompanyInfoVo com) throws Exception;

	public CompanyInfoVo insertCompanyCertificate(CompanyInfoVo companyInfoVo) throws Exception;

	public CompanyInfoVo updateCompanyCertificate(CompanyInfoVo companyInfoVo) throws Exception;
	
	public int insertCompanyCertificateImage(BasCompanyCertificateImage certificateImageVo) throws Exception;

	public int updateCompanyCertificateImage(BasCompanyCertificateImage certificateImageVo) throws Exception;

	public int updateLogo(CompanyInfoVo company) throws Exception;

	public int deleteImage(String id) throws Exception;

	public void updateProvCerts(BasCompanyCertificate companyCert) throws Exception;

	public void addProvCerts(CompanyCertificateVo companyCert) throws Exception;

	public int deleteProvCerts(CompanyCertificateVo companyCert) throws Exception;

	public void addImage(BasCompanyCertificateImage image) throws Exception;

	public void addMyProvCerts(CollectorProvCertificateVo companyCert) throws Exception;

	public void updateMyProvCerts(CollectorProvCertificate companyCert) throws Exception;

	public int deleteMyprovImage(String id) throws Exception;

	public void addMyProvImage(ProvCertificateImage image) throws Exception;

	public int deleteMyProvCerts(CollectorProvCertificate companyCert) throws Exception;

}
