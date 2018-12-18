package gyqx.spdhdi.baseData.dao.mapper;

import java.util.List;

import gyqx.spdhdi.po.BasCompanyInfo;

public interface IBasCompanyInfoMapper {
	List<BasCompanyInfo> getBasCompanyInfoList(BasCompanyInfo com);
	int insert(BasCompanyInfo com);

}
