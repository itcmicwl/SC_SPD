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
import gyqx.platform.po.SysProject;
import gyqx.platform.sys.service.ISysProjectService;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysProjectVo;

@Controller
@RequestMapping(value = "sys/project")
public class SysProjectCrontroller extends BaseController {
	@Resource
	ISysProjectService sysProjectService;

	@RequestMapping(value = "sysProjectList")
	@ResponseBody
	public AjaxResult<QueryResult<SysProjectVo>> getSysProjectList(@RequestBody QueryInfo<SysProject> queryInfo) throws Exception {
		AjaxResult<QueryResult<SysProjectVo>> result = new AjaxResult<QueryResult<SysProjectVo>>();
		QueryResult<SysProjectVo> lstSysProject = sysProjectService.getSysProjectList(queryInfo);
		result.setData(lstSysProject);
		return result;
	}
	@RequestMapping(value = "pSysProList")
	@ResponseBody
	public AjaxResult<List<SysProject>> getParentSysProList() throws Exception {
		AjaxResult<List<SysProject>> result = new AjaxResult<List<SysProject>>();
		QueryInfo<SysProject> queryInfo = new QueryInfo<SysProject>();
		List<SysProject> lstSysProject = sysProjectService.getParentSysProList(queryInfo);
		result.setData(lstSysProject);
		return result;
	}

	@RequestMapping(value = "getSysProjectById")
	public AjaxResult<SysProject> getSysProjectByid(String id) throws Exception {
		SysProject sysProject = sysProjectService.getSysProjectByid(id);
		AjaxResult<SysProject> res = new AjaxResult<SysProject>();
		res.setData(sysProject);
		return res;
	}
	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult<SysProject> insertSysProject(@RequestBody SysProject sysProject) throws Exception {
		AjaxResult<SysProject> res = new AjaxResult<SysProject>();
		if (sysProject == null) {
			res.setCode(-1);
			res.setMsg("操作失败");
			return res;
		}
		if ("".equals(sysProject.getEname())) {			
			ValidateException.Throw("name","名称不能为空","");
		}
		SysProject sc = new SysProject();
		sc.setEname(sysProject.getEname());
		boolean isExist = sysProjectService.existSysProject(sc);
		if (isExist) {
			ValidateException.Throw("name",String.format("%s的配置项名称已存在。", sysProject.getEname()),sysProject.getEname());
		}
		int flag = sysProjectService.insertSysProject(sysProject);
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
	public AjaxResult<SysProject> setSysProject(@RequestBody SysProject sysProject) throws Exception {
		AjaxResult<SysProject> res = new AjaxResult<SysProject>();
		if (sysProject == null) {
			res.setCode(-1);
			res.setMsg("操作失败");
			return res;
		}
		if ("".equals(sysProject.getEname())) {
			res.setCode(-2);
			res.setMsg("配置项名称不能为空");
			return res;
		}
		SysProject sc = new SysProject();
		sc.setEname(sysProject.getEname());
		sc.setId(sysProject.getId());
		boolean isExist = sysProjectService.existSysProject(sc);
		if (isExist) {
			res.setCode(-2);
			res.setMsg(String.format("%s的配置项名称已存在。", sysProject.getEname()));
			return res;
		}
		int flag = sysProjectService.setSysProject(sysProject);
		if (flag > 0) {
			res.setCode(0);
			res.setMsg("操作成功");
		} else {
			res.setCode(-1);
			res.setMsg("操作失败");
		}
		return res;
	}
	@RequestMapping(value = "delSysProjectByCode")
	@ResponseBody
	public AjaxResult<SysProject> delSysProject(@RequestBody SysProjectVo sysProject) throws Exception {
		AjaxResult<SysProject> res = new AjaxResult<SysProject>();
		if (sysProject == null || sysProject.getCode()==null) {
			res.setCode(-1);
			res.setMsg("操作失败！");
			return res;
		}		
		int flag = sysProjectService.delSysProjectByCode(sysProject.getCode());
		if (flag > 0) {
			res.setCode(0);
			res.setMsg("操作成功");
		} else {
			res.setCode(-1);
			res.setMsg("操作失败");
		}
		return res;
	}
	@RequestMapping(value = "sysProjectTree")
	@ResponseBody
	public AjaxResult<List<ElTreeVo>> sysProjectTree() throws Exception {
		AjaxResult<List<ElTreeVo>> res = new AjaxResult<List<ElTreeVo>>();
		List<ElTreeVo> treeResult = sysProjectService.getSysProjectTree();
		res.setData(treeResult);
		return res;
	}
}
