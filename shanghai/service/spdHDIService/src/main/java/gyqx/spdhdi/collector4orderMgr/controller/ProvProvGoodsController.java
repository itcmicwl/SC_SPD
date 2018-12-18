package gyqx.spdhdi.collector4orderMgr.controller;


import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.collector4orderMgr.service.IProvProvGoodsService;
import gyqx.spdhdi.collector4orderMgr.vo.ProvProvGoodsVo;

@Controller
@RequestMapping(value = "collector4orderMgr/provProvGoods")
public class ProvProvGoodsController extends BaseController 
{
	@Resource
	private IProvProvGoodsService  provProvGoodsService;

	
	@RequestMapping(value = "listVoByPage")
	@ResponseBody
	public AjaxResult listVoByPage(@RequestBody @Valid QueryInfo<ProvProvGoodsVo> queryInfo ,Errors error  )  throws  Exception  
	{
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		QueryResult<ProvProvGoodsVo> ret = provProvGoodsService.listVoByPage(queryInfo);
		result.setData(ret);
		return result;
	}
}
