package gyqx.platform.permission.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import javax.annotation.Resource;

import gyqx.platform.sys.vo.SysProjectMenuVo;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import common.base.KV;
import common.db.SimpleDao;
import common.db.query.QueryInfo;
import gyqx.platform.permission.dao.mapper.IOrgAdminUserMapper;
import gyqx.platform.permission.vo.AdminRoleConfig;
import gyqx.platform.permission.vo.CompanyInfo;
import gyqx.platform.permission.vo.MenuConfig;
import gyqx.platform.permission.vo.MenuItem;
import gyqx.platform.permission.vo.OrgAdminUser;
import gyqx.platform.permission.vo.UserOrg;
import gyqx.platform.po.SysAdminMgrMenu;
import gyqx.platform.po.SysAdminMgrOrg;
import gyqx.platform.po.SysAdminMgrRole;
import gyqx.platform.po.SysProject;
import gyqx.platform.po.SysRole;
import gyqx.platform.po.SysUser;
import gyqx.spdhdi.po.BasCompanyInfo;

import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.utils.UtilsContext;

@Repository
public class AdminPermissionConfigDao 
{	
	@Resource
	private IOrgAdminUserMapper mapper;
	
	@Resource
	private SimpleDao dao;	
	
	@Resource
	private UtilsContext utilsContext;
	
	public List<CompanyInfo> listCompanies()
	{
		return dao.queryForList("select * from bas_company_info where flag = '1' ", CompanyInfo.class);
	}

	public List<OrgAdminUser> listOrgAdminUser(String corpId,String orgId) throws Exception 
	{
		if(dao.checkExists(BasCompanyInfo.class , "id", corpId) != 1)
			throw new Exception("单位的ID不存在:" + corpId);

		return mapper.list(KV.from("corpId",corpId,"orgId",orgId));
	}
	
	public List<OrgAdminUser> listOrgUsers(String corpId,String orgId) throws Exception 
	{
		if(dao.checkExists(BasCompanyInfo.class , "id", corpId) != 1)
			throw new Exception("单位的ID不存在:" + corpId);

		return mapper.listOrgUsers(KV.from("corpId",corpId,"orgId",orgId));
	}

	public List<UserOrg> listOrg(String corpId) throws Exception 
	{
		if(dao.checkExists(BasCompanyInfo.class , "id", corpId) != 1)
			throw new Exception("单位的ID不存在:" + corpId);
		return dao.queryForList("select id,pid,ename,corp_id from sys_org where flag = '1' and corp_id=? ",
				UserOrg.class,corpId);
	}
	
	public List<SysProjectMenuVo> listMenu() throws Exception
	{
		return dao.queryForList("select m.id,m.code,m.pcode ,m.cname,m.project_code ,p.cname as project_name,m.menu_level as menuLevel from sys_project_menu m LEFT JOIN sys_project p on m.project_code = p.code where m.require_auth='1' order by m.project_code,m.code",
				SysProjectMenuVo.class);
	}
	
	public List<MenuItem> listProjectMenu(String projectCode) throws Exception 
	{
		return dao.queryForList("select m.id,m.code,m.pcode ,m.cname,m.project_code ,p.cname as project_name from sys_project_menu m LEFT JOIN sys_project p on m.project_code = p.code where m.require_auth='1' and m.project_code =? order by m.project_code,m.code",
				MenuItem.class,projectCode);
	}


	public List<OrgAdminUser> listCompanyAdmin(String corpId) throws Exception {
		if(dao.checkExists(BasCompanyInfo.class , "id", corpId) != 1)
			throw new Exception("单位的ID不存在:" + corpId);
		
		String sql = "select DISTINCT us.user_id as id,us1.user_code, us.ename ,org.ename as org_name,us.user_id as flag"
					+ " from sys_user_org us "
					+" INNER JOIN sys_user us1 on us.user_id = us1.id   " 
					+" INNER JOIN sys_org org on us.org_id = org.id "
					+" INNER JOIN sys_user_role ur on ur.user_id = us.user_id "
					+" INNER JOIN sys_role role on ur.role_id = role.id  "
					+" where org.corp_id = ? and role.kind = '11' ";
		return dao.queryForList(sql,OrgAdminUser.class,corpId);
	}

	public List<String> listAdminOrgsConfig(String userId) throws Exception {
		if(dao.checkExists(SysUser.class, "id", userId) != 1)
			throw new Exception("用户的ID不存在:"+ userId);
		
		String sql = "select org_ids from sys_admin_mgr_org where admin_id=?";
		List<String> vals = dao.queryValues(sql, String.class,userId);
		String orgIds = vals ==null || vals.size() ==0 ? null:vals.get(0); 
		if(orgIds == null)
			return vals;
		String [] ss = orgIds.split(",");
		ArrayList<String> al = new ArrayList<>();
		for(String s:ss){
			if(StringUtils.hasText(s))
				al.add(s);
		}
		return al;
	}


	public List<String> listAdminMenusConfig(String userId) throws Exception {
		if(dao.checkExists(SysUser.class, "id", userId) != 1)
			throw new Exception("用户的ID不存在:"+ userId);
		String sql = "select menu_ids from sys_admin_mgr_menu where admin_id=?";
		List<String> vals = dao.queryValues(sql, String.class,userId);
		
		String menuIds = vals ==null || vals.size() ==0 ? 
				null: vals.stream().reduce((acc, item)->acc+"," + item).get(); 
		if(menuIds == null)
			return vals;
		
		String [] ss = menuIds.split(",");
		ArrayList<String> al = new ArrayList<>();
		for(String s:ss){
			if(StringUtils.hasText(s))
				al.add(s);
		}
		return al;
	}

	public List<AdminRoleConfig> listAdminRolesConfig(String corpId,String userId) throws Exception {
		if(dao.checkExists(SysUser.class, "id", userId) != 1)
			throw new Exception("用户的ID不存在:"+ userId);
		if(dao.checkExists(BasCompanyInfo.class , "id", corpId) != 1)
			throw new Exception("单位的ID不存在:" + corpId);
		String sql ="select role.id,role.cname,role.remark,role.mt,IFNULL(mrole.admin_id,'0') as flag  from sys_role role "
				+ "LEFT JOIN ( "
				+ "	select * from sys_admin_mgr_role mrole where admin_id = ? "
				+ " ) as mrole  on role.id = mrole.role_id "
				+ " where role.kind = '12' and role.company_id=?";
		if(dao.getDatabaseProductName().equalsIgnoreCase("oracle"))
		{
		 sql ="select role.id,role.cname,role.remark,role.mt,NVL(mrole.admin_id,'0') as flag  from sys_role role "
				+ "LEFT JOIN ( "
				+ "	select * from sys_admin_mgr_role mrole where admin_id = ? "
				+ " ) as mrole  on role.id = mrole.role_id "
				+ " where role.kind = '12' and role.company_id=?";
		}
		return dao.queryForList(sql, AdminRoleConfig.class, userId,corpId);
	}
	
	public List<AdminRoleConfig> listCompanyDefaultRolesConfig(String corpId) throws Exception
	{
		if(dao.checkExists(BasCompanyInfo.class , "id", corpId) != 1)
			throw new Exception("单位的ID不存在:" + corpId);
		String sql="select tr.id,tr.cname,tr.kind,tr.remark,tr.mt,IFNULL(cr.id,'0') as flag from sys_role tr"
				+ " LEFT JOIN ("
				+ "   select * from sys_role otr where exists ( "
				+"       select * from sys_role  where kind > '10' and flag = '1' and company_id = ? and role_template_id is not null and  role_template_id <>'' and otr.id = role_template_id "
				+  "  ) "
				+  " ) as cr on tr.id = cr.id WHERE tr.kind <'10'";
		if(dao.getDatabaseProductName().equalsIgnoreCase("oracle"))
		{
			sql="select tr.id,tr.cname,tr.kind,tr.remark,tr.mt,NVL(cr.id,'0') as flag from sys_role tr"
				+ " LEFT JOIN ("
				+ "   select * from sys_role otr where exists ( "
				+"       select * from sys_role  where kind > '10' and flag = '1' and company_id = ? and role_template_id is not null and otr.id = role_template_id "
				+  "  ) "
				+  " ) cr on tr.id = cr.id WHERE tr.kind <'10'";
		}
		return dao.queryForList(sql, AdminRoleConfig.class, corpId);
	}
	public void deleteRoleRelations(String roleId)
	{
		String sql="delete from sys_user_role where role_id ='%s' \n"
				+ "delete from sys_role_menu where role_id = '%s' \n"
				+ "delete from sys_admin_mgr_role where role_id = '%s'";
		sql = String.format(sql, roleId,roleId,roleId);
		dao.batch()
			.executeSql("delete from sys_user_role where role_id =?", roleId)
			.executeSql("delete from sys_role_menu where role_id = ?", roleId)
			.executeSql("delete from sys_admin_mgr_role where role_id =?", roleId)
			.done();
	}
	public void appendRoleRelations(String srcRoleId,String newRoleId)
	{
		String sql ="insert into sys_role_menu (id,role_id,menu_id,button_codes,uxid,mt,version) "
				+ " select UUID() as id,'%s' as role_id,m.menu_id,m.button_codes,m.uxid,sysdate() as mt,0 as version "
				+ " from sys_role_menu m where role_id ='%s'";
		if(dao.getDatabaseProductName().equalsIgnoreCase("oracle"))
		{
			sql ="insert into sys_role_menu (id,role_id,menu_id,button_codes,uxid,mt,version) "
					+ " select lower(sys_guid()) as id,'%s' as role_id,m.menu_id,m.button_codes,m.uxid,sysdate as mt,0 as version "
					+ " from sys_role_menu m where role_id ='%s'";
		}
		sql = String.format(sql, newRoleId,srcRoleId);
		dao.executeSql(sql);
	}
	public void setCompanyDefaultRolesConfig(String corpId,String roleId,String flag)throws Exception
	{
		if(dao.checkExists(BasCompanyInfo.class , "id", corpId) != 1)
			throw new Exception("单位的ID不存在:" + corpId);
		
		SysRole temp_role = dao.get(SysRole.class, roleId);
		if(temp_role == null)
			throw new Exception("不正确的角色ID:" + roleId);
		
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();		
		
		String sql = "select * from sys_role where role_template_id =? and  company_id=?";
		
		List<SysRole> roles = dao.queryForList(sql, SysRole.class, roleId,corpId);
		if(roles== null || roles.size() ==0)
		{
			SysRole corpRole = new SysRole();
			String id = utilsContext.getSysAtomUtil().newValue("role-id");
			corpRole.setRoleTemplateId(roleId);
			corpRole.setId(id);
			corpRole.setKind("1" + temp_role.getKind().charAt(1));
			corpRole.setVersion(0);
			corpRole.setCompanyId(corpId);
			corpRole.setFlag("1");
			corpRole.setCname(temp_role.getCname().replace("模板", ""));
			corpRole.setUid(uid);
			dao.insert(corpRole);
			appendRoleRelations(roleId,corpRole.getId());
			
		}else{
			
			for(SysRole corpRole :roles){
				sql = "delete from sys_role where id = ? ";
				if(flag.equals("no")){
					deleteRoleRelations(corpRole.getId());
					dao.executeSql(sql, corpRole.getId() );				
					return;
				}
				
				corpRole.setCompanyId(corpId);
				corpRole.setMt(new java.sql.Date(new Date().getTime()));
				corpRole.setFlag("1");
				corpRole.setUid(uid);
				dao.update(temp_role);
			}
		}
		
	}

	public void setAdminFlag(String corpId,String userId, String flag) throws Exception {
		
		if(dao.checkExists(SysUser.class, "id", userId) != 1)
			ValidateException.Throw("userId", "用户的ID不存在", userId);
		
		if(dao.checkExists(BasCompanyInfo.class , "id", corpId) != 1)
			ValidateException.Throw("corpId", "单位的ID不存在", corpId);
		
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		
		String sql ="delete from sys_user_role  where user_id = ? and  exists ("
				+" select * from sys_role r where r.kind='11' "
				+ " and r.company_id = ? and sys_user_role.role_id = r.id)";
		
		dao.executeSql(sql, userId,corpId);
		
		if(flag.equals("yes"))
		{			
			sql="insert into sys_user_role  (id,user_id,role_id,uxid,mt,version) "
					+" select UUID(),'"+ userId +"',r.id,'"+ uid  + "',sysdate(),0 "
					+" from  sys_role r where r.kind='11' and r.company_id = '"+ corpId + "'";
			if(dao.getDatabaseProductName().equalsIgnoreCase("oracle"))
			{
				sql="insert into sys_user_role  (id,user_id,role_id,uxid,mt,version) "
						+" select lower(sys_guid()) as id,'"+ userId +"',r.id as role_id,'"+ uid  + "',sysdate,0 "
						+" from  sys_role r where r.kind='11' and r.company_id = '"+ corpId + "'";
			}
			dao.executeSql(sql);
		}
	}
	
	public void setAdminOrgsConfig(String userId, List<String> orgIds) throws Exception 
	{
		if(dao.checkExists(SysUser.class, "id", userId) != 1)
			ValidateException.Throw("userId", "用户的ID不存在", userId);
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		
		boolean bDelete = orgIds == null || orgIds.size() == 0;
		
		if(bDelete)
		{
			String sql = "delete from sys_admin_mgr_org where admin_id = ? ";
			dao.executeSql(sql, userId);
			return;
		}
		
		Optional<String> result = orgIds.stream().reduce((acc, item)->acc+"," + item);

		SysAdminMgrOrg adminmo = (SysAdminMgrOrg) dao.getByFieldName(SysAdminMgrOrg.class, "adminId", userId);
		if(adminmo == null ){
			adminmo = new SysAdminMgrOrg();
			adminmo.setId(userId);
			adminmo.setAdminId(userId);
			adminmo.setMt(new java.sql.Date(new Date().getTime()));
			adminmo.setOrgIds(result.get());
			adminmo.setUid(uid);
			adminmo.setVersion(0);
			dao.insert(adminmo);
			
		}else{
			adminmo.setMt(new java.sql.Date(new Date().getTime()));
			adminmo.setOrgIds(result.get());
			dao.update(adminmo);
		}
		
	}


	public void setAdminMenusConfig(String userId, List<MenuConfig> menuConfigs) throws Exception 
	{
		if(dao.checkExists(SysUser.class, "id", userId) != 1)
			ValidateException.Throw("userId", "用户的ID不存在", userId);
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
				
		String sql = "delete from sys_admin_mgr_menu where admin_id = ? ";
		dao.executeSql(sql, userId);
		Map<String ,SysProject> map = new HashMap<>();
		for(MenuConfig menuConfig: menuConfigs){
			List<String> menuIds = menuConfig.getMenuIds();				
			
			Optional<String> result = menuIds.stream().reduce((acc, item)->acc+"," + item);
			
			SysProject project = map.get(menuConfig.getProjectCode());
			if(project == null){
				project = (SysProject) dao.getByFieldName(SysProject.class, "code", menuConfig.getProjectCode());
				if(project == null)
					throw new Exception("根据工程编码没有找到工程项！code=" + menuConfig.getProjectCode());
				map.put(menuConfig.getProjectCode(), project);
			}

			SysAdminMgrMenu adminmo = new SysAdminMgrMenu();
			adminmo.setId(userId + ":" + project.getId());
			adminmo.setAdminId(userId);
			adminmo.setProjectId(project.getId());
			//adminmo.setMt(new java.sql.Date(new Date().getTime()));
			adminmo.setMenuIds(result.get());
			adminmo.setUid(uid);
			adminmo.setVersion(0);
			dao.insert(adminmo);
			
		}
		
	}

	public void setAdminRolesConfig(String userId, List<String> roles) throws Exception 
	{
		if(dao.checkExists(SysUser.class, "id", userId) != 1)
			ValidateException.Throw("userId", "用户的ID不存在", userId);
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		
		boolean bDelete = roles == null || roles.size() == 0;
		
		String sql = "delete from sys_admin_mgr_role where admin_id = ? ";
		dao.executeSql(sql, userId);
		
		if(bDelete)
		{
			return;
		}
		for(String roleId: roles)
		{
			SysAdminMgrRole mrole = new SysAdminMgrRole();
			mrole.setId(userId+":"+roleId);
			mrole.setAdminId(userId);
			mrole.setUid(uid);
			//mrole.setMt(new Date());
			mrole.setRoleId(roleId);
			dao.insert(mrole);
		}
	}
}


