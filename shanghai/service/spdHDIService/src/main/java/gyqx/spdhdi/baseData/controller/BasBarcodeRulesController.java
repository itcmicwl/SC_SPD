package gyqx.spdhdi.baseData.controller;


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
import gyqx.spdhdi.po.BasBarcodeRules;
import gyqx.spdhdi.baseData.service.IBasBarcodeRulesService;

import java.util.List;
import java.util.Map;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;

@Controller
@RequestMapping(value = "baseData/basBarcodeRules")
public class BasBarcodeRulesController extends BaseController 
{
	@Resource
	private IBasBarcodeRulesService  basBarcodeRulesService;

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody @Valid BasBarcodeRules basBarcodeRules ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		BasBarcodeRules ret = basBarcodeRulesService.add(basBarcodeRules);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "get/{id}")
	@ResponseBody
	public AjaxResult get(@PathVariable("id") String id ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		BasBarcodeRules ret = basBarcodeRulesService.get(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid BasBarcodeRules basBarcodeRules ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		BasBarcodeRules ret = basBarcodeRulesService.update(basBarcodeRules);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxResult delete(@RequestBody @Valid BasBarcodeRules basBarcodeRules ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		basBarcodeRulesService.delete(basBarcodeRules);
		return result;
	}	
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid BasBarcodeRules basBarcodeRules ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<BasBarcodeRules> ret = basBarcodeRulesService.list(basBarcodeRules);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String,String>> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<BasBarcodeRules> ret = basBarcodeRulesService.query(queryInfo.getPredicate(),queryInfo.getOrderBy(),queryInfo.getQueryObject());
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody @Valid QueryInfo<BasBarcodeRules> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<BasBarcodeRules> ret = basBarcodeRulesService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
}
