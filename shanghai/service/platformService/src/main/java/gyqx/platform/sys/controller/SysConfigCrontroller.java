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
import gyqx.platform.po.SysConfig;
import gyqx.platform.sys.service.ISysConfigService;

@Controller
@RequestMapping(value = "sys/config")
public class SysConfigCrontroller extends BaseController {
	@Resource
	ISysConfigService sysConfigService;

	@RequestMapping(value = "sysConfigList")
	@ResponseBody
	public AjaxResult<QueryResult<SysConfig>> getSysConfigList(@RequestBody QueryInfo<SysConfig> queryInfo) throws Exception {
		AjaxResult<QueryResult<SysConfig>> result = new AjaxResult<QueryResult<SysConfig>>();
		QueryResult<SysConfig> lstSysConfig = sysConfigService.getSysConfigList(queryInfo);
		result.setData(lstSysConfig);
		return result;
	}

	@RequestMapping(value = "getSysConfigById")
	public AjaxResult<SysConfig> getSysConfigByid(String id) throws Exception {
		SysConfig sysConfig = sysConfigService.getSysConfigByid(id);
		AjaxResult<SysConfig> res = new AjaxResult<SysConfig>();
		res.setData(sysConfig);
		return res;
	}

	@RequestMapping(value = "getSysConfigByName")
	public AjaxResult<SysConfig> getSysConfigByName(String Name) throws Exception {
		SysConfig sysConfig = sysConfigService.getSysConfigByName(Name);
		AjaxResult<SysConfig> res = new AjaxResult<SysConfig>();
		res.setData(sysConfig);
		return res;
	}

	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult<SysConfig> insertSysConfig(@RequestBody SysConfig sysConfig) throws Exception {
		AjaxResult<SysConfig> res = new AjaxResult<SysConfig>();
		if (sysConfig == null) {
			res.setCode(-1);
			res.setMsg("操作失败");
			return res;
		}
		if ("".equals(sysConfig.getEname())) {
			res.setCode(-2);
			res.setMsg("配置项名称不能为空");
			return res;
		}
		SysConfig sc = new SysConfig();
		sc.setEname(sysConfig.getEname());
		boolean isExist = sysConfigService.existSysConfig(sc);
		if (isExist) {
			res.setCode(-2);
			res.setMsg(String.format("%s的配置项名称已存在。", sysConfig.getEname()));
			return res;
		}
		int flag = sysConfigService.insertSysConfig(sysConfig);
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
	public AjaxResult<SysConfig> setStatus(@RequestBody SysConfig sysconfig) throws Exception {
		AjaxResult<SysConfig> res = new AjaxResult<SysConfig>();
		int flag = sysConfigService.setStatus(sysconfig);
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
	public AjaxResult<SysConfig> setSysConfig(@RequestBody SysConfig sysConfig) throws Exception {
		AjaxResult<SysConfig> res = new AjaxResult<SysConfig>();
		if (sysConfig == null) {
			res.setCode(-1);
			res.setMsg("操作失败");
			return res;
		}
		if ("".equals(sysConfig.getEname())) {
			res.setCode(-2);
			res.setMsg("配置项名称不能为空");
			return res;
		}
		SysConfig sc = new SysConfig();
		sc.setEname(sysConfig.getEname());
		sc.setId(sysConfig.getId());
		boolean isExist = sysConfigService.existSysConfig(sc);
		if (isExist) {
			res.setCode(-2);
			res.setMsg(String.format("%s的配置项名称已存在。", sysConfig.getEname()));
			return res;
		}
		int flag = sysConfigService.setSysConfig(sysConfig);
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
