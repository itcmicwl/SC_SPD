package gyqx.spdhdi.baseData.service;

import java.util.List;
import java.util.Map;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.baseData.service.impl.BasGoodsInfoService.Task;
import gyqx.spdhdi.baseData.vo.BasGoodsInfoVo;
import gyqx.spdhdi.baseData.vo.InitGoodsVo;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import gyqx.spdhdi.po.BasCompanyInfo;
import gyqx.spdhdi.po.BasGoodsInfo;
import gyqx.spdhdi.po.BasMfrsInfo;
import gyqx.spdhdi.po.HosGoodsInfo;
import gyqx.spdhdi.po.ProvGoodsHos;
import gyqx.spdhdi.po.ProvGoodsInfo;
import gyqx.spdherp.po.HosCollector;
import gyqx.spdherp.po.HosCollectorProv;
import gyqx.spdherp.po.ProvProvGoods;


public interface IBasGoodsInfoService 
{
	public BasGoodsInfo addBasGood(BasGoodsInfo good) throws Exception;
	
	public BasGoodsInfo updateBasGood(BasGoodsInfo good) throws Exception;
	
	public QueryResult<BasGoodsInfoVo> listBasGoods(QueryInfo<BasGoodsInfoVo> queryInfo) throws Exception;
	
	public void deleteBasGood(BasGoodsInfo good) throws Exception;	
	public List<BasGoodsInfo> addBaseGoodS4ImportExcel(List<BasGoodsInfo> goods) throws Exception;
	
	public List<InitGoodsVo> add4ImportExcelInit(List<InitGoodsVo> vList) throws Exception;
	public List<InitGoodsVo> add4ImportExcelInit_v2(List<InitGoodsVo> vList) throws Exception;

	public Task getBySid(String type, String s_taskId) throws Exception;
	public Map startTask(@SuppressWarnings("rawtypes") Map map) throws Exception;
	public String add4DealImport(@SuppressWarnings("rawtypes") Map map,String userId) throws Exception;
	

}
