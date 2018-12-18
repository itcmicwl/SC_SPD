package gyqx.txp.configuration;

public class TransferTaskConfig extends TaskConfig
{
	public static final String catalog_code = "transfer";
	
	private String paramsBindingExpression;
	private String beginTransferDataExpression;
	private String processTransferDataExpression;
	private String endTransferDataExpression;
	
	public String getBeginTransferDataExpression() {
		return beginTransferDataExpression;
	}
	public void setBeginTransferDataExpression(String beginTransferDataExpression) {
		this.beginTransferDataExpression = beginTransferDataExpression;
	}
	public String getProcessTransferDataExpression() {
		return processTransferDataExpression;
	}
	public void setProcessTransferDataExpression(String processTransferDataExpression) {
		this.processTransferDataExpression = processTransferDataExpression;
	}
	public String getEndTransferDataExpression() {
		return endTransferDataExpression;
	}
	public void setEndTransferDataExpression(String endTransferDataExpression) {
		this.endTransferDataExpression = endTransferDataExpression;
	}
	public String getParamsBindingExpression() {
		return paramsBindingExpression;
	}
	public void setParamsBindingExpression(String paramsBindingExpression) {
		this.paramsBindingExpression = paramsBindingExpression;
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	
}
