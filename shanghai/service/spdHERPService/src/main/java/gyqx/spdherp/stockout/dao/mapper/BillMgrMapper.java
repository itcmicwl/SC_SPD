package gyqx.spdherp.stockout.dao.mapper;

import gyqx.spdherp.po.OutStock;
import gyqx.spdherp.stockout.vo.OutStockBillVo;
import gyqx.spdherp.stockout.vo.SysOrgVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-8-29 上午11:17
 */
public interface BillMgrMapper {
    List<SysOrgVo> listSysOrgByCorpId(@Param("corpId") String corpId,
                                      @Param("stockId") String stockId,
                                      @Param("deptId") String deptId,
                                      @Param("userId") String userId,
                                      @Param("submitted") Boolean submitted);

    List<OutStockBillVo> listBill(@Param("corpId") String corpId,
                                  @Param("deptId") String deptId,
                                  @Param("stockId") String stockId,
                                  @Param("beginDate") Date beginDate,
                                  @Param("endDate") Date endDate,
                                  @Param("status") String status,
                                  @Param("userId") String userId);

    OutStockBillVo getBillDetail(@Param("id") String id, @Param("userId") String userId);

    Integer stockpilebatchOutstock(OutStock outStock);

//    void updateOutStockBillStatus(OutStock outStock);
}
