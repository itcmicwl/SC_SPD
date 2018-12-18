package gyqx.txp.message.txp;

import java.util.Date;
import java.util.Map;

public abstract class TxpNotify 
{
	private String cmd;
	private Map<String,Object> context;
	private Date createDate;
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public Map<String,Object> getContext() {
		return context;
	}
	public void setContext(Map<String,Object> context) {
		this.context = context;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public abstract boolean check(TxpNotify txp);

}
