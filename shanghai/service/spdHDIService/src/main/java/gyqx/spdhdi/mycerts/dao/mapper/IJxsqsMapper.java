package gyqx.spdhdi.mycerts.dao.mapper;

import java.util.List;

import gyqx.spdhdi.mycerts.vo.JxsqsGoodsVo;
import gyqx.spdhdi.mycerts.vo.JxsqsToHosVo;
import gyqx.spdhdi.mycerts.vo.JxsqsVo;

public interface IJxsqsMapper {
	
	JxsqsToHosVo getForHospitals(String sqsId);

	JxsqsVo getJxsqsById(String id);

	List<JxsqsVo> getJxsqsList(JxsqsVo queryObject);

	List<JxsqsGoodsVo> getJxsqsGoodsInfo(JxsqsGoodsVo queryObject);

}
