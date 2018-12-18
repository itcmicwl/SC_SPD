package tools;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import common.db.SimpleDao;
import common.db.dialect.mysql.MysqlDbMetaInfo;
import common.db.meta.DbMetaInfo;
import common.db.meta.TableMeta;
import tools.codeBuilder.parse.mysql.MysqlTableMetaBuilderFromExcel;
import tools.query.creator.QueryBeanDescCreator;
import tools.toDb.TableBeanInfoUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class QueryBeanCreate {

	private DbMetaInfo dbMetaInfo;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource
	private SimpleDao dao;
	
	String path ="D:/Work/项目s/SPD/设计文档/02-数据库文档/";//    平台表定义.xlsx  // 功能表定义.xlsx
	String fileName = "功能表定义.xlsx";
	//String path ="E:/wjy/Work/GYQX/设计文档/表定义/";
	@Before
	public void setUp() throws Exception {
		dbMetaInfo = new MysqlDbMetaInfo();
		
		TableBeanInfoUtils utils = new TableBeanInfoUtils(dao);
		String groupName = "gyqx.spdhdi";
		String packageName = "gyqx.spdhdi.po"; //"gyqx.platform.sys.po"; // "gyqx.spdhdi.po";
		List<TableMeta> tables = utils.getTableMetasFromDb(groupName);
		dbMetaInfo.appendTables(tables);	
		
	}

	@Test
	public void test() throws Exception {
		InputStream inStr = this.getClass().getResourceAsStream( "sql.txt");
		String sql = IOUtils.toString(inStr,Charset.forName("utf-8"));
		
		/*QueryBeanDescCreator creator = new QueryBeanDescCreator(dbMetaInfo,freeMarkerConfigurer,dao);
		
		String packageName = "a.b.c";
		String queryBeanName = "SomeQ";
		String queryResultBeanName = "SomeQR";
		String basePath = path + "query/";
		
		creator.create(packageName, sql, queryBeanName, queryResultBeanName, basePath);*/
		
		
		
	}

}
