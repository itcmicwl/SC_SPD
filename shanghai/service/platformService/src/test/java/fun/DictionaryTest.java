package fun;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.utils.DictionaryUtils.DictionaryValue;
import common.utils.UtilsContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class DictionaryTest 
{
	@Autowired
	private UtilsContext utils;

	@Before
	public void setUp() throws Exception 
	{
		
	}

	@Test
	public void test() 
	{
		List<DictionaryValue> dictvalues = utils.getDictUtils().getDictItems("CCTJ");
		for(DictionaryValue dv: dictvalues)
		{
			System.out.println(dv);
		}
		DictionaryValue dv = utils.getDictUtils().getDictItem("CCTJ","阴凉、避光");
		System.out.println(dv);
		
	}

}
