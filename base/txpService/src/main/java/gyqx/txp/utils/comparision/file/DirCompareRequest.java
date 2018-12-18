package gyqx.txp.utils.comparision.file;

public class DirCompareRequest 
{
	private String key;
	private DirCompareInfo compareInfo;
	@Override
	public String toString() {
		return "CompareRequest [key=" + key + ", compareInfo=" + compareInfo + "]";
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public DirCompareInfo getCompareInfo() {
		return compareInfo;
	}
	public void setCompareInfo(DirCompareInfo compareInfo) {
		this.compareInfo = compareInfo;
	}
	

}
