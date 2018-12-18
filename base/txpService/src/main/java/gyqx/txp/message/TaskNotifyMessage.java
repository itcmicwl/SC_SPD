package gyqx.txp.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.util.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import gyqx.txp.runtime.status.StatusLogItem;

public class TaskNotifyMessage extends StatusLogItem
{
	public static final String LOG_TAG = "tnmsg" ;
	private String taskName;
	private String catalog="";
	private String version="";
	private String cmd="";
	private Map<String,Object> propertySet;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public Map<String, Object> getPropertySet() {
		return propertySet;
	}
	public void setPropertySet(Map<String, Object> propertySet) {
		this.propertySet = propertySet;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	private String id ;
	@Override
	public String getTag() {
		return LOG_TAG;//"";
	}
	@Override
	public void setTag(String tag) {

	}
	@Override
	public String key() {
		
		if(StringUtils.hasText(id))
			return id;
		synchronized (this) {
			if(StringUtils.hasText(id))
				return id;
			StringBuilder sb = new StringBuilder();
			sb.append(this.taskName).append(catalog).append(cmd);
			id = sb.toString();			
		}
	
		return id;
	}
	@Override
	public void loadJson(JsonNode jnode) {
		taskName = jnode.get("taskName").asText();
		catalog = jnode.get("catalog").asText();
		version = jnode.get("version").asText();
		cmd = jnode.get("cmd").asText();
		// propertySet
		JsonNode jnodec = jnode.get("propertySet");
		if(jnodec == null)
			return;
		
		propertySet = new HashMap<>();
		Iterator<Map.Entry<String, JsonNode>> nIter =  jnodec.fields();
		while(nIter.hasNext())
		{
			Map.Entry<String, JsonNode> kv =  nIter.next();
			
			String name = kv.getKey();
			JsonNode jnv = kv.getValue();
			
			Object val = null;
			if(jnv.isTextual())
			{
				val = jnv.asText();
			}else if(jnv.isNumber() )
			{
				String v = jnv.asText();
				if(v.indexOf(".") >=0)
					val = jnv.asDouble();
				else
					val = jnv.asLong();
			}
			propertySet.put(name, val);
		}		
	}	

	@Override
	public String toString() {
		return "TaskNotifyMessage [taskName=" + taskName + ", catalog=" + catalog + ", version=" + version + ", cmd="
				+ cmd + ", propertySet=" + propertySet + "]";
	}

}
