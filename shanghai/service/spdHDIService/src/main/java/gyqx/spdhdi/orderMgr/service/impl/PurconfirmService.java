package gyqx.spdhdi.orderMgr.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.aspectj.apache.bcel.generic.RET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.myInfos.service.IProvHosDeptInfoCodeService;
import gyqx.spdhdi.myInfos.vo.SaleCodeInfoVo;
import gyqx.spdhdi.orderMgr.Constance;
import gyqx.spdhdi.orderMgr.dao.PurconfirmDao;
import gyqx.spdhdi.orderMgr.dao.PurconfirmListDao;
import gyqx.spdhdi.orderMgr.service.IPurchaseService;
import gyqx.spdhdi.orderMgr.service.IPurconfirmService;
import gyqx.spdhdi.orderMgr.vo.PurchaseListVo;
import gyqx.spdhdi.orderMgr.vo.PurchaseVo;
import gyqx.spdhdi.orderMgr.vo.PurconfirmListVo;
import gyqx.spdhdi.orderMgr.vo.PurconfirmVo;
import gyqx.spdhdi.uploadData.service.InfoSyncService;
import gyqx.spdhdi.uploadData.vo.Msg;
import gyqx.spdhdi.uploadData.vo.PurType;
import gyqx.spdhdi.uploadData.vo.PurconfirmJson;
import gyqx.spdhdi.uploadData.vo.PurconfirmListJson;

@Service
public class PurconfirmService implements IPurconfirmService {
	private static Logger log = LoggerFactory.getLogger(PurconfirmService.class);
	@Resource
	private PurconfirmDao dao;
	@Resource
	private PurconfirmListDao detailDao;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private SysConfigUtil sysConfigUtil;
	@Resource
	IPurchaseService purService;
	@Resource
	private IProvHosDeptInfoCodeService povHosDeptInfoCodeService;
	@Resource
	private InfoSyncService erpService;
	@Resource
	private SimpleDao simpleDao;

	@Override
	public PurconfirmVo getById(String id) throws Exception {
		PurconfirmVo p = dao.get(id);
		if (p != null) {
			PurconfirmListVo pdetail = new PurconfirmListVo();
			pdetail.setPid(p.getId());
			List<PurconfirmListVo> lst = detailList(pdetail);
			p.setLstDetail(lst);
		}
		return p;
	}

	@Override
	public PurconfirmVo getByBillid(String billid) throws Exception {
		PurconfirmVo p = dao.getByBillid(billid);
		if (p != null) {
			PurconfirmListVo pdetail = new PurconfirmListVo();
			pdetail.setPid(p.getId());
			List<PurconfirmListVo> lst = detailList(pdetail);
			p.setLstDetail(lst);
		}
		return p;
	}

	@Override
	public List<String> createPurconfirmBill(PurconfirmVo purconfirm) throws Exception {
		List<PurconfirmListVo> lstDetail = purconfirm.getLstDetail();
		List<String> res = new ArrayList<String>();// salean
		if (lstDetail.size() == 0) {
			ValidateException.Throw("PurconfirmList", "明细行不能为空", "");
		}
		// ProvHosDeptInfoCodeVo phd =
		// povHosDeptInfoCodeService.getThePHDeptCodeVoByProvIdAndOrgId(purconfirm.getProvId(),
		// lstDetail.get(0).getApplyDeptId());
		// 将没有业务员的明细做一个订单
		List<PurconfirmListVo> lstNoSaleMan = lstDetail.stream()
				.filter(item -> StringUtils.isNullOrEmpty(item.getSaleMan())).collect(Collectors.toList());
		if (lstNoSaleMan.size() > 0) {
			PurconfirmVo noSaleManPc = (PurconfirmVo) purconfirm.clone();
			noSaleManPc.setLstDetail(lstNoSaleMan);
			// noSaleManPc.setProvOrgCode(phd.getErpCode());
			String billId = this.addPurc(noSaleManPc);
			res.add(billId);
			lstDetail.removeAll(lstNoSaleMan);
		}
		// 将有业务员的明细拆单
		if (lstDetail.size() > 0) {
			Map<String, PurconfirmListVo> purcmap = Tx.converList(lstDetail).withKeyName("saleMan")
					.toMap(PurconfirmListVo.class);
			Iterator<Entry<String, PurconfirmListVo>> iter = purcmap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, PurconfirmListVo> entry = iter.next();
				String key = entry.getKey(); // 业务员ID
				PurconfirmVo pc = (PurconfirmVo) purconfirm.clone();
				pc.setSaleMan(key);
				String userOrgErpCode = simpleDao.queryForObject(
						"select  o.erp_code from sys_user_org u LEFT JOIN sys_org o on u.org_id = o.id where o.erp_code is not null and user_id =?",
						String.class, key);

				pc.setProvOrgCode(userOrgErpCode);
				// List<PurconfirmListVo> lstPcDetail=
				// lstDetail.stream().filter(item->item.getApplyDeptId().equals(key)).collect(Collectors.toList());
				List<PurconfirmListVo> lstPcDetail = lstDetail.stream().filter(item -> item.getSaleMan().equals(key))
						.collect(Collectors.toList());
				pc.setLstDetail(lstPcDetail);
				String billId = this.addPurc(pc);
				res.add(billId);
			}
		}
		return res;
	}

	@Override
	public String addPurc(PurconfirmVo purconfirm) throws Exception {
		String spdCode = sysConfigUtil.getValue(Constance.SPDID);
		String billId = atomUtil.newId("purConfirmId").prefix(purconfirm.getPurchaseCompanyId()).get();// atomUtil.newValue("purConfirmId");
		purconfirm.setId(billId);
		purconfirm.setBillId(billId);
		purconfirm.setSpdCode(spdCode);
		purconfirm.setSumRow(purconfirm.getLstDetail().size());
		purconfirm.setSumAmount(new BigDecimal(0));
		purconfirm.setSumTaxAmount(new BigDecimal(0));
		purconfirm.setIsTrans(0); // 默认发失败
		int rowIndex =0;
		for (PurconfirmListVo pdetail : purconfirm.getLstDetail()) {
			//String detailId = atomUtil.newValue("purConfirmDetailId");
			//pdetail.setId(detailId);
			pdetail.setBillId(billId);
			pdetail.setPid(purconfirm.getId());
			pdetail.setRowNumber(++rowIndex);
			purconfirm.setSumAmount(pdetail.getQrAmount().add(purconfirm.getSumAmount()));
			purconfirm.setSumTaxAmount(pdetail.getQrTaxAmount().add(purconfirm.getSumTaxAmount()));
		}
		if (purconfirm.getStatus().equals(Constance.PURCONFIRM_STATUS_SUBMIT)) {
			// Msg msg = this.sendToERP(purconfirm);
			// purconfirm.setIsTrans(msg.isSuccess() ? 1 : 0);
			this.setPurchase(purconfirm);
		}
		detailDao.insertByBatch(purconfirm.getLstDetail());
		dao.insert(purconfirm);
		return billId;
	}

	@Override
	public PurconfirmVo update(PurconfirmVo purconfirm) throws Exception {
		purconfirm.setIsTrans(1);
		purconfirm.setSumRow(purconfirm.getLstDetail().size());
		purconfirm.setSumAmount(new BigDecimal(0));
		purconfirm.setSumTaxAmount(new BigDecimal(0));
		for (PurconfirmListVo pdetail : purconfirm.getLstDetail()) {
//			String detailId = atomUtil.newValue("purConfirmDetailId");
//			pdetail.setId(detailId);
			pdetail.setBillId(purconfirm.getBillId());
			pdetail.setPid(purconfirm.getId());
			purconfirm.setSumAmount(pdetail.getQrAmount().add(purconfirm.getSumAmount()));
			purconfirm.setSumTaxAmount(pdetail.getQrTaxAmount().add(purconfirm.getSumTaxAmount()));
		}
		if (purconfirm.getStatus().equals(Constance.PURCONFIRM_STATUS_SUBMIT)) {
			// Msg msg = this.sendToERP(purconfirm);
			// purconfirm.setIsTrans(msg.isSuccess() ? 1 : 0);
			this.setPurchase(purconfirm);
		}
		detailDao.delByBillid(purconfirm.getBillId());
		detailDao.insertByBatch(purconfirm.getLstDetail());
		dao.update(purconfirm);
		return getById(purconfirm.getId());
	}

	@Override
	public int updateByBatch(List<PurconfirmVo> lst) throws Exception {
		return dao.updateByBatch(lst);
	}

	@Override
	public int delete(PurconfirmVo purconfirm) throws Exception {
		return dao.delete(purconfirm);
	}

	@Override
	public List<PurconfirmVo> list(PurconfirmVo bean) throws Exception {
		return dao.list(bean);
	}

	@Override
	public QueryResult<PurconfirmVo> listByPage(QueryInfo<PurconfirmVo> queryInfo) throws Exception {

		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

	@Override
	public List<PurconfirmVo> secList(PurconfirmVo purconfirmVo) throws Exception {
		return dao.secList(purconfirmVo);
	}

	@Override
	public QueryResult<PurconfirmVo> secListByPage(QueryInfo<PurconfirmVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.secList(queryInfo.getQueryObject()));
	}

	// 确认单写ERP
	private Msg sendToERP(PurconfirmVo purc) throws Exception {
		PurconfirmJson pct = new PurconfirmJson();
		pct.setSpdCode(sysConfigUtil.getValue("sys.name"));
		pct.setPurBillId(purc.getBillId());
		if (purc.getPurType() != null && purc.getPurType() == 10) {
			pct.setPurType(PurType.ACTUAL_PURCHASE);
		} else if (purc.getPurType() != null && purc.getPurType() == 20) {
			pct.setPurType(PurType.PRE_PURCHASE);
		} else {
		}
		pct.setPurchaseCompanyId(purc.getPurchaseCompanyId());
		pct.setPurchaseCompanyErpCode(purc.getPurchaseCompanyErpCode());
		pct.setPurchaseCompanyName(purc.getPurchaseCompanyName());

		pct.setApplyOrgCode(purc.getApplyOrgCode());
		pct.setApplyOrgName(purc.getApplyOrgName());
		pct.setApplyOrgErpCode(purc.getApplyOrgErpCode());

		pct.setRecieveOrgCode(purc.getRecieveOrgCode());
		pct.setRecieveOrgErpCode(purc.getRecieveOrgErpCode());
		pct.setRecieveOrgName(purc.getRecieveOrgName());

		pct.setProvId(purc.getProvId());
		pct.setProvName(purc.getProvName());
		pct.setProvErpCode(purc.getProvErpCode());
		pct.setProvSystemId(purc.getProvSystemId());
		pct.setProvOrgCode(purc.getProvOrgCode());
		String userId = purc.getSaleMan();
		if (org.apache.commons.lang3.StringUtils.isEmpty(userId)) {
			log.info("###############################重新上传采购确认单, 业务员为空, 采购确认单: => {}" + purc);
		}
		SaleCodeInfoVo saleMan = povHosDeptInfoCodeService.getSaleCodeInfoById(userId);
		if (saleMan != null) {
			pct.setSaleDeptErpCode(saleMan.getSaleDeptErpCode());
			pct.setSaleDeptName(saleMan.getSaleDeptName());
			pct.setSalemanCode(saleMan.getSalemanCode());
			pct.setSalemanName(saleMan.getSalemanName());
		}
		List<PurconfirmListJson> lstDetail = new ArrayList<>();
		for (PurconfirmListVo purLstVo : purc.getLstDetail()) {
			PurconfirmListJson plstVo = new PurconfirmListJson();
			plstVo.setRowNumber(purLstVo.getRowNumber());
			plstVo.setPurBillId(purLstVo.getPurBillId() + "_" + purLstVo.getPurBillRownum());
			plstVo.setApplyBillId(purLstVo.getApplyBillId());
			plstVo.setHosGoodsCode(purLstVo.getHosGoodsCode());
			plstVo.setProvGoodsErpCode(purLstVo.getProvGoodsErpCode());
			plstVo.setProvGoodsErpName(purLstVo.getProvGoodsErpName());
			plstVo.setApplyDate(purLstVo.getApplyDate());
			plstVo.setGoodsGg(purLstVo.getGoodsGg());
			plstVo.setMrfsName(purLstVo.getMfrsName());// 生产商
			plstVo.setMade(purLstVo.getMade());
			plstVo.setUnit(purLstVo.getUnit());
			// plstVo.setPackeage(purLstVo.getP);//包装数
			plstVo.setProvGoodsErpPrice(purLstVo.getProvGoodsErpPrice());
			plstVo.setSendQty(purLstVo.getQty());
			plstVo.setEsArrivalDate(purLstVo.getEsArrivalDate());
			lstDetail.add(plstVo);
		}
		pct.setPurchaseList(lstDetail);
		pct.setBillTotal(lstDetail.size());
		Msg msg = erpService.sendPurchaseInfo(pct);
		log.info(msg.toString());
		return msg;
	}

	// 订单确认回写采购单状态
	private void setPurchase(PurconfirmVo purc) throws Exception {
		PurchaseVo pur = purService.getPurByid(purc.getPurBillId());// 采购单
		List<PurconfirmListVo> purcDetailLst = purc.getLstDetail();// 采购确认单明细
		Integer purStatus = Constance.PURCHASE_STATUS_ALLCONFIRM;
		for (PurchaseListVo purDetail : pur.getPurchaseList()) {
			int i = purcDetailLst.indexOf(purDetail);
			if (i >= 0) {
				PurconfirmListVo tmp = purcDetailLst.get(i);
				// purDetail.setStatus(1);
				purDetail.setStatus(Constance.PURCHASE_LIST_STATUS_CONFIRM);
				purDetail.setUnitQty(tmp.getUnitQty());
				purDetail.setQty(tmp.getQty());
			} else if (purDetail.getStatus().equals(0)) {
				purStatus = Constance.PURCHASE_STATUS_SOMECONFIRM;
			}
		}
		pur.setStatus(purStatus);
		purService.updatePurchaseStatus(pur);
		// MessageVo msgVo = new MessageVo();
		// msgVo.setTitle("您提交的采购订单状态有更新，请及时查看");
		// msgVo.setUrl(String.format("/applyMgr/basePurchase?billId=%s",purc.getPurBillId()));
		// msgVo.setMessage(String.format("%s刚刚处理了您提交的采购订单:%s。",purc.getFillterName(),purc.getPurBillId()));
		// List<String> users = new ArrayList<>();
		// users.add(pur.getFillter());
		// msgVo.setUserIds(users);
		// MessageSender.send(msgVo);
	}

	// 明细表操作
	@Override
	public boolean insertDetailByBatch(List<PurconfirmListVo> lst) throws Exception {

		return detailDao.insertByBatch(lst) > 0;
	}

	@Override
	public void delDetailById(String id) throws Exception {
		detailDao.delById(id);
	}

	@Override
	public void delDetailByBillid(String billid) throws Exception {
		detailDao.delByBillid(billid);
	}

	@Override
	public List<PurconfirmListVo> detailList(PurconfirmListVo bean) throws Exception {
		return (List<PurconfirmListVo>) detailDao.list(bean);
	}

	@Override
	public Msg purchaseAgain(String billId) throws Exception {
		PurconfirmVo vo = this.getByBillid(billId);
		Msg msg = this.sendToERP(vo);
		if (msg.isSuccess()) {
			vo.setIsTrans(msg.isSuccess() ? 1 : 0);
			dao.update(vo);
		}
		return msg;
	}

	@Override
	public List<PurconfirmVo> getAllBillsNotSend() {
		return dao.getAllBillsNotSend();
	}

	@Override
	public boolean getCanSendToJdeByProvId(String provId) throws Exception {
		String idStr = sysConfigUtil.getValue(Constance.SEDN_TO_JDE_PROVIDS_NAME);
		if(StringUtils.isNullOrEmpty(idStr)){
			return false;
		}
		String[] idArr = idStr.split(",");
		Optional<String> opProv = Arrays.stream(idArr).filter(o-> {return o.equals(provId);}).findAny();
		return opProv.isPresent();
	}

	@Override
	public String[] getCanSendToJdeByProvIds() throws Exception {
		String idStr = sysConfigUtil.getValue(Constance.SEDN_TO_JDE_PROVIDS_NAME);
		if(StringUtils.isNullOrEmpty(idStr)){
			return null;
		}
		return idStr.split(",");
	}
}
