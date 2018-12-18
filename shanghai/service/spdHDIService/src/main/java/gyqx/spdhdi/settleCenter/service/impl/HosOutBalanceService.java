package gyqx.spdhdi.settleCenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UtilsContext;
import gyqx.spdhdi.myInfos.service.IProvHosDeptInfoCodeService;
import gyqx.spdhdi.myInfos.vo.SaleCodeInfoVo;
import gyqx.spdhdi.quartz.utils.UpConstance;
import gyqx.spdhdi.settleCenter.Constance;
import gyqx.spdhdi.settleCenter.dao.HosOutBalanceDao;
import gyqx.spdhdi.settleCenter.dao.HosOutBalanceDetailDao;
import gyqx.spdhdi.settleCenter.service.IHosOutBalanceService;
import gyqx.spdhdi.settleCenter.vo.HosOutBalanceDetailVo;
import gyqx.spdhdi.settleCenter.vo.HosOutBalanceVo;
import gyqx.spdhdi.uploadData.service.InfoSyncService;
import gyqx.spdhdi.uploadData.vo.BalanceDetailJson;
import gyqx.spdhdi.uploadData.vo.BalanceJson;
import gyqx.spdhdi.uploadData.vo.Msg;
import gyqx.spdhdi.uploadData.vo.PurType;

@Service
public class HosOutBalanceService implements IHosOutBalanceService {

	private static Logger log = LoggerFactory.getLogger(HosOutBalanceService.class);

	@Resource
	private HosOutBalanceDao dao;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private HosOutBalanceDetailDao detailDao;
	// @Resource
	// private InStockListDao inStockListDao;
	@Resource
	private IProvHosDeptInfoCodeService povHosDeptInfoCodeService;
	@Resource
	private UtilsContext utilsContext;
	@Resource
	private SysConfigUtil configUtil;
	@Resource
	private InfoSyncService infoSyncService;
	@Resource
	private SimpleDao simpleDao;

	public HosOutBalanceVo getById(String id) throws Exception {
		return dao.getById(id);
	}

	public HosOutBalanceVo getByBillId(String billId) throws Exception {
		return dao.getByBillId(billId);
	}

	public HosOutBalanceVo add(HosOutBalanceVo hosOutBalance) throws Exception {
		// String spdCode =
		// utilsContext.getSysConfigUtil().getValue(gyqx.spdhdi.orderMgr.Constance.SPDID);
		// String id =
		// atomUtil.newId("outBalanceId").prefix(hosOutBalance.getHosId()).get();//atomUtil.newValue("outBalanceId");
		// hosOutBalance.setId(id);
		// hosOutBalance.setBillId(id);
		// SaleCodeInfoVo sciVo = new SaleCodeInfoVo();
		// sciVo.setHosDeptId(utilsContext.getUserStateUtils().getCurrent().getOrgId());
		// sciVo.setHosId(hosOutBalance.getHosId());
		// sciVo.setProvId(hosOutBalance.getProvId());
		// SaleCodeInfoVo saleMan =
		// povHosDeptInfoCodeService.getSaleCodeInfo(sciVo);
		// if(saleMan != null) {
		// hosOutBalance.setProvDeptErpCode(saleMan.getSaleDeptErpCode());
		// }
		// for (HosOutBalanceDetailVo hosOutBalanceDetailVo :
		// hosOutBalance.getLstDetail()) {
		// String did = atomUtil.newValue("outBalanceDetailId");
		// hosOutBalanceDetailVo.setId(did);
		// hosOutBalanceDetailVo.setBillId(hosOutBalance.getBillId());
		// hosOutBalanceDetailVo.setPid(hosOutBalance.getId());
		// }
		// detailDao.insertByBatch(hosOutBalance.getLstDetail());
		// dao.insert(hosOutBalance);
		// if(Constance.SETTLE_MAIN_STATUS_SUBMIT.equals(hosOutBalance.getStatus())){
		// this.setOutBillStatus(hosOutBalance);
		// }
		// return this.getById(id);
		return null;
	}

	public HosOutBalanceVo update(HosOutBalanceVo hosOutBalance) throws Exception {
		 Date lastUpdateTime = new Date();
		 for (HosOutBalanceDetailVo hosOutBalanceDetailVo :
		 hosOutBalance.getLstDetail()) {
//		 String did = atomUtil.newValue("outBalanceDetailId");
//		 hosOutBalanceDetailVo.setId(did);
//		 hosOutBalanceDetailVo.setBillId(hosOutBalance.getBillId());
//		 hosOutBalanceDetailVo.setPid(hosOutBalance.getId());
			 hosOutBalanceDetailVo.setLastUpdateDatetime(lastUpdateTime);
		 }
//		 detailDao.delByBillId(hosOutBalance.getBillId());
		 hosOutBalance.setLastUpdateDatetime(lastUpdateTime);
		 detailDao.updateByBatch(hosOutBalance.getLstDetail());
		 dao.update(hosOutBalance);
//		 if(Constance.SETTLE_MAIN_STATUS_SUBMIT.equals(hosOutBalance.getStatus())){
//		 this.setOutBillStatus(hosOutBalance);
//		 }
		 return this.getById(hosOutBalance.getId());
//		return null;
	}

	@SuppressWarnings("unused")
	private int setOutBillStatus(HosOutBalanceVo hosOutBalance) throws Exception {
		int res = 0;
		// List<InStockListVo> lstOutDetail = new ArrayList<>();
		// for (HosOutBalanceDetailVo hosOutBalanceDetailVo :
		// hosOutBalance.getLstDetail()) {
		// InStockListVo osl = new InStockListVo();
		// osl.setPid(hosOutBalanceDetailVo.getOutBillid());
		// osl.setBillId(hosOutBalanceDetailVo.getOutBillid());
		// osl.setInBillRow(hosOutBalanceDetailVo.getOutRowNum());
		// osl.setState(Constance.IN_STOCK_LIST_STATUS_SETTLE);
		// lstOutDetail.add(osl);
		// }
		// if(lstOutDetail.size()>0){
		// res = inStockListDao.updateInBillLstStatus(lstOutDetail);
		// }
		return res;
	}

	public int delById(String id) throws Exception {
		return dao.delById(id);
	}

	public List<HosOutBalanceVo> list(HosOutBalanceVo bean) throws Exception {
		return dao.list(bean);
	}

	public QueryResult<HosOutBalanceVo> listByPage(QueryInfo<HosOutBalanceVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

	// JDE start
	@Override
	public List<HosOutBalanceVo> getAllBillSubmit() {
		return dao.getAllBillSubmit();
	}

	@Override
	public Msg balanceUp(HosOutBalanceVo hosOutBalanceVo) throws Exception {
		Msg msg = this.sendToERP(hosOutBalanceVo);
		if (msg.isSuccess()) {
			hosOutBalanceVo.setStatus(Constance.SETTLE_MAIN_STATUS_UP);
			dao.update(hosOutBalanceVo);
		}
		return msg;
	}

	// 结算单写ERP
	private Msg sendToERP(HosOutBalanceVo hbv) throws Exception {
		BalanceJson bj = new BalanceJson();
		bj.setSpdCode(configUtil.getValue("sys.name"));

		bj.setProvId(hbv.getProvId());
		bj.setProvErpCode(hbv.getProvErpCode());
		bj.setProvDeptErpCode(hbv.getProvDeptErpCode());
		bj.setProvName(hbv.getProvName());

		bj.setHosId(hbv.getHosId());
		bj.setHosErpCode(hbv.getHosErpCode());
		bj.setHosName(hbv.getHosName());
		bj.setJsBillId(hbv.getBillId());//结算单号

		String userId = hbv.getSaleMan();
		if (org.apache.commons.lang3.StringUtils.isEmpty(userId)) {
			log.info("###############################SPD上传结算单, 业务员为空, 结算单: => {}" + hbv.getBillId());
		}
		SaleCodeInfoVo saleMan = povHosDeptInfoCodeService.getSaleCodeInfoById(userId);
		if (saleMan != null) {
			bj.setSaleDeptErpCode(saleMan.getSaleDeptErpCode());
			bj.setSaleDeptName(saleMan.getSaleDeptName());
			bj.setSalemanCode(saleMan.getSalemanCode());
			bj.setSalemanName(saleMan.getSalemanName());
		}

		List<BalanceDetailJson> bdjs = new ArrayList<>();
		for (HosOutBalanceDetailVo hbd : hbv.getLstDetail()) {
			BalanceDetailJson bdj = new BalanceDetailJson();
			/*
			bdj.setProvId(hbd.getProvId());
			
			bdj.setProvName(hbd.getProvName());*/
			bdj.setProvErpCode(hbd.getProvErpCode());
			//bdj.setJsBillId(hbv.getBillId());//计算单号
			bdj.setReceiveDate(hbd.getReceiveDate());
			bdj.setReceiveTime(hbd.getReceiveDate());
			bdj.setDeliveryDate(hbd.getDeliveryDate());
			bdj.setDeliveryTime(hbd.getDeliveryDate());
			bdj.setPurchasePrice(hbd.getPurchasePrice());
			bdj.setSalePrice(hbd.getPrice());
			bdj.setReceiveTemprature(hbd.getReceiveTemprature());
			bdj.setSendTemprature(hbd.getSendTemprature());
			bdj.setTransportType(hbd.getTransportType());
			
			bdj.setSubProvId(hbd.getSubProvId());
			bdj.setSubProvErpCode(hbd.getSubProvErpCode());
			bdj.setSubProvName(hbd.getSubProvName());

			bdj.setOutDeptId(hbd.getOutDeptId());
			bdj.setOutDeptErpCode(hbd.getOutDeptErpCode());
			bdj.setOutDeptName(hbd.getOutDeptName());
			bdj.setOutBillId(hbd.getOutBillid());
			bdj.setOutDate(hbd.getOutDate());
			// 0 -- 表示结算机构自己的结算数据
			// 1 -- 表示合作供应商需要通过结算供应商进行结算数据
			bdj.setNumKind(UpConstance.PARTNER_ONE);
			bdj.setOutKind(UpConstance.NORMAL_OUT); // 默认正常出库
			//bdj.setOutRowNum(hbd.getOutRowNum());
			bdj.setOutRowNum(hbd.getRowNum());//这里需要传结算单行号
			bdj.setProvGoodsId(hbd.getProvGoodsId());
			//bdj.setProvGoodsErpCode(getProvGoodsErpCode(hbd.getProvGoodsId()));
			bdj.setProvGoodsErpCode(hbd.getGoodsErpCode());
			bdj.setProvGoodsErpName(hbd.getProvGoodsName());
			
			bdj.setGoodsGg(hbd.getGoodsGg());
			bdj.setMfrsName(hbd.getMfrsName());
			bdj.setMade(hbd.getMade());
			bdj.setUnit(hbd.getProvUnit());
			bdj.setBatchCode(hbd.getBatchCode());
			bdj.setSterilizationCode(hbd.getSterilizationCode());
			bdj.setStockType(PurType.ACTUAL_PURCHASE); // 默认请购
			bdj.setExpdtEndDate(hbd.getExpdtEndDate());//有效期至
			bdj.setProduceDatetime(hbd.getProduceDatetime());
			bdj.setSterilizationDate(hbd.getSterilizationDate());
			bdj.setSterilizationEndDate(hbd.getSterilizationEndDate());
			// bdj.setPackeage(new BigDecimal(hbd.getPacketCode())); // 包装
			//bdj.setQty(hbd.getProvUnitQty());
			bdj.setQty(hbd.getSettleQty());
			bdjs.add(bdj);
		}
		bj.setBalanceDetails(bdjs);
		bj.setSumRow(bdjs.size());
		bj.setDate(new Date());
		Msg msg = infoSyncService.sendHosOutBalance(bj);
		log.info(msg.toString());
		return msg;
	}

	private String getProvGoodsErpCode(String provGoodsId) {
		return simpleDao.queryForObject("select erp_code from prov_goods_info where id = ? and flag = '1'",
				String.class, provGoodsId);
	}

	// JDE end

}
