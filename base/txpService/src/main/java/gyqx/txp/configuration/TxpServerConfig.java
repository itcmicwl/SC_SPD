package gyqx.txp.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.Assert;

import gyqx.txp.db.meta.DbMetaInfo;
import gyqx.txp.utils.info.MachineInfo;


public class TxpServerConfig extends TxpConfigBase
{
	public static TxpServerConfig cfg = null;
	
	private String centerDestinationName;
	private int fileDownloadDataMaxLength;
	private Map<String,List<HospInfo>> mapSiteHospitalIfs;
	private List<SiteInfo> siteInfos;
	private transient Map<String,SiteInfo> mapSiteInfo = null;
	private transient Map<String,String> mapClientId2SiteId = null;
	
	public TxpServerConfig(){}
	
	public TxpServerConfig(String cfgfile,String syncTaskConfigsPath,
			String dataUploadTaskConfigsPath,
			String dataDownloadTaskConfigsPath,
			String fileDownloadTaskConfigsPath,
			String fileUploadTaskConfigsPath,
			String transferTaskConfigsPath,
			String fileCompareConfigsPath
			) throws Exception
	{
		TaskConfigLoader loader = new TaskConfigLoader();
		
		TxpServerConfig cfg0 = loader.loadTxpServerConfig(cfgfile,
				syncTaskConfigsPath, 
				dataUploadTaskConfigsPath,
				dataDownloadTaskConfigsPath,
				fileDownloadTaskConfigsPath,
				fileUploadTaskConfigsPath,
				transferTaskConfigsPath,
				fileCompareConfigsPath);
		
		BeanUtils.copyProperties(this,cfg0);
		
		cfg = this;
		cfg.setDefault();
	}
	
	public void checkTaskConfigs(DbMetaInfo dbMeta)
	{
		super.checkTaskConfigs(dbMeta);
		
		mapSiteInfo = new HashMap<>();
		Map<String,SiteInfo> maptmc = new HashMap<>();
		for(SiteInfo si:siteInfos)
		{
			Assert.isTrue(mapSiteInfo.get(si.getSiteId()) == null,
					"重复的siteID:" + si.getSiteId());
			Assert.isTrue(maptmc.get(si.getTerminalCode()) == null,
					"重复的terminalCode:"+si.getTerminalCode()+";SiteId=" +si.getSiteId());
			mapSiteInfo.put(si.getSiteId(), si);
			maptmc.put(si.getTerminalCode(), si);
		}
		
	}
	
	public SiteInfo validate(String siteId,String terminalCode)
	{
		SiteInfo si = querySiteInfoBySiteId(siteId);
		Assert.isTrue(si != null,"不存在的siteId:" + siteId);
		
		Assert.isTrue(si.getTerminalCode().trim().equalsIgnoreCase(terminalCode.trim()),
				"错误的验证码:" +terminalCode );

		return si;
	}
	public SiteInfo querySiteInfoBySiteId(String siteId)
	{
		return mapSiteInfo.get(siteId);
	}
	
	

	public String getCenterDestinationName() {
		return centerDestinationName;
	}

	public void setCenterDestinationName(String centerDestinationName) {
		this.centerDestinationName = centerDestinationName;
	}

	

	public int getFileDownloadDataMaxLength() {
		return fileDownloadDataMaxLength;
	}

	public void setFileDownloadDataMaxLength(int fileDownloadDataMaxLength) {
		this.fileDownloadDataMaxLength = fileDownloadDataMaxLength;
	}

	public Map<String,List<HospInfo>> getMapSiteHospitalIfs() {
		return mapSiteHospitalIfs;
	}
	
	public void setMapSiteHospitalIfs(Map<String,List<HospInfo>> mapSiteHospitalIfs) {
		this.mapSiteHospitalIfs = mapSiteHospitalIfs;
	}

	public List<SiteInfo> getSiteInfos() {
		return siteInfos;
	}

	public void setSiteInfos(List<SiteInfo> siteInfos) {
		this.siteInfos = siteInfos;
	}

	

}
