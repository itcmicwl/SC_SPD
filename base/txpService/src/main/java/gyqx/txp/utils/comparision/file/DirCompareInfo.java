package gyqx.txp.utils.comparision.file;

import java.util.ArrayList;
import java.util.List;

public class DirCompareInfo 
{
	private String name;
	private boolean omit;
	
	private List<FileCompareInfo> fileInfoList = new ArrayList<>();
	private List<DirCompareInfo> subDirInfoList = new ArrayList<>();
	
	public FileCompareInfo findFileCompareInfo(String name)
	{
		for(FileCompareInfo fi:fileInfoList)
		{
			if(fi.getFileName().equalsIgnoreCase(name))
				return fi;
		}
		return null;
	}
	
	public DirCompareInfo findSubDirCompareInfo(String name)
	{

		for(DirCompareInfo dci: subDirInfoList)
		{
			if(dci.getName().equalsIgnoreCase(name))
				return dci;
		}
		return null;
	}

	public List<FileCompareInfo> getFileInfoList() {
		return fileInfoList;
	}

	public void setFileInfoList(List<FileCompareInfo> fileInfoList) {
		this.fileInfoList = fileInfoList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DirCompareInfo> getSubDirInfoList() {
		return subDirInfoList;
	}

	public void setSubDirInfoList(List<DirCompareInfo> subDirInfoList) {
		this.subDirInfoList = subDirInfoList;
	}
	
	public boolean isOmit() {
		return omit;
	}

	public void setOmit(boolean omit) {
		this.omit = omit;
	}

	@Override
	public String toString() {
		return "DirCompareInfo [name=" + name +",omit="+omit + ", fileInfoList=" + fileInfoList.size() + ", subDirInfoList=" + subDirInfoList.size()
				+ "]";
	}

	

	

}
