package gyqx.txp.message;

import java.util.Map;

public class DataUploadResponseBody implements java.io.Serializable
{
	private Map<String,Object> propertySet;

	public Map<String,Object> getPropertySet() {
		return propertySet;
	}

	public void setPropertySet(Map<String,Object> propertySet) {
		this.propertySet = propertySet;
	}
}
