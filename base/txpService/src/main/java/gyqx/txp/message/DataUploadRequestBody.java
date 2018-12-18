package gyqx.txp.message;

import java.util.Map;

import gyqx.txp.data.RecordSet;

public class DataUploadRequestBody implements java.io.Serializable
{
	private RecordSet recordSet;
	private Map<String,Object> propertySet;

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
