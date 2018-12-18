package gyqx.spdhdi.orderMgr.controller;


import java.util.List;

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
import gyqx.spdhdi.orderMgr.service.IPurconfirmService;
import gyqx.spdhdi.orderMgr.vo.PurconfirmListVo;
import gyqx.spdhdi.orderMgr.vo.PurconfirmVo;
import gyqx.spdhdi.uploadData.vo.Msg;

@Controller
@RequestMapping(value = "orderMgr/purconfirm")
public class PurconfirmController extends BaseController 
{
	@Resource
	private IPurconfirmService  purconfirmService;

	@RequestMapping(value = "submitPurconfirm")
	@ResponseBody
	public AjaxResult<List<String>> add(@RequestBody @Valid PurconfirmVo purconfirm ,Errors error  )  throws  Exception  
	{
		AjaxResult<List<String>> result = new AjaxResult<List<String>>();
		filterErrors(error);
		//PurconfirmVo ret = purconfirmService.add(purconfirm);
		//result.setData(ret);
		List<String> res = purconfirmService.createPurconfirmBill(purconfirm);
		result.setData(res);
		return result;
	}
	@RequestMapping(value = "getById/{id}")
	@ResponseBody
	public AjaxResult<PurconfirmVo> getById(@PathVariable("id") String id ,Errors error  )  throws  Exception  
	{
		AjaxResult<PurconfirmVo> result = new AjaxResult<PurconfirmVo>();
		filterErrors(error);
		PurconfirmVo ret = purconfirmService.getById(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getByBillid/{billid}",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResult<PurconfirmVo> getByBillid(@PathVariable String billid) throws  Exception  
	{
		AjaxResult<PurconfirmVo> result = new AjaxResult<PurconfirmVo>();
		PurconfirmVo ret = purconfirmService.getByBillid(billid);
		result.setData(ret);
		return result;
	}
	
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult<PurconfirmVo> update(@RequestBody @Valid PurconfirmVo purconfirm ,Errors error  )  throws  Exception  
	{
		AjaxResult<PurconfirmVo> result = new AjaxResult<PurconfirmVo>();
		filterErrors(error);
		PurconfirmVo ret = purconfirmService.update(purconfirm);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult<List<PurconfirmVo>> list(@RequestBody @Valid PurconfirmVo purconfirm ,Errors error  )  throws  Exception  
	{
		AjaxResult<List<PurconfirmVo>> result = new AjaxResult<List<PurconfirmVo>>();
		filterErrors(error);
		List<PurconfirmVo> ret = purconfirmService.list(purconfirm);
		result.setData(ret);
		return result;
	}	
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult<QueryResult<PurconfirmVo>> listByPage(@RequestBody @Valid QueryInfo<PurconfirmVo> queryInfo)  throws  Exception  
	{
		AjaxResult<QueryResult<PurconfirmVo>> result = new AjaxResult<QueryResult<PurconfirmVo>>();
		PurconfirmVo p = queryInfo.getQueryObject();
		if(p.getBillId().indexOf(",")>0){
			p.setArrBillIds(p.getBillId().split(","));
			p.setBillId(null);
		}
		QueryResult<PurconfirmVo> ret = purconfirmService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "secListByPage")
	@ResponseBody
	public AjaxResult<QueryResult<PurconfirmVo>> secListByPage(@RequestBody @Valid QueryInfo<PurconfirmVo> queryInfo)  throws  Exception
	{
		AjaxResult<QueryResult<PurconfirmVo>> result = new AjaxResult<QueryResult<PurconfirmVo>>();
		PurconfirmVo p = queryInfo.getQueryObject();
		if(p.getBillId().indexOf(",")>0){
			p.setArrBillIds(p.getBillId().split(","));
			p.setBillId(null);
		}
		QueryResult<PurconfirmVo> ret = purconfirmService.secListByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "purConfirmDetail/{billId}",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResult<List<PurconfirmListVo>> getPurConfirmDetail(@PathVariable String billId)  throws  Exception  
	{
		AjaxResult<List<PurconfirmListVo>> result = new AjaxResult<List<PurconfirmListVo>>();
		PurconfirmListVo plv = new PurconfirmListVo();
		plv.setBillId(billId);
		List<PurconfirmListVo> ret = purconfirmService.detailList(plv);
		result.setData(ret);
		return result;
	}
	/**再次上传JDE*/
	@RequestMapping(value = "purchaseAgain/{billId}",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResult<Msg> purchaseAgain(@PathVariable String billId)  throws  Exception  
	{
		AjaxResult<Msg> result = new AjaxResult<>();
		Msg msg = purconfirmService.purchaseAgain(billId);
		result.setData(msg);
		return result;
	}
	/**此供应商是否上传JDE*/
	@RequestMapping(value = "getCanSendToJdeByProvId/{provId}",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResult<Boolean> getCanSendToJdeByProvId(@PathVariable String provId)  throws  Exception
	{
		AjaxResult<Boolean> result = new AjaxResult<>();
		Boolean res = purconfirmService.getCanSendToJdeByProvId(provId);
		result.setData(res);
		return result;
	}
	/**获取能上传到JDE的供应商列表*/
	@RequestMapping(value = "getCanSendToJdeByProvIds",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResult getCanSendToJdeByProvId()  throws  Exception
	{
		AjaxResult result = new AjaxResult<>();
		String[] res = purconfirmService.getCanSendToJdeByProvIds();
		result.setData(res);
		return result;
	}
}
