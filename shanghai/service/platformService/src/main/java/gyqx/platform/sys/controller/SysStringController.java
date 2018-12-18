package gyqx.platform.sys.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.po.SysString;
import gyqx.platform.sys.service.ISysStringService;
import gyqx.platform.sys.vo.SysStringVo;

@Controller
@RequestMapping(value = "sys/string")
public class SysStringController extends BaseController {
	@Resource
	ISysStringService sysStringService;

	@RequestMapping(value = "sysStringList")
	@ResponseBody
	public AjaxResult<QueryResult<SysString>> getSysStringList(@RequestBody QueryInfo<SysString> queryInfo) throws Exception {
		AjaxResult<QueryResult<SysString>> result = new AjaxResult<QueryResult<SysString>>();
		QueryResult<SysString> lstSysConfig = sysStringService.getSysStringList(queryInfo);
		result.setData(lstSysConfig);
		return result;
	}
	
	@RequestMapping(value = "sysStringVoList")
	@ResponseBody
	public AjaxResult<QueryResult<SysStringVo>> getSysStringVoList(@RequestBody QueryInfo<SysStringVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<SysStringVo>> result = new AjaxResult<QueryResult<SysStringVo>>();
		QueryResult<SysStringVo> lstSysConfig = sysStringService.getSysStringVoList(queryInfo);
		result.setData(lstSysConfig);
		return result;
	}
	
	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult<SysString> insertSysConfig(@RequestBody SysString sysString) throws Exception {
		AjaxResult<SysString> res = new AjaxResult<SysString>();
		if (sysString == null) {
			res.setCode(-1);
			res.setMsg("操作失败");
			return res;
		}
		if ("".equals(sysString.getEname())) {
			res.setCode(-2);
			res.setMsg("配置项名称不能为空");
			return res;
		}
		SysString ss = new SysString();
		ss.setId(sysString.getId());
//		boolean isExist = sysStringService.existSysString(ss);
//		if (isExist) {
//			res.setCode(-2);
//			res.setMsg(String.format("%s的ID已存在。", sysString.getId()));
//			return res;
//		}
		
//		sysStringService.insertSysString(sysString);
//		res.setCode(0);
//		res.setMsg("操作成功");
//		
		int flag = sysStringService.insertSysString(sysString);
		if (flag > 0) {
			res.setCode(0);
			res.setMsg("操作成功");
		} else {
			res.setCode(-1);
			res.setMsg("操作失败");
		}
		return res;
	}

	
	@RequestMapping(value = "setStatus")
	@ResponseBody
	public AjaxResult<SysString> setStatus(@RequestBody SysString sysString) throws Exception {
		AjaxResult<SysString> res = new AjaxResult<SysString>();
		int flag = sysStringService.setStatus(sysString);
		if(flag>0){
			res.setCode(0);
			res.setMsg("操作失败");
		}else{
			res.setCode(-1);
			res.setMsg("操作成功");
		}
		return res;
	}
	
	
	
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult<SysString> setSysString(@RequestBody SysString sysString) throws Exception {
		AjaxResult<SysString> res = new AjaxResult<SysString>();
		if (sysString == null) {
			res.setCode(-1);
			res.setMsg("操作失败");
			return res;
		}
		if ("".equals(sysString.getEname())) {
			res.setCode(-2);
			res.setMsg("配置项名称不能为空");
			return res;
		}
//		SysConfig sc = new SysConfig();
//		sc.setEname(sysString.getEname());
//		sc.setId(sysString.getId());
//		boolean isExist = sysStringService.existSysConfig(sc);
//		if (isExist) {
//			res.setCode(-2);
//			res.setMsg(String.format("%s的配置项名称已存在。", sysString.getEname()));
//			return res;
//		}
		int flag = sysStringService.setSysString(sysString);
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
