package gyqx.spdhdi.provManager.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gyqx.spdhdi.myInfos.vo.CompanyInfoVo;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import gyqx.spdhdi.myInfos.vo.ProvRegistInfoPushVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.provManager.service.INewProvInfoVoService;

@Controller
@RequestMapping(value = "provManager/newProvInfo")
public class NewProvInfoVoController extends BaseController {
	@Resource
	INewProvInfoVoService newProvInfoVoService;

	/**
	 * 医院查询新供应商列表信息
	 * @param queryInfo 查询参数对象
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "newProvInfoVoList")
	@ResponseBody
	public AjaxResult<QueryResult<ProvHosInfoVo>> getNewProvInfoVoList(@RequestBody QueryInfo<ProvHosInfoVo> queryInfo)
			throws Exception {
		AjaxResult<QueryResult<ProvHosInfoVo>> result = new AjaxResult<QueryResult<ProvHosInfoVo>>();
		QueryResult<ProvHosInfoVo> lstProvHosInfoVoList = newProvInfoVoService.getNewProvInfoVoList(queryInfo);
		result.setData(lstProvHosInfoVoList);
		return result;
	}

	@RequestMapping(value = "getProvCompanyInfo")
	@ResponseBody
	public AjaxResult<CompanyInfoVo> getCompanyInfo(HttpServletRequest request, HttpServletResponse response,
													@RequestBody QueryInfo<ProvRegistInfoPushVo> queryInfo) throws Exception {
		AjaxResult<CompanyInfoVo> result = new AjaxResult<CompanyInfoVo>();
		CompanyInfoVo companyInfoVo = newProvInfoVoService.getProvCompanyInfo(queryInfo);
		result.setData(companyInfoVo);
		return result;
	}


}
