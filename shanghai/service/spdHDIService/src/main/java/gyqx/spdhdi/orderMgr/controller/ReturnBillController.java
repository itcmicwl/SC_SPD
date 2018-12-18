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
import common.transform.Tx;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.orderMgr.service.IReturnBillService;
import gyqx.spdhdi.orderMgr.vo.ReturnBillListVo;
import gyqx.spdhdi.orderMgr.vo.ReturnBillVo;
import gyqx.spdhdi.po.ReturnBill;

@Controller
@RequestMapping(value = "orderMgr/returnBill")
public class ReturnBillController extends BaseController 
{
	@Resource
	private IReturnBillService  returnBillService;

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody @Valid ReturnBill returnBill ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		ReturnBill ret = returnBillService.add(returnBill);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "get/{id}")
	@ResponseBody
	public AjaxResult get(@PathVariable("id") String id ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		ReturnBill ret = returnBillService.get(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value="getReturnBill/{billId}",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResult<ReturnBillVo> getReturnBill(@PathVariable String billId) throws Exception{
		AjaxResult<ReturnBillVo> result = new AjaxResult<ReturnBillVo>();
		ReturnBillVo res = returnBillService.getReturnBillByBillId(billId);
		result.setData(res);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid ReturnBill returnBill ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		ReturnBill ret = returnBillService.update(returnBill);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid ReturnBill returnBill ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<ReturnBill> ret = returnBillService.list(returnBill);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String,String>> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<ReturnBill> ret = returnBillService.query(queryInfo.getPredicate(),queryInfo.getOrderBy(),queryInfo.getQueryObject());
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody @Valid QueryInfo<ReturnBill> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<ReturnBill> ret = returnBillService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	
	@RequestMapping(value = "listByPageVo")
	@ResponseBody
	public AjaxResult listByPageVo(@RequestBody @Valid QueryInfo<ReturnBillVo> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<ReturnBillVo> ret = returnBillService.listReturnBillVoByPage(queryInfo);
		
		result.setData(ret);
		return result;
	}
	
	@RequestMapping(value = "addReturnBill")
	@ResponseBody
	public AjaxResult addReturnBill(@RequestBody @Valid ReturnBillVo returnBillVo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		ReturnBill rb = new ReturnBill();
		Tx.transform(returnBillVo).to(rb);
		List<ReturnBillListVo> rbSubList =returnBillVo.getReturnBillSubList();
		//数据转换 returnBillVo 中取 returnBill 和subList
	    returnBillService.add(rb, rbSubList);
		return result;
	}
	@RequestMapping(value = "updateReturnBill")
	@ResponseBody
	public AjaxResult updateReturnBill(@RequestBody @Valid ReturnBillVo returnBillVo ,Errors error  )  throws  Exception  
	{
		AjaxResult<ReturnBillVo> result = new AjaxResult<ReturnBillVo>();
		filterErrors(error);
		ReturnBillVo res =returnBillService.updateReturnBill(returnBillVo);
		result.setData(res);
		return result;
	}
	@RequestMapping(value="delReturnBill")
	@ResponseBody
	public AjaxResult delReturnBill(@RequestBody ReturnBillVo rbVo) throws Exception{
		AjaxResult<ReturnBillVo> result = new AjaxResult<ReturnBillVo>();
		 returnBillService.deleteReturnBill(rbVo);
		
		return result;
	}
}
