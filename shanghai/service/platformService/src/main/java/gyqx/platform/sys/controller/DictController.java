package gyqx.platform.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.po.SysDict;
import gyqx.platform.po.SysDictValue;
import gyqx.platform.sys.service.ISysDictService;
import gyqx.platform.sys.service.ISysOrgService;
import gyqx.platform.sys.vo.SysDictValueVo;
import gyqx.platform.sys.vo.SysDictVo;

@Controller
@RequestMapping(value="sys/dict")
public class DictController extends BaseController
{
	@Resource
	private ISysOrgService sysOrgService;
	@Resource
	private ISysDictService sysDictService;
	@RequestMapping(value="update")   //id 为空 insert  id不为空 update
	@ResponseBody
	public AjaxResult update(HttpServletRequest  request,HttpServletResponse response,@RequestBody SysDict dict) throws Exception  
	{
		AjaxResult result = new AjaxResult();
	
			if(dict.getId()!=null&&dict.getId()!="")
			{
				//
				dict = sysDictService.update(dict);
			}
			else {
				dict = sysDictService.insert(dict);
			}
			
			//新增或修改完毕后 重新加载字典列表
			Map map = new HashMap();
		    QueryResult<SysDict> data=	sysDictService.query(map);
			result.setData(data);
			
	
		
		//JSONHelper.returnObject(result, request, response);
		return result;
	}
	@RequestMapping(value="delete")
	@ResponseBody
	public AjaxResult delete(HttpServletRequest  request,HttpServletResponse response,
			@RequestBody Map params ,Errors error) throws Exception
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		sysDictService.delete(params.get("id").toString());
		//删除完毕后 重新加载字典列表
		Map map = new HashMap();
	    QueryResult<SysDict> data=	sysDictService.query(map);
		result.setData(data);
		return result;
	}
	
	@RequestMapping(value="getDicts")
	@ResponseBody	
	public AjaxResult getDicts(HttpServletRequest  request,HttpServletResponse response,@RequestBody Map params) throws Exception{
		AjaxResult result = new AjaxResult();
	//	Map<String, Object> map = new HashMap<String, Object>();
		QueryResult<SysDict> queryResult=   sysDictService.query(params);
			result.setData(queryResult);
	   return result;
	}
	
	@RequestMapping(value="getTheDict")
	@ResponseBody
	public AjaxResult getTheDict(HttpServletRequest  request,HttpServletResponse response,@RequestBody Map params) throws Exception{
		AjaxResult result = new AjaxResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", params.get("id")); 
	
			QueryResult<SysDict> queryResult=   sysDictService.query(map);
			if(queryResult.getData().size()>0){
				result.setData(queryResult.getData().get(0));
			}
		return result;
	}
 	
	@RequestMapping(value="getDictValueVos") 
	@ResponseBody	
	public AjaxResult getDictValueVos(HttpServletRequest  request,HttpServletResponse response,@RequestBody Map params) throws Exception{
		AjaxResult result = new AjaxResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dictId", params.get("dictId").toString());
			QueryResult<SysDictValueVo> queryResult=   sysDictService.queryDictValueVos(map);
			result.setData(queryResult);
	   return result;
	}
	
	
	@RequestMapping(value="getTheDictValue")
	@ResponseBody
	public AjaxResult getTheDictValue(HttpServletRequest  request,HttpServletResponse response,@RequestBody Map params) throws Exception{
		AjaxResult result = new AjaxResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", params.get("id")); 
		map.put("dictId", params.get("dictId")); 
			QueryResult<SysDictValueVo> queryResult=   sysDictService.queryDictValueVos(map);
			if(queryResult.getData().size()>0){
				result.setData(queryResult.getData().get(0));
			}
		return result;
	}
	@RequestMapping(value="getDictValueByDictEname")
	@ResponseBody
	public AjaxResult getDictValueByDictEname(HttpServletRequest  request,HttpServletResponse response,@RequestBody Map params) throws Exception{
		AjaxResult result = new AjaxResult();
		if(StringUtils.isEmpty(params.get("dictName"))){
			throw new Exception("dictName 不能为空");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dictName", params.get("dictName")); 
		map.put("val", params.get("val")); 
			QueryResult<SysDictValueVo> queryResult=   sysDictService.queryDictValueVos(map);
			
				result.setData(queryResult.getData());
			
		return result;
	}
	
	
	@RequestMapping(value="updateDictValue")   //id 为空 insert  id不为空 update
	@ResponseBody
	public AjaxResult updateDictValue(HttpServletRequest  request,HttpServletResponse response,@RequestBody SysDictValueVo dvv) throws Exception  
	{
		  SysDictValue dv = new SysDictValue();
		  Tx.transform(dvv).to(dv);
		
		AjaxResult result = new AjaxResult();
	
			if(dv.getId()!=null&&dv.getId()!="")
			{
				//
				dv = sysDictService.updateDictValue(dv);
			}
			else {
				dv = sysDictService.insertDictValue(dv);
			}
			
			//新增或修改完毕后 重新加载字典列表
			Map map = new HashMap();
			map.put("dictId",dvv.getDictId()); 
		    QueryResult<SysDictValueVo> data=	sysDictService.queryDictValueVos(map);
			result.setData(data);
			return result;
	}
	  	
	@RequestMapping(value="deleteDictValue")
	@ResponseBody
	public AjaxResult deleteDictValue(HttpServletRequest  request,HttpServletResponse response,
			@RequestBody Map params ,Errors error) throws Exception
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		sysDictService.deleteDictValue(params.get("id").toString());
		//删除完毕后 重新加载字典列表
		Map map = new HashMap();
		map.put("dictId",params.get("dictId").toString()); 
	    QueryResult<SysDictValueVo> data=	sysDictService.queryDictValueVos(map);
	  
		result.setData(data);
		return result;
	}
	
	/**
	 * 分页查询字典信息
	 * @param request
	 * @param response
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getDictList")
	@ResponseBody	
	public AjaxResult getDictList(HttpServletRequest  request,HttpServletResponse response,@RequestBody QueryInfo<SysDictVo> queryInfo) throws Exception{
		AjaxResult result = new AjaxResult();
		QueryResult<SysDict> queryResult=   sysDictService.getDictList(queryInfo);
			result.setData(queryResult);
	   return result;
	}
	
}
