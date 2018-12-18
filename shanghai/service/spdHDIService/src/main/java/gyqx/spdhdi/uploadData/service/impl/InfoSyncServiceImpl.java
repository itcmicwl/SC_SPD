package gyqx.spdhdi.uploadData.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gyqx.spdhdi.uploadData.dao.ProvOrgInnerCodeDao;
import gyqx.spdhdi.uploadData.service.InfoSyncService;
import gyqx.spdhdi.uploadData.service.ResponseService;
import gyqx.spdhdi.uploadData.util.JsonUtil;
import gyqx.spdhdi.uploadData.vo.BalanceJson;
import gyqx.spdhdi.uploadData.vo.BaseConst;
import gyqx.spdhdi.uploadData.vo.BaseJson;
import gyqx.spdhdi.uploadData.vo.DefaultCode;
import gyqx.spdhdi.uploadData.vo.Msg;
import gyqx.spdhdi.uploadData.vo.ProvOrgInnerCodeVo;
import gyqx.spdhdi.uploadData.vo.PurconfirmJson;
import gyqx.spdhdi.uploadData.vo.ReturnconfirmJson;

@Service
public class InfoSyncServiceImpl implements InfoSyncService {

	private static Logger log = LoggerFactory.getLogger(InfoSyncServiceImpl.class);

	@Resource
	private ResponseService responseService;
	@Resource
	private ProvOrgInnerCodeDao innerCodeDao;

	@Override
	public Msg sendPurchaseInfo(PurconfirmJson vo) throws Exception {
		if (vo == null) {
			log.info("###############################SPD订单上传, 采购单数据为空: => {}", vo);
			return new Msg(false, DefaultCode.CUSTOM, "采购单数据为空");
		}
		if (StringUtils.isEmpty(vo.getProvId())) {
			log.info("###############################SPD订单上传, 配送机构不存在: => {}", vo);
			return new Msg(false, DefaultCode.CUSTOM, "配送机构不存在");
		}
		ProvOrgInnerCodeVo innerCodeVo = innerCodeDao.getProOrgInnerCode(vo.getProvSystemId(), vo.getProvId());
		Msg msg = checkProvSystem(innerCodeVo);
		if (!msg.isSuccess()) {
			return msg;
		}
		BaseJson<PurconfirmJson> bean = new BaseJson<>();
		bean.setType(BaseConst.SPDHEAD.SPD_DD);
		bean.setFlag(innerCodeVo.getSysFlag());
		vo.setProvSystemId(innerCodeVo.getSysFlag());
		bean.setObj(vo);
		String jsondata = JsonUtil.toJsonString(bean);
		String str = responseService.sendData(jsondata, innerCodeVo, BaseConst.SPDHEAD.SPD_DD);
		return responseService.doResponse(str);
	}

	@Override
	public Msg sendReturnconfirm(ReturnconfirmJson vo) throws Exception {
		if (vo == null) {
			log.info("###############################SPD退货单上传, 退货单数据为空: => {}", vo);
			return new Msg(false, DefaultCode.CUSTOM, "退货单数据为空");
		}
		if (StringUtils.isEmpty(vo.getProvId())) {
			log.info("###############################SPD退货单上传, 配送机构不存在: => {}", vo);
			return new Msg(false, DefaultCode.CUSTOM, "配送机构不存在");
		}
		ProvOrgInnerCodeVo innerCodeVo = innerCodeDao.getProOrgInnerCode(vo.getProvSystemId(), vo.getProvId());
		Msg msg = checkProvSystem(innerCodeVo);
		if (!msg.isSuccess()) {
			return msg;
		}
		BaseJson<ReturnconfirmJson> bean = new BaseJson<>();
		bean.setType(BaseConst.SPDHEAD.SPD_QT);
		bean.setFlag(innerCodeVo.getSysFlag());
		vo.setProvSystemId(innerCodeVo.getSysFlag());
		bean.setObj(vo);
		String jsondata = JsonUtil.toJsonString(bean);
		String str = responseService.sendData(jsondata, innerCodeVo, BaseConst.SPDHEAD.SPD_QT);
		return responseService.doResponse(str);
	}

	@Override
	public Msg sendHosOutBalance(BalanceJson vo) throws Exception {
		if (vo == null) {
			log.info("###############################SPD结算单上传, 结算单数据为空: => {}", vo);
			return new Msg(false, DefaultCode.CUSTOM, "结算单数据为空");
		}
		if (StringUtils.isEmpty(vo.getProvId())) {
			log.info("###############################SPD结算单上传, 配送机构不存在: => {}", vo);
		}
		ProvOrgInnerCodeVo innerCodeVo = innerCodeDao.getProOrgInnerCode(vo.getProvSystemId(), vo.getProvId());
		Msg msg = checkProvSystem(innerCodeVo);
		if (!msg.isSuccess()) {
			return msg;
		}
		BaseJson<BalanceJson> bean = new BaseJson<>();
		bean.setType(BaseConst.SPDHEAD.SPD_JS);
		bean.setFlag(innerCodeVo.getSysFlag());
		vo.setProvSystemId(innerCodeVo.getSysFlag());
		bean.setObj(vo);
		String jsondata = JsonUtil.toJsonString(bean);
		String str = responseService.sendData(jsondata, innerCodeVo, BaseConst.SPDHEAD.SPD_JS);
		return responseService.doResponse(str);
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
