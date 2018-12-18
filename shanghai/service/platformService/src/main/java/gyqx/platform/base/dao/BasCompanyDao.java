package gyqx.platform.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.platform.base.dao.mapper.IBasCompanyInfoMapper;
import gyqx.platform.base.vo.BasCompanyInfoVo;
import gyqx.platform.base.vo.ProvVo;
import gyqx.spdhdi.po.BasCompanyInfo;

@Repository
public class BasCompanyDao {
	@Resource
	private IBasCompanyInfoMapper mapper;
	public List<BasCompanyInfo> getBasCompanyInfoList(BasCompanyInfo com) throws Exception {
		return mapper.getBasCompanyInfoList(com);
	}
	public List<BasCompanyInfoVo> getMyProvs(ProvVo queryObject) {
		return mapper.getMyProvs(queryObject);
	}
}
