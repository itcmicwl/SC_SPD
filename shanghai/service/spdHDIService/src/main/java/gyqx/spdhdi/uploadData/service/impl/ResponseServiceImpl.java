package gyqx.spdhdi.uploadData.service.impl;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.el.cmic.service.SpdWsServiceImplServiceStub;
import com.el.cmic.service.SpdWsServiceImplServiceStub.UpLoadData;
import com.el.cmic.service.SpdWsServiceImplServiceStub.UpLoadDataE;
import com.el.cmic.service.SpdWsServiceImplServiceStub.UpLoadDataResponseE;

import common.exception.ExceptionMessage;
import gyqx.spdhdi.uploadData.excepiton.UploadDataException;
import gyqx.spdhdi.uploadData.service.ResponseService;
import gyqx.spdhdi.uploadData.util.DateUtil;
import gyqx.spdhdi.uploadData.util.MD5Util;
import gyqx.spdhdi.uploadData.vo.Msg;
import gyqx.spdhdi.uploadData.vo.ProvOrgInnerCodeVo;

/**
 * webService 调用JDE数据接口
 *
 * @date 2017年9月14日 上午10:12:18
 * @author LIWENKANG
 */
@Service
public class ResponseServiceImpl implements ResponseService {

	private final static Logger log = LoggerFactory.getLogger(ResponseServiceImpl.class);

	//private SpdWsServiceImplServiceStub stub = null;
	 
	private Map<String,SpdWsServiceImplServiceStub> stubMap = new ConcurrentHashMap<>();
	
	@Override
	public String sendData(String jsondata, ProvOrgInnerCodeVo vo, String type) throws Exception {
		SpdWsServiceImplServiceStub stub = stubMap.get(vo.getProvErpCode());
		String wsdlUrl = vo.getUploadUrl();
		if(stub == null){
			log.info("#######################<{}>初始化{}#######################", type, wsdlUrl);
			wsdlUrl = wsdlUrl .replace("?wsdl", "");
			SpdWsServiceImplServiceStub stub1 = new SpdWsServiceImplServiceStub(wsdlUrl);
			stub1._getServiceClient().getOptions().setTimeOutInMilliSeconds(100_000);
			stubMap.put(vo.getProvErpCode(), stub1);
			log.info("#######################初始化 OK#######################");
		}

		String res = null;
		String appkey = vo.getUploadKey();
		String appPassword = vo.getUploadPass();
		String timestamp = DateUtil.getTimeStamp();
		log.info("#######################<{}>json串: => {}", type, jsondata);

		String data = Base64.getEncoder().encodeToString(jsondata.getBytes("utf-8"));
		String sign = MD5Util.getMD5(timestamp, data, appPassword);
		String ver = "1";
		try {
			log.info("#######################<{}>, UpLoadData方法调用开始#######################", type);
			UpLoadDataE ud = new UpLoadDataE();
			UpLoadData udp = new UpLoadData();
			udp.setArg0(appkey);
			udp.setArg1(timestamp);
			udp.setArg2(data);
			udp.setArg3(sign);
			udp.setArg4(ver);
			ud.setUpLoadData(udp);

			UpLoadDataResponseE resp = stubMap.get(vo.getProvErpCode()).upLoadData(ud);
			res = resp.getUpLoadDataResponse().get_return();

			//res = client.invoke("UpLoadData", appkey, timestamp, data, sign, ver);
			log.info("#######################<{}>, UpLoadData方法调用成功, 返回结果: => {}#######################", type, res);
		} catch (Exception e) {
			log.error("#######################<{}>, 调用JDE接口<方法>失败: => {}", type, ExceptionMessage.fromException(e));
			throw new UploadDataException("JDE接口方法调用失败, 请检查网络连接");
		}
		if (!StringUtils.hasText(res)) {
			log.error("#######################<{}>, UpLoadData返回信息为空: => {}", type, res);
			throw new IllegalArgumentException("JDE返回信息为空: => " + res);
		}
		return res;
//		log.info("#######################<{}>开始连接{}#######################", type, wsdlUrl);
//		Client client;
//		try {
//			JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
//			client = factory.createClient(wsdlUrl);
//			log.info("#######################<{}>连接{}成功#######################", type, wsdlUrl);
//		} catch (Exception e) {
//			log.error("#######################<{}>, 调用JDE接口失败: => {}", type, ExceptionMessage.fromException(e));
//			throw new CreateServiceFailException("JDE接口服务异常, 请联系JDE管理员");
//		}
//		Object[] res = null;
//		String appkey = vo.getUploadKey();
//		String appPassword = vo.getUploadPass();
//		String timestamp = DateUtil.getTimeStamp();
//		log.info("#######################<{}>json串: => {}", type, jsondata);
//
//		String data = Base64.getEncoder().encodeToString(jsondata.getBytes("utf-8"));
//		String sign = MD5Util.getMD5(timestamp, data, appPassword);
//		String ver = "1";
//		try {
//			log.info("#######################<{}>, UpLoadData方法调用开始#######################", type);
//			res = client.invoke("UpLoadData", appkey, timestamp, data, sign, ver);
//			log.info("#######################<{}>, UpLoadData方法调用成功, 返回结果: => {}#######################", type, res);
//		} catch (Exception e) {
//			log.error("#######################<{}>, 调用JDE接口<方法>失败: => {}", type, ExceptionMessage.fromException(e));
//			throw new UploadDataException("JDE接口方法调用失败, 请检查网络连接");
//		}
//		if (res == null || res.length == 0) {
//			log.error("#######################<{}>, UpLoadData返回信息为空: => {}", type, res);
//			throw new IllegalArgumentException("JDE返回信息为空: => " + res);
//		}
//		return res[0].toString();

	}

	@Override
	public Msg doResponse(Object obj) throws Exception {
		// 解码
		Msg msg = null;
		log.info("#######################UpLoadData方法返回结果{}开始解析#######################", obj);
		byte[] decode = Base64.getDecoder().decode(obj.toString().getBytes("utf-8"));
		String res = new String(decode, "utf-8");
		log.info("#######################成功解析UpLoadData远程方法返回结果{}, 解析得到: => {}", obj, res);
		msg = JSON.parseObject(res, Msg.class);
		log.info("#######################远程调用返回结果：=> {}, 调用结束#######################", msg.getMessage());
		return msg;
	}

}
