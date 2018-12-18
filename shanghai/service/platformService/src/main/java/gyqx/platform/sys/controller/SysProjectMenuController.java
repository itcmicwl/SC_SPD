package gyqx.platform.sys.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.sys.service.ISysProjectMenuService;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysProjectMenuVo;
@Controller
@RequestMapping(value = "sys/proMenu")
public class SysProjectMenuController extends BaseController {
	@Resource
	ISysProjectMenuService sysProjectMenuService;

	@RequestMapping(value = "sysProMenuList")
	@ResponseBody
	public AjaxResult<QueryResult<SysProjectMenuVo>> getSysProjectList(
			@RequestBody QueryInfo<SysProjectMenuVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<SysProjectMenuVo>> result = new AjaxResult<QueryResult<SysProjectMenuVo>>();
		QueryResult<SysProjectMenuVo> lstSysProject = sysProjectMenuService.getSysProMenuList(queryInfo);
		result.setData(lstSysProject);
		return result;
	}
	@RequestMapping(value = "sysParentProMenuList")
	@ResponseBody
	public AjaxResult<List<ElTreeVo>> getsysParentProMenuList(
			@RequestBody SysProjectMenuVo sysProjectMenuVo) throws Exception {
		AjaxResult<List<ElTreeVo>> result = new AjaxResult<List<ElTreeVo>>();
		List<ElTreeVo> lstSysProject = sysProjectMenuService.getParentSysProMenuList(sysProjectMenuVo);
		result.setData(lstSysProject);
		return result;
	}
	@GetMapping(value = "sysMenuKindList/{projectCode}/ss")
	@ResponseBody
	public AjaxResult<List<SysProjectMenuVo>> sysMenuKindList(@PathVariable("projectCode") String projectCode) throws Exception {
		AjaxResult result = new AjaxResult<List<ElTreeVo>>();
		List<SysProjectMenuVo> lstSysProject = sysProjectMenuService.sysMenuKindList(projectCode);
		result.setData(lstSysProject);
		return result;
	}
	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult insertSysProjectMenu(@RequestBody @Valid SysProjectMenuVo sysProjectMenuVo) throws Exception {
		AjaxResult result = new AjaxResult();
//		if (sysProjectMenuVo.getCode() == null || "".equals(sysProjectMenuVo.getCode())) {
//			ValidateException.Throw("code", "编码不能为空", "");
//		}
//		boolean flag = sysProjectMenuService.existProMenuCode(sysProjectMenuVo);
//		if (flag) {
//			ValidateException.Throw("code", String.format("%s 已存在。", sysProjectMenuVo.getCode()),
//					sysProjectMenuVo.getCode());
//		}
		int res = sysProjectMenuService.insertProMenu(sysProjectMenuVo);
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult updateSysProjectMenu(@RequestBody SysProjectMenuVo sysProjectMenuVo) throws Exception {
		AjaxResult result = new AjaxResult();
		if (sysProjectMenuVo.getId() == null || "".equals(sysProjectMenuVo.getId())) {
			ValidateException.Throw("ID", "ID不能为空", "");
		}
//		if (sysProjectMenuVo.getCode() == null || "".equals(sysProjectMenuVo.getCode())) {
//			ValidateException.Throw("code", "编码不能为空", "");
//		}
//		boolean flag = sysProjectMenuService.existProMenuCode(sysProjectMenuVo);
//		if (flag) {
//			ValidateException.Throw("code", String.format("%s 已存在。", sysProjectMenuVo.getCode()),
//					sysProjectMenuVo.getCode());
//		}
		int res = sysProjectMenuService.updateProMenu(sysProjectMenuVo);
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxResult deleteSysProjectMenu(@RequestBody SysProjectMenuVo sysProjectMenuVo) throws Exception {
		AjaxResult result = new AjaxResult();
		if (sysProjectMenuVo.getId() == null || "".equals(sysProjectMenuVo.getId())) {
			ValidateException.Throw("ID", "ID不能为空", "");
		}		
		int res = sysProjectMenuService.deleteProMenu(sysProjectMenuVo.getId());
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	@RequestMapping(value = "ProMenuTree")
	@ResponseBody
	public AjaxResult<List<SysProjectMenuVo>> getSysProjectMenuTree(@RequestBody SysProjectMenuVo sysProjectMenuVo) throws Exception {
		AjaxResult<List<SysProjectMenuVo>> result = new AjaxResult<List<SysProjectMenuVo>>();
		if (sysProjectMenuVo.getProjectCode() == null || "".equals(sysProjectMenuVo.getProjectCode())) {
			ValidateException.Throw("projectCode", "projectCode不能为空", "");
		}		
		List<SysProjectMenuVo> data = sysProjectMenuService.getSysProjectMenuTree(sysProjectMenuVo);
		result.setData(data);
		return result;
	}
	@GetMapping(value = "projectMenuTree/{projectCode}/list")
	@ResponseBody
	public AjaxResult<List<SysProjectMenuVo>> getSysProjectMenuTree(@PathVariable("projectCode") String projectCode) throws Exception {
		AjaxResult<List<SysProjectMenuVo>> result = new AjaxResult<List<SysProjectMenuVo>>();
		if (StringUtils.isEmpty(projectCode)) {
			ValidateException.Throw("projectCode", "projectCode不能为空", "");
		}
		SysProjectMenuVo sysProjectMenuVo = new SysProjectMenuVo();
		sysProjectMenuVo.setProjectCode(projectCode);
		List<SysProjectMenuVo> data = sysProjectMenuService.getSysProjectMenuTree(sysProjectMenuVo);
		result.setData(data);
		return result;
	}
}
