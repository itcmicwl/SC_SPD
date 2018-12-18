package gyqx.spdhdi.baseData.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.transform.Tx;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.spdhdi.baseData.service.IBasGoodsInfoService;
import gyqx.spdhdi.baseData.service.impl.BasGoodsInfoService.Task;
import gyqx.spdhdi.baseData.vo.BasGoodsInfoVo;
import gyqx.spdhdi.myGoods.util.ConfigPropertiesUtils;
import gyqx.spdhdi.myGoods.util.FileSave;
import gyqx.spdhdi.myGoods.util.NumberValidationUtils;
import gyqx.spdhdi.myGoods.util.ReadExcle;
import gyqx.spdhdi.po.BasGoodsInfo;
import gyqx.spdhdi.po.BasMfrsInfo;

@Controller
@RequestMapping(value = "bas/goods")
@Slf4j
public class BasGoodsInfoController extends BaseController {
	@Resource
	private UserOnlineStateUtils userOnline;
	
	List<BasGoodsInfo> errors= new ArrayList<BasGoodsInfo>();
	
	@Resource
	private SysConfigUtil configUtil;
	@Resource
	private SimpleDao simpleDao;
	@Resource
	private IBasGoodsInfoService basGoodsInfoService;
	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult insert(@RequestBody @Valid BasGoodsInfoVo good, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		BasGoodsInfo goodInfo = new BasGoodsInfo();
		Tx.transform(good).to(goodInfo);
		goodInfo= basGoodsInfoService.addBasGood(goodInfo);
		return result;
	}
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult update(@RequestBody @Valid BasGoodsInfoVo good, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		 if (simpleDao.checkExists(BasMfrsInfo.class, "id,mfrsName,", good.getMfrsId(),good.getMfrsName()) == 0) {
				ValidateException.Throw("mfrsId","您输入的厂商 ："+ good.getMfrsName()+ "不存在，请重新选择!", good.getMfrsName());
			}
		BasGoodsInfo goodInfo = new BasGoodsInfo();
		Tx.transform(good).to(goodInfo);
		goodInfo= basGoodsInfoService.updateBasGood(goodInfo);
		return result;
	}
	@RequestMapping(value = "delGoodsInfo")
	@ResponseBody
	public AjaxResult delGoodsInfo(@RequestBody @Valid BasGoodsInfoVo good, Errors error) throws Exception {
		AjaxResult result = new AjaxResult();
		filterErrors(error);
		BasGoodsInfo goodInfo = new BasGoodsInfo();
		Tx.transform(good).to(goodInfo);
		basGoodsInfoService.deleteBasGood(goodInfo);
		return result;
	}
	@RequestMapping(value = "getGoodsInfos")
	@ResponseBody
	public AjaxResult getGoodsInfos(HttpServletRequest request, HttpServletResponse response,
			                         @RequestBody QueryInfo<BasGoodsInfoVo> queryInfo)throws Exception {
		QueryResult<BasGoodsInfoVo> queryResult=   basGoodsInfoService.listBasGoods(queryInfo);
		AjaxResult result = new AjaxResult();
		result.setData(queryResult);
		return result;
	}

	@RequestMapping(value = "importExcel")
	@ResponseBody
	public AjaxResult importExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map)
			throws Exception {
		AjaxResult result = new AjaxResult();
		errors.clear();
		if (!StringUtils.isEmpty(map.get("id"))) {
			String fileURL = ConfigPropertiesUtils.getValue("fileUploadURL") + map.get("path") + "/" + map.get("id")
					+ "." + map.get("ext");
			byte[] btImg = FileSave.getImageFromNetByUrl(fileURL);
			File file = new File(request.getRealPath("/") + "/docs");
			if (!file.exists()) {
				log.info(file.getPath() + "不存在");
				if (file.mkdir()) {
					log.info("创建后的文件目录为：" + file.getPath());
				}
			}
			String path = request.getSession().getServletContext().getRealPath("docs");
			String fileName = "import2003_BasGoods" + map.get("id") + "." + map.get("ext");
			FileSave.writeImageToDisk(btImg, fileName, path);
			String localFilePath = path + "/" + fileName;
			ReadExcle read = new ReadExcle();
			List<BasGoodsInfo> pgs = new ArrayList<BasGoodsInfo>();
			List<List<Object>> list = read.read2003Excel(new File(localFilePath));
			for (int i = 1; i < list.size(); i++) {
				List kk = list.get(i);
				BasGoodsInfo pp = new BasGoodsInfo();
				Map mapre = convert2BasGoodsInfo(kk, pp);
				if (mapre.get("ifErrorCel").toString().equals("true")) {
					// 数字转换有错
					errors.add((BasGoodsInfo) mapre.get("pp"));
				} else {
					log.info(pp.toString());
					pgs.add(pp);
				}
			}
			List<BasGoodsInfo> xx = basGoodsInfoService.addBaseGoodS4ImportExcel(pgs);// 需要处理mfrsId
																						// 及失败返回信息
			errors.addAll(xx);
			log.info(String.valueOf(errors.size()));

		}
		result.setData(errors);
		return result;
	}

	private Map convert2BasGoodsInfo(List<Object> kk, BasGoodsInfo pp) throws Exception {
		boolean ifErrorCel = false;
		Map map = new HashMap<>();
		pp.setRemark("");
		if (!StringUtils.isEmpty(kk.get(9)))
			pp.setRemark(kk.get(9).toString());
		if (!StringUtils.isEmpty(kk.get(0)))
			pp.setGoodsName(kk.get(0).toString());
		if (!StringUtils.isEmpty(kk.get(1)))
			pp.setGoodsGg(kk.get(1).toString());
		if (!StringUtils.isEmpty(kk.get(2)))
			pp.setMade(kk.get(2).toString());
		if (!StringUtils.isEmpty(kk.get(3)))
			pp.setMfrsId(kk.get(3).toString() + "@@4mfrsName");
		if (!StringUtils.isEmpty(kk.get(4)))
			pp.setUnit(kk.get(4).toString());
		if (!StringUtils.isEmpty(kk.get(5))) {
			if (NumberValidationUtils.isDecimal(kk.get(5).toString())) {
				pp.setPackeage(new BigDecimal(kk.get(5).toString()));
			} else {
				ifErrorCel = true;
				pp.setRemark("包装必须为数字");
				log.info("包装必须为数字");
			}
		}
		if (!StringUtils.isEmpty(kk.get(6)))
			pp.setMasterCode(kk.get(6).toString());
		if (!StringUtils.isEmpty(kk.get(7)))
			pp.setMfrsCode(kk.get(7).toString());
		if (!StringUtils.isEmpty(kk.get(8)))
			pp.setKind68code(kk.get(8).toString());
		if (!StringUtils.isEmpty(kk.get(10)))
			pp.setBrand(kk.get(10).toString());
		map.put("ifErrorCel", ifErrorCel);
		map.put("pp", pp);
		return map;

	}
	 
	@RequestMapping(value = "importExcel4init")
	@ResponseBody
	public AjaxResult importExcel4init(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map)
			throws Exception {
		AjaxResult result = new AjaxResult();
		String userId = userOnline.getCurrent().getUserId();
		final Map map4return = basGoodsInfoService.startTask(map);
		new Thread() {
			public void run() {
				try {
					basGoodsInfoService.add4DealImport(map4return,userId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		result.setTag(map4return.get("id").toString());
		return result;
	}
	@RequestMapping(value = "catProvByProperties/{type}/{s_taskId}")
	@ResponseBody
	public AjaxResult<Task> getRecBillById(@PathVariable("type") String type,@PathVariable("s_taskId") String s_taskId)  throws  Exception {
		
		log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		AjaxResult<Task> result = new AjaxResult<>();
		Task ret = basGoodsInfoService.getBySid(type,s_taskId);
		result.setData(ret);
		return result;
	}

}
