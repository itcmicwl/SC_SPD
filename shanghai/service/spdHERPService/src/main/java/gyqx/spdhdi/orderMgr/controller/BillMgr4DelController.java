package gyqx.spdhdi.orderMgr.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.orderMgr.service.IBillMgr4DelService;
import gyqx.spdhdi.orderMgr.vo.DistrBillListVo;

@Controller
@RequestMapping(value = "orderMgr4Del/billMgr")
public class BillMgr4DelController extends BaseController {
	@Resource
    private  IBillMgr4DelService billMgrService;
    @RequestMapping(value = "getStockBillIds/{billId}")
	@ResponseBody
	public AjaxResult getStockBillIds(@PathVariable("billId") String billId)  throws  Exception  {
    	AjaxResult result = new AjaxResult();	
    	billMgrService.getContextBillByPSid(billId);
    	return result;
	}
    
    @RequestMapping(value = "delDistrBillRows")
  	@ResponseBody
  	public AjaxResult delDistrBillRows(@RequestBody List<DistrBillListVo> details ,Errors error)  throws  Exception  {
      	AjaxResult result = new AjaxResult();	
      	filterErrors(error);

      	return result;
  	}

    @RequestMapping(value = "delDistrBillRow")
  	@ResponseBody
  	public AjaxResult delDistrBillRow(@RequestBody DistrBillListVo detail ,Errors error)  throws  Exception  {
      	AjaxResult result = new AjaxResult();	
      	filterErrors(error);
       int re = billMgrService.deleteBillRow(detail);
       result.setData(re);
      	return result;
  	}
    @RequestMapping(value = "billContextQuery/{billId}")
  	@ResponseBody
  	public AjaxResult delDistrBillRow(@PathVariable("billId") String billId)  throws  Exception  {
      	AjaxResult result = new AjaxResult();	
        Map re= billMgrService.getBillContextIds(billId);
        result.setData(re);
      	return result;
  	}
}
