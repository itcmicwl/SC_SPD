package gyqx.platform.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.SysAtomUtil;
import gyqx.platform.sys.dao.SysProjectMenuDao;
import gyqx.platform.sys.service.ISysProjectMenuService;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysProjectMenuVo;

@Service
public class SysProjectMenuService implements ISysProjectMenuService {
	@Resource
	private SysProjectMenuDao sysProjectMenuDao;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private SimpleDao dao;

	@Override
	public QueryResult<SysProjectMenuVo> getSysProMenuList(QueryInfo<SysProjectMenuVo> queryInfo) throws Exception {
		QueryResult<SysProjectMenuVo> lstSysConfig = new QueryResult<SysProjectMenuVo>();
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<SysProjectMenuVo> ListSysProMenu = sysProjectMenuDao.getSysProMenuList(queryInfo.getQueryObject());
		PageInfo<SysProjectMenuVo> SysProMenuPage = new PageInfo<SysProjectMenuVo>(ListSysProMenu);
		lstSysConfig.setData(SysProMenuPage.getList());
		lstSysConfig.setPageNum(SysProMenuPage.getPageNum());
		lstSysConfig.setPageSize(SysProMenuPage.getPageSize());
		lstSysConfig.setTotal(SysProMenuPage.getTotal());
		return lstSysConfig;
	}

	@Override
	@CacheEvict(value="permits", allEntries=true)
	public int insertProMenu(SysProjectMenuVo sysProjectMenuVo) throws Exception {
		String id = atomUtil.newValue("sys.projectMenu");
		sysProjectMenuVo.setId(id);
		if (null == sysProjectMenuVo.getPcode() || "".equals(sysProjectMenuVo.getPcode())) {
			sysProjectMenuVo.setCode(id);
		} else {
			sysProjectMenuVo.setCode(sysProjectMenuVo.getPcode() + "." + id);
		}
		Integer n = sysProjectMenuVo.getCode().split("\\.").length-1;
		sysProjectMenuVo.setMenuLevel(n);
		int flag = sysProjectMenuDao.insertProMenu(sysProjectMenuVo);
		String code = sysProjectMenuVo.getCode();
		boolean isLeaf = code.split("[\\.]").length == 3;
		if (flag > 0 && isLeaf) {
			String sql = "insert into sys_pageview (id,menu_id,ename,func_ids,uxid,mt,version) select CONCAT('v',id) as id,id as menu_id,cname as ename,null  as func_ids,'0' as uxid,sysdate() as mt ,0 as version from sys_project_menu where id =? ";
			if(dao.getDatabaseProductName().equalsIgnoreCase("oracle"))
				sql = "insert into sys_pageview (id,menu_id,ename,func_ids,uxid,mt,version) select CONCAT('v',id) as id,id as menu_id,cname as ename,null  as func_ids,'0' as uxid,sysdate as mt ,0 as version from sys_project_menu where id =? ";
			dao.executeSql(sql, id);
			sql = "insert into sys_pageview_element ( id,page_id,ename,code,uxid,version)  select CONCAT(id,'00') as id,id as page_id,'默认' as ename, '00' as code ,'0' as uxid ,0 as version  "
					+ "from sys_pageview pv where pv.menu_id = ?";
			dao.executeSql(sql, id);
		}
		return flag;
	}

	@Override
	@CacheEvict(value="permits", allEntries=true)
	public int updateProMenu(SysProjectMenuVo sysProjectMenuVo) throws Exception {
		if (null == sysProjectMenuVo.getPcode() || "".equals(sysProjectMenuVo.getPcode())) {
			sysProjectMenuVo.setCode(sysProjectMenuVo.getId());
		} else {
			sysProjectMenuVo.setCode(sysProjectMenuVo.getPcode() + "." + sysProjectMenuVo.getId());
		}
		Integer n = sysProjectMenuVo.getCode().split("\\.").length-1;
		sysProjectMenuVo.setMenuLevel(n);
		sysProjectMenuVo.setMt(new Date());
		return sysProjectMenuDao.updateProMenu(sysProjectMenuVo);
	}

	@Override
	@CacheEvict(value="permits", allEntries=true)
	public int deleteProMenu(String id) throws Exception {
		if(!checkCanDel(id)){
			throw new Exception("当前菜单下还有子菜单或页面无法删除");
		}
		SysProjectMenuVo spm =  sysProjectMenuDao.getById(id);
		if(spm.getMenuLevel().equals(2)){
			String sql = "DELETE FROM sys_pageview where menu_id = ?";
			dao.executeSql(sql, id);
			sql ="DELETE FROM sys_pageview_element where page_id in (select id from sys_pageview where menu_id = ?)";
			dao.executeSql(sql, id);
		}
		return sysProjectMenuDao.deleteProMenu(id);
	}
	private boolean checkCanDel(String id) throws Exception{
		String sql ="select count(0) from sys_project_menu spm where spm.pcode in (select spmp.code from sys_project_menu spmp where spmp.id =?)";
		Integer count =new Integer(dao.queryForObject(sql,java.lang.String.class,id));
		return count == 0;
	}
	@Override
	public boolean existProMenuCode(SysProjectMenuVo sysProjectMenuVo) throws Exception {
		return sysProjectMenuDao.existSysProject(sysProjectMenuVo);
	}

	@Override
	public List<ElTreeVo> getParentSysProMenuList(SysProjectMenuVo sysProjectMenuVo) throws Exception {
		List<ElTreeVo> res = new ArrayList<ElTreeVo>();
		List<SysProjectMenuVo> ListSysProMenu = sysProjectMenuDao.getSysProMenuList(sysProjectMenuVo);
		// 第一级菜单
		List<SysProjectMenuVo> lstPSysProMenu = ListSysProMenu.stream()
				.filter(o -> o.getPcode() == null || "".equals(o.getPcode())).collect(Collectors.toList());
		res = buildTree(lstPSysProMenu, ListSysProMenu);
		return res;
	}

	private List<ElTreeVo> buildTree(List<SysProjectMenuVo> lstC, List<SysProjectMenuVo> lstAll) {
		List<ElTreeVo> res = new ArrayList<ElTreeVo>();
		if (lstC.size() > 0) {
			lstC.forEach(pMenu -> {
				ElTreeVo fNode = new ElTreeVo();
				fNode.setCode(pMenu.getCode());
				fNode.setId(pMenu.getId());
				fNode.setLabel(pMenu.getCname());
				fNode.setOb(pMenu);
				List<SysProjectMenuVo> children = lstAll.stream().filter(c -> c.getPcode() != null).filter(c -> c.getPcode().equals(pMenu.getCode()))
						.collect(Collectors.toList());
				fNode.setChildren(buildTree(children, lstAll));
				res.add(fNode);
			});
		}
		if (res.size() > 0) {
			return res;
		} else {
			return null;
		}
	}

	@Override
	public List<SysProjectMenuVo> getSysProjectMenuTree(SysProjectMenuVo sysProjectMenuVo) throws Exception {
		List<SysProjectMenuVo> ListSysProMenu = sysProjectMenuDao.getSysProMenuList(sysProjectMenuVo);
		// 一级菜单
		List<SysProjectMenuVo> lstPSysProMenu = ListSysProMenu.stream()
				.filter(o -> o.getPcode() == null || "".equals(o.getPcode())).collect(Collectors.toList());
		// 二级菜单c.getPcode().equals(pMenu.getCode())
		lstPSysProMenu.forEach(pMenu -> {
			List<SysProjectMenuVo> children = ListSysProMenu.stream().filter(c -> StringUtils.equals(c.getPcode(), pMenu.getCode()))
					.collect(Collectors.toList());
			if (children.size() > 0) {
				// 三级菜单 t.getPcode().equals(SMenu.getCode())
				children.forEach(SMenu -> {
					List<SysProjectMenuVo> tMenuLst = ListSysProMenu.stream()
							.filter(t -> StringUtils.equals(t.getPcode(),SMenu.getCode())).collect(Collectors.toList());
					if (tMenuLst.size() > 0) {
						SMenu.setChildren(tMenuLst);
					}
				});
				pMenu.setChildren(children);
			}

		});
		return lstPSysProMenu;
	}

	@Override
	public List<SysProjectMenuVo> sysMenuKindList(String projectCode) throws Exception {
		return sysProjectMenuDao.sysMenuKindList(projectCode);
	}
}
