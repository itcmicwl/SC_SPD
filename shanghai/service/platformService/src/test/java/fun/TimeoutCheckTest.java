package fun;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import common.base.TimeoutCheckConcurrentMap;

public class TimeoutCheckTest {

	TimeoutCheckConcurrentMap<String,String> map;
	@Before
	public void setUp() throws Exception {
		map = new TimeoutCheckConcurrentMap<>();
	}
	private int v;

	@Test
	public void test() throws InterruptedException 
	{
		Thread[] threads = new Thread[1];
		for(int rhi = 0; rhi < threads.length; rhi++){
			v = rhi;
			threads[rhi] = new Thread(new Tester(rhi,map));
			threads[rhi].start();		
		}
		
		for(int rhi = 0; rhi < threads.length; rhi++){
			threads[rhi].join();
		}
		
	}

}
class Tester implements Runnable
{
	int num;
	TimeoutCheckConcurrentMap<String,String> map;
	public Tester(int num,TimeoutCheckConcurrentMap<String,String> map)
	{
		this.num = num;
		this.map = map;
	}
	@Override
	public void run() {
		for(int i = 0 ; i < 10*1; i++){
			int v = ( num * 10000000 +i);
			map.set("key" +v,"val" + v);
			try {
				Thread.sleep(11000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String rv = map.get("key" +v);
			/*try{
				Assert.assertEquals("val" + v, rv);
			}catch(Exception ex)
			{
				System.out.close();
			}*/
		}
		
	}
	
}
