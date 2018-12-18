package gyqx.platform.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.util.Assert;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import common.cache.CacheWrapper;
import common.db.IBean;
import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.EntityUtils;
import common.utils.UtilsContext;
import gyqx.platform.sys.service.IEntityUtilsService;

@Service
public class EntityUtilsService implements IEntityUtilsService
{
	@Resource
	private SimpleDao simpleDao;
	
	@Resource
	private UtilsContext utilsContext;
	
	private CacheWrapper entityCache;
	
	@PostConstruct
	public void init()
	{		
		entityCache = utilsContext.getCacheUtils().getEntityCache();
		Assert.notNull(entityCache, "entityCache == null!");
	}

	@Override
	public IBean get(String name, String id) throws Exception {
		Class<? extends IBean> beanClass = simpleDao.getBeanClassByName(name);
		
		IBean bean=null;
		String key = beanClass.getSimpleName() + ":id:" + id;
		bean = entityCache.get(key);
		if(bean != null)
			return bean;
		
		bean = simpleDao.get(beanClass, id);
		
		if(bean != null)
			entityCache.put(key, bean);
		
		return bean;
	}
	
	@Override	
	public IBean getByField(String name, String fieldName, Object fieldValue) throws Exception {
		
		IBean bean=null;
		String key = name + ":" + fieldName + ":" + fieldValue;
		String id = entityCache.get(key);
		if(id != null){
			bean = entityCache.get(name + ":id:" + id);
		}
		if(bean != null)
			return bean;
		
		Class<? extends IBean> beanClass = simpleDao.getBeanClassByName(name);	
		
		bean = simpleDao.getByFieldName(beanClass, fieldName, fieldValue);
		
		key = name + ":" + fieldName + ":" + fieldValue;
		
		entityCache.put(key,bean.getId());		
		entityCache.put(name + ":id:" + id, bean);
		return bean;
	}
	
	@Override
	public IBean insertAndGet(IBean bean) throws Exception {
		
		String key = bean.getClass().getSimpleName() + ":id:" + bean.getId();
		try{
			bean =  simpleDao.insertAndGet(bean);
			entityCache.put(key, bean);		
		}catch(Exception ex)
		{
			entityCache.evict(key);
			throw ex;
		}		
		
		return bean;
	}

	@Override
	public IBean insertAndGet(String name,Object map) throws Exception {
		Class<? extends IBean> beanClass = simpleDao.getBeanClassByName(name);
		
		IBean bean = beanClass.newInstance();
		Tx.transform(map).to(bean);
		
		String key = beanClass.getSimpleName() + ":id:" + bean.getId();
		try{
			bean =  simpleDao.insertAndGet(bean);
			entityCache.put(key, bean);	
		}catch(Exception ex)
		{
			entityCache.evict(key);
			throw ex;
		}
		return bean;
	}
	
	@Override
	public IBean updateAndGet(IBean bean) throws Exception {
		
		String key = bean.getClass().getSimpleName() + ":id:" + bean.getId();
		try{
			bean =  simpleDao.updateAndGet(bean);
			entityCache.put(key, bean);	
		}catch(Exception ex)
		{
			entityCache.evict(key);
			throw ex;
		}
		return bean;		
	}

	@Override
	public IBean updateAndGet(String name,Object map) throws Exception {
		Class<? extends IBean> beanClass = simpleDao.getBeanClassByName(name);
		
		IBean bean = beanClass.newInstance();
		Tx.transform(map).to(bean);
		
		String key = bean.getClass().getSimpleName() + ":id:" + bean.getId();
		try{
			bean =  simpleDao.updateAndGet(bean);
			entityCache.put(key, bean);	
		}catch(Exception ex)
		{
			entityCache.evict(key);
			throw ex;
		}
		return bean;
	}

	@Override
	public List<? extends IBean> queryByFields(String name,String predicate,String orderBy,Object queryObject) throws Exception {
		
		Class<? extends IBean> beanClass = simpleDao.getBeanClassByName(name);

		QueryInfo queryInfo = new QueryInfo();
		queryInfo.setOrderBy(orderBy);
		queryInfo.setPredicate(predicate);
		queryInfo.setQueryObject(queryObject);
		
		QueryResult<? extends IBean> result = simpleDao.queryForList(queryInfo, beanClass);
		
		for(IBean bean: result.getData())
		{
			String key = beanClass.getSimpleName() + ":id:" + bean.getId();
			entityCache.put(key, bean);
		}
		
		return result.getData();
	}

	@Override
	public void delete(IBean bean) throws Exception {
		try{
			simpleDao.delete(bean);	
		}finally{		
			String key = bean.getClass().getSimpleName() + ":id:" + bean.getId();
			entityCache.evict(key);
		}		
	}

	@Override
	public void delete(String name, Object map) throws Exception {
		Class<? extends IBean> beanClass = simpleDao.getBeanClassByName(name);
		
		IBean bean = beanClass.newInstance();
		Tx.transform(map).to(bean);
		try{
			simpleDao.delete(bean);	
		}finally{		
			String key = bean.getClass().getSimpleName() + ":id:" + bean.getId();
			entityCache.evict(key);
		}			
	}
}
