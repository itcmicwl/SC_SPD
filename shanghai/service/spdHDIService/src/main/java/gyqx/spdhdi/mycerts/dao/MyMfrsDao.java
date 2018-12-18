package gyqx.spdhdi.mycerts.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.mdqc.mycerts.vo.MyMfrsVo;
import gyqx.mdqc.mycerts.vo.MyProvParamVo;
import gyqx.spdhdi.mycerts.dao.mapper.IMyMfrsMapper;

@Repository
public class MyMfrsDao {
	@Resource
	private IMyMfrsMapper mapper;

	public List<MyMfrsVo> getMyMfrsInfo(MyProvParamVo queryObject) {
		return mapper.getMyMfrsInfo(queryObject);
	}
}
