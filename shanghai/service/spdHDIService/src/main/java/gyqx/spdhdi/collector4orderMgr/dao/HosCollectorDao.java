package gyqx.spdhdi.collector4orderMgr.dao;


import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;

import gyqx.spdhdi.collector4orderMgr.dao.mapper.IHosCollectorMapper;
import gyqx.spdherp.po.HosCollector;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;

@Repository
public class HosCollectorDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IHosCollectorMapper mapper;

	public HosCollector get(String id)
	{
		return dao.get(HosCollector.class, id);	
	}	

	public HosCollector insertAndGet(HosCollector hosCollector) throws Exception 
	{
		return dao.insertAndGet(hosCollector);
	}

	public HosCollector updateAndGet(HosCollector hosCollector) throws Exception {
		return dao.updateAndGet(hosCollector);
	}
	public int updateFields(HosCollector hosCollector,String fieldNames) throws Exception
	{
		return dao.updateFields(hosCollector, fieldNames);
	}
	public int updateNotNullFields(HosCollector hosCollector) throws Exception
	{
		return dao.updateNotNullFields(hosCollector);
	}

	public void delete(HosCollector hosCollector) throws Exception {
		dao.delete(hosCollector);
	}
	
	public HosCollector getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (HosCollector) dao.getByFieldName(HosCollector.class, fieldName, fieldValue);
	}
	
	public List<HosCollector> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<HosCollector>) dao.queryBeanList(HosCollector.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(HosCollector.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<HosCollector> list(HosCollector qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
}


