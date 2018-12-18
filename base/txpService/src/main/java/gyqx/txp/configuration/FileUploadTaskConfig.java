package gyqx.txp.configuration;

public class FileUploadTaskConfig extends TaskConfig
{
	public static final String catalog_code = "fileUpload";
	private String basePath;
	private String findFilenamesExpression;
	private String endTransferDataExpression;

	public String getFindFilenamesExpression() {
		return findFilenamesExpression;
	}

	public void setFindFilenamesExpression(String findFilenamesExpression) {
		this.findFilenamesExpression = findFilenamesExpression;
	}

	public String getEndTransferDataExpression() {
		return endTransferDataExpression;
	}

	public void setEndTransferDataExpression(String endTransferDataExpression) {
		this.endTransferDataExpression = endTransferDataExpression;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public void init() {
		
	}

}
