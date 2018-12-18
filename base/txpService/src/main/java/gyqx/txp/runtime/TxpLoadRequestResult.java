package gyqx.txp.runtime;

import java.util.List;

import gyqx.txp.message.TxpMessage;

public class TxpLoadRequestResult 
{
	private int code;
	private String errorMessage;	
	private List<TxpMessage> txpMessages;
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
	public List<TxpMessage> getTxpMessages() {
		return txpMessages;
	}
	public void setTxpMessages(List<TxpMessage> txpMessages) {
		this.txpMessages = txpMessages;
	}

}
