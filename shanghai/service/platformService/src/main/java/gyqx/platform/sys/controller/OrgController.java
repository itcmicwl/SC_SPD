package gyqx.platform.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.transform.Tx;
import common.utils.UserOnlineStateUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.base.service.IBasCompanyService;
import gyqx.platform.po.SysOrg;
import gyqx.platform.sys.service.ISysOrgService;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysOrgVo;
import gyqx.spdhdi.po.BasCompanyInfo;

@Controller
@RequestMapping(value="sys/org")
public class OrgController extends BaseController
{

	@Resource
	private ISysOrgService sysOrgService;
	@Resource
	private IBasCompanyService basCompanyService;
	@Resource
	private UserOnlineStateUtils userOnline;
	@RequestMapping(value="insert")
	@ResponseBody
	public AjaxResult insert(@RequestBody @Valid SysOrg org, Errors error)throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		org = sysOrgService.addOrg(org);
		result.setData(org);
		return result;
	}

	@RequestMapping(value = "update")
	// id 为空 insert id不为空 update
	@ResponseBody
	public AjaxResult update(HttpServletRequest request,
			HttpServletResponse response, @RequestBody @Valid SysOrgVo orgVo,
			Errors error) throws ValidateException {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		SysOrg org = new SysOrg();
		try {
			if (orgVo.getLastUpdateDatetime() == null) {
				orgVo.setLastUpdateDatetime(new java.util.Date());
			}
			Tx.transform(orgVo).to(org);
			System.out.println(org.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (StringUtils.hasText(org.getId())) {
				org = sysOrgService.updateOrg(org);
			} else {
				org = sysOrgService.addOrg(org);
			}
			Map map = new HashMap<String, Object>();
			map.put("id", org.getId());
			List<SysOrgVo> data = sysOrgService.getOrgVo(map);
			result.setData(data.get(0));
		} catch (Exception ex) {
			result.setException(ex);
		}
		return result;
	}
	@RequestMapping(value="delete")
	@ResponseBody
	public AjaxResult delete(HttpServletRequest  request,HttpServletResponse response,
			@RequestBody @Valid SysOrgVo org ,Errors error) throws Exception
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		
		//通过code 来更新 flag 		
		
		Map map = new HashMap<String,Object>();
		map.put("code", org.getCode());
		map.put("id", org.getId());
		
		sysOrgService.stateDeleteOrgs(map);
		//sysOrgService.deleteSysOrg(org);
		
		return result;
	}
	@RequestMapping(value="list")
	@ResponseBody
	public AjaxResult list(HttpServletRequest  request,HttpServletResponse response,
			@RequestBody @Valid QueryInfo queryInfo ,Errors error) throws Exception
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<SysOrg> data = sysOrgService.listOrg(queryInfo);
		result.setData(data);		
		return result;
	}

	@RequestMapping(value = "getOrgsByUnit")
	@ResponseBody
	public AjaxResult getOrgsByUnit(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map params)
			throws Exception {
		AjaxResult result = new AjaxResult();
		String corpId;
		String corpCname="" ;
		String corpKind = ""; // 该字段借用treeNode.setNodeType来传值，仅供HDI_UI使用,SPD_UI
								// 因前端选择单位，已有单位对象，不会在getOrgsByUnit方法返回中获取该对象
		BasCompanyInfo com = new BasCompanyInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(params.get("corpId"))) {
			// HDI
			corpId = userOnline.getCurrent().getCorpId();
			
			com.setId(corpId);
			com = basCompanyService.getBasCompany(com);
			corpCname = com.getCname();
			corpKind = com.getKind();
		} else { // 系统管理员
			corpId = params.get("corpId").toString();
			if (!StringUtils.isEmpty(params.get("corpCname")))
		    	corpCname = params.get("corpCname").toString();
			
		}
		map.put("corpId", corpId);
		map.put("pid", "/");

		ElTreeVo root = new ElTreeVo();
		root.setId("-1");
		root.setLabel("root");

		ElTreeVo treeNode = new ElTreeVo();
		treeNode.setId(corpId);
		treeNode.setLabel(corpCname);
		treeNode.setCode(corpId);
		treeNode.setNodeType(corpKind);
		treeNode.setOb(com);
		List<ElTreeVo> tsonList = new ArrayList();

		try {
			List<SysOrg> data = sysOrgService.query(map);

			if (data.size() > 0) {

				for (SysOrg theOrg : data) {
					ElTreeVo childNode = new ElTreeVo();
					childNode.setId(theOrg.getId());
					childNode.setLabel(theOrg.getCname());
					childNode.setPid(theOrg.getPid());
				    childNode.setCode(theOrg.getCode());
				    childNode.setOb(theOrg);
					if (checkIfLeaf(childNode.getId())) {
						// System.out.println("当前节点："+theOrg.getCode()+"是叶子");
					} else {
						childNode = appColumnNode(childNode);
						// parentNode.setChildVo(childNode);
					}
					tsonList.add(childNode);
				}
				treeNode.setChildren(tsonList);
			}
			List<ElTreeVo> rootSonList = new ArrayList();
			rootSonList.add(treeNode);
			root.setChildren(rootSonList);
			result.setData(root);
			System.out.println(result.getData().toString());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "getOrgs")
	@ResponseBody
	public AjaxResult getOrgs(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxResult result = new AjaxResult();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "1");

		ElTreeVo treeNode = new ElTreeVo();
		try {
			List<SysOrg> data = sysOrgService.query(map);

			if (data.size() > 0) {

				SysOrg orgNode = data.get(0);
				treeNode.setId(orgNode.getId());
				treeNode.setLabel(orgNode.getCname());
			    treeNode.setCode(orgNode.getCode());
			    treeNode.setPid(orgNode.getPid());
				treeNode = appColumnNode(treeNode);
			}
			result.setData(treeNode);
			System.out.println(result.getData().toString());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		return result;
	}
	
	@RequestMapping(value="getTheOrg")
	@ResponseBody
	public AjaxResult getTheOrg(HttpServletRequest  request,HttpServletResponse response,@RequestBody Map params){
		AjaxResult result = new AjaxResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", params.get("id")); 
		try {
			List<SysOrgVo> data = sysOrgService.getOrgVo(map);
			if(data.size()>0){
				result.setData(data.get(0));
			}
		} catch (Exception e) {
			result.setException(e);
		}
		return result;
	}
	
	@RequestMapping(value="searchOrgs")
	@ResponseBody
	public AjaxResult searchOrgs(HttpServletRequest  request,HttpServletResponse response,@RequestBody Map params) throws Exception{
		AjaxResult result = new AjaxResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cname", params.get("cname")); 
		map.put("corpId", params.get("corpId")); 
		
		List<SysOrg> data = sysOrgService.query(map);
		if(data.size()>0){
			result.setData(data);
		}
		
		return result;
	}
	
	 private ElTreeVo appColumnNode(ElTreeVo parentNode) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			   map.put("pid", parentNode.getId()); 
	   	    List<ElTreeVo> tsonList=new ArrayList();
	    	
    		List<SysOrg> data=   sysOrgService.query(map);
			if(data.size()>0){
				for (SysOrg theOrg : data){
					ElTreeVo childNode  = new ElTreeVo();
					childNode.setId(theOrg.getId());
					childNode.setLabel(theOrg.getCname());
					childNode.setCode(theOrg.getCode());
					childNode.setPid(theOrg.getPid());
					childNode.setOb(theOrg);
					if(checkIfLeaf(childNode.getId())){
						//System.out.println("当前节点："+theOrg.getCode()+"是叶子");
					}
					else {
						childNode =appColumnNode(childNode);
					//	parentNode.setChildVo(childNode);
					}
					tsonList.add(childNode);
				}
				parentNode.setChildren(tsonList);
			}
			

	    	return parentNode;    
	    }
	  	protected boolean checkIfLeaf(String  id) throws Exception{
			boolean  b = true;
			Map<String, Object> map = new HashMap<String, Object>();
			   map.put("pid", id); 
				
			List<SysOrg> data=   sysOrgService.query(map);
			if(data.size()>0){
				b =false;
			}	
				
			return b;
		}
	  	
	  	
	  	
}
