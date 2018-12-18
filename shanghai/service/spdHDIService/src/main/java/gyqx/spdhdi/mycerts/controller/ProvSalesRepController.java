package gyqx.spdhdi.mycerts.controller;

import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.mycerts.service.ProvFrwtsService;
import gyqx.spdhdi.mycerts.service.ProvSalesRepService;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvSalesRepVo;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-11-1 上午10:08
 */
@RestController
@RequestMapping("/provSalesRep")
@CrossOrigin
public class ProvSalesRepController extends BaseController {
    @Resource
    private ProvFrwtsService frwtsService;

    @Resource
    private ProvSalesRepService service;

    @GetMapping("/getHosList")
    public AjaxResult<List<BasCompanyInfoJxsqsVo>> getHosList(@RequestParam("hosName") String hosName) {
        AjaxResult<List<BasCompanyInfoJxsqsVo>> result = new AjaxResult<>();
        result.setData(frwtsService.findHosList(hosName));
        return result;
    }

    @GetMapping("/getSalesReps")
    public AjaxResult getJxsqs(@RequestParam(value = "start", required = false) Date start,
                               @RequestParam(value = "end", required = false) Date end,
                               @RequestParam(value = "name", defaultValue = "") String name,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {
        AjaxResult<QueryResult<ProvSalesRepVo>> result = new AjaxResult<>();
        result.setData(service.listSalesRep(start, end, name, pageNum, pageSize));
        return result;
    }

    @PostMapping("/addSalesRep")
    public AjaxResult<String> addFrwts(@RequestBody @Valid ProvSalesRepVo provSalesRepVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.addSalesReps(provSalesRepVo));
        return result;
    }

    @PutMapping("/modSalesRep")
    public AjaxResult<Integer> modFrwts(@RequestBody @Valid ProvSalesRepVo provSalesRepVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.updateSalesRep(provSalesRepVo));
        return result;
    }

    @DeleteMapping("/delSalesRep")
    public AjaxResult<String> delFrwts(@RequestParam("id") String id, @RequestParam("version") int version) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.deleteSalesRep(id, version));
        return result;
    }
}