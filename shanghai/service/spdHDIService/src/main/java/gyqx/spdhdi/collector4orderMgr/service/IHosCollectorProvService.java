package gyqx.spdhdi.collector4orderMgr.service;


import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.collector4orderMgr.vo.HosCollectorProvVo;
import gyqx.spdherp.po.HosCollectorProv;

public interface IHosCollectorProvService 
{
	HosCollectorProv get(String id) throws Exception ;
	HosCollectorProv add(HosCollectorProv hosCollectorProv) throws Exception ;
	HosCollectorProv update(HosCollectorProv hosCollectorProv) throws Exception;
	
	List<HosCollectorProv> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<HosCollectorProv> list(HosCollectorProv bean) throws Exception;
	
	QueryResult<HosCollectorProv> listByPage(QueryInfo<HosCollectorProv> queryInfo) throws Exception;	

	QueryResult<HosCollectorProvVo> listVoByPage(QueryInfo<HosCollectorProvVo> queryInfo) throws Exception;
	List<HosCollectorProvVo> listVo(HosCollectorProvVo bean) throws Exception;
}


