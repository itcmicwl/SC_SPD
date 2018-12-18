package gyqx.spdhdi.collector4orderMgr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import gyqx.spdhdi.collector4orderMgr.dao.HosCollectorProvDao;
import gyqx.spdhdi.collector4orderMgr.service.IHosCollectorProvService;
import gyqx.spdhdi.collector4orderMgr.vo.HosCollectorProvVo;
import gyqx.spdherp.po.HosCollectorProv;

@Service
public class HosCollectorProvService implements IHosCollectorProvService {
	
	@Resource
	private HosCollectorProvDao  dao;
	
	public HosCollectorProv get(String id)
	{
		return dao.get( id);	
	}	

	public HosCollectorProv add(HosCollectorProv hosCollectorProv) throws Exception 
	{
		return dao.insertAndGet(hosCollectorProv);
	}
		
	public HosCollectorProv update(HosCollectorProv hosCollectorProv) throws Exception {
		return dao.updateAndGet(hosCollectorProv);
	}
	public int updateFields(HosCollectorProv hosCollectorProv,String fieldNames) throws Exception
	{
		return dao.updateFields(hosCollectorProv, fieldNames);
	}

	public void delete(HosCollectorProv hosCollectorProv) throws Exception {
		dao.delete(hosCollectorProv);
	}
	
	public HosCollectorProv getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (HosCollectorProv) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<HosCollectorProv> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<HosCollectorProv>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<HosCollectorProv> list(HosCollectorProv bean) throws Exception
	{
		return (List<HosCollectorProv>) dao.list(bean);
	}
	public List<HosCollectorProvVo> listVo(HosCollectorProvVo bean) throws Exception
	{
		return dao.listVo(bean);
	}
	public QueryResult<HosCollectorProv> listByPage(QueryInfo<HosCollectorProv> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}
	
	public QueryResult<HosCollectorProvVo> listVoByPage(QueryInfo<HosCollectorProvVo> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.listVo(queryInfo.getQueryObject()));
	}

}


