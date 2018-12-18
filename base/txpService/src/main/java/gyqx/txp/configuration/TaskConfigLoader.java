package gyqx.txp.configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyqx.txp.utils.groovy.GroovyScriptHelper;
import gyqx.txp.utils.json.Json;

public class TaskConfigLoader 
{
	final static Logger logger = LoggerFactory.getLogger(TaskConfigLoader.class);
	public TaskConfigLoader()
	{
	}	
	private void load(TxpConfigBase configBase,
			String syncTaskConfigsPath,
			String dataUploadTaskConfigsPath,
			String dataDownloadTaskConfigsPath,
			String fileDownloadTaskConfigsPath,
			String fileUploadTaskConfigsPath,
			String transferTaskConfigsPath,
			String fileCompareConfigsPath)
	{

		File syncTaskConfigsPathDir = FileUtils.toFile(this.getClass().getResource(syncTaskConfigsPath));
		File dataUploadTaskConfigsPathDir = FileUtils.toFile(this.getClass().getResource(dataUploadTaskConfigsPath));
		File dataDownloadTaskConfigsPathDir = FileUtils.toFile(this.getClass().getResource(dataDownloadTaskConfigsPath));
		File fileDownloadTaskConfigsPathDir = FileUtils.toFile(this.getClass().getResource(fileDownloadTaskConfigsPath));
		File fileUploadTaskConfigsPathDir = FileUtils.toFile(this.getClass().getResource(fileUploadTaskConfigsPath));
		File transferTaskConfigsPathDir = FileUtils.toFile(this.getClass().getResource(transferTaskConfigsPath));
		File fileCompareConfigsPathDir = FileUtils.toFile(this.getClass().getResource(fileCompareConfigsPath));
		
		FileUtils.listFiles(syncTaskConfigsPathDir, new String[]{"groovy"}, false).forEach(file->{
			try {
				SyncTaskConfig config = getObject(file,SyncTaskConfig.class);
				List<String> tableNames = new ArrayList<>();
				for(TableConfig tcfg:config.getTableConfigs())
				{
					tableNames.add(tcfg.getTableName().toLowerCase());
				}
				config.setTableNames(tableNames);
				config.setCatalog(SyncTaskConfig.catalog_code );
				configBase.getSyncTaskConfigs().add(config);
			} catch (Exception e) {
				logger.error("配置出错：{}",file.toString());
				throw new RuntimeException(e);
			}
		});
		
		if(dataUploadTaskConfigsPathDir != null)
		FileUtils.listFiles(dataUploadTaskConfigsPathDir, new String[]{"groovy"}, false).forEach(file->{
			try {
				DataUploadTaskConfig config = getObject(file,DataUploadTaskConfig.class);
				
				List<String> tableNames = new ArrayList<>();
				for(TableConfig tcfg:config.getTableConfigs())
				{
					tableNames.add(tcfg.getTableName().toLowerCase());
				}
				config.setTableNames(tableNames);
				config.setCatalog(DataUploadTaskConfig.catalog_code );
				configBase.getDataUploadTaskConfigs().add(config);
			} catch (Exception e) {
				logger.error("配置出错：{}",file.toString());
				throw new RuntimeException(e);
			}
		});
		
		if(dataDownloadTaskConfigsPathDir != null)
		FileUtils.listFiles(dataDownloadTaskConfigsPathDir, new String[]{"groovy"}, false).forEach(file->{
			try {
				DataDownloadTaskConfig config = getObject(file,DataDownloadTaskConfig.class);
				List<String> tableNames = new ArrayList<>();
				for(TableConfig tcfg:config.getTableConfigs())
				{
					tableNames.add(tcfg.getTableName().toLowerCase());
				}
				config.setTableNames(tableNames);
				config.setCatalog(DataDownloadTaskConfig.catalog_code );
				configBase.getDataDownloadTaskConfigs().add(config);
			} catch (Exception e) {
				logger.error("配置出错：{}",file.toString());
				throw new RuntimeException(e);
			}
		});
		
		if(fileDownloadTaskConfigsPathDir != null)
		FileUtils.listFiles(fileDownloadTaskConfigsPathDir, new String[]{"groovy"}, false).forEach(file->{
			try {
				FileDownloadTaskConfig config = getObject(file,FileDownloadTaskConfig.class);
				
				config.setCatalog(FileDownloadTaskConfig.catalog_code);
				configBase.getFileDownloadTaskConfigs().add(config);
			} catch (Exception e) {
				logger.error("配置出错：{}",file.toString());
				throw new RuntimeException(e);
			}
		});
		if(fileUploadTaskConfigsPathDir != null)
			FileUtils.listFiles(fileUploadTaskConfigsPathDir, new String[]{"groovy"}, false).forEach(file->{
				try {
					FileUploadTaskConfig syncTaskConfig = getObject(file,FileUploadTaskConfig.class);
					syncTaskConfig.setCatalog(FileUploadTaskConfig.catalog_code);
					configBase.getFileUploadTaskConfigs().add(syncTaskConfig);
				} catch (Exception e) {
					logger.error("配置出错：{}",file.toString());
					throw new RuntimeException(e);
				}
			});
		
		if(transferTaskConfigsPathDir != null)
		FileUtils.listFiles(transferTaskConfigsPathDir, new String[]{"groovy"}, false).forEach(file->{
			try {
				TransferTaskConfig transferTaskConfig = getObject(file,TransferTaskConfig.class);
				transferTaskConfig.setCatalog(TransferTaskConfig.catalog_code);
				configBase.getTransferTaskConfigs().add(transferTaskConfig);
			} catch (Exception e) {
				logger.error("配置出错：{}",file.toString());
				throw new RuntimeException(e);
			}
		});
		
		if(fileCompareConfigsPathDir != null)
			FileUtils.listFiles(fileCompareConfigsPathDir, new String[]{"groovy"}, false).forEach(file->{
				try {
					SiteFileCompareConfig config = getObject(file,SiteFileCompareConfig.class);
					configBase.getSiteFileCompareConfigs().add(config);
				} catch (Exception e) {
					logger.error("配置出错：{}",file.toString());
					throw new RuntimeException(e);
				}
			});
	}
	public TxpClientConfig loadTxpClientConfig(String cfgfile,
			String syncTaskConfigsPath,
			String dataUploadTaskConfigsPath,
			String dataDownloadTaskConfigsPath,
			String fileDownloadTaskConfigsPath,
			String fileUploadTaskConfigsPath,
			String transferTaskConfigsPath,
			String fileCompareConfigsPath) throws Exception
	{

		File cfgFile = FileUtils.toFile(this.getClass().getResource( cfgfile));
		String script = FileUtils.readFileToString(cfgFile, "utf8");
		
		TxpClientConfig txpClientConfig = getObject(script,TxpClientConfig.class);
		
		load(txpClientConfig,
				syncTaskConfigsPath,
			 dataUploadTaskConfigsPath,
			 dataDownloadTaskConfigsPath,
			 fileDownloadTaskConfigsPath,
			 fileUploadTaskConfigsPath,
			 transferTaskConfigsPath,
			 fileCompareConfigsPath);
		
		return txpClientConfig;
	}
	
	public TxpServerConfig loadTxpServerConfig(String cfgfile,String syncTaskConfigsPath,
			String dataUploadTaskConfigsPath,
			String dataDownloadTaskConfigsPath,
			String fileDownloadTaskConfigsPath,
			String fileUploadTaskConfigsPath,
			String transferTaskConfigsPath,
			String fileCompareConfigsPath ) throws Exception
	{
		
		File cfgFile = FileUtils.toFile(this.getClass().getResource(cfgfile));
		String script = FileUtils.readFileToString(cfgFile, "utf8");
		
		TxpServerConfig txpServerConfig = getObject(script,TxpServerConfig.class);
		
		load(txpServerConfig,
			syncTaskConfigsPath,
			 dataUploadTaskConfigsPath,
			 dataDownloadTaskConfigsPath,
			 fileDownloadTaskConfigsPath,
			 fileUploadTaskConfigsPath,
			 transferTaskConfigsPath,
			 fileCompareConfigsPath);
			
		
		return txpServerConfig;
	}	
	
	public <T> T getObject(String groovyScript,Class<T> cls) throws Exception
	{
		GroovyScriptHelper helper = new GroovyScriptHelper();
		Object obj = helper.eval(groovyScript);
		String json = Json.write(obj);
		T cfg0 = Json.read(json, cls);
		return cfg0;
	}
	public <T> T getObject(File file,Class<T> cls) throws Exception
	{
		String script = FileUtils.readFileToString(file, "utf8");
		return getObject(script,cls);
	}
	
	

}
