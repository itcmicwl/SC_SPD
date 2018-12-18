package gyqx.spdhdi.collector4orderMgr.dao.mapper;

import java.util.List;

import gyqx.spdherp.po.HosCollector;
 
public interface IHosCollectorMapper {
	int update(HosCollector bean);
	int insert(HosCollector bean);
	List<HosCollector> list(HosCollector queryInfo) ;
}

