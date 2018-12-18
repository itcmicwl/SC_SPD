package gyqx.spdhdi.mycerts.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;
import gyqx.mdqc.mycerts.vo.MyMfrsVo;
import gyqx.mdqc.mycerts.vo.MyProvParamVo;
import gyqx.spdhdi.myInfos.vo.CompanyInfoVo;
import gyqx.spdhdi.mycerts.service.IMyMfrsService;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import gyqx.spdhdi.po.ProvMfrsCertificateImage;

@Controller
@RequestMapping(value = "myCerts/myMfrs")
public class MyMfrsController extends BaseController {
	
	@Resource
	private IMyMfrsService myMfrsService;
	
	@RequestMapping(value = "getMyMfrsInfo")
	@ResponseBody
	public AjaxResult<QueryResult<MyMfrsVo>> getMyMfrsInfo(@RequestBody QueryInfo<MyProvParamVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<MyMfrsVo>> result = new AjaxResult<QueryResult<MyMfrsVo>>();
		QueryResult<MyMfrsVo> mfrsList = myMfrsService.getMyMfrsInfo(queryInfo);
		result.setData(mfrsList);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateMyMfrsFlag")
	@ResponseBody
	public AjaxResult updateMyMfrsFlag(@RequestBody MyMfrsVo vo) throws Exception {
		AjaxResult result = new AjaxResult();
		myMfrsService.updateMyMfrsFlag(vo);
		return result;
	}
	
	@RequestMapping(value = "deleteMfrsCerts")
	@ResponseBody
	public AjaxResult<Integer> deleteMfrsCerts(@RequestBody ProvMfrsCertificateVo companyCert) throws Exception {
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		int res = myMfrsService.deleteMfrsCerts(companyCert);
		if (res < 0) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteImage")
	@ResponseBody
	public AjaxResult deleteImage(@RequestBody @Valid ProvMfrsCertificateImage image) throws Exception {
		AjaxResult result = new AjaxResult();
		int res = myMfrsService.deleteImage(image.getId());
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "addMyMfrsInfo")
	@ResponseBody
	public AjaxResult addMyMfrsInfo(@RequestBody List<BasCompanyInfoVo> mfrsInfos) throws Exception {
		AjaxResult result = new AjaxResult();
		myMfrsService.addMyMfrsInfo(mfrsInfos);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateMfrsCerts")
	@ResponseBody
	public AjaxResult updateMfrsCerts(@RequestBody  ProvMfrsCertificateVo cert, Errors error) throws Exception {
		AjaxResult<CompanyInfoVo> res = new AjaxResult<CompanyInfoVo>();
		filterErrors(error);
		myMfrsService.updateMfrsCerts(cert);
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "addImage")
	@ResponseBody
	public AjaxResult addImage(@RequestBody ProvMfrsCertificateImage image, Errors error) throws Exception {
		AjaxResult res = new AjaxResult();
		filterErrors(error);
		myMfrsService.addImage(image);
		return res;
	}
	
	@RequestMapping(value = "addMyMfrsCerts")
	@ResponseBody
	public AjaxResult<ProvMfrsCertificateVo> addMyMfrsCerts(@RequestBody ProvMfrsCertificateVo certVo, Errors error) throws Exception {
		AjaxResult<ProvMfrsCertificateVo> res = new AjaxResult<ProvMfrsCertificateVo>();
		filterErrors(error);
		ProvMfrsCertificateVo vo = myMfrsService.addMyMfrsCerts(certVo);
		res.setData(vo);
		return res;
	}
	
	
}
