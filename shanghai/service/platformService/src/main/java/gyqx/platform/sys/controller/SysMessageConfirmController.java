package gyqx.platform.sys.controller;


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
import gyqx.platform.po.SysMessageConfirm;
import gyqx.platform.sys.service.ISysMessageConfirmService;

import java.util.List;
import java.util.Map;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;

@Controller
@RequestMapping(value = "sys/sysMessageConfirm")
public class SysMessageConfirmController extends BaseController 
{
	@Resource
	private ISysMessageConfirmService  sysMessageConfirmService;

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody @Valid SysMessageConfirm sysMessageConfirm ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		SysMessageConfirm ret = sysMessageConfirmService.add(sysMessageConfirm);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "get/{id}")
	@ResponseBody
	public AjaxResult get(@PathVariable("id") String id ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		SysMessageConfirm ret = sysMessageConfirmService.get(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid SysMessageConfirm sysMessageConfirm ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		SysMessageConfirm ret = sysMessageConfirmService.update(sysMessageConfirm);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid SysMessageConfirm sysMessageConfirm ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<SysMessageConfirm> ret = sysMessageConfirmService.list(sysMessageConfirm);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String,String>> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<SysMessageConfirm> ret = sysMessageConfirmService.query(queryInfo.getPredicate(),queryInfo.getOrderBy(),queryInfo.getQueryObject());
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody @Valid QueryInfo<SysMessageConfirm> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<SysMessageConfirm> ret = sysMessageConfirmService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getProUserMsgCount/{projectCode}/{userId}/")
	@ResponseBody
	public AjaxResult<Integer> getProUserMsgCount(@PathVariable("userId") String userId,@PathVariable("projectCode") String projectCode)  throws  Exception
	{
		AjaxResult<Integer> result = new AjaxResult<>();
		Integer ret = sysMessageConfirmService.getProUserMsgCount(projectCode,userId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "setMsgRead")
	@ResponseBody
	public AjaxResult<Integer> setMsgRead(@RequestBody SysMessageConfirm msgc)  throws  Exception
	{
		AjaxResult<Integer> result = new AjaxResult<>();
		Integer ret = sysMessageConfirmService.setMsgRead(msgc);
		result.setData(ret);
		return result;
	}
}
