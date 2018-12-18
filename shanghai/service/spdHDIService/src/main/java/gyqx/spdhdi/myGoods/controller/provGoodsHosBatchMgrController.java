package gyqx.spdhdi.myGoods.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.myGoods.service.IProvGoodsHosBatchMgrService;
import gyqx.spdhdi.myGoods.vo.HosProvInfoVo;

/**
 * 集配商供货目录批量管理
 * @author CHENJIANGHUA
 *
 */
@Controller
@RequestMapping(value = "myGoods/provGoodsHosBatchMgrController")
public class provGoodsHosBatchMgrController extends BaseController{

	@Resource
	private IProvGoodsHosBatchMgrService provGoodsHosBatchMgrService;
	/**
	 * 获取医院供应商列表
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "hosProvInfoVoList")
	@ResponseBody
	public AjaxResult<QueryResult<HosProvInfoVo>> getHosProvInfoVoList(@RequestBody QueryInfo<HosProvInfoVo> queryInfo) 
			throws Exception {
		AjaxResult<QueryResult<HosProvInfoVo>> result = new AjaxResult<QueryResult<HosProvInfoVo>>();
		QueryResult<HosProvInfoVo> list = provGoodsHosBatchMgrService.getHosProvInfoVoList(queryInfo);
		result.setData(list);
		return result;
	}
}
