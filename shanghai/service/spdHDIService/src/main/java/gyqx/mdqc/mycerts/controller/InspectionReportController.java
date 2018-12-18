package gyqx.mdqc.mycerts.controller;

import com.github.stuxuhai.jpinyin.PinyinException;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.mdqc.mycerts.service.InspectionReportService;
import gyqx.mdqc.mycerts.vo.InspectionReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @Author liangwu
 * @Date 18-9-10 下午4:20
 */
@RestController
@RequestMapping("/inspectionReport")
public class InspectionReportController extends BaseController {
    @Autowired
    private InspectionReportService service;

    @GetMapping("/listReport")
    public AjaxResult listReport(@RequestParam HashMap<String, String> params){
//        请求参数
//        String goodsName,
//        String goodsSpec,
//        String mfrsName,
//        String provId,
//        String lot,
//        String page,
//        String size
        AjaxResult<QueryResult<InspectionReportVo>> result = new AjaxResult<>();
        result.setData(service.listReport(params));
        return result;
    }

    @GetMapping("/getReportAttachment/{id}/{version}")
    public AjaxResult getReportAttachment(@PathVariable("id") String id,
                                          @PathVariable("version") String version){
        AjaxResult<List<String>> result = new AjaxResult<>();
        result.setData(service.listReportAttachment(id, version));
        return result;
    }

    @DeleteMapping("/delReport/{id}/{version}")
    public AjaxResult delReport(@PathVariable("id") String id,
                                @PathVariable("version") String version){
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.delReport(id, version));
        return result;
    }

    @PostMapping("/addReport")
    public AjaxResult addReport(@RequestBody InspectionReportVo vo) throws PinyinException {
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.addReport(vo));
        return result;
    }
}
