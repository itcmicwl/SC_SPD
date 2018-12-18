package gyqx.spdherp.stockout.service.impl;

import common.db.SimpleDao;
import common.exception.ParameterException;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;
import gyqx.spdherp.applyMgr.service.IDeptBuySubService;
import gyqx.spdherp.po.DeptBuySub;
import gyqx.spdherp.po.HosTakingStock;
import gyqx.spdherp.po.OutStock;
import gyqx.spdherp.po.OutStockList;
import gyqx.spdherp.stockout.dao.BillMgrDao;
import gyqx.spdherp.stockout.service.BillMgrService;
import gyqx.spdherp.stockout.vo.OutStockBillVo;
import gyqx.spdherp.stockout.vo.SysOrgVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Auther Liangwu
 * @Date 17-8-29 上午11:16
 */
@Service
public class BillMgrServiceImpl implements BillMgrService {
    @Resource
    private UtilsContext utilsContext;

    @Resource
    private BillMgrDao billMgrDao;

    @Resource
    private SimpleDao simpleDao;

    @Resource
    private IDeptBuySubService deptBuySubService;

    @Override
    public List<SysOrgVo> listSysOrg(String stockId, Boolean submitted) {
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        return billMgrDao.listSysOrgByCorpId(user.getCorpId(), stockId, user.getOrgId(), user.getUserId(), submitted);
    }

    @Override
    public List<OutStockBillVo> listBill(String deptId, String stockId, Date beginDate, Date endDate, String status) {
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        return billMgrDao.listBill(user.getCorpId(), deptId, stockId, beginDate, endDate, status, user.getUserId());
    }

    @Override
    public OutStockBillVo getBillDetail(String id) {
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        return billMgrDao.getBillDetail(id, user.getUserId());
    }

    @Override
    @Transactional
    public Integer updateOutStock(OutStock outStock) throws Exception {
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();

        switch (outStock.getStatus()) {
            case 10: // 提交
                outStock.setFiller(user.getUserId());
                outStock.setFillDate(new Date());
//                writeHosTakingStock(outStock.getBsId());
                break;
            case 20: // 审核
                outStock.setAuditor(user.getUserId());
                outStock.setAuditDate(new Date());
                break;
            case 30: // 记帐
                outStock.setAccounter(user.getUserId());
                outStock.setAccountDate(new Date());
                if (Objects.equals(outStock.getOutStockKind(), gyqx.spdherp.stockout.Constance.OUTSTOCK_KIND_REQUEST.toString())) { // 请购出库回写状态
                    writeDeptBuySub(outStock.getId(), false);
                }
                // 商品正式出库
                // 调用存储过程
                if (!billMgrDao.stockpilebatchOutstock(outStock)) {
                    throw new ParameterException("记帐失败！", -1);
                }
                removeTakingInfo(outStock.getId());
                break;
            case 40: // 作废
                // 恢复库存
            case 50: // 驳回
                // 恢复库存
                removeTakingInfo(outStock.getId());
                removeOutStockListQty(outStock.getId());
                break;
            default:
                break;
        }

        outStock.setLastUpdateDatetime(new Date());
        simpleDao.updateNotNullFields(outStock);
        return outStock.getVersion()+1;
    }

    private void removeOutStockListQty(String billId) {
        simpleDao.executeSql("update out_stock_list set out_qty = 0, last_update_datetime = NOW(), version = version +1 where pid = ?", billId);
    }

    private void removeTakingInfo(String billId) {
        simpleDao.executeSql("delete from hos_taking_stock where bill_id=?", billId);
    }

    private void writeHosTakingStock(String id) throws Exception {
        OutStock outStock = simpleDao.get(OutStock.class, id);
        List<OutStockList> outStockLists = simpleDao.queryByFieldName(OutStockList.class, "pid", id);

        for (OutStockList outStockSub : outStockLists) {
            HosTakingStock hosTakingStock = new HosTakingStock();
            hosTakingStock.setId(utilsContext.getSysAtomUtil().newValue("hos_taking_stock_id"));
            hosTakingStock.setHosId(outStock.getOutOrgId());
            hosTakingStock.setStocId(outStock.getOutStocId());
            hosTakingStock.setHosGoodsId(outStockSub.getGoodsId());
            hosTakingStock.setKind(outStock.getOutStockKind());
            hosTakingStock.setBillId(outStock.getBillId());
            hosTakingStock.setQty(outStockSub.getOutQty());
            hosTakingStock.setBillRownum(outStockSub.getOutBillRow());
            hosTakingStock.setVersion(0);
            simpleDao.insert(hosTakingStock);
        }
    }

    /**
     * 回写请购单状态
     * @param id 出库单主表ID
     * @param isRemove true，出库单撤销或其它原因，请购单子表已发送数量减少；false，出库后请购单子表的已发送数量增加
     * @throws Exception simpleDao Exception
     */
    // TODO: 未考虑出库目标科室是否为请购科室
    private void writeDeptBuySub(String id, boolean isRemove) throws Exception {
        List<OutStockList> outStockLists = simpleDao.queryByFieldName(OutStockList.class, "pid", id);

        for (OutStockList outStockSub : outStockLists) {
            DeptBuySub deptBuySub = simpleDao.get(DeptBuySub.class, outStockSub.getSourceBillId());
            BigDecimal sum = deptBuySub.getSendQty() == null ? new BigDecimal(0.0) : deptBuySub.getSendQty();
            sum = isRemove ? sum.subtract(outStockSub.getOutQty()) : sum.add(outStockSub.getOutQty());
            if (sum.compareTo(deptBuySub.getQty()) > 0) {
                throw new ParameterException("出库数量不能大于请购数量！", -1);
            } else if (sum.doubleValue() == 0) {
                deptBuySub.setSubState(gyqx.spdherp.applyMgr.Constance.DEPTAPPLAY_SUB_STATUS_PENDING); // TODO: 回写10是否正确，未考虑30不采购状态
            } else if (sum.compareTo(deptBuySub.getQty()) == 0) {
                deptBuySub.setSubState(gyqx.spdherp.applyMgr.Constance.DEPTAPPLAY_SUB_STATUS_ALLDISTRED); // 全部配送
            } else {
                deptBuySub.setSubState(gyqx.spdherp.applyMgr.Constance.DEPTAPPLAY_SUB_STATUS_PARTDISTRED); // 部分配送
            }
            deptBuySub.setSendQty(sum);
            simpleDao.updateNotNullFields(deptBuySub);
//            deptBuySub.setVersion(deptBuySub.getVersion() + 1);
//            deptBuySubService.updateBill4OutStock(deptBuySub);
        }
    }
}
