package ${groupPackageName}.controller;


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
import ${basePackageName}.po.${className};
import ${groupPackageName}.service.I${className}Service;

<#list imports as item>
import ${item.packageName}.${item.className};
</#list>

@Controller
@RequestMapping(value = "${url}")
public class ${className}Controller extends BaseController 
{
	@Resource
	private I${className}Service  ${pname}Service;

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody @Valid ${className} ${pname} ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		${className} ret = ${pname}Service.add(${pname});
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "get/{id}")
	@ResponseBody
	public AjaxResult get(@PathVariable("id") String id ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		${className} ret = ${pname}Service.get(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid ${className} ${pname} ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		${className} ret = ${pname}Service.update(${pname});
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid ${className} ${pname} ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<${className}> ret = ${pname}Service.list(${pname});
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String,String>> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<${className}> ret = ${pname}Service.query(queryInfo.getPredicate(),queryInfo.getOrderBy(),queryInfo.getQueryObject());
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody @Valid QueryInfo<${className}> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<${className}> ret = ${pname}Service.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
}
