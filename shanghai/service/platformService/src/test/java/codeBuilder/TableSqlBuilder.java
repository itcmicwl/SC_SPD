package codeBuilder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import common.db.SimpleDao;
import common.db.dialect.mysql.MysqlDbMetaInfo;
import common.db.dialect.oracle.OracleDbMetaInfo;
import common.db.meta.FieldMeta;
import common.db.meta.TableMeta;
import freemarker.template.Configuration;
import freemarker.template.Template;
import tools.codeBuilder.bean.BeanCodeCreator;
import tools.codeBuilder.parse.mysql.MysqlTableMetaBuilderFromExcel;
import tools.codeBuilder.parse.oracle.OracleTableMetaBuilderFromExcel;
import tools.query.creator.QueryBeanDescCreator;
import tools.toDb.TableBeanInfoUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context.xml" })

@Transactional(transactionManager="txManager")
@Commit
public class TableSqlBuilder 
{
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private SimpleDao dao;	
	
	private Template getTemplate( String name) throws Exception
	{
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		Template temp = cfg.getTemplate(name);
		return temp;
	}
	
	//@Test
	public void create() throws Exception
	{
		//String path ="E:/wjy/Work/GYQX/设计文档/02-数据库文档/";//D:\Work\项目s\SPD\设计文档\02-数据库文档
		//String path ="D:/Work/项目s/SPD/设计文档/02-数据库文档/";
		String docPath = "D:/Work/项目s/SPD/设计文档/02-数据库文档/"; //"D:/Work/项目s/SPD/设计文档/02-数据库文档/";//    平台表定义.xlsx  // 功能表定义.xlsx
		
		String fileName = "平台表定义.xlsx";
		String packageName = "gyqx.platform.po";
		
		//String fileName = "内网功能表定义.xlsx";//"内网功能表定义.xlsx";//"功能表定义.xlsx";
		//String packageName = "gyqx.spdherp.po";
		
		String sFilters = "sys_message,sys_message_confirm";//+ "distr_bill_list,distr_bill";//"bas_barcode_rules"; 

		//String sFilters = "";
		
		outputSql_BeanVO_SaveTable(docPath,fileName,packageName,sFilters);
		System.out.println("ok!");
	}
	private void outputSql_BeanVO_SaveTable(String docPath,String fileName,String packageName,String sFilters) throws Exception
	{
		File newFile = new File(docPath + "copy/" + fileName);
		FileUtils.copyFile(new File(docPath + fileName), newFile);
		MysqlTableMetaBuilderFromExcel builder = new MysqlTableMetaBuilderFromExcel();
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
				FileUtils.write(new File(docPath + "/copy/"+ groupName +".sql"), sql, "utf-8");
			}
			
			Template temp = getTemplate("bean.ftl");
			//String packageName = "gyqx.platform.po";//gyqx.spdhdi.po
			//String packageName = "gyqx.spdhdi.po";
			String jfPath = docPath + "/copy/" +packageName.replace(".", "/")+ "/";
			for(TableMeta td : tables)
			{
//				String beanCode = new BeanCodeCreator().create(temp,td,packageName);
//				FileUtils.write(new File(jfPath + FieldMeta.toUpperCamelName(td.getName()) +".java"),
//						beanCode, "utf-8");

				String subSystem = packageName.substring(0, packageName.lastIndexOf("."));
				bcreator.saveTable(td, subSystem);
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
	
	public void createOracleTableSql()
	{
		String docPath = "D:/Work/项目s/SPD/设计文档/02-数据库文档/";
	}
	private void outputOracleSql(String docPath,String dbName,String fileName,String packageName,String sFilters) throws Exception
	{
		File newFile = new File(docPath + "copy/" + fileName);
		FileUtils.copyFile(new File(docPath + fileName), newFile);
		OracleTableMetaBuilderFromExcel builder = new OracleTableMetaBuilderFromExcel(dbName);
		HashMap<String, List<TableMeta>> map = builder.getTableDescriptionsByExcelFile(newFile);
		
		//TableBeanInfoUtils bcreator = new TableBeanInfoUtils(dao);
		
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
				FileUtils.write(new File(docPath + "/copy/"+ groupName +".sql"), sql, "utf-8");
			}
			
//			Template temp = getTemplate("bean.ftl");
//			
//			String jfPath = docPath + "/copy/" +packageName.replace(".", "/")+ "/";
//			for(TableMeta td : tables)
//			{
//
//
//				String subSystem = packageName.substring(0, packageName.lastIndexOf("."));
//				bcreator.saveTable(td, subSystem);
//			}
		}		
	}
	
	@Test
	public void createOracleSqlFromDb() throws Exception
	{
		TableBeanInfoUtils utils = new TableBeanInfoUtils(dao);
		String groupName = "*";
		//String packageName = "gyqx.platform.po"; //"gyqx.platform.sys.po"; // "gyqx.spdhdi.po";
		String dbName = "spd";
		
		String docPath = "D:/Work/项目s/SPD/设计文档/02-数据库文档/";
		
		List<TableMeta> tables = utils.getTableMetasFromDb(groupName);
		
		
		//tables = filterTables(tables,"sys_message,sys_message_confirm");
		MysqlDbMetaInfo dbMetaInfo = new MysqlDbMetaInfo(dao.getDataSource());
		
		//OracleDbMetaInfo dbMetaInfo = new OracleDbMetaInfo(dao.getDataSource());
		
		dbMetaInfo.appendTables(tables);
		
		OracleTableMetaBuilderFromExcel builder = new OracleTableMetaBuilderFromExcel(dbName);
		
		String sql = builder.getTableCreateSql(tables);
		if(StringUtils.hasText(sql))
		{
			if(groupName.equalsIgnoreCase("*"))
				groupName = "alltables";
			FileUtils.write(new File(docPath + "/copy/"+ groupName +".sql"), sql, "utf-8");
		}
		
	}
	//@Test
	public void createFromDb() throws Exception
	{
		TableBeanInfoUtils utils = new TableBeanInfoUtils(dao);
		String groupName = "gyqx.spdhdi";
		String packageName = "gyqx.spdhdi.po"; //"gyqx.platform.sys.po"; // "gyqx.spdhdi.po";

		List<TableMeta> tables = utils.getTableMetasFromDb(groupName);
		MysqlDbMetaInfo dbMetaInfo = new MysqlDbMetaInfo();
		dbMetaInfo.appendTables(tables);
		
		String path ="e:/temp";//    平台表定义.xlsx  // 功能表定义.xlsx
		Template temp = getTemplate("bean.ftl");
		//String packageName = "gyqx.platform.sys.po";//gyqx.spdhdi.po
		
		String jfPath = path + "/copy/po/" + packageName.replace(".", "/");
		
		
		String qoPackageName = groupName + ".qo";
		
		//String qoPackageName = groupName + ".qo";
		
		for(TableMeta td : tables)
		{
			String beanCode = new BeanCodeCreator().create(temp,td,packageName);
			FileUtils.write(new File(jfPath +"/"+ FieldMeta.toUpperCamelName(td.getName()) +".java"),
					beanCode, "utf-8");
			System.out.println(beanCode);
		}
		
		QueryBeanDescCreator creator = new QueryBeanDescCreator(dbMetaInfo,freeMarkerConfigurer,dao);		
		
		String queryBeanName = "SomeQ";
		String queryResultBeanName = "SomeQR";
		String basePath = path + "query/";
		
		creator.create(packageName, "select * from " + tables.get(0) , queryBeanName, queryResultBeanName, basePath);
	}

}
