package fun;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.codehaus.groovy.binding.BindingProxy;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class NamedParameterJdbcTemplateTest
{
	@Resource(name="dataSource")
	DataSource ds;
	NamedParameterJdbcTemplate njt;
	
	JdbcTemplate jt;
	
	@Before
	public void setUp() throws Exception {
		njt = new NamedParameterJdbcTemplate(ds);
		jt = new JdbcTemplate(ds);
	}
	//@Test
	public void test(){
		Map map = new LinkedHashMap();
		map.put("mfrsKind", "1");
		List<?> list = njt.queryForList("select * from bas_mfrs_info  where mfrs_kind = :mfrsKind", map);
		
		List<?> list1 = jt.queryForList("select * from bas_mfrs_info  where mfrs_kind = ?", "1");
		System.out.println(list.size());
	}
	@Test
	public void testGroovy() throws IOException{
		
		InputStream inStr = this.getClass().getResourceAsStream( "sql.txt");
		String sql = IOUtils.toString(inStr,Charset.forName("utf-8"));	
		
		inStr = this.getClass().getResourceAsStream( "groovy-script.txt");
		String script = IOUtils.toString(inStr,Charset.forName("utf-8"));	
		
		
		Map map = new LinkedHashMap();
		map.put("mfrsKind", "1");
		
		ImportCustomizer importCustomizer = new ImportCustomizer();
	    importCustomizer.addStaticStars("common.transform.Tx");
	    CompilerConfiguration configuration = new CompilerConfiguration();
	    configuration.addCompilationCustomizers(importCustomizer);
	    
		
		Binding binding = new Binding();  
		  
	    binding.setVariable("njt", njt);  
	    
	    binding.setVariable("map", map);  
	    
	    GroovyShell shell = new GroovyShell(binding,configuration);
	    
	    Object value =shell.evaluate(script);  
	    
	    System.out.println(value);
	}
	

}
