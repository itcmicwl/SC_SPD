package gyqx.txp.message;

import java.util.Map;

public class DataDownloadRequestBody implements java.io.Serializable
{
	private Map<String,Object> requestParams;

	public Map<String,Object> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String,Object> requestParams) {
		this.requestParams = requestParams;
	}
	

}
