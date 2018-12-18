package gyqx.platform.permission.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import common.web.AjaxResult;
import common.web.BaseController;

import java.util.List;
import java.util.Map;

import gyqx.platform.permission.vo.MenuPageviewPermission;
import gyqx.platform.permission.vo.ModuleFunction;
import gyqx.platform.po.SysPageview;
import gyqx.platform.po.SysPageviewElement;
import gyqx.platform.permission.service.IMenuPageviewItemsConfigService;

@RestController
@RequestMapping(value = "permission/menuPageviewItemsConfig")
public class MenuPageviewItemsConfigController extends BaseController 
{
	@Resource
	private IMenuPageviewItemsConfigService  iMenuPageviewItemsConfigService;
	
	
	@RequestMapping(value = "getUserMenuIds/{userId}")
	public AjaxResult getUserMenuIds(@PathVariable("userId")String userId)
	{
		AjaxResult result = new AjaxResult();
		List<String> ret = iMenuPageviewItemsConfigService.getUserMenuIds(userId);
		result.setData(ret);
		return result;
	}
	@GetMapping(value = "getUserMenusByProjectCode/{projectCode}/{userId}")
	@ResponseBody
	public AjaxResult getUserMenuIdsByProjectCode(@PathVariable("userId")String userId,@PathVariable("projectCode")String projectCode)throws  Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData( iMenuPageviewItemsConfigService.getUserMenus(userId,projectCode));
		return result;
	}

	@RequestMapping(value = "getPageviewElements/{menuId}")
	public AjaxResult getPageviewElements(@PathVariable("menuId") String menuId   ) throws  Exception
	{
		AjaxResult result = new AjaxResult();

		MenuPageviewPermission ret = iMenuPageviewItemsConfigService.getPageviewElements(menuId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getUserMenuElementIds/{userId}/{menuId}")
	public AjaxResult getUserMenuElementIds(@PathVariable("userId")String userId,@PathVariable("menuId")String menuId) 
	{
		AjaxResult result = new AjaxResult();
		result.setData(iMenuPageviewItemsConfigService.getUserMenuElementIds(userId,menuId));
		return result;
	}
	@RequestMapping(value = "getPageviewBindings/{pageviewId}")
	@ResponseBody
	public AjaxResult getPageviewBindings(@PathVariable("pageviewId") String pageviewId  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();

		List<ModuleFunction> ret = iMenuPageviewItemsConfigService.getPageviewBindings(pageviewId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getPageviewElementBindings/{elementId}")
	@ResponseBody
	public AjaxResult getPageviewElementBindings(@PathVariable("elementId") String elementId )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();

		List<ModuleFunction> ret = iMenuPageviewItemsConfigService.getPageviewElementBindings(elementId);
		result.setData(ret);
		return result;
	}
	
	@RequestMapping(value = "updateSysPageview")
	@ResponseBody
	public AjaxResult updateSysPageview(@RequestBody @Valid SysPageview sysPageview ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		SysPageview ret = iMenuPageviewItemsConfigService.updateSysPageview(sysPageview);
		result.setData(ret);
		return result;
	}
	
	@RequestMapping(value = "updateSysPageviewElement")
	@ResponseBody
	public AjaxResult updateSysPageviewElement(@RequestBody @Valid SysPageviewElement sysPageviewElement ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		SysPageviewElement ret = iMenuPageviewItemsConfigService.updateSysPageviewElement(sysPageviewElement);
		result.setData(ret);
		return result;
	}
	
	@RequestMapping(value = "deleteSysPageviewElement")
	@ResponseBody
	public AjaxResult deleteSysPageviewElement(@RequestBody @Valid SysPageviewElement sysPageviewElement ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		iMenuPageviewItemsConfigService.deleteSysPageviewElement(sysPageviewElement);
		
		return result;
	}
	
	@RequestMapping(value = "getAllProjects")
	public AjaxResult getAllProjects( ) 
	{
		AjaxResult result = new AjaxResult();
		Map<String,List> ret = iMenuPageviewItemsConfigService.getAllProjects();
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getModuleFunctions/{moduleId}")
	public AjaxResult getModuleFunctions(@PathVariable("moduleId") String moduleId ) throws  Exception
	{
		AjaxResult result = new AjaxResult();

		List<ModuleFunction> ret = iMenuPageviewItemsConfigService.getModuleFunctions(moduleId);
		result.setData(ret);
		return result;
	}
}
