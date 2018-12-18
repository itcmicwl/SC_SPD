package gyqx.platform.permission.service.impl;

import javax.annotation.Resource;

import gyqx.platform.sys.vo.SysProjectMenuVo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;
import common.utils.UtilsContext;
import gyqx.platform.permission.dao.AdminPermissionConfigDao;
import gyqx.platform.permission.service.IAdminPermissionConfigService;
import gyqx.platform.permission.vo.AdminRoleConfig;
import gyqx.platform.permission.vo.CompanyInfo;
import gyqx.platform.permission.vo.MenuConfig;
import gyqx.platform.permission.vo.MenuItem;
import gyqx.platform.permission.vo.OrgAdminUser;
import gyqx.platform.permission.vo.UserOrg;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.security.SecurityContext;

@Service
public class AdminPermissionConfigService implements IAdminPermissionConfigService {

    @Resource
    private AdminPermissionConfigDao dao;

    @Resource
    private UtilsContext utils;

    @Override
    public List<CompanyInfo> listCompanies() throws Exception {

        return dao.listCompanies();
    }

    @Override
    public List<OrgAdminUser> listOrgAdminUser(String corpId, String orgId) throws Exception {
        return dao.listOrgAdminUser(corpId, orgId);
    }

    @Override
    public List<OrgAdminUser> listOrgUsers(String corpId, String orgId) throws Exception {
        return dao.listOrgUsers(corpId, orgId);
    }

    @Override
    public List<UserOrg> listOrg(String corpId) throws Exception {
        return dao.listOrg(corpId);
    }

    @Override
    public List<OrgAdminUser> listCompanyAdmin(String corpId) throws Exception {
        return dao.listCompanyAdmin(corpId);
    }

    @Override
    public List<String> listAdminOrgsConfig(String userId) throws Exception {
        return dao.listAdminOrgsConfig(userId);
    }

    @Override
    public List<String> listAdminMenusConfig(String userId) throws Exception {
        return dao.listAdminMenusConfig(userId);
    }

    @Override
    public List<AdminRoleConfig> listAdminRolesConfig(String corpId, String userId) throws Exception {
        return dao.listAdminRolesConfig(corpId, userId);
    }

    @Override
    @CacheEvict(value="permits", allEntries=true)
    public void setAdminFlag(String corpId, String userId, String flag) throws Exception {
        dao.setAdminFlag(corpId, userId, flag);
        utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis());
    }

    @Override
    @CacheEvict(value="permits", allEntries=true)
    public void setAdminOrgsConfig(String userId, List<String> orgIds) throws Exception {
        dao.setAdminOrgsConfig(userId, orgIds);
        utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis());
    }

    @Override
    @CacheEvict(value="permits", allEntries=true)
    public void setAdminMenusConfig(String userId, List<MenuConfig> menuConfigs) throws Exception {
        dao.setAdminMenusConfig(userId, menuConfigs);
        utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis());
    }

    @Override
    @CacheEvict(value="permits", allEntries=true)
    public void setAdminRolesConfig(String userId, List<String> roleIds) throws Exception {
        dao.setAdminRolesConfig(userId, roleIds);
        utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis());
    }

    @Override
    public List<SysProjectMenuVo> listMenu() throws Exception {
        return dao.listMenu();
    }

    @Override
    public List<MenuItem> listProjectMenu(String projectCode) throws Exception {
        return dao.listProjectMenu(projectCode);
    }

    @Override
    @CacheEvict(value="permits", allEntries=true)
    public void setAdminAllConfig(String userId, List<String> orgIds, List<MenuConfig> menuConfigs, List<String> roleIds)
            throws Exception {
        setAdminOrgsConfig(userId, orgIds);
        setAdminMenusConfig(userId, menuConfigs);
        setAdminRolesConfig(userId, roleIds);

        utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis());
    }

    @Override
    public List<AdminRoleConfig> listCompanyDefaultRolesConfig(String corpId) throws Exception {

        return dao.listCompanyDefaultRolesConfig(corpId);
    }

    @Override
    @CacheEvict(value="permits", allEntries=true)
    public void setCompanyDefaultRolesConfig(String corpId, String roleId, String flag) throws Exception {
        dao.setCompanyDefaultRolesConfig(corpId, roleId, flag);
        utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis());
    }


}


