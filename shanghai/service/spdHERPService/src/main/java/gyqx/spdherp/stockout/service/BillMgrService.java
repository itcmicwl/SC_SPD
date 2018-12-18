package gyqx.spdherp.stockout.service;

import gyqx.spdherp.po.OutStock;
import gyqx.spdherp.stockout.vo.OutStockBillVo;
import gyqx.spdherp.stockout.vo.SysOrgVo;

import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-8-29 上午11:16
 */
public interface BillMgrService {
    List<SysOrgVo> listSysOrg(String stockId, Boolean submitted);

    List<OutStockBillVo> listBill(String deptId, String stockId, Date beginDate, Date endDate, String status);

    OutStockBillVo getBillDetail(String id);

    Integer updateOutStock(OutStock outStock) throws Exception;
}
