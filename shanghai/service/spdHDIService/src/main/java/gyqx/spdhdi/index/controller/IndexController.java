package gyqx.spdhdi.index.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.index.service.IIndexService;
import gyqx.spdhdi.index.vo.OverViewVo;

@Controller
@RequestMapping(value = "index/summary")
public class IndexController extends BaseController {
	@Resource
	private IIndexService  indexService;
	@RequestMapping(value = "overView/{comId}")
	@ResponseBody
	public AjaxResult<OverViewVo> getDistrBill(@PathVariable("comId") String comId)  throws  Exception  
	{
		AjaxResult<OverViewVo> result = new AjaxResult<OverViewVo>();	
		OverViewVo ret = indexService.getOverViewInfo(comId);
		result.setData(ret);
		return result;
	} 
}
