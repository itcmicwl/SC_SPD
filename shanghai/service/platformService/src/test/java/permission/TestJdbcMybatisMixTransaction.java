package permission;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import gyqx.platform.permission.dao.mapper.IMenuPageviewConfigMapper;
import gyqx.platform.permission.service.IMenuPageviewItemsConfigService;
import gyqx.platform.permission.vo.MenuPageviewPermission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })

@Transactional(transactionManager="txManager")
//@Rollback
@Commit
public class TestJdbcMybatisMixTransaction 
{
	@Autowired
	private IMenuPageviewItemsConfigService IMenuPageviewItemsConfigservice;
	
	@Before
	public void setUp() throws Exception 
	{
		
	}
	@Test
	public void test() throws Exception 
	{
		MenuPageviewPermission pvitems =IMenuPageviewItemsConfigservice.getPageviewElements("pm00028");
		System.out.println(pvitems);
	}
}
