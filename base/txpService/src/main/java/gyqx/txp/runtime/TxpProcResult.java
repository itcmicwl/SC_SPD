package gyqx.txp.runtime;

import gyqx.txp.message.TxpMessage;

/*
 * 数据处理结果描述
 */
public class TxpProcResult 
{
	private String taskName;
	private String catalog;
	/*
	 * 期望后续处理的方法
	 */
	private String eagerMethod;
	/*
	 * 是否还要继续处理
	 */
	private boolean hasMore;
	/*
	 * 需要跳转的URI
	 */
	private String eagerNextUri;
	private int code;
	private String errorMessage;
	
	private TxpMessage txpMessage;
	
	public String getEagerMethod() {
		return eagerMethod;
	}
	public void setEagerMethod(String eagerMethod) {
		this.eagerMethod = eagerMethod;
	}
	public boolean isHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	public String getEagerNextUri() {
		return eagerNextUri;
	}
	public void setEagerNextUri(String eagerNextUri) {
		this.eagerNextUri = eagerNextUri;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public TxpMessage getTxpMessage() {
		return txpMessage;
	}
	public void setTxpMessage(TxpMessage txpMessage) {
		this.txpMessage = txpMessage;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	

}
