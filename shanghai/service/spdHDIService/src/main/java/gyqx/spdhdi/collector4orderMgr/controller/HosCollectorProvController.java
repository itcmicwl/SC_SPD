package gyqx.spdhdi.collector4orderMgr.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import gyqx.spdhdi.myInfos.service.IUserHosService;
import gyqx.spdhdi.po.UserHos;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.UtilsContext;
import common.web.AjaxResult;
import common.web.BaseController;
import common.web.UserOnlineInfo;
import gyqx.spdhdi.collector4orderMgr.service.IHosCollectorProvService;
import gyqx.spdhdi.collector4orderMgr.vo.HosCollectorProvVo;
import gyqx.spdherp.po.HosCollectorProv;

@Controller
@RequestMapping(value = "collector4orderMgr/hosCollectorProv")
public class HosCollectorProvController extends BaseController 
{
	@Resource
	private IHosCollectorProvService  hosCollectorProvService;
	@Resource
    private UtilsContext utilsContext;
	@Resource
	private IUserHosService userHosSer;
	@RequestMapping(value = "add")
	@ResponseBody
	public AjaxResult add(@RequestBody @Valid HosCollectorProv hosCollectorProv ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosCollectorProv ret = hosCollectorProvService.add(hosCollectorProv);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "get/{id}")
	@ResponseBody
	public AjaxResult get(@PathVariable("id") String id ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosCollectorProv ret = hosCollectorProvService.get(id);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid HosCollectorProv hosCollectorProv ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		HosCollectorProv ret = hosCollectorProvService.update(hosCollectorProv);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public AjaxResult list(@RequestBody @Valid HosCollectorProv hosCollectorProv ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<HosCollectorProv> ret = hosCollectorProvService.list(hosCollectorProv);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "query")
	@ResponseBody
	public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String,String>> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<HosCollectorProv> ret = hosCollectorProvService.query(queryInfo.getPredicate(),queryInfo.getOrderBy(),queryInfo.getQueryObject());
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listByPage")
	@ResponseBody
	public AjaxResult listByPage(@RequestBody @Valid QueryInfo<HosCollectorProv> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<HosCollectorProv> ret = hosCollectorProvService.listByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listVoByPage")
	@ResponseBody
	public AjaxResult listByVoPage(@RequestBody @Valid QueryInfo<HosCollectorProvVo> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<HosCollectorProvVo> ret = hosCollectorProvService.listVoByPage(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "listVo")
	@ResponseBody
	public AjaxResult listVo(@RequestBody @Valid HosCollectorProvVo queryInfo ,Errors error  )  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		List<HosCollectorProvVo> ret = hosCollectorProvService.listVo(queryInfo);
		result.setData(ret);
		return result;
	}
	@RequestMapping(value = "getSubProvAndHosByUserId/{userId}")
	@ResponseBody
	public AjaxResult getSubProvAndHosByUserId(@PathVariable("userId") String userId)  throws  Exception
	{
		AjaxResult result = new AjaxResult();
		HosCollectorProvVo hcp = new HosCollectorProvVo();
		UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
		hcp.setCollectorId(userId);
		hcp.setCollectorId(user.getCorpId());
		List<HosCollectorProvVo> ret = hosCollectorProvService.listVo(hcp);
		List<UserHos> lst = userHosSer.getHosLists(userId);
		UserHosInfo res = new UserHosInfo();
		res.setLstHosProv(ret);
		res.setLstUserHos(lst);
		result.setData(res);
		return result;
	}
	public static class  UserHosInfo{
		List<HosCollectorProvVo> lstHosProv = new ArrayList<>();
		List<UserHos> lstUserHos = new ArrayList<>();

		public List<HosCollectorProvVo> getLstHosProv() {
			return lstHosProv;
		}

		public void setLstHosProv(List<HosCollectorProvVo> lstHosProv) {
			this.lstHosProv = lstHosProv;
		}

		public List<UserHos> getLstUserHos() {
			return lstUserHos;
		}

		public void setLstUserHos(List<UserHos> lstUserHos) {
			this.lstUserHos = lstUserHos;
		}
	}
}
