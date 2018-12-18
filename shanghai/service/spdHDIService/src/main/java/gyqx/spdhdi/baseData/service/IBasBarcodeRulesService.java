package gyqx.spdhdi.baseData.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.po.BasBarcodeRules;

public interface IBasBarcodeRulesService 
{
	BasBarcodeRules get(String id) throws Exception ;
	BasBarcodeRules add(BasBarcodeRules basBarcodeRules) throws Exception ;
	BasBarcodeRules update(BasBarcodeRules basBarcodeRules) throws Exception;
	void delete(BasBarcodeRules basBarcodeRules) throws Exception;
	
	List<BasBarcodeRules> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<BasBarcodeRules> list(BasBarcodeRules bean) throws Exception;
	
	QueryResult<BasBarcodeRules> listByPage(QueryInfo<BasBarcodeRules> queryInfo) throws Exception;	

}


