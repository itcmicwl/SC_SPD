package gyqx.platform.permission.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import common.security.SecurityContext;
import common.utils.UtilsContext;
import gyqx.platform.permission.dao.RolePermisionConfigDao;
import gyqx.platform.permission.service.IRoleConfigService;
import gyqx.platform.permission.vo.MenuPageviewPermission;
import gyqx.platform.permission.vo.MenuPageviewPermission.MenuPermissionSet;
import gyqx.platform.po.SysRole;
import gyqx.platform.po.SysUserRole;

@Service
public class RoleConfigService implements IRoleConfigService 
{
	@Resource
	private RolePermisionConfigDao dao;
	@Resource
	private UtilsContext utils;
	
	@Override
	@CacheEvict(value="permits", allEntries=true)
	public SysRole addRole(SysRole role) throws Exception {

		return dao.addRole(role);
	}

	@Override
	@CacheEvict(value="permits", allEntries=true)
	public SysRole updateRole(SysRole role) throws Exception {
		if(role.getId().equals("0"))
			return addRole(role);
		role = dao.updateRole(role);
		
		utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis() );
		return role;
	}
	@Override
	@CacheEvict(value="permits", allEntries=true)
	public void deleteRole(SysRole role) throws Exception {			
		dao.deleteRole(role);
		utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis() );
	}

	@Override
	public List<SysRole> listTemplateRoles() {

		return dao.listTemplateRoles();
	}

	@Override
	public List<SysRole> getCompanyAllRole(String corpId) throws Exception {

		return dao.listCompanyAllRoles(corpId);
	}

	@Override
	public List<SysRole> listUserAllRoles(String userId) {

		return dao.listUserAllRoles(userId);
	}

	@Override
	@CacheEvict(value="permits", allEntries=true)
	public void saveUserRoles(String userId, List<String> roleIds) throws Exception {
		dao.saveUserRoles(userId, roleIds);
		utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis() );
	}

	@Override
	public List<SysRole> listRolesManagedByAdmin(String adminId) throws Exception {

		return dao.listRolesManagedByAdmin(adminId);
	}

	@Override
	public List<String> getRoleMenuIds(String roleId) throws Exception {

		return dao.getRoleMenuIds(roleId);
	}

	@Override
	public MenuPageviewPermission getMenuElementsPermission(String menuId) {

		return dao.getMenuElementsPermission(menuId);
	}

	@Override
	public MenuPermissionSet getRoleMenuElementsPermits(String menuId, String roleId) {

		return dao.getRoleMenuElementsPermits(menuId, roleId);
	}

	@Override
	@CacheEvict(value="permits", allEntries=true)
	public void saveMenuPermission(String roleId, MenuPermissionSet mps) throws Exception {
		dao.saveMenuPermission(roleId, mps);
		utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis() );
	}
	@Override
	@CacheEvict(value="permits", allEntries=true)
	public void saveRoleMenuPermissions(String roleId, List<String> menuIds) throws Exception {		
		dao.saveRoleMenuPermissions(roleId,menuIds);
	}

	@Override
	@CacheEvict(value="permits", allEntries=true)
	public void deleteRoleMenuPermissions(String roleId, List<String> menuIds) throws Exception {
		dao.deleteRoleMenuPermissions(roleId,menuIds);		
	}

	@Override
	public List<String> getUserMenuElementCodes(String userId, String menuId) throws Exception {
		// TODO Auto-generated method stub
		return dao.getUserMenuElementCodes(userId,menuId);
	}

}
