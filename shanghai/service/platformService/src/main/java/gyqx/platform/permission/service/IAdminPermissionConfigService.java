package gyqx.platform.permission.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.permission.vo.AdminRoleConfig;
import gyqx.platform.permission.vo.CompanyInfo;
import gyqx.platform.permission.vo.MenuConfig;
import gyqx.platform.permission.vo.MenuItem;
import gyqx.platform.permission.vo.OrgAdminUser;
import gyqx.platform.permission.vo.UserOrg;
import gyqx.platform.sys.vo.SysProjectMenuVo;

public interface IAdminPermissionConfigService 
{
	List<CompanyInfo> listCompanies() throws Exception;
	List<OrgAdminUser> listOrgAdminUser(String corpId,String orgId) throws Exception;	
	List<OrgAdminUser> listOrgUsers(String corpId,String orgId) throws Exception;	
	List<UserOrg> listOrg(String corpId) throws Exception;	
	List<OrgAdminUser> listCompanyAdmin(String corpId) throws Exception;	
	List<SysProjectMenuVo> 		listMenu() throws Exception;
	
	List<MenuItem> 		listProjectMenu(String projectCode) throws Exception;
	
	List<String>	listAdminOrgsConfig(String userId) throws Exception;
	List<String>	listAdminMenusConfig(String userId) throws Exception;
	List<AdminRoleConfig>	listAdminRolesConfig(String corpId,String userId) throws Exception;
	
	void setAdminFlag(String corpId, String userId,String flag) throws Exception;
	void setAdminOrgsConfig(String userId,List<String> orgIds) throws Exception;
	void setAdminMenusConfig(String userId,List<MenuConfig> menuConfigs) throws Exception;
	void setAdminRolesConfig(String userId,List<String> roleIds) throws Exception;
	
	void setAdminAllConfig(String userId,List<String> orgIds,List<MenuConfig> menuConfigs,List<String> roleIds) throws Exception;	
	
	List<AdminRoleConfig> listCompanyDefaultRolesConfig(String corpId) throws Exception;
	void setCompanyDefaultRolesConfig(String corpId,String roleId,String flag) throws Exception;
}


