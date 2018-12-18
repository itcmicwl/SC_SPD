package codeBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.util.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import common.db.SimpleDao;
import common.db.meta.TableMeta;
import freemarker.template.Configuration;
import freemarker.template.Template;
import tools.codeBuilder.parse.TableMetaBuilderFromExcel;
import tools.codeBuilder.parse.mysql.MysqlTableMetaBuilderFromExcel;
import tools.codeBuilder.parse.oracle.OracleTableMetaBuilderFromExcel;
import tools.toDb.TableBeanInfoUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context.xml" })

@Transactional(transactionManager="txManager")
@Commit
public class AllTablesMetaInfoCreateTest 
{
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private SimpleDao dao;	
	
	@Test
	public void output() throws Exception
	{
		create("mysql",false);
		//create("oracle",true);
	}
	
	//@Test
	public void outputSql() throws Exception
	{
		create("mysql",true);
		//create("oracle",true);
	}
	
	
	public void create(String dbType,boolean bOnlySql) throws Exception
	{
		StringBuilder sb = new StringBuilder();
		String docPath =  "E:\\doc\\spd相关\\documents\\02-数据库文档\\";
		
		String fileName = "二期部分结构.xlsx";
		String packageName = "gyqx.spdherp.po";
		
		String sFilters = "";
		System.out.println("正在处理：" + fileName);
		outputSql_BeanVO_SaveTable(docPath,fileName,packageName,sFilters,dbType,bOnlySql,sb);		
		System.out.println("完成：" + fileName );
		
//		fileName = "功能表定义.xlsx";
//		packageName = "gyqx.spdhdi.po";
//		System.out.println("正在处理：" + fileName);
//		outputSql_BeanVO_SaveTable(docPath,fileName,packageName,sFilters,dbType,bOnlySql,sb);
//		System.out.println("完成：" + fileName );
//
//		fileName = "内网功能表定义.xlsx";
//		packageName = "gyqx.spdherp.po";
//		System.out.println("正在处理：" + fileName);
		outputSql_BeanVO_SaveTable(docPath,fileName,packageName,sFilters,dbType,bOnlySql,sb);
		
		FileUtils.write(new File(docPath + "/copy/alltables-" + dbType +".sql"), sb.toString(), "utf-8");
		System.out.println("完成：" + fileName );
	}
	private void outputSql_BeanVO_SaveTable(String docPath,String fileName,String packageName,String sFilters
			,String dbType,boolean bOnlySql,StringBuilder sb) throws Exception
	{
		File newFile = new File(docPath + "copy/" + fileName);
		FileUtils.copyFile(new File(docPath + fileName), newFile);
		TableMetaBuilderFromExcel builder = new MysqlTableMetaBuilderFromExcel();
		
		if(dbType.equalsIgnoreCase("oracle"))
			builder = new OracleTableMetaBuilderFromExcel("spd");
		
		System.out.println("database type:" + dbType);
		
		HashMap<String, List<TableMeta>> map = builder.getTableDescriptionsByExcelFile(newFile);
		
		TableBeanInfoUtils bcreator = new TableBeanInfoUtils(dao);
		
		for(Entry<String, List<TableMeta>> entry  :map.entrySet())
		{
			String groupName = entry.getKey();
			List<TableMeta> tables = entry.getValue();
			if(ObjectUtils.isEmpty( tables))
				continue;
			
			tables = filterTables(tables,sFilters);
			if(ObjectUtils.isEmpty( tables))
				continue;
			
			String sql = builder.getTableCreateSql(tables);
			if(StringUtils.hasText(sql))
			{
				FileUtils.write(new File(docPath + "/copy/"+ groupName+ "." + dbType +".sql"), sql, "utf-8");
				sb.append("\n").append(sql);
			}
			if(!bOnlySql){
				for(TableMeta td : tables)
				{
					String subSystem = packageName.substring(0, packageName.lastIndexOf("."));
					bcreator.saveTable(td, subSystem);
				}
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
