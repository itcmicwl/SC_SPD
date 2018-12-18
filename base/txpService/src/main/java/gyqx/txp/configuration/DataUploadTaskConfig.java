package gyqx.txp.configuration;

import java.util.List;


public class DataUploadTaskConfig extends TaskConfig
{
	public static final String catalog_code = "dataUpload";
	
	private String paramsBindingExpression;	
	private List<TableConfig> tableConfigs;
	private String loadDataExpression;
	private String processDataExpression;
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
	public String getParamsBindingExpression() {
		return paramsBindingExpression;
	}
	public void setParamsBindingExpression(String paramsBindingExpression) {
		this.paramsBindingExpression = paramsBindingExpression;
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
	

}
