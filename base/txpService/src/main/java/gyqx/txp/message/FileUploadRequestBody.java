package gyqx.txp.message;

import java.util.List;

import gyqx.txp.data.FilePathData;

public class FileUploadRequestBody 
{
	private List<FilePathData> filePathDataItems;

	public List<FilePathData> getFilePathDataItems() {
		return filePathDataItems;
	}
	public void setFilePathDataItems(List<FilePathData> filePathDataItems) {
		this.filePathDataItems = filePathDataItems;
	}	

}
