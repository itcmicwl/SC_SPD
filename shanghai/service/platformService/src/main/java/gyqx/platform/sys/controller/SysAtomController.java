package gyqx.platform.sys.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.AtomItem;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.po.SysAtom;
import gyqx.platform.po.SysConfig;
import gyqx.platform.sys.dao.SysUserDao;
import gyqx.platform.sys.service.ISysAtomService;

@Controller
@RequestMapping(value = "sys/atom")
public class SysAtomController extends BaseController {
	@Resource
	ISysAtomService sysAtomService;
	@Resource
	SysUserDao udao;

	@RequestMapping(value = "getSysAtomMap")
	@ResponseBody
	public AjaxResult<HashMap<String, AtomItem>> getSysAtomMap() throws Exception {
		AjaxResult<HashMap<String, AtomItem>> result = new AjaxResult<HashMap<String, AtomItem>>();
		result.setData(sysAtomService.getAtomItemMap());
		return result;
	}

	@RequestMapping(value = "getSysAtomList")
	@ResponseBody
	public AjaxResult<QueryResult<SysAtom>> getSysAtomList(@RequestBody QueryInfo<SysAtom> queryInfo) throws Exception {
		AjaxResult<QueryResult<SysAtom>> result = new AjaxResult<QueryResult<SysAtom>>();
		result.setData(sysAtomService.getSysAtomList(queryInfo));
		return result;
	}
}
