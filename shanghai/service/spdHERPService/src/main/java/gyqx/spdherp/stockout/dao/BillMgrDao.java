package gyqx.spdherp.stockout.dao;

import gyqx.spdherp.po.OutStock;
import gyqx.spdherp.stockout.dao.mapper.BillMgrMapper;
import gyqx.spdherp.stockout.vo.OutStockBillVo;
import gyqx.spdherp.stockout.vo.SysOrgVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-8-29 上午11:17
 */
@Repository
public class BillMgrDao {
    @Resource
    private BillMgrMapper mapper;

    public List<SysOrgVo> listSysOrgByCorpId(String corpId, String stockId, String deptId, String userId, Boolean submitted) {
        return mapper.listSysOrgByCorpId(corpId, stockId, deptId, userId, submitted);
    }

    public List<OutStockBillVo> listBill(String corpId, String deptId, String stockId, Date beginDate, Date endDate, String status, String userId) {
        return mapper.listBill(corpId, deptId, stockId, beginDate, endDate, status, userId);
    }

    public OutStockBillVo getBillDetail(String id, String userId) {
        return mapper.getBillDetail(id, userId);
    }

    public boolean stockpilebatchOutstock(OutStock outStock) {
        return mapper.stockpilebatchOutstock(outStock)>=0;
    }

/*    public Integer updateOutStockBillStatus(OutStock outStock) {
        mapper.updateOutStockBillStatus(outStock);
        return outStock.getVersion() + 1;
    }*/
}
