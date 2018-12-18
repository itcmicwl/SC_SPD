package tools;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.db.SimpleDao;
import common.utils.UtilsContext;
import common.utils.jms.JMSSender;
import gyqx.platform.po.SysString;
import gyqx.platform.sys.service.ISysStringService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context.xml" })
public class MybatisInterceptorTest 
{
	@Resource
	private UtilsContext utilsContext;
	@Resource
	private SimpleDao dao;
	@Resource
	private ISysStringService service;
	
	@Before
	public void setUp() 
	{
		
	}
	@Test
	public void test() throws Exception
	{
		
		Pattern pat = Pattern.compile("update\\s+(\\w+)|delete\\s+from\\s+((\\w+))", Pattern.CASE_INSENSITIVE);
		String sqlt = "upDate tab set ..";
		//String sqlt = "delete from tab  ..";
		Matcher mat = pat.matcher(sqlt);
		if(mat.find())
		{
			for(int i = 0; i< mat.groupCount();i++)
			{
				System.out.println( mat.group(i));
			}
		}
		
		dao.executeSql("delete from sys_string where id = ?","new.id1");
		
		SysString ss  = new SysString();
		ss.setId("new.id1");
		ss.setEname("test.name");
		ss.setKind("");
		ss.setMt(new Date());
		ss.setStatus("1");
		ss.setVal("value...");
		ss.setVersion(0);
		service.insertSysString(ss);
		service.setStatus(ss);
		ss = service.getSysStringByid(ss.getId());
		service.deleteSysString(ss);
		service.getSysStringAll();
		System.in.read();
	}
	//@Test
	public void testMultithread() throws Exception
	{
		long start = System.currentTimeMillis();   
		Thread[] threads = new Thread[10];
		for(int i = 0; i< threads.length;i++)
		{
			final String n = "thread_" + i; 
			threads[i] = new Thread(()->{
				for(int m = 0; m< 500000;m++){
					utilsContext.getJmsSendUtils().send("mult.test."  , "{property='id', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}, ParameterMapping{property='ename', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}, ParameterMapping{property='val', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}, ParameterMapping{property='remark', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}, ParameterMapping{property='kind', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}", null);
					System.out.println("this=" + n + ";m=" + m );
				}	
			});
			
			threads[i].start();
		}
		for(int i = 0; i< threads.length;i++)
		{
			threads[i].join();
		}
		long end = System.currentTimeMillis();  
		System.out.println(end-start);
	}

}
