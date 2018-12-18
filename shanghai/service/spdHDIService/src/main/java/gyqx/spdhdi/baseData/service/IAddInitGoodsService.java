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


public interface IAddInitGoodsService 
{
	
	public InitGoodsVo add4GoodsItems_v2(InitGoodsVo v,BasCompanyInfo mfrs4add,BasCompanyInfo prov4add,BasGoodsInfo bg,ProvGoodsInfo pg,HosGoodsInfo hg,ProvGoodsHos pgh,ProvHosInfoVo phi,HosCollector hc,HosCollectorProv hcp,ProvProvGoods ppg,String userId  ) throws Exception;

}
