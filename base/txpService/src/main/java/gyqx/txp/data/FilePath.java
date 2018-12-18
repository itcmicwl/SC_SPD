package gyqx.txp.data;

public class FilePath implements java.io.Serializable
{
	private String fileName;
	
	private String relativePath;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

}
