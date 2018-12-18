package gyqx.txp.configuration;

import java.util.List;

public class SiteInfo 
{
	private String siteId;
	private String siteName;
	private String terminalCode;
	private List<HospInfo> hospInfos;
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public List<HospInfo> getHospInfos() {
		return hospInfos;
	}
	public void setHospInfos(List<HospInfo> hospInfos) {
		this.hospInfos = hospInfos;
	}
	public String getTerminalCode() {
		return terminalCode;
	}
	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}
	@Override
	public String toString() {
		return "SiteInfo [siteId=" + siteId + ", siteName=" + siteName + ", terminalCode=" + terminalCode
				+ ", hospInfos=" + hospInfos.size() + "]";
	}	

}
