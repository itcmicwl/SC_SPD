package gyqx.spdherp.settleCenter.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.UtilsContext;
import common.validate.FieldMessage;
import common.validate.ValidateErrors;
import gyqx.platform.po.SysOrg;
import gyqx.spdhdi.myInfos.service.IProvHosDeptInfoCodeService;
import gyqx.spdhdi.myInfos.vo.SaleCodeInfoVo;
import gyqx.spdherp.po.InStockBatch;
import gyqx.spdherp.po.InStockList;
import gyqx.spdherp.settleCenter.dao.HosOutBalanceDao;
import gyqx.spdherp.settleCenter.dao.HosOutBalanceDetailDao;
import gyqx.spdherp.settleCenter.service.IHosOutBalanceService;
import gyqx.spdherp.settleCenter.vo.HosOutBalanceDetailVo;
import gyqx.spdherp.settleCenter.vo.HosOutBalanceVo;
import gyqx.spdherp.stockMgr.Constance;
import gyqx.spdherp.stockMgr.dao.InStockListDao;
import gyqx.spdherp.stockMgr.vo.InStockListVo;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;

@Service
public class HosOutBalanceService implements IHosOutBalanceService {
	
	@Resource
	private HosOutBalanceDao  dao;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private HosOutBalanceDetailDao detailDao;
	@Resource
	private InStockListDao inStockListDao;
	@Resource
	private IProvHosDeptInfoCodeService povHosDeptInfoCodeService;
	@Resource
	private UtilsContext utilsContext;
	@Resource
	private SimpleDao simpleDao;
	public HosOutBalanceVo getById(String id) throws Exception
	{
		return dao.getById(id);
	}
    public HosOutBalanceVo getByBillId(String billId)throws Exception
    {
        return dao.getByBillId(billId);
    }
	public List<String> createSettleBill(HosOutBalanceVo hosOutBalance)throws Exception{
		List<HosOutBalanceDetailVo> lstDetail = hosOutBalance.getLstDetail();
		List<String> res = new ArrayList<String>();// salean
		if (lstDetail.size() == 0) {
			ValidateException.Throw("HosOutBalanceDetailVo", "明细行不能为空", "");
		}
		// 将没有业务员的明细做一个订单
		List<HosOutBalanceDetailVo> lstNoSaleMan = lstDetail.stream()
				.filter(item -> StringUtils.isNullOrEmpty(item.getSaleMan())).collect(Collectors.toList());
		if (lstNoSaleMan.size() > 0) {
			HosOutBalanceVo noSaleManPc = (HosOutBalanceVo) hosOutBalance.clone();
			noSaleManPc.setLstDetail(lstNoSaleMan);
			// noSaleManPc.setProvOrgCode(phd.getErpCode());
			String billId = this.add(noSaleManPc);
			res.add(billId);
			lstDetail.removeAll(lstNoSaleMan);
		}
		// 将有业务员的明细拆单
		if (lstDetail.size() > 0) {
			Map<String, HosOutBalanceDetailVo> purcmap = Tx.converList(lstDetail).withKeyName("saleMan")
					.toMap(HosOutBalanceDetailVo.class);
			Iterator<Map.Entry<String, HosOutBalanceDetailVo>> iter = purcmap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, HosOutBalanceDetailVo> entry = iter.next();
				String key = entry.getKey(); // 业务员ID
				HosOutBalanceVo pc = (HosOutBalanceVo) hosOutBalance.clone();
				pc.setSaleMan(key);
				String userOrgErpCode = simpleDao.queryForObject(
						"select  o.erp_code from sys_user_org u LEFT JOIN sys_org o on u.org_id = o.id where o.erp_code is not null and u.user_id =?",
						String.class, key);

				pc.setProvDeptErpCode(userOrgErpCode);
				List<HosOutBalanceDetailVo> lstPcDetail = lstDetail.stream().filter(item -> item.getSaleMan().equals(key))
						.collect(Collectors.toList());
				pc.setLstDetail(lstPcDetail);
				String billId = this.add(pc);
				res.add(billId);
			}
		}
		return res;

	}
    public String add(HosOutBalanceVo hosOutBalance) throws Exception	{
       // String spdCode = utilsContext.getSysConfigUtil().getValue(gyqx.spdhdi.orderMgr.Constance.SPDID);
		String id = atomUtil.newId("outBalanceId").prefix(hosOutBalance.getHosId()).get();//atomUtil.newValue("outBalanceId");
		hosOutBalance.setId(id);
		hosOutBalance.setBillId(id);
		SaleCodeInfoVo sciVo = new SaleCodeInfoVo();
		sciVo.setHosDeptId(utilsContext.getUserStateUtils().getCurrent().getOrgId());
		sciVo.setHosId(hosOutBalance.getHosId());
		sciVo.setProvId(hosOutBalance.getProvId());
		int rowIndex = 0;
		for (HosOutBalanceDetailVo hosOutBalanceDetailVo : hosOutBalance.getLstDetail()) {
			String did = hosOutBalance.getHosId()+atomUtil.newValue("outBalanceDetailId");
			hosOutBalanceDetailVo.setId(did);
			hosOutBalanceDetailVo.setBillId(hosOutBalance.getBillId());
			hosOutBalanceDetailVo.setPid(hosOutBalance.getId());
			hosOutBalanceDetailVo.setRowNum(++rowIndex);
			hosOutBalanceDetailVo.setSettleAmount(hosOutBalanceDetailVo.getPrice().multiply(hosOutBalanceDetailVo.getHosUnitQty()));
			hosOutBalance.setSettleAmount(hosOutBalance.getSettleAmount().add(hosOutBalanceDetailVo.getSettleAmount()));
		}
		if(Constance.SETTLE_MAIN_STATUS_SUBMIT.equals(hosOutBalance.getStatus())){
			this.setOutBillStatus_new(hosOutBalance);
		}
	   detailDao.insertByBatch(hosOutBalance.getLstDetail());
	   dao.insert(hosOutBalance);
		return id;//return this.getById(id);
	}
		
	public HosOutBalanceVo update(HosOutBalanceVo hosOutBalance) throws Exception {
        for (HosOutBalanceDetailVo hosOutBalanceDetailVo : hosOutBalance.getLstDetail()) {
            String did = atomUtil.newValue("outBalanceDetailId");
            hosOutBalanceDetailVo.setId(did);
            hosOutBalanceDetailVo.setBillId(hosOutBalance.getBillId());
            hosOutBalanceDetailVo.setPid(hosOutBalance.getId());
			hosOutBalanceDetailVo.setSettleAmount(hosOutBalanceDetailVo.getPrice().multiply(hosOutBalanceDetailVo.getHosUnitQty()));
			hosOutBalance.setSettleAmount(hosOutBalance.getSettleAmount().add(hosOutBalanceDetailVo.getSettleAmount()));
        }
		if(Constance.SETTLE_MAIN_STATUS_SUBMIT.equals(hosOutBalance.getStatus())){
			this.setOutBillStatus_new(hosOutBalance);
		}
        detailDao.delByBillId(hosOutBalance.getBillId());
        detailDao.insertByBatch(hosOutBalance.getLstDetail());
		dao.update(hosOutBalance);

		return this.getById(hosOutBalance.getId());
	}


	/**
	 * 修改入库单表中状态（原先是出库单表）
 	 * @param hosOutBalance
	 * @return
	 * @throws Exception
	 */
	private int setOutBillStatus(HosOutBalanceVo hosOutBalance) throws Exception {
		int res = 0;  
		List<InStockListVo> lstOutDetail = new ArrayList<>();    
		for (HosOutBalanceDetailVo hosOutBalanceDetailVo : hosOutBalance.getLstDetail()) {
			InStockListVo osl = new InStockListVo();
			osl.setPid(hosOutBalanceDetailVo.getOutBillid());
			osl.setBillId(hosOutBalanceDetailVo.getOutBillid());
			osl.setInBillRow(hosOutBalanceDetailVo.getOutRowNum());
			osl.setState(Constance.IN_STOCK_LIST_STATUS_SETTLE);
			lstOutDetail.add(osl);
		}
		if(lstOutDetail.size()>0){
			List<InStockListVo> unCheckedLst = inStockListDao.getSettledListByBillIdAndRow(lstOutDetail);
			if(unCheckedLst.size()>0){
				ValidateErrors ve = new ValidateErrors();
				List<FieldMessage> fms = new ArrayList<>();
				for(InStockListVo item : unCheckedLst){
					FieldMessage fm = new FieldMessage();
					fm.setMessage(String.format("%s 已经结算过了",item.getGoodsName()));
					fms.add(fm);
				};
				ve.setFieldErrors(fms);
				throw new ValidateException(ve);
			}
			res = inStockListDao.updateInBillLstStatus(lstOutDetail);
		}
		return res ;
	}
	private int setOutBillStatus_new(HosOutBalanceVo hosOutBalance) throws Exception {
		int res = 0;
		for (HosOutBalanceDetailVo v : hosOutBalance.getLstDetail()) {
			// 获取批次表信息，比较 系统settleQty+此次结算的数量 与qty的关系
			InStockBatch batch=   (InStockBatch) simpleDao.queryByFieldNames(InStockBatch.class, "billId,pRowId,goodsId,goodsBatchId", 
					                                                        v.getOutBillid(),v.getOutRowNum(),v.getHosGoodsId(),v.getBatchId()).get(0);
			if(batch.getSettleQty()==null){
				batch.setSettleQty(new BigDecimal(0));
			}
			// 将批次表的 数量增加 
			simpleDao.executeSql("update in_stock_batch set settle_qty =?, version =version+1,last_update_datetime=SYSDATE() where  bill_id =? and p_row_id =? and goods_id =? and goods_batch_id=?",
					batch.getSettleQty().add(v.getSettleQty()),v.getOutBillid(),v.getOutRowNum(),v.getHosGoodsId(),v.getBatchId());
			
			//查询 明细表
			InStockList detail=   (InStockList) simpleDao.queryByFieldNames(InStockList.class, "billId,inBillRow,goodsId", 
                    v.getOutBillid(),v.getOutRowNum(),v.getHosGoodsId()).get(0);
			if(detail.getSettleQty()==null){
				detail.setSettleQty(new BigDecimal(0));
			}
			
			 detail.setState(Constance.IN_STOCK_LIST_STATUS_PART_SETTLE);
			if(detail.getInQty().compareTo(detail.getSettleQty().add(v.getSettleQty()))==0){
				 detail.setState(Constance.IN_STOCK_LIST_STATUS_SETTLE);
			}
			
			simpleDao.executeSql("update in_stock_list set settle_qty =?,state =?,  version =version+1,last_update_datetime=SYSDATE() where  bill_id =? and in_bill_row =? and goods_id =? ",
					detail.getSettleQty().add(v.getSettleQty()), detail.getState(),   v.getOutBillid(),v.getOutRowNum(),v.getHosGoodsId());
		}
	
		return res ;
	}
	
	public int delById(String id) throws Exception {
		return dao.delById(id);
	}

	public List<HosOutBalanceVo> list(HosOutBalanceVo bean) throws Exception
	{
		return dao.list(bean);
	}	
	
	public QueryResult<HosOutBalanceVo> listByPage(QueryInfo<HosOutBalanceVo> queryInfo) throws Exception{
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}
	
	@Override
	public List<SysOrg> getSettleSubProvList(QueryInfo<Out4Settle> queryInfo) throws Exception {
		String toJdeProvIdsStr = utilsContext.getSysConfigUtil().getValue(Constance.SEDN_TO_JDE_PROVIDS_NAME);
		Out4Settle query = queryInfo.getQueryObject();
		if(!StringUtils.isNullOrEmpty(toJdeProvIdsStr)){
			query.setToJdeProvIds(toJdeProvIdsStr.split(","));
		}
		return dao.getSettleSubProvList(queryInfo.getQueryObject());
	}
	public QueryResult<HosOutBalanceDetailVo> getExportData(QueryInfo<HosOutBalanceVo> queryInfo) throws Exception{
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.getExportData(queryInfo.getQueryObject()));
	}
}


