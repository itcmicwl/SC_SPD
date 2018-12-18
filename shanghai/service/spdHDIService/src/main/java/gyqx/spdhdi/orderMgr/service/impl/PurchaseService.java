package gyqx.spdhdi.orderMgr.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mysql.jdbc.StringUtils;
import common.exception.ValidateException;
import gyqx.spdhdi.orderMgr.Constance;
import org.springframework.stereotype.Service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import gyqx.spdhdi.orderMgr.dao.PurchaseDao;
import gyqx.spdhdi.orderMgr.dao.PurchaseListDao;
import gyqx.spdhdi.orderMgr.service.IPurchaseService;
import gyqx.spdhdi.orderMgr.vo.PurchaseListVo;
import gyqx.spdhdi.orderMgr.vo.PurchaseVo;

@Service
public class PurchaseService implements IPurchaseService {
	@Resource
	PurchaseDao purchaseDao;
	@Resource
	PurchaseListDao purchaseListDao;
	@Resource
	private SysAtomUtil atomUtil;

	@Override
	public QueryResult<PurchaseVo> getPurchaseList(QueryInfo<PurchaseVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(purchaseDao.getPurchaseList(queryInfo.getQueryObject()));
	}

	@Override
	public QueryResult<PurchaseVo> getSecPurchaseList(QueryInfo<PurchaseVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(purchaseDao.getSecPurchaseList(queryInfo.getQueryObject()));
	}

	@Override
	public PurchaseVo getPurchaseByBillId(String billId) throws Exception{
		PurchaseVo p =  purchaseDao.getPurchaseByBillId(billId);
		if(p== null){
			return null;
		}
		PurchaseListVo pd= new PurchaseListVo();
		pd.setBillId(billId);
		List<PurchaseListVo> pdlst = purchaseListDao.getPurchaseList(pd);
		if(pdlst != null){
			p.setPurchaseList(pdlst);
		}
		return p;
	}

	@Override
	public PurchaseVo insertPurchase(PurchaseVo purchaseVo) throws Exception{
		this.checkPurchase(purchaseVo);
		String billId =atomUtil.newId("purcharseID").prefix(purchaseVo.getPurchaseComId()).get(); //atomUtil.newValue("purcharseID");
		purchaseVo.setId(billId);
		purchaseVo.setBillId(billId);
		for(PurchaseListVo pdetail : purchaseVo.getPurchaseList()){
			if(StringUtils.isNullOrEmpty(pdetail.getProvId())){
				ValidateException.Throw("provId",String.format("第%d行：%s商品未与供应商相关联",pdetail.getRowNumber(),pdetail.getHosGoodsName()),"");
			}
			String detailId = atomUtil.newId("purcharseListID").prefix(purchaseVo.getPurchaseComId()).get();//atomUtil.newValue("purcharseListID");
			pdetail.setId(detailId);
			pdetail.setBillId(billId);
			pdetail.setPid(purchaseVo.getId());
		}
		purchaseListDao.insertPurchaseListByBatch(purchaseVo.getPurchaseList());
		purchaseDao.insertPurchase(purchaseVo);
		return purchaseVo;
	}

	@Override
	public PurchaseVo updatePurchase(PurchaseVo purchaseVo) throws Exception{
		this.checkPurchase(purchaseVo);
		purchaseDao.updatePurchase(purchaseVo);
		//purchaseListDao.updatePurchaseListByBatch(purchaseVo.getPurchaseList());
		purchaseListDao.deletePurchaseListByBillId(purchaseVo.getBillId());
		for(PurchaseListVo pdetail : purchaseVo.getPurchaseList()){
			String detailId =  atomUtil.newId("purcharseListID").prefix(purchaseVo.getPurchaseComId()).get();
			pdetail.setId(detailId);
			pdetail.setBillId(purchaseVo.getBillId());
			pdetail.setPid(purchaseVo.getId());
		}
		purchaseListDao.insertPurchaseListByBatch(purchaseVo.getPurchaseList());
		return purchaseVo;
	}

	private void checkPurchase(PurchaseVo p) throws Exception{
		if(p.getPurchaseList().size() == 0){
			ValidateException.Throw("purchaseList","明细行不能为空","");
		}
		for (PurchaseListVo purchaseListVo : p.getPurchaseList()) {
			if(purchaseListVo.getHosPurQty() == null || purchaseListVo.getHosPurQty().compareTo(new BigDecimal(0)) <= 0){
				ValidateException.Throw("hosPurQty",String.format("第%d行：%s的采购数量必须大于零",purchaseListVo.getRowNumber(),purchaseListVo.getHosGoodsName()),"");
			}
			if(StringUtils.isNullOrEmpty(purchaseListVo.getProvId())){
				ValidateException.Throw("hosPurQty",String.format("第%d行：%s的供应商不能为空",purchaseListVo.getRowNumber(),purchaseListVo.getHosGoodsName()),"");
			}
		}
	}
	@Override
	public int deletePurchase(PurchaseVo purchaseVo) throws Exception{
		purchaseListDao.deletePurchaseListByBillId(purchaseVo.getBillId());
		return purchaseDao.deletePurchase(purchaseVo);
	}

	@Override
	public PurchaseVo getPurByid(String id) throws Exception {
		PurchaseVo p =  purchaseDao.getPurByid(id);
		PurchaseListVo pd= new PurchaseListVo();
		pd.setPid(id);
		List<PurchaseListVo> pdlst = purchaseListDao.getPurchaseList(pd);
		if(pdlst != null){
			p.setPurchaseList(pdlst);
		}
		return p;
	}
	@Override
	public PurchaseVo updatePurchaseStatus(PurchaseVo purchaseVo) throws Exception{
		purchaseDao.updatePurchase(purchaseVo);
		purchaseListDao.UpdatePurchaseListByBatch(purchaseVo.getPurchaseList());		
		return purchaseVo;
	}

	@Override
	public int updatePurStatus(Map<String, List<PurchaseListVo>> map) throws Exception {
		Iterator<Map.Entry<String, List<PurchaseListVo>>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, List<PurchaseListVo>> entry = entries.next();
			PurchaseVo purBill = this.getPurchaseByBillId(entry.getKey());
			if(purBill == null){
				continue;
			}
			for (PurchaseListVo purchaseListVo : purBill.getPurchaseList()) {
				Integer index = entry.getValue().indexOf(purchaseListVo);
				if(index>=0){
					Integer subStatus = entry.getValue().get(index).getStatus();
					purchaseListVo.setStatus(subStatus);
				}
			}
			purBill.setStatus(getPurStateByLst(purBill.getPurchaseList()));
			this.updatePurchaseStatus(purBill);
		}
		return 0;
	}
	private Integer getPurStateByLst(List<PurchaseListVo> lst){
		Integer sStateC=0,sStateD=0;
		for (PurchaseListVo purchaseListVo : lst) {
			if(purchaseListVo.getStatus().equals(Constance.PURCHASE_LIST_STATUS_CONFIRM)){
				sStateC++;
			}else if(purchaseListVo.getStatus().equals(Constance.PURCHASE_LIST_STATUS_RECEIVE)){
				sStateD++;
			}
		}
		if(sStateD.equals(lst.size())){
			return Constance.PURCHASE_STATUS_ALLREC;
		}
		if(sStateC.equals(lst.size())){
			return Constance.PURCHASE_STATUS_ALLCONFIRM;
		}
		if(sStateD>0){
			return Constance.PURCHASE_STATUS_SOMEREC;
		}
		if(sStateC>0){
			return Constance.PURCHASE_STATUS_SOMECONFIRM;
		}
		return Constance.PURCHASE_STATUS_ERR;
	}
}