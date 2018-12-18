package gyqx.spdhdi.orderMgr.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import gyqx.spdhdi.orderMgr.service.IDistrBillService;
import gyqx.spdhdi.orderMgr.vo.DistrBillVo;

@Controller
@RequestMapping(value = "orderMgr/distrBill")
public class DistrBillController extends BaseController 
{
	@Resource
	private IDistrBillService  distrBillService;

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult<DistrBillVo> add(@RequestBody DistrBillVo distrBill)  throws  Exception  
	{
		AjaxResult<DistrBillVo> result = new AjaxResult<DistrBillVo>();
		DistrBillVo ret = distrBillService.add(distrBill);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "distrBillByBillId/{billId}")
	@ResponseBody
	public AjaxResult<DistrBillVo> getDistrBill(@PathVariable("billId") String billId)  throws  Exception  
	{
		AjaxResult<DistrBillVo> result = new AjaxResult<DistrBillVo>();	
		DistrBillVo ret = distrBillService.getByBillId(billId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "distrBillById/{id}")
	@ResponseBody
	public AjaxResult<DistrBillVo> getDistrBillById(@PathVariable("id") String id)  throws  Exception  
	{
		AjaxResult<DistrBillVo> result = new AjaxResult<DistrBillVo>();	
		DistrBillVo ret = distrBillService.getById(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult<DistrBillVo> update(@RequestBody DistrBillVo distrBill ,Errors error  )  throws  Exception  
	{
		AjaxResult<DistrBillVo> result = new AjaxResult<DistrBillVo>();
		DistrBillVo ret = distrBillService.update(distrBill);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult<List<DistrBillVo>> list(@RequestBody DistrBillVo distrBill ,Errors error  )  throws  Exception  
	{
		AjaxResult<List<DistrBillVo>> result = new AjaxResult<List<DistrBillVo>>();
		filterErrors(error);
		List<DistrBillVo> ret = distrBillService.list(distrBill);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "seclist")
	@ResponseBody
	public AjaxResult<List<DistrBillVo>> seclist(@RequestBody DistrBillVo distrBill)  throws  Exception
	{
		AjaxResult<List<DistrBillVo>> result = new AjaxResult<List<DistrBillVo>>();
		List<DistrBillVo> ret = distrBillService.seclist(distrBill);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody QueryInfo<DistrBillVo> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<DistrBillVo> ret = distrBillService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "secListByPage")
	@ResponseBody
	public AjaxResult secListByPage(@RequestBody QueryInfo<DistrBillVo> queryInfo ,Errors error  )  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<DistrBillVo> ret = distrBillService.secListByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "get/{uniqueCode}")
	@ResponseBody
	public AjaxResult existUniqueCode(@PathVariable("uniqueCode") String uniqueCode)  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		boolean ret = distrBillService.existUniqueCode(uniqueCode);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "convertDistrBill/{billId}")
	@ResponseBody
	public AjaxResult convertDistrBill(@PathVariable("billId") String billId, @RequestBody DistrBillVo distrBillVo)  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		boolean ret = distrBillService.updateConvertDistrBill(billId, distrBillVo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "back4Price/{billId}")
	@ResponseBody
	public AjaxResult back4Price(@PathVariable("billId") String billId)  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		boolean ret = distrBillService.back4Price(billId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "editDistrPrice")
	@ResponseBody
	public AjaxResult editDistrPrice(@RequestBody DistrBillVo distrBill)  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		boolean ret = distrBillService.updatePrice(distrBill);
		result.setData(ret);
		return result;
	}
	/**
	 * 四川配送单打印时收货地址获取方法
	 * 通过确认单ID 获取采购单收货地址
	 * @param billId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRecAddress/{billId}")
	@ResponseBody
	public AjaxResult getRecAddress(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("billId") String billId)  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		String ret = distrBillService.getRecAddress(billId);
		result.setData(ret);
		return result;
	}
	
}
