package gyqx.spdherp.stockout.dao;


import gyqx.spdherp.po.OutStock;
import gyqx.spdherp.stockout.dao.mapper.RequestOutMapper;
import gyqx.spdherp.stockout.vo.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-8-16 上午10:30
 */
@Repository
public class RequestOutDao {
    @Resource
    private RequestOutMapper mapper;

    /**
     * 获取医院请领部门列表
     * @param corpId 当前医院ID
     * @param stockId 当前仓库ID
     * @param deptId 用户部门ID，用于获取请领关系
     * @return
     */
    public List<SysOrgVo> listSysOrgByCorpId(String corpId, String stockId, String deptId, String userId) {
        return mapper.listSysOrgByCorpId(corpId, stockId, deptId, userId);
    }

    public List<IdNameVo> listOwnStock(String hosId, String userId) {
        return mapper.listOwnStock(hosId, userId);
    }

    public List<DeptBuyMainVo> listDeptBuyRequest(Date beginDate, Date endDate, String billId, String deptId, String stockId, String hosId, String userId) {
        return mapper.listDeptBuyRequest(beginDate, endDate, billId, deptId, stockId, hosId, userId);
    }

    public List<GoodsNoIdUniqueVo> listUniqueCode(String hosId, String stockId, String buyKind, List<String> goodsIdList) {
        return mapper.listUniqueCode(hosId, stockId, buyKind, goodsIdList);
    }

    public List<HosStockpileVo> listHosStockpile(String stockTableId, String hosId, String stockId, String goodsId, String batchNo, String batchId, String uniqueCode, Integer stocKind, boolean isUnique) {
        return mapper.listHosStockpile(stockTableId, hosId, stockId, goodsId, batchNo, batchId, uniqueCode, stocKind, isUnique);
    }

    public List<DeptBuySubVo> listDeptBuyRequestDetail(String hosId, String billId, String deptId, String stockId) {
        return mapper.listDeptBuyRequestDetail(hosId, billId, deptId, stockId);
    }

    public List<HosStockpileVo> listHosStockpileWithDs(RequestStockGoodsVo requestStockGoodsVo, OutStock outStockBill) {
        return mapper.listHosStockpileWithDs(requestStockGoodsVo, outStockBill);
    }

    public IdNameVo getDeptInfoByStockId(String corpId, String outStocId) {
        return mapper.getDeptInfoByStockId(corpId, outStocId);
    }

    public int countByPatientInHosId(String patientInHosId) {
        return mapper.countByPatientInHosId(patientInHosId);
    }

    public void insertSickerInfo(String id, String hosId, String patientId, String inHosId, String name, String shortPinyin, String filler) {
        mapper.insertSickerInfo(id,hosId, patientId, inHosId, name, shortPinyin, filler);
    }

    public void insertSickerInHospitalInfo(String id, String hosId, String patientId, String deptCode, String bedNum) {
        mapper.insertSickerInHospitalInfo(id, hosId, patientId, deptCode, bedNum);
    }

    public List<SickerInfoVo> listSickerInfo(String query, String hosId) {
        return mapper.listSickerInfo(query, hosId);
    }
}
