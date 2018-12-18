package fun;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.UtilsContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class AtomIdTest {

	@Resource
	private SimpleDao dao;
	@Resource
	private UtilsContext context;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws InterruptedException {
		Thread[] threads = new Thread[10];
		for(int rhi = 0; rhi < threads.length; rhi++){
			threads[rhi] = new Thread(new Runnable(){
					@Override
					public void run() {
						for(int i = 0 ; i < 20; i++){
							//String id = context.getSysAtomUtil().newValue("ttt");
							String id = context.getSysAtomUtil().newId("ttt").prefix("a","b","c").suffix(i,2).get();
						}
					}
				});
			threads[rhi].start();
		}
		
		for(int rhi = 0; rhi < threads.length; rhi++){
			threads[rhi].join();
		}
	}
	//@Test
	public void testParams() throws UnknownHostException {
		//String addr = InetAddress.getLocalHost().getHostAddress();
		SysAtomUtil atomUtil = context.getSysAtomUtil();
		String id = context.getSysAtomUtil().newId("ttt").prefix("a","b","c").suffix(1,2).get();
		id = context.getSysAtomUtil().newId("ttt").prefix("a","b","c").suffix(1,2).get();
		id = context.getSysAtomUtil().newId("ttt").prefix("a","b","c").suffix(1,2).get();
		
		id = atomUtil.newId("purConfirmId").prefix("xxx").get();
		id = atomUtil.newId("purConfirmId").prefix("xxx").get();
		id = atomUtil.newId("purConfirmId").prefix("xxx").get();
		
		System.out.println(id);
	}
	
	

}
