package tools;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.util.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import common.db.meta.FieldMeta;
import common.db.meta.TableMeta;
import freemarker.template.Configuration;
import freemarker.template.Template;
import tools.codeBuilder.bean.BeanCodeCreator;
import tools.codeBuilder.parse.mysql.MysqlTableMetaBuilderFromExcel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class CreatePoAndSql
{
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	private Map createDataModel()
	{
		// Create the root hash. We use a Map here, but it could be a JavaBean too.
		Map<String, Object> root = new HashMap<>();

//		// Put string "user" into the root
//		root.put("user", "Big Joe");
//
//		// Create the "latestProduct" hash. We use a JavaBean here, but it could be a Map too.
//		Product latest = new Product();
//		latest.setUrl("products/greenmouse.html");
//		latest.setName("green mouse");
//		latest.setHasVal(true);
//		latest.setHasDay(false);
//		// and put it into the root
//		root.put("latestProduct", latest);
		
		return root;
	}
	
	private Template getTemplate( String name) throws Exception
	{
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		Template temp = cfg.getTemplate(name);
		return temp;
	}
	
	@Test
	public void create() throws Exception
	{
		URL url = Thread.currentThread().getContextClassLoader().getResource(".");
		File ufile = FileUtils.toFile(url);
		String ufileName = ufile.getPath();

		//String path ="E:/wjy/Work/GYQX/设计文档/表定义/";D:\Work\项目s\SPD\设计文档\02-数据库文档
		//String path ="D:/Work/项目s/SPD/设计文档/表定义/";
		String path ="D:/Work/项目s/SPD/设计文档/02-数据库文档/";//    平台表定义.xlsx  // 功能表定义.xlsx
		//String fileName = "平台表定义.xlsx";
		
		String fileName = "功能表定义.xlsx";
		File newFile = new File(path + "copy/" + fileName);
		FileUtils.copyFile(new File(path + fileName), newFile);
		MysqlTableMetaBuilderFromExcel builder = new MysqlTableMetaBuilderFromExcel();
		HashMap<String, List<TableMeta>> map = builder.getTableDescriptionsByExcelFile(newFile);
		
		
		for(Entry<String, List<TableMeta>> entry  :map.entrySet())
		{
			String groupName = entry.getKey();
			List<TableMeta> tables = entry.getValue();
			if(ObjectUtils.isEmpty( tables))
				continue;
			String sql = builder.getTableCreateSql(tables);
			if(StringUtils.hasText(sql))
			{
				FileUtils.write(new File(path + "/copy/" + groupName +".sql"), sql, "utf-8");
			}
			
			Template temp = getTemplate("bean.ftl");
			String packageName = "gyqx.spdhdi.po";//gyqx.spdhdi.mfrscert.po
			String jfPath = path + "/copy/po/";
			for(TableMeta td : tables)
			{
				String beanCode = new BeanCodeCreator().create(temp,td,packageName);//builder.getTableBeanSource(temp,td,packageName);
				FileUtils.write(new File(jfPath + FieldMeta.toUpperCamelName(td.getName()) +".java"),
						beanCode, "utf-8");
				System.out.println(beanCode);
			}
		}		
	}
	private void appendDict()
	{
		
	}

}
