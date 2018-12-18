package gyqx.txp.db.meta;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class FieldMeta 
{
	private TableMeta table;
	private String name;
	private String cname;
	private String type; //	
	private String remark;	
	private int ordinal;
	private int length;
	private int scale;
	private String javaType;
	private String camelName;
	private String lowerCamelName;
	private boolean pk;
	private boolean enableNull;
	private String defaultValue;
	private String foreignTableName;
	private String foreignField;
	private String dictDesc;
	private List<String> dicts;
	private String validateDesc;
	private List<String> validates;
	private String safeDbFieldName;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getForeignTableName() {
		return foreignTableName;
	}
	public void setForeignTableName(String foreignTableName) {
		this.foreignTableName = foreignTableName;
	}
	public String getForeignField() {
		return foreignField;
	}
	public void setForeignField(String foreignField) {
		this.foreignField = foreignField;
	}
	public boolean isPk() {
		return pk;
	}
	public void setPk(boolean pk) {
		this.pk = pk;
	}
	public boolean isEnableNull() {
		return enableNull;
	}
	public void setEnableNull(boolean enableNull) {
		this.enableNull = enableNull;
	}	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}	
	
	public TableMeta getTable() {
		return table;
	}
	public void setTable(TableMeta table) {
		this.table = table;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(int orderIndex) {
		this.ordinal = orderIndex;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getCamelName() {
		return camelName;
	}
	public void setCamelName(String camelName) {
		this.camelName = camelName;
		this.lowerCamelName = StringUtils.uncapitalize(camelName);
	}
	public String getDictDesc() {
		return dictDesc;
	}
	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}
	public String getValidateDesc() {
		return validateDesc;
	}
	public void setValidateDesc(String validateDesc) {
		this.validateDesc = validateDesc;
	}
	public List<String> getDicts() {
		return dicts;
	}
	public void setDicts(List<String> dicts) {
		this.dicts = dicts;
	}
	
	@Override
	public String toString() {
		return "FieldMeta [table=" + table + ", name=" + name + ", cname=" + cname + ", type=" + type + ", comment="
				+ remark + ", orderIndex=" + ordinal + ", length=" + length + ", scale=" + scale + ", javaType="
				+ javaType + ", camelName=" + camelName + ", pk=" + pk + ", enableNull=" + enableNull + ", fkTableName="
				+ foreignTableName + ", fkFieldName=" + foreignField + "]";
	}
	
	public boolean isFk()
	{
		return foreignTableName != null && foreignTableName.trim().length() > 0;
	}
	
	public static String toCamelName(String name)
	{
		if(name.indexOf('_')< 0)//error when s is camelName 
			return name;
		String[] ss = name.toLowerCase().split("_");
		StringBuilder fieldName = new StringBuilder();

		for(int i = 0; i< ss.length;i++){
			
			char[] chrs = ss[i].toCharArray();
			if(chrs.length == 0) continue;
			if(i >0 && chrs[0] >='a' && chrs[0] <='z')// first char not to upper
				chrs[0] -= 32;
			fieldName.append(chrs);
		}
		return fieldName.toString();
	}
	public static String toUpperCamelName(String name)
	{
		if(name.indexOf('_')< 0)//error when s is camelName 
		{
			char[] chrs = name.toCharArray();
			if( chrs[0] >='a' && chrs[0] <='z')
				chrs[0] -= 32;
			return new String(chrs);
		}
			
		String[] ss = name.toLowerCase().split("_");
		StringBuilder fieldName = new StringBuilder();

		for(int i = 0; i< ss.length;i++){
			
			char[] chrs = ss[i].toCharArray();
			if(chrs.length == 0) continue;
			if( chrs[0] >='a' && chrs[0] <='z')
				chrs[0] -= 32;
			fieldName.append(chrs);
		}
		return fieldName.toString();
	}
	
	public static String toDbName(String camelName)
	{
		char[] chrs = camelName.toCharArray();
		StringBuilder fieldName = new StringBuilder();
		
		for(int i = 0; i< chrs.length;i++)
		{
			if(chrs[i] >='A' && chrs[i] <='Z'){
				if(i>0)
					fieldName.append("_");
				chrs[i] += 32;
				fieldName.append(chrs[i]);
				continue;
			}
			fieldName.append(chrs[i]);
				
		}
		return fieldName.toString();
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public List<String> getValidates() {
		return validates;
	}
	public void setValidates(List<String> validates) {
		this.validates = validates;
	}
	public String getLowerCamelName() {
		return lowerCamelName;
	}
	public void setLowerCamelName(String lowerCamelName) {
		this.lowerCamelName = lowerCamelName;
	}
	public String getSafeDbFieldName() {
		return safeDbFieldName;
	}
	public void setSafeDbFieldName(String safeDbFieldName) {
		this.safeDbFieldName = safeDbFieldName;
	}
	

}
