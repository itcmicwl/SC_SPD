package gyqx.txp.configuration;

import java.util.List;

public class SiteFileCompareConfig extends TaskConfig
{
	public static final String catalog_code = "fileCompare";
	private String siteId;
	private String script;
	private String direct = "fromCenter"; // "toCenter"
	private List<FileCompareConfig> configs;
	
	public SiteFileCompareConfig()
	{
		catalog = catalog_code;
	}

	public FileCompareConfig find(String key)
	{
		for(FileCompareConfig fcc: configs)
		{
			if(fcc.getKey().equalsIgnoreCase(key))
				return fcc;
		}
		return null;
	}

	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public List<FileCompareConfig> getConfigs() {
		return configs;
	}
	public void setConfigs(List<FileCompareConfig> configs) {
		this.configs = configs;
	}
	
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

}
