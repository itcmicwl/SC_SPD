package gyqx.spdhdi.baseData.controller;

import common.web.AjaxResult;
import gyqx.spdhdi.baseData.service.BasBarcodeService;
import gyqx.spdhdi.baseData.vo.BarcodeParseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Auther Liangwu
 * @Date 17-7-14 下午3:21
 */
@RestController
@RequestMapping("/baseData/basBarcode")
public class BasBarcodeController {
    @Resource
    private BasBarcodeService service;

    @GetMapping("/parseBarcodeBase") //Set<BarcodeParseResult>
    public AjaxResult parseBarcodeBase(@RequestParam("barcodeStr") String barcodeStr) throws Exception {
        AjaxResult<Set<BarcodeParseResult>> result = new AjaxResult<>();
        result.setData(service.parseBarcode(barcodeStr));
        return result;
    }

    @PostMapping("/parseBarcode")
    public AjaxResult parseBarcode(@RequestBody BarcodeParseResult barcode) throws Exception {
        AjaxResult<Set<BarcodeParseResult>> result = new AjaxResult<>();
        result.setData(service.parseBarcode(barcode.getBarcode(), barcode.getHosId(), barcode.getProvId()));
        return result;
    }

    @PostMapping("/parseCombinedBarcode")
    public AjaxResult parseCombinedBarcode(@RequestBody BarcodeParseResult combinedBarcode) throws Exception {
        AjaxResult<Set<BarcodeParseResult>> result = new AjaxResult<>();
        result.setData(service.parseBarcode(combinedBarcode.getBarcode(),
                combinedBarcode.getSlaverBarcode(),
                combinedBarcode.getHosId(),
                combinedBarcode.getProvId()));
        return result;
    }
}
