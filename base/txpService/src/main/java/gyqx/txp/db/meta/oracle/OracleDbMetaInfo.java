package gyqx.txp.db.meta.oracle;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import gyqx.txp.db.meta.DbMetaInfo;
import gyqx.txp.db.meta.FieldMeta;
import gyqx.txp.db.meta.TableMeta;

public class OracleDbMetaInfo extends DbMetaInfo
{
	
	public OracleDbMetaInfo(DataSource ds)
	{		
		JdbcTemplate jt = new JdbcTemplate(ds);
		
		List<Map<String,Object>> dtTableSchema = jt.queryForList("select ut.TABLE_NAME,utc.COMMENTS as TABLE_COMMENT from user_tables ut left join user_tab_comments utc on ut.TABLE_NAME = utc.TABLE_NAME order by TABLE_NAME");

		for(Map<String,Object> row:dtTableSchema)
		{
			TableMeta tm = new TableMeta();
			tm.setName(row.get("TABLE_NAME").toString());
			tm.setRemark(toString(row.get("TABLE_COMMENT")));
			tm.setCamelName(toCamelName(tm.getName(),true));
			tables.add(tm);
			map.put(tm.getName().toLowerCase().replace("_", ""), tm);
		}
		Pattern pat1 = Pattern.compile("((.)+)(\\s+(.+))?");
		
		String sqlcols = "select "
				+ " col.TABLE_NAME,"
				+ " col.COLUMN_NAME,"
				+ " col.COLUMN_ID as pos,"
				+ " col.DATA_TYPE as dtype, "
				+ " col.CHAR_LENGTH as len,"
				+ " au.CONSTRAINT_TYPE as keyflag, "
				+ " col.DATA_PRECISION as prec, "
				+ " col.DATA_SCALE as scale, "
				+ " col.NULLABLE as nullflag, "
				+ " cc.COMMENTS as remark "
				+ " from user_tab_columns col left join user_col_comments cc  "
				+ "   on col.TABLE_NAME = cc.TABLE_NAME and col.COLUMN_NAME = cc.COLUMN_NAME   "
				+ "   left join user_cons_columns cu on col.TABLE_NAME = cu.TABLE_NAME and col.COLUMN_NAME = cu.COLUMN_NAME and cu.position is not null"
				+ "   left join user_constraints au on cu.constraint_name = au.CONSTRAINT_NAME"
				+ "  where au.CONSTRAINT_TYPE is null or au.CONSTRAINT_TYPE = 'P'  order by col.column_id";
		
		List<Map<String,Object>> dtTableFieldSchema = jt.queryForList(sqlcols);
		for(Map<String,Object> row:dtTableFieldSchema)
		{
			TableMeta tm = this.get(row.get("TABLE_NAME").toString());
			if(tm == null)
				continue;
			FieldMeta fm = new FieldMeta();
			fm.setName(row.get("COLUMN_NAME").toString());
			fm.setSafeDbFieldName("\"" + fm.getName() + "\"");
			fm.setTable(tm);
			fm.setRemark(toString(row.get("remark")));
			Object v = row.get("pos");
			fm.setOrdinal(Integer.parseInt(v.toString()));

			if(row.get("len")==null || Integer.parseInt(row.get("len").toString()) ==0)
			{
				fm.setLength(row.get("prec") ==null?0: Integer.parseInt(row.get("prec").toString()));
			}else{
				fm.setLength(Integer.parseInt(row.get("len").toString()));
			}
			fm.setPk("P".equals(row.get("keyflag")));
			
			fm.setEnableNull("Y".equals(row.get("nullflag")));
			
			if(row.get("scale")!= null &&  Integer.parseInt(row.get("scale").toString()) != 0)
			{
				fm.setScale(Integer.parseInt(row.get("scale").toString()));
			}
			
			fm.setCamelName(toCamelName(fm.getName(),false));
			
			String stype = row.get("dtype").toString();
			int lidx = stype.indexOf("(");
			if(lidx >=0)
				stype = stype.substring(0,lidx);
			
			if(stype.equalsIgnoreCase("number")){
				if(fm.getScale() ==0)
				{
					if(fm.getLength() <=12)
					{
						stype = "int";
					}else
						stype = "bigint";
				}
			}
			if(stype.equalsIgnoreCase("binary_double"))
				stype = "double";
			if(stype.equalsIgnoreCase("nclob"))
				stype = "clob";
			fm.setType(stype);	
			fm.setJavaType(queryBeanType(fm.getType(),fm.isEnableNull()));
			
			
			String remark = fm.getRemark() == null?"":fm.getRemark() ;
			Matcher mat1 = pat1.matcher(remark);
			if(mat1.find())
			{
				fm.setCname(mat1.group(1));
			}else{
				fm.setCname(fm.getName());
			}
			if(tm == null)
				System.out.println("tm == null");
			tm.append(fm);
		}
		
	}
	private String toString(Object v)
	{
		if(v == null)
			return null;
		return v.toString();
	}

	@Override
	public String queryBeanType(String dbType, boolean enableNull) {
		return dbTypeToJavaType(dbType,enableNull);
	}
	
	public static String dbTypeToJavaType(String dbType,boolean enableNull)
	{
		String result = "";
		if(dbType.equalsIgnoreCase("char") || dbType.equalsIgnoreCase("varchar") || dbType.equalsIgnoreCase("varchar2")
				|| dbType.equalsIgnoreCase("nvarchar2") || dbType.equalsIgnoreCase("nchar") || dbType.equalsIgnoreCase("nchar2") || dbType.equalsIgnoreCase("clob")|| dbType.equalsIgnoreCase("nclob"))
			result = "String";
		else if(dbType.equalsIgnoreCase("date") || dbType.equalsIgnoreCase("timestamp"))
			result = "java.util.Date";
		else if(dbType.equalsIgnoreCase("decimal") || dbType.equalsIgnoreCase("number"))
			result = "java.math.BigDecimal";
		else if(dbType.equalsIgnoreCase("int") )
			result = enableNull?"Integer":"int";
		else if(dbType.equalsIgnoreCase("bigint"))
			result = enableNull?"Long":"long";
		else if(dbType.equalsIgnoreCase("BINARY_double"))
			result = enableNull?"Double":"double";
		else if(dbType.equalsIgnoreCase("date"))
			result = "Date";
		else if(dbType.equalsIgnoreCase("blob") || dbType.equalsIgnoreCase("longblob"))
			result = "byte[]";
		else		
		throw new RuntimeException("unknown type!");
		return result;
	}

}
