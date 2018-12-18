package gyqx.platform.permission.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import common.db.SimpleDao;
import common.db.SqlUtils;
import common.db.batch.BatchOption;
import common.security.UserChecker;
import common.utils.UtilsContext;
import gyqx.platform.permission.dao.mapper.IMenuPageviewConfigMapper;
import gyqx.platform.permission.vo.MenuPageviewPermission;
import gyqx.platform.permission.vo.MenuPageviewPermission.MenuPermissionSet;
import gyqx.platform.po.SysPageviewElement;
import gyqx.platform.po.SysRole;
import gyqx.platform.po.SysRoleMenu;
import gyqx.platform.po.SysUser;
import gyqx.platform.po.SysUserRole;

@Repository
public class RolePermisionConfigDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private IMenuPageviewConfigMapper menuPageviewConfigMapper;
	
	@Resource
	private UtilsContext utilsContext;
	public List<SysRole> listTemplateRoles()
	{
		return dao.queryForList("select * from sys_role where company_id = '0'", SysRole.class);
	}
	
	public List<SysRole> listCompanyAllRoles(String corpId)
	{
		return dao.queryForList("select * from sys_role where company_id = ? order by kind,flag desc,mt desc", SysRole.class,corpId);
	}
	public SysRole addRole(SysRole role) throws Exception
	{
		if(dao.count(SysRole.class, "cname=? and companyId=?", role)>0)
			throw new Exception("存在相同的角色名称[" + role.getCname() +"]");
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		String id = utilsContext.getSysAtomUtil().newValue("role-id");
		role.setId(id);
		role.setUid(uid);
		return dao.insertAndGet(role);
	}
	public SysRole updateRole(SysRole role) throws Exception
	{
		if(dao.count(SysRole.class, "id<>? and cname=? and companyId=?", role)>0)
			throw new Exception("存在相同的角色名称[" + role.getCname() +"]");
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		role.setUid(uid);
		return dao.updateAndGet(role);
	}
	public void deleteRole(SysRole role) throws Exception
	{
		int kind = Integer.parseInt(role.getKind());
		
		if(kind < 11)
			throw new Exception("系统内置的角色模板不可以删除！");
			
		if(role.getKind().equals("11"))
			throw new Exception("管理员角色不可以删除！");
		
		if(dao.count(SysUserRole.class,"roleId=?" ,role.getId()) > 0)
			throw new Exception("已经有用户拥有此角色，不可以删除！");
		
		dao.executeSql("delete from sys_admin_mgr_role where role_id = ?", role.getId());
		dao.executeSql("delete from sys_role_menu where role_id = ?", role.getId());
		dao.delete(role);
		
	}
	
	/**
	 * 取用户的全部角色
	 * @param userId
	 * @return
	 */
	public List<SysRole> listUserAllRoles(String userId)
	{
		String sql="select * from sys_role r where EXISTS "
				+"(select * from sys_user_role ur where r.id = ur.role_id and ur.user_id=? ) ";
		return dao.queryForList(sql, SysRole.class,userId);
	}
	/**
	 * 更新用户的角色
	 * @param userId
	 * @param roleIds
	 * @throws Exception
	 */
	public void saveUserRoles(String userId,List<String> roleIds) throws Exception
	{
		if(dao.count(SysUser.class,"id=?",userId) <1)
			throw new Exception("用户信息不存在！");
		
		String sql="delete from sys_user_role where user_id = ?";
		dao.executeSql(sql, userId);
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		for(String roleId : roleIds)
		{
			SysUserRole ur = new SysUserRole();
			String id = utilsContext.getSysAtomUtil().newValue("user-role-id");
			ur.setId(id);
			//ur.setMt(new Date());
			ur.setRoleId(roleId);
			ur.setUid(uid);
			ur.setUserId(userId);
			dao.insert(ur);
		}		
	}	
	/*
	 * 管理员可分配的角色
	 */
	public List<SysRole> listRolesManagedByAdmin(String adminId)
	{
		String sql = "select * from sys_role r where "
				+" exists ( "
				+" select * from sys_admin_mgr_role m where r.id = m.role_id and m.admin_id=?"
				+" )";
		return dao.queryForList(sql, SysRole.class,adminId);
	}
		
	
	public List<String> getRoleMenuIds(String roleId) throws Exception
	{
		if(dao.count(SysRole.class,"id=?",roleId) <1)
			throw new Exception("用户角色信息不存在！");
		
		String sql="select menu_id from sys_role_menu where role_id=?";
		List<String> vals = dao.queryValues(sql, String.class,roleId);
		return vals;
	}
	
	
	/**
	 * 取菜单关联的界面的全部功能按钮许可
	 * @param menuId
	 * @return
	 */
	public MenuPageviewPermission getMenuElementsPermission(String menuId)
	{
		return menuPageviewConfigMapper.get(menuId);
	}
	
	/**
	 * 取某角色的 菜单关联的界面的功能按钮许可
	 * @param menuId 菜单ID
	 * @param roleId 角色ID
	 * @return 功能按钮许可
	 */
	public MenuPermissionSet getRoleMenuElementsPermits(String menuId,String roleId)
	{
		String sql = "select button_codes from sys_role_menu where menu_id=? and role_id=?";
		List<String> ids = dao.queryValues(sql, String.class, menuId,roleId);
		MenuPermissionSet mps = new MenuPermissionSet();
		mps.setMenuId(menuId);
		List<String> eleIds = new ArrayList<>();
		for(String v:ids)
		{
			String [] ss = null;
			if(v.equals("*"))
			{
				sql = "select e.id as element_id  from sys_pageview_element e INNER JOIN sys_pageview p on e.page_id = p.id where p.menu_id =?";
				List<String> elemIds = dao.queryValues(sql, String.class, menuId);
				ss = new String[elemIds.size()];
				elemIds.toArray(ss);
			}else
				ss = v.split(",");
			
			for(String s:ss){
				if(StringUtils.hasText(s))
					eleIds.add(s);
			}
		}
		mps.setElementIds(eleIds);
		return mps;
	}
	/**
	 * 保存菜单关联的界面的功能按钮许可
	 * @param roleId 角色ID
	 * @param mps 功能按钮许可
	 * @throws Exception
	 */
	public void saveMenuPermission(String roleId,MenuPermissionSet mps ) throws Exception
	{
		if(dao.count(SysRole.class,"id=?",roleId) <1)
			throw new Exception("用户角色信息不存在！");
		String sql = "delete from sys_role_menu where role_id=? and menu_id=?";
		dao.executeSql(sql, roleId,mps.getMenuId());
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		if(mps.getElementIds().size()==0)
			return ;
		SysRoleMenu rmenu = new SysRoleMenu();
		String id = roleId + ":" + mps.getMenuId();
		rmenu.setId(id);
		rmenu.setMenuId(mps.getMenuId());
		rmenu.setRoleId(roleId);
		String buttonIds = "";
		for(String item : mps.getElementIds())
		{
			if(buttonIds.length() >0)
				buttonIds +=",";
			buttonIds += item;
		}
		rmenu.setButtonCodes(buttonIds);
		//rmenu.setMt(new Date());
		rmenu.setUid(uid);
		rmenu.setVersion(0);
		
		dao.insert(rmenu);		
	}
	/**
	 * 保存角色的菜单许可，以及全部相关的按钮许可
	 * @param roleId
	 * @param menuIds
	 * @throws Exception
	 */
	public void saveRoleMenuPermissions(String roleId, List<String> menuIds) throws Exception 
	{
		if(dao.count(SysRole.class,"id=?",roleId) <1)
			throw new Exception("用户角色信息不存在！");
		String sql = "delete from sys_role_menu where role_id=? and menu_id=?";
		BatchOption bo =dao.batch();
		for(String menuId :menuIds)
		{
			bo.executeSql(sql, roleId,menuId);
		}		
		bo.done();

		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		for(String menuId :menuIds)
		{
			SysRoleMenu rmenu = new SysRoleMenu();
			String id = roleId + ":" + menuId;
			rmenu.setId(id);
			rmenu.setMenuId(menuId);
			rmenu.setRoleId(roleId);
			String buttonIds = "*";
			
			rmenu.setButtonCodes(buttonIds);
			//rmenu.setMt(new Date());
			rmenu.setUid(uid);
			rmenu.setVersion(0);
			
			dao.insert(rmenu);	
		}
		
	}

	/**
	 * 删除角色的菜单许可，以及全部相关的按钮许可
	 * @param roleId
	 * @param menuIds
	 * @throws Exception
	 */
	public void deleteRoleMenuPermissions(String roleId, List<String> menuIds) throws Exception 
	{
		if(dao.count(SysRole.class,"id=?",roleId) <1)
			throw new Exception("用户角色信息不存在！");
		String sql = "delete from sys_role_menu where role_id=? and menu_id=?";
		BatchOption bo =dao.batch();
		for(String menuId :menuIds)
		{
			bo.executeSql(sql, roleId,menuId);
		}
		bo.done();
	}	
	/**
	 * 取用户的某菜单关联的页面元素code
	 * @param userId
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	public List<String> getUserMenuElementCodes(String userId,String menuId) throws Exception
	{
		if(dao.count(SysUser.class,"id=?",userId) <1)
			throw new Exception("用户信息不存在！");
		if(UserChecker.isSuperAdministrator(userId))
		{
			String sql = "select e.code   from sys_pageview_element e INNER JOIN sys_pageview p on e.page_id = p.id where p.menu_id =?";
			List<String> allCodeIds = dao.queryValues(sql, String.class, menuId);
			return allCodeIds;
		}
		String sql = "select button_codes from sys_role_menu r where r.menu_id=? and EXISTS (select * from sys_user_role ur where r.role_id = ur.role_id and ur.user_id=? )";
		List<String> codesList = dao.queryValues(sql, String.class,menuId,userId);
		List<String> menuElements = new ArrayList<>();		
		
		for(String codes:codesList) /// 最多一行数据
		{
			String[] ss = null;
			if(codes.equals("*"))
			{
				sql = "select e.code   from sys_pageview_element e INNER JOIN sys_pageview p on e.page_id = p.id where p.menu_id =?";
				List<String> codeIds = dao.queryValues(sql, String.class, menuId);
				return codeIds;
			}else
				ss = codes.split(",");
			for(String s:ss){
				if(StringUtils.hasText(s))
					menuElements.add(s);
			}
		}
		
		if(menuElements.size() == 0)
			return menuElements;
		
		sql = String.format("select code from sys_pageview_element where id in (%s)",
				SqlUtils.idListToSql(menuElements));
		
		return dao.queryValues(sql, String.class);
	}

}
