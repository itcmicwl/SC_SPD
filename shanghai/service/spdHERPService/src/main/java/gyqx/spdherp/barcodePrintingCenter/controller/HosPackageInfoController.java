package gyqx.spdherp.barcodePrintingCenter.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdherp.barcodePrintingCenter.service.IHosPackageInfoService;
import gyqx.spdherp.barcodePrintingCenter.vo.HosPackageInfoVo;
import gyqx.spdherp.po.HosPackageInfo;
import gyqx.spdherp.stockout.vo.vo4In.OutSub4InVo;

@Controller
@RequestMapping(value = "barcodePrintingCenter/hosPackageInfo")
public class HosPackageInfoController extends BaseController {
	@Resource
	private IHosPackageInfoService hosPackageInfoService;

	@RequestMapping(value = "addHosPackageInfos")
	@ResponseBody
	public AjaxResult<List<HosPackageInfoVo>> addHosPackageInfos(@RequestBody Map<String,List<OutSub4InVo>> map) throws Exception {
		AjaxResult<List<HosPackageInfoVo>> result = new AjaxResult<List<HosPackageInfoVo>>();
		List<HosPackageInfoVo> lists = hosPackageInfoService.addHosPackageInfos(map);
		result.setData(lists);
		return result;
	}

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody @Valid HosPackageInfo hosPackageInfo, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosPackageInfo ret = hosPackageInfoService.add(hosPackageInfo);
		result.setData(ret);
		return result;
	}

	@RequestMapping(value = "get/{id}")
	@ResponseBody
	public AjaxResult get(@PathVariable("id") String id, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosPackageInfo ret = hosPackageInfoService.get(id);
		result.setData(ret);
		return result;
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid HosPackageInfo hosPackageInfo, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosPackageInfo ret = hosPackageInfoService.update(hosPackageInfo);
		result.setData(ret);
		return result;
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid HosPackageInfo hosPackageInfo, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<HosPackageInfo> ret = hosPackageInfoService.list(hosPackageInfo);
		result.setData(ret);
		return result;
	}

	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String, String>> queryInfo, Errors error)
			throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<HosPackageInfo> ret = hosPackageInfoService.query(queryInfo.getPredicate(), queryInfo.getOrderBy(),
				queryInfo.getQueryObject());
		result.setData(ret);
		return result;
	}

	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody @Valid QueryInfo<HosPackageInfo> queryInfo, Errors error)
			throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<HosPackageInfo> ret = hosPackageInfoService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
}
