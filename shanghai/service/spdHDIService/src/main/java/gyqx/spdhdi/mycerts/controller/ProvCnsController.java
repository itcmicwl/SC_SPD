package gyqx.spdhdi.mycerts.controller;

import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.mycerts.service.ProvCnsService;
import gyqx.spdhdi.mycerts.service.ProvFrwtsService;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvCnsVo;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by liangwu on 17-6-12.
 */
@RestController
@RequestMapping("/provCns")
@CrossOrigin
public class ProvCnsController extends BaseController {
    @Resource
    private ProvFrwtsService frwtsService;

    @Resource
    private ProvCnsService service;

    @GetMapping("/getHosList")
    public AjaxResult<List<BasCompanyInfoJxsqsVo>> getHosList(@RequestParam("hosName") String hosName) {
        AjaxResult<List<BasCompanyInfoJxsqsVo>> result = new AjaxResult<>();
        result.setData(frwtsService.findHosList(hosName));
        return result;
    }

    @PostMapping("/addCns")
    public AjaxResult<String> addCns(@RequestBody @Valid ProvCnsVo provCnsVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.addCns(provCnsVo));
        return result;
    }

    @GetMapping("/getCns")
    public AjaxResult<QueryResult<ProvCnsVo>> getJxsqs(@RequestParam(value = "start", required = false) Date start,
                                                       @RequestParam(value = "end", required = false) Date end,
                                                       @RequestParam(value = "hosName", defaultValue = "") String hosName,
                                                       @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {
        AjaxResult<QueryResult<ProvCnsVo>> result = new AjaxResult<>();
        result.setData(service.findCns(start, end, hosName, pageNum, pageSize));
        return result;
    }

    @PutMapping("/modCns")
    public AjaxResult<Integer> modCns(@RequestBody @Valid ProvCnsVo provCnsVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.updateCns(provCnsVo));
        return result;
    }

    @DeleteMapping("/delCns")
    public AjaxResult<String> delCns(@RequestParam("id") String id, @RequestParam("version") int version) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.deleteCns(id, version));
        return result;
    }
}
