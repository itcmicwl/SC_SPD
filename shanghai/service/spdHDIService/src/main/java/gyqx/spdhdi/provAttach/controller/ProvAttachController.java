package gyqx.spdhdi.provAttach.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.provAttach.service.ProvAttachService;
import gyqx.spdhdi.provAttach.vo.ProvAttachVo;

@Controller
@RequestMapping("/provAttach")
public class ProvAttachController {

	@Resource
	private SimpleDao simpleDao;
	
	@Resource
	private ProvAttachService service;
	
	/**
	 * 获取供应商
	 * @param hosName
	 * @return
	 */
	@RequestMapping(value = "getProvList",method = RequestMethod.GET)
	@ResponseBody
    public AjaxResult<List<BasCompanyInfoJxsqsVo>> getProvList(@RequestParam("provName") String provName) {
        AjaxResult<List<BasCompanyInfoJxsqsVo>> result = new AjaxResult<>();
        result.setData(service.findProvList(provName));
        return result;
    }
	
	/**
	 * 查询供应商附件数据
	 * @param queryInfo
	 * @return
	 */
	@RequestMapping(value = "getAttachList")
	@ResponseBody
	public AjaxResult getAttachList(@RequestBody QueryInfo<ProvAttachVo> queryInfo){
		AjaxResult result = new AjaxResult();
		QueryResult<ProvAttachVo> re = service.getAttachList(queryInfo);
		result.setData(re);
		return result;
	}
	
	/**
	 * 新增附件
	 * @param attachVo
	 * @return id
	 * @throws Exception
	 */
	@RequestMapping(value = "insertAttach")
	@ResponseBody
	public AjaxResult insertAttach(@RequestBody ProvAttachVo attachVo) throws Exception{
		AjaxResult result = new AjaxResult();
		String id = service.insertAttach(attachVo);
		result.setData(id);
		return result;
	}
	
	/**
	 * 删除附件(逻辑删除,更新flag为0)
	 * @param attachVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateAttach")
	@ResponseBody
	public AjaxResult updateAttach(@RequestBody List<ProvAttachVo> attachVo) throws Exception{
		AjaxResult result = new AjaxResult();
		service.updateAttach(attachVo);
		return result;
	}
	
}
