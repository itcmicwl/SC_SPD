package gyqx.spdherp.basedatamaint.service.impl;

import common.db.SimpleDao;
import common.utils.UtilsContext;
import gyqx.platform.po.SysOrg;
import gyqx.spdhdi.po.ProvGoodsInfo;
import gyqx.spdherp.basedatamaint.dao.BarcodeParseDao;
import gyqx.spdherp.basedatamaint.service.BarcodeParseService;
import gyqx.spdherp.basedatamaint.vo.BarcodeParseResult;
import gyqx.spdherp.basedatamaint.vo.BarcodeRuleVo;
import gyqx.spdherp.basedatamaint.vo.HosPackageInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther Liangwu
 * @Date 17-8-12 下午3:57
 */
@Service
public class BarcodeParseServiceImpl implements BarcodeParseService {

    @Resource
    private BarcodeParseDao basBarcodeDao;

    @Resource
    private UtilsContext utilsContext;

    @Resource
    private SimpleDao simpleDao;

    @Override
    public Set<BarcodeParseResult> parseBarcodeBase(String barcodeStr) throws Exception {
        Set<BarcodeParseResult> results = new HashSet<>();

        List<BarcodeRuleVo> matchedRules = getMatchRules(barcodeStr);
        for (BarcodeRuleVo rule : matchedRules) {
            BarcodeParseResult parseResult = new BarcodeParseResult();

            if (rule.getConsumablesPosition() != null && rule.getConsumablesPosition() > 0 && rule.getConsumablesPosition() < rule.getBarcodeLen()) {
                parseResult.setGoodsNo(getGoodsNo(barcodeStr, rule));
                setGoodsName(parseResult);
            }

            if (rule.getPeriodPosition() != null && rule.getPeriodPosition() > 0 && rule.getPeriodPosition() < rule.getBarcodeLen())
                parseResult.setExpiredDate(getPeriodDate(barcodeStr, rule));

            if (rule.getProductionPosition() != null && rule.getProductionPosition() > 0 && rule.getProductionPosition() < rule.getBarcodeLen())
                parseResult.setBatchNo(getProduction(barcodeStr, rule));

            if (rule.getTracknumPosition() != null && rule.getTracknumPosition() > 0 && rule.getTracknumPosition() < rule.getBarcodeLen())
                parseResult.setTrackNo(getTracknum(barcodeStr, rule));

            setRuleInfo(parseResult, barcodeStr, rule);

            results.add(parseResult);
        }
        return results;
    }

    @Override
    public Set<BarcodeParseResult> parseBarcodeCombine(String masterBarcode, String slaverBarcode, String hosId, String provId) throws Exception {
        // 主码解析结果
        Set<BarcodeParseResult> results = parseBarcode(masterBarcode, hosId, provId);
        if (!StringUtils.hasText(slaverBarcode)) {
            return results;
        }

        // 解析副码
        Set<BarcodeParseResult> slaverResults = new HashSet<>();
        List<BarcodeRuleVo> matchedRules = getMatchRules(slaverBarcode);
        matchedRules = matchedRules.stream().filter(rule -> rule.getIsMain().equals("0")).collect(Collectors.toList());
        for (BarcodeRuleVo rule : matchedRules) {
            BarcodeParseResult parseResult = new BarcodeParseResult();

            if (rule.getPeriodPosition() != null && rule.getPeriodPosition() > 0 && rule.getPeriodPosition() < rule.getBarcodeLen())
                parseResult.setExpiredDate(getPeriodDate(slaverBarcode, rule));

            if (rule.getProductionPosition() != null && rule.getProductionPosition() > 0 && rule.getProductionPosition() < rule.getBarcodeLen())
                parseResult.setBatchNo(getProduction(slaverBarcode, rule));

            if (rule.getTracknumPosition() != null && rule.getTracknumPosition() > 0 && rule.getTracknumPosition() < rule.getBarcodeLen())
                parseResult.setTrackNo(getTracknum(slaverBarcode, rule));
//            setRuleInfo(parseResult, slaverBarcode, rule);
            slaverResults.add(parseResult);
        }
        for (BarcodeParseResult parseResult : slaverResults) {
            results.stream().forEach(item -> {
                if (parseResult.getExpiredDate() != null)
                    item.setExpiredDate(parseResult.getExpiredDate());
                if (StringUtils.hasText(parseResult.getTrackNo()))
                    item.setTrackNo(parseResult.getTrackNo());
                if (StringUtils.hasText(parseResult.getBatchNo()))
                    item.setBatchNo(parseResult.getBatchNo());
            });
        }
        for (BarcodeParseResult result : results) {
            result.setSlaverBarcode(slaverBarcode);
        }
        return results;
    }

    @Override
    public Set<BarcodeParseResult> parseBarcodeHERP(String masterBarcode, String slaverBarcode) throws Exception {
        return parseBarcodeCombine(masterBarcode, slaverBarcode, null, null);
    }

    @Override
    public List<HosPackageInfoVo> parseHosPackageInfo(String barcode) {
        String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return basBarcodeDao.listHosPackageInfo(barcode, hosId);
    }

    public Set<BarcodeParseResult> parseBarcode(String barcodeStr, String hosId, String provId) throws Exception {
        Set<BarcodeParseResult> results = new HashSet<>();

        List<BarcodeRuleVo> matchedRules = getMatchRules(barcodeStr);
        for (BarcodeRuleVo rule : matchedRules) {
            Set<BarcodeParseResult> matchedRuleResults = new HashSet<>();

            if (rule.getConsumablesPosition() != null && rule.getConsumablesPosition() > 0 && rule.getConsumablesPosition() < rule.getBarcodeLen()) {
                String goodsNo = getGoodsNo(barcodeStr, rule);
//                List<ProvGoodsInfo> goodsInfoList = (List<ProvGoodsInfo>) simpleDao.queryByFieldNames(ProvGoodsInfo.class, "provId,mfrsCode", provId, goodsNo);
                if (!StringUtils.hasText(hosId)) {
                    hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
                }
                List<String> goodsIdList;
                if (StringUtils.hasText(provId)) {
                    goodsIdList = basBarcodeDao.listProvGoodsHosId(goodsNo, hosId, provId);
                } else {
                    goodsIdList = basBarcodeDao.listHosGoodsId(goodsNo, hosId);
                }
                if (goodsIdList.size() > 0) {
//                    List<String> goodsHosIds = basBarcodeDao.getGoodsHosId(hosId, provId, goodsInfoList);
                    for (String goodsId : goodsIdList) {
                        BarcodeParseResult parseResult = new BarcodeParseResult();
                        parseResult.setGoodsId(goodsId);
                        parseResult.setGoodsNo(goodsNo);
                        matchedRuleResults.add(parseResult);
                    }
                } else {
                    BarcodeParseResult parseResult = new BarcodeParseResult();
                    parseResult.setGoodsId("");
                    parseResult.setGoodsNo(goodsNo);
                    matchedRuleResults.add(parseResult);
                }
            }

            if (rule.getPeriodPosition() != null && rule.getPeriodPosition() > 0 && rule.getPeriodPosition() < rule.getBarcodeLen()) {
                Date period = getPeriodDate(barcodeStr, rule);
                if (matchedRuleResults.size() > 0) {
                    for (BarcodeParseResult item : matchedRuleResults) {
                        item.setExpiredDate(period);
                    }
                } else {
                    BarcodeParseResult parseResult = new BarcodeParseResult();
                    parseResult.setExpiredDate(period);
                    matchedRuleResults.add(parseResult);
                }
            }

            if (rule.getProductionPosition() != null && rule.getProductionPosition() > 0 && rule.getProductionPosition() < rule.getBarcodeLen()) {
                String batchNo = getProduction(barcodeStr, rule);
                if (matchedRuleResults.size() > 0) {
                    for (BarcodeParseResult item : matchedRuleResults) {
                        item.setBatchNo(batchNo);
                    }
                } else {
                    BarcodeParseResult parseResult = new BarcodeParseResult();
                    parseResult.setBatchNo(batchNo);
                    matchedRuleResults.add(parseResult);
                }
            }

            if (rule.getTracknumPosition() != null && rule.getTracknumPosition() > 0 && rule.getTracknumPosition() < rule.getBarcodeLen()) {
                String trackNo = getTracknum(barcodeStr, rule);
                if (matchedRuleResults.size() > 0) {
                    for (BarcodeParseResult item : matchedRuleResults) {
                        item.setTrackNo(trackNo);
                    }
                } else {
                    BarcodeParseResult parseResult = new BarcodeParseResult();
                    parseResult.setTrackNo(trackNo);
                    matchedRuleResults.add(parseResult);
                }
            }

            for (BarcodeParseResult parseResult : matchedRuleResults) {
                setRuleInfo(parseResult, barcodeStr, rule);
            }

            results.addAll(matchedRuleResults);
        }

        return results;
    }

    private void setGoodsName(BarcodeParseResult parseResult) throws Exception {
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        SysOrg userOrg = (SysOrg) simpleDao.getByFieldName(SysOrg.class, "corpId", corpId);
        if (userOrg.getCorpKind().equals("1")) {
            List<ProvGoodsInfo> goodsList = (List<ProvGoodsInfo>) simpleDao.queryByFieldNames(ProvGoodsInfo.class, "mfrsCode", parseResult.getGoodsNo());
//                    Map<String, String> goodsSpec = list.stream().collect(Collectors.toMap(ProvGoodsInfo::getId, ProvGoodsInfo::getGoodsGg));
            if (goodsList.size() > 0) {
                Set<String> hisNames = basBarcodeDao.listHistNameById(goodsList, corpId);
                List<String> hisNameList = hisNames.stream().filter(StringUtils::hasText).collect(Collectors.toList());
                parseResult.setGoodsName(hisNameList.get(0));
            }
        } else if (userOrg.getCorpKind().equals("2")) {
            List<ProvGoodsInfo> goodsList = (List<ProvGoodsInfo>) simpleDao.queryByFieldNames(ProvGoodsInfo.class, "provId,mfrsCode", corpId, parseResult.getGoodsNo());
            if (goodsList.size() > 0) {
                parseResult.setGoodsName(goodsList.get(0).getGoodsName());
                parseResult.setGoodsSpec(goodsList.get(0).getGoodsGg());
            }
        }
    }

    private List<BarcodeRuleVo> getMatchRules(String barcodeStr) {
        List<BarcodeRuleVo> matchedRules = new ArrayList<>();

        Set<BarcodeRuleVo> ruleList = basBarcodeDao.listByBarcodeLen(barcodeStr.length());
        if (ruleList.size() == 0)
            return matchedRules;

        Map<String, List<BarcodeRuleVo>> ruleMap = ruleList.stream().collect(Collectors.groupingBy(BarcodeRuleVo::getBarleader));

//        ruleMap.keySet().stream().filter(barcodeStr::startsWith).forEach((String barleader) -> {
//            matchedRules.addAll(ruleMap.get(barleader));
//        });
        for (String barleader : ruleMap.keySet()) {
            if (barcodeStr.startsWith(barleader)) {
                matchedRules.addAll(ruleMap.get(barleader));
            }
        }

        for (Iterator<BarcodeRuleVo> iter = matchedRules.iterator(); iter.hasNext(); ) {
            boolean b = true;
            BarcodeRuleVo rule = iter.next();
            if ((StringUtils.hasText(rule.getSign1()) && !barcodeStr.startsWith(rule.getSign1(), rule.getSign1Position() - 1)) ||
                    (StringUtils.hasText(rule.getSign2()) && !barcodeStr.startsWith(rule.getSign2(), rule.getSign2Position() - 1))) {
                b = false;
            }
            if (!b) iter.remove();
        }

        return matchedRules;
    }

    private String getGoodsNo(String barcodeStr, BarcodeRuleVo rule) {
        return barcodeStr.substring(rule.getConsumablesPosition() - 1, rule.getConsumablesPosition() + rule.getConsumablesLen() - 1);
    }

    private Date getPeriodDate(String barcodeStr, BarcodeRuleVo rule) throws ParseException {
        String periodFormat = rule.getPeriodFormat() == null ? "yyMMdd" : rule.getPeriodFormat().toLowerCase().replaceAll("[^ymd]", "").replaceAll("mm", "MM");
        String period = barcodeStr.substring(rule.getPeriodPosition() - 1, rule.getPeriodPosition() + periodFormat.length() - 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(periodFormat);
        Date result = dateFormat.parse(period);
        int index = periodFormat.indexOf("dd");
        if (index != -1 && period.substring(index, index + 2).equals("00")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(result);
            calendar.add(Calendar.MONTH, 1);
            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, lastDay);
            result = calendar.getTime();
        }
        return result;
    }

    private String getProduction(String barcodeStr, BarcodeRuleVo rule) {
        return barcodeStr.substring(rule.getProductionPosition() - 1, rule.getProductionPosition() + rule.getProductionLen() - 1);
    }

    private String getTracknum(String barcodeStr, BarcodeRuleVo rule) {
        return barcodeStr.substring(rule.getTracknumPosition() - 1, rule.getTracknumPosition() + rule.getTracknumLen() - 1);
    }

    private void setRuleInfo(BarcodeParseResult parseResult, String barcodeStr, BarcodeRuleVo rule) {
        parseResult.setBarcode(barcodeStr);
        parseResult.setBarcodeLen(rule.getBarcodeLen());
        parseResult.setBarleader(rule.getBarleader());
        parseResult.setSign1(rule.getSign1());
        parseResult.setSign2(rule.getSign2());
        parseResult.setMainFlag(rule.getIsMain());
    }
}
