package gyqx.txp.message;

import java.util.Map;

public class CustomRecords
{	
	/**
	 * 操作方式  
	 * update,insert ,delete, deleteByIdAndInsert
	 */
	private String opName;
	
	private String idempotentExpress;
	
	private String keyNames;	
	
	private Map<String,Object> records;	

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Map<String,Object> getRecords() {
		return records;
	}

	public void setRecords(Map<String,Object> records) {
		this.records = records;
	}	

	public String getKeyNames() {
		return keyNames;
	}

	public void setKeyNames(String keyNames) {
		this.keyNames = keyNames;
	}

	public String getIdempotentExpress() {
		return idempotentExpress;
	}

	public void setIdempotentExpress(String idempotentExpress) {
		this.idempotentExpress = idempotentExpress;
	}

}
