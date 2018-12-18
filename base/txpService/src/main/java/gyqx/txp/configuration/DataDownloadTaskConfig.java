package gyqx.txp.configuration;

import java.util.List;

public class DataDownloadTaskConfig extends TaskConfig
{
	public static final String catalog_code = "dataDownload";
	
	private boolean forSingleSite;
	
	private List<TableConfig> tableConfigs;
	
	private String paramsBindingExpression;
	
	private String loadDataExpression;
	
	private String processDataExpression;

	public String getParamsBindingExpression() {
		return paramsBindingExpression;
	}

	public void setParamsBindingExpression(String paramsBindingExpression) {
		this.paramsBindingExpression = paramsBindingExpression;
	}

	public String getLoadDataExpression() {
		return loadDataExpression;
	}

	public void setLoadDataExpression(String loadDataExpression) {
		this.loadDataExpression = loadDataExpression;
	}

	public String getProcessDataExpression() {
		return processDataExpression;
	}

	public void setProcessDataExpression(String processDataExpression) {
		this.processDataExpression = processDataExpression;
	}

	public List<TableConfig> getTableConfigs() {
		return tableConfigs;
	}

	public void setTableConfigs(List<TableConfig> tableConfigs) {
		this.tableConfigs = tableConfigs;
	}
	
	

	@Override
	public void init() {
		for(TableConfig tc: tableConfigs)
		{
			TableConfig.setDefaultValues(tc);
		}	
	}

	public boolean isForSingleSite() {
		return forSingleSite;
	}

	public void setForSingleSite(boolean forSingleSite) {
		this.forSingleSite = forSingleSite;
	}
	

}
