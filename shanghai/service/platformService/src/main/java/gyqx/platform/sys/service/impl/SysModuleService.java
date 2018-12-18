package gyqx.platform.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.platform.po.SysModule;
import gyqx.platform.po.SysProject;
import gyqx.platform.sys.dao.SysModuleDao;
import gyqx.platform.sys.service.ISysModuleService;
import gyqx.platform.sys.service.ISysProjectService;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysModuleVo;

@Service
public class SysModuleService implements ISysModuleService {
	@Resource
	private SysModuleDao sysModuleDao;
	@Resource
	private ISysProjectService sysProjectService;

	@Resource
	private SysConfigUtil configUtil;

	@Resource
	private SysAtomUtil atomUtil;

	@Resource
	private SimpleDao simpleDao;

	@Override
	public QueryResult<SysModuleVo> queryModuleVos(Map<String, Object> map) throws Exception {

		return sysModuleDao.queryModuleVos(map);
	}
	@Override

	public  QueryResult<SysModuleVo> queryModuleVos(QueryInfo<SysModuleVo> queryInfo) throws Exception{
		 QueryResult<SysModuleVo> re = new  QueryResult<SysModuleVo>();
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<SysModuleVo> dataList = sysModuleDao.queryModuleVos(queryInfo.getQueryObject());
		PageInfo<SysModuleVo> sysConfigPage = new PageInfo<SysModuleVo>(dataList);
		re.setData(sysConfigPage.getList());
		re.setPageNum(sysConfigPage.getPageNum());
		re.setPageSize(sysConfigPage.getPageSize());
		re.setTotal(sysConfigPage.getTotal());
	
		 
	   	return re; 
		    
	}
	
    @Override
	public QueryResult<SysProject> getSysProjectOptions(Map<String, Object> map) throws Exception {
		List<SysProject> sysProjects = simpleDao.queryForList("select  * from  sys_project  where pid = ?",
				SysProject.class, map.get("pid").toString());
		QueryResult<SysProject> re = new QueryResult<SysProject>();
		re.setData(sysProjects);
		return re;

	}

	public SysModule insert(SysModule mo) throws Exception {
		String id = atomUtil.newValue("module_id");
		mo.setId(id);

		return simpleDao.insertAndGet(mo);
	}

	public SysModule update(SysModule mo) throws Exception {
		return simpleDao.updateAndGet(mo);
	}

	
	 public int delModule(SysModule mo)throws Exception {
	
		 return simpleDao.deleteByFieldNames(mo, "id");
		// return simpleDao.deleteByWhere("sys_module","id",mo.getId());
	 }
	
	

	
	
	
   


	@Override
	public List<ElTreeVo> getSysModuleTree() throws Exception {
		List<ElTreeVo> tree = sysProjectService.getSysProjectTree();
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("dictId", "dict-00001");
		List<SysModuleVo> lstModules = this.queryModuleVos(m).getData();
		for (ElTreeVo el : tree) {
			if(el.getChildren() != null && el.getChildren().size()>0){
				for(ElTreeVo cE : el.getChildren()){//子系统下面挂模块
					List<ElTreeVo> subTree = this.buildSubTree(cE.getCode(),lstModules);
					if(subTree.size()>0){						
						cE.setChildren(subTree);
					}else{
						cE.setDisabled(true);
					}
				}
			}else{
				el.setDisabled(true);
			}
		}
		return tree;
	}
	private List<ElTreeVo> buildSubTree(String spCode,List<SysModuleVo> lstSysModule){
		List<ElTreeVo> tree = new ArrayList<ElTreeVo>();
		List<SysModuleVo> proModLst = lstSysModule.stream().filter(o -> spCode.equals(o.getProjectCode()))
				.collect(Collectors.toList());
		proModLst.forEach(c->{
			ElTreeVo cEl = new ElTreeVo();
			cEl.setId(c.getId());
			cEl.setCode(c.getId());
			cEl.setLabel(c.getCname());
			cEl.setNodeType("module");			
			cEl.setOb(c);
			tree.add(cEl);
		});
		return tree;
	}


}
