package gyqx.spdherp.stockMgr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import common.utils.UtilsContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ParameterException;
import common.exception.ValidateException;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.orderMgr.dao.DistrBillDao;
import gyqx.spdhdi.orderMgr.dao.DistrBillListDao;
import gyqx.spdhdi.orderMgr.service.IDistrBillService;
import gyqx.spdhdi.orderMgr.service.IPurchaseService;
import gyqx.spdhdi.orderMgr.vo.DistrBillListVo;
import gyqx.spdhdi.orderMgr.vo.DistrBillVo;
import gyqx.spdhdi.orderMgr.vo.PurchaseListVo;
import gyqx.spdherp.applyMgr.service.IDeptBuyMainService;
import gyqx.spdherp.applyMgr.service.IDeptBuySubService;
import gyqx.spdherp.po.OutStock;
import gyqx.spdherp.stockMgr.Constance;
import gyqx.spdherp.stockMgr.dao.InStockDao;
import gyqx.spdherp.stockMgr.service.IInStockBatchService;
import gyqx.spdherp.stockMgr.service.IInStockListService;
import gyqx.spdherp.stockMgr.service.IInStockService;
import gyqx.spdherp.stockMgr.service.IInStockUniqueCodeService;
import gyqx.spdherp.stockMgr.vo.InStockBatchVo;
import gyqx.spdherp.stockMgr.vo.InStockListVo;
import gyqx.spdherp.stockMgr.vo.InStockUniqueCodeVo;
import gyqx.spdherp.stockMgr.vo.InStockVo;
import gyqx.spdherp.stockout.service.OutBill4InService;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InStockService implements IInStockService {
	private final Log logger = LogFactory.getLog(InStockService.class);
	@Resource
	private SimpleDao simpleDao;
	@Resource
	private IDeptBuyMainService deptMainService;
	@Resource
	private IDeptBuySubService deptSubService;
	@Resource
	private InStockDao  dao;
	@Resource
	private OutBill4InService o4iService;
	@Resource
	private IInStockListService detailService;
	@Resource
	private IInStockBatchService batchService;
	@Resource
	private IInStockUniqueCodeService codeService;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IDistrBillService distrService;
	@Resource
	private IPurchaseService purService;
	@Resource
	private DistrBillDao distrdao;
	@Resource
	private DistrBillListDao distrListdetailDao; // 明细表DAO
	@Resource
	private UserOnlineStateUtils userOnlineStateUtils;
	@Resource
	private UtilsContext utilsContext;
//    @Resource
//    private IHosStockpileService stockPileService;
	@Override
	public InStockVo getByBillId(String billId) throws Exception {
        InStockVo inVo =  dao.getByBillId(billId);
        InStockListVo query = new InStockListVo();
        query.setPid(inVo.getId());
        List<InStockListVo> lstDetail = detailService.list(query);
        for (InStockListVo inStockListVo : lstDetail) {
            InStockBatchVo batchQ = new InStockBatchVo();
            batchQ.setPid(inStockListVo.getId());
            batchQ.setPRowId(inStockListVo.getInBillRow());
            InStockUniqueCodeVo codeQ = new InStockUniqueCodeVo();
            codeQ.setPid(inStockListVo.getId());
            codeQ.setPRowId(inStockListVo.getInBillRow());
            inStockListVo.setLstBatch(batchService.list(batchQ));
            inStockListVo.setLstUniqueCode(codeService.list(codeQ));
        }
        inVo.setLstDetail(lstDetail);
        return inVo;
	}

	public InStockVo getById(String id) throws Exception
	{
		InStockVo inVo =  dao.getById(id);
		InStockListVo query = new InStockListVo();
		query.setPid(inVo.getId());
		List<InStockListVo> lstDetail = detailService.list(query);
		for (InStockListVo inStockListVo : lstDetail) {
			InStockBatchVo batchQ = new InStockBatchVo();
			batchQ.setPid(inStockListVo.getId());
			batchQ.setPRowId(inStockListVo.getInBillRow());
			InStockUniqueCodeVo codeQ = new InStockUniqueCodeVo();
			codeQ.setPid(inStockListVo.getId());
			codeQ.setPRowId(inStockListVo.getInBillRow());
			List<InStockBatchVo> temps =	batchService.list(batchQ);
			inStockListVo.setLstBatch(temps);
			inStockListVo.setLstUniqueCode(codeService.list(codeQ));
			if(temps.size()>0)
			inStockListVo.setInPrice(temps.get(0).getInPrice());
		}
		inVo.setLstDetail(lstDetail);
		return inVo;
	}
	
	/**
	 * 采购入库查询入库单明细
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public InStockVo getById4Instock(String id) throws Exception
	{
		InStockVo inVo =  dao.getById(id);
		InStockListVo query = new InStockListVo();
		query.setPid(inVo.getId());
		List<InStockListVo> lstDetail = detailService.list4Instock(query);
		for (InStockListVo inStockListVo : lstDetail) {
			InStockBatchVo batchQ = new InStockBatchVo();
			batchQ.setPid(inStockListVo.getId());
			batchQ.setPRowId(inStockListVo.getInBillRow());
			InStockUniqueCodeVo codeQ = new InStockUniqueCodeVo();
			codeQ.setPid(inStockListVo.getId());
			codeQ.setPRowId(inStockListVo.getInBillRow());
			List<InStockBatchVo> temps =	batchService.list(batchQ);
			inStockListVo.setLstBatch(temps);
			inStockListVo.setLstUniqueCode(codeService.list(codeQ));
			if(temps.size()>0)
			inStockListVo.setInPrice(temps.get(0).getInPrice());
		}
		inVo.setLstDetail(lstDetail);
		return inVo;
	}	

	@Transactional
	public InStockVo add(InStockVo inStock) throws Exception
	{
		this.checkBill(inStock);
		String billId = atomUtil.newId("cgInStock").prefix(inStock.getInOrgId()).get();//atomUtil.newValue("cgInStock");
		inStock.setBillId(billId);
		inStock.setId(billId);
		List<InStockListVo> lstDetail = inStock.getLstDetail();
		List<InStockBatchVo> lstBatch;
		List<InStockUniqueCodeVo> lstUnCode = new ArrayList<>();
        for (InStockListVo inStockListVo : lstDetail) {
            // 收货温度或时间不为空，且outBillId为配送单号时写配送单明细表
           /* if ((inStockListVo.getReceiveTemprature() != null
                    || inStockListVo.getReceiveDate() != null)
                    && inStock.getOutBillId().startsWith("PS")) {
                if(updateDistrBill(inStock, inStockListVo) < 1){
                    throw new ParameterException("填写收货信息失败。");
				}
            }*/
            String detailId = inStock.getInOrgId()+atomUtil.newValue("cgInStockDetail");
            inStockListVo.setId(detailId);
            inStockListVo.setPid(billId);
            inStockListVo.setBillId(inStock.getBillId());
			for (InStockUniqueCodeVo inStockUniqueCodeVo : inStockListVo.getLstUniqueCode()) {
				String unqCodeId = atomUtil.newValue("instockUnCodeId");
				inStockUniqueCodeVo.setId(unqCodeId);
				inStockUniqueCodeVo.setBillId(inStock.getBillId());
				inStockUniqueCodeVo.setPid(inStockListVo.getId());
				inStockUniqueCodeVo.setBillId(inStock.getBillId());
				inStockUniqueCodeVo.setPRowId(inStockListVo.getInBillRow());
				if(Constance.INSTOCK_KIND_SEND.equals(inStock.getInStockKind())){
					inStockUniqueCodeVo.setGoodsBatchId(inStock.getOutBillId());
				}
				inStockUniqueCodeVo.setInTime(inStock.getFillDate());
				lstUnCode.add(inStockUniqueCodeVo);
			}

        }
        lstBatch = buildBatchRecodes(inStock);
        this.writeBackStatus(inStock);              //回写各单据状态
		if(lstBatch.size() > 0){
			batchService.insertByBatch(lstBatch);
		}
		if(lstUnCode.size()>0){
			codeService.insertByBatch(lstUnCode);
		}
		detailService.insertByBatch(lstDetail);
        dao.insert(inStock);

		// ------------------------------
        // 提交直接记帐
        String userId = userOnlineStateUtils.getCurrent().getUserId();
        inStock.setAccounter(userId);
        inStock.setAuditor(userId);
        if(inStock.getStatus() == 20 && !audit(inStock)){
            throw new ParameterException("生成入库单错误。");
        }
        // ------------------------------
		return inStock;
	}

    private int updateDistrBill(InStockVo inStock, InStockListVo inStockListVo) {
        return dao.updateDistrBill(inStock, inStockListVo);
    }

    private void checkBill(InStockVo inStock) throws Exception{
		if(StringUtils.isNullOrEmpty(inStock.getInStocId())){
			ValidateException.Throw("stocId","入库库房不能为空","");
		}
		if(inStock.getLstDetail().size() == 0){
			ValidateException.Throw("lstDetail","明细行不能为空","");
		}
		for (InStockListVo inStockListVo : inStock.getLstDetail()) {
			if(inStockListVo.getInQty() == null || inStockListVo.getInQty().compareTo(new BigDecimal(0)) <= 0){
				ValidateException.Throw("hosPurQty",String.format("第%d行：%s的入库数量必须大于零",inStockListVo.getInBillRow(),inStockListVo.getGoodsName()),"");
			}
		}
	}
	public List<InStockBatchVo> buildBatchRecodes(InStockVo inStock)	{
		List<InStockListVo> lstDetail = inStock.getLstDetail();
		List<InStockBatchVo> lstBatch = new ArrayList<>();
		for (InStockListVo inStockListVo : lstDetail) {
			//if(Constance.INSTOCK_KIND_SEND.equals(inStock.getInStockKind()) || Constance.INSTOCK_KIND_ORIGINAL.equals(inStock.getInStockKind())){
              if(inStockListVo.getLstBatch() == null || inStockListVo.getLstBatch().size()==0){
                //手工入库和采购入库生成批次号（即入库单号）
                InStockBatchVo inBatchVo = new InStockBatchVo();
				String batchId = inStock.getInOrgId()+atomUtil.newValue("inStockBatchId");
				inBatchVo.setId(batchId);
				inBatchVo.setBillId(inStock.getBillId());
				inBatchVo.setPid(inStockListVo.getId());
				inBatchVo.setPRowId(inStockListVo.getInBillRow());
				inBatchVo.setProvId(inStockListVo.getProvId());
				inBatchVo.setGoodsId(inStockListVo.getGoodsId());
				//inBatchVo.setGoodsBatchId(inStockListVo.getBatchCode());
				inBatchVo.setInPrice(inStockListVo.getInPrice()==null?new BigDecimal(0):inStockListVo.getInPrice());
				inBatchVo.setQty(inStockListVo.getInQty());
				inBatchVo.setInTime(inStock.getFillDate());
				if(inStock.getInStockKind().equals(Constance.INSTOCK_KIND_SEND)){		//采购入库为配送单号
					inBatchVo.setGoodsBatchId(inStock.getOutBillId());
				}else if(inStock.getInStockKind().equals(Constance.INSTOCK_KIND_ORIGINAL)){
					inBatchVo.setGoodsBatchId(inStock.getBillId());							//手工入库为入库单号
				}
				inBatchVo.setBigBatchC(inStockListVo,inStock.getInStockType());						//大批号
				lstBatch.add(inBatchVo);
			}else {
				for (InStockBatchVo inStockBatchVo : inStockListVo.getLstBatch()) {
					String batchId =inStock.getInOrgId()+ atomUtil.newValue("inStockBatchId");
					inStockBatchVo.setId(batchId);
					inStockBatchVo.setBillId(inStock.getBillId());
					inStockBatchVo.setPid(inStockListVo.getId());
					inStockBatchVo.setPRowId(inStockListVo.getInBillRow());
					inStockBatchVo.setInTime(inStock.getFillDate());
					inStockBatchVo.setBigBatchC(inStockListVo,inStock.getInStockType());						//大批号
					lstBatch.add(inStockBatchVo);
				}
			}
		}
		return lstBatch;
	}

	private void writeBackStatus(InStockVo inStock) throws Exception{
	    switch (inStock.getInStockKind()){
            case Constance.INSTOCK_KIND_DEPTAPPLY:                          //请领采购 修改出库单的状态为6
                o4iService.updateOutBillStatus(inStock.getOutBillId(), gyqx.spdherp.stockout.Constance.OUTSTOCKBILL_STATUS_INSTOCK);
                break;
            case Constance.INSTOCK_KIND_SEND:
                this.setPurStatus(inStock);
                this.setDistrBillStatus(inStock);
                break;
            case Constance.INSTOCK_KIND_MOVE:
            case Constance.INSTOCK_KIND_RETURNS:	
                o4iService.updateOutBillStatus(inStock.getOutBillId(), gyqx.spdherp.stockout.Constance.OUTSTOCKBILL_STATUS_INSTOCK);
                break;

        }
    }
	/**
	 * 回写采购单状态
	 * @param inStock
	 * @return
	 */
	public void setPurStatus(InStockVo inStock) throws Exception{
		List<InStockListVo> lstInStockDetail = inStock.getLstDetail();
		if(!Constance.INSTOCK_KIND_SEND.equals(inStock.getInStockKind())){
			return ;
		}
		if(StringUtils.isNullOrEmpty(inStock.getOutBillId())){
			return;
		}
		//查询配送单信息
		DistrBillVo distrBill = distrService.getByBillId(inStock.getOutBillId());
		if(distrBill != null && distrBill.getDistrBillList() !=null && distrBill.getDistrBillList().size()>0){//配送单明细不为空
			Map<String,List<PurchaseListVo>> purMap = new HashMap<>();
			for (InStockListVo inStockListVo : lstInStockDetail) {//循环入库单明细
				Optional<DistrBillListVo> opDetail = distrBill.getDistrBillList().stream().filter(item->item.getBillId().equals(inStock.getOutBillId())
						&& inStockListVo.getOutBillRow() ==item.getRowNumber()).findFirst();
				if(opDetail.isPresent()){
					DistrBillListVo distrDetail = opDetail.get();
					PurchaseListVo purLstVo = new PurchaseListVo();
					purLstVo.setRowNumber(distrDetail.getPurRowNumber());
					purLstVo.setBillId(distrDetail.getPurBillId());
					purLstVo.setStatus(gyqx.spdhdi.orderMgr.Constance.PURCHASE_LIST_STATUS_RECEIVE);
					if(purMap.containsKey(distrDetail.getPurBillId())){
						purMap.get(distrDetail.getPurBillId()).add(purLstVo);
					}else{
						List<PurchaseListVo> lst = new ArrayList<>();
						lst.add(purLstVo);
						purMap.put(distrDetail.getPurBillId(),lst);
					}
				}
			}
			purService.updatePurStatus(purMap);
		}
		return ;
	}
    /**
     * 回写配送单状态
     * @param inStock
     * @return
     */
	private void setDistrBillStatus(InStockVo inStock) throws Exception{
		List<InStockListVo> lstInStockDetail = inStock.getLstDetail();
		if(!Constance.INSTOCK_KIND_SEND.equals(inStock.getInStockKind())){
			return ;
		}
		if(StringUtils.isNullOrEmpty(inStock.getOutBillId())){
			return;
		}
		// 获取配送单
		DistrBillVo distrBill = distrService.getByBillId(inStock.getOutBillId());
		distrBill.setStatus(gyqx.spdhdi.orderMgr.Constance.DISTRBILL_STATUS_ALLREC);
		for (DistrBillListVo dlst : distrBill.getDistrBillList()) {
			int i = lstInStockDetail.indexOf(dlst);
			if (i >= 0) {
				InStockListVo tmp = lstInStockDetail.get(i);
				//回写 收货温度或时间到配送单明细表
				dlst.setReceiveTemprature(tmp.getReceiveTemprature());
				dlst.setReceiveDate(tmp.getReceiveDate());
				
				BigDecimal recQty  = tmp.getInQty().multiply(dlst.getSendScale());
				dlst.setReceiveQty(dlst.getReceiveQty().add(recQty));
				int flag = dlst.getSendQty().compareTo(dlst.getReceiveQty());
				if (flag < 0) {
					ValidateException.Throw("SendQty", String.format("%s收货数量大于发货数量", dlst.getHosGoodsName()), "");
				} else if (flag == 0) {
					dlst.setStatus(gyqx.spdhdi.orderMgr.Constance.DISTRBILL_DETAIL_STATUS_ALLREC);
				} else if (flag > 0) {
					dlst.setStatus(gyqx.spdhdi.orderMgr.Constance.DISTRBILL_DETAIL_STATUS_SOMEREC);
					distrBill.setStatus(gyqx.spdhdi.orderMgr.Constance.DISTRBILL_STATUS_SOMEREC);
				}
			}
		}
		distrdao.update(distrBill);
		distrListdetailDao.updateByBatch(distrBill.getDistrBillList());
	}

    public InStockVo update(InStockVo inStock) throws Exception {
        List<InStockListVo> lstDetail = inStock.getLstDetail();
        List<InStockBatchVo> lstBatch;
        List<InStockUniqueCodeVo> lstUnCode = new ArrayList<>();
        for (InStockListVo inStockListVo : lstDetail) {
            String detailId = inStock.getInOrgId()+atomUtil.newValue("cgInStockDetail");
            inStockListVo.setId(detailId);
            inStockListVo.setPid(inStock.getId());
            inStockListVo.setBillId(inStock.getBillId());
            for (InStockUniqueCodeVo inStockUniqueCodeVo : inStockListVo.getLstUniqueCode()) {
                String unqCodeId = atomUtil.newValue("instockUnCodeId");
                inStockUniqueCodeVo.setId(unqCodeId);
                inStockUniqueCodeVo.setBillId(inStock.getBillId());
                inStockUniqueCodeVo.setPid(inStockListVo.getId());
                inStockUniqueCodeVo.setBillId(inStock.getBillId());
                inStockUniqueCodeVo.setPRowId(inStockListVo.getInBillRow());
                inStockUniqueCodeVo.setGoodsBatchId(inStock.getBillId());
                inStockUniqueCodeVo.setInTime(inStock.getFillDate());
                lstUnCode.add(inStockUniqueCodeVo);
            }
        }
        lstBatch = buildBatchRecodes(inStock);
        batchService.delByBillId(inStock.getBillId());
        codeService.delByBillId(inStock.getBillId());
        detailService.delByBillId(inStock.getBillId());
        if(lstBatch.size() > 0){
            batchService.insertByBatch(lstBatch);
        }
        if(lstUnCode.size()>0){
            codeService.insertByBatch(lstUnCode);
        }
        detailService.insertByBatch(lstDetail);
        dao.update(inStock);
        return inStock;
	}

	public void delete(String id) throws Exception {
		dao.delete(id);
	}
	

	public List<InStockVo> list(InStockVo bean) throws Exception
	{
		return dao.list(bean);
	}	
	
	public QueryResult<InStockVo> listByPage(QueryInfo<InStockVo> queryInfo) throws Exception{
		//获取当前登陆人,根据人员与供应商关系查询供应商信息
		String userId = userOnlineStateUtils.getCurrent().getUserId();
		String hosId = userOnlineStateUtils.getCurrent().getCorpId();
		String inStockind = queryInfo.getQueryObject().getInStockKind();
		queryInfo.getQueryObject().setUserId(userId);
		queryInfo.getQueryObject().setHosId(hosId);
		PageUtils.startPage(queryInfo);
		//入库类型 1：采购入库  2：请购入库
		if (org.apache.commons.lang3.StringUtils.equals(inStockind, "1")) {
			return PageUtils.endPage(dao.purchaseList(queryInfo.getQueryObject()));
		} else {
			return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
		}
	}
    @Override
    public boolean audit(InStockVo inStock) throws Exception {
    	InStockVo v=  	dao.getByBillId(inStock.getBillId());
    	inStock.getBillId();
        dao.auditAndunAudit(inStock,true);
        inStock.setVersion(inStock.getVersion()+1);
 
        List<OutStock> outStocks = simpleDao.queryByFieldName(OutStock.class, "billId",  v.getOutBillId());
        if(outStocks.size()>0){
        	 OutStock  out=	outStocks.get(0);
        	 if(!org.springframework.util.StringUtils.isEmpty(out.getSourceBillId())){
                 int state = 	 deptSubService.updateMainStateByBillId(out.getSourceBillId());

        	 }
        }

        
        return dao.charge(inStock);

    }

    @Override
    public boolean unAudit(InStockVo inStock) throws Exception {
        return dao.auditAndunAudit(inStock,false);
    }

    @Override
    public boolean charge(InStockVo inStock) throws Exception {
        return dao.charge(inStock);
    }

	@Override
	public QueryResult<Out4Settle> inBill4settle(QueryInfo<Out4Settle> queryInfo) throws Exception {
		String toJdeProvIdsStr = utilsContext.getSysConfigUtil().getValue(Constance.SEDN_TO_JDE_PROVIDS_NAME);
		Out4Settle query = queryInfo.getQueryObject();
		if(!StringUtils.isNullOrEmpty(toJdeProvIdsStr)){
			query.setToJdeProvIds(toJdeProvIdsStr.split(","));
		}
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.inBill4settle(query));
	}
}
