package tools;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.cache.SegmentCache;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class UserOnlineStateTest 
{
	@Resource
	private UtilsContext utilsContext;

	@Before
	public void setUp() throws Exception 
	{
	}

	@Test
	public void test() 
	{
		List<UserOnlineInfo> uos = utilsContext.getUserStateUtils().listAllUserOnline();
		UserOnlineInfo userOnline = null;
		if(uos == null || uos.size() ==0){
			userOnline = new UserOnlineInfo();
			userOnline.setSessionId(UUID.randomUUID().toString());
			userOnline.setCacheId(UUID.randomUUID().toString());
			
			utilsContext.getUserStateUtils().set(userOnline);
		}else{
			userOnline = uos.get(0);
			utilsContext.getUserStateUtils().set(userOnline);
		}
		
		
		userOnline = utilsContext.getUserStateUtils().getCurrent();
		
		utilsContext.getUserStateUtils().touchCurrent();
		SegmentCache sessionCache = utilsContext.getUserStateUtils().getSessionCache();
		
		/*sessionCache.put("key", " value");
		
		String val = utilsContext.getUserStateUtils().getObject("key");
		
		for(int i = 0;i < 100;i++)
		{
			sessionCache.put("key" + i, " value" + i + ":" + "lskdja;lskdjfaslkjalskjflkjd" + UUID.randomUUID().toString());
		}*/
		for(int i = 0;i < 100;i++){
			utilsContext.getUserStateUtils().touchCurrent();
		}
		utilsContext.getUserStateUtils().clear(userOnline);
		System.out.println("");
		
	}

}
