package gyqx.spdhdi.baseData.controller;

import java.util.List;

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

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.DictionaryUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.baseData.service.BasRegInfoService;
import gyqx.spdhdi.baseData.vo.BasRegInfoVo;
import gyqx.spdhdi.baseData.vo.BasRegistrationInfoVo;

/**
 * Created by liangwu on 17-6-6.
 * 基础资料/产品注册证基本信息
 */
@RestController
@RequestMapping("/basRegInfo")
@CrossOrigin
public class BasRegInfoController extends BaseController {
    @Resource
    private BasRegInfoService basRegInfoService;

    /**
     * 获取注册证类型字典
     * @return 返回字典列表
     */
    @GetMapping("/getDicts")
    public AjaxResult<List<DictionaryUtils.DictionaryValue>> getDicts() {
        AjaxResult<List<DictionaryUtils.DictionaryValue>> result = new AjaxResult<>();
        result.setData(basRegInfoService.findDicts());
        return result;
    }

    /**
     * 获取生产厂家产品注册证列表
     *
     * @param mfrsId   生产厂商ID
     * @param pageNum  当前页
     * @param pageSize 每页行数
     * @return 返回注册证列表及分页信息
     */
    @GetMapping("/getCerts")
    public AjaxResult<QueryResult<BasRegInfoVo>> getCerts(@RequestParam("mfrsId") String mfrsId,
                               @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {
        AjaxResult<QueryResult<BasRegInfoVo>> result = new AjaxResult<>();
        result.setData(basRegInfoService.findCerts(mfrsId, pageNum, pageSize));
        return result;
    }
    
    @RequestMapping(value = "getRegCerts")
	@ResponseBody
	public AjaxResult<QueryResult<BasRegInfoVo>> getRegCerts(HttpServletRequest request, 
			HttpServletResponse response,@RequestBody QueryInfo<BasRegInfoVo> queryInfo)throws Exception {
		QueryResult<BasRegInfoVo> queryResult = basRegInfoService.getCerts(queryInfo);
		AjaxResult<QueryResult<BasRegInfoVo>> result = new AjaxResult<QueryResult<BasRegInfoVo>>();
		result.setData(queryResult);
		return result;
	}

    /**
     * 新增厂商注册证基本信息
     * @param basRegInfoVo 注册证基本信息
     * @param errors 验证返回错误
     * @return 返回注册证基本信息ID值
     * @throws Exception
     */
    @PostMapping("/addCert")
    public AjaxResult<String> addCert(@RequestBody @Valid BasRegistrationInfoVo basRegInfoVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(basRegInfoService.addCert(basRegInfoVo));
        return result;
    }

    /**
     * 修改厂商注册证基本信息
     * @param basRegInfoVo 修改后注册证基本信息
     * @param errors 参数验证错误信息
     * @return 数据修改后注册证版本号
     * @throws Exception
     */
    @PutMapping("/modCert")
    public AjaxResult<Integer> modCert(@RequestBody @Valid BasRegistrationInfoVo basRegInfoVo, Errors errors) throws Exception {
        filterErrors(errors);
        AjaxResult<Integer> result = new AjaxResult<>();
        result.setData(basRegInfoService.updateCert(basRegInfoVo));
        return result;
    }

    /**
     * 删除注册证基本信息
     * @param id 产品注册证ID
     * @param version 产品注册证版本号
     * @return “OK"
     * @throws Exception
     */
    @DeleteMapping("/delCert")
    public AjaxResult<String> delCert(@RequestParam(value = "id", defaultValue = "") String id,
                                      @RequestParam(value = "version", defaultValue = "") Integer version) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        result.setData(basRegInfoService.deleteCert(id, version));
        return result;
    }
}
