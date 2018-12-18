package gyqx.spdhdi.collector4orderMgr.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdherp.po.HosCollector;

public interface IHosCollectorService 
{
	HosCollector get(String id) throws Exception ;
	HosCollector add(HosCollector hosCollector) throws Exception ;
	HosCollector update(HosCollector hosCollector) throws Exception;
	
	List<HosCollector> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<HosCollector> list(HosCollector bean) throws Exception;
	
	QueryResult<HosCollector> listByPage(QueryInfo<HosCollector> queryInfo) throws Exception;	

}


