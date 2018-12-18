package gyqx.platform.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.po.SysModule;
import gyqx.platform.po.SysModuleGroup;
import gyqx.platform.po.SysProject;
import gyqx.platform.sys.service.ISysModuleGroupService;
import gyqx.platform.sys.vo.SysModuleGroupVo;
import gyqx.platform.sys.vo.SysModuleVo;

@Controller
@RequestMapping(value = "sys/moduleGroup")
public class ModuleGroupController extends BaseController {

	@Resource
	private ISysModuleGroupService sysModuleGroupService;


 	

	@RequestMapping(value = "getModuleGroupVos")
	@ResponseBody
	public AjaxResult getModuleGroupVos(HttpServletRequest request, HttpServletResponse response, @RequestBody QueryInfo<SysModuleGroupVo> queryInfo)
			throws Exception {

		QueryResult<SysModuleGroupVo> queryResult=   sysModuleGroupService.queryModuleGroupVos(queryInfo);

	
	

		
		AjaxResult result = new AjaxResult();
		result.setData(queryResult);
		return result;
	}

	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult insert(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SysModuleGroupVo moduleGroupVo) throws Exception {
		SysModuleGroup mg = new SysModuleGroup();
		Map<String, Object> map = new HashMap<String, Object>();
		Tx.transform(moduleGroupVo).to(mg);
		mg = sysModuleGroupService.insert(mg);
		AjaxResult result = new AjaxResult();
		result.setData(mg);
		return result;
	}

	@RequestMapping(value = "update")
	@ResponseBody      
	public AjaxResult update(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SysModuleGroupVo moduleGroupVo) throws Exception {
		SysModuleGroup mo = new SysModuleGroup();
			Tx.transform(moduleGroupVo).to(mo);
	     mo= sysModuleGroupService.update(mo);
		AjaxResult result = new AjaxResult();
		result.setData(mo);
		System.out.println("xxxxxxxxxxxxxx");
	  return result;
	}  
	@RequestMapping(value="delModuleGroup") 
	@ResponseBody	
	public AjaxResult delModule(HttpServletRequest  request,HttpServletResponse response,@RequestBody SysModuleGroupVo mv) throws Exception{
		AjaxResult<SysProject> res = new AjaxResult<SysProject>();
		 SysModuleGroup mg = new SysModuleGroup();
			Tx.transform(mv).to(mg);
		if(mv==null||StringUtils.isEmpty(mv.getId())){
		   res.setCode(-1);
			res.setMsg("操作失败！");
			return res;
	   }
		//删除还需要检查分组下是否挂有模块 待完成
		int flag = sysModuleGroupService.delModuleGroup(mg);
		if (flag > 0) {
			res.setCode(0);
			res.setMsg("操作成功");
		} else {
			res.setCode(-1);
			res.setMsg("操作失败");
		}
	   return res;
	}  
	
	
	
	

/*	
	@RequestMapping(value="getSysProjectOptions") 
	@ResponseBody	
	public AjaxResult getSysProjectOptions(HttpServletRequest  request,HttpServletResponse response,@RequestBody Map params) throws Exception{
		ElTreeVo treeNode  = new ElTreeVo();
		 treeNode.setId("");
		 treeNode.setLabel("虚拟根");
		treeNode=appColumnNode(treeNode);
		AjaxResult result = new AjaxResult();
		result.setData(treeNode);
		return result;
	}*/

	/*@RequestMapping(value = "getGroupOpts")
	@ResponseBody
	public AjaxResult getGroupOpts(HttpServletRequest request, HttpServletResponse response, @RequestBody Map params)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dictId", params.get("dictId"));
		QueryResult<SysDictValueVo> queryResult = sysDictService.queryDictValueVos(map);
		AjaxResult result = new AjaxResult();
		result.setData(queryResult);
		return result;
	}

	 private ElTreeVo appColumnNode(ElTreeVo parentNode) {
			Map<String, Object> map = new HashMap<String, Object>();
			   map.put("pid", parentNode.getId()); 
			 List<ElTreeVo> tsonList=new ArrayList();
	    	try {
	    		List<SysProject> data=   sysModuleService.getSysProjectOptions(map).getData();
				if(data.size()>0){
					for (SysProject project : data){
							ElTreeVo childNode  = new ElTreeVo();
							childNode.setId(project.getId());
							childNode.setLabel(project.getEname());
							childNode.setCode(project.getCode());
						
							
							if(checkIfLeaf(childNode.getId())&&	StringUtils.isEmpty(project.getPid())){
								childNode.setDisabled(true);
								System.out.println("当前节点："+project.getCode()+"是叶子");
							}
							else {
								
								childNode =appColumnNode(childNode);
							//	parentNode.setChildVo(childNode);
							}
							tsonList.add(childNode);

				}
				parentNode.setChildren(tsonList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parentNode;
	}

	protected boolean checkIfLeaf(String id) {
		boolean b = true;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", id);
		try {
			List<SysProject> data = sysModuleService.getSysProjectOptions(map).getData();
			if (data.size() > 0) {
				b = false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}

	@RequestMapping(value = "moduleTree")
	@ResponseBody
	public AjaxResult<List<ElTreeVo>> getModuleTree() throws Exception {
		AjaxResult<List<ElTreeVo>> result = new AjaxResult<List<ElTreeVo>>();
		List<ElTreeVo> lst = sysModuleService.getSysModuleTree();
		result.setData(lst);
		return result;
	}*/

}
