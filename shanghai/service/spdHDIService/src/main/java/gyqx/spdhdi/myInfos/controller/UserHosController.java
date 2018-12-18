package gyqx.spdhdi.myInfos.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.myInfos.service.IUserHosService;
import gyqx.spdhdi.po.UserHos;
@Controller
@RequestMapping(value = "myInfo/userHos")
public class UserHosController extends BaseController {

	@Resource
	IUserHosService userHosService;

	/**
	 * 供应商查询我的医院列表信息
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getUserHosLists")
	@ResponseBody
	public AjaxResult<List<UserHos>> getUserHosLists(
			@RequestBody QueryInfo<UserHos> queryInfo) throws Exception {
		AjaxResult<List<UserHos>> result = new AjaxResult<List<UserHos>>();
		List<UserHos> list = userHosService.getUserHosLists(queryInfo.getQueryObject().getHosId());
		result.setData(list);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "addUserHos")
	@ResponseBody
	public AjaxResult addUserHos(@RequestBody List<UserHos> lists) throws Exception {
		AjaxResult result = new AjaxResult();
		int res = userHosService.addUserHos(lists);
		result.setCode(res);
		return result;
	}
	
	@RequestMapping(value = "getHosLists/{userId}")
	@ResponseBody
	public AjaxResult<List<UserHos>> getHosLists(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") String userId) throws Exception {
		AjaxResult<List<UserHos>> result = new AjaxResult<>();
		List<UserHos> hosLists = userHosService.getHosLists(userId);
		result.setData(hosLists);
		return result;
	}
}
