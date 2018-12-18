package gyqx.mdqc.mycerts.controller;

import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.mdqc.mycerts.service.BasRegistrationService;
import gyqx.mdqc.mycerts.vo.BasGoodsInfoVo;
import gyqx.mdqc.mycerts.vo.BasRegistrationInfoVo;
import gyqx.mdqc.mycerts.vo.IdNameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * 注册证控制器
 *
 * @Author liangwu
 * @Date 7/31/18 11:11 AM
 */
@RestController
@RequestMapping("/BasRegistration")
public class BasRegistrationController extends BaseController {
    final static int QUERY_STR_LEN = 3;
    
    @Autowired
    private BasRegistrationService service;

//    @Autowired
//    public BasRegistrationController(BasRegistrationService service) {
//        this.service = service;
//    }

    @GetMapping("/listCerts")
    public AjaxResult listCerts(@RequestParam(required = false) String input,
                                @RequestParam(required = false) String kind,
                                @RequestParam(required = false) String mfrsId,
                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                @RequestParam(required = false, defaultValue = "30") Integer size) {
        AjaxResult<QueryResult<BasRegistrationInfoVo>> result = new AjaxResult<>();
        result.setData(service.listCerts(input, kind, mfrsId, page, size));
        return result;
    }

    @GetMapping("/getDict/{dictName}")
    public AjaxResult getDict(@PathVariable("dictName") String dictName) {
        AjaxResult<List<IdNameVo>> result = new AjaxResult<>();
        result.setData(service.listDictValues(dictName));
        return result;
    }

    @GetMapping("/getMfrs/{queryStr}")
    public AjaxResult getMfrs(@PathVariable("queryStr") String queryStr) {
        if (queryStr.length() < QUERY_STR_LEN) {
            throw new IllegalArgumentException("搜索字符必须大于等于3个");
        }
        AjaxResult<List<IdNameVo>> result = new AjaxResult<>();
        result.setData(service.listMfrs(queryStr));
        return result;
    }

    @DeleteMapping("/del/{id}/{version}")
    public AjaxResult deleteCert(@PathVariable("id") String id,
                                 @PathVariable("version") Integer version) {
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.delCert(id, version));
        return result;
    }

    @GetMapping("/verifyMfrs")
    public AjaxResult<Boolean> verifyMfrs() {
        AjaxResult<Boolean> result = new AjaxResult<>();
        result.setData(service.verifyMfrs());
        return result;
    }

    @GetMapping("/getMfrsProduct/{mfrsId}")
    public AjaxResult getMfrsProduct(@PathVariable("mfrsId") String mfrsId,
                                     @RequestParam(value = "input", required = false) String input) {
        AjaxResult<List<BasGoodsInfoVo>> result = new AjaxResult<>();
        result.setData(service.getMfrsProduct(mfrsId, input));
        return result;
    }

    @PostMapping("/addCert")
    public AjaxResult addCert(@RequestBody BasRegistrationInfoVo basRegistrationInfoVo) throws SQLException {
        AjaxResult<Boolean> result = new AjaxResult<>();
        result.setData(service.addCert(basRegistrationInfoVo));
        return result;
    }

    @GetMapping("/getCert/{id}/{version}/{mfrsId}")
    public AjaxResult getCert(@PathVariable("id") String id,
                              @PathVariable("version") Integer version,
                              @PathVariable("mfrsId") String mfrsId){
        AjaxResult<BasRegistrationInfoVo> result = new AjaxResult<>();
        result.setData(service.getCert(id, version, mfrsId));
        return result;
    }

    @PostMapping("/modCert")
    public AjaxResult modCert(@RequestBody BasRegistrationInfoVo basRegistrationInfoVo) throws Exception {
        AjaxResult<Boolean> result = new AjaxResult<>();
        result.setData(service.modCert(basRegistrationInfoVo));
        return result;
    }

    @PostMapping("/replaceCert")
    public AjaxResult replaceCert(@RequestBody BasRegistrationInfoVo basRegistrationInfoVo) throws SQLException {
        AjaxResult<Boolean> result = new AjaxResult<>();
        result.setData(service.replaceCert(basRegistrationInfoVo));
        return result;
    }
}
