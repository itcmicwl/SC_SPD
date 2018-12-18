package gyqx.spdhdi.mycerts.controller;

import common.db.query.QueryResult;
import common.utils.DictionaryUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.mycerts.service.ProvMfrsInfoService;
import gyqx.spdhdi.mycerts.vo.BasMfrsInfoMyCertsVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsInfoVo;
import gyqx.spdhdi.po.ProvMfrsCertificateImage;
import gyqx.spdhdi.po.ProvMfrsInfo;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by liangwu on 17-6-12.
 */
@RestController
@RequestMapping("/provMfrsInfo")
@CrossOrigin
public class ProvMfrsInfoController extends BaseController {
    @Resource
    private ProvMfrsInfoService service;

    @GetMapping("/getMfrsKind")
    public AjaxResult<List<DictionaryUtils.DictionaryValue>> getMfrsKind() {
        AjaxResult<List<DictionaryUtils.DictionaryValue>> result = new AjaxResult<>();
        result.setData(service.findCjlxDicts());
        return result;
    }

    @GetMapping("/getCertKind")
    public AjaxResult<List<DictionaryUtils.DictionaryValue>> getCertKind() {
        AjaxResult<List<DictionaryUtils.DictionaryValue>> result = new AjaxResult<>();
        result.setData(service.findZzlxDicts());
        return result;
    }

    @GetMapping("/getMfrsInfo") // <QueryResult<ProvMfrsInfoVo>>
    public AjaxResult getMfrsInfo(@RequestParam(value = "searchType", required = false, defaultValue = "0") int searchType,
                                  @RequestParam(value = "searchText", defaultValue = "") String searchText,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") int pageNum,
                                  @RequestParam(value = "rows", required = false, defaultValue = "30") int pageSize) {
        AjaxResult<QueryResult<ProvMfrsInfoVo>> result = new AjaxResult<>();
        result.setData(service.findMfrsInfo(searchType, searchText, pageNum, pageSize));
        return result;
    }

    @GetMapping("/getBasMfrs") // <List<BasMfrsInfoMyCertsVo>>
    public AjaxResult getBasMfrs(@RequestParam("mfrsName") String mfrsName) {
        AjaxResult<List<BasMfrsInfoMyCertsVo>> result = new AjaxResult<>();
        result.setData(service.findBasMfrs(mfrsName));
        return result;
    }

    @GetMapping("/getCertList")
    public AjaxResult getCertList(@RequestParam("id") String id) {
        AjaxResult<List<ProvMfrsCertificateVo>> result = new AjaxResult<>();
        result.setData(service.findCertList(id));
        return result;
    }

    @PostMapping("/addMfrsInfo")
    public AjaxResult addMfrsInfo(@RequestBody @Valid ProvMfrsInfoVo provMfrsInfoVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.addMfrsInfo(provMfrsInfoVo));
        return result;
    }

    @PatchMapping("/modMfrsInfo")
    public AjaxResult<Integer> modMfrsInfo(@RequestBody @Valid ProvMfrsInfoVo provMfrsInfoVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.updateMfrsInfo(provMfrsInfoVo));
        return result;
    }

    @DeleteMapping("/delMfrsInfo")
    public AjaxResult<String> delMfrsInfo(@RequestParam("id") String id, @RequestParam("version") int version) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.deleteMfrsInfo(id, version));
        return result;
    }

    @DeleteMapping("/delPic")
    public AjaxResult<String> delPic(@RequestParam("id") String id) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.deleteMfrsCertImg(id));
        return result;
    }

    @PostMapping("/addPic")
    public AjaxResult<Set<ProvMfrsCertificateImage>> addPic(@RequestBody ProvMfrsCertificateVo cert) throws Exception {
        AjaxResult<Set<ProvMfrsCertificateImage>> result = new AjaxResult<>();
        result.setData(service.addPic(cert));
        return result;
    }

    @PatchMapping("/changeFlag")
    public AjaxResult<ProvMfrsInfo> changeFlag(@RequestBody @Valid ProvMfrsInfo provMfrsInfo
                                               /*@RequestParam("id") String id,
                                               @RequestParam("version") int version,
                                               @RequestParam("flag") String flag*/) throws Exception {
        AjaxResult<ProvMfrsInfo> result = new AjaxResult<>();
        result.setData(service.updateFlag(provMfrsInfo));
        return result;
    }

    @DeleteMapping("/delCert")
    public AjaxResult<Integer> delCert(@RequestParam("id") String id,
                                       @RequestParam(value = "version", defaultValue = "0") int version) throws Exception {
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.deleteMfrsCert(id, version));
        return result;
    }
}
