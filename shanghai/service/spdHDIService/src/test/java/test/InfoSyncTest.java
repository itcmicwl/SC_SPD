package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;

import gyqx.spdhdi.uploadData.dao.ProvOrgInnerCodeDao;
import gyqx.spdhdi.uploadData.service.ResponseService;
import gyqx.spdhdi.uploadData.util.JsonUtil;
import gyqx.spdhdi.uploadData.vo.BaseConst;
import gyqx.spdhdi.uploadData.vo.Msg;
import gyqx.spdhdi.uploadData.vo.ProvOrgInnerCodeVo;

@RunWith(SpringJUnit4ClassRunner.class) // 使用junit4进行测试
@ContextConfiguration({ "classpath:configs/spring-context.xml", "classpath:configs/application-cxf.xml" })
public class InfoSyncTest {

	@Resource
	private ResponseService responseService;
	@Resource
	private ProvOrgInnerCodeDao innerCodeDao;
 	
	/**
	 * 测试修改采购订单号20170927p22
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		String data = "{\"接收系统标识\": \"JDE\",\"接口类型\": \"SPD订单接口表\",\"接口数据\": {\"采购订单明细\": [{\"产地\": \"德国\",\"采购订单日期\": \"2017-09-27 00:00:00\",\"生产厂商\": \"杭州元力医疗器械有限公司\",\"指定供应商ERP编号\": \"1000461\",\"指定供应商ERP名称\": \"xx医疗器械有限公司\",\"医疗机构申请单号\": \"20170912P58\",\"单价\": 100,\"规格\": \"0.25x2mm\",\"包装\": \"12\",\"采购订单行号\": 1,\"科室申请单号\": \"p0449\",\"单位\": \"支\",\"数量\": 2,\"医药医材ERP编号\": \"10007768\",\"预计到货日期\": \"2017-09-28 00:00:00\",\"医药医材名称\": \"导引导管\",\"医药医材编号\": \"10007768\"}],\"业务员ERP编号\": \"17000339\",\"配送单位ERP编号\": \"00102\",\"_date\":\"2017-09-27 00:00:00\",\"订单总行数\": 1,\"请购类型\": \"请购\",\"SPD系统标识\": \"GYQX-SPD\",\"要货医院名称\": \"北京市海淀区清河医院\",	\"业务部门ERP编号\":\"102011\",\"要货医院ERP编号\": \"1001442\",\"配送单位系统标识\": \"JDE\",\"收货科室名称\": \"设备科\",\"要货医院\": \"h0006\",\"要货科室名称\": \"设备科\",\"业务部门名称\": \"国联医院项目部\",\"要货科室ERP编号\": \"6331\",\"收货科室ERP编号\": \"6331\",\"要货科室编号\": \"h0006:001\",\"配送单位ERP部门编号\": \"102302\",\"业务员名称\": \"王宝强\",\"配送单位名称\": \"国药集团联合医疗器械有限公司\",\"收货科室编号\": \"h0006:001\",\"配送单位\": \"p0005\",\"采购订单号\": \"20170927p22\"}}";
		JSONObject obj = JSONObject.parseObject(data);
		String jsondata = JsonUtil.toJsonString(obj);
		System.out.println("json字符串: => " + jsondata);
		ProvOrgInnerCodeVo vo =innerCodeDao.getProOrgInnerCode("JDE", "00102");
		// vo.setUploadUrl("http://10.17.9.90:8080/spdPile/ws/gyqx/uploadData?wsdl");
		String str = "";//responseService.sendData(jsondata, vo, BaseConst.SPDHEAD.SPD_DD);
		Msg msg = responseService.doResponse(str);
		System.out.println("返回结果: => " + msg);
	}
}
