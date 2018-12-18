package gyqx.platform.sys.service.impl;

import common.base.Hash;
import common.image.PdfImage;
import common.utils.SysConfigUtil;
import common.web.upload.UploadResult;
import gyqx.platform.sys.service.IFileManagerService;
import gyqx.platform.sys.vo.PicPathVo;
import net.coobird.thumbnailator.Thumbnails;

import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileManagerService implements IFileManagerService {
    @Resource
    private SysConfigUtil configUtil;
    private static long  thumSize =  2*1024*1024;
    Pattern p = Pattern.compile(".*(:|\\?|\\*|\\\\|/|<|>|\"|\\|).*");

    @Override
    public UploadResult uploadFile(CommonsMultipartFile cmfile, String folder, String fileName,long fileSize) throws Exception {
        fileName = fileName.trim();
        if (fileName.length() == 0)
            throw new Exception("请选择文件！");
        Matcher mat = p.matcher(fileName);
        if (mat.matches())
            throw new Exception("文件名中不能存在[：? * \\/\"| < >]这些符号！");
        mat = p.matcher(folder);
        if (mat.matches())
            throw new Exception("文件夹名中不能存在[：? * \\/\"| < >]这些符号！");

        String ext = "";
        int dotpos = fileName.lastIndexOf(".");
        if (dotpos > 0) {
            ext = fileName.substring(dotpos + 1);
            ext = ext.toLowerCase();
            if (ext.length() > 5)
                ext = "";
        }
        boolean bHasThumbnailImage = ext.equalsIgnoreCase("jpg") ||
                ext.equalsIgnoreCase("bmp") ||
                ext.equalsIgnoreCase("png") ||
                ext.equalsIgnoreCase("jpeg") ||
                ext.equalsIgnoreCase("gif");

        String hash = Hash.getFileHash(cmfile);
        String firstPath = StringUtils.hasText(ext) ? ext + "/" : "";
        String basePath = this.getbasePath("upload.basepath");
        String filePath = basePath + "/" + folder + "/" + firstPath + hash.charAt(0) + "/" + hash.charAt(1);
        File filedir = new File(filePath);
        if (!filedir.exists())  filedir.mkdirs();
        File file = new File(filePath + "/" + hash + "." + ext);
        if (!file.exists() || file.exists() && (file.length() != cmfile.getSize())) {
            if (bHasThumbnailImage && cmfile.getSize() > thumSize) { //压缩图片
                Thumbnails.of(cmfile.getInputStream()).scale(1f).outputQuality(0.25f).toFile(file);
            }else{
                cmfile.transferTo(file);
            }
        }
        String prefix = "thumb-";
        if (bHasThumbnailImage) {
//            ImageUtils imageUtils = new ImageUtils();
//            imageUtils.thumbnailImage(file, 200, 1, prefix, false);
        	if(ext.equalsIgnoreCase("pdf"))
        	{        		
        		new PdfImage(file).thumbnailImage(1, 200f, 200f,"jpg", filePath + "/" + prefix + hash + ".jpg");
        	}else{
	            File thumbnailFile = new File(filePath + "/" + prefix + hash + "." + ext);
	            Thumbnails.of(file).size(200, 200).toFile(thumbnailFile);
        	}
        }

        UploadResult uresult = new UploadResult();
        uresult.setId(hash);
        uresult.setExt(ext);
        uresult.setName(fileName);
        uresult.setFileSize(fileSize);
        if (bHasThumbnailImage)
        {
        	if(ext.equalsIgnoreCase("pdf"))
        	{
        		uresult.setThumbnailId(prefix + hash+ ".jpg");
        	}else
        		uresult.setThumbnailId(prefix + hash+ "." + ext);
        }
        uresult.setPath(folder + "/" + firstPath + hash.charAt(0) + "/" + hash.charAt(1));

        return uresult;
    }

	@Override
	public void fileDownload(HttpServletResponse response, String filePath, String fileName) throws IOException {
		// TODO Auto-generated method stub
		String basePath = this.getbasePath("upload.basepath");
        filePath=basePath+"/"+filePath;
		byte[] data = toByteArray2(filePath);  
//	    fileName = URLEncoder.encode(fileName, "UTF-8");  
	    fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
	    response.reset();  
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
	    response.addHeader("Content-Length", "" + data.length);  
	    response.setContentType("application/octet-stream;charset=UTF-8"); 
	    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
	    outputStream.write(data);  
	    outputStream.flush();  
	    outputStream.close();
	    response.flushBuffer();
	}
	private String getbasePath(String keyName){
        String basePath = configUtil.getValue(keyName);
        if (basePath.indexOf(";") > 0) {
            String[] ss = basePath.split(";");
            if (System.getProperty("os.name").toLowerCase().indexOf("window") >= 0)  basePath = ss[0];
            else basePath = ss[1];
        }
        return basePath;
    }
	/**
	 * 读取到字节数组2
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray2(String filePath) throws IOException {
		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    @Override
    public String addPicShuiYin(PicPathVo picPath) throws Exception {
	    String realPicPath = picPath.getPicPath().substring(picPath.getPicPath().indexOf("upload/")+7);
        String oldFilePath = this.getbasePath("upload.basepath")+"/"+realPicPath;
        String newFileName = this.getbasePath("upload.basepath.shuiyin")+"/"+realPicPath;
        String newFilePath = newFileName.substring(0,newFileName.lastIndexOf("/"));
        String warterMarkPath = this.getbasePath("shuiyinPic");
        File newFile = new File(newFilePath);
        if (!newFile.exists()){
            newFile.mkdirs();
        }
        BufferedImage watermarkImage = ImageIO.read(new File(warterMarkPath));
        Thumbnails.of(oldFilePath).scale(1f).watermark((enclosingWidth, enclosingHeight, width, height, insetLeft, insetRight, insetTop, insetBottom) -> {
            int x = enclosingWidth/4*3 - width/2 - insetRight;
            int y = enclosingHeight/4*3 - height/2 - insetBottom;
            return new Point(x, y);
        }, watermarkImage, 0.5f).toFile(newFileName);
        return newFileName;
    }

    @Override
    public List<PicPathVo> addPicsShuiYin(List<PicPathVo> lstPic) throws Exception {
        for (PicPathVo picPathVo : lstPic) {
            String newPath = this.addPicShuiYin(picPathVo);
            picPathVo.setNewPath(newPath);
        }
        return lstPic;
    }
}
