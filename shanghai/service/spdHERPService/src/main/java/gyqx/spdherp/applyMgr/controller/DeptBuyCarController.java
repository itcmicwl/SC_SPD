package gyqx.spdherp.applyMgr.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import common.utils.UserOnlineStateUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdherp.applyMgr.service.IDeptBuyCarService;
import gyqx.spdherp.applyMgr.service.IDeptGoodsStVoService;
import gyqx.spdherp.applyMgr.vo.DeptCarGoodsStVo;
import gyqx.spdherp.applyMgr.vo.DeptGoodsStVo;
import gyqx.spdherp.po.DeptBuyCar;

@Controller
@RequestMapping(value = "applyMgr/deptBuyCar")
public class DeptBuyCarController extends BaseController 
{
	@Resource
	private UserOnlineStateUtils userOnline;
	@Resource
	private IDeptBuyCarService  deptBuyCarService;
	@Resource
	private IDeptGoodsStVoService  deptGoodsStVoService;
	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody @Valid DeptBuyCar deptBuyCar ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		DeptBuyCar ret = deptBuyCarService.add(deptBuyCar);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "get/{id}",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResult get(@PathVariable("id") String id  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		DeptBuyCar ret = deptBuyCarService.get(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid DeptBuyCar deptBuyCar ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		DeptBuyCar ret = deptBuyCarService.update(deptBuyCar);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid DeptBuyCar deptBuyCar ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<DeptBuyCar> ret = deptBuyCarService.list(deptBuyCar);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String,String>> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<DeptBuyCar> ret = deptBuyCarService.query(queryInfo.getPredicate(),queryInfo.getOrderBy(),queryInfo.getQueryObject());
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody @Valid QueryInfo<DeptBuyCar> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<DeptBuyCar> ret = deptBuyCarService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	/**
	 * 
	 * @param purMode 采购模式 10 实采 20 虚采
	 * @param hosId
	 * @param hGoods
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "add2car/{purMode}/{hosId}/{deptId}")
	@ResponseBody
	public AjaxResult add2car(@PathVariable("purMode") String purMode ,@PathVariable("hosId") String hosId ,@PathVariable("deptId") String deptId, @RequestBody ArrayList<DeptGoodsStVo> hGoods)
			throws Exception {
	  
		AjaxResult result = new AjaxResult();
		List<DeptBuyCar> cars = new ArrayList<DeptBuyCar>();
		for(DeptGoodsStVo good:hGoods){
			DeptBuyCar c = new DeptBuyCar();
			c.setGoodsId(good.getGoodsId());
			c.setBuyKind(Integer.valueOf(purMode));
			c.setDeptId(deptId);
			c.setHosId(hosId);
//			if("20".equals(good.getPurMode())||"10".equals(good.getPurMode()))
//			c.setBuyKind(Integer.valueOf(good.getPurMode()));
			c.setBuyKind(10); //华山北院全部采用实际采购模式流程，
			c.setPurMode(Integer.valueOf(good.getPurMode())); // 新增purMode 用于设备科分类审核：10 低值 20 高值 30 办公
			c.setNeedQty(good.getNeedQty());
			cars.add(c);
		}
		deptBuyCarService.addList(cars);
	//	hosGoodsInfoService.addHosGoodS(hosGoods);
		return result;
	}
	
	/**
	 * 科室产品+库存 列表信息。 供添加到请购车
	 * @param request
	 * @param response
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getDeptGoodsStVo")
	@ResponseBody
	public AjaxResult getDeptGoodsStVo(HttpServletRequest request, HttpServletResponse response, @RequestBody QueryInfo<DeptGoodsStVo> queryInfo)
			throws Exception {
		// 传入医院Id
		AjaxResult result = new AjaxResult();
		if(queryInfo.getQueryObject().getPurMode()=="0"){
			queryInfo.getQueryObject().setPurMode(null);
		}
		queryInfo.getQueryObject().setOppertor(userOnline.getCurrent().getUserId());
		QueryResult<DeptGoodsStVo> queryResult=   deptGoodsStVoService.listByPage(queryInfo);
		
		result.setData(queryResult);
		return result;
	}
	
	/**
	 * 科室购物车 商品库存 供生成请购单
	 * @param request
	 * @param response
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getDeptCarGoodsStVo")
	@ResponseBody
	public AjaxResult getDeptCarGoodsStVo(HttpServletRequest request, HttpServletResponse response, @RequestBody QueryInfo<DeptCarGoodsStVo> queryInfo)
			throws Exception {
		// 传入医院Id
		AjaxResult result = new AjaxResult();
//		if(queryInfo.getQueryObject().getBuyKind().equals("0")){
//			queryInfo.getQueryObject().setBuyKind(null);    
//		}
		QueryResult<DeptCarGoodsStVo> queryResult=   deptBuyCarService.listByPage_deptCarGoodsStlist(queryInfo);
		result.setData(queryResult);
		return result;
	}
	
	
}
