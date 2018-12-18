package gyqx.spdhdi.collector4orderMgr.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;
import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdherp.po.HosCollector;
import gyqx.spdhdi.collector4orderMgr.dao.HosCollectorDao;
import gyqx.spdhdi.collector4orderMgr.service.IHosCollectorService;

@Service
public class HosCollectorService implements IHosCollectorService {
	
	@Resource
	private HosCollectorDao  dao;
	
	public HosCollector get(String id)
	{
		return dao.get( id);	
	}	

	public HosCollector add(HosCollector hosCollector) throws Exception 
	{
		return dao.insertAndGet(hosCollector);
	}
		
	public HosCollector update(HosCollector hosCollector) throws Exception {
		return dao.updateAndGet(hosCollector);
	}
	public int updateFields(HosCollector hosCollector,String fieldNames) throws Exception
	{
		return dao.updateFields(hosCollector, fieldNames);
	}

	public void delete(HosCollector hosCollector) throws Exception {
		dao.delete(hosCollector);
	}
	
	public HosCollector getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (HosCollector) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<HosCollector> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<HosCollector>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<HosCollector> list(HosCollector bean) throws Exception
	{
		return (List<HosCollector>) dao.list(bean);
	}	
	
	public QueryResult<HosCollector> listByPage(QueryInfo<HosCollector> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

}


