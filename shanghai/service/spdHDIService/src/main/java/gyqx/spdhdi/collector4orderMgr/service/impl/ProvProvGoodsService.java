package gyqx.spdhdi.collector4orderMgr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import gyqx.spdhdi.collector4orderMgr.dao.ProvProvGoodsDao;
import gyqx.spdhdi.collector4orderMgr.service.IProvProvGoodsService;
import gyqx.spdhdi.collector4orderMgr.vo.ProvProvGoodsVo;
import gyqx.spdherp.po.ProvProvGoods;

@Service
public class ProvProvGoodsService implements IProvProvGoodsService {
	
	@Resource
	private ProvProvGoodsDao  dao;
	
	public ProvProvGoods get(String id)
	{
		return dao.get( id);	
	}	

	public ProvProvGoods add(ProvProvGoods provProvGoods) throws Exception 
	{
		return dao.insertAndGet(provProvGoods);
	}
		
	public ProvProvGoods update(ProvProvGoods provProvGoods) throws Exception {
		return dao.updateAndGet(provProvGoods);
	}
	public int updateFields(ProvProvGoods provProvGoods,String fieldNames) throws Exception
	{
		return dao.updateFields(provProvGoods, fieldNames);
	}

	public void delete(ProvProvGoods provProvGoods) throws Exception {
		dao.delete(provProvGoods);
	}
	
	public ProvProvGoods getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (ProvProvGoods) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<ProvProvGoods> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<ProvProvGoods>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<ProvProvGoods> list(ProvProvGoods bean) throws Exception
	{
		return (List<ProvProvGoods>) dao.list(bean);
	}	
	
	public QueryResult<ProvProvGoods> listByPage(QueryInfo<ProvProvGoods> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}
	public QueryResult<ProvProvGoodsVo> listVoByPage(QueryInfo<ProvProvGoodsVo> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.listVo(queryInfo.getQueryObject()));
	}
}


