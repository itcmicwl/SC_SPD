package gyqx.platform.sys.service;

import java.util.List;
import java.util.Map;

import common.db.IBean;

public interface IEntityUtilsService 
{
	
	IBean get(String name,String id) throws Exception;
	IBean getByField(String name,String fieldName, Object fieldValue) throws Exception;
	
	IBean insertAndGet(IBean bean) throws Exception;
	IBean updateAndGet(IBean bean) throws Exception;
	
	IBean insertAndGet(String name,Object bean) throws Exception;
	IBean updateAndGet(String name,Object bean) throws Exception;
	
	void delete(IBean bean) throws Exception;
	void delete(String name,Object bean) throws Exception;
	
	List<? extends IBean> queryByFields(String name,String predicate,String orderBy,Object queryObject) throws Exception;
	

}
