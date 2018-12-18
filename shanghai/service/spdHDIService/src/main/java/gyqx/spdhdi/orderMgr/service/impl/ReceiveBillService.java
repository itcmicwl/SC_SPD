package gyqx.spdhdi.orderMgr.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import gyqx.spdhdi.orderMgr.Constance;
import gyqx.spdhdi.orderMgr.dao.DistrBillDao;
import gyqx.spdhdi.orderMgr.dao.DistrBillListDao;
import gyqx.spdhdi.orderMgr.dao.ReceiveBillDao;
import gyqx.spdhdi.orderMgr.dao.ReceiveBillHumitureListDao;
import gyqx.spdhdi.orderMgr.dao.ReceiveBillListDao;
import gyqx.spdhdi.orderMgr.dao.ReceiveBillUniqueListDao;
import gyqx.spdhdi.orderMgr.service.IDistrBillService;
import gyqx.spdhdi.orderMgr.service.IReceiveBillService;
import gyqx.spdhdi.orderMgr.vo.DistrBillListVo;
import gyqx.spdhdi.orderMgr.vo.DistrBillVo;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillHumitureListVo;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillUniqueListVo;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillVo;

@Service
public class ReceiveBillService implements IReceiveBillService {

	@Resource
	private ReceiveBillDao dao;
	@Resource
	private ReceiveBillListDao detailDao;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IDistrBillService distrService;
	@Resource
	private DistrBillDao distrdao;
	@Resource
	private DistrBillListDao distrListdetailDao; // 明细表DAO
	@Resource 
	private ReceiveBillUniqueListDao recUniqueDao;
	@Resource 
	private ReceiveBillHumitureListDao recHumitureDao;
	@Override
	public ReceiveBillVo getById(String id) throws Exception {
		ReceiveBillVo db = dao.getById(id);
		if (db != null) {
			ReceiveBillListVo dblVo = new ReceiveBillListVo();
			dblVo.setPid(db.getId());
			List<ReceiveBillListVo> lstDetail = detailDao.list(dblVo);
			if(lstDetail.size()>0){
				for(ReceiveBillListVo dbDetail : lstDetail){
					ReceiveBillHumitureListVo dbhv = new ReceiveBillHumitureListVo();
					dbhv.setPid(db.getId());
					dbhv.setDistrRowNumber(dbDetail.getRowNumber());
					List<ReceiveBillHumitureListVo> lstDbhv = recHumitureDao.list(dbhv);
					dbDetail.setLstHumitureList(lstDbhv);
					
					ReceiveBillUniqueListVo dbuv = new ReceiveBillUniqueListVo();
					dbuv.setPid(db.getId());
					dbuv.setRowNumber(dbDetail.getRowNumber());
					List<ReceiveBillUniqueListVo> lstDbuv = recUniqueDao.list(dbuv); 
					dbDetail.setLstUniqueList(lstDbuv);
				}
			}
			db.setLstDetail(lstDetail);
		}
		return db;
	}

	@Override
	public ReceiveBillVo getByBillId(String billId) throws Exception {
		return dao.getByBillId(billId);
	}

	@Override
	public ReceiveBillVo add(ReceiveBillVo receiveBill) throws Exception {
		String billId = atomUtil.newId("purcharseID").prefix(receiveBill.getPurchaseCompanyId()).get();//atomUtil.newValue("recBillId");
		receiveBill.setId(billId);
		receiveBill.setBillId(billId);
		List<ReceiveBillHumitureListVo> lstRecHumiture = new ArrayList<ReceiveBillHumitureListVo>();
		List<ReceiveBillUniqueListVo> lstRecUnique = new ArrayList<ReceiveBillUniqueListVo>();
		for (ReceiveBillListVo rdetail : receiveBill.getLstDetail()) {
			String detailId = atomUtil.newValue("purConfirmDetailId");
			rdetail.setId(detailId);
			rdetail.setBillId(billId);
			rdetail.setPid(receiveBill.getId());
			for (ReceiveBillHumitureListVo dbhv : rdetail.getLstHumitureList()) {
				dbhv.setId(atomUtil.newValue("distrBHumiture"));
				dbhv.setPid(receiveBill.getId());
				dbhv.setDistrRowNumber(rdetail.getRowNumber());
				lstRecHumiture.add(dbhv);
			}
			for (ReceiveBillUniqueListVo dbuv : rdetail.getLstUniqueList()) {
				dbuv.setId(atomUtil.newValue("distrBUniqueCode"));
				dbuv.setPid(receiveBill.getId());
				dbuv.setRowNumber(rdetail.getRowNumber());
				lstRecUnique.add(dbuv);
			}
		}
		if (receiveBill.getStatus().equals(Constance.RECEIVEBILL_STATUS_SUBMIT)) {
			 this.setDistrBill(receiveBill); //回写配送单
		}
		if(!lstRecHumiture.isEmpty()){
			recHumitureDao.insertBatch(lstRecHumiture);
		}
		if(!lstRecUnique.isEmpty()){
			recUniqueDao.insertBatch(lstRecUnique);
		}		
		detailDao.insertBatch(receiveBill.getLstDetail());
		dao.insert(receiveBill);
		return receiveBill;//getById(receiveBill.getId());
	}

	@Override
	public ReceiveBillVo update(ReceiveBillVo receiveBill) throws Exception {
		for (ReceiveBillListVo pdetail : receiveBill.getLstDetail()) {
			String detailId = atomUtil.newValue("purConfirmDetailId");
			pdetail.setId(detailId);
			pdetail.setBillId(receiveBill.getBillId());
			pdetail.setPid(receiveBill.getId());
		}
		if (receiveBill.getStatus().equals(Constance.RECEIVEBILL_STATUS_SUBMIT)) {
			 this.setDistrBill(receiveBill); //回写配送单
		}
		detailDao.deleteByBillId(receiveBill.getBillId());
		detailDao.insertBatch(receiveBill.getLstDetail());
		dao.update(receiveBill);
		return getById(receiveBill.getId());
	}

	// 回写配送单状态
	private void setDistrBill(ReceiveBillVo recBill) throws Exception {
		List<ReceiveBillListVo> rDLst = recBill.getLstDetail();
		DistrBillVo distrBill = distrService.getByBillId(recBill.getDistrBillId());// 采购单
		distrBill.setStatus(Constance.DISTRBILL_STATUS_ALLREC);
		for (DistrBillListVo dlst : distrBill.getDistrBillList()) {
			int i = rDLst.indexOf(dlst);
			if (i >= 0) {
				ReceiveBillListVo tmp = rDLst.get(i);
				BigDecimal recQty  = tmp.getQty().multiply(dlst.getSendScale());
				dlst.setReceiveQty(dlst.getReceiveQty().add(recQty));
				int flag = dlst.getSendQty().compareTo(dlst.getReceiveQty());
				if (flag < 0) {
					ValidateException.Throw("SendQty", String.format("%s收货数量大于发货数量", dlst.getHosGoodsName()), "");
				} else if (flag == 0) {
					dlst.setStatus(Constance.DISTRBILL_DETAIL_STATUS_ALLREC);
				} else if (flag == 1) {
					dlst.setStatus(Constance.DISTRBILL_DETAIL_STATUS_SOMEREC);
					distrBill.setStatus(Constance.DISTRBILL_STATUS_SOMEREC);
				}
			}
		}
		distrdao.update(distrBill);
		distrListdetailDao.updateByBatch(distrBill.getDistrBillList());
//		List<PurconfirmListVo> purcDetailLst = purc.getLstDetail();// 采购确认单明细
	}

	@Override
	public List<ReceiveBillVo> list(ReceiveBillVo bean) throws Exception {
		return dao.list(bean);
	}

	@Override
	public QueryResult<ReceiveBillVo> listByPageVo(QueryInfo<ReceiveBillVo> queryInfo) throws Exception {

		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

}
