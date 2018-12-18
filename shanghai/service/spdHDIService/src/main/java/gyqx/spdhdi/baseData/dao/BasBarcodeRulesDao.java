package gyqx.spdhdi.baseData.dao;


import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;

import gyqx.spdhdi.baseData.dao.mapper.IBasBarcodeRulesMapper;
import gyqx.spdhdi.po.BasBarcodeRules;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;

@Repository
public class BasBarcodeRulesDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IBasBarcodeRulesMapper mapper;

	public BasBarcodeRules get(String id)
	{
		return dao.get(BasBarcodeRules.class, id);	
	}	

	public BasBarcodeRules insertAndGet(BasBarcodeRules basBarcodeRules) throws Exception 
	{
		return dao.insertAndGet(basBarcodeRules);
	}

	public BasBarcodeRules updateAndGet(BasBarcodeRules basBarcodeRules) throws Exception {
		return dao.updateAndGet(basBarcodeRules);
	}
	public int updateFields(BasBarcodeRules basBarcodeRules,String fieldNames) throws Exception
	{
		return dao.updateFields(basBarcodeRules, fieldNames);
	}
	public int updateNotNullFields(BasBarcodeRules basBarcodeRules) throws Exception
	{
		return dao.updateNotNullFields(basBarcodeRules);
	}

	public void delete(BasBarcodeRules basBarcodeRules) throws Exception {
		basBarcodeRules.setFlag("0");
		mapper.update(basBarcodeRules);
	}
	
	public BasBarcodeRules getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (BasBarcodeRules) dao.getByFieldName(BasBarcodeRules.class, fieldName, fieldValue);
	}
	
	public List<BasBarcodeRules> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<BasBarcodeRules>) dao.queryBeanList(BasBarcodeRules.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(BasBarcodeRules.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<BasBarcodeRules> list(BasBarcodeRules qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
}


