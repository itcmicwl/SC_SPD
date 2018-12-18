package gyqx.spdhdi.uploadData.service;

import gyqx.spdhdi.uploadData.vo.BalanceJson;
import gyqx.spdhdi.uploadData.vo.Msg;
import gyqx.spdhdi.uploadData.vo.PurconfirmJson;
import gyqx.spdhdi.uploadData.vo.ReturnconfirmJson;

/**
 * 接口间接调用webService
 * 
 * @date 2017年9月18日 下午3:11:34
 */
public interface InfoSyncService {
	/**
	 * 接口类型：SPD订单接口表<br>
	 * 采购确认单
	 * 
	 * @param vo
	 * @return
	 */
	Msg sendPurchaseInfo(PurconfirmJson vo) throws Exception;

	/**
	 * 接口类型：SPD请退接口表<br>
	 * 退货确认单
	 * 
	 * @param vo
	 * @return
	 */
	Msg sendReturnconfirm(ReturnconfirmJson vo) throws Exception;

	/**
	 * 接口类型：SPD结算接口表<br>
	 * 医院出库结算单
	 * 
	 * @param vo
	 * @return
	 */
	Msg sendHosOutBalance(BalanceJson vo) throws Exception;

}
