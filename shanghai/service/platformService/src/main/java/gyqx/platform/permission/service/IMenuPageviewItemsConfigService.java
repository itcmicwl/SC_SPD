package gyqx.platform.permission.service;

import java.util.List;
import java.util.Map;

import gyqx.platform.permission.vo.MenuPageviewPermission;
import gyqx.platform.permission.vo.ModuleFunction;
import gyqx.platform.po.SysPageview;
import gyqx.platform.po.SysPageviewElement;
import gyqx.platform.po.SysProjectMenu;
import gyqx.platform.sys.vo.ProjectMenuVo;
import gyqx.platform.sys.vo.SysProjectMenuVo;

public interface IMenuPageviewItemsConfigService
{
	MenuPageviewPermission getPageviewElements(String menuId);
	
	List<ModuleFunction> getPageviewBindings(String pageviewId) throws Exception;	
	List<ModuleFunction> getPageviewElementBindings(String elementId) throws Exception;
	
	SysPageview updateSysPageview(SysPageview pv) throws Exception;	
	SysPageviewElement updateSysPageviewElement(SysPageviewElement pve) throws Exception;
	void deleteSysPageviewElement(SysPageviewElement pve) throws Exception;
	
	Map<String,List> getAllProjects();	
	List<ModuleFunction> getModuleFunctions(String moduleId);
	
	List<String> getUserMenuElementIds(String userId,String menuId);
	
	List<String> getUserMenuIds(String userId);
	List<ProjectMenuVo> getUserMenus(String userId, String projectCode) throws Exception;
}
