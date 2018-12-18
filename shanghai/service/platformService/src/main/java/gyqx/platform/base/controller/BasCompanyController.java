package gyqx.platform.base.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.base.service.IBasCompanyService;
import gyqx.platform.base.vo.BasCompanyInfoVo;
import gyqx.platform.base.vo.CollectorProvCertificateVo;
import gyqx.platform.base.vo.ProvVo;
import gyqx.spdhdi.po.BasCompanyInfo;

@Controller
@RequestMapping(value = "bas/company")
public class BasCompanyController extends BaseController {
	@Resource
	private IBasCompanyService basCompanyService;
	
	@RequestMapping(value = "importCompanyCerts")
	@SuppressWarnings("rawtypes")
	@ResponseBody
	public AjaxResult importCompanyCerts(HttpServletRequest request, HttpServletResponse response,@RequestBody Map map) 
			throws Exception {
		AjaxResult result = new AjaxResult<>();
		basCompanyService.saveCompanyCertsInfo(map);
		return result;
	}

	@RequestMapping(value = "insertMyProv")
	@ResponseBody
	public AjaxResult insert(@RequestBody @Valid BasCompanyInfo company,@RequestParam("hosId")String hosId, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		company.setFlag("1");
		company= basCompanyService.insertMyProv(company,hosId);
		return result;
	}
	
	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult insert(@RequestBody @Valid BasCompanyInfo company,Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		company.setFlag("1");
		company= basCompanyService.addBasCompany(company);
		result.setData(company);
		return result;
	}
	
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid BasCompanyInfo company, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		company= basCompanyService.updateBasCompany(company);
		return result;
	}
	@RequestMapping(value = "delCompany")
	@ResponseBody
	public AjaxResult delCompany(@RequestBody @Valid BasCompanyInfo company, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		basCompanyService.deleteBasCompany(company);
		return result;
	}
	@RequestMapping(value = "getCompanys")
	@ResponseBody
	public AjaxResult getCompanys(HttpServletRequest request, HttpServletResponse response, @RequestBody QueryInfo<BasCompanyInfo> queryInfo)
			throws Exception {

		QueryResult<BasCompanyInfo> queryResult=   basCompanyService.listBasCompany(queryInfo);
		AjaxResult result = new AjaxResult();
		result.setData(queryResult);
		return result;
	}
	@RequestMapping(value = "getMyProvs")
	@ResponseBody
	public AjaxResult<QueryResult<BasCompanyInfoVo>> getMyProvs(HttpServletRequest request, HttpServletResponse response, @RequestBody QueryInfo<ProvVo> queryInfo)
			throws Exception {
		QueryResult<BasCompanyInfoVo> queryResult = basCompanyService.getMyProvs(queryInfo);
		AjaxResult<QueryResult<BasCompanyInfoVo>> result = new AjaxResult<QueryResult<BasCompanyInfoVo>>();
		result.setData(queryResult);
		return result;
	}
	@RequestMapping(value = "getMyProvCerts")
	@ResponseBody
	public AjaxResult<List<CollectorProvCertificateVo>> getMyProvCerts(HttpServletRequest request, HttpServletResponse response, @RequestBody BasCompanyInfoVo vo)
			throws Exception {
		AjaxResult<List<CollectorProvCertificateVo>> result = new AjaxResult<List<CollectorProvCertificateVo>>();
		List<CollectorProvCertificateVo> queryResult = basCompanyService.getMyProvCerts(vo.getHcpId(),vo.getId());
		result.setData(queryResult);
		return result;
	}
	@RequestMapping(value = "delMyProv")
	@ResponseBody
	public AjaxResult delMyProv(@RequestBody @Valid BasCompanyInfoVo companyVo, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		basCompanyService.delMyProv(companyVo);
		return result;
	}
	@RequestMapping(value = "companysList")
	@ResponseBody
	public AjaxResult<List<BasCompanyInfo>> getCompanyList(@RequestBody BasCompanyInfo basCompanyInfo) throws Exception {
		List<BasCompanyInfo> queryResult=  basCompanyService.getListBasCompany(basCompanyInfo);
		AjaxResult<List<BasCompanyInfo>> result = new AjaxResult<List<BasCompanyInfo>>();
		result.setData(queryResult);
		return result;
	}
}
