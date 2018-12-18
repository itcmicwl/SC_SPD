package codeBuilder;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ObjectUtils;

import com.itextpdf.text.log.SysoCounter;

import common.db.SimpleDao;
import common.db.dialect.mysql.MysqlDbMetaInfo;
import common.db.dialect.oracle.OracleDbMetaInfo;
import common.db.meta.DbMetaInfo;
import common.db.meta.FieldMeta;
import common.db.meta.TableMeta;
import tools.codeBuilder.parse.TableMetaBuilderFromExcel;
import tools.codeBuilder.parse.mysql.MysqlTableMetaBuilderFromExcel;
import tools.codeBuilder.parse.oracle.OracleTableMetaBuilderFromExcel;
import tools.toDb.TableBeanInfoUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit-ds.xml" })
public class TablesCompareTest 
{
	@Resource(name="dataSource")
	private DataSource dataSource;
	
	DbMetaInfo metainfo;
	private String databaseProductName;
	@Before
	public void setUp() throws Exception {
		try(Connection cnn= dataSource.getConnection()){
			DatabaseMetaData md = cnn.getMetaData();
			databaseProductName = md.getDatabaseProductName().toLowerCase();
		}
		if(databaseProductName.indexOf("mysql") >=0){
			databaseProductName = "mysql";
			metainfo = new MysqlDbMetaInfo(dataSource);
		}
		else{
			databaseProductName = "oracle";
			metainfo = new OracleDbMetaInfo(dataSource);
		}
	}
	@Test
	public void test() throws Exception
	{
		
		create(databaseProductName);
		checkTables(metainfo,tms);
		
		System.out.println("---------------sql: add column ---------------");
		System.out.println(sb.toString());
		
		System.out.println("---------------sql: modify column ---------------");
		System.out.println(sbm.toString());
	}
	List<TableMeta> tms = new ArrayList<>();
	public void create(String dbType) throws Exception
	{
		String docPath = "E:/wjy/Workspaces/documents/02-数据库文档/"; 
		
		String fileName = "平台表定义.xlsx";
		String packageName = "gyqx.platform.po";
		
		String sFilters = "";
		System.out.println("正在处理：" + fileName);
		outputSql_BeanVO_SaveTable(docPath,fileName,packageName,sFilters,dbType);		
		System.out.println("完成：" + fileName );
		
		fileName = "功能表定义.xlsx";
		packageName = "gyqx.platform.hdi";
		System.out.println("正在处理：" + fileName);
		outputSql_BeanVO_SaveTable(docPath,fileName,packageName,sFilters,dbType);
		System.out.println("完成：" + fileName );
		
		fileName = "内网功能表定义.xlsx";
		packageName = "gyqx.platform.herp";
		System.out.println("正在处理：" + fileName);
		outputSql_BeanVO_SaveTable(docPath,fileName,packageName,sFilters,dbType);
		
		System.out.println("完成：" + fileName );
	}
	private void outputSql_BeanVO_SaveTable(String docPath,String fileName,String packageName,String sFilters
			,String dbType) throws Exception
	{
		File newFile = new File(docPath + "copy/" + fileName);
		FileUtils.copyFile(new File(docPath + fileName), newFile);
		TableMetaBuilderFromExcel builder = new MysqlTableMetaBuilderFromExcel();
		
		if(dbType.equalsIgnoreCase("oracle"))
			builder = new OracleTableMetaBuilderFromExcel("spd");		
		
		System.out.println("database type:" + dbType);
		
		HashMap<String, List<TableMeta>> map = builder.getTableDescriptionsByExcelFile(newFile);
		
		
		for(Entry<String, List<TableMeta>> entry  :map.entrySet())
		{

			List<TableMeta> tables = entry.getValue();
			if(ObjectUtils.isEmpty( tables))
				continue;
			
			tables = filterTables(tables,sFilters);
			if(ObjectUtils.isEmpty( tables))
				continue;
			
			tms.addAll(tables);
		}
	}
	
	private void checkTables(DbMetaInfo mi,List<TableMeta> tmsDoc)
	{
		for(TableMeta tmdb : mi.getTables())
		{
			System.out.println(tmdb.getName());
			boolean bFind = false;
			for(TableMeta tm : tmsDoc)
			{
				if(tmdb.getName().equalsIgnoreCase(tm.getName()))
				{
					bFind = true;
					break;
				}
			}
			if(!bFind)
			{
				System.err.println("表 " + tmdb.getName() + "在设计文档中不存在!");
			}
		}
		
		for(TableMeta tmDoc : tmsDoc)
		{
			checkTableFields(mi,tmDoc);
		}
			
	}
	
	private StringBuilder sb = new StringBuilder();
	private StringBuilder sbm = new StringBuilder();
	
	private void checkTableFields(DbMetaInfo mi,TableMeta tmDoc)
	{
		TableMeta tmDb = mi.get(tmDoc.getName());
		
		if(tmDb == null)
		{
			System.err.println("表 " + tmDoc.getName() + "在数据库中不存在!");
			return;
		}
		for(FieldMeta fmDb: tmDb.getFields())
		{
			FieldMeta fmFind = null;
			for(FieldMeta fmDoc: tmDoc.getFields())
			{
				if(fmDb.getName().equalsIgnoreCase(fmDoc.getName()))
				{
					fmFind = fmDoc;
					break;
				}
			}
			if(fmFind == null)
			{
				System.err.println("表 " + tmDoc.getName() + "中 字段：" + fmDb.getName() + " 在文档中不存在 !");
			}else{
				if(!fmDb.getType().equalsIgnoreCase(fmFind.getType()) &&
						!fmDb.getJavaType().equalsIgnoreCase(fmFind.getJavaType()))
				{
					
					System.err.println(String.format("表%s, 字段%s 类型和文档中定义的不一样[数据库：%s|文档：%s] ",
							tmDoc.getName(),fmDb.getName(),fmDb.getJavaType(),fmFind.getJavaType()));
				}else if(fmDb.getLength() != fmFind.getLength()&& !fmFind.getType().equalsIgnoreCase("int"))
				{
					System.err.println(String.format("表%s, 字段%s 长度和文档中定义的不一样[数据库：%d|文档：%d] ",
							tmDoc.getName(),fmDb.getName(),fmDb.getLength(),fmFind.getLength()));
					if(fmFind.getType().equalsIgnoreCase("varchar") || fmFind.getType().equalsIgnoreCase("char") )
					{
						String sModify = "alter table %s  modify column %s %s(%d);\n";
						sbm.append(String.format(sModify, tmDb.getName(),fmDb.getName(), fmDb.getType(),fmFind.getLength()));
					}
					
				}
			}
		}

		for(FieldMeta fmDoc: tmDoc.getFields())
		{
			FieldMeta fmFind = null;
			for(FieldMeta fmDb: tmDb.getFields())
			{
				if(fmDb.getName().equalsIgnoreCase(fmDoc.getName()))
				{
					fmFind = fmDoc;
					break;
				}
			}
			if(fmFind == null)
			{
				System.err.println("表 " + tmDoc.getName() + "中 字段：" + fmDoc.getName() + " 在数据库中不存在 !");
				
//				if(fmDoc.getName().equalsIgnoreCase("last_update_datetime") || fmDoc.getName().equalsIgnoreCase("mt"))
//				{
				if(fmDoc.getJavaType().equalsIgnoreCase("String")  )
				{
					String remark = fmDoc.getCname() == null?"":fmDoc.getCname();
					String sAlter = "alter table %s add column %s varchar(%d) COMMENT '%s';\n";
					sb.append(String.format(sAlter, tmDoc.getName(),fmDoc.getName(), fmDoc.getLength(),remark.replace("'", "''") ));
				}
				if(fmDoc.getJavaType().equalsIgnoreCase("java.util.Date"))
				{
					String sAlter="alter table %s add column %s datetime(3) COMMENT '%s';\n";
					String remark = fmDoc.getCname() == null?"":fmDoc.getCname();
					sb.append(String.format(sAlter, tmDoc.getName(),fmDoc.getName(), remark.replace("'", "''") ));
				}else if(fmDoc.getJavaType().equalsIgnoreCase("java.math.BigDecimal"))
				{
					String sAlter="alter table %s add column %s decimal(%d,%d) COMMENT '%s';\n";
//					if(this.databaseProductName.equalsIgnoreCase("oracle"))
//					{
//						sAlter="alter table %s add (column %s number(%d,%d);comment on column %.%  is '%';\n";
//					}
					
					String remark = fmDoc.getCname() == null?"":fmDoc.getCname();
//					if(this.databaseProductName.equalsIgnoreCase("mysql"))
//					{
						sb.append(String.format(sAlter, tmDoc.getName(),fmDoc.getName(),
								fmDoc.getLength(),fmDoc.getScale(),
								remark.replace("'", "''") ));
					//}
//					else if(this.databaseProductName.equalsIgnoreCase("oracle"))
//					{
//						sb.append(String.format(sAlter, tmDoc.getName(),fmDoc.getName(),
//								fmDoc.getLength(),fmDoc.getScale(),
//								remark.replace("'", "''") ));
//					}
				}
				//}
				
			}else{
//				if(!fmDoc.getJavaType().equalsIgnoreCase(fmFind.getJavaType()))
//				{
//					System.err.println(String.format("表%s, 字段%s 类型和文档中定义的不一样[文档：%s|数据库：%s] ",
//							tmDoc.getName(),fmDoc.getName(),fmDoc.getJavaType(),fmFind.getJavaType()));
//				}else if(fmDoc.getLength() != fmFind.getLength() && !fmDoc.getType().equalsIgnoreCase("int"))
//				{
//					System.err.println(String.format("表%s, 字段%s 长度和文档中定义的不一样[文档：%d|数据库： %d] ",
//							tmDoc.getName(),fmDoc.getName(),fmDoc.getLength(),fmFind.getLength()));
//					
//					
//				}
			}
		}
		
	}
	
	
	
	private List<TableMeta> filterTables(List<TableMeta> tab,String sFilters)
	{
		if(!StringUtils.hasText(sFilters))
			return tab;
		List<TableMeta> ntab ;
		String[] sfilters = sFilters.split(",");
		ntab = tab.stream().filter(t->{ 
				for(String tn : sfilters)
				{
					if( t.getName().replace("_", "").equalsIgnoreCase(tn.replace("_","")))
						return true;
				}
				return false;
			}).collect(Collectors.toList());
		return ntab;		
	}

}
