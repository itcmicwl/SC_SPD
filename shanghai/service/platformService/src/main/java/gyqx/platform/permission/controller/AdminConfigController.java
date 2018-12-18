package gyqx.platform.permission.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import common.base.KV;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.permission.service.IAdminPermissionConfigService;
import gyqx.platform.permission.vo.AdminRoleConfig;
import gyqx.platform.permission.vo.MenuConfig;
import gyqx.platform.permission.vo.OrgAdminUser;

@RestController
@RequestMapping(value = "permission/adminConfig")
public class AdminConfigController extends BaseController 
{
	@Resource
	private IAdminPermissionConfigService adminPermissionConfigService;
	
	@RequestMapping(value="listCompanies")
	public AjaxResult listCompanies() throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listCompanies());
		return result;
	}
	
	@RequestMapping(value="listOrgAdminUser/{corpId}/{orgId}")
	public AjaxResult listOrgAdminUser(@PathVariable("corpId") String corpId,@PathVariable("orgId")String orgId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listOrgAdminUser(corpId, orgId));
		return result;
	}
	@RequestMapping(value="listOrgUsers/{corpId}/{orgId}")
	public AjaxResult listOrgUsers(@PathVariable("corpId") String corpId,@PathVariable("orgId")String orgId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listOrgUsers(corpId, orgId));
		return result;
	}
	
	@RequestMapping(value="listOrg/{corpId}")
	public AjaxResult listOrg(@PathVariable("corpId")String corpId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listOrg(corpId));
		return result;
	}
	
	@RequestMapping(value="listCompanyAdmin/{corpId}")
	public AjaxResult listCompanyAdmin(@PathVariable("corpId")String corpId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listCompanyAdmin(corpId));
		return result;
	}
	
	@RequestMapping(value="listMenu")
	public AjaxResult listMenu() throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listMenu());
		return result;
	}
	@RequestMapping(value="listProjectMenu/{projectCode}")
	public AjaxResult listProjectMenu(@PathVariable("projectCode") String projectCode) throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listProjectMenu(projectCode));
		return result;
	}
	
	@RequestMapping(value="listAdminOrgsConfig/{userId}")
	public AjaxResult listAdminOrgsConfig(@PathVariable("userId")String userId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listAdminOrgsConfig(userId));
		return result;
	}
	
	@RequestMapping(value="listAdminMenusConfig/{userId}")
	public AjaxResult listAdminMenusConfig(@PathVariable("userId")String userId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listAdminMenusConfig(userId));
		return result;
	}
	
	@RequestMapping(value="listAdminRolesConfig/{corpId}/{userId}")
	public AjaxResult listAdminRolesConfig(@PathVariable("corpId")String corpId,@PathVariable("userId")String userId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listAdminRolesConfig(corpId,userId));
		return result;
	}
	
	@RequestMapping(value="listAdminAllConfig/{corpId}/{userId}")
	public AjaxResult listAdminAllConfig(@PathVariable("corpId")String corpId,@PathVariable("userId")String userId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		Map map = KV.from("orgs",adminPermissionConfigService.listOrg(corpId),
				"menu",adminPermissionConfigService.listMenu(),
				"orgIds",adminPermissionConfigService.listAdminOrgsConfig(userId),
				"menuIds",adminPermissionConfigService.listAdminMenusConfig(userId),
				"roles",adminPermissionConfigService.listAdminRolesConfig(corpId,userId));
		result.setData(map);
		return result;
		
	}
	
	@RequestMapping(value="setAdminFlag/{corpId}/{userId}/{flag}")
	public AjaxResult setAdminFlag(@PathVariable("corpId")String corpId, @PathVariable("userId")String userId,@PathVariable("flag")String flag) throws Exception
	{
		AjaxResult result = new AjaxResult();
		adminPermissionConfigService.setAdminFlag(corpId,userId,flag);
		return result;
	}
	
	@RequestMapping(value="setAdminOrgsConfig/{userId}")
	public AjaxResult setAdminOrgsConfig(@PathVariable("userId")String userId,List<String> orgIds) throws Exception
	{
		AjaxResult result = new AjaxResult();
		adminPermissionConfigService.setAdminOrgsConfig(userId,orgIds);
		return result;
	}
	
	@RequestMapping(value="setAdminMenusConfig/{userId}")
	public AjaxResult setAdminMenusConfig(@PathVariable("userId")String userId,List<MenuConfig> menuIds) throws Exception
	{
		AjaxResult result = new AjaxResult();
		adminPermissionConfigService.setAdminMenusConfig(userId,menuIds);
		return result;
	}
	
	@RequestMapping(value="setAdminRolesConfig/{userId}")
	public AjaxResult setAdminRolesConfig(@PathVariable("userId")String userId,@RequestBody List<String> roleIds) throws Exception
	{
		AjaxResult result = new AjaxResult();
		adminPermissionConfigService.setAdminRolesConfig(userId,roleIds);
		return result;
	}
	public static class AdminAllConfig
	{
		private List<String> orgIds;
		private List<MenuConfig> menuConfigs;
		private List<String> roleIds;
		public List<String> getOrgIds() {
			return orgIds;
		}
		public void setOrgIds(List<String> orgIds) {
			this.orgIds = orgIds;
		}
		public List<MenuConfig> getMenuConfigs() {
			return menuConfigs;
		}
		public void setMenuConfigs(List<MenuConfig> menuConfigs) {
			this.menuConfigs = menuConfigs;
		}
		public List<String> getRoleIds() {
			return roleIds;
		}
		public void setRoleIds(List<String> roleIds) {
			this.roleIds = roleIds;
		}
		
	}
	@RequestMapping(value="setAdminAllConfig/{userId}")
	public AjaxResult setAdminAllConfig(@PathVariable("userId")String userId,@RequestBody AdminAllConfig config ) throws Exception
	{
		AjaxResult result = new AjaxResult();
		
		List<String> orgIds = config.getOrgIds();
		List<MenuConfig> menuConfigs = config.getMenuConfigs();
		List<String> roleIds = config.getRoleIds();
		
		adminPermissionConfigService.setAdminAllConfig(userId,orgIds,menuConfigs,roleIds);
		
		return result;
	}
	
	@RequestMapping(value="listCompanyDefaultRolesConfig/{corpId}")
	public AjaxResult listCompanyDefaultRolesConfig(@PathVariable("corpId")String corpId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		result.setData(adminPermissionConfigService.listCompanyDefaultRolesConfig(corpId));
		return result;
	}
	@RequestMapping(value="setCompanyDefaultRolesConfig/{corpId}/{roleId}/{flag}")
	public AjaxResult setCompanyDefaultRolesConfig(@PathVariable("corpId")String corpId, @PathVariable("roleId") String roleId,@PathVariable("flag") String flag) throws Exception
	{
		AjaxResult result = new AjaxResult();
		adminPermissionConfigService.setCompanyDefaultRolesConfig(corpId,roleId,flag);
		return result;
	}
	
}
