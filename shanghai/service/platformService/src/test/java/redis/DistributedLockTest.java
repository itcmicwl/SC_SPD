package redis;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import common.cache.CacheWrapper;
import common.utils.UtilsContext;
import common.utils.lock.LockUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class DistributedLockTest {
//	static{
//		try {
//			Log4jConfigurer.initLogging("classpath:configs/log4j2.xml");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//	}

	@Resource
	private UtilsContext utils;
	
	@Resource
	private LockUtils lockUtils;
	@Before
	public void setUp() throws Exception {
		 
	}
	

	@Test
	public void test() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		String ds =sdf.format(new Date());
		CacheWrapper cache = utils.getCacheUtils().getCache("status",false);
		
		//String lk = cache.lock("test");
		cache.put("flag", (long)0);
		//cache.unlock("test" ,lk);
		
		int n = 1;
		Thread[] threads = new Thread[n];
		List<Integer> [] testResults = new ArrayList[n];
		for(int i = 0; i < threads.length;i++)
		{
			testResults[i] = new ArrayList<Integer>();
			threads[i] = new Thread(new AtomAdv("t" + i,cache,lockUtils,testResults[i]));
			threads[i].setName("testthread:" + i);
			threads[i].start();
		}
		
		for(int i = 0; i < threads.length;i++)
		{
			threads[i].join();
		}
		System.out.println("ok");
		
	}

}
class AtomAdv implements Runnable
{
	CacheWrapper cache;
	private String name;
	public AtomAdv(String name,CacheWrapper cache,LockUtils lockUtils,List<Integer> list)
	{
		this.name = name;
		this.cache = cache;
		this.list = list;
		this.lockUtils = lockUtils;
	}
	private List<Integer> list;	
	private LockUtils lockUtils;

//	@Override
//	public void run() {
//		int i = 0;
//		while(i++ <100){
//			long lockKey = 0;
//			try {
//				lockKey = cache.lock("test");
//				Integer v = cache.get("flag");
//				//list.add(v);
//				System.out.println(name + ":" + String.format("%04d", v));
//				cache.put("flag", ++v);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}finally{
//				cache.unlock("test",lockKey);
//			}
//		}		
//	}	
	@Override
	public void run() {
		int i = 0;
		while(i++ <100){
			long lockKey = 0;
			try {
				lockUtils.lock("test");
				Integer v = cache.get("flag");
				list.add(v);
				System.out.println(name + ":" + String.format("%04d", v));
				cache.put("flag", ++v);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				lockUtils.unlock("test");
			}
		}		
	}	
}
