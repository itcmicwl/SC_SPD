package gyqx.txp.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncTaskConfig extends TaskConfig
{
	public static final String catalog_code = "syncData";
	private List<TableConfig> tableConfigs;
	private List<String> relations;
	private List<TableRelation> tabRels;
	
	private transient Map<String ,TableConfig> map = null;
	
	public List<TableRelation> getRelationsMap()
	{
		if(tabRels != null)
			return tabRels;
		
		tabRels = new ArrayList<>();
		
		for(String sr: relations)
		{
			String[] stfs = sr.split("=");
			String[] mtfs = stfs[0].split("\\.");
			String[] dtfs = stfs[1].split("\\.");
			TableRelation trel = new TableRelation();
			
			trel.setTableName(mtfs[0].trim());
			trel.setFieldName(mtfs[1].trim());
			trel.setRelativeTableName(dtfs[0].trim());
			trel.setRelativefieldName(dtfs[1].trim());
			
			tabRels.add(trel);			
		}
		
		return tabRels;
		
	}
	
	public List<TableRelation> getRootTableRelations()
	{
		List<TableRelation> rels = getRelationsMap();
		
		List<TableRelation> relRoots = new ArrayList<>();
		
		for(TableRelation r :  rels)
		{
			boolean b = false;
			for(TableRelation r0 :  rels)
			{
				if(r.getTableName().equals(r0.getRelativeTableName()))
				{
					b = true;
					break;
				}
			}
			relRoots.add(r);
		}
		return relRoots;
	}
	
	public List<TableRelation> getRelativeTableRelations(String tableName)
	{
		List<TableRelation> rels = getRelationsMap();
		List<TableRelation> relativeTRs = new ArrayList<>();
		
		for(TableRelation r :  rels)
		{
			if(r.getRelativeTableName().equalsIgnoreCase(tableName))
			{
				relativeTRs.add(r);
			}
		}
		return relativeTRs;
	}
	
	public List<TableRelation> getMainTableRelations(String tableName)
	{
		List<TableRelation> rels = getRelationsMap();
		List<TableRelation> tRs = new ArrayList<>();
		
		for(TableRelation r :  rels)
		{
			if(r.getTableName().equalsIgnoreCase(tableName))
			{
				tRs.add(r);
			}
		}
		return tRs;
	}
	
	
	public synchronized TableConfig getTableConfig(String tableName)
	{
		if(map == null){
			map = new HashMap<>();
			for(TableConfig cfg:tableConfigs)
			{
				map.put(cfg.getTableName().toLowerCase(),cfg);
			}
		}
		return map.get(tableName.toLowerCase());		
	}
	public List<String> getRelations() {
		return relations;
	}

	public void setRelations(List<String> relations) {
		this.relations = relations;
	}

	public List<TableConfig> getTableConfigs() {
		return tableConfigs;
	}

	public void setTableConfigs(List<TableConfig> tableConfigs) {
		this.tableConfigs = tableConfigs;
	}

	@Override
	public void init() {
		for(TableConfig tc: tableConfigs)
		{
			TableConfig.setDefaultValues(tc);
		}
		
	}


}
