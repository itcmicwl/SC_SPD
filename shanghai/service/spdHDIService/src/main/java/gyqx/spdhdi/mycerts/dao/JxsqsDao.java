package gyqx.spdhdi.mycerts.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.mycerts.dao.mapper.IJxsqsMapper;
import gyqx.spdhdi.mycerts.vo.JxsqsGoodsVo;
import gyqx.spdhdi.mycerts.vo.JxsqsToHosVo;
import gyqx.spdhdi.mycerts.vo.JxsqsVo;

@Repository
public class JxsqsDao {
	@Resource
	private IJxsqsMapper mapper;
	
	public JxsqsToHosVo getForHospitals(String sqsId) {
		return mapper.getForHospitals(sqsId);
	}
	
	public JxsqsVo getJxsqsById(String id) {
		return mapper.getJxsqsById(id);
	}

	public List<JxsqsVo> getJxsqsList(JxsqsVo queryObject) {
		return mapper.getJxsqsList(queryObject);
	}

	public List<JxsqsGoodsVo> getJxsqsGoodsInfo(JxsqsGoodsVo queryObject) {
		return mapper.getJxsqsGoodsInfo(queryObject);
	}
}
