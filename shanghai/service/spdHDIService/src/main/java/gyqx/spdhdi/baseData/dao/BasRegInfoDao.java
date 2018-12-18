package gyqx.spdhdi.baseData.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.baseData.dao.mapper.BasRegInfoMapper;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.baseData.vo.BasRegInfoVo;

/**
 * Created by liangwu on 17-6-6.
 */
@Repository
public class BasRegInfoDao {
    @Resource
    private BasRegInfoMapper mapper;

    public List<BasRegInfoVo> getCerts(String mfrsId, int pageNum, int pageSize) {
        return mapper.getCerts(mfrsId, pageNum, pageSize);
    }
    
    public List<BasMfrsInfoVo> getMfrsInfoLikeName(String mfrsName, int pageNum, int pageSize) {
        mfrsName = mfrsName.replaceAll("%", "\\\\%");
        return mapper.getMfrsInfoLikeName(mfrsName, pageNum, pageSize);
    }

    public List<BasMfrsInfoVo> getCompanyInfoLikeName(String name, int pageNum, int pageSize) {
        name = name.replaceAll("%", "\\\\%");
        return mapper.getCompanyInfoLikeName(name, pageNum, pageSize);
    }

	public List<BasRegInfoVo> getCerts(BasRegInfoVo queryObject) {
		return mapper.getBasRegCerts(queryObject);
	}
}
