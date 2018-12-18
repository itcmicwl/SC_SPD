package gyqx.spdhdi.myInfos.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.myInfos.service.IProvHosInfoVoService;
import gyqx.spdhdi.myInfos.vo.HosInfoVo;
import gyqx.spdhdi.myInfos.vo.HosProvVo;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import gyqx.spdhdi.po.BasCompanyInfo;
@Controller
@RequestMapping(value = "myInfo/provHosInfo")
public class ProvHosInfoVoController extends BaseController {
	@Resource
	IProvHosInfoVoService provHosInfoVoService;

	/**
	 * 供应商查询我的医院列表信息
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "provHosInfoVoList")
	@ResponseBody
	public AjaxResult<QueryResult<ProvHosInfoVo>> getProvHosInfoVoList(
			@RequestBody QueryInfo<ProvHosInfoVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<ProvHosInfoVo>> result = new AjaxResult<QueryResult<ProvHosInfoVo>>();
		QueryResult<ProvHosInfoVo> lstProvHosInfoVoList = provHosInfoVoService.getProvHosInfoVoList(queryInfo);
		result.setData(lstProvHosInfoVoList);
		return result;
	}
	
	/**
	 * 供应商新增我的医院下拉列表数据
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getHosInfoVoList")
	@ResponseBody
	public AjaxResult<QueryResult<HosInfoVo>> getHosInfoVoList(@RequestBody QueryInfo<HosInfoVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<HosInfoVo>> result = new AjaxResult<QueryResult<HosInfoVo>>();
		QueryResult<HosInfoVo> lstProvHosInfoVoList = provHosInfoVoService.getHosInfoVoList(queryInfo);
		result.setData(lstProvHosInfoVoList);
		return result;
	}
	
	/**
	 * 医院新增供应商获取供应商下拉选项
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getHosProvVoList")
	@ResponseBody
	public AjaxResult<QueryResult<HosProvVo>> getHosProvVoList(@RequestBody QueryInfo<HosProvVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<HosProvVo>> result = new AjaxResult<QueryResult<HosProvVo>>();
		QueryResult<HosProvVo> lstProvHosInfoVoList = provHosInfoVoService.getHosProvVoList(queryInfo);
		result.setData(lstProvHosInfoVoList);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult insertProvHosInfo(@RequestBody @Valid ProvHosInfoVo provHosInfoVo) throws Exception {
		AjaxResult result = new AjaxResult();
//		if (sysProjectMenuVo.getCode() == null || "".equals(sysProjectMenuVo.getCode())) {
//			ValidateException.Throw("code", "编码不能为空", "");
//		}
//		boolean flag = provHosInfoVoService.existProMenuCode(sysProjectMenuVo);
//		if (flag) {
//			ValidateException.Throw("code", String.format("%s 已存在。", sysProjectMenuVo.getCode()),
//					sysProjectMenuVo.getCode());
//		}
		int res = provHosInfoVoService.insertProvHosInfo(provHosInfoVo);
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "insertBatch")
	@ResponseBody
	public AjaxResult insertProvHosInfoBatch(@RequestBody List<BasCompanyInfo> companyInfoVos,@RequestParam("hosId") String hosId) throws Exception {
		AjaxResult result = new AjaxResult();
		provHosInfoVoService.insertProvHosInfoBatch(hosId,companyInfoVos);
		return result;
	}	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult setProvHosInfo(@RequestBody @Valid ProvHosInfoVo provHosInfoVo) throws Exception {
		AjaxResult result = new AjaxResult();
		int res = provHosInfoVoService.setProvHosInfo(provHosInfoVo);
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	@RequestMapping(value = "setStatus")
	@ResponseBody
	public AjaxResult<ProvHosInfoVo> setStatus(@RequestBody ProvHosInfoVo provHosInfoVo) throws Exception {
		AjaxResult<ProvHosInfoVo> res = new AjaxResult<ProvHosInfoVo>();
		int flag = provHosInfoVoService.setStatus(provHosInfoVo);
		if(flag>0){
			res.setCode(0);
			res.setMsg("操作失败");
		}else{
			res.setCode(-1);
			res.setMsg("操作成功");
		}
		return res;
	}
	
	@RequestMapping(value = "setHosView")
	@ResponseBody
	public AjaxResult<ProvHosInfoVo> setHosView(@RequestBody ProvHosInfoVo provHosInfoVo) throws Exception {
		AjaxResult<ProvHosInfoVo> res = new AjaxResult<ProvHosInfoVo>();
		int flag = provHosInfoVoService.setHosView(provHosInfoVo);
		if(flag>0){
			res.setCode(0);
			res.setMsg("操作成功");
		}else{
			res.setCode(-1);
			res.setMsg("操作失败");
		}
		return res;
	}	
	
	/**
	 * （根据医院ID）解除关系
	 * @param provHosInfoVo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxResult deleteByPrimaryKey(@RequestBody @Valid ProvHosInfoVo provHosInfoVo) throws Exception {
		AjaxResult result = new AjaxResult();
		if (StringUtils.isEmpty(provHosInfoVo.getHosId())) {
			ValidateException.Throw("ID", "医院ID不能为空", "");
		}
		int res = provHosInfoVoService.deleteByHosId(provHosInfoVo.getHosId());
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	/**
	 * 集配商批量删除医院和供应商关系
	 * @param provHosInfoVo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteBatch")
	@ResponseBody
	public AjaxResult deleteHosProvBatch(@RequestBody @Valid List<ProvHosInfoVo> provHosInfoVos) throws Exception {
		AjaxResult result = new AjaxResult();
		provHosInfoVoService.deleteHosProvBatch(provHosInfoVos);
		return result;
	}	
	
	
	/**
	 * 查询医院供应商信息列表
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getHosProvCompanyInfoVoList")
	@ResponseBody
	public AjaxResult<QueryResult<ProvHosInfoVo>> getHosProvCompanyInfoVoList(
			@RequestBody QueryInfo<ProvHosInfoVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<ProvHosInfoVo>> result = new AjaxResult<QueryResult<ProvHosInfoVo>>();
		QueryResult<ProvHosInfoVo> lstProvCompanyInfoVoList = provHosInfoVoService.getHosProvCompanyInfoVoList(queryInfo);
		result.setData(lstProvCompanyInfoVoList);
		return result;
	}
	
	/**
	 * 查询还未与医院建立关系的供应商列表
	 * @param request
	 * @param response
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getNotRelatedCompanys")
	@ResponseBody
	public AjaxResult<QueryResult<BasCompanyInfo>> getNotRelatedCompanys(HttpServletRequest request, HttpServletResponse response, @RequestBody QueryInfo<HosInfoVo> queryInfo)
			throws Exception {
		QueryResult<BasCompanyInfo> queryResult= provHosInfoVoService.getNotRelatedCompanysByHosId(queryInfo);
		AjaxResult<QueryResult<BasCompanyInfo>> result = new AjaxResult<QueryResult<BasCompanyInfo>>();
		result.setData(queryResult);
		return result;
	}
}
