package tools;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

import common.utils.UtilsContext;
import common.utils.message.MessageSender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context.xml" })
public class MessageSenderTest 
{
	@Resource
	private UtilsContext utilsContext;
	@Before
	public void setUp() throws Exception 
	{
		
	}

	//@Test
	public void test() throws JsonProcessingException
	{
		String txt = "{\"cmd\":\"sql.table.changed\",\"context\":null,\"createDate\":1511407393208,\"method\":\"insert\",\"tableName\":\"purconfirm\",\"sql\":\"\"}";
        
		MessageSender.send("title", "message", "url", "writerId", "", "user-00026","user-00028");
		MessageSender.send("title", "message", "url", "writerId", "org-101");
	}
	@Test
	public void testMultithread() throws InterruptedException
	{
		long start = System.currentTimeMillis();   
		Thread[] threads = new Thread[64];
		for(int i = 0; i< threads.length;i++)
		{
			final String n = "thread_" + i; 
			threads[i] = new Thread(()->{
				for(int m = 0; m< 10000;m++){
					utilsContext.getJmsSendUtils().send("mult.test." , "{property='id', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}, ParameterMapping{property='ename', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}, ParameterMapping{property='val', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}, ParameterMapping{property='remark', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}, ParameterMapping{property='kind', mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}", null);
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
