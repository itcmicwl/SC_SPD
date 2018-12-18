package gyqx.spdhdi.mycerts.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;
import gyqx.spdhdi.mycerts.service.IJxsqsService;
import gyqx.spdhdi.mycerts.vo.JxsqsGoodsVo;
import gyqx.spdhdi.mycerts.vo.JxsqsToHosVo;
import gyqx.spdhdi.mycerts.vo.JxsqsVo;
import gyqx.spdhdi.po.ProvJxsqs;
import gyqx.spdhdi.po.ProvJxsqsGoods;
import gyqx.spdhdi.po.ProvJxsqsHos;

@Controller
@RequestMapping(value = "myCerts/jxsqs")
public class JxsqsController extends BaseController {
	
	@Resource
	private IJxsqsService jxsqsService;
	
	@RequestMapping(value = "getForHospitals")
	@ResponseBody
	public AjaxResult<JxsqsToHosVo> getForHospitals(@RequestBody String sqsId) throws Exception {
		AjaxResult<JxsqsToHosVo> result = new AjaxResult<JxsqsToHosVo>();
		JxsqsToHosVo jxsqsToHosVo = jxsqsService.getForHospitals(sqsId);
		result.setData(jxsqsToHosVo);
		return result;
	}
	
	@RequestMapping(value = "getJxsqsById")
	@ResponseBody
	public AjaxResult<JxsqsVo> getJxsqsById(@RequestBody String id) throws Exception {
		AjaxResult<JxsqsVo> result = new AjaxResult<JxsqsVo>();
		JxsqsVo jxsqsVo = jxsqsService.getJxsqsById(id);
		result.setData(jxsqsVo);
		return result;
	}
	
	@RequestMapping(value = "getJxsqsList")
	@ResponseBody
	public AjaxResult<QueryResult<JxsqsVo>> getJxsqsList(@RequestBody QueryInfo<JxsqsVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<JxsqsVo>> result = new AjaxResult<QueryResult<JxsqsVo>>();
		QueryResult<JxsqsVo> jxsqs = jxsqsService.getJxsqsList(queryInfo);
		result.setData(jxsqs);
		return result;
	}
	
	@RequestMapping(value = "getJxsqsGoodsInfo")
	@ResponseBody
	public AjaxResult<QueryResult<JxsqsGoodsVo>> getJxsqsGoodsInfo(@RequestBody QueryInfo<JxsqsGoodsVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<JxsqsGoodsVo>> result = new AjaxResult<QueryResult<JxsqsGoodsVo>>();
		QueryResult<JxsqsGoodsVo> jxsqsGoods = jxsqsService.getJxsqsGoodsInfo(queryInfo);
		result.setData(jxsqsGoods);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteJxsqsToHos")
	@ResponseBody
	public AjaxResult deleteJxsqsToHos(@RequestBody ProvJxsqsHos jxsqsHos) throws Exception {
		AjaxResult result = new AjaxResult<JxsqsVo>();
		jxsqsService.deleteJxsqsToHos(jxsqsHos);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteJxsqsGoods")
	@ResponseBody
	public AjaxResult deleteJxsqsGoods(@RequestBody ProvJxsqsGoods jxsqsGoods) throws Exception {
		AjaxResult result = new AjaxResult<JxsqsVo>();
		jxsqsService.deleteJxsqsGoods(jxsqsGoods);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteJxsqsCert")
	@ResponseBody
	public AjaxResult deleteJxsqsCert(@RequestBody ProvJxsqs jxsqs) throws Exception {
		AjaxResult result = new AjaxResult<JxsqsVo>();
		jxsqsService.deleteJxsqsCert(jxsqs);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateJxsqsCert")
	@ResponseBody
	public AjaxResult updateJxsqsCert(@RequestBody ProvJxsqs jxsqs) throws Exception {
		AjaxResult result = new AjaxResult<JxsqsVo>();
		jxsqsService.updateJxsqsCert(jxsqs);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "addJxsqsToHos/{sqsId}/{provName}")
	@ResponseBody
	public AjaxResult addJxsqsToHos(@RequestBody List<BasCompanyInfoVo> hosInfos,
			@PathVariable("sqsId")String sqsId,@PathVariable("provName")String provName) throws Exception {
		AjaxResult result = new AjaxResult<JxsqsVo>();
		jxsqsService.addJxsqsToHos(hosInfos,sqsId,provName);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "addJxsqsGoods")
	@ResponseBody
	public AjaxResult addJxsqsGoods(@RequestBody JxsqsVo jxsqsVo) throws Exception {
		AjaxResult result = new AjaxResult();
		jxsqsService.addJxsqsGoods(jxsqsVo);
		return result;
	}
	
	@RequestMapping(value = "addJxsqs")
	@ResponseBody
	public AjaxResult<JxsqsVo> addJxsqs(@RequestBody JxsqsVo jxsqsVo) throws Exception {
		AjaxResult<JxsqsVo> result = new AjaxResult<JxsqsVo>();
		jxsqsVo = jxsqsService.addJxsqs(jxsqsVo);
		result.setData(jxsqsVo);
		return result;
	}
	
	@RequestMapping(value = "updateJxsqs")
	@ResponseBody
	public AjaxResult<JxsqsVo> updateJxsqs(@RequestBody JxsqsVo jxsqsVo) throws Exception {
		AjaxResult<JxsqsVo> result = new AjaxResult<JxsqsVo>();
		jxsqsVo = jxsqsService.updateJxsqs(jxsqsVo);
		result.setData(jxsqsVo);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteJxsqs")
	@ResponseBody
	public AjaxResult deleteJxsqs(@RequestBody JxsqsVo jxsqsVo) throws Exception {
		AjaxResult result = new AjaxResult();
		jxsqsService.deleteJxsqs(jxsqsVo);
		return result;
	}
	
}
