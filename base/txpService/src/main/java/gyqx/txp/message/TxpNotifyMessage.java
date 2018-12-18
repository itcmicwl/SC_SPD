package gyqx.txp.message;

import java.util.HashMap;
import java.util.Map;

public class TxpNotifyMessage extends TxpMessage
{	
	private Map<String,TaskNotifyMessage> messages = new HashMap<>();	
	
	public Map<String, TaskNotifyMessage> getMessages() {
		return messages;
	}
	public void setMessages(Map<String, TaskNotifyMessage> messages) {
		this.messages = messages;
	}	
	
}
