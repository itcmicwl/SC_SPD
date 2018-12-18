package permission;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.db.SimpleDao;
import common.security.matcher.WildcardPathMatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class UrlPathMatcherTest 
{
	@Autowired
	private SimpleDao dao;
	@Before
	public void setUp() throws Exception {
		List<String> urls = dao.queryValues("select url from sys_module_function_design", String.class);
		
		List<String> newurls = new  ArrayList<>();
		for(String s:urls)
		{
			
			newurls.add(s);
			String[] parts = s.split("/");
			String seg1 = parts[1];
			String seg2 = parts[2];
			for(int i = 0; i< 10;i++)
			{
				parts[1] = seg1 + i;
				for(int j = 0; j <10;j++)
				{
					parts[2] = seg2 + j;
					String nurl = buildPattern(parts,0);
					newurls.add(s);
					
				}
			}
		}
		for(String s:newurls)
		{
			wptree.addPath(s);
		}
		urlSamples = urls;
	}
	private WildcardPathMatcher wptree = new WildcardPathMatcher();
	
	private List<String> urlSamples;
	
	@Test
	public void test() 
	{

		long t = System.currentTimeMillis();
		for(int i = 0; i< 1000;i++){
			for(String url : urlSamples)
			{
				assertTrue(wptree.match(url));
			}
		}
		t = System.currentTimeMillis() -t;
		System.out.println("ms:" + t);
		
	}
	private String buildPattern(String[] parts,int step)
	{
		StringBuilder sb = new StringBuilder(32);
		sb.append(parts[step]);
		for(int i = step + 1;i <parts.length;i++ )
		{
			sb.append(WildcardPathMatcher.PART_DIVIDER_TOKEN).append(parts[i]);
		}
		return sb.toString();
	}

}
