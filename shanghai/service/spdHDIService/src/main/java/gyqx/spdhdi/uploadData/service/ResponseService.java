package gyqx.spdhdi.uploadData.service;

import gyqx.spdhdi.uploadData.vo.Msg;
import gyqx.spdhdi.uploadData.vo.ProvOrgInnerCodeVo;

public interface ResponseService {

	/**
	 * webService推送数据
	 * @param jsondata json串
	 * @param vo 配送机构内部标识
	 * @param type 接口类型
	 * @return
	 */
	String sendData(String jsondata, ProvOrgInnerCodeVo vo, String type) throws Exception;

	/**
	 * 推送数据返回结果处理
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException 
	 */
	Msg doResponse(Object obj) throws Exception;
}
