package gyqx.platform.permission.dao;

import java.util.*;

import javax.annotation.Resource;

import gyqx.platform.sys.vo.ProjectMenuVo;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import common.db.SimpleDao;
import common.db.SqlUtils;
import common.db.datatable.DataTable;
import common.db.exception.SqlInjectException;
import common.security.UserChecker;
import common.utils.UtilsContext;
import common.validate.Checker;
import gyqx.platform.permission.dao.mapper.IMenuPageviewConfigMapper;
import gyqx.platform.permission.vo.MenuElementFunctionId;
import gyqx.platform.permission.vo.MenuPageviewPermission;
import gyqx.platform.permission.vo.ModuleFunction;
import gyqx.platform.po.SysModule;
import gyqx.platform.po.SysModuleGroup;
import gyqx.platform.po.SysPageview;
import gyqx.platform.po.SysPageviewElement;
import gyqx.platform.po.SysProject;
import gyqx.platform.po.SysProjectMenu;
import gyqx.platform.po.SysUser;
@Repository
public class PageviewConfigDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private IMenuPageviewConfigMapper menuPageviewConfigMapper;
	
	@Resource
	private UtilsContext utilsContext;
	
	public SysPageview addSysPageview(SysPageview pv) throws Exception
	{
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		String id = "pv" + pv.getMenuId();
		pv.setId(id);
		//pv.setMt(new Date());
		pv.setUid(uid);

		return dao.insertAndGet(pv);
	}
	public SysPageview updateSysPageview(SysPageview pv) throws Exception
	{
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		//pv.setMt(new Date());
		pv.setUid(uid);
		return dao.updateAndGet(pv);
		
	}
	/**
	 * 取页面所关联的模块方法列表
	 * @param pageviewId
	 * @return
	 * @throws Exception
	 */
	public List<ModuleFunction> getPageviewBindings(String pageviewId) throws Exception
	{
		SysPageview pv = dao.get(SysPageview.class, pageviewId);
		String ids=null;
		if(StringUtils.hasText(pv.getFuncIds()))
		{
			ids = SqlUtils.idListToSql(pv.getFuncIds().split(","));
		}
		String sql="select m.id as module_id, m.ename as module_name, f.id as function_id,f.ename as function_name, f.remark ,d.url "
				+ "from sys_module m left join sys_module_function f on m.id = f.module_id LEFT JOIN sys_module_function_design d on f.id= d.function_id "
				+ " where f.id in (%s) ";
		sql = String.format(sql, ids);
		return dao.queryForList(sql, ModuleFunction.class);
	}
	/**
	 * 取页面元素所关联的模块方法列表
	 * @param elementId
	 * @return
	 * @throws Exception
	 */
	public List<ModuleFunction> getPageviewElementBindings(String elementId) throws Exception
	{
		SysPageviewElement pv = dao.get(SysPageviewElement.class, elementId);
		String ids=null;
		if(StringUtils.hasText(pv.getFuncIds()))
		{
			ids = SqlUtils.idListToSql(pv.getFuncIds().split(","));
		}
		String sql="select m.id as module_id, m.ename as module_name, f.id as function_id,f.ename as function_name, f.remark ,d.url "
				+ "from sys_module m left join sys_module_function f on m.id = f.module_id LEFT JOIN sys_module_function_design d on f.id= d.function_id "
				+ " where f.id in (%s) ";
		sql = String.format(sql, ids);
		return dao.queryForList(sql, ModuleFunction.class);
	} 
	
	/**
	 * 取菜单关联的界面的全部功能按钮许可
	 * @param menuId
	 * @return
	 */
	public MenuPageviewPermission getMenuPermission(String menuId)
	{
		return menuPageviewConfigMapper.get(menuId);
	}
	public SysPageviewElement addSysPageviewElement(SysPageviewElement pve) throws Exception
	{
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();
		
		if(!StringUtils.hasText(pve.getCode ()))
			throw new Exception("元素编码不可为空");
		
		Long cnt = dao.count(SysPageviewElement.class, "pageId=? and code=?", pve);
		if(cnt >0)
			throw new Exception("重复的元素编码：" + pve.getCode());
		
		String id = pve.getPageId() + pve.getCode();
		
		pve.setId(id);
		//pve.setMt(new Date());
		pve.setUid(uid);

		return dao.insertAndGet(pve);
	}
	public SysPageviewElement updateSysPageviewElement(SysPageviewElement pve) throws Exception
	{
		String uid = utilsContext.getUserStateUtils().getCurrent().getUserId();

		Long cnt = dao.count(SysPageviewElement.class, "pageId=? and code=? and id <>? ", pve);
		if(cnt >0)
			throw new Exception("重复的元素编码：" + pve.getCode());
		
		//pve.setMt(new Date());
		pve.setUid(uid);
		return dao.updateAndGet(pve);
	}
	public void deleteSysPageviewElement(SysPageviewElement pve) throws Exception
	{
		dao.delete(pve);
	}
	
	public List<SysProject> getSubProjects()
	{
		return dao.queryForList("select * from sys_project where pid is not null and length(pid) > 0", SysProject.class);
	}
	public List<SysModuleGroup> getModuleGroups()
	{
		return dao.queryForList("select * from sys_module_group", SysModuleGroup.class);
	}
	public List<SysModule> getModules()
	{
		return dao.queryForList("select * from sys_module", SysModule.class);
	}
	public List<ModuleFunction> getModuleFunctions(String moduleId)
	{
		String sql="select m.id as module_id, m.ename as module_name, f.id as function_id,f.ename as function_name, f.remark ,d.url "
				+ "from sys_module m left join sys_module_function f on m.id = f.module_id LEFT JOIN sys_module_function_design d on f.id= d.function_id "
				+ " where f.module_id = ?";
		return dao.queryForList(sql, ModuleFunction.class,moduleId );
	}
	
	public List<String> getUserMenuIds(String userId)
	{
		String sql = "select menu_id from sys_role_menu r where EXISTS (select * from sys_user_role ur where r.role_id = ur.role_id and ur.user_id=? )";
		return dao.queryValues(sql, String.class,userId);
	}
	public List<ProjectMenuVo> getUserMenusAndEls(String userId,String projectCode) throws Exception{
		if(dao.count(SysUser.class,"id=?",userId) <1) {
			throw new Exception("用户信息不存在！");
		}
		if(UserChecker.isSuperAdministrator(userId)){
			return 	menuPageviewConfigMapper.getSuperAdminMenus(projectCode);
		}else{
			return menuPageviewConfigMapper.getUserMenus(projectCode,userId);
		}
	}
	/**
	 * 取用户在某系统中的全部菜单
	 * @param userId
	 * @param projectCode
	 * @return
	 * @throws Exception 
	 */
	public List<SysProjectMenu> getUserMenus(String userId,String projectCode) throws Exception
	{
		if(dao.count(SysUser.class,"id=?",userId) <1)
			throw new Exception("用户信息不存在！");
		String sql="";
		List<SysProjectMenu> selectMenus = null;
		if(UserChecker.isSuperAdministrator(userId))
		{
			sql ="select * from sys_project_menu m where  m.project_code = ?  ";
			selectMenus = dao.queryForList(sql, SysProjectMenu.class,projectCode);
		}else{
//			sql = "select * from sys_project_menu m where m.require_auth='1' and  m.project_code = ? and EXISTS ( "
//	               + "select menu_id from sys_role_menu r where EXISTS "
//	               + " (select * from sys_user_role ur where r.role_id = ur.role_id and ur.user_id=? ) "
//	               + " and m.id = r.menu_id"
//	               +") and not exists ( "
//	               +" select * from sys_project_menu m0 WHERE m0.pcode = m.code )";
			// 2017-12-23 mysql  优化性能  为毛用 'in' 还快些呢？
			sql = "select  * from sys_project_menu m where m.require_auth='1' and  m.project_code = ? and (m.id in ( "
		               + "  select menu_id from sys_role_menu r where EXISTS "
		               + " (select * from sys_user_role ur where r.role_id = ur.role_id and ur.user_id=? )) "
		               +" or m.menu_level <2) "
						+"union all "
						+"  select * from sys_project_menu mx where  mx.project_code = ? and mx.require_auth='0' ";
			selectMenus = dao.queryForList(sql, SysProjectMenu.class,projectCode,userId,projectCode);
		}
		return selectMenus;
	}
	
	public List<String> getUserMenuElementIds(String userId,String menuId)
	{
		String sql = "select button_codes from sys_role_menu r where r.menu_id=? and EXISTS (select * from sys_user_role ur where r.role_id = ur.role_id and ur.user_id=? )";
		List<String> codesList = dao.queryValues(sql, String.class,menuId,userId);
		List<String> menuElements = new ArrayList<>();
		for(String codes:codesList)
		{
			String[] ss = codes.split(",");
			if(codes.equals("*"))
			{
				sql = "select e.id   from sys_pageview_element e INNER JOIN sys_pageview p on e.page_id = p.id where p.menu_id =?";
				List<String> ids = dao.queryValues(sql, String.class, menuId);
				return ids;
			}else
				ss = codes.split(",");
			for(String s:ss){
				if(StringUtils.hasText(s))
					menuElements.add(s);
			}
		}
		return menuElements;
	}	
	

}
