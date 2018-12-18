package gyqx.platform.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.sys.vo.SysModuleFunctionVo;
import gyqx.platform.sys.service.ISysModuleFunctionService;

@Controller
@RequestMapping(value = "sys/modelFun")
public class SysModuleFunctionCrontroller extends BaseController {
	@Resource
	ISysModuleFunctionService sysModuleFunctionService;

	@RequestMapping(value = "modelFunList")
	@ResponseBody
	public AjaxResult<QueryResult<SysModuleFunctionVo>> getSysModuleFunctionVoList(@RequestBody QueryInfo<SysModuleFunctionVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<SysModuleFunctionVo>> result = new AjaxResult<QueryResult<SysModuleFunctionVo>>();
		QueryResult<SysModuleFunctionVo> lstSysModuleFunctionVo = sysModuleFunctionService.getSysModuleFunctionVoList(queryInfo);
		result.setData(lstSysModuleFunctionVo);
		return result;
	}	

	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult<SysModuleFunctionVo> insertSysModuleFunctionVo(@RequestBody SysModuleFunctionVo sysModuleFunctionVo) throws Exception {
		AjaxResult<SysModuleFunctionVo> res = new AjaxResult<SysModuleFunctionVo>();
		if (sysModuleFunctionVo == null) {
			res.setCode(-1);
			res.setMsg("操作失败");
			return res;
		}
		if ("".equals(sysModuleFunctionVo.getEname())) {			
			ValidateException.Throw("name","名称不能为空","");
		}
		if ("".equals(sysModuleFunctionVo.getCode())) {			
			ValidateException.Throw("code","code不能为空","");
		}
		SysModuleFunctionVo smf = new SysModuleFunctionVo();
		smf.setEname(sysModuleFunctionVo.getEname());
		smf.setModuleId(sysModuleFunctionVo.getModuleId());
		smf.setCode(sysModuleFunctionVo.getCode());
		boolean isExistName = sysModuleFunctionService.existNameInModule(smf);
		boolean isExistCode = sysModuleFunctionService.existCodeInSys(smf);
		if (isExistName) {
			ValidateException.Throw("name",String.format("%s 已存在。", sysModuleFunctionVo.getEname()),sysModuleFunctionVo.getEname());
		}
		if (isExistCode) {
			ValidateException.Throw("code",String.format("%s 已存在。", sysModuleFunctionVo.getCode()),sysModuleFunctionVo.getCode());
		}
		int flag = sysModuleFunctionService.insertSysModuleFunctionVo(sysModuleFunctionVo);
		if (flag > 0) {
			res.setCode(0);
			res.setMsg("操作成功");
		} else {
			res.setCode(-1);
			res.setMsg("操作失败");
		}
		return res;
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult<SysModuleFunctionVo> setSysModuleFunctionVo(@RequestBody SysModuleFunctionVo sysModuleFunctionVo) throws Exception {
		AjaxResult<SysModuleFunctionVo> res = new AjaxResult<SysModuleFunctionVo>();
		if (sysModuleFunctionVo == null) {
			res.setCode(-1);
			res.setMsg("操作失败");
			return res;
		}
		if ("".equals(sysModuleFunctionVo.getEname())) {			
			ValidateException.Throw("name","名称不能为空","");
		}
		if ("".equals(sysModuleFunctionVo.getCode())) {			
			ValidateException.Throw("code","code不能为空","");
		}
		SysModuleFunctionVo smf = new SysModuleFunctionVo();
		smf.setId(sysModuleFunctionVo.getId());
		smf.setModuleId(sysModuleFunctionVo.getModuleId());
		smf.setEname(sysModuleFunctionVo.getEname());
		smf.setCode(sysModuleFunctionVo.getCode());
		boolean isExistName = sysModuleFunctionService.existNameInModule(smf);
		boolean isExistCode = sysModuleFunctionService.existCodeInSys(smf);
		if (isExistName) {
			ValidateException.Throw("name",String.format("%s 名称已存在。", sysModuleFunctionVo.getEname()),sysModuleFunctionVo.getEname());
		}
		if (isExistCode) {
			ValidateException.Throw("code",String.format("%s code已存在。", sysModuleFunctionVo.getCode()),sysModuleFunctionVo.getCode());
		}
		int flag = sysModuleFunctionService.setSysModuleFunctionVo(sysModuleFunctionVo);
		if (flag > 0) {
			res.setCode(0);
			res.setMsg("操作成功");
		} else {
			res.setCode(-1);
			res.setMsg("操作失败");
		}
		return res;
	}
	@RequestMapping(value = "delSysModuleFunById")
	@ResponseBody
	public AjaxResult<SysModuleFunctionVo> delSysModuleFunctionVo(@RequestBody SysModuleFunctionVo sysModuleFunctionVo) throws Exception {
		AjaxResult<SysModuleFunctionVo> res = new AjaxResult<SysModuleFunctionVo>();
		if (sysModuleFunctionVo == null || sysModuleFunctionVo.getCode()==null) {
			res.setCode(-1);
			res.setMsg("操作失败！");
			return res;
		}		
		int flag = sysModuleFunctionService.delSysModuleFunction(sysModuleFunctionVo.getId());
		if (flag > 0) {
			res.setCode(0);
			res.setMsg("操作成功");
		} else {
			res.setCode(-1);
			res.setMsg("操作失败");
		}
		return res;
	}
}
