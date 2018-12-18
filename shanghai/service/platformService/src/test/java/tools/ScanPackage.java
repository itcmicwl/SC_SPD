package tools;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.Table;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import common.utils.ScanUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:configs/spring-context-for-junit-nodb.xml" })
public class ScanPackage {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Before
	public void setUp() throws Exception {
	}
	private Template getTemplate( String name) throws Exception
	{
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		Template temp = cfg.getTemplate(name);
		return temp;
	}

	@Test
	public void test() throws Exception {
		
		AntPathMatcher pathMatcher = new  AntPathMatcher();
		
		assertTrue(pathMatcher.match("/foo/bar/**", "/foo/bar")) ;

		assertTrue(pathMatcher.match("", ""));

		assertTrue(pathMatcher.match("/{bla}.*", "/testing.html")); 
		
		
		
		List<Class<?>> list =ScanUtils.scanClass("gyqx.platform.**.controller", ( cls)-> {
				return true;//cls.isInterface();
			});
		
		for(Class<?> cls : list)
		{
			System.out.println(cls);
			RequestMapping annrm= cls.getAnnotation(RequestMapping.class);
			if(annrm != null){
				System.out.print(" " + annrm.value()[0]);
				Package  pkg = cls.getPackage();
				System.out.println(" ;" + pkg.getName());
				allInterfaceMethod(cls);
			}
		}
		//CollectionUtils.class
		/*list =ScanUtils.scanClass("gyqx.platform.**.po", new ScanUtils.IFilter() {			
			@Override
			public boolean filter(Class<?> cls) {
				 return cls.getAnnotation(Table.class) != null;
			}
		});
		
		for(Class<?> cls : list)
		{
			System.out.println(cls);
		}
		
		list =ScanUtils.scanClass("gyqx.platform.**.vo",null);
		
		for(Class<?> cls : list)
		{
			System.out.println(cls);
		}*/
	}
	private void allInterfaceMethod(Class cls)
	{
		for(Method method:cls.getDeclaredMethods())
		{
			String methodName = method.getName();
			RequestMapping annrm = method.getAnnotation(RequestMapping.class);
			if(annrm != null)
			{
				//System.out.print("\t" + methodName);
				System.out.println("\t" + annrm.value()[0]);
			}

			/*Type retType = method.getGenericReturnType();
			System.out.print(retType.getTypeName());
			System.out.print(" " + methodName + "(");
			for(Type t: method.getGenericParameterTypes())
			{
				System.out.print(t.getTypeName());
				System.out.print(",");
			}
			System.out.println(")");*/
		}
	}
	
	private void output(Class cls)
	{
		
	}

}
