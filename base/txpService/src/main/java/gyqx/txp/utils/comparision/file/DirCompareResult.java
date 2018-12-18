package gyqx.txp.utils.comparision.file;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class DirCompareResult 
{
	private String name;
	/**
	 * 需要删除的文件列表。
	 */
	private List<String> delFiles = new ArrayList<>();
	/**
	 * 新增的文件。(更新的文件将先删除后增加)
	 */
	private List<FileData> addFiles = new ArrayList<>();
	
	/**
	 * 子文件夹(不存在的需要新增)
	 */
	private List<DirCompareResult> subDirCompareResult = new ArrayList<>();
	
	public boolean noop()
	{
		return CollectionUtils.isEmpty(delFiles) && 
				CollectionUtils.isEmpty(addFiles) && 
				CollectionUtils.isEmpty(subDirCompareResult);
		
	}

	public List<String> getDelFiles() {
		return delFiles;
	}

	public void setDelFiles(List<String> delFiles) {
		this.delFiles = delFiles;
	}

	public List<FileData> getAddFiles() {
		return addFiles;
	}

	public void setAddFiles(List<FileData> addFiles) {
		this.addFiles = addFiles;
	}

	public List<DirCompareResult> getSubDirCompareResult() {
		return subDirCompareResult;
	}

	public void setSubDirCompareResult(List<DirCompareResult> subDirCompareResult) {
		this.subDirCompareResult = subDirCompareResult;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DirCompareResult [name=" + name + ", delFiles=" + delFiles.size() + ", addFiles=" + addFiles.size()
				+ ", subDirCompareResult=" + subDirCompareResult.size() + "]";
	}	

}
