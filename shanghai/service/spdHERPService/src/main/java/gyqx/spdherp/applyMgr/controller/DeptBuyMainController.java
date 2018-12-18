package gyqx.spdherp.applyMgr.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import gyqx.platform.sys.vo.SysOrgVo;
import gyqx.spdherp.applyMgr.vo.ApplyBillInfoVo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.SysConfigUtil;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdherp.applyMgr.service.IDeptBuyCarService;
import gyqx.spdherp.applyMgr.service.IDeptBuyMainService;
import gyqx.spdherp.applyMgr.vo.DeptBuyBillVo;
import gyqx.spdherp.applyMgr.vo.DeptCarGoodsStVo;
import gyqx.spdherp.po.DeptBuyCar;
import gyqx.spdherp.po.DeptBuyMain;
import gyqx.spdherp.po.DeptBuySub;

@Controller
@RequestMapping(value = "applyMgr/deptBuyMain")
public class DeptBuyMainController extends BaseController 
{
	@Resource
	private IDeptBuyMainService  deptBuyMainService;
	@Resource
	private IDeptBuyCarService  deptBuyCarService;
	@Resource
	private SysConfigUtil configUtil;
	
	
	@RequestMapping(value = "close")
	@ResponseBody
	public AjaxResult close(@RequestBody @Valid DeptBuyMain deptBuyMain  ,Errors error )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		System.out.println("正在关闭请购单："+deptBuyMain.getBillId());
	    int ret=	deptBuyMainService.close(deptBuyMain.getBillId());
		return result;
	}

	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody @Valid DeptBuyMain deptBuyMain ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		DeptBuyMain ret = deptBuyMainService.add(deptBuyMain);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "get/{id}")
	@ResponseBody
	public AjaxResult get(@PathVariable("id") String id ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		DeptBuyMain ret = deptBuyMainService.get(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid DeptBuyMain deptBuyMain ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		DeptBuyMain ret = deptBuyMainService.update(deptBuyMain);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid DeptBuyMain deptBuyMain ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<DeptBuyMain> ret = deptBuyMainService.list(deptBuyMain);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String,String>> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<DeptBuyMain> ret = deptBuyMainService.query(queryInfo.getPredicate(),queryInfo.getOrderBy(),queryInfo.getQueryObject());
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody @Valid QueryInfo<DeptBuyMain> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<DeptBuyMain> ret = deptBuyMainService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listVoByPage")
	@ResponseBody
	public AjaxResult listVoByPage(@RequestBody @Valid QueryInfo<DeptBuyBillVo> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		DeptBuyBillVo queryObject = queryInfo.getQueryObject();
		if (queryObject.getBuyDeptId() != null
				&& queryObject.getBuyDeptId().equals(configUtil.getValue("equipDepartmentOrgId"))) {
			queryObject.setBuyDeptId(null);//设备科查询所有科室请购单
		}
		QueryResult<DeptBuyBillVo> ret = deptBuyMainService.getVoList(queryInfo);
		result.setData(ret);
		return result;
	}
	/**
	 * 通过请购车 生成请购单 并删除请购车的记录
	 * @param cars
	 * @param error
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "create/{userId}")
	@ResponseBody
	public AjaxResult create(@PathVariable("userId") String userId ,@RequestBody DeptBuyBillVo billVo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		billVo.setFillter(userId);
	//	List<DeptBuySub> subs = new ArrayList<DeptBuySub>();	
	//	deptBuyMainService.addDeptBuyBillVo(billVo);   //实际采购与虚拟采购分开提交
		deptBuyMainService.addDeptBuyBillVoByMix(billVo); //实际采购与虚拟采购混合提交

		return result;
	}
	
	/**
	 * 二级科室合并提交请购单
	 * @param cars
	 * @param error
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "mergeDeptApply/{userId}")
	@ResponseBody
	public AjaxResult mergeDeptApply(@PathVariable("userId") String userId ,@RequestBody DeptBuyBillVo billVo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		billVo.setFillter(userId);
		List<DeptBuySub> subs = new ArrayList<DeptBuySub>();	
		deptBuyMainService.mergeDeptApply(billVo); //实际采购与虚拟采购混合提交

		return result;
	}	
	
	/**
	 * 再次请购
	 * @param cars
	 * @param error
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "createBillAgain/{userId}")
	@ResponseBody
	public AjaxResult createBillAgain(@PathVariable("userId") String userId, @RequestBody DeptBuyBillVo billVo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		billVo.setFillter(userId);
		List<DeptBuySub> subs = new ArrayList<DeptBuySub>();	
		String billId = deptBuyMainService.createBillAgain(billVo);   //实际采购与虚拟采购分开提交
		result.setData(billId);
		return result;
	}
	
	/**
	 * 修改请购单
	 * @param userId
	 * @param billVo
	 * @param error
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update/{userId}")
	@ResponseBody
	public AjaxResult update(@PathVariable("userId") String userId ,@RequestBody DeptBuyBillVo billVo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		deptBuyMainService.updateDeptBuyBillVo(billVo);
		return result;
	}
	
	@RequestMapping(value = "getBillDetails")
	@ResponseBody
	public AjaxResult getBillDetails(@RequestBody @Valid DeptBuyBillVo billVo  ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		billVo =	deptBuyMainService.getBillDetails(billVo);
		result.setData(billVo);
		return result;
	}
	
	@RequestMapping(value = "delCar")
	@ResponseBody
	public AjaxResult delCar(@RequestBody @Valid List<DeptCarGoodsStVo> carList  ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<DeptBuyCar> cars = new ArrayList();
		carList.forEach(vo->{
			System.out.println(vo.getCarId());
			DeptBuyCar car = new DeptBuyCar();
			car.setId(vo.getCarId());
			cars.add(car);
		});
	
		deptBuyCarService.deleteCars(cars);
		
		return result;
	}
	@RequestMapping(value = "getMainBillVo4pur")
	@ResponseBody
	public AjaxResult getMainBillVo4pur(@RequestBody @Valid DeptBuyBillVo vo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<DeptBuyBillVo> ret = deptBuyMainService.getMainBillVo4pur(vo);
		result.setData(ret);
		return result;
	}

	@RequestMapping(value = "getMainBillVo4merger")
	@ResponseBody
	public AjaxResult getMainBillVo4Merger(@RequestBody @Valid DeptBuyBillVo vo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<DeptBuyBillVo> ret = deptBuyMainService.getMainBillVo4merger(vo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getBuyDeptList")
	@ResponseBody
	public AjaxResult<List<SysOrgVo>> getBuyDeptList(@RequestBody DeptBuyBillVo vo) throws Exception{
		AjaxResult result = new AjaxResult();
		List<SysOrgVo> ret = deptBuyMainService.getBuyDeptList(vo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getApplyPurInfos")
	@ResponseBody
	public AjaxResult<List<ApplyBillInfoVo>> getApplyPurInfos(@RequestBody ApplyBillInfoVo vo) throws Exception{
		AjaxResult result = new AjaxResult();
		List<ApplyBillInfoVo> ret = deptBuyMainService.getApplyPurInfos(vo);
		result.setData(ret);
		return result;
	}
}
