package gyqx.spdhdi.mycerts.dao.mapper;

import java.util.List;

import gyqx.mdqc.mycerts.vo.CollectorProvCertVo;
import gyqx.mdqc.mycerts.vo.MyProvParamVo;

public interface IMyProvMapper {

	List<CollectorProvCertVo> getMyProvInfo(MyProvParamVo queryObject);

}
