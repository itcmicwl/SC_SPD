package gyqx.platform.permission.controller;


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

import common.base.KV;
import common.web.AjaxResult;
import common.web.BaseController;

import java.util.List;
import gyqx.platform.permission.vo.MenuPageviewPermission;
import gyqx.platform.permission.vo.MenuPageviewPermission.MenuPermissionSet;
import gyqx.platform.po.SysRole;
import gyqx.platform.permission.service.IRoleConfigService;

@Controller
@RequestMapping(value = "permission/roleConfig")
public class RoleConfigController extends BaseController 
{
	@Resource
	private IRoleConfigService  iRoleConfigService;

	@RequestMapping(value = "listUserAllRoles/{userId}")
	@ResponseBody
	public AjaxResult listUserAllRoles(@PathVariable("userId") String userId  ) throws  Exception
	{
		AjaxResult result = new AjaxResult();

		List<SysRole> ret = iRoleConfigService.listUserAllRoles(userId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "addRole")
	@ResponseBody
	public AjaxResult addRole(@RequestBody @Valid SysRole sysRole ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		result.setData(iRoleConfigService.addRole(sysRole));
		return result;
	}
	@RequestMapping(value = "updateRole")
	@ResponseBody
	public AjaxResult updateRole(@RequestBody @Valid SysRole sysRole ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		SysRole ret = iRoleConfigService.updateRole(sysRole);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "deleteRole")
	@ResponseBody
	public AjaxResult deleteRole(@RequestBody @Valid SysRole sysRole ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		iRoleConfigService.deleteRole(sysRole);
		return result;
	}
	@RequestMapping(value = "getRoleMenuIds/{roleId}")
	@ResponseBody
	public AjaxResult getRoleMenuIds(@PathVariable("roleId") String roleId  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();

		List<String> ret = iRoleConfigService.getRoleMenuIds(roleId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "saveUserRoles/{userId}")
	@ResponseBody
	public AjaxResult saveUserRoles(@PathVariable("userId") String userId,@RequestBody List<String> list ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		iRoleConfigService.saveUserRoles(userId,list);
		return result;
	}
	@RequestMapping(value = "listTemplateRoles")
	@ResponseBody
	public AjaxResult listTemplateRoles( ) 
	{
		AjaxResult result = new AjaxResult();
		List<SysRole> ret = iRoleConfigService.listTemplateRoles();
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getCompanyAllRole/{corpId}")
	@ResponseBody
	public AjaxResult getCompanyAllRole(@PathVariable("corpId") String corpId  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		
		List<SysRole> ret = iRoleConfigService.getCompanyAllRole(corpId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getCompanyAllRolesAndUserAllRoles/{corpId}/{userId}")
	@ResponseBody
	public AjaxResult getCompanyAllRolesAndUserAllRoles(@PathVariable("corpId") String corpId,
			@PathVariable("userId") String userId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		
		result.setData(
				KV.from(
						"corpRoles",iRoleConfigService.getCompanyAllRole(corpId)
						,"userRoles",iRoleConfigService.listUserAllRoles(userId)
						)
				);
		
		return result;
		
	}
	
	@RequestMapping(value = "listRolesManagedByAdmin/{adminId}")
	@ResponseBody
	public AjaxResult listRolesManagedByAdmin(@PathVariable("adminId") String adminId   )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();

		List<SysRole> ret = iRoleConfigService.listRolesManagedByAdmin(adminId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getMenuElementsPermission/{menuId}")
	@ResponseBody
	public AjaxResult getMenuElementsPermission(@PathVariable("menuId") String menuId   ) throws  Exception
	{
		AjaxResult result = new AjaxResult();

		MenuPageviewPermission ret = iRoleConfigService.getMenuElementsPermission(menuId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getRoleMenuElementsPermits/{menuId}/{roleId}")
	@ResponseBody
	public AjaxResult getRoleMenuElementsPermits(@PathVariable("menuId") String menuId,@PathVariable("roleId")  String roleId  ) throws  Exception
	{
		AjaxResult result = new AjaxResult();

		MenuPermissionSet ret = iRoleConfigService.getRoleMenuElementsPermits(menuId,roleId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "saveMenuPermission/{roleId}")
	@ResponseBody
	public AjaxResult saveMenuPermission(@PathVariable("roleId") String roleId,@RequestBody @Valid MenuPermissionSet menuPermissionSet ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		iRoleConfigService.saveMenuPermission(roleId,menuPermissionSet);
		return result;
	}
	@RequestMapping(value = "saveRoleMenuPermissions/{roleId}")
	@ResponseBody
	public AjaxResult saveRoleMenuPermissions(@PathVariable("roleId") String roleId,@RequestBody List<String> menuIds) throws Exception
	{
		AjaxResult result = new AjaxResult();
		iRoleConfigService.saveRoleMenuPermissions(roleId,menuIds);
		return result;
	}
	@RequestMapping(value = "deleteRoleMenuPermissions/{roleId}")
	@ResponseBody
	public AjaxResult deleteRoleMenuPermissions(@PathVariable("roleId") String roleId,@RequestBody List<String> menuIds) throws Exception
	{
		AjaxResult result = new AjaxResult();
		iRoleConfigService.deleteRoleMenuPermissions(roleId,menuIds);
		return result;
	}
	@RequestMapping(value = "getUserMenuElementCodes/{userId}/{menuId}")
	@ResponseBody
	public AjaxResult getUserMenuElementCodes(@PathVariable("userId")String userId, @PathVariable("menuId")String menuId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(iRoleConfigService.getUserMenuElementCodes(userId, menuId));
		return result;
	}
}
