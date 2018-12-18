package gyqx.platform.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gyqx.platform.sys.vo.PicPathVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import common.base.JSONHelper;
import common.exception.FileUploadException;
import common.exception.ParameterException;
import common.web.AjaxResult;
import common.web.BaseController;
import common.web.upload.UploadResult;
import gyqx.platform.sys.service.IFileManagerService;
import tools.ExcelExport;


@RestController
@RequestMapping(value="sys/upload")
public class FileUploadController extends BaseController
{
	@Resource
	private IFileManagerService iFMService ;

	@RequestMapping(value = "file/{folder}", method = RequestMethod.POST)  
    public AjaxResult handleFormUpload(@PathVariable("folder")String folder, @RequestParam("file") CommonsMultipartFile[] files,
    		HttpServletRequest request,HttpServletResponse response) throws Exception {  
		String msg = "";
		AjaxResult result = new AjaxResult();
		List<UploadResult> uresults = new ArrayList<>();
		try{
			for(int i = 0; i < files.length;i++)
			{
				UploadResult uresult =  iFMService.uploadFile(files[i],folder,files[i].getOriginalFilename(),files[i].getSize());
				uresults.add(uresult);
			}
			result.setData(uresults);
		}catch(Exception ex)
		{
			throw new FileUploadException(ex.getMessage());
		}

		return result;
    } 
	
	@RequestMapping(value = "downFile", method = RequestMethod.GET)  
    public void handleFormDownload(@RequestParam("url")String url,@RequestParam("fileName")String fileName,HttpServletResponse response) throws Exception { 
		iFMService.fileDownload(response, url, fileName);
    }
    @PostMapping("addPicShuiYin")
	public AjaxResult addPicShuiYin(@RequestBody PicPathVo picPath) throws Exception{
		AjaxResult<String> res = new AjaxResult<>();
		String newPath = iFMService.addPicShuiYin(picPath);
		res.setData(newPath);
		return res;
	}
	@PostMapping("addPicsShuiYin")
	public AjaxResult addPicsShuiYin(List<PicPathVo> picPathVo) throws Exception{
		AjaxResult<List<PicPathVo>> res = new AjaxResult<>();
		List<PicPathVo> r = iFMService.addPicsShuiYin(picPathVo);
		res.setData(r);
		return res;
	}
}
