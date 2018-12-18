package gyqx.txp.db.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class DbMetaInfo 
{
	protected List<TableMeta> tables = new ArrayList<TableMeta>();
	protected HashMap<String ,TableMeta> map = new HashMap<String ,TableMeta>();
	public List<TableMeta> getTables() {
		return tables;
	}
	public void setTables(List<TableMeta> tables) {
		this.tables = tables;
	}
	public HashMap<String, TableMeta> getMap() {
		return map;
	}
	public void setMap(HashMap<String, TableMeta> map) {
		this.map = map;
	}
	public TableMeta get(String name)
	{
		return map.get(name.toLowerCase().replace("_",""));
	}
	
	public void appendTables( List<TableMeta> tabs)
	{
		for(TableMeta tab:tabs)
		{
			String tabname = tab.getName().toLowerCase().replace("_","");
			if(map.get(tabname) == null)
			{
				tables.add(tab);
				map.put(tabname, tab);
				tab.refreshMap();
			}
		}
	}
	public static String toCamelName(String name,boolean firstUpper)
	{
		name = name.toLowerCase();
		if(name.indexOf('_')< 0)//error when s is camelName 
		{
			if(firstUpper)
			{
				char[] chrs = name.toCharArray();
				if(chrs[0] >='a' && chrs[0] <='z')// to upper
					chrs[0] -= 32;
				name = new String(chrs);
			}
			return name;
		}
		String[] ss = name.split("_");
		String fieldName="";

		for(int i = 0; i< ss.length;i++){
			
			char[] chrs = ss[i].toCharArray();
			if(chrs.length == 0)continue;
			
			if(firstUpper)
			{
				if(chrs[0] >='a' && chrs[0] <='z')// to upper
					chrs[0] -= 32;
			}else{
				if(i >0 && chrs[0] >='a' && chrs[0] <='z') // to upper
					chrs[0] -= 32;
				if(i ==0 && chrs[0] >='A' && chrs[0] <='Z')// first char not to upper
					chrs[0] += 32;
			}
			fieldName +=new String(chrs);
		}
		return fieldName;
	}
	public static String firstCharToUpper(String s)
	{
		String sf = s.substring(0, 1);
		char a = sf.charAt(0);
		if(a>='a' && a <='z')
			sf = sf.toUpperCase();
		else
			return s;
		
		return sf + s.substring(1);
	}

	public abstract String queryBeanType(String dbType,boolean enableNull);
	

}
