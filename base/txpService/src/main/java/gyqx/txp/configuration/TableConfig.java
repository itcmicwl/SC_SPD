package gyqx.txp.configuration;

import org.springframework.util.StringUtils;

public class TableConfig
{
	public static final String default_pknames="id";
	public static final String default_parent_id="id";
	public static final String default_fk_pid="pid";
	public static final String default_last_update_fieldname="last_update_datetime";
	public static final String default_compare_field_names="id,version";
	public static final String default_version_field_name="version";
	
	public static final TableConfig setDefaultValues(TableConfig cfg)
	{
		if(!StringUtils.hasText(cfg.getPkNames()))
		{
			cfg.pkNames = default_pknames;
		}
		if(!StringUtils.hasText(cfg.lastUpdateFieldName))
		{
			cfg.lastUpdateFieldName = default_last_update_fieldname;
		}
		if(StringUtils.hasText(cfg.parentTableName) && !StringUtils.hasText(cfg.parentId))
		{
			cfg.parentId = default_parent_id;			
		}
		if(StringUtils.hasText(cfg.parentTableName) && !StringUtils.hasText(cfg.fkName))
		{
			cfg.fkName = default_fk_pid;
		}
		if(!StringUtils.hasText(cfg.compareFieldNames) )
		{
			cfg.compareFieldNames = default_compare_field_names;
		}
		if(!StringUtils.hasText(cfg.versionFieldName) )
		{
			cfg.versionFieldName = default_version_field_name;
		}
		return cfg;
	}
	
	private String tableName;
	private String pkNames;
	private String versionFieldName;
	private String lastUpdateFieldName;
	private String compareFieldNames;
	private String compareExpression;
	private String loadDataExpression;
	
	private String siteIdFieldName;
	private String parentId;
	private String parentTableName;
	private String fkName;
	
	private String loadCompareDataExpression;
	
	private String insertExpression;
	private String updateExpression;
	private String deleteExpression;
	
	private String onFinishExpression;
	
	public String getOnFinishExpression() {
		return onFinishExpression;
	}
	public void setOnFinishExpression(String onFinishExpression) {
		this.onFinishExpression = onFinishExpression;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String name) {
		this.tableName = name;
	}
	public String getPkNames() {
		return pkNames;
	}
	public void setPkNames(String pkNames) {
		this.pkNames = pkNames;
	}
	public String getCompareFieldNames() {
		return compareFieldNames;
	}
	public void setCompareFieldNames(String compareFieldNames) {
		this.compareFieldNames = compareFieldNames;
	}
	
	public String getInsertExpression() {
		return insertExpression;
	}
	public void setInsertExpression(String insertExpression) {
		this.insertExpression = insertExpression;
	}
	public String getUpdateExpression() {
		return updateExpression;
	}
	public void setUpdateExpression(String updateExpression) {
		this.updateExpression = updateExpression;
	}
	public String getDeleteExpression() {
		return deleteExpression;
	}
	public void setDeleteExpression(String deleteExpression) {
		this.deleteExpression = deleteExpression;
	}
	public String getCompareExpression() {
		return compareExpression;
	}
	public void setCompareExpression(String compareExpression) {
		this.compareExpression = compareExpression;
	}
	
	public String getLoadCompareDataExpression() {
		return loadCompareDataExpression;
	}
	public void setLoadCompareDataExpression(String loadCompareDataExpression) {
		this.loadCompareDataExpression = loadCompareDataExpression;
	}
	public String getLastUpdateFieldName() {
		return lastUpdateFieldName;
	}
	public void setLastUpdateFieldName(String lastModifyFieldName) {
		this.lastUpdateFieldName = lastModifyFieldName;
	}
	public String getLoadDataExpression() {
		return loadDataExpression;
	}
	public void setLoadDataExpression(String loadDataExpression) {
		this.loadDataExpression = loadDataExpression;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentTableName() {
		return parentTableName;
	}
	public void setParentTableName(String parentTableName) {
		this.parentTableName = parentTableName;
	}
	public String getVersionFieldName() {
		return versionFieldName;
	}
	public void setVersionFieldName(String versionFieldName) {
		this.versionFieldName = versionFieldName;
	}
	public String getSiteIdFieldName() {
		return siteIdFieldName;
	}
	public void setSiteIdFieldName(String siteIdFieldName) {
		this.siteIdFieldName = siteIdFieldName;
	}
	public String getFkName() {
		return fkName;
	}
	public void setFkName(String fkName) {
		this.fkName = fkName;
	}
	

}
