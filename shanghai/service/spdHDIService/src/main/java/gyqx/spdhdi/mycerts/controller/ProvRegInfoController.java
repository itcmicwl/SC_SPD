package gyqx.spdhdi.mycerts.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import common.db.query.QueryResult;
import common.exception.ParameterException;
import common.utils.DictionaryUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.mycerts.service.ProvRegInfoService;
import gyqx.spdhdi.mycerts.vo.DlgBasRegInfoVo;
import gyqx.spdhdi.mycerts.vo.ProvGoodsInfo4CertVo;
import gyqx.spdhdi.mycerts.vo.ProvRegImageVo;
import gyqx.spdhdi.mycerts.vo.ProvRegInfoVo;
import gyqx.spdhdi.mycerts.vo.ProvRegistrationInfoVo;
import gyqx.spdhdi.po.ProvRegistrationImage;

/**
 * Created by liangwu on 17-6-12.
 */
@RestController
@RequestMapping("/provRegInfo")
@CrossOrigin
public class ProvRegInfoController extends BaseController {
    @Resource
    private ProvRegInfoService service;

    @RequestMapping(value = "importMfrsCerts")
	@SuppressWarnings("rawtypes")
	@ResponseBody
	public AjaxResult importMfrsCerts(HttpServletRequest request, HttpServletResponse response,@RequestBody Map map) 
			throws Exception {
		AjaxResult result = new AjaxResult<>();
		service.importRegCerts(map);
		return result;
	}
    
    @GetMapping("/getCertKind")
    public AjaxResult<List<DictionaryUtils.DictionaryValue>> getCertKind() {
        AjaxResult<List<DictionaryUtils.DictionaryValue>> result = new AjaxResult<>();
        result.setData(service.findDicts());
        return result;
    }

    @GetMapping("/getProvRegInfo") //<QueryResult<ProvRegInfoVo>>
    public AjaxResult getProvRegInfo(@RequestParam(value = "certificateCode", required = false) String certificateCode,
                                     @RequestParam(value = "productName", required = false) String productName,
                                     @RequestParam("pageNum") int pageNum,
                                     @RequestParam("pageSize") int pageSize) {
        AjaxResult<QueryResult<ProvRegInfoVo>> result = new AjaxResult<>();
        result.setData(service.findItemsCerts(certificateCode, productName, pageNum, pageSize));
        return result;
    }

    // 用于新增、修改界面注册证信息下拉列表
    @GetMapping("/getBasRegInfo")
    public AjaxResult<List<DlgBasRegInfoVo>> getBasRegInfo(@RequestParam(value = "mfrsId", defaultValue = "") String mfrsId,
                                                           @RequestParam(value = "certCode", defaultValue = "") String certCode,
                                                           @RequestParam(value = "regKind", defaultValue = "") String regKind) {
        AjaxResult<List<DlgBasRegInfoVo>> result = new AjaxResult<>();
        result.setData(service.findBasRegInfo(mfrsId, certCode, regKind));
        return result;
    }


    @GetMapping("/getMfrs")
    public AjaxResult<List<BasMfrsInfoVo>> getMfrs(@RequestParam(value = "mfrsName", defaultValue = "") String mfrsName) {
        AjaxResult<List<BasMfrsInfoVo>> result = new AjaxResult<>();
        result.setData(service.findMfrs(mfrsName));
        return result;
    }

    @PostMapping("/addCert")
    public AjaxResult<String> addCert(@RequestBody @Valid ProvRegInfoVo regInfo, Errors errors) throws Exception {
        filterErrors(errors);
        if (regInfo.getExpdtBeginDate().getTime() > regInfo.getExpdtEndDate().getTime()) {
            throw new ParameterException("有效期开始日期不能大于结束日期！");
        }
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.addCert(regInfo));
        return result;
    }

    @DeleteMapping("/delCert")
    public AjaxResult<String> delCert(@RequestParam(value = "id", defaultValue = "") String id,
                                      @RequestParam(value = "version") int version) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.deleteCert(id, version));
        return result;
    }

    @PutMapping("/modCert")
    public AjaxResult<Integer> modCert(@RequestBody @Valid ProvRegInfoVo regInfo, Errors errors) throws Exception {
        filterErrors(errors);
        if (regInfo.getExpdtBeginDate().getTime() > regInfo.getExpdtEndDate().getTime()) {
            throw new ParameterException("有效期开始日期不能大于结束日期！");
        }
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(service.updateCert(regInfo));
        return result;
    }

    @PostMapping("/uploadPic")
    public AjaxResult<ProvRegImageVo> uploadFile(@RequestBody @Valid ProvRegistrationImage provRegImage) throws Exception {
        if (provRegImage.getCertificateId() == null || provRegImage.getFilePath() == null)
            throw new ParameterException("上传数据错误，请联系管理员。");
        AjaxResult<ProvRegImageVo> result = new AjaxResult<>();
        ProvRegImageVo uresult = service.addProvRegImage(provRegImage);
        result.setData(uresult);
        return result;
    }

    @GetMapping("/getCertImg")
    public AjaxResult<List<ProvRegImageVo>> getCertImg(@RequestParam("certId") String certId) {
        AjaxResult<List<ProvRegImageVo>> result = new AjaxResult<>();
        result.setData(service.findCertImg(certId));
        return result;
    }
    
    @GetMapping("/getProvRegsBySpdGoodsCode")
    public AjaxResult<List<ProvRegistrationInfoVo>> getProvRegsBySpdGoodsCode(@RequestParam("spdGoodsCode") String spdGoodsCode) throws Exception {
    	AjaxResult<List<ProvRegistrationInfoVo>> result = new AjaxResult<>();
    	result.setData(service.getProvRegsBySpdGoodsCode(spdGoodsCode));
    	return result;
    }

    @DeleteMapping("/delCertImg")
    public AjaxResult<String> delCertImg(@RequestParam("id") String id,
                                         @RequestParam("version") String version) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.deleteCertImg(id, version));
        return result;
    }

    @GetMapping("/getGoodsInfo") //<List<ProvGoodsInfo4CertVo>>
    public AjaxResult getGoodsInfo(@RequestParam("mfrsId") String mfrsId) {
        AjaxResult<List<ProvGoodsInfo4CertVo>> result = new AjaxResult<>();
        result.setData(service.findGoodsInfo(mfrsId));
        return result;
    }

    @PostMapping("/replaceCert")
    public AjaxResult<String> replaceCert(@RequestBody @Valid ProvRegInfoVo regInfo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(service.replaceCert(regInfo));
        return result;
    }
}
