package gyqx.spdhdi.uploadData.service;

import java.util.List;

import gyqx.spdhdi.uploadData.vo.EmergencyStocCondition;
import gyqx.spdhdi.uploadData.vo.EmergencyStocResponse;
import gyqx.spdhdi.uploadData.vo.GoodsSaleCondition;
import gyqx.spdhdi.uploadData.vo.GoodsSaleResponse;
import gyqx.spdhdi.uploadData.vo.LedgerCondition;
import gyqx.spdhdi.uploadData.vo.LedgerResponse;

/**
 * webService查询接口
 * 
 * @date 2017年9月14日 上午10:24:07
 */
public interface InfoQueryService {
	
	/**
	 * 商品销售明细查询
	 * @param condition
	 * @return
	 */
	List<GoodsSaleResponse> getGoodsSaleMx(GoodsSaleCondition condition) throws Exception;
	
	/**
	 * 应急库分类账查询
	 * @param condition
	 * @return
	 */
	List<LedgerResponse> getLedger(LedgerCondition condition) throws Exception;
	
	/**
	 * 应急库库存查询
	 * @param condition
	 * @return
	 */
	List<EmergencyStocResponse> getEmergencyStoc(EmergencyStocCondition condition) throws Exception;
}
