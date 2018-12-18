package gyqx.txp.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCompareConfig 
{
	private String key;
	private String basePath;
	private List<String> excludeFiles = new ArrayList<>();
	private List<String> excludePaths = new ArrayList<>();
	
	private Map<String,String> map = null;	
	
	public void createCache()
	{
		map = new HashMap<>();
		for(String fn :excludeFiles)
		{
			fn = basePath + "/" + fn;
			if(fn.indexOf('\\')>=0)
				fn = fn.replace("\\","/");
			map.put(fn, "1");
		}
		for(String fn :excludePaths)
		{
			fn = basePath + "/" + fn;
			if(fn.indexOf('\\')>=0)
				fn = fn.replace("\\","/");
			map.put(fn, "1");
		}
	}
	public synchronized boolean needExclude(File file)
	{
		if(map == null)
			createCache();
		String fn = file.getAbsolutePath();
		if(fn.indexOf('\\')>=0)
			fn =  fn.replace("\\","/");
		
		return map.get(fn) != null;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public List<String> getExcludeFiles() {
		return excludeFiles;
	}
	public void setExcludeFiles(List<String> excludeFiles) {
		this.excludeFiles = excludeFiles;
	}
	public List<String> getExcludePaths() {
		return excludePaths;
	}
	public void setExcludePaths(List<String> excludePaths) {
		this.excludePaths = excludePaths;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}	

}
