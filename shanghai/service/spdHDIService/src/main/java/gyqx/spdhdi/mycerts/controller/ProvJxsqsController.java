package gyqx.spdhdi.mycerts.controller;

import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.mycerts.service.ProvJxsqsService;
import gyqx.spdhdi.mycerts.vo.*;
import gyqx.spdhdi.po.ProvJxsqsGoods;
import gyqx.spdhdi.po.ProvJxsqsHos;
import gyqx.spdhdi.po.ProvJxsqsZjzj;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by liangwu on 17-6-12.
 */
@RequestMapping("/provJxsqs")
@RestController
@CrossOrigin
public class ProvJxsqsController extends BaseController {
    @Resource
    private ProvJxsqsService service;

    @GetMapping("/getBasMfrs")
    public AjaxResult<List<BasMfrsInfoVo>> getBasMfrs(@RequestParam(value = "mfrsName", defaultValue = "") String mfrsName) {
        AjaxResult<List<BasMfrsInfoVo>> result = new AjaxResult<>();
        result.setData(service.findBasMfrs(mfrsName));
        return result;
    }

    @GetMapping("/getBasCompany")
    public AjaxResult<List<BasMfrsInfoVo>> getBasCompany(@RequestParam(value = "name", defaultValue = "") String name) {
        AjaxResult<List<BasMfrsInfoVo>> result = new AjaxResult<>();
        result.setData(service.findBasCompany(name));
        return result;
    }

    @GetMapping("/getZjzjCompany")
    public AjaxResult<List<String>> getZjzjCompany(@RequestParam(value = "name", defaultValue = "") String name) {
        AjaxResult<List<String>> result = new AjaxResult<>();
        result.setData(service.findZjzjCompany(name));
        return result;
    }

    @GetMapping("/getProvRegInfo")
    public AjaxResult<QueryResult<ProvRegInfoJxsqsVo>> getProvRegInfo(
            @RequestParam("mfrsId") String mfrsId,
            @RequestParam("certName") String certName,
            @RequestParam(value = "sqsId", defaultValue = "") String sqsId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        AjaxResult<QueryResult<ProvRegInfoJxsqsVo>> result = new AjaxResult<>();
        result.setData(service.findProvRegInfo(mfrsId, certName, sqsId, pageNum));
        return result;
    }

    @GetMapping("/getBasHos")
    public AjaxResult<QueryResult<BasCompanyInfoJxsqsVo>> getBasHos(
            @RequestParam(value = "hosName", defaultValue = "") String hosName,
            @RequestParam(value = "sqsId", defaultValue = "") String sqsId,
            @RequestParam("pageNum") int pageNum) {
        AjaxResult<QueryResult<BasCompanyInfoJxsqsVo>> result = new AjaxResult<>();
        result.setData(service.findBasHos(hosName, sqsId, pageNum));
        return result;
    }

    @PostMapping("/addJxsqs")
    public AjaxResult<String> addJxsqs(@RequestBody @Valid ProvJxsqsVo provJxsqsVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.addJxsqs(provJxsqsVo));
        return result;
    }

    @PostMapping("/addProvJxsqsZjzj")
    public AjaxResult<String> addProvJxsqsZjzj(@RequestBody @Valid ProvJxsqsZjzj provJxsqsZjzj, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.addProvJxsqsZjzj(provJxsqsZjzj));
        return result;
    }

    @GetMapping("/getJxsqs")
    public AjaxResult<QueryResult<ProvJxsqsVo>> getJxsqs(@RequestParam(value = "start", required = false) Date start,
                                                         @RequestParam(value = "end", required = false) Date end,
                                                         @RequestParam(value = "mfrsName", defaultValue = "") String mfrsName,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                         @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {
        AjaxResult<QueryResult<ProvJxsqsVo>> result = new AjaxResult<>();
        result.setData(service.findJxsqs(start, end, mfrsName, pageNum, pageSize));
        return result;
    }
    @PostMapping("getJxsqsList")
    public AjaxResult<List<ProvJxsqsNVo>> getJxsqlList(@RequestBody ProvJxsqsGoodsVo query) throws Exception{
        AjaxResult<List<ProvJxsqsNVo>> res = new AjaxResult<>();
        List<ProvJxsqsNVo> data = service.getJxsqsList(query);
        res.setData(data);
        return res;
    }
    
    
    

    @PutMapping("/modJxsqs")
    public AjaxResult<Integer> modJxsqs(@RequestBody @Valid ProvJxsqsVo provJxsqsVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.updateJxsqs(provJxsqsVo));
        return result;
    }

    @DeleteMapping("/delJxsqs")
    public AjaxResult<String> delJxsqs(@RequestParam("id") String id, @RequestParam("version") int version) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.deleteJxsqs(id, version));
        return result;
    }

    @DeleteMapping("/delProvJxsqsZjzj")
    public AjaxResult<String> delProvJxsqsZjzj(@RequestParam("id") String id, @RequestParam("version") int version) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.delProvJxsqsZjzj(id, version));
        return result;
    }
    @PostMapping("saveJxsqsRelation")
    public AjaxResult<Boolean> saveJxsqsRelation(@RequestBody SaveJxsqsRelationVo saveJxsqsRelationVo)throws Exception{
        AjaxResult res = new AjaxResult();
        Boolean flag = service.saveJxsqsRelation(saveJxsqsRelationVo);
        res.setData(flag);
        return res;
    }
    @PostMapping("saveRelation")
    public AjaxResult<Boolean> saveRelationAndGet(@RequestBody SaveJxsqsRelationVo saveJxsqsRelationVo)throws Exception{
        AjaxResult res = new AjaxResult();
        Boolean flag = service.saveJxsqsRelation(saveJxsqsRelationVo);
        res.setData(flag);
        return res;
    }
    @PostMapping("saveRelation/{sqsId}")
    public AjaxResult<ProvJxsqsNVo> saveRelation(@RequestBody List<ProvJxsqsGoods> lst,@PathVariable("sqsId") String sqsId)throws Exception{
        AjaxResult res = new AjaxResult();
        ProvJxsqsNVo data = service.saveRelation(lst,sqsId);
        res.setData(data);
        return res;
    }
    @PostMapping("delRelation/{sqsId}")
    public AjaxResult<ProvJxsqsNVo> delRelationAndGet(@RequestBody List<ProvJxsqsGoods> lst,@PathVariable("sqsId") String sqsId)throws Exception{
        AjaxResult res = new AjaxResult();
        ProvJxsqsNVo data = service.delRelationAndGet(lst,sqsId);
        res.setData(data);
        return res;
    }
    @GetMapping("getSqsRelationById/{sqsId}")
    public AjaxResult<ProvJxsqsNVo> getSqsRelationById(@PathVariable("sqsId")String sqsId) throws Exception{
        AjaxResult res = new AjaxResult();
        ProvJxsqsNVo data = service.getSqsRelationById(sqsId);
        res.setData(data);
        return res;
    }
    @GetMapping("getSqsHosLst/{sqsId}")
    public AjaxResult<List<ProvJxsqsHosVo>> getSqsHosLst(@PathVariable("sqsId")String sqsId) throws Exception{
        AjaxResult res = new AjaxResult();
        List<ProvJxsqsHosVo> data = service.getSqsHosLst(sqsId);
        res.setData(data);
        return res;
    }
    @PostMapping("saveSqsHosLst")
    public AjaxResult<List<ProvJxsqsHosVo>> saveSqsHosLst(@RequestBody List<ProvJxsqsHos> lst) throws Exception{
        AjaxResult res = new AjaxResult();
        if(lst==null || lst.size()==0){
            throw new Exception("医院为空");
        }
        service.saveSqsHosLst(lst);
        List<ProvJxsqsHosVo> data = service.getSqsHosLst(lst.get(0).getSqsId());
        res.setData(data);
        return res;
    }
    @PostMapping("delSqsHos")
    public AjaxResult<List<ProvJxsqsHosVo>> delSqsHos(@RequestBody ProvJxsqsHos provJxsqsHos) throws Exception{
        AjaxResult res = new AjaxResult();        
        service.delSqsHos(provJxsqsHos);
        List<ProvJxsqsHosVo> data = service.getSqsHosLst(provJxsqsHos.getSqsId());
        res.setData(data);
        return res;
    }
}
