package gyqx.platform.permission.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import common.utils.CacheUtils;
import gyqx.platform.sys.vo.ProjectMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import common.security.SecurityContext;
import common.transform.Tx;
import common.utils.UtilsContext;
import gyqx.platform.permission.dao.PageviewConfigDao;
import gyqx.platform.permission.service.IMenuPageviewItemsConfigService;
import gyqx.platform.permission.vo.MenuPageviewPermission;
import gyqx.platform.permission.vo.ModuleFunction;
import gyqx.platform.po.SysPageview;
import gyqx.platform.po.SysPageviewElement;
import gyqx.platform.po.SysProjectMenu;
import gyqx.platform.sys.vo.SysProjectMenuVo;
import org.springframework.util.StringUtils;

@Service
public class MenuPageviewItemsConfigService implements IMenuPageviewItemsConfigService 
{
	@Resource
	private PageviewConfigDao dao;
	@Autowired
	CacheUtils cacheUtils;
	@Resource
	private UtilsContext utils;
	
	@Override
	public MenuPageviewPermission getPageviewElements(String menuId) {
		return dao.getMenuPermission(menuId);
	}


	@Override
	public Map<String, List> getAllProjects() {
		Map<String, List> map = new HashMap<>();
		
		map.put("subProjects", dao.getSubProjects());
		map.put("moduleGroups", dao.getModuleGroups());
		map.put("modules", dao.getModules());
		
		return map;
	}

	@Override
	public List<ModuleFunction> getModuleFunctions(String moduleId) {

		return dao.getModuleFunctions(moduleId);
	}


	@Override
	public List<ModuleFunction> getPageviewBindings(String pageviewId) throws Exception {
		// TODO Auto-generated method stub
		return dao.getPageviewBindings(pageviewId);
	}


	@Override
	public List<ModuleFunction> getPageviewElementBindings(String elementId) throws Exception {
		// TODO Auto-generated method stub
		return dao.getPageviewElementBindings(elementId);
	}


	@Override
	@CacheEvict(value="permits", allEntries=true)
	public SysPageview updateSysPageview(SysPageview pv) throws Exception {
		if(pv.getId().equals("0"))
			pv = dao.addSysPageview(pv);
		else
			pv= dao.updateSysPageview(pv);
		
		utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis() );
		return pv;
	}


	@Override
	@CacheEvict(value="permits", allEntries=true)
	public SysPageviewElement updateSysPageviewElement(SysPageviewElement pve) throws Exception {
		if(pve.getId().equals("0"))
			pve = dao.addSysPageviewElement(pve);
		else
			pve= dao.updateSysPageviewElement(pve);
		utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis() );
		return pve;
	}

	@Override
	@CacheEvict(value="permits", allEntries=true)
	public void deleteSysPageviewElement(SysPageviewElement pve) throws Exception {
		dao.deleteSysPageviewElement(pve);	
		utils.getStatusUtils().setStatus(SecurityContext.permit_status_version, System.currentTimeMillis() );
	}

	@Override
	public List<String> getUserMenuElementIds(String userId, String menuId) {
		return dao.getUserMenuElementIds(userId,menuId);
	}


	@Override
	public List<String> getUserMenuIds(String userId) {
		
		return dao.getUserMenuIds(userId);
	}


	@Override
	@Cacheable(value = "permits", key="T(String).valueOf(#userId).concat('-').concat(#projectCode)")
	public List<ProjectMenuVo> getUserMenus(String userId, String projectCode) throws Exception{
		if(!StringUtils.hasText(userId) || !StringUtils.hasText(projectCode)){
			throw new Exception("系统或用户信息不能为空");
		}
		List<ProjectMenuVo> lst = dao.getUserMenusAndEls(userId,projectCode);
		return lst;
	}
	private List<SysProjectMenuVo> convertMenuVo(List<SysProjectMenu> lst){
		List<SysProjectMenuVo> res = new ArrayList<SysProjectMenuVo>();
		lst.forEach(menu->{
			SysProjectMenuVo m = new SysProjectMenuVo();
			try {
				Tx.transform(menu).to(m);
			} catch (Exception e) {				
				e.printStackTrace();
			}				
			res.add(m);
		});
		
		return res;
	}
	public List<SysProjectMenuVo> getSysProjectMenuTree(List<SysProjectMenu> lst) throws Exception {
		if(lst == null || lst.size() == 0){
			return null;
		}
		List<SysProjectMenuVo> ListSysProMenu = this.convertMenuVo(lst);
		//一级菜单
		List<SysProjectMenuVo> lstPSysProMenu = ListSysProMenu.stream()
				.filter(o -> o.getPcode() == null || "".equals(o.getPcode())).collect(Collectors.toList());
		//二级菜单
		lstPSysProMenu.forEach(pMenu -> {
			List<SysProjectMenuVo> children = ListSysProMenu.stream().filter(c -> c.getPcode().equals(pMenu.getCode()))
					.collect(Collectors.toList());
			if(children.size()>0){
				//三级菜单
				children.forEach(SMenu->{
					List<SysProjectMenuVo> tMenuLst = ListSysProMenu.stream().filter(t -> t.getPcode().equals(SMenu.getCode()))
							.collect(Collectors.toList());
					if(tMenuLst.size()>0){
						SMenu.setChildren(tMenuLst);
					}
				});
				pMenu.setChildren(children);
			}
			
		});
		return lstPSysProMenu;
	}

}
