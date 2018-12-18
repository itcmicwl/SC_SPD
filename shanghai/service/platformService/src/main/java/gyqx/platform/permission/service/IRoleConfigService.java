package gyqx.platform.permission.service;

import java.util.List;

import gyqx.platform.permission.vo.MenuPageviewPermission;
import gyqx.platform.permission.vo.MenuPageviewPermission.MenuPermissionSet;
import gyqx.platform.po.SysRole;

public interface IRoleConfigService 
{
	SysRole addRole(SysRole role) throws Exception;
	SysRole updateRole(SysRole role) throws Exception;
	void deleteRole(SysRole role) throws Exception;
	List<SysRole> listTemplateRoles();
	
	List<SysRole> getCompanyAllRole(String corpId) throws Exception;
	List<SysRole> listUserAllRoles(String userId);
	void saveUserRoles(String userId,List<String> roleIds) throws Exception;
	List<SysRole> listRolesManagedByAdmin(String adminId) throws Exception;	

	List<String> getRoleMenuIds(String roleId) throws Exception;
	
	MenuPageviewPermission getMenuElementsPermission(String menuId);
	MenuPermissionSet getRoleMenuElementsPermits(String menuId,String roleId);
	
	List<String> getUserMenuElementCodes(String userId,String menuId) throws Exception;
	
	void saveMenuPermission(String roleId,MenuPermissionSet mps ) throws Exception;
	
	void saveRoleMenuPermissions(String roleId,List<String> menuIds) throws Exception;
	
	void deleteRoleMenuPermissions(String roleId,List<String> menuIds) throws Exception;

}
