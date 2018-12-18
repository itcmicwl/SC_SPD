package gyqx.spdhdi.settleCenter.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Magenta;
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
import gyqx.spdhdi.po.HosOutBalance;
import gyqx.spdhdi.settleCenter.service.IHosOutBalanceService;
import gyqx.spdhdi.settleCenter.vo.HosOutBalanceVo;
import gyqx.spdhdi.uploadData.vo.Msg;

@Controller
@RequestMapping(value = "settleCenter/hosOutBalance")
public class HosOutBalanceController extends BaseController 
{
	@Resource
	private IHosOutBalanceService  hosOutBalanceService;

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody HosOutBalanceVo hosOutBalance , Errors error  )  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosOutBalance ret = hosOutBalanceService.add(hosOutBalance); 
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getById/{id}")
	@ResponseBody
	public AjaxResult getById(@PathVariable("id") String id ,Errors error)  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosOutBalance ret = hosOutBalanceService.getById(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getByBillId/{billId}")
	@ResponseBody
	public AjaxResult getByBillId(@PathVariable("billId") String billId)  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		HosOutBalance ret = hosOutBalanceService.getByBillId(billId);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody HosOutBalanceVo hosOutBalance ,Errors error  )  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosOutBalance ret = hosOutBalanceService.update(hosOutBalance);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid HosOutBalanceVo hosOutBalance ,Errors error  )  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<HosOutBalanceVo> ret = hosOutBalanceService.list(hosOutBalance);
		result.setData(ret);
		return result;
	}

	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody QueryInfo<HosOutBalanceVo> queryInfo )  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		QueryResult<HosOutBalanceVo> ret = hosOutBalanceService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	
	/**
	 * 结算单上传JDE
	 * @param billId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "upBillToErp/{billId}",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResult<Msg> upBillToErp(@PathVariable String billId)  throws  Exception  
	{
		AjaxResult<Msg> result = new AjaxResult<>();
		HosOutBalanceVo vo = hosOutBalanceService.getByBillId(billId);
		Msg msg = null;
		if(vo != null){
			msg = hosOutBalanceService.balanceUp(vo);
		}
		result.setData(msg);
		return result;
	}
}
