package gyqx.txp.utils.comparision.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;

import gyqx.txp.configuration.FileCompareConfig;
import gyqx.txp.utils.Hash;

public class FileCompareUtil
{
	/**
	 * 生成目录的比较信息
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public static DirCompareInfo createDirCompareInfo(FileCompareConfig config) throws Exception
	{
		return createDirCompareInfo(config,new File(config.getBasePath()));
	}
	/**
	 * 生成目录的比较信息
	 * @param basePath
	 * @return
	 * @throws Exception
	 */
	public static DirCompareInfo createDirCompareInfo(FileCompareConfig config,File basePath ) throws Exception
	{

		File[] files = basePath.listFiles();
		DirCompareInfo dirci = new DirCompareInfo();
		dirci.setName(basePath.getName());
		
		for(File file:files)
		{			
			if(file.isDirectory())
			{
				DirCompareInfo subDirCi = null;
				if(config.needExclude(file))
				{
					subDirCi =new DirCompareInfo();
					subDirCi.setName(file.getName());
					subDirCi.setOmit(true);
				}else{
				
					subDirCi = createDirCompareInfo(config,file);
					if(dirci.getSubDirInfoList() == null)
					{
						dirci.setSubDirInfoList(new ArrayList<>());	
					}
				}
				dirci.getSubDirInfoList().add(subDirCi);
				
			}else{
				FileCompareInfo fci = new FileCompareInfo();
				fci.setFileName(file.getName());
				fci.setSize(file.length());
				if(config.needExclude(file))
				{
					fci.setOmit(true);
				}else{
					String hash = Hash.getFileHash(file, "md5");
					fci.setHash(hash);					
				}
				dirci.getFileInfoList().add(fci);
			}
		}
		
		return dirci;
	}
	
	public static DirCompareResult compare(
			FileCompareConfig config,
			DirCompareRequest request) throws Exception
	{
		return compare(new File(config.getBasePath()),request.getCompareInfo());
	}

	/**
	 * basePath 与 target 进行比较
	 * @param basePath
	 * @param config
	 * @param target
	 * @return
	 * @throws Exception 
	 */
	public static DirCompareResult compare(
			File basePath,
			DirCompareInfo target) throws Exception
	{
		DirCompareResult result = new DirCompareResult();
		result.setName(target.getName());
		
		Map<String,File> mapFiles = new HashMap<>();
		for(File file:basePath.listFiles())
		{
			mapFiles.put(file.getName(), file);
		}
		for(DirCompareInfo dci: target.getSubDirInfoList())
		{
			if(dci.isOmit())
				continue;
			
			File sf =  mapFiles.get(dci.getName());			
			if(sf == null || !sf.isDirectory())
			{
				result.getDelFiles().add(dci.getName());
			}
		}
		for(FileCompareInfo fci: target.getFileInfoList())
		{
			if(fci.isOmit())
				continue;
			File sf =  mapFiles.get(fci.getFileName());
			if(sf == null || sf.isDirectory())
			{
				result.getDelFiles().add(fci.getFileName());
			}
		}
		for(File file:basePath.listFiles())
		{
			if(file.isDirectory())
			{
				DirCompareInfo fdir = target.findSubDirCompareInfo(file.getName());
				if(fdir == null)
				{
					DirCompareResult n = createDirCompareResult(file);
					if(!n.noop())
						result.getSubDirCompareResult().add(n);
				}else{
					if(fdir.isOmit())
						continue;
					DirCompareResult subDcResult = compare(file,fdir);
					if(!subDcResult.noop())
						result.getSubDirCompareResult().add(subDcResult);
				}
			}else{
				FileCompareInfo fci = target.findFileCompareInfo(file.getName());
				if(fci == null)
				{
					FileData fd = new FileData();
					fd.setName(file.getName());
					byte[] data = FileUtils.readFileToByteArray(file);
					fd.setData(data);
					result.getAddFiles().add(fd);
				}else{
					if(fci.isOmit())
						continue;
					String hash = Hash.getFileHash(file, "md5");
					long length = file.length();
					if(!fci.getHash().equalsIgnoreCase(hash) || fci.getSize() != length)
					{ // 先删后加
						result.getDelFiles().add(fci.getFileName());
						
						FileData fd = new FileData();
						fd.setName(file.getName());
						byte[] data = FileUtils.readFileToByteArray(file);
						fd.setData(data);
						result.getAddFiles().add(fd);
					}
				}
			}
		}
		return result;
	}
	/**
	 * 根据文件夹比较结果合并到目标文件夹
	 * @param config
	 * @param result
	 * @throws IOException
	 */
	public static void merge(FileCompareConfig config,DirCompareResult result) throws IOException
	{
		merge(new File(config.getBasePath()),result);
	}
	/**
	 * 根据文件夹比较结果合并到目标文件夹
	 * @param config
	 * @param basePath
	 * @param result
	 * @throws IOException 
	 */
	public static void merge(File basePath,DirCompareResult result) throws IOException
	{
		Assert.isTrue(basePath.getName().equalsIgnoreCase(result.getName()),
				"目标文件夹与将要合并的目录名不一致！");
		
		for(String delFileName : result.getDelFiles())
		{
			File subFile = new File( basePath + "/"+ delFileName);
			if(!subFile.exists())
				continue;
			if(subFile.isFile())
				subFile.delete();
			if(subFile.isDirectory())
				FileUtils.deleteDirectory(subFile);
		}
		for(FileData fd:result.getAddFiles())
		{
			File subFile = new File( basePath + "/"+ fd.getName());
			if(subFile.exists())
				subFile.delete();
			if(!subFile.createNewFile())
				throw new IOException("创建文件出错：" + subFile.getAbsolutePath());
			FileUtils.writeByteArrayToFile(subFile, fd.getData());			
		}
		for(DirCompareResult subResult: result.getSubDirCompareResult())
		{
			File subFile = new File( basePath + "/"+ subResult.getName());
			if(!subFile.exists())
			{
				subFile.mkdir();
			}
			merge(subFile,subResult);
		}	
		
	}
	/**
	 * 根据目录生成全部文件的比较结构及内容
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public static DirCompareResult createDirCompareResult(File dir) throws IOException
	{
		if(!dir.isDirectory())
			return null;
		DirCompareResult dircr = new DirCompareResult();
		dircr.setName(dir.getName());
		for(File file:dir.listFiles())
		{
			if(file.isDirectory())
			{
				DirCompareResult subcr = createDirCompareResult(file); 
				if(subcr != null)
				{
					if(dircr.getSubDirCompareResult() == null)
						dircr.setSubDirCompareResult(new ArrayList<>());
					dircr.getSubDirCompareResult().add(subcr);
				}
			}else{
				if(dircr.getAddFiles() == null)
					dircr.setAddFiles(new ArrayList<>());
				FileData fd = new FileData();
				fd.setName(file.getName());
				byte[] data = FileUtils.readFileToByteArray(file);
				fd.setData(data);
				dircr.getAddFiles().add(fd);
			}
		}
		return dircr;
	}

}
