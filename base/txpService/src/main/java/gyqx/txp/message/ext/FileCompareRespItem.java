package gyqx.txp.message.ext;

import gyqx.txp.utils.comparision.file.DirCompareResult;

public class FileCompareRespItem 
{
	private String key;
	private DirCompareResult result;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public DirCompareResult getResult() {
		return result;
	}
	public void setResult(DirCompareResult result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "FileCompareRespItem [key=" + key + "]";
	}

}
