package gyqx.spdhdi.mycerts.controller;

import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.mycerts.service.ProvFrwtsService;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvFrwtsVo;
import gyqx.spdhdi.mycerts.vo.WtrUserInfoVo;
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
@CrossOrigin
@RequestMapping("/provFrwts")
public class ProvFrwtsController extends BaseController {
    @Resource
    private ProvFrwtsService service;

    @GetMapping("/getHosList")
    public AjaxResult<List<BasCompanyInfoJxsqsVo>> getHosList(@RequestParam("hosName") String hosName) {
        AjaxResult<List<BasCompanyInfoJxsqsVo>> result = new AjaxResult<>();
        result.setData(service.findHosList(hosName));
        return result;
    }

    @GetMapping("/getWtrInfo")
    public AjaxResult<List<WtrUserInfoVo>> getWtrInfo() {
        AjaxResult<List<WtrUserInfoVo>> result = new AjaxResult<>();
        result.setData(service.findWtrInfo());
        return result;
    }

    @PostMapping("/addFrwts")
    public AjaxResult<String> addFrwts(@RequestBody @Valid ProvFrwtsVo provFrwtsVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.addFrwts(provFrwtsVo));
        return result;
    }

    @GetMapping("/getFrwts")
    public AjaxResult<QueryResult<ProvFrwtsVo>> getFrwts(@RequestParam(value = "start", required = false) Date start,
                                                         @RequestParam(value = "end", required = false) Date end,
                                                         @RequestParam(value = "name", defaultValue = "") String name,
                                                         @RequestParam(value = "provName", defaultValue = "") String provName,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                         @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {
        AjaxResult<QueryResult<ProvFrwtsVo>> result = new AjaxResult<>();
        result.setData(service.findFrwts(start, end, name,provName, pageNum, pageSize,"",""));
        return result;
    }

    @PutMapping("/modFrwts")
    public AjaxResult<Integer> modFrwts(@RequestBody @Valid ProvFrwtsVo provFrwtsVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.updateFrwts(provFrwtsVo));
        return result;
    }

    @DeleteMapping("/delFrwts")
    public AjaxResult<String> delFrwts(@RequestParam("id") String id, @RequestParam("version") int version) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.deleteFrwts(id, version));
        return result;
    }
}
