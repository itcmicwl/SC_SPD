package gyqx.txp.message;

import java.util.List;

import gyqx.txp.data.FilePath;

public class FileDownloadRequestBody implements java.io.Serializable
{
	private static final long serialVersionUID = 1489013200202418189L;
	
	private List<FilePath> filePaths;

	public List<FilePath> getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(List<FilePath> filePaths) {
		this.filePaths = filePaths;
	}

}
