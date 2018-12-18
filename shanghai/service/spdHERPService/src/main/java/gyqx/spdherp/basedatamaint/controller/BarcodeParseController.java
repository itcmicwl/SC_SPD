package gyqx.spdherp.basedatamaint.controller;

import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdherp.basedatamaint.service.BarcodeParseService;
import gyqx.spdherp.basedatamaint.vo.BarcodeParseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * @Auther Liangwu
 * @Date 17-8-12 下午1:33
 */
@RestController
@RequestMapping({"/baseData/baseDataMaint/BarcodeParse"})
public class BarcodeParseController extends BaseController {
//    private static final int HOS_GOODS_ID_LENGTH = 25;
//    private static final int PACKAGE_QTY_LENGTH = 4;
    private static final int SN_LENGTH = 10;
    private static final int PACKAGE_ID_LENGTH = 2 /*+ HOS_GOODS_ID_LENGTH + PACKAGE_QTY_LENGTH*/ + SN_LENGTH;
    //    DS company-009|hosGood-0008 0004 1709210000
//    pGoodh-00135	12
//    |hosGood-00089	14
//    h0003|hosGood-00095	19
//    company-009|hosGood-00087	25

    @Resource
    private BarcodeParseService service;

    @GetMapping("/parseBarcodeBase") //Set<BarcodeParseResult>
    public AjaxResult parseBarcodeBase(@RequestParam("barcodeStr") String barcodeStr) throws Exception {
        AjaxResult<Set<BarcodeParseResult>> result = new AjaxResult<>();
        result.setData(service.parseBarcodeBase(barcodeStr));
        return result;
    }

    @PostMapping("/parseBarcodeCombine")
    public AjaxResult parseBarcodeCombine(@Valid @RequestBody BarcodeParseResult combinedBarcode) throws Exception {
        AjaxResult<Object> result = new AjaxResult<>();
        if (combinedBarcode.getBarcode().length() == PACKAGE_ID_LENGTH && combinedBarcode.getBarcode().toLowerCase().startsWith("ds")){
            result.setData(service.parseHosPackageInfo(combinedBarcode.getBarcode()));
        }else {
            result.setData(service.parseBarcodeCombine(combinedBarcode.getBarcode(),
                    combinedBarcode.getSlaverBarcode(),
                    combinedBarcode.getHosId(),
                    combinedBarcode.getProvId()));
        }
        return result;
    }

    @GetMapping("/parseBarcodeHERP")
    public AjaxResult parseBarcodeHERP(@RequestParam("masterBarcode") String masterBarcode,
                                       @RequestParam(value = "slaverBarcode", required = false) String slaverBarcode) throws Exception {
        AjaxResult<Object> result = new AjaxResult();
        if (masterBarcode.length() == PACKAGE_ID_LENGTH && masterBarcode.toLowerCase().startsWith("ds")){
            result.setData(service.parseHosPackageInfo(masterBarcode));
        }else {
            result.setData(service.parseBarcodeHERP(masterBarcode, slaverBarcode));
        }
        return result;
    }
}
