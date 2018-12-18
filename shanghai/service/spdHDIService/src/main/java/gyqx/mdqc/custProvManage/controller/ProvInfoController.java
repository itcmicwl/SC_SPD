package gyqx.mdqc.custProvManage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.mdqc.custProvManage.service.IProvInfoService;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;

@Controller
@RequestMapping(value = "custProvManage/provInfo")
public class ProvInfoController extends BaseController {
	@Resource
	private IProvInfoService provInfoService;
	
	@RequestMapping(value = "getProvInfoList")
	@ResponseBody
	public AjaxResult<QueryResult<BasCompanyInfoVo>> getProvInfoList(@RequestBody QueryInfo<BasCompanyInfoVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<BasCompanyInfoVo>> result = new AjaxResult<QueryResult<BasCompanyInfoVo>>();
		QueryResult<BasCompanyInfoVo> provs = provInfoService.getProvInfoList(queryInfo);
		result.setData(provs);
		return result;
	}
	
	/**
	 * 查询机构信息，不带分页
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getProvInfo")
	@ResponseBody
	public AjaxResult<List<BasCompanyInfoVo>> getProvInfo(@RequestBody BasCompanyInfoVo queryInfo) throws Exception {
		AjaxResult<List<BasCompanyInfoVo>> result = new AjaxResult<List<BasCompanyInfoVo>>();
		List<BasCompanyInfoVo> provs = provInfoService.getProvInfo(queryInfo);
		result.setData(provs);
		return result;
	}

}
