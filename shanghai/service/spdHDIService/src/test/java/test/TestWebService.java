package test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;

import common.db.SimpleDao;
import gyqx.spdhdi.uploadData.service.ResponseService;
import gyqx.spdhdi.uploadData.service.impl.ResponseServiceImpl;
import gyqx.spdhdi.uploadData.util.JsonUtil;
import gyqx.spdhdi.uploadData.vo.BaseConst;
import gyqx.spdhdi.uploadData.vo.Msg;
import gyqx.spdhdi.uploadData.vo.ProvOrgInnerCodeVo;

//@RunWith(SpringJUnit4ClassRunner.class) // 使用junit4进行测试
//@ContextConfiguration({ "classpath:configs/spring-context.xml"})
public class TestWebService {


	private static ResponseService responseService = new ResponseServiceImpl();
	
	@Resource
	private static SimpleDao simpleDao;
	public static void main(String[] args) throws Exception {
		
		String data = "{\"接口类型\":\"SPD订单接口表\",\"接收系统标识\":\"JDE\",\"接口数据\":"
				+ "{\"SPD系统标识\":\"CT-SPD\",\"_date\":\"2018-03-07 08:51:43\",\"业务员ERP编号\":\"12345612\",\"业务员名称\":\"宋江\",\"业务部门ERP编号\":\"102301\",\"业务部门名称\":\"发货部门\",\"收货科室ERP编号\":null,\"收货科室名称\":\"设备科\",\"收货科室编号\":\"h0011:001\",\"要货医院\":\"h0011\",\"要货医院ERP编号\":\"1002519\",\"要货医院名称\":\"复旦大学附属华山医院北院\",\"要货科室ERP编号\":null,\"要货科室名称\":\"设备科\",\"要货科室编号\":\"h0011:001\",\"订单总行数\":1,\"请购类型\":\"请购\",\"配送单位\":\"p000d\",\"配送单位ERP编号\":\"00102\",\"配送单位ERP部门编号\":\"102301\","
				+ "\"配送单位名称\":\"国药集团联合医疗器械有限公司\",\"配送单位系统标识\":\"JDE\",\"采购订单号\":\"QRh0011201803070001\",\"采购订单明细\":"
				+ "[{\"产地\":\"马来西亚\",\"包装\":null,\"医疗机构申请单号\":\"CGh0011201803070001\",\"医药医材ERP编号\":\"10000024\",\"医药医材名称\":\"颅骨板固定系统\",\"医药医材编号\":\"hsby_0009\",\"单价\":800.00,\"单位\":\"件\",\"指定供应商ERP名称\":null,\"指定供应商ERP编号\":null,\"数量\":1.00,\"生产厂商\":\"马来西亚 Ansell Shah Alam Sdn. Bhd.\",\"科室申请单号\":\"b20180207001\",\"规格\":\"53-00324\",\"采购订单日期\":\"2018-03-07 08:36:46\",\"采购订单行号\":1,\"预计到货日期\":null}]}}";
		//String data = "{\"接口类型\":\"SPD请退接口表\",\"接收系统标识\":\"JDE\",\"接口数据\":{\"SPD系统标识\":\"JKSPD\",\"配送单位系统标识\":\"用友 U8A 帐套\",\"配送单位\":\"000003\",\"配送单位ERP编号\":\"00001\",\"配送单位ERP部门编号\":\"01001\",\"配送单位名称\":\"第一医药配送公司\",\"退货医院\":\"000001\",\"退货医院ERP编号\":\"011\",\"退货医院名称\":\"第一人民医院\",\"退货科室编号\":\"000003\",\"退货科室ERP编号\":\"K004\",\"退货科室名称\":\"第一医院住院部\",\"出库科室编号\":\"000002\",\"出库科室ERP编号\":\"K002\",\"出库科室名称\":\"第一医院设备科\",\"_date\":\"2016-09-14 00:00:00\",\"退货单号\":\"KSQT2014080002\",\"退货单日期\":\"2014-08-16 09:28:00\",\"退货单总行数\":2,\"请退明细\":[{\"退货单行号\":1,\"原医院请购单号\":\"QGDD2014087774\",\"原科室请购单号\":\"KSQG0001\",\"医药医材编号\":\"000001\",\"医药医材ERP编号\":\"a001\",\"医药医材名称\":\"测试医材11\",\"规格\":\"1x1\",\"生产厂商\":\"康辉器械厂\",\"产地\":\"-\",\"单位\":\"组\",\"批号\":\"00\",\"灭菌批号\":\"-\",\"库存类型\":\"借货\",\"有效期至\":\"2019-01-02 00:00:00\",\"生产日期\":\"2013-01-01 00:00:00\",\"灭菌日期\":\"2013-01-02 00:00:00\",\"灭菌批号有效期至\":\"2019-01-01 00:00:00\",\"包装\":10.0000,\"单价\":100.000000,\"数量\":4.0000,\"入库类型\":0}]}}";
		JSONObject obj = JSONObject.parseObject(data);
		data = "{}";
		String jsondata = JsonUtil.toJsonString(obj);
		System.out.println("json字符串: => " + jsondata);
		//ProvOrgInnerCodeVo vo =innerCodeDao.getProOrgInnerCode("JDE", "00102");
		ProvOrgInnerCodeVo vo = new ProvOrgInnerCodeVo();
		vo.setUploadUrl("http://10.17.9.237:8080/cmicSpdV2/spd/uploaddata?wsdl");
		vo.setProvErpCode("00102");
		vo.setPutPass("iFc0J0T3fQgK1xAS");
		vo.setPutKey("7B85B3A0-64D6-4AA1-B92B-6BF0602CAE95");
		vo.setUploadPass("iFc0J0T3fQgK1xAS");
		vo.setUploadKey("7B85B3A0-64D6-4AA1-B92B-6BF0602CAE95");
		
		String str = responseService.sendData(jsondata, vo, BaseConst.SPDHEAD.SPD_DD);
		Msg msg = responseService.doResponse(str);
		System.out.println("返回结果: => " + msg);
	}

}
