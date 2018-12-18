package gyqx.spdhdi.collector4orderMgr.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.collector4orderMgr.dao.mapper.IProvProvGoodsMapper;
import gyqx.spdhdi.collector4orderMgr.vo.ProvProvGoodsVo;
import gyqx.spdherp.po.ProvProvGoods;

@Repository
public class ProvProvGoodsDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IProvProvGoodsMapper mapper;

	public ProvProvGoods get(String id)
	{
		return dao.get(ProvProvGoods.class, id);	
	}	

	public ProvProvGoods insertAndGet(ProvProvGoods provProvGoods) throws Exception 
	{
		return dao.insertAndGet(provProvGoods);
	}

	public ProvProvGoods updateAndGet(ProvProvGoods provProvGoods) throws Exception {
		return dao.updateAndGet(provProvGoods);
	}
	public int updateFields(ProvProvGoods provProvGoods,String fieldNames) throws Exception
	{
		return dao.updateFields(provProvGoods, fieldNames);
	}
	public int updateNotNullFields(ProvProvGoods provProvGoods) throws Exception
	{
		return dao.updateNotNullFields(provProvGoods);
	}

	public void delete(ProvProvGoods provProvGoods) throws Exception {
		dao.delete(provProvGoods);
	}
	
	public ProvProvGoods getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (ProvProvGoods) dao.getByFieldName(ProvProvGoods.class, fieldName, fieldValue);
	}
	
	public List<ProvProvGoods> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<ProvProvGoods>) dao.queryBeanList(ProvProvGoods.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(ProvProvGoods.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<ProvProvGoods> list(ProvProvGoods qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
	public List<ProvProvGoodsVo> listVo(ProvProvGoodsVo qbean) throws Exception{		
		
		return mapper.listVo(qbean);
	}
}


