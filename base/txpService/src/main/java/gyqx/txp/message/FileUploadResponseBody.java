package gyqx.txp.message;

import java.util.List;

import gyqx.txp.data.FilePath;

public class FileUploadResponseBody
{
	private List<FilePath> filePaths;

	public List<FilePath> getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(List<FilePath> filePaths) {
		this.filePaths = filePaths;
	}

}
