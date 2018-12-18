package gyqx.spdhdi.mycerts.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.mdqc.mycerts.vo.CollectorProvCertVo;
import gyqx.mdqc.mycerts.vo.MyProvParamVo;
import gyqx.spdhdi.mycerts.dao.mapper.IMyProvMapper;

@Repository
public class MyProvDao {
	@Resource
	private IMyProvMapper mapper;

	public List<CollectorProvCertVo> getMyProvInfo(MyProvParamVo queryObject) {
		return mapper.getMyProvInfo(queryObject);
	}
}
