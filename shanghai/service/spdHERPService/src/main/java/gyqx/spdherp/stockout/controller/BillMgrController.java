package gyqx.spdherp.stockout.controller;

import common.utils.DictionaryUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdherp.po.OutStock;
import gyqx.spdherp.stockout.service.BillMgrService;
import gyqx.spdherp.stockout.service.RequestOutService;
import gyqx.spdherp.stockout.vo.IdNameVo;
import gyqx.spdherp.stockout.vo.OutStockBillVo;
import gyqx.spdherp.stockout.vo.SysOrgVo;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-8-29 上午11:15
 */
@RequestMapping({"/stockMgr/out/billMgr"})
@RestController
public class BillMgrController extends BaseController {
    @Resource
    private BillMgrService service;

    @Resource
    private RequestOutService requestOutService;

    @GetMapping("/getOrgs")
    public AjaxResult<List<SysOrgVo>> getOrgs(@RequestParam("stockId") String stockId,
                                              @RequestParam(value = "submitted", defaultValue = "false") Boolean submitted) {
        AjaxResult<List<SysOrgVo>> result = new AjaxResult<>();
        result.setData(service.listSysOrg(stockId, submitted));
        return result;
    }

    @GetMapping("/getStockList")
    public AjaxResult<List<IdNameVo>> getStockList() {
        AjaxResult<List<IdNameVo>> result = new AjaxResult<>();
        result.setData(requestOutService.listOwnStock());
        return result;
    }

    @GetMapping("/getDict")
    public AjaxResult<List<DictionaryUtils.DictionaryValue>> getDict(@RequestParam("dict") String dict) {
        AjaxResult<List<DictionaryUtils.DictionaryValue>> result = new AjaxResult<>();
        result.setData(requestOutService.listDictValue(dict));
        return result;
    }

    @GetMapping("/getBillList") // <List<OutStockBillVo>>
    public AjaxResult getBillList(@RequestParam("deptId") String deptId,
                                  @RequestParam("stockId") String stockId,
                                  @RequestParam(value = "beginDate", required = false) Date beginDate,
                                  @RequestParam(value = "endDate", required = false) Date endDate,
                                  @RequestParam("status") String status) {
        AjaxResult<List<OutStockBillVo>> result = new AjaxResult<>();
        result.setData(service.listBill(deptId, stockId, beginDate, endDate, status));
        return result;
    }

    @GetMapping("/getBillDetail")
    public AjaxResult<OutStockBillVo> getBillDetail(@RequestParam("id") String id) {
        AjaxResult<OutStockBillVo> result = new AjaxResult<>();
        result.setData(service.getBillDetail(id));
        return result;
    }

    @PatchMapping("/updateOutStockBill")
    public AjaxResult<Integer> updateOutStockBill(@Valid @RequestBody OutStock outStock, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.updateOutStock(outStock));
        return result;
    }
}
