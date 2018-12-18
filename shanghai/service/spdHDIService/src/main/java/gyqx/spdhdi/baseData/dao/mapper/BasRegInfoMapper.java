package gyqx.spdhdi.baseData.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.baseData.vo.BasRegInfoVo;

public interface BasRegInfoMapper {

	 List<BasMfrsInfoVo> getMfrsInfoLikeName(@Param("mfrsName") String mfrsName, @Param("page") int pageNum, @Param("rows") int pageSize);
	
    List<BasRegInfoVo> getCerts(@Param("mfrsId") String mfrsId, @Param("page") int pageNum, @Param("rows") int pageSize);

    List<BasMfrsInfoVo> getCompanyInfoLikeName(@Param("name") String name, @Param("page") int pageNum, @Param("rows") int pageSize);

	List<BasRegInfoVo> getBasRegCerts(BasRegInfoVo queryObject);
}