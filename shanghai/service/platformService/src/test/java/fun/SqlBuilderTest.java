package fun;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.db.SimpleDao;
import tools.codeBuilder.sql.SqlBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context.xml" })
public class SqlBuilderTest 
{
	@Resource
	private SimpleDao dao;

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void test() throws Exception
	{
		SqlBuilder builder = new SqlBuilder(dao);
		HashMap<String,Object> config = new HashMap<>();
		config.put("use_named_param", "true");
		
		String sql = builder.buildUpdate("bas_mfrs_info", config);
		System.out.println(sql);
		
		sql = builder.buildInsert("bas_mfrs_info", config);
		System.out.println(sql);
	}

}
