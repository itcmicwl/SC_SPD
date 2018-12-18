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
import gyqx.platform.po.SysModuleGroup;
import gyqx.platform.sys.dao.SysModuleDao;
import gyqx.platform.sys.dao.SysModuleGroupDao;
import gyqx.platform.sys.service.ISysModuleGroupService;
import gyqx.platform.sys.service.ISysProjectService;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysModuleGroupVo;
import gyqx.platform.sys.vo.SysModuleVo;

@Service
public class SysModuleGroupService implements ISysModuleGroupService {
	@Resource
	private SysModuleDao sysModuleDao;
	@Resource
	private SysModuleGroupDao sysModuleGroupDao;
	@Resource
	private ISysProjectService sysProjectService;

	@Resource
	private SysConfigUtil configUtil;

	@Resource
	private SysAtomUtil atomUtil;

	@Resource
	private SimpleDao simpleDao;
	
	

	
	@Override

	public  QueryResult<SysModuleGroupVo> queryModuleGroupVos(QueryInfo<SysModuleGroupVo> queryInfo) throws Exception{
		 QueryResult<SysModuleGroupVo> re = new  QueryResult<SysModuleGroupVo>();
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<SysModuleGroupVo> dataList = sysModuleGroupDao.queryModuleGroupVos(queryInfo.getQueryObject());
		PageInfo<SysModuleGroupVo> sysConfigPage = new PageInfo<SysModuleGroupVo>(dataList);
		re.setData(sysConfigPage.getList());
		re.setPageNum(sysConfigPage.getPageNum());
		re.setPageSize(sysConfigPage.getPageSize());
		re.setTotal(sysConfigPage.getTotal());
	
		 
	   	return re; 
		    
	}
	
  

	public SysModuleGroup insert(SysModuleGroup mo)throws Exception {
		String id = atomUtil.newValue("sys.moduleGroup.id");
		mo.setId(id);
//查询在同一系统下是否有重复分组
		  long  num=	simpleDao.queryForObject("select count(*) from sys_module_group where  project_code=? and( ename= ? or cname=? )  ", Long.class,mo.getProjectCode(), mo.getEname(),mo.getCname());
     	   if(num>0){
      		   throw new Exception(mo.getEname()+"已存在或编码"+mo.getCname()+"冲突！");
     	   }
		
		return simpleDao.insertAndGet(mo);
	}

	public SysModuleGroup update(SysModuleGroup mo) throws Exception {
		//查询在同一系统下是否有重复分组
		  long  num=	simpleDao.queryForObject("select count(*) from sys_module_group where  project_code=? and( ename= ? or cname=? ) and id<>? ", Long.class,mo.getProjectCode(), mo.getEname(),mo.getCname(),mo.getId());
	   	   if(num>0){
	   		   throw new Exception(mo.getEname()+"已存在或编码"+mo.getCname()+"冲突！");
	   	   }
		return simpleDao.updateAndGet(mo);
	}

	
	 public int delModuleGroup(SysModuleGroup mo)throws Exception {
		  long  num=	simpleDao.queryForObject("select count(*) from sys_module where  group_id=?  ", Long.class,mo.getId());
	   	   if(num>0){
	   		   throw new Exception(mo.getCname()+"下存在模块，不能执行删除！");
	   	   }
		 return simpleDao.deleteByFieldNames(mo, "id");
		// return simpleDao.deleteByWhere("sys_module","id",mo.getId());
	 }
	
	

	
	
	
   


	/*@Override
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
	}*/
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
