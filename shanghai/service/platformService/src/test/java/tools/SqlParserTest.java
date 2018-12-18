package tools;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import common.db.SimpleDao;
import common.db.datatable.DataColumn;
import common.db.datatable.DataTable;
import common.db.dialect.mysql.MysqlDbMetaInfo;
import common.db.meta.DbMetaInfo;
import common.db.meta.FieldMeta;
import common.db.meta.TableMeta;
import common.db.parse.SqlColumn;
import common.db.parse.SqlParser;
import common.db.parse.UserVariableCtx;
import freemarker.template.Configuration;
import freemarker.template.Template;
import net.sf.jsqlparser.statement.Statement;
import tools.codeBuilder.bean.BeanDesc;
import tools.codeBuilder.bean.BeanField;
import tools.codeBuilder.parse.mysql.MysqlTableMetaBuilderFromExcel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class SqlParserTest {

	private DbMetaInfo dbMetaInfo;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource
	private SimpleDao dao;
	
	@Before
	public void setUp() throws Exception {
		dbMetaInfo = new MysqlDbMetaInfo();
	}
	private Template getTemplate( String name) throws Exception
	{
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		Template temp = cfg.getTemplate(name);
		return temp;
	}
	

	@Test
	public void test() throws Exception {
		
		//String path ="D:/Work/项目s/SPD/设计文档/表定义/";
		String path ="E:/wjy/Work/GYQX/设计文档/表定义/";
		MysqlTableMetaBuilderFromExcel builder = new MysqlTableMetaBuilderFromExcel();
		HashMap<String, List<TableMeta>> map = builder.getTableDescriptionsByExcelFile(path + "平台表定义.xlsx");
		for(Entry<String, List<TableMeta>> entry  :map.entrySet())
		{
			String groupName = entry.getKey();
			List<TableMeta> tables = entry.getValue();
			
			dbMetaInfo.appendTables(tables);
		}
		//String where = "exists (select * from t) or f1 = @param and a like @param and a1 between @param1 and @param or fin in(a,'b',123,@param )";
		//parse();
		//testMultithread();
		parseAndExecute();
	}
	private void testMultithread() throws Exception
	{
		InputStream inStr = this.getClass().getResourceAsStream( "sql.txt");
		String sql = IOUtils.toString(inStr,Charset.forName("utf-8"));
		SqlParser parser = new SqlParser(sql);
		
		final Statement stmt = parser.parse();
		
		parser = new SqlParser(sql);
		parser.parse(stmt);
		
		Thread[] threads = new Thread[32];
		
		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				SqlParser parser = new SqlParser(sql);
				try {
					parser.parse(stmt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}};
		
		for(int i = 0; i<threads.length;i++)
		{
			threads[i] = new Thread(runnable);			
		}
		for(int i = 0; i<threads.length;i++)
		{
			threads[i].start();
			threads[i].join();
		}

	}
	private void parse() throws Exception
	{		
		InputStream inStr = this.getClass().getResourceAsStream( "sql.txt");
		
		String sql =null;// "select *,t.*,t.f1,a2.f1, al1.f3+a2.f4 as m, (select count(*) from v) as f, count(*) as cnt from t al1 inner join (select * from t2) a2 where " + where;
		sql = IOUtils.toString(inStr,Charset.forName("utf-8"));
		SqlParser parser = new SqlParser(sql);
		parser.parse();
		//ConcurrentHashMap<String,SqlParser> m = new ConcurrentHashMap<>();
		HashMap <String,SqlParser> m = new HashMap<>();
		long t0 = System.currentTimeMillis();
		for(int i = 0; i < 1000000;i++){
			//SqlParser parser = new SqlParser(sql);
			String nsql = sql;//.replace(" ","");
			//if(m.get(nsql)==null)
			m.put(nsql, parser);
			//parser.parse();
			//List<SqlColumn> columns = parser.parseColumns(dbMetaInfo);
		}
		t0 = System.currentTimeMillis() -t0;
		System.out.println(t0);
	}
	private void parseAndExecute() throws Exception
	{
		InputStream inStr = this.getClass().getResourceAsStream( "sql.txt");
		String sql =null;
		
		sql = IOUtils.toString(inStr,Charset.forName("utf-8"));
		SqlParser parser = new SqlParser(sql);
		parser.parse();
		
		List<SqlColumn> columns = parser.parseColumns(dbMetaInfo);
		List<String> paramNames = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		for(UserVariableCtx uv: parser.getUserVariables())
		{	
			String column = uv.getColumn().getColumnName();
			System.out.println(uv.getColumn().getFullyQualifiedName());
			paramNames.add(column);
			params.add(null);
		}
		DataTable dt = dao.queryDataTable(sql,params);
		
		String packageName = "";
		String queryBeanName = "" + "Query";
		String queryResultBeanName = "" +"QueryResult";		
		
		BeanDesc qBeanDesc = new BeanDesc();
		BeanDesc qrBeanDesc = new BeanDesc();
		
		qBeanDesc.setName(queryBeanName);
		qrBeanDesc.setName(queryResultBeanName);
		
		for(DataColumn dc: dt.getColumns().getColumns())
		{
			if(StringUtils.hasText(dc.getTableName()))
			{
				String tn = dc.getTableName();
				FieldMeta fd = dbMetaInfo.get(tn).get(dc.getFieldName());				
					
				BeanField field = new BeanField(fd);
				qrBeanDesc.getFields().add(field);
				
				if(dc.getJavaType().equalsIgnoreCase("Date"))
				{
					fd = new FieldMeta();
					fd.setName(dc.getFieldName()+"$begin");
					fd.setCname(dc.getFieldName()+"$begin");					
					fd.setJavaType(dc.getJavaType());
					field = new BeanField(fd);
					qBeanDesc.getFields().add(field);
					
					fd = new FieldMeta();
					fd.setName(dc.getFieldName()+"$end");
					fd.setCname(dc.getFieldName()+"$end");
					fd.setJavaType(dc.getJavaType());
					field = new BeanField(fd);
					qBeanDesc.getFields().add(field);
				}

			}else{
				if(dc.getJavaType().equalsIgnoreCase("Date"))
				{
					FieldMeta fd = new FieldMeta();
					fd.setName(dc.getFieldName()+"$begin");
					fd.setCname(dc.getFieldName()+"$begin");					
					fd.setJavaType(dc.getJavaType());
					BeanField field = new BeanField(fd);
					qBeanDesc.getFields().add(field);
					
					fd = new FieldMeta();
					fd.setName(dc.getFieldName()+"$end");
					fd.setCname(dc.getFieldName()+"$end");
					fd.setJavaType(dc.getJavaType());
					field = new BeanField(fd);
					qBeanDesc.getFields().add(field);
					
					fd = new FieldMeta();
					fd.setName(dc.getFieldName());
					fd.setCname(dc.getFieldName());	
					fd.setJavaType(dc.getJavaType());
					field = new BeanField(fd);
					qrBeanDesc.getFields().add(field);
					
				}else{
					FieldMeta fd = new FieldMeta();
					fd.setName(dc.getFieldName());
					fd.setCname(dc.getFieldName());
					
					fd.setJavaType(dc.getJavaType());
					BeanField field = new BeanField(fd);
					
					qBeanDesc.getFields().add(field);
					qrBeanDesc.getFields().add(field);
				}
			}
		}
		
		System.out.println(dt.getRows().size());
		
	}

}
