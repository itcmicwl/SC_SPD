package gyqx.spdhdi.collector4orderMgr.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdherp.po.HosCollector;
import gyqx.spdhdi.collector4orderMgr.service.IHosCollectorService;

import java.util.List;
import java.util.Map;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;

@Controller
@RequestMapping(value = "collector4orderMgr/hosCollector")
public class HosCollectorController extends BaseController 
{
	@Resource
	private IHosCollectorService  hosCollectorService;

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody @Valid HosCollector hosCollector ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosCollector ret = hosCollectorService.add(hosCollector);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "get/{id}")
	@ResponseBody
	public AjaxResult get(@PathVariable("id") String id ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosCollector ret = hosCollectorService.get(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid HosCollector hosCollector ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosCollector ret = hosCollectorService.update(hosCollector);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid HosCollector hosCollector ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<HosCollector> ret = hosCollectorService.list(hosCollector);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String,String>> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<HosCollector> ret = hosCollectorService.query(queryInfo.getPredicate(),queryInfo.getOrderBy(),queryInfo.getQueryObject());
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody @Valid QueryInfo<HosCollector> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<HosCollector> ret = hosCollectorService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
}
