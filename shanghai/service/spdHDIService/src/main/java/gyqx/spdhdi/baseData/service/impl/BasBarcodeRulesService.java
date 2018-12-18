package gyqx.spdhdi.baseData.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;

import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.po.BasBarcodeRules;
import gyqx.spdhdi.baseData.dao.BasBarcodeRulesDao;
import gyqx.spdhdi.baseData.service.IBasBarcodeRulesService;

@Service
public class BasBarcodeRulesService implements IBasBarcodeRulesService {
	
	@Resource
	private BasBarcodeRulesDao  dao;
	
	@Resource
	private SysAtomUtil atomUtil;
	
	public BasBarcodeRules get(String id)
	{
		return dao.get( id);	
	}	

	public BasBarcodeRules add(BasBarcodeRules basBarcodeRules) throws Exception 
	{
		String id = atomUtil.newValue("basBarcodeRules");
		basBarcodeRules.setId(id);
		return dao.insertAndGet(basBarcodeRules);
	}
		
	public BasBarcodeRules update(BasBarcodeRules basBarcodeRules) throws Exception {
		return dao.updateAndGet(basBarcodeRules);
	}
	public int updateFields(BasBarcodeRules basBarcodeRules,String fieldNames) throws Exception
	{
		return dao.updateFields(basBarcodeRules, fieldNames);
	}

	public void delete(BasBarcodeRules basBarcodeRules) throws Exception {
		dao.delete(basBarcodeRules);
	}
	
	public BasBarcodeRules getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (BasBarcodeRules) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<BasBarcodeRules> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<BasBarcodeRules>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<BasBarcodeRules> list(BasBarcodeRules bean) throws Exception
	{
		return (List<BasBarcodeRules>) dao.list(bean);
	}	
	
	public QueryResult<BasBarcodeRules> listByPage(QueryInfo<BasBarcodeRules> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

}


