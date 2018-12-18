package gyqx.spdhdi.collector4orderMgr.dao.mapper;

import java.util.List;

import gyqx.spdhdi.collector4orderMgr.vo.HosCollectorProvVo;
import gyqx.spdherp.po.HosCollectorProv;
 
public interface IHosCollectorProvMapper {
	int update(HosCollectorProv bean);
	int insert(HosCollectorProv bean);
	List<HosCollectorProv> list(HosCollectorProv queryInfo) ;
	List<HosCollectorProvVo>listVo(HosCollectorProvVo queryInfo);
}

