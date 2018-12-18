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
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.orderMgr.service.IReceiveBillListVoService;
import gyqx.spdhdi.orderMgr.service.IReceiveBillService;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillListVo;
import gyqx.spdhdi.orderMgr.vo.ReceiveBillVo;

@Controller
@RequestMapping(value = "orderMgr/receiveBill")
public class ReceiveBillController extends BaseController 
{
	@Resource
	private IReceiveBillService  receiveBillService;
	@Resource
	private IReceiveBillListVoService  receiveBillListService;

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult<ReceiveBillVo> add(@RequestBody @Valid ReceiveBillVo receiveBill ,Errors error  )  throws  Exception  
	{
		AjaxResult<ReceiveBillVo> result = new AjaxResult<ReceiveBillVo>();
		filterErrors(error);
		ReceiveBillVo ret = receiveBillService.add(receiveBill);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getRecBillById/{id}")
	@ResponseBody
	public AjaxResult<ReceiveBillVo> getRecBillById(@PathVariable("id") String id )  throws  Exception  
	{
		AjaxResult<ReceiveBillVo> result = new AjaxResult<ReceiveBillVo>();
		ReceiveBillVo ret = receiveBillService.getById(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult<ReceiveBillVo> update(@RequestBody ReceiveBillVo receiveBill)  throws  Exception  
	{
		AjaxResult<ReceiveBillVo> result = new AjaxResult<ReceiveBillVo>();
		ReceiveBillVo ret = receiveBillService.update(receiveBill);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult<List<ReceiveBillVo>> list(@RequestBody @Valid ReceiveBillVo receiveBill)  throws  Exception  
	{
		AjaxResult<List<ReceiveBillVo>> result = new AjaxResult<List<ReceiveBillVo>>();
		List<ReceiveBillVo> ret = receiveBillService.list(receiveBill);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult<QueryResult<ReceiveBillVo>> listByPage(@RequestBody @Valid QueryInfo<ReceiveBillVo> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult<QueryResult<ReceiveBillVo>> result = new AjaxResult<QueryResult<ReceiveBillVo>>();
		QueryResult<ReceiveBillVo> ret = receiveBillService.listByPageVo(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listSubReceiveByPage")
	@ResponseBody
	public AjaxResult listSubReceiveByPage(@RequestBody @Valid QueryInfo<ReceiveBillListVo> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<ReceiveBillListVo> ret = receiveBillListService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "addReceiveAndSubs")
	@ResponseBody
	public AjaxResult addReceiveAndSubs(@RequestBody Map map )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		map.get("receiveBill");
		map.get("receiveBillSubList");
		return result;
	}
}
