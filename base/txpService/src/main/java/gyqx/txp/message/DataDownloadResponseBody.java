package gyqx.txp.message;

import java.util.Map;

import gyqx.txp.data.RecordSet;

public class DataDownloadResponseBody implements java.io.Serializable
{
	private Map<String,Object> propertySet;
	
	private RecordSet recordSet;

	public RecordSet getRecordSet() {
		return recordSet;
	}

	public void setRecordSet(RecordSet recordSet) {
		this.recordSet = recordSet;
	}

	public Map<String,Object> getPropertySet() {
		return propertySet;
	}

	public void setPropertySet(Map<String,Object> propertySet) {
		this.propertySet = propertySet;
	}

}
