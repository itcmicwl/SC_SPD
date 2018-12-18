package gyqx.platform.base.dao.mapper;

import java.util.List;

import gyqx.platform.base.vo.BasCompanyInfoVo;
import gyqx.platform.base.vo.CollectorProvCertificateVo;
import gyqx.platform.base.vo.ProvVo;
import gyqx.spdhdi.po.BasCompanyInfo;
import gyqx.spdhdi.po.ProvCertificateImage;

public interface BasCompanyInfoMapper {
	
	List<BasCompanyInfo> getBasCompanyInfoList(BasCompanyInfo com);

	List<BasCompanyInfoVo> getMyProvs(ProvVo queryObject);
	
	List<CollectorProvCertificateVo> getProvCertificate(String pid);
	
	List<ProvCertificateImage> getProvCertificateImages(String pid);

}
