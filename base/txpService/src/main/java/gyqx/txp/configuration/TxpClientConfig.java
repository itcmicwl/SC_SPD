package gyqx.txp.configuration;


import org.apache.commons.beanutils.BeanUtils;

import gyqx.txp.db.meta.DbMetaInfo;
import gyqx.txp.utils.info.MachineInfo;

public class TxpClientConfig extends TxpConfigBase
{
	public static TxpClientConfig cfg = null;
	
	private String siteId;
	private String centerDestinationName;
	private String reportDestinationName;
	private String reportUri;	
	private String clientId;	

	public TxpClientConfig(){}
	
	public TxpClientConfig(String cfgfile,String syncTaskConfigsPath,
			String dataUploadTaskConfigsPath,
			String dataDownloadTaskConfigsPath,
			String fileDownloadTaskConfigsPath,
			String fileUploadTaskConfigsPath,
			String transferTaskConfigsPath,
			String fileCompareConfigsPath) throws Exception
	{

		TaskConfigLoader loader = new TaskConfigLoader();
		
		TxpClientConfig cfg0 = loader.loadTxpClientConfig(cfgfile, syncTaskConfigsPath,
				dataUploadTaskConfigsPath,
				dataDownloadTaskConfigsPath,
				fileDownloadTaskConfigsPath, fileUploadTaskConfigsPath,
				transferTaskConfigsPath,fileCompareConfigsPath);
		
		BeanUtils.copyProperties(this,cfg0);
		
		cfg = this;
		cfg.setDefault();
		clientId = MachineInfo.clientId();
	}
	public void checkTaskConfigs(DbMetaInfo dbMeta)
	{
		super.checkTaskConfigs(dbMeta);
		
	}

	public String getCenterDestinationName() {
		return centerDestinationName;
	}

	public void setCenterDestinationName(String centerDestinationName) {
		this.centerDestinationName = centerDestinationName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getReportDestinationName() {
		return reportDestinationName;
	}

	public void setReportDestinationName(String reportDestinationName) {
		this.reportDestinationName = reportDestinationName;
	}

	public String getReportUri() {
		return reportUri;
	}

	public void setReportUri(String reportUri) {
		this.reportUri = reportUri;
	}	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
