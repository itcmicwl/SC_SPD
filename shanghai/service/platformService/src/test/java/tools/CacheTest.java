package tools;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.base.formatter.MultiDateFormat;
import common.cache.redis.StringObjectRedisTemplate;
import common.utils.CacheUtils;
import demo.test.vo.DateTest;
import demo.test.vo.Organization;
import demo.test.vo.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context.xml" })
public class CacheTest 
{
	@Autowired
	CacheManager cacheManager ;
	
	@Autowired
	StringObjectRedisTemplate stringObjRedisTemplate;
	
	@Autowired
	CacheUtils cacheUtils;

	@Before
	public void setUp() throws Exception 
	{
		System.out.println(cacheManager);
	}

	@Test
	public void test() throws InterruptedException, Exception
	{
		testLarge();
		
		/*
		String key = "test:key";
		cacheUtils.getSessionCache().put("test:key", "keyvalue'''''");
		
		for(int i = 0; i< 10000;i++ )
		{
			Object ret = cacheUtils.getSessionCache().get(key);
		
			System.out.println(ret);
		}*/
		
		//stringObjRedisTemplate.expire(key, timeout, unit)
		
		/*Cache cache = cacheManager.getCache("pubCache");
		cache.put("hello_redis",this );
		
		ValueWrapper vw = cache.get("hello_redis");
		Object val = vw.get();
		
		System.out.println(val);*/
		
//		ValueOperations<String,Object> voper = stringObjRedisTemplate.opsForValue();
//		
//		voper.increment("atom1", 1L);
//		Thread[] threads = new Thread[1];
//		for(int rhi = 0; rhi < threads.length; rhi++){
//			threads[rhi] = new Thread(new Runnable(){
//					@Override
//					public void run() {
//						for(int i = 0 ; i < 100000; i++){
//							//voper.increment("atom1", 1L);
//							String key = "test:key";
//							cacheUtils.getSessionCache().put(key, "keyvalue'''''");	
//							
//						}						
//					}				
//				});
//			threads[rhi].start();
//		}
//		
//		for(int rhi = 0; rhi < threads.length; rhi++){
//			threads[rhi].join();
//		}
	}
	
	private void testLarge() throws JsonGenerationException, JsonMappingException, IOException
	{
		/*stringObjRedisTemplate.executePipelined(new RedisCallback<Long>(){

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {

	        	//String key = "`session`@:538d6f54-1114-4a19-85be-a96df46c464d~key~" ;
	        	String key = "asdffff" ;
	        	connection.set(stringObjRedisTemplate.getKeyRaw(key),
	        			stringObjRedisTemplate.getValueRaw(pt));
	        	byte[] rawkey = stringObjRedisTemplate.getKeyRaw(key);
	        	//byte[] raw = connection.get(rawkey);
	        	
	        	
	        	connection.set(rawkey,
	        			stringObjRedisTemplate.getValueRaw(" value "));
	        	byte[] raw1 = connection.get(rawkey);
	        	Object ret = stringObjRedisTemplate.getObjectFromRaw(raw1);
		    		//stringObjRedisTemplate.opsForValue().set(key,pt);
		    		//Object ret = stringObjRedisTemplate.opsForValue().get(key);

				return null;
			}
        	
        } );
		String key = "`session`@:538d6f54-1114-4a19-85be-a96df46c464d~key~" ;
		Object obj = stringObjRedisTemplate.opsForValue().get(key);*/
		
		Organization org1 = new Organization("ApacheIgnite","wu han xxxxxxxx",1,new Date());
        Organization org2 = new Organization("Other","china ccccc xxxxxxxx",1,new Date());
        
        List<Person> persons = new ArrayList<>();
        for(int i=0;i < 1000;i++)
        {
        	Person pt = new Person(i%2==0?org1:org2, "John" + i, "Doe" + i, 2000 +i, "John Doe has Master Degree.!!!!!!!!!");
        	persons.add(pt);
        }
        //`session`@:538d6f54-1114-4a19-85be-a96df46c464d~key~
        
        
        
      /*  stringObjRedisTemplate.executePipelined(new RedisCallback<Long>(){

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				for(Person pt :persons){
		        	String key = "uid:" + pt.id.toString();
		        	connection.set(stringObjRedisTemplate.getKeyRaw(key),
		        			stringObjRedisTemplate.getValueRaw(pt));
		        	
		        	byte[] raw = connection.get(stringObjRedisTemplate.getKeyRaw(key));
		        	
		        	//Object ret = stringObjRedisTemplate.getObjectFromRaw(raw);
		    		//stringObjRedisTemplate.opsForValue().set(key,pt);
		    		Object ret = stringObjRedisTemplate.opsForValue().get(key);
		    	}
				return null;
			}
        	
        } );*/
        
        DateTest dt = new DateTest();
        dt.setId("01");
        dt.setLast(new Date());
        
        String key = "uid:" + dt.getId();
		stringObjRedisTemplate.opsForValue().set(key,dt);
		Object ret = stringObjRedisTemplate.opsForValue().get(key);
		
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		
		MultiDateFormat dateFormat = new MultiDateFormat();
		
		mapper.setDateFormat(dateFormat);
		StringWriter strw = new StringWriter();
		mapper.writeValue(strw,dt);
		
		System.out.println(strw.toString());
		String json = "{\"id\":\"01\",\"last\":\"2017-06-15\"}";
		
		DateTest dt0 =  mapper.readValue(json, DateTest.class);
		
		System.out.println(ret);
        
        
       /* for(Person pt :persons){
        	String key = "uid:" + pt.id.toString();
    		stringObjRedisTemplate.opsForValue().set(key,pt);
    		Object ret = stringObjRedisTemplate.opsForValue().get(key);
    		System.out.println(ret);
    	}*/
	}

}
