package gyqx.spdhdi.baseData.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.baseData.service.IBasMfrsInfoService;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.po.BasMfrsInfo;
@Controller
@RequestMapping(value = "bas/mfrsInfo")
public class BasMfrsInfoController extends BaseController {
	@Resource
	IBasMfrsInfoService basMfrsInfoService;

	@RequestMapping(value = "importMfrsCerts")
	@SuppressWarnings("rawtypes")
	@ResponseBody
	public AjaxResult importMfrsCerts(HttpServletRequest request, HttpServletResponse response,@RequestBody Map map) 
			throws Exception {
		AjaxResult result = new AjaxResult<>();
		basMfrsInfoService.saveMfrsCertsInfo(map);
		return result;
	}
	
	@RequestMapping(value = "basMfrsInfoList")
	@ResponseBody
	public AjaxResult<QueryResult<BasMfrsInfoVo>> getBasMfrsInfoList(@RequestBody QueryInfo<BasMfrsInfo> queryInfo) throws Exception {
		AjaxResult<QueryResult<BasMfrsInfoVo>> result = new AjaxResult<QueryResult<BasMfrsInfoVo>>();
		QueryResult<BasMfrsInfoVo> lstSysProject = basMfrsInfoService.getBasMfrsInfoList(queryInfo);
		result.setData(lstSysProject);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult insertBasMfrsInfo(@RequestBody @Valid BasMfrsInfo basMfrsInfo) throws Exception {
		AjaxResult result = new AjaxResult();
//		if (sysProjectMenuVo.getCode() == null || "".equals(sysProjectMenuVo.getCode())) {
//			ValidateException.Throw("code", "编码不能为空", "");
//		}
//		boolean flag = basMfrsInfoService.existProMenuCode(sysProjectMenuVo);
//		if (flag) {
//			ValidateException.Throw("code", String.format("%s 已存在。", sysProjectMenuVo.getCode()),
//					sysProjectMenuVo.getCode());
//		}
		int res = basMfrsInfoService.insertBasMfrsInfo(basMfrsInfo);
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult setBasMfrsInfo(@RequestBody @Valid BasMfrsInfoVo basMfrsInfo) throws Exception {
		AjaxResult result = new AjaxResult();
		if (basMfrsInfo.getId() == null || "".equals(basMfrsInfo.getId())) {
			ValidateException.Throw("ID", "ID不能为空", "");
		}
//		if (sysProjectMenuVo.getCode() == null || "".equals(sysProjectMenuVo.getCode())) {
//			ValidateException.Throw("code", "编码不能为空", "");
//		}
//		boolean flag = basMfrsInfoService.existProMenuCode(sysProjectMenuVo);
//		if (flag) {
//			ValidateException.Throw("code", String.format("%s 已存在。", sysProjectMenuVo.getCode()),
//					sysProjectMenuVo.getCode());
//		}
		int res = basMfrsInfoService.setBasMfrsInfo(basMfrsInfo);
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	@RequestMapping(value = "setStatus")
	@ResponseBody
	public AjaxResult<BasMfrsInfo> setStatus(@RequestBody BasMfrsInfoVo basMfrsInfo) throws Exception {
		AjaxResult<BasMfrsInfo> res = new AjaxResult<BasMfrsInfo>();
		int flag = basMfrsInfoService.setStatus(basMfrsInfo);
		if(flag>0){
			res.setCode(0);
			res.setMsg("操作失败");
		}else{
			res.setCode(-1);
			res.setMsg("操作成功");
		}
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxResult deleteByPrimaryKey(@RequestBody @Valid BasMfrsInfoVo basMfrsInfo) throws Exception {
		AjaxResult result = new AjaxResult();
		if (basMfrsInfo.getId() == null || "".equals(basMfrsInfo.getId())) {
			ValidateException.Throw("ID", "ID不能为空", "");
		}
		int res = basMfrsInfoService.deleteByPrimaryKey(basMfrsInfo.getId());
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
}
