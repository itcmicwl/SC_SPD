package gyqx.txp.db.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableMeta {

	private String name;
	private String cname;
	private String camelName;	
	private String remark;
	private String groupName;
	
	private List<FieldMeta> fields = new ArrayList<FieldMeta>();
	private HashMap<String ,FieldMeta> map = new HashMap<String ,FieldMeta>();
	private List<FieldMeta> pks = new ArrayList<FieldMeta>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<FieldMeta> getFields() {
		return fields;
	}
	public void setFields(List<FieldMeta> fields) {
		this.fields = fields;
	}
	public HashMap<String, FieldMeta> getMap() {
		return map;
	}
	public void setMap(HashMap<String, FieldMeta> map) {
		this.map = map;
	}
	
	public void append(FieldMeta fm)
	{
		fields.add(fm);
		
		map.put(fm.getName().toLowerCase().replace("_", ""),fm);
		
		if(fm.isPk())
			pks.add(fm);
	}
	
	public List<FieldMeta> getPks() {
		return pks;
	}
	public void setPks(List<FieldMeta> pks) {
		this.pks = pks;
	}
	public FieldMeta get(String name)
	{
		if(name == null)
			return null;
		return map.get(name.toLowerCase().replace("_",""));
	}
	public String getCamelName() {
		return camelName;
	}
	public void setCamelName(String camelName) {
		this.camelName = camelName;
	}
	
	public void refreshMap()
	{
		map.clear();
		for(FieldMeta fd:fields)
		{
			map.put(fd.getName().toLowerCase(),fd);
		}
	
	}
	
	@Override
	public String toString() {
		return "TableMeta [name=" + name + ", camelName=" + camelName + ", remark=" + remark + ", fields=" + fields
				+ ", map=" + map + ", pks=" + pks + "]";
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
