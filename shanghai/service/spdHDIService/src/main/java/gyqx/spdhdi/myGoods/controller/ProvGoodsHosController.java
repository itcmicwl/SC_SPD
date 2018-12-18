package gyqx.spdhdi.myGoods.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.UserOnlineStateUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.myGoods.service.IProvGoodsHosService;
import gyqx.spdhdi.myGoods.vo.ProHosGoodsInfoVo;
import gyqx.spdhdi.myGoods.vo.ProvGoods4HosVo;
import gyqx.spdhdi.myGoods.vo.ProvGoodsInfoVo;
import gyqx.spdhdi.po.ProvGoodsHos;



@Controller
@RequestMapping(value = "myGoods/provGoodsHos")
public class ProvGoodsHosController extends BaseController {
	
	@Resource
	private UserOnlineStateUtils userOnline;
	@Resource
	private IProvGoodsHosService provGoodsHosService;
	@RequestMapping(value = "getProHosGoodsList")
	@ResponseBody
	public AjaxResult<QueryResult<ProHosGoodsInfoVo>> getProHosGoodsList(@RequestBody QueryInfo<ProHosGoodsInfoVo> queryInfo)
			throws Exception {
		AjaxResult<QueryResult<ProHosGoodsInfoVo>> result = new AjaxResult<QueryResult<ProHosGoodsInfoVo>>();
		//queryInfo.getQueryObject().setProvId(userOnline.getCurrent().getCorpId());
		//queryInfo.getQueryObject().setProvId("company-009");
		QueryResult<ProHosGoodsInfoVo> lstGoods = provGoodsHosService.getProHosGoodsList(queryInfo);
		result.setData(lstGoods);
		return result;
	}
	
	@RequestMapping(value = "getProGoods4HosList")
	@ResponseBody
	public AjaxResult<QueryResult<ProvGoods4HosVo>> getProGoods4HosList(@RequestBody QueryInfo<ProvGoods4HosVo> queryInfo)
			throws Exception {
		AjaxResult<QueryResult<ProvGoods4HosVo>> result = new AjaxResult<QueryResult<ProvGoods4HosVo>>();
		//queryInfo.getQueryObject().setProvId(userOnline.getCurrent().getCorpId()); //由前端vue传入
		//queryInfo.getQueryObject().setProvId("company-009");
		QueryResult<ProvGoods4HosVo> lstGoods = provGoodsHosService.getProGoods4HosList(queryInfo);
		result.setData(lstGoods);
		return result;
	}
	
	@RequestMapping(value = "getPHGoodsList")    //获取供货列表供批量导入
	@ResponseBody
	public AjaxResult<QueryResult<ProvGoods4HosVo>> getPHGoodsList(@RequestBody QueryInfo<ProvGoods4HosVo> queryInfo)
			throws Exception {
		AjaxResult<QueryResult<ProvGoods4HosVo>> result = new AjaxResult<QueryResult<ProvGoods4HosVo>>();
		queryInfo.getQueryObject().setHosId(userOnline.getCurrent().getCorpId());
		//queryInfo.getQueryObject().setProvId("company-009");
		QueryResult<ProvGoods4HosVo> lstGoods = provGoodsHosService.getProGoods4HosList(queryInfo);
		result.setData(lstGoods);
		return result;
	}
	
	
	/*@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult insert(@RequestBody @Valid ProvGoodsInfo good, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		good.setProvId(userOnline.getCurrent().getCorpId());//默认设置 ，需要从 用户身份中取值"company-009"
		good= provGoodsInfoService.addProvGood(good);
		return result;
	}
	
	*/
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid ProvGoods4HosVo good, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		ProvGoodsHos pgh = new ProvGoodsHos();
		pgh = provGoodsHosService.getProvGoodsHosById(good.getId());
		pgh.setSalePrice(good.getSalePrice());
		pgh.setPurPrice(good.getPurPrice());
		pgh.setTaxRate(good.getTaxRate());
		pgh.setUnitRate(good.getUnitRate());
		pgh.setHisCode(good.getHisCode());
		pgh.setHisUnit(good.getHisUnit());
		pgh.setHisName(good.getHisName());
		provGoodsHosService.updateYCodeAndYName(good); // 医保编码和医保名称
		pgh= provGoodsHosService.updateProvGoodsHos(pgh);
		return result;
	}
	
	/**
	 * 对码提交
	 * @param good
	 * @param error
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "submissionCode")
	@ResponseBody
	public AjaxResult submissionCode(@RequestBody @Valid ProvGoods4HosVo good, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		provGoodsHosService.processSubmissionCode(good);;
		return result;
	}	
	
	@RequestMapping(value = "delProvGoodsHos")
	@ResponseBody
	public AjaxResult delProvGoodsHos(@RequestBody @Valid ProvGoods4HosVo good, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		ProvGoodsHos pgh = new ProvGoodsHos();
		pgh = provGoodsHosService.getProvGoodsHosById(good.getId());
		provGoodsHosService.delProvGoodsHos(pgh);
		return result;
	}
	
	@RequestMapping(value = "multDel")
	@ResponseBody
	public AjaxResult multDel(@RequestBody ArrayList<ProvGoods4HosVo> pHGoods) throws Exception {
		AjaxResult result = new AjaxResult();
		List<ProvGoodsHos> goods = new ArrayList<ProvGoodsHos>();
		for(ProvGoods4HosVo pgv:pHGoods ){
			ProvGoodsHos pgh = new ProvGoodsHos();
			pgh.setId(pgv.getId());
			goods.add(pgh);
		}
		provGoodsHosService.multDelProvGoodsHos(goods);
	
		return result;
	}
	
	@RequestMapping(value = "goodImprotFromProGoods")
	@ResponseBody
	public AjaxResult goodImprotFromProGoods(HttpServletRequest request, HttpServletResponse response, @RequestBody ArrayList<ProvGoodsInfoVo> provGoods,@RequestParam("hosId") String hosId  )
			throws Exception {	
		AjaxResult result = new AjaxResult();
		List<ProvGoodsHos> provGoodsHosList = new ArrayList<ProvGoodsHos>();
		for(ProvGoodsInfoVo provGoodVo:provGoods){
			ProvGoodsHos provGoodsHos = new ProvGoodsHos();
			provGoodsHos.setHosId(hosId);
			provGoodsHos.setUnitRate(new BigDecimal(1));
			provGoodsHos = provGoodsHosService.convertProvGood2PGH(provGoodVo, provGoodsHos);
			provGoodsHosList.add(provGoodsHos);
		}
   	provGoodsHosService.addProvGoodsHosList(provGoodsHosList);
		return result;
	}
	
}
