package gyqx.txp.runtime.status;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class StatusLogItem 
{
	public abstract String getTag() ;
	public abstract void setTag(String tag) ;
	public abstract String key() ;
	
	public abstract void loadJson(JsonNode jnode);
}
