package gyqx.spdhdi.collector4orderMgr.service;


import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.collector4orderMgr.vo.ProvProvGoodsVo;
import gyqx.spdherp.po.ProvProvGoods;

public interface IProvProvGoodsService 
{
	ProvProvGoods get(String id) throws Exception ;
	ProvProvGoods add(ProvProvGoods provProvGoods) throws Exception ;
	ProvProvGoods update(ProvProvGoods provProvGoods) throws Exception;
	
	List<ProvProvGoods> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<ProvProvGoods> list(ProvProvGoods bean) throws Exception;
	
	QueryResult<ProvProvGoods> listByPage(QueryInfo<ProvProvGoods> queryInfo) throws Exception;	
	QueryResult<ProvProvGoodsVo> listVoByPage(QueryInfo<ProvProvGoodsVo> queryInfo) throws Exception;	

}


