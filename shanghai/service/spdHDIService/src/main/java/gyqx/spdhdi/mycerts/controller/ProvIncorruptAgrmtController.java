package gyqx.spdhdi.mycerts.controller;

import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.mycerts.service.ProvCnsService;
import gyqx.spdhdi.mycerts.service.ProvFrwtsService;
import gyqx.spdhdi.mycerts.service.ProvIncorruptAgrmtService;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvCnsVo;
import gyqx.spdhdi.mycerts.vo.ProvIncorruptAgrmtVo;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-10-31 下午8:25
 */
@RestController
@RequestMapping("/provIncorruptAgrmt")
@CrossOrigin
public class ProvIncorruptAgrmtController extends BaseController {
    @Resource
    private ProvFrwtsService frwtsService;

    @Resource
    private ProvIncorruptAgrmtService service;

    @GetMapping("/getHosList")
    public AjaxResult<List<BasCompanyInfoJxsqsVo>> getHosList(@RequestParam("hosName") String hosName) {
        AjaxResult<List<BasCompanyInfoJxsqsVo>> result = new AjaxResult<>();
        result.setData(frwtsService.findHosList(hosName));
        return result;
    }

    @PostMapping("/addIncorruptAgrmt")
    public AjaxResult<String> addCns(@RequestBody @Valid ProvIncorruptAgrmtVo vo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.addIncorruptAgrmt(vo));
        return result;
    }

    @GetMapping("/getIncorruptAgrmt")
    public AjaxResult getJxsqs(@RequestParam(value = "start", required = false) Date start,
                               @RequestParam(value = "end", required = false) Date end,
                               @RequestParam(value = "hosName", defaultValue = "") String hosName,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {
        AjaxResult<QueryResult<ProvIncorruptAgrmtVo>> result = new AjaxResult<>();
        result.setData(service.listIncorruptAgrmt(start, end, hosName, pageNum, pageSize));
        return result;
    }

    @PutMapping("/modIncorruptAgrmt")
    public AjaxResult<Integer> modCns(@RequestBody @Valid ProvIncorruptAgrmtVo provIncorruptAgrmtVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.updateIncorruptAgrmt(provIncorruptAgrmtVo));
        return result;
    }

    @DeleteMapping("/delIncorruptAgrmt")
    public AjaxResult<String> delCns(@RequestParam("id") String id, @RequestParam("version") int version) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.deleteIncorruptAgrmt(id, version));
        return result;
    }
}
