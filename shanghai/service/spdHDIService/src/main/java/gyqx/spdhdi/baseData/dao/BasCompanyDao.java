package gyqx.spdhdi.baseData.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.baseData.dao.mapper.IBasCompanyInfoMapper;
import gyqx.spdhdi.po.BasCompanyInfo;

@Repository
public class BasCompanyDao {
	@Resource
	private IBasCompanyInfoMapper mapper;
	public List<BasCompanyInfo> getBasCompanyInfoList(BasCompanyInfo com) throws Exception {
		return mapper.getBasCompanyInfoList(com);
	}
	
	public int insert(BasCompanyInfo com) throws Exception {
		return mapper.insert(com);
	}
}
