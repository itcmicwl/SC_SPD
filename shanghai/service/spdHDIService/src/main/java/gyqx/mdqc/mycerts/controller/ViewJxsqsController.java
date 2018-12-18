package gyqx.mdqc.mycerts.controller;

import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.mdqc.mycerts.service.BasRegistrationService;
import gyqx.mdqc.mycerts.service.ViewJxsqsService;
import gyqx.mdqc.mycerts.vo.GoodsInfoVo;
import gyqx.mdqc.mycerts.vo.IdNameVo;
import gyqx.mdqc.mycerts.vo.JxsqsData;
import gyqx.mdqc.mycerts.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 查看产品授权关系控制器
 *
 * @Author liangwu
 * @Date 18-8-22 上午10:17
 */
@RestController
public class ViewJxsqsController extends BaseController {
    @Autowired
    private BasRegistrationService regService;

    @Autowired
    private ViewJxsqsService service;

    @GetMapping("/viewJxsqs/getMfrs/{queryStr}")
    public AjaxResult getMfrs(@PathVariable("queryStr") String queryStr) {
        if (queryStr.length() < BasRegistrationController.QUERY_STR_LEN) {
            throw new IllegalArgumentException("搜索字符必须大于等于3个");
        }
        AjaxResult<List<IdNameVo>> result = new AjaxResult<>();
        result.setData(regService.listMfrs(queryStr));
        return result;
    }

    @GetMapping("/viewJxsqs/getHos/{queryStr}")
    public AjaxResult getHos(@PathVariable("queryStr") String queryStr) {
        if (queryStr.length() < BasRegistrationController.QUERY_STR_LEN) {
            throw new IllegalArgumentException("搜索字符必须大于等于3个");
        }
        AjaxResult<List<IdNameVo>> result = new AjaxResult<>();
        result.setData(regService.listHos(queryStr));
        return result;
    }

    @GetMapping("/viewJxsqs/listGoodsInfo")
    public AjaxResult listCerts(@RequestParam(required = false) String input,
                                @RequestParam(required = false) String mfrsId,
                                @RequestParam(required = false) String hosId, // 按医院区分产品
                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                @RequestParam(required = false, defaultValue = "30") Integer size) {
        AjaxResult<QueryResult<GoodsInfoVo>> result = new AjaxResult<>();
        result.setData(service.listGoodsInfo(input, mfrsId, hosId, page, size));
        return result;
    }

    @GetMapping("/viewJxsqs/getTrackingChain/{goodsId}")
    public AjaxResult getTrackingChain(@PathVariable("goodsId") String goodsId,
                                       @RequestParam(value = "hosId", required = false) String hosId) {
        AjaxResult<List<TreeNode<JxsqsData>>> result = new AjaxResult<>();
        result.setData(service.getTrackingChain(goodsId, hosId));
        return result;
    }

    @PostMapping("/viewJxsqs/getSqsHos")
    public AjaxResult getSqsHos(@RequestBody Set<String> sqsIds) {
        if (sqsIds.isEmpty()) {
            throw new IllegalArgumentException("非法参数。");
        }
        AjaxResult<Map<String, List<String>>> result = new AjaxResult<>();
        result.setData(service.getSqsHos(sqsIds));
        return result;
    }

    @GetMapping({"/viewJxsqs/getExcel/{hosId}/{hosName}/{xus}/", "/mdqcService/viewJxsqs/getExcel/{hosId}/{hosName}/"})
    public ResponseEntity<Resource> getExcel(@PathVariable("hosId") String hosId,
                                             @PathVariable("hosName") String hosName) throws IOException {
        ByteArrayOutputStream outputStream = service.getExcel(hosId, hosName);
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
        outputStream.close();
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String filename = hosName + LocalDate.now().toString() + ".xlsx";
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(filename, "UTF-8") + "\"")
                .body(resource);
    }
}
