package gyqx.spdhdi.provManager.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.myInfos.vo.HosInfoVo;
import gyqx.spdhdi.myInfos.vo.HosProvVo;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import gyqx.spdhdi.provManager.service.IHosProvInfoVoService;
@Controller
@RequestMapping(value = "provManager/hosProvInfo")
public class HosProvInfoVoController extends BaseController {
	@Resource
	IHosProvInfoVoService hosProvInfoVoService;

	/**
	 * 医院查询供应商列表信息
	 * @param queryInfo 查询参数对象
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "hosProvInfoVoList")
	@ResponseBody
	public AjaxResult<QueryResult<ProvHosInfoVo>> getHosProvInfoVoList(@RequestBody QueryInfo<ProvHosInfoVo> queryInfo) 
			throws Exception {
		AjaxResult<QueryResult<ProvHosInfoVo>> result = new AjaxResult<QueryResult<ProvHosInfoVo>>();
		QueryResult<ProvHosInfoVo> lstProvHosInfoVoList = hosProvInfoVoService.getHosProvInfoVoList(queryInfo);
		result.setData(lstProvHosInfoVoList);
		return result;
	}
	
	/**
	 * 医院查询平台供应商列表：不包括已建立关系的供应商
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getHosProvVoList")
	@ResponseBody
	public AjaxResult<List<HosProvVo>> getHosProvVoList(@RequestBody HosProvVo hosProvVo) throws Exception {
		AjaxResult<List<HosProvVo>> result = new AjaxResult<List<HosProvVo>>();
		List<HosProvVo> lstProvHosInfoVoList = hosProvInfoVoService.getHosProvVoList(hosProvVo);
		result.setData(lstProvHosInfoVoList);
		return result;
	}
	
	@RequestMapping(value = "getHosInfoVoList")
	@ResponseBody
	public AjaxResult<QueryResult<HosInfoVo>> getHosInfoVoList(
			@RequestBody QueryInfo<HosInfoVo> queryInfo) throws Exception {
		AjaxResult<QueryResult<HosInfoVo>> result = new AjaxResult<QueryResult<HosInfoVo>>();
		QueryResult<HosInfoVo> lstProvHosInfoVoList = hosProvInfoVoService.getHosInfoVoList(queryInfo);
		result.setData(lstProvHosInfoVoList);
		return result;
	}
	
	/**
	 * 医院新增医院供应商关系
	 * @param provHosInfoVo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult insertProvHosInfo(@RequestBody @Valid ProvHosInfoVo provHosInfoVo) throws Exception {
		AjaxResult result = new AjaxResult();
//		if (sysProjectMenuVo.getCode() == null || "".equals(sysProjectMenuVo.getCode())) {
//			ValidateException.Throw("code", "编码不能为空", "");
//		}
//		boolean flag = hosProvInfoVoService.existProMenuCode(sysProjectMenuVo);
//		if (flag) {
//			ValidateException.Throw("code", String.format("%s 已存在。", sysProjectMenuVo.getCode()),
//					sysProjectMenuVo.getCode());
//		}
		int res = hosProvInfoVoService.insertProvHosInfo(provHosInfoVo);
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	/**
	 * 医院更新医院供应商信息
	 * @param provHosInfoVo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult setProvHosInfo(@RequestBody @Valid ProvHosInfoVo provHosInfoVo) throws Exception {
		AjaxResult result = new AjaxResult();
		int res = hosProvInfoVoService.setProvHosInfo(provHosInfoVo);
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
	/**
	 * 医院解除医院与供应商关系，设置status状态为0
	 * @param provHosInfoVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "setStatus")
	@ResponseBody
	public AjaxResult<ProvHosInfoVo> setStatus(@RequestBody ProvHosInfoVo provHosInfoVo) throws Exception {
		AjaxResult<ProvHosInfoVo> res = new AjaxResult<ProvHosInfoVo>();
		int flag = hosProvInfoVoService.setStatus(provHosInfoVo);
		if(flag>0){
			res.setCode(0);
			res.setMsg("操作失败");
		}else{
			res.setCode(-1);
			res.setMsg("操作成功");
		}
		return res;
	}
	
	/**
	 * （根据医院ID）解除关系
	 * @param provHosInfoVo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxResult deleteByPrimaryKey(@RequestBody @Valid ProvHosInfoVo provHosInfoVo) throws Exception {
		AjaxResult result = new AjaxResult();
		if (StringUtils.isEmpty(provHosInfoVo.getHosId())) {
			ValidateException.Throw("ID", "医院ID不能为空", "");
		}
		int res = hosProvInfoVoService.deleteByPrimaryKey(provHosInfoVo.getHosId());
		if (res < 1) {
			result.setCode(-1);
			result.setMsg("操作失败！");
		}
		return result;
	}
	
}
