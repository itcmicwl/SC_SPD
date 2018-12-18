package gyqx.spdherp.stockout.service.impl;

import common.base.PinYin;
import common.db.SimpleDao;
import common.exception.ParameterException;
import common.transform.Tx;
import common.utils.DictionaryUtils;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;
import gyqx.spdherp.po.*;
import gyqx.spdherp.stockout.dao.RequestOutDao;
import gyqx.spdherp.stockout.service.RequestOutService;
import gyqx.spdherp.stockout.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther Liangwu
 * @Date 17-8-16 上午10:31
 */
@Service
public class RequestOutServiceImpl implements RequestOutService {
    @Resource
    private RequestOutDao requestOutDao;

    @Resource
    private SimpleDao simpleDao;

    @Resource
    private UtilsContext utilsContext;

    @Override
    public List<SysOrgVo> listSysOrg(String stockId) {
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        return requestOutDao.listSysOrgByCorpId(user.getCorpId(), stockId, user.getOrgId(), user.getUserId());
    }

    @Override
    public List<IdNameVo> listOwnStock() {
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        String uid = user.getUserId();
        String corpId = user.getCorpId();
        return requestOutDao.listOwnStock(corpId, uid);
    }

    @Override
    public List<DeptBuyMainVo> listDeptBuyRequest(Date beginDate, Date endDate, String billId, String deptId, String stockId) {
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        if (StringUtils.hasText(billId)) {
            billId = billId.replaceAll("%", "\\\\%");
        }
        return requestOutDao.listDeptBuyRequest(beginDate, endDate, billId, deptId, stockId, user.getCorpId(), user.getUserId());
    }

    @Override
    public List<DictionaryUtils.DictionaryValue> listDictValue(String dict) {
        return utilsContext.getDictUtils().getDictItems(dict);
    }

    @Override
    @Transactional
    // TODO: 写出代码逻辑
    public String saveOutStockBill(OutStockBillVo outStockBillVo) throws Exception {
        // 判断患者信息是否存在或信息是否完整
        if (outStockBillVo.getOutStockType() == 20 && requestOutDao.countByPatientInHosId(outStockBillVo.getPatientName()) == 0) {
            if (!(StringUtils.hasText(outStockBillVo.getPatient()) &&
                    StringUtils.hasText(outStockBillVo.getPatientName()) &&
                    StringUtils.hasText(outStockBillVo.getPatientBedNum()) &&
                    StringUtils.hasText(outStockBillVo.getPatientDeptCode()))) {
                throw new ParameterException("患者信息输入不全，请补全。");
            }
            String sickerInfoId = utilsContext.getSysAtomUtil().newValue("sicker_info_id");
            String sickerInHospitalInfoId = utilsContext.getSysAtomUtil().newValue("sicker_in_hospital_info");
            String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
            String filler = utilsContext.getUserStateUtils().getCurrent().getUserId();
            String patientId = UUID.randomUUID().toString();
            // 写入患者基本信息
            requestOutDao.insertSickerInfo(sickerInfoId,
                    hosId,
                    patientId,
                    outStockBillVo.getPatientName(),
                    outStockBillVo.getPatient(),
                    PinYin.getShortPinyin(outStockBillVo.getPatient()),
                    filler);
            // 写入患者住院信息
            requestOutDao.insertSickerInHospitalInfo(sickerInHospitalInfoId,
                    hosId,
                    patientId,
                    outStockBillVo.getPatientDeptCode(),
                    outStockBillVo.getPatientBedNum());
        }
        OutStock outStockBill = new OutStock();
        Tx.transform(outStockBillVo).to(outStockBill);
        //gw add 请购出库提交时自动完成审核
        outStockBill.setAuditor(utilsContext.getUserStateUtils().getCurrent().getUserId());
        outStockBill.setAuditDate(new Date());
        outStockBill.setStatus(20);
       //end
        String hosId=  outStockBillVo.getGoodsList().get(0).getGoodsId().split("\\|")[0];
        simpleDao.insert(updateOutStockBillInfo(outStockBill,hosId));

        // 以商品ID，批号，效期，灭菌效期进行分组
        Map<RequestStockGoodsVo, List<RequestStockGoodsVo>> goodsMap = outStockBillVo.getGoodsList().stream().collect(Collectors.groupingBy(o -> o));
        int outBillRow = 0;
        for (RequestStockGoodsVo key : goodsMap.keySet()) {

            List<HosStockpileVo> hosStockpileVos;
            if (key.getStockTableId().toLowerCase().startsWith("ds")) {
                hosStockpileVos = requestOutDao.listHosStockpileWithDs(key, outStockBill);
            } else {
                hosStockpileVos = requestOutDao.listHosStockpile(key.getStockTableId(), outStockBill.getOutOrgId(), outStockBill.getOutStocId(), key.getGoodsId(), key.getBatchNo(), key.getBatchId(), key.getUniqueCode(), outStockBill.getOutStockType(), !key.getIsUnique().equals("3"));
            }
            if (hosStockpileVos.size() == 0) {
                throw new ValidationException("库存未找到相关批号/批次/唯一码商品");
            }

            HosStockpileVo prevHosStockpile = null;
            OutStockList prevOutStockBillSub = null;
            Double subGoodsSum = 0.0;
            for (Iterator<RequestStockGoodsVo> it = goodsMap.get(key).iterator(); it.hasNext(); ) {
                RequestStockGoodsVo requestStockGoodsVo = it.next();
                OutStockList outStockBillSub = new OutStockList();
                Tx.transform(requestStockGoodsVo).to(outStockBillSub);

                Optional<HosStockpileVo> hosStockpileVo = hosStockpileVos.stream().filter(o -> o.getId().equals(requestStockGoodsVo.getStockTableId())).findFirst();
                if (!hosStockpileVo.isPresent()) {
                    throw new ValidationException("库存未找到相关批号/批次/唯一码商品");
                }

                if (prevHosStockpile == null) {
                    outStockBillSub = updateOutStockBillSubInfo(outStockBillSub, ++outBillRow, outStockBill, requestStockGoodsVo, hosStockpileVo.get());
                    prevOutStockBillSub = outStockBillSub;
                    prevHosStockpile = hosStockpileVo.get();
                } else if (!prevHosStockpile.equals(hosStockpileVo.get())) {
                    prevOutStockBillSub.setOutQty(new BigDecimal(subGoodsSum));
                    simpleDao.insert(prevOutStockBillSub);
                    simpleDao.insert(updateHosTakingStock(outStockBill, prevOutStockBillSub, prevHosStockpile.getId()));
                    subGoodsSum = 0.0;
                    outStockBillSub = updateOutStockBillSubInfo(outStockBillSub, ++outBillRow, outStockBill, requestStockGoodsVo, hosStockpileVo.get());
                    prevOutStockBillSub = outStockBillSub;
                    prevHosStockpile = hosStockpileVo.get();
                } else {
                    outStockBillSub = prevOutStockBillSub;
                }

                if (requestStockGoodsVo.getIsUnique().equalsIgnoreCase("3")) { // 非唯一码管理
                    simpleDao.insert(updateOutStockBatch(outStockBillSub, requestStockGoodsVo, hosStockpileVo.get().getBigBatchCode()));
                    subGoodsSum += requestStockGoodsVo.getQty();
                } else { // 唯一码管理
                    simpleDao.insert(updateOutStockUniqueCode(outStockBillSub, requestStockGoodsVo));
                    subGoodsSum += requestStockGoodsVo.getQty();
                }

                if (!it.hasNext()) {
                    prevOutStockBillSub.setOutQty(new BigDecimal(subGoodsSum));
                    simpleDao.insert(prevOutStockBillSub);
                    simpleDao.insert(updateHosTakingStock(outStockBill, prevOutStockBillSub, prevHosStockpile.getId()));
                }
            }
        }
        return "OK";
    }

    private OutStockUniqueCode updateOutStockUniqueCode(OutStockList outStockSub, RequestStockGoodsVo requestStockGoodsVo) {
        OutStockUniqueCode outStockUniqueCode = new OutStockUniqueCode();

        outStockUniqueCode.setId(utilsContext.getSysAtomUtil().newValue("out_stock_unique_code_id"));
        outStockUniqueCode.setPid(outStockSub.getId());
        outStockUniqueCode.setBillId(outStockSub.getBillId());
        outStockUniqueCode.setPRowId(outStockSub.getOutBillRow());
        outStockUniqueCode.setProvId(outStockSub.getProvId());
        outStockUniqueCode.setGoodsId(outStockSub.getGoodsId());
        outStockUniqueCode.setGoodsBatchId(requestStockGoodsVo.getBatchId());
        outStockUniqueCode.setInPrice(new BigDecimal(requestStockGoodsVo.getPrice()));
        outStockUniqueCode.setInTime(new Date());
        outStockUniqueCode.setUniqueCode(requestStockGoodsVo.getUniqueCode());
        outStockUniqueCode.setVersion(0);

        outStockUniqueCode.setLastUpdateDatetime(new Date());

        return outStockUniqueCode;
    }

    private OutStockBatch updateOutStockBatch(OutStockList outStockSub, RequestStockGoodsVo requestStockGoodsVo, String bigBatchCode) {
        OutStockBatch outStockBatch = new OutStockBatch();

        outStockBatch.setId(outStockSub.getGoodsId().split("\\|")[0]+utilsContext.getSysAtomUtil().newValue("out_stock_batch_id"));
        outStockBatch.setPid(outStockSub.getId());
        outStockBatch.setBillId(outStockSub.getBillId());
        outStockBatch.setPRowId(outStockSub.getOutBillRow());
        outStockBatch.setProvId(outStockSub.getProvId());
        outStockBatch.setGoodsId(outStockSub.getGoodsId());
        outStockBatch.setGoodsBatchId(requestStockGoodsVo.getBatchId());
        outStockBatch.setBigBatchCode(bigBatchCode);
        outStockBatch.setInPrice(new BigDecimal(requestStockGoodsVo.getPrice() == null ? 0 : requestStockGoodsVo.getPrice()));
        outStockBatch.setInTime(new Date());
        outStockBatch.setQty(new BigDecimal(requestStockGoodsVo.getQty()));
        outStockBatch.setVersion(0);

        outStockBatch.setLastUpdateDatetime(new Date());

        return outStockBatch;
    }

    @Override
    // TODO: 条目查询时关联库存占用表时，用库存表ID关联
    public List<GoodsNoIdUniqueVo> listUniqueCode(String stockId, String buyKind, List<String> goodsIdList) {
        String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return requestOutDao.listUniqueCode(hosId, stockId, buyKind, goodsIdList);
    }

    @Override
    public List<DeptBuySubVo> listDeptBuyRequestDetail(String billId, String deptId, String stockId) {
        String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return requestOutDao.listDeptBuyRequestDetail(hosId, billId, deptId, stockId);
    }

    @Override
    public List<SickerInfoVo> listSickerInfo(String query) {
        String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        query = query.replaceAll("%", "\\\\%");
        query = "%" + query + "%";
        return requestOutDao.listSickerInfo(query, hosId);
    }

    private HosTakingStock updateHosTakingStock(OutStock outStockBill, OutStockList outStockSub, String stockpileId) {
        HosTakingStock hosTakingStock = new HosTakingStock();
        hosTakingStock.setId(utilsContext.getSysAtomUtil().newValue("hos_taking_stock_id"));
        hosTakingStock.setHosId(outStockBill.getOutOrgId());
        hosTakingStock.setStocId(outStockBill.getOutStocId());
        hosTakingStock.setHosGoodsId(outStockSub.getGoodsId());
        hosTakingStock.setStockpileId(stockpileId);
        hosTakingStock.setKind(outStockBill.getOutStockKind());
        hosTakingStock.setBillId(outStockBill.getBillId());
        hosTakingStock.setQty(outStockSub.getOutQty());
        hosTakingStock.setBillRownum(outStockSub.getOutBillRow());
        hosTakingStock.setVersion(0);
        hosTakingStock.setLastUpdateDatetime(new Date());
        return hosTakingStock;
    }

    private OutStockList updateOutStockBillSubInfo(OutStockList outStockSub, int outBillRow, OutStock outStockBill, RequestStockGoodsVo requestStockGoodsVo, HosStockpileVo hosStockpile) {
        outStockSub.setId(outStockBill.getOutOrgId()+utilsContext.getSysAtomUtil().newValue("out_stock_list_id"));
        outStockSub.setSourceBillId(requestStockGoodsVo.getBsId());
        outStockSub.setPid(outStockBill.getId());
        outStockSub.setBillId(outStockBill.getBillId());
        outStockSub.setOutBillRow(outBillRow);
        outStockSub.setOutQty(new BigDecimal(requestStockGoodsVo.getQty()));

        if (StringUtils.hasText(requestStockGoodsVo.getBatchNo()))
            outStockSub.setBatchCode(requestStockGoodsVo.getBatchNo());
        if (StringUtils.hasText(requestStockGoodsVo.getCertificateCode()))
            outStockSub.setCertificateCode(requestStockGoodsVo.getCertificateCode());
        if (hosStockpile.getSterilizationDate() != null)
            outStockSub.setSterilizationDate(hosStockpile.getSterilizationDate());
        if (StringUtils.hasText(hosStockpile.getSterilizationCode()))
            outStockSub.setSterilizationCode(hosStockpile.getSterilizationCode());
        if (hosStockpile.getSterilizationEndDate() != null)
            outStockSub.setSterilizationEndDate(hosStockpile.getSterilizationEndDate());
        if (hosStockpile.getExpdtEndDate() != null)
            outStockSub.setExpdtEndDate(hosStockpile.getExpdtEndDate());
        if (StringUtils.hasText(hosStockpile.getBarcode()))
            outStockSub.setMasterCode(hosStockpile.getBarcode());
        if (StringUtils.hasText(hosStockpile.getSecCode()))
            outStockSub.setSecCode(hosStockpile.getSecCode());
        if (StringUtils.hasText(hosStockpile.getHibcCode()))
            outStockSub.setHibcCode(hosStockpile.getHibcCode());

        outStockSub.setStatus(0);
        outStockSub.setVersion(0);

        outStockSub.setLastUpdateDatetime(new Date());

        return outStockSub;
    }

    private OutStock updateOutStockBillInfo(OutStock outStockBill,String hosId) {
        //String id = utilsContext.getSysAtomUtil().newValue("out_stock_id");
    	String id = utilsContext.getSysAtomUtil().newId("out_stock_id").prefix(hosId).get();
        //atomUtil.newId("cgInStock").prefix(inStock.getInOrgId()).get();//atomUtil.newValue("cgInStock");
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        outStockBill.setId(id);
        outStockBill.setSourceBillId(outStockBill.getBillId());
        outStockBill.setBillId(id);
        outStockBill.setFiller(user.getUserId());
        outStockBill.setFillDate(new Date());
        outStockBill.setOutOrgId(user.getCorpId());
        outStockBill.setOutOrgName(user.getCorpName());
        IdNameVo deptInfo = requestOutDao.getDeptInfoByStockId(user.getCorpId(), outStockBill.getOutStocId());
        outStockBill.setOutDeptId(deptInfo.getId());
        outStockBill.setOutDeptName(deptInfo.getName());
        outStockBill.setInOrgId(user.getCorpId());
        outStockBill.setInOrgName(user.getCorpName());
        outStockBill.setVersion(0);

        outStockBill.setLastUpdateDatetime(new Date());

        return outStockBill;
    }
}
