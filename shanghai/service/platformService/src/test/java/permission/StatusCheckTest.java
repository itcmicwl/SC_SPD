package permission;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.cache.CacheWrapper;
import common.utils.UtilsContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class StatusCheckTest 
{
	@Resource
	private UtilsContext utils;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		CacheWrapper cache = utils.getCacheUtils().getCache("status",false);
		
		Object o = cache.getAndSet("abc", System.currentTimeMillis()) ;
		System.out.println(o);
		
		cache.put("abc", System.currentTimeMillis());
		System.out.println(o);
		
	}

}
