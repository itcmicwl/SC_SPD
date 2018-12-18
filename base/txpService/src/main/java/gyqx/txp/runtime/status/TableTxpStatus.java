package gyqx.txp.runtime.status;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;

public class TableTxpStatus extends StatusLogItem
{
	public static final String LOG_TAG = "tab" ;
	
	private String id;  
	
	private String tableName;
	private Map<String,Object> keyMap ;
	private Date lastSyncDateBegin;
	private Date lastSyncDateEnd;
	private String status;	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public Date getLastSyncDateBegin() {
		return lastSyncDateBegin;
	}
	public void setLastSyncDateBegin(Date lastSyncDateBegin) {
		this.lastSyncDateBegin = lastSyncDateBegin;
	}
	public Date getLastSyncDateEnd() {
		return lastSyncDateEnd;
	}
	public void setLastSyncDateEnd(Date lastSyncDateEnd) {
		this.lastSyncDateEnd = lastSyncDateEnd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getTag() {
		return LOG_TAG;
	}
	@Override
	public void setTag(String tag) {

	}
	
	@Override
	public void loadJson(JsonNode jnode) {
		tableName = jnode.get("tableName").asText();
		JsonNode jnodec = jnode.get("keyMap");
		keyMap = new HashMap<>();
		
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
			keyMap.put(name, val);			
		}		
		
		lastSyncDateBegin = new Date( jnode.get("lastSyncDateBegin").asLong());
		lastSyncDateEnd = new Date( jnode.get("lastSyncDateEnd").asLong());
		status = jnode.get("status").asText();
		key();
	}
	public void copyFrom(TableTxpStatus ns)
	{
		this.id = ns.key();
		this.tableName = ns.tableName;
		this.keyMap = ns.keyMap;
		this.lastSyncDateBegin = ns.lastSyncDateBegin;
		this.lastSyncDateEnd = ns.lastSyncDateEnd;
		this.status = ns.status;
	}
	public Map<String,Object> getKeyMap() {
		return keyMap;
	}
	public void setKeyMap(Map<String,Object> keyMap) {
		this.keyMap = keyMap;
	}
	@Override
	public String key() {
		if(StringUtils.hasText(id))
			return id;
		synchronized (this) {
			if(StringUtils.hasText(id))
				return id;
			StringBuilder sb = new StringBuilder();
			keyMap.forEach((k,v)->{
				sb.append(v);
			});
			id = sb.toString();			
		}
		
		return id;
	}

}
