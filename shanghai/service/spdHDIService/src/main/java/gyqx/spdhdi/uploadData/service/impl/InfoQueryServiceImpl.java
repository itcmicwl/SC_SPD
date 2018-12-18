package gyqx.spdhdi.uploadData.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import common.exception.ExceptionMessage;
import gyqx.spdhdi.uploadData.dao.ProvOrgInnerCodeDao;
import gyqx.spdhdi.uploadData.service.InfoQueryService;
import gyqx.spdhdi.uploadData.service.ResponseService;
import gyqx.spdhdi.uploadData.util.JsonUtil;
import gyqx.spdhdi.uploadData.vo.BaseConst;
import gyqx.spdhdi.uploadData.vo.BaseJson;
import gyqx.spdhdi.uploadData.vo.DefaultCode;
import gyqx.spdhdi.uploadData.vo.EmergencyStocCondition;
import gyqx.spdhdi.uploadData.vo.EmergencyStocResponse;
import gyqx.spdhdi.uploadData.vo.GoodsSaleCondition;
import gyqx.spdhdi.uploadData.vo.GoodsSaleResponse;
import gyqx.spdhdi.uploadData.vo.LedgerCondition;
import gyqx.spdhdi.uploadData.vo.LedgerResponse;
import gyqx.spdhdi.uploadData.vo.Msg;
import gyqx.spdhdi.uploadData.vo.ProvOrgInnerCodeVo;

@Service
public class InfoQueryServiceImpl implements InfoQueryService {

	private static Logger log = LoggerFactory.getLogger(InfoQueryServiceImpl.class);

	@Resource
	private ResponseService responseService;
	@Resource
	private ProvOrgInnerCodeDao innerCodeDao;

	static {
		List<String> fs = new ArrayList<>();
		fs.add("GUID");
		fs.add("SPD系统标识");
		fs.add("医疗机构");
		fs.add("医疗机构ERP编号");
		fs.add("配送机构");
		fs.add("配送机构ERP编号");
		fs.add("配送机构系统标识");
		fs.add("起始行");
		fs.add("截止行");
	}

	@Override
	public List<GoodsSaleResponse> getGoodsSaleMx(GoodsSaleCondition condition) throws Exception {
		if (condition == null) {
			return null;
		}
		if (StringUtils.isEmpty(condition.getProvId())) {
			return null;
		}
		ProvOrgInnerCodeVo innerCodeVo = innerCodeDao.getProOrgInnerCode(condition.getProvSystemId(),
				condition.getProvId());
		Msg msg = checkProvSystem(innerCodeVo);
		if (!msg.isSuccess()) {
			return null;
		}
		List<GoodsSaleResponse> mx = new ArrayList<>();
		BaseJson<GoodsSaleCondition> bean = new BaseJson<>();
		bean.setType(BaseConst.SPDHEAD.SHPXSHMX);
		bean.setFlag(innerCodeVo.getSysFlag());
		condition.setProvSystemId(innerCodeVo.getSysFlag());
		bean.setObj(condition);
		String jsondata = JsonUtil.toJsonString(bean);
		log.info("###############################<{}>查询条件{}", bean.getType(), jsondata);
		String data = responseService.sendData(jsondata, innerCodeVo, BaseConst.SPDHEAD.SHPXSHMX);
		if (StringUtils.isEmpty(data)) {
			return null;
		}
		byte[] decode = Base64.getDecoder().decode(data.getBytes("utf-8"));
		String res = new String(decode, "utf-8");
		log.info("###############################<{}>返回数据{}", bean.getType(), res);
		JSONArray array = null;
		try {
			array = JSON.parseArray(res);
		} catch (Exception e) {
			log.info("###############################<{}>返回数据{}转换错误, 错误信息: => {}", bean.getType(), res,
					ExceptionMessage.fromException(e));
		}
		if (array == null) {
			return null;
		}
		for (Object obj : array) {
			GoodsSaleResponse record = JSON.parseObject(obj.toString(), GoodsSaleResponse.class);
			mx.add(record);
		}
		return mx;
	}

	@Override
	public List<LedgerResponse> getLedger(LedgerCondition condition) throws Exception {
		if (condition == null) {
			return null;
		}
		if (StringUtils.isEmpty(condition.getProvId())) {
			return null;
		}
		ProvOrgInnerCodeVo innerCodeVo = innerCodeDao.getProOrgInnerCode(condition.getProvSystemId(),
				condition.getProvId());
		Msg msg = checkProvSystem(innerCodeVo);
		if (!msg.isSuccess()) {
			return null;
		}
		List<LedgerResponse> mx = new ArrayList<>();
		BaseJson<LedgerCondition> bean = new BaseJson<>();
		bean.setType(BaseConst.SPDHEAD.YJKFLZH);
		bean.setFlag(innerCodeVo.getSysFlag());
		condition.setProvSystemId(innerCodeVo.getSysFlag());
		bean.setObj(condition);
		String jsondata = JsonUtil.toJsonString(bean);
		String data = responseService.sendData(jsondata, innerCodeVo, BaseConst.SPDHEAD.YJKFLZH);
		if (StringUtils.isEmpty(data)) {
			return null;
		}
		byte[] decode = Base64.getDecoder().decode(data.getBytes("utf-8"));
		String res = new String(decode, "utf-8");
		JSONArray array = JSON.parseArray(res);
		for (Object obj : array) {
			LedgerResponse record = JSON.parseObject(obj.toString(), LedgerResponse.class);
			mx.add(record);
		}
		return mx;
	}

	@Override
	public List<EmergencyStocResponse> getEmergencyStoc(EmergencyStocCondition condition) throws Exception {
		if (condition == null) {
			return null;
		}
		if (StringUtils.isEmpty(condition.getProvId())) {
			return null;
		}
		ProvOrgInnerCodeVo innerCodeVo = innerCodeDao.getProOrgInnerCode(condition.getProvSystemId(),
				condition.getProvId());
		Msg msg = checkProvSystem(innerCodeVo);
		if (!msg.isSuccess()) {
			return null;
		}
		List<EmergencyStocResponse> mx = new ArrayList<>();
		BaseJson<EmergencyStocCondition> bean = new BaseJson<>();
		bean.setType(BaseConst.SPDHEAD.YJKKC);
		bean.setFlag(innerCodeVo.getSysFlag());
		condition.setProvSystemId(innerCodeVo.getSysFlag());
		bean.setObj(condition);
		String jsondata = JsonUtil.toJsonString(bean);
		String data = responseService.sendData(jsondata, innerCodeVo, BaseConst.SPDHEAD.YJKKC);
		if (StringUtils.isEmpty(data)) {
			return null;
		}
		byte[] decode = Base64.getDecoder().decode(data.getBytes("utf-8"));
		String res = new String(decode, "utf-8");
		JSONArray array = JSON.parseArray(res);
		for (Object obj : array) {
			EmergencyStocResponse record = JSON.parseObject(obj.toString(), EmergencyStocResponse.class);
			mx.add(record);
		}
		return mx;
	}

	private Msg checkProvSystem(ProvOrgInnerCodeVo innerCodeVo) {
		if (innerCodeVo == null) {
			log.info("###############################JDE上传, 配送机构ERP信息为空: => {}", innerCodeVo);
			return new Msg(false, DefaultCode.CUSTOM, "配送机构ERP信息为空");
		}
		if (StringUtils.isEmpty(innerCodeVo.getProvErpCode())) {
			log.info("###############################JDE上传, 配送机构ERP编号为空: => {}", innerCodeVo);
			return new Msg(false, DefaultCode.CUSTOM, "配送机构ERP编号为空");
		}
		if (StringUtils.isEmpty(innerCodeVo.getSysFlag())) {
			log.info("###############################JDE上传, 配送机构系统标识为空: => {}", innerCodeVo);
			return new Msg(false, DefaultCode.CUSTOM, "配送机构系统标识为空");
		}
		if (StringUtils.isEmpty(innerCodeVo.getUploadUrl())) {
			log.info("###############################JDE上传, 配送机构接口地址为空: => {}", innerCodeVo);
			return new Msg(false, DefaultCode.CUSTOM, "配送机构接口地址为空");
		}
		log.info("###############################JDE上传, 配送机构信息: => {}", innerCodeVo);
		return new Msg(true, DefaultCode.CUSTOM, "配送机构信息：" + innerCodeVo.getUploadUrl());
	}
}
