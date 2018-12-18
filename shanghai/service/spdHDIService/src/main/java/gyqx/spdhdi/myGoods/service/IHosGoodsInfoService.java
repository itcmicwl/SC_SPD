package gyqx.spdhdi.myGoods.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.baseData.vo.BasGoodsInfoVo;
import gyqx.spdhdi.myGoods.vo.HosGoodsInfoVo;
import gyqx.spdhdi.myGoods.vo.ProvGoods4HosVo;
import gyqx.spdhdi.po.HosGoodsInfo;
import gyqx.spdhdi.po.ProvGoodsInfo;


public interface IHosGoodsInfoService 
{
	public HosGoodsInfo addHosGood(HosGoodsInfo good) throws Exception;
	
	public HosGoodsInfo updateHosGood(HosGoodsInfo good) throws Exception;
	
	public QueryResult<HosGoodsInfoVo> listHosGoods(QueryInfo<HosGoodsInfo> queryInfo) throws Exception;
	public QueryResult<HosGoodsInfoVo> listHosGoods4CodeMgr(QueryInfo<HosGoodsInfo> queryInfo) throws Exception;

	
	public void deleteHosGood(HosGoodsInfo good) throws Exception;	
	public void multDelHosGoods(List<HosGoodsInfo> goods) throws Exception;	
	
	
	public void addHosGoodS(List<HosGoodsInfo> goods) throws Exception;

	public HosGoodsInfo convertBasGood2HosGood(BasGoodsInfoVo basGood,HosGoodsInfo hosGood);
	
	public HosGoodsInfo convertPHGood2HosGood(ProvGoods4HosVo phGoodVo,HosGoodsInfo hosGood);
	
	public List<HosGoodsInfo> addHosGoodS4ImportExcel(List<HosGoodsInfo> goods) throws Exception;

	
}
