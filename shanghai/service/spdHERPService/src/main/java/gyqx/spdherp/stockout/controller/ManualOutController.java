package gyqx.spdherp.stockout.controller;

import common.db.query.QueryResult;
import common.utils.DictionaryUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdherp.stockout.service.ManualOutService;
import gyqx.spdherp.stockout.service.RequestOutService;
import gyqx.spdherp.stockout.vo.*;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-9-11 上午10:12
 */
@RestController
@RequestMapping({"/stockMgr/out/manualOut"})
public class ManualOutController extends BaseController {
    @Resource
    RequestOutService requestOutService;

    @Resource
    ManualOutService service;

    @GetMapping("/getStockList")
    public AjaxResult<List<IdNameVo>> getStockList() {
        AjaxResult<List<IdNameVo>> result = new AjaxResult<>();
        result.setData(requestOutService.listOwnStock());
        return result;
    }

    @PostMapping("/getUniqueCodeList")
    public AjaxResult getUniqueCodeList(@RequestBody UniqueCodeQueryVo vo) {
        AjaxResult<List<GoodsNoIdUniqueVo>> result = new AjaxResult<>();
        result.setData(requestOutService.listUniqueCode(vo.getStockId(), vo.getBuyKind(), vo.getGoodsId()));
        return result;
    }

    @GetMapping("/getStockGoodsList")
    public AjaxResult getStockGoodsList(@RequestParam("stockId") String stockId,
                                        @RequestParam("stocKind") Integer stocKind,
                                        @RequestParam("pageNum") int pageNum,
                                        @RequestParam("pageSize") int pageSize) {
        AjaxResult<QueryResult<StockpileHzVo>> result = new AjaxResult<>();
        result.setData(service.listStockGoods(stockId, stocKind, pageNum, pageSize));
        return result;
    }

    @GetMapping("/getDict")
    public AjaxResult<List<DictionaryUtils.DictionaryValue>> getDict(@RequestParam("dict") String dict) {
        AjaxResult<List<DictionaryUtils.DictionaryValue>> result = new AjaxResult<>();
        result.setData(requestOutService.listDictValue(dict));
        return result;
    }

    @PostMapping("/saveOutStockBill")
    public AjaxResult<String> saveOutStockBill(@Valid @RequestBody OutStockBillVo outStockBillVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(requestOutService.saveOutStockBill(outStockBillVo));
        return result;
    }
}
