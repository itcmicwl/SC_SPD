package gyqx.txp.message;

import java.util.Date;

public class TxpMessage 
{
	private MessageBodyKind kind;
	private String idempotentValue;	
	private int dataSyncVersion;
	private Date dataSyncDatetime;
	private boolean directCmd = false;
	private String fromId;
	private String toId;
	private String taskName;
	private String catalog;
	private boolean hasMoreData = false;
	private boolean hasError = false;
	private String description;
	private String script = null;
	private String scriptParam = null;
	private String termimalSession = null;
	private Object body;

	public String getIdempotentValue() {
		return idempotentValue;
	}

	public void setIdempotentValue(String idempotentValue) {
		this.idempotentValue = idempotentValue;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public MessageBodyKind getKind() {
		return kind;
	}

	public void setKind(MessageBodyKind kind) {
		this.kind = kind;
	}

	public boolean isHasMoreData() {
		return hasMoreData;
	}

	public void setHasMoreData(boolean hasMoreData) {
		this.hasMoreData = hasMoreData;
	}	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDataSyncVersion() {
		return dataSyncVersion;
	}

	public void setDataSyncVersion(int dataSyncVersion) {
		this.dataSyncVersion = dataSyncVersion;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public Date getDataSyncDatetime() {
		return dataSyncDatetime;
	}

	public void setDataSyncDatetime(Date dataSyncDatetime) {
		this.dataSyncDatetime = dataSyncDatetime;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}	
	
	@Override
	public String toString() {
		return "TxpMessage [kind=" + kind + ", dataSyncVersion=" + dataSyncVersion + ", dataSyncDatetime="
				+ dataSyncDatetime + ", fromId=" + fromId + ", toId=" + toId + ", taskName=" + taskName + ", catalog="
				+ catalog + ", hasMoreData=" + hasMoreData + ", hasError=" + hasError + ", description=" + description
				+ "]";
	}

	public boolean isDirectCmd() {
		return directCmd;
	}

	public void setDirectCmd(boolean directCmd) {
		this.directCmd = directCmd;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getScriptParam() {
		return scriptParam;
	}

	public void setScriptParam(String scriptParam) {
		this.scriptParam = scriptParam;
	}

	public String getTermimalSession() {
		return termimalSession;
	}

	public void setTermimalSession(String termimalSession) {
		this.termimalSession = termimalSession;
	}
}
