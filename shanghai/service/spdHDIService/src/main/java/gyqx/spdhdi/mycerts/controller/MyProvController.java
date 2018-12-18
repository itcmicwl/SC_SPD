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
import gyqx.mdqc.mycerts.vo.CollectorProvCertVo;
import gyqx.mdqc.mycerts.vo.MyProvParamVo;
import gyqx.spdhdi.myInfos.vo.CollectorProvCertificateVo;
import gyqx.spdhdi.myInfos.vo.CompanyInfoVo;
import gyqx.spdhdi.mycerts.service.IMyProvService;
import gyqx.spdhdi.po.CollectorProvCertificate;
import gyqx.spdhdi.po.ProvCertificateImage;

@Controller
@RequestMapping(value = "myCerts/myProv")
public class MyProvController extends BaseController {
	
	@Resource
	private IMyProvService myProvService;
	
	@RequestMapping(value = "getMyProvInfo")
	@ResponseBody
	public AjaxResult<QueryResult<CollectorProvCertVo>> getProvInfoList(@RequestBody QueryInfo<MyProvParamVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<CollectorProvCertVo>> result = new AjaxResult<QueryResult<CollectorProvCertVo>>();
		QueryResult<CollectorProvCertVo> provs = myProvService.getMyProvInfo(queryInfo);
		result.setData(provs);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateMyProvFlag")
	@ResponseBody
	public AjaxResult updateMyProvFlag(@RequestBody CollectorProvCertVo vo) throws Exception {
		AjaxResult result = new AjaxResult();
		myProvService.updateMyProvFlag(vo);
		return result;
	}
	
	@RequestMapping(value = "deleteProvCerts")
	@ResponseBody
	public AjaxResult<Integer> deleteProvCerts(@RequestBody CollectorProvCertificateVo companyCert) throws Exception {
		AjaxResult<Integer> result = new AjaxResult<Integer>();
		int res = myProvService.deleteProvCerts(companyCert);
		if (res < 0) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteImage")
	@ResponseBody
	public AjaxResult deleteImage(@RequestBody @Valid ProvCertificateImage image) throws Exception {
		AjaxResult result = new AjaxResult();
		int res = myProvService.deleteImage(image.getId());
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "addMyProvInfo")
	@ResponseBody
	public AjaxResult addMyProvInfo(@RequestBody List<BasCompanyInfoVo> provInfos) throws Exception {
		AjaxResult result = new AjaxResult();
		myProvService.addMyProvInfo(provInfos);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateProvCerts")
	@ResponseBody
	public AjaxResult updateProvCerts(@RequestBody  CollectorProvCertificate cert, Errors error) throws Exception {
		AjaxResult<CompanyInfoVo> res = new AjaxResult<CompanyInfoVo>();
		filterErrors(error);
		myProvService.updateProvCerts(cert);
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "addImage")
	@ResponseBody
	public AjaxResult addImage(@RequestBody ProvCertificateImage image, Errors error) throws Exception {
		AjaxResult res = new AjaxResult();
		filterErrors(error);
		myProvService.addImage(image);
		return res;
	}
	
	@RequestMapping(value = "addMyProvCerts")
	@ResponseBody
	public AjaxResult<CollectorProvCertificateVo> addMyProvCerts(@RequestBody CollectorProvCertificateVo certVo, Errors error) throws Exception {
		AjaxResult<CollectorProvCertificateVo> res = new AjaxResult<CollectorProvCertificateVo>();
		filterErrors(error);
		CollectorProvCertificateVo vo =myProvService.addMyProvCerts(certVo);
		res.setData(vo);
		return res;
	}
	
	
}
