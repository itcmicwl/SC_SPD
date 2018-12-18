package gyqx.platform.sys.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import gyqx.platform.sys.vo.PicPathVo;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import common.web.upload.UploadResult;

public interface IFileManagerService 
{
	UploadResult uploadFile(CommonsMultipartFile file,String folder,String fileName,long fileSize) throws Exception;
	
	void fileDownload (final HttpServletResponse response, String filePath, String fileName) throws IOException;
	String addPicShuiYin(PicPathVo picPath) throws Exception;
	List<PicPathVo> addPicsShuiYin(List<PicPathVo> lstPic) throws Exception;
}
