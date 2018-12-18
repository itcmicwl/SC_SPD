package gyqx.spdhdi.mycerts.dao.mapper;

import java.util.List;

import gyqx.mdqc.mycerts.vo.MyMfrsVo;
import gyqx.mdqc.mycerts.vo.MyProvParamVo;

public interface IMyMfrsMapper {

	List<MyMfrsVo> getMyMfrsInfo(MyProvParamVo queryObject);

}
