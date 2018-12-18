package gyqx.txp.configuration;

public class TableRelation 
{
	private String tableName;
	private String fieldName;
	
	private String relativeTableName;
	private String relativefieldName;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getRelativeTableName() {
		return relativeTableName;
	}
	public void setRelativeTableName(String relativeTableName) {
		this.relativeTableName = relativeTableName;
	}
	public String getRelativefieldName() {
		return relativefieldName;
	}
	public void setRelativefieldName(String relativefieldName) {
		this.relativefieldName = relativefieldName;
	}

}
