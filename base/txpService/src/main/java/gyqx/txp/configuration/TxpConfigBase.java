package gyqx.txp.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import gyqx.txp.db.meta.DbMetaInfo;
import gyqx.txp.db.meta.TableMeta;

public class TxpConfigBase 
{
	private static final Logger logger = LoggerFactory.getLogger(TxpConfigBase.class);
	
	private String fileDownloadPath;
	private String clientId;

	private List<SyncTaskConfig> syncTaskConfigs;	
	private transient Map<String,SyncTaskConfig> syncTaskConfigsMap;		
	private List<DataUploadTaskConfig> dataUploadTaskConfigs;	
	private transient Map<String,DataUploadTaskConfig> dataUploadTaskConfigsMap;	
	
	private List<DataDownloadTaskConfig> dataDownloadTaskConfigs;	
	private transient Map<String,DataDownloadTaskConfig> dataDownloadTaskConfigsMap;
	
	private List<FileDownloadTaskConfig> fileDownloadTaskConfigs;	
	private transient Map<String,FileDownloadTaskConfig> fileDownloadTaskConfigsMap;	
	
	private List<FileUploadTaskConfig> fileUploadTaskConfigs;	
	private transient Map<String,FileUploadTaskConfig> fileUploadTaskConfigsMap;	
	
	private List<TransferTaskConfig> transferTaskConfigs;
	private transient Map<String,TransferTaskConfig> transferTaskConfigsMap;
	
	private List<SiteFileCompareConfig> siteFileCompareConfigs;
	private transient Map<String,SiteFileCompareConfig> siteFileCompareConfigMap;
	
	
	public List<TaskConfig> queryTaskConfigsByTableName(String tableName)
	{
		List<TaskConfig> tasks = new ArrayList<>();
		for(TaskConfig tc:syncTaskConfigs)
		{
			if(tc.hasTable(tableName))
				tasks.add(tc);
		}
		
		for(TaskConfig tc:dataUploadTaskConfigs)
		{
			if(tc.hasTable(tableName))
				tasks.add(tc);
		}
		for(TaskConfig tc:dataDownloadTaskConfigs)
		{
			if(tc.hasTable(tableName))
				tasks.add(tc);
		}
		for(TaskConfig tc:fileDownloadTaskConfigs)
		{
			if(tc.hasTable(tableName))
				tasks.add(tc);
		}
		for(TaskConfig tc:fileUploadTaskConfigs)
		{
			if(tc.hasTable(tableName))
				tasks.add(tc);
		}
		for(TaskConfig tc:transferTaskConfigs)
		{
			if(tc.hasTable(tableName))
				tasks.add(tc);
		}
		return tasks;
	}
	public List<String> getAllTableNames()
	{
		HashMap<String,String> map = new HashMap<>();
		
		for(TaskConfig tc:syncTaskConfigs)
		{
			for(String tn:tc.getTableNames())
			{
				map.put(tn, tn);
			}
		}
		
		for(TaskConfig tc:dataUploadTaskConfigs)
		{
			for(String tn:tc.getTableNames())
			{
				map.put(tn, tn);
			}
		}
		for(TaskConfig tc:dataDownloadTaskConfigs)
		{
			for(String tn:tc.getTableNames())
			{
				map.put(tn, tn);
			}
		}
		for(TaskConfig tc:fileDownloadTaskConfigs)
		{
			for(String tn:tc.getTableNames())
			{
				map.put(tn, tn);
			}
		}
		for(TaskConfig tc:fileUploadTaskConfigs)
		{
			for(String tn:tc.getTableNames())
			{
				map.put(tn, tn);
			}
		}
		for(TaskConfig tc:transferTaskConfigs)
		{
			for(String tn:tc.getTableNames())
			{
				map.put(tn, tn);
			}
		}
		List<String> tableNames = new ArrayList<>();
		map.forEach((k,v)->{
			tableNames.add(k);
		});
		return tableNames;
		
	}
	public TaskConfig getTaskConfig(String taskName,String catalog)
	{
		if(catalog.equals(SyncTaskConfig.catalog_code))
		{
			return getSyncTaskConfig(taskName);
		}else if(catalog.equals(DataUploadTaskConfig.catalog_code))
		{
			return getDataUploadTaskConfig(taskName);
		}else if(catalog.equals(DataDownloadTaskConfig.catalog_code))
		{
			return getDataDownloadTaskConfig(taskName);
		}else if(catalog.equals(FileDownloadTaskConfig.catalog_code))
		{
			return getFileDownloadTaskConfig(taskName);
		}else if(catalog.equals(FileUploadTaskConfig.catalog_code))
		{
			return getFileUploadTaskConfig(taskName);
		}else if(catalog.equals(TransferTaskConfig.catalog_code))
		{
			return getTransferTaskConfig(taskName);
		}else if(catalog.equals(SiteFileCompareConfig.catalog_code))
		{
			return getSiteFileCompareConfig(taskName);
		}

		return null;
	}
	public void setDefault()
	{
		for(TaskConfig tc:syncTaskConfigs)
		{
			tc.init();
		}		
		for(TaskConfig tc:dataUploadTaskConfigs)
		{
			tc.init();
		}
		for(TaskConfig tc:dataDownloadTaskConfigs)
		{
			tc.init();
		}
		for(TaskConfig tc:fileUploadTaskConfigs)
		{
			tc.init();
		}
		for(TaskConfig tc:transferTaskConfigs)
		{
			tc.init();
		}
		for(TaskConfig tc:siteFileCompareConfigs)
		{
			tc.init();
		}
	}
	public void checkTaskConfigs(DbMetaInfo dbMeta)
	{
		Assert.isTrue(StringUtils.hasText(fileDownloadPath)  , "未配置的文件同步的根目录！");
		File filed = new File(fileDownloadPath);
		Assert.isTrue(filed.exists() && filed.isDirectory(),String.format("文件同步的根目录%s不存在！",
				fileDownloadPath));
		Map<String,TaskConfig> map = new HashMap<>();
		Map<String,TableConfig> mapTab = new HashMap<>();
		for(SyncTaskConfig tc:syncTaskConfigs)
		{
			logger.info("正在验证的数据同步任务:{}......", tc.getName());
			Assert.isTrue(map.get(tc.getName() ) == null, String.format("所配置的同步任务名%s重复！", tc.getName()));
			map.put(tc.getName(),tc);
			mapTab.clear();
			for(TableConfig tabc:tc.getTableConfigs())
			{
				Assert.isTrue(mapTab.get(tabc.getTableName()) ==null,
						String.format("所配置的同步任务名%s的表名%s有重复！", tc.getName(),tabc.getTableName()));
				mapTab.put(tabc.getTableName(), tabc);
			}
			for(TableConfig tabc:tc.getTableConfigs())
				checkTableConfig(dbMeta, tabc ,mapTab );
		}
		
		map.clear();
		for(FileDownloadTaskConfig tc:fileDownloadTaskConfigs)
		{
			logger.info("正在验证的文件下载任务:{}......", tc.getName());
			Assert.isTrue(map.get(tc.getName() ) == null, String.format("所配置的文件下载任务名%s重复！", tc.getName()));
			map.put(tc.getName(),tc);
			mapTab.clear();	
			
			if(StringUtils.hasText(tc.getBasePath()))
			{
				File file = new File(tc.getBasePath());
				Assert.isTrue(file.exists() && file.isDirectory(),String.format("文件下载任务%s配置的文件同步的根目录%s不存在！",
						tc.getName(),tc.getBasePath()));
			}
			
			Assert.isTrue(!CollectionUtils.isEmpty( tc.getTableNames())  ,  String.format("文件下载任务%s未配置的表名！",tc.getName()));
			for(String s: tc.getTableNames())
			{
				Assert.isTrue(dbMeta.get(s) != null, String.format("所配置的表名不存在:%s", s));
			}
		}
		map.clear();
		for(FileUploadTaskConfig tc:fileUploadTaskConfigs)
		{
			logger.info("正在验证的文件上传任务:{}......", tc.getName());
			Assert.isTrue(map.get(tc.getName() ) == null, String.format("所配置的文件上传任务名%s重复！", tc.getName()));
			map.put(tc.getName(),tc);
			
			if(StringUtils.hasText(tc.getBasePath()))
			{
				File file = new File(tc.getBasePath());
				Assert.isTrue(file.exists() && file.isDirectory(),String.format("文件上传任务%s配置的文件同步的根目录%s不存在！",
						tc.getName(),tc.getBasePath()));
			}
			
			Assert.isTrue(!CollectionUtils.isEmpty( tc.getTableNames())  ,  String.format("文件上传任务%s未配置的表名！",tc.getName()));
			for(String s: tc.getTableNames())
			{
				Assert.isTrue(dbMeta.get(s) != null, String.format("所配置的表名不存在:%s", s));
			}
		}
		
		map.clear();
		for(DataUploadTaskConfig tc:dataUploadTaskConfigs)
		{
			logger.info("正在验证的数据上传任务:{}......", tc.getName());
			Assert.isTrue(map.get(tc.getName() ) == null, String.format("所配置的数据上传任务名%s重复！", tc.getName()));
			map.put(tc.getName(),tc);
			mapTab.clear();
			for(TableConfig tabc:tc.getTableConfigs())
			{
				Assert.isTrue(mapTab.get(tabc.getTableName()) ==null,
						String.format("所配置的数据上传任务名%s的表名%s有重复！", tc.getName(),tabc.getTableName()));
				mapTab.put(tabc.getTableName(), tabc);
			}
			for(TableConfig tabc:tc.getTableConfigs())
				checkTableConfig(dbMeta, tabc ,mapTab );
		}
		map.clear();
		for(DataDownloadTaskConfig tc:dataDownloadTaskConfigs)
		{
			logger.info("正在验证的数据下载任务:{}......", tc.getName());
			Assert.isTrue(map.get(tc.getName() ) == null, String.format("所配置的数据下载任务名%s重复！", tc.getName()));
			map.put(tc.getName(),tc);
			mapTab.clear();
			for(TableConfig tabc:tc.getTableConfigs())
			{
				Assert.isTrue(mapTab.get(tabc.getTableName()) ==null,
						String.format("所配置的数据下载任务名%s的表名%s有重复！", tc.getName(),tabc.getTableName()));
				mapTab.put(tabc.getTableName(), tabc);
			}
			for(TableConfig tabc:tc.getTableConfigs())
				checkTableConfig(dbMeta, tabc ,mapTab );
		}
		for(TaskConfig tc:transferTaskConfigs)
		{
			
		}
		Map<String,SiteFileCompareConfig> mapsitef = new HashMap<>();
		for(SiteFileCompareConfig tc:siteFileCompareConfigs)
		{
			logger.info("正在验证的站点文件同步任务:{}......", tc.getName());
			Assert.isTrue(mapsitef.get(tc.getName() ) == null, String.format("所配置的站点文件同步任务名%s重复！", tc.getName()));
			mapsitef.put(tc.getName(),tc);
			Map<String ,FileCompareConfig> mapFcc = new HashMap<>();
			for(FileCompareConfig fcc:tc.getConfigs())
			{
				Assert.hasText(fcc.getKey(),"文件比较点不能为空!");
				Assert.hasText(fcc.getBasePath(),"文件比较点的根目录不能为空!");
				Assert.isTrue(mapFcc.get(fcc.getKey() ) == null, String.format("文件比较点名称%s重复！", fcc.getKey()));
				
				File f = new File(fcc.getBasePath());
				Assert.isTrue(f.exists() && f.isDirectory(),String.format("站点文件同步的根目录%s不存在！",
						fcc.getBasePath()));
				
				mapFcc.put(fcc.getKey(),fcc);
			}
		}
	}
	private void checkTableConfig(DbMetaInfo dbMeta ,TableConfig tc,Map<String,TableConfig> map)
	{
		TableMeta tm = dbMeta.get(tc.getTableName());
		Assert.isTrue(tm != null, String.format("所配置的表名不存在:%s", tc.getTableName()));
		
		String [] ss = tc.getPkNames().split(",");
		for(String s:ss)
		{
			Assert.isTrue(tm.get(s)!= null, String.format("所配置的表%s的字段不存在:%s", tc.getTableName(),s));
		}
		if(StringUtils.hasText(tc.getCompareFieldNames()))
		{
			ss = tc.getCompareFieldNames().split(",");
			for(String s:ss)
			{
				Assert.isTrue(tm.get(s)!= null, String.format("所配置的表%s的字段不存在:%s", tc.getTableName(),s));
			}
		}		
		Assert.isTrue(tm.get(tc.getVersionFieldName())!= null, String.format("所配置的表%s的字段%s不存在!", tc.getTableName(),tc.getVersionFieldName()));

		Assert.isTrue(tm.get(tc.getLastUpdateFieldName())!= null, String.format("所配置的表%s的字段%s不存在!", tc.getTableName(),tc.getLastUpdateFieldName()));
		
		if(StringUtils.hasText(tc.getSiteIdFieldName()))
		{
			Assert.isTrue(tm.get(tc.getSiteIdFieldName())!= null, String.format("所配置的表%s的字段%s不存在!", tc.getTableName(),tc.getSiteIdFieldName()));
		}		
		
		if(StringUtils.hasText(tc.getParentTableName()))
		{
			Assert.isTrue(tm.get(tc.getFkName() )!= null, String.format("所配置的表%s的字段不存在:%s", tc.getTableName(),tc.getFkName()));
			
			Assert.isTrue(map.get(tc.getParentTableName())!= null,String.format("所配置的表%s的外关联表%s未在表配置项中!", tc.getTableName(),tc.getParentTableName()));
			
			TableMeta tmp = dbMeta.get(tc.getParentTableName());
			Assert.isTrue(tmp != null, String.format("所配置的表%s的外关联表%s不存在于数据库中!", tc.getTableName(),tc.getParentTableName()));
			Assert.isTrue(tmp.get(tc.getParentId()) != null, String.format("所配置的表%s的外关联表%s的字段%s不存在于数据库中!", tc.getTableName(),tc.getParentTableName(),tc.getParentId()));
		}
	}
	
	public Map<String,SyncTaskConfig> getSyncTaskConfigsMap()
	{
		if(syncTaskConfigsMap == null && !CollectionUtils.isEmpty( syncTaskConfigs))
		{
			syncTaskConfigsMap = new HashMap<>();
			syncTaskConfigs.forEach(config->{
				syncTaskConfigsMap.put(config.getName(),config);
			});
		}
		return syncTaskConfigsMap;
	}
	public SyncTaskConfig getSyncTaskConfig(String name)
	{
		Map<String,SyncTaskConfig> map = getSyncTaskConfigsMap();
		if(map == null)
			return null;
		return map.get(name);
	}
	
	public Map<String,DataUploadTaskConfig> getDataUploadTaskConfigsMap()
	{
		if(dataUploadTaskConfigsMap == null && !CollectionUtils.isEmpty( dataUploadTaskConfigs))
		{
			dataUploadTaskConfigsMap = new HashMap<>();
			dataUploadTaskConfigs.forEach(config->{
				dataUploadTaskConfigsMap.put(config.getName(),config);
			});
		}
		return dataUploadTaskConfigsMap;
	}
	public DataUploadTaskConfig getDataUploadTaskConfig(String name)
	{
		Map<String,DataUploadTaskConfig> map = getDataUploadTaskConfigsMap();
		if(map == null)
			return null;
		return map.get(name);
	}
	
	public Map<String,DataDownloadTaskConfig> getDataDownloadTaskConfigsMap()
	{
		if(dataDownloadTaskConfigsMap == null && !CollectionUtils.isEmpty( dataDownloadTaskConfigs))
		{
			dataDownloadTaskConfigsMap = new HashMap<>();
			dataDownloadTaskConfigs.forEach(config->{
				dataDownloadTaskConfigsMap.put(config.getName(),config);
			});
		}
		return dataDownloadTaskConfigsMap;
	}
	public DataDownloadTaskConfig getDataDownloadTaskConfig(String name)
	{
		Map<String,DataDownloadTaskConfig> map = getDataDownloadTaskConfigsMap();
		if(map == null)
			return null;
		return map.get(name);
	}	
	
	public Map<String,FileDownloadTaskConfig> getFileDownloadTaskConfigsMap()
	{
		if(fileDownloadTaskConfigsMap == null && !CollectionUtils.isEmpty( fileDownloadTaskConfigs))
		{
			fileDownloadTaskConfigsMap = new HashMap<>();
			fileDownloadTaskConfigs.forEach(config->{
				fileDownloadTaskConfigsMap.put(config.getName(),config);
			});
		}
		return fileDownloadTaskConfigsMap;
	}
	public FileDownloadTaskConfig getFileDownloadTaskConfig(String name)
	{
		Map<String,FileDownloadTaskConfig> map = getFileDownloadTaskConfigsMap();
		if(map == null)
			return null;
		return map.get(name);
	}	
	
	public Map<String,FileUploadTaskConfig> getFileUploadTaskConfigsMap()
	{
		if(fileUploadTaskConfigsMap == null && !CollectionUtils.isEmpty( fileUploadTaskConfigs))
		{
			fileUploadTaskConfigsMap = new HashMap<>();
			fileUploadTaskConfigs.forEach(config->{
				fileUploadTaskConfigsMap.put(config.getName(),config);
			});
		}
		return fileUploadTaskConfigsMap;
	}
	public FileUploadTaskConfig getFileUploadTaskConfig(String name)
	{
		Map<String,FileUploadTaskConfig> map = getFileUploadTaskConfigsMap();
		if(map == null)
			return null;
		return map.get(name);
	}	
	
	public Map<String,TransferTaskConfig> getTransferTaskConfigsMap()
	{
		if(transferTaskConfigsMap == null && !CollectionUtils.isEmpty( transferTaskConfigs))
		{
			transferTaskConfigsMap = new HashMap<>();
			transferTaskConfigs.forEach(config->{
				transferTaskConfigsMap.put(config.getName(),config);
			});
		}
		return transferTaskConfigsMap;
	}
	public TransferTaskConfig getTransferTaskConfig(String name)
	{
		Map<String,TransferTaskConfig> map = getTransferTaskConfigsMap();
		if(map == null)
			return null;
		return map.get(name);
	}
	
	
	public Map<String,SiteFileCompareConfig> getSiteFileCompareConfigMap()
	{
		if(siteFileCompareConfigMap == null && !CollectionUtils.isEmpty( siteFileCompareConfigs))
		{
			siteFileCompareConfigMap = new HashMap<>();
			siteFileCompareConfigs.forEach(config->{
				siteFileCompareConfigMap.put(config.getName(),config);
			});
		}
		return siteFileCompareConfigMap;
	}
	
	public SiteFileCompareConfig getSiteFileCompareConfig(String name)
	{
		Map<String,SiteFileCompareConfig> map = getSiteFileCompareConfigMap();
		if(map == null)
			return null;
		return map.get(name);
	}
	
	
	public List<SyncTaskConfig> getSyncTaskConfigs() {
		return syncTaskConfigs;
	}

	public void setSyncTaskConfigs(List<SyncTaskConfig> syncTaskConfigs) {
		this.syncTaskConfigs = syncTaskConfigs;
	}

	public List<TransferTaskConfig> getTransferTaskConfigs() {
		return transferTaskConfigs;
	}

	public void setTransferTaskConfigs(List<TransferTaskConfig> transferTaskConfigs) {
		this.transferTaskConfigs = transferTaskConfigs;
	}

	public List<FileDownloadTaskConfig> getFileDownloadTaskConfigs() {
		return fileDownloadTaskConfigs;
	}

	public void setFileDownloadTaskConfigs(List<FileDownloadTaskConfig> fileDownloadTaskConfigs) {
		this.fileDownloadTaskConfigs = fileDownloadTaskConfigs;
	}

	public List<DataUploadTaskConfig> getDataUploadTaskConfigs() {
		return dataUploadTaskConfigs;
	}

	public void setDataUploadTaskConfigs(List<DataUploadTaskConfig> dataUploadTaskConfigs) {
		this.dataUploadTaskConfigs = dataUploadTaskConfigs;
	}

	public List<DataDownloadTaskConfig> getDataDownloadTaskConfigs() {
		return dataDownloadTaskConfigs;
	}

	public void setDataDownloadTaskConfigs(List<DataDownloadTaskConfig> dataDownloadTaskConfigs) {
		this.dataDownloadTaskConfigs = dataDownloadTaskConfigs;
	}
	
	public String getFileDownloadPath() {
		return fileDownloadPath;
	}

	public void setFileDownloadPath(String fileDownloadPath) {
		this.fileDownloadPath = fileDownloadPath;
	}
	public List<FileUploadTaskConfig> getFileUploadTaskConfigs() {
		return fileUploadTaskConfigs;
	}
	public void setFileUploadTaskConfigs(List<FileUploadTaskConfig> fileUploadTaskConfigs) {
		this.fileUploadTaskConfigs = fileUploadTaskConfigs;
	}	
	public void setFileUploadTaskConfigsMap(Map<String,FileUploadTaskConfig> fileUploadTaskConfigsMap) {
		this.fileUploadTaskConfigsMap = fileUploadTaskConfigsMap;
	}
	public List<SiteFileCompareConfig> getSiteFileCompareConfigs() {
		return siteFileCompareConfigs;
	}
	public void setSiteFileCompareConfigs(List<SiteFileCompareConfig> siteFileCompareConfigs) {
		this.siteFileCompareConfigs = siteFileCompareConfigs;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
