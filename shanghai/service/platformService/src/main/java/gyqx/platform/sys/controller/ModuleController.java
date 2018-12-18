package gyqx.platform.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import gyqx.platform.po.SysProject;
import gyqx.platform.sys.service.ISysDictService;
import gyqx.platform.sys.service.ISysModuleGroupService;
import gyqx.platform.sys.service.ISysModuleService;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysModuleGroupVo;
import gyqx.platform.sys.vo.SysModuleVo;

@Controller
@RequestMapping(value = "sys/module")
public class ModuleController extends BaseController {

	@Resource
	private ISysModuleService sysModuleService;
	@Resource
	private ISysDictService sysDictService;
	@Resource
	private ISysModuleGroupService sysModuleGroupService;
 	

	@RequestMapping(value = "getModuleVos")
	@ResponseBody
	public AjaxResult getModuleVos(HttpServletRequest request, HttpServletResponse response, @RequestBody QueryInfo<SysModuleVo> queryInfo)
			throws Exception {

		QueryResult<SysModuleVo> queryResult=   sysModuleService.queryModuleVos(queryInfo);

	
	

		
		AjaxResult result = new AjaxResult();
		result.setData(queryResult);
		return result;
	}

	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult insert(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SysModuleVo moduleVo) throws Exception {
		SysModule mo = new SysModule();
		Map<String, Object> map = new HashMap<String, Object>();
		Tx.transform(moduleVo).to(mo);
		mo = sysModuleService.insert(mo);
		AjaxResult result = new AjaxResult();
		result.setData(mo);
		return result;
	}

	@RequestMapping(value = "update")
	@ResponseBody      
	public AjaxResult update(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SysModuleVo moduleVo) throws Exception {
		SysModule mo = new SysModule();
			Tx.transform(moduleVo).to(mo);
		mo = sysModuleService.update(mo);
		AjaxResult result = new AjaxResult();
		result.setData(mo);
	  return result;
	}  
	@RequestMapping(value="delModule") 
	@ResponseBody	
	public AjaxResult delModule(HttpServletRequest  request,HttpServletResponse response,@RequestBody SysModuleVo mv) throws Exception{
		AjaxResult<SysProject> res = new AjaxResult<SysProject>();
		 SysModule mo = new SysModule();
			Tx.transform(mv).to(mo);
		if(mv==null||StringUtils.isEmpty(mv.getId())){
		   res.setCode(-1);
			res.setMsg("操作失败！");
			return res;
	   }
		
		int flag = sysModuleService.delModule(mo);
		if (flag > 0) {
			res.setCode(0);
			res.setMsg("操作成功");
		} else {
			res.setCode(-1);
			res.setMsg("操作失败");
		}
	   return res;
	}  
	
	
	
	

	
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
	}

	@RequestMapping(value = "getGroupOpts")
	@ResponseBody
	public AjaxResult getGroupOpts(HttpServletRequest request, HttpServletResponse response, @RequestBody Map params)
			throws Exception {
		AjaxResult result = new AjaxResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dictId", params.get("dictId"));
		if(params==null||StringUtils.isEmpty(params.get("projectCode"))){
			result.setCode(-1);
			result.setMsg("请先选择所属系统！");
				return result;
		   }
		
		
		QueryInfo<SysModuleGroupVo> qi= new QueryInfo<SysModuleGroupVo>();
		SysModuleGroupVo mo = new  SysModuleGroupVo();
		mo.setProjectCode(params.get("projectCode").toString());
		qi.setQueryObject(mo);
		QueryResult<SysModuleGroupVo> queryResult = sysModuleGroupService.queryModuleGroupVos(qi);
		
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
							childNode.setLabel(project.getCname());
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
	}

}
