package codeBuilder;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import common.db.SimpleDao;
import common.db.dialect.mysql.MysqlDbMetaInfo;
import common.db.meta.TableMeta;
import freemarker.template.Configuration;
import freemarker.template.Template;

import tools.codeBuilder.bean.ClassInfo;
import tools.codeBuilder.builder.CodeBuilderWithTableMeta;
import tools.codeBuilder.builder.ControllerCodeBuilderByScan;
import tools.codeBuilder.builder.QueryCodeBuilder;
import tools.codeBuilder.parse.CodeParser;
import tools.toDb.TableBeanInfoUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context.xml" })
public class ServiceAndControllerCodeGenTest {
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private SimpleDao dao;

	@Before
	public void setUp() throws Exception {
	}
	private Template getTemplate( String name) throws Exception
	{
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		Template temp = cfg.getTemplate(name);
		return temp;
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
	// 源代码输出目录
	String outputPath= "D:/Workspaces";
	
	
	/**
	 * 根据表生成基本处理代码
	 * @throws Exception
	 */
	@Test
	public void outputPoAllCode() throws Exception {
		
		TableBeanInfoUtils utils = new TableBeanInfoUtils(dao);
		//D:\Workspaces\eclipse\spdHDI\src\main\java
		// 子系统的包路径
		String subSystem = "gyqx.spdherp";
		//系统分组名称
		String groupName = "stockout";
		// 需要选择的表，空表示选择全部
		String sFilters = "sicker_use,sicker_use_list";
		
		List<TableMeta> tables = utils.getTableMetasFromDb(subSystem);
		
		tables = filterTables(tables,sFilters);
		MysqlDbMetaInfo dbMetaInfo = new MysqlDbMetaInfo();
		dbMetaInfo.appendTables(tables);
		

		for(TableMeta tm : tables){
			System.out.println("正在生成表" +tm.getName() + "的相关对象");
			CodeBuilderWithTableMeta codeBuilder = new CodeBuilderWithTableMeta(freeMarkerConfigurer,tm
					,outputPath);
			
			codeBuilder.build( groupName);
		}
		System.out.println("ok");
	}
	/**
	 * 根据SQL语句生成查询处理代码
	 * @throws Exception
	 */
	//@Test
	public void outputQueryBeanCode() throws Exception
	{
		// 子系统的包路径
		String subSystem = "gyqx.spdhdi";
		// 源代码输出目录
		//String outputPath="D:/Workspaces/eclipse/platform/src/main/java";
		
		//系统分组名称
		String groupName = "test";
		// 查询条件对象名称
		String queryBeanName = "QueryVo";
		//查询结果对象名称
		String queryResultBeanName = "QueryVo";
				
		QueryCodeBuilder builder = new QueryCodeBuilder(freeMarkerConfigurer,dao,subSystem,outputPath);
		
		InputStream inStr = this.getClass().getResourceAsStream( "sql.txt");
		String sql = IOUtils.toString(inStr,Charset.forName("utf-8"));	
		
		System.out.println("正在根据SQL语名生成相关的查询处理代码，查询结象名称：" + queryBeanName );
		builder.build(sql, groupName, queryBeanName, queryResultBeanName);
		System.out.println("ok!");
	}
	
	/**
	 * 通过扫描服务接口包，生成Con
	 * @throws Exception
	 */
	//@Test
	public void outputControllerCode() throws Exception
	{
		
		// 源代码输出目录
		//String outputPath="D:/Workspaces/eclipse/platform/src/main/java";
		
		String packageScanPath="gyqx.platform.permission.service";
		
		ControllerCodeBuilderByScan builder = new ControllerCodeBuilderByScan(freeMarkerConfigurer,outputPath,packageScanPath);
		
		builder.build();
		
		System.out.println("ok!");
	}

}
