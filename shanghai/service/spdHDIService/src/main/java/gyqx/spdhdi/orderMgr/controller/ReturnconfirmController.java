package gyqx.spdhdi.orderMgr.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.orderMgr.service.IReturnconfirmService;
import gyqx.spdhdi.orderMgr.vo.ReturnconfirmVo;
import gyqx.spdhdi.po.Returnconfirm;
/**
 * 
 * @author ganwei
 * 退货确认单
 */
@Controller
@RequestMapping(value = "orderMgr/returnconfirm")
public class ReturnconfirmController extends BaseController 
{
	@Resource
	private IReturnconfirmService  returnconfirmService;

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody @Valid Returnconfirm returnconfirm ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		//Returnconfirm ret = returnconfirmService.add(returnconfirm);
		//result.setData(ret);
		return result;
	}
	@RequestMapping(value = "get/{id}")
	@ResponseBody
	public AjaxResult get(@PathVariable("id") String id ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		Returnconfirm ret = returnconfirmService.get(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getByBillId/{billId}",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResult getByBillid(@PathVariable String billId)  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		ReturnconfirmVo ret = returnconfirmService.getByBillId(billId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid Returnconfirm returnconfirm ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		Returnconfirm ret = returnconfirmService.update(returnconfirm);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid Returnconfirm returnconfirm ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<Returnconfirm> ret = returnconfirmService.list(returnconfirm);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String,String>> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<Returnconfirm> ret = returnconfirmService.query(queryInfo.getPredicate(),queryInfo.getOrderBy(),queryInfo.getQueryObject());
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody @Valid QueryInfo<Returnconfirm> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<Returnconfirm> ret = returnconfirmService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPageVo")
	@ResponseBody
	public AjaxResult listByPageVo(@RequestBody @Valid QueryInfo<ReturnconfirmVo> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<ReturnconfirmVo> ret = returnconfirmService.listByPageVo(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "submitReturnconfirm")
	@ResponseBody
	public AjaxResult submitReturnconfirm(@RequestBody @Valid ReturnconfirmVo vo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		Returnconfirm ret = returnconfirmService.addBill(vo);
		result.setData(ret);
		return result;
	}
}
