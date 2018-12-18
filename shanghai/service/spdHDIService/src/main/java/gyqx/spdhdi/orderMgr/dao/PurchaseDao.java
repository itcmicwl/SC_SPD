package gyqx.spdhdi.orderMgr.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.orderMgr.dao.mapper.IPurchaseMapper;
import gyqx.spdhdi.orderMgr.vo.PurchaseVo;
/**
 * modify last_update_datetime at dao for oracle
 * just insert and update here
 * because sysdate() in mysql is different from which sysdate in oracle
 * English just so so
 * @date 2017年11月13日 上午11:40:21
 */
@Repository
public class PurchaseDao {
	@Resource
	IPurchaseMapper purchaseMapper;
	public List<PurchaseVo> getPurchaseList(PurchaseVo purchaseVo){
		return purchaseMapper.getPurchaseList(purchaseVo);
	}
	public List<PurchaseVo> getSecPurchaseList(PurchaseVo purchaseVo){
		return purchaseMapper.getSecPurchaseList(purchaseVo);
	}
	public PurchaseVo getPurchaseByBillId(String billId){
		return purchaseMapper.getPurchaseByBillId(billId);
	}
	public PurchaseVo getPurByid(String id){
		return purchaseMapper.getPurByid(id);
	}
	public int insertPurchase(PurchaseVo purchaseVo){
		purchaseVo.setLastUpdateDt(new Date());
		return purchaseMapper.insertPurchase(purchaseVo);
	}
	public int updatePurchase(PurchaseVo purchaseVo){
		purchaseVo.setLastUpdateDt(new Date());
		return purchaseMapper.updatePurchase(purchaseVo);
	}
	public int deletePurchase(PurchaseVo purchaseVo){
		return purchaseMapper.deletePurchase(purchaseVo);
	}
}
