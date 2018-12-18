package gyqx.spdhdi.baseData.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.baseData.service.IProvOrgInnerCodeService;
import gyqx.spdhdi.baseData.vo.UUIDandPassword;
import gyqx.spdherp.po.ProvOrgInnerCode;
@Controller
@RequestMapping(value = "bas/provOrgInnerCode")
public class ProvOrgInnerCodeController extends BaseController {
	@Resource
	IProvOrgInnerCodeService service;

	@RequestMapping(value = "getUUIDandPassword")
	@ResponseBody
	public AjaxResult<UUIDandPassword> getUUIDandPassword() 
			throws Exception {
		AjaxResult<UUIDandPassword> result = new AjaxResult<UUIDandPassword>();
		UUIDandPassword uuidAndPassword = service.getUUIDandPassword();
		result.setData(uuidAndPassword);
		return result;
	}
	
	@RequestMapping(value = "insertProvOrgInnerCode")
	@ResponseBody
	public AjaxResult<ProvOrgInnerCode> insert(@RequestBody ProvOrgInnerCode provOrgInnerCode) throws Exception {
		AjaxResult<ProvOrgInnerCode> result = new AjaxResult<ProvOrgInnerCode>();
		service.insertProvOrgInnerCode(provOrgInnerCode);
		result.setData(provOrgInnerCode);
		return result;
	}
	
	@RequestMapping(value = "updateProvOrgInnerCode")
	@ResponseBody
	public AjaxResult<ProvOrgInnerCode> update(@RequestBody ProvOrgInnerCode provOrgInnerCode) throws Exception {
		AjaxResult<ProvOrgInnerCode> result = new AjaxResult<ProvOrgInnerCode>();
		service.updateProvOrgInnerCode(provOrgInnerCode);
		result.setData(provOrgInnerCode);
		return result;
	}
	
	@RequestMapping(value = "queryProvOrgInnerCode")
	@ResponseBody
	public AjaxResult<ProvOrgInnerCode> queryProvOrgInnerCode(@RequestBody String provId) throws Exception {
		AjaxResult<ProvOrgInnerCode> result = new AjaxResult<ProvOrgInnerCode>();
		ProvOrgInnerCode obj = service.queryProvOrgInnerCode(provId.replace("=", ""));
		result.setData(obj);
		return result;
	}
}
