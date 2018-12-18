package gyqx.txp.message;

import java.util.List;

import gyqx.txp.data.FilePath;
import gyqx.txp.data.FilePathData;

public class FileDownloadResponseBody implements java.io.Serializable
{
	private static final long serialVersionUID = 1489013450202418189L;
	
	private List<FilePathData> filePathDataItems;

	public List<FilePathData> getFilePathDataItems() {
		return filePathDataItems;
	}
	public void setFilePathDataItems(List<FilePathData> filePathDataItems) {
		this.filePathDataItems = filePathDataItems;
	}	

}
