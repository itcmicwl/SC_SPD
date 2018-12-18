package permission;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import common.db.SimpleDao;
import common.utils.ScanUtils;
import common.utils.UtilsContext;

import tools.toDb.ImportModuleFunctionUtil;
import tools.toDb.SysProject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })

@Transactional(transactionManager="txManager")
//@Rollback
@Commit
public class ImportModuleFunction 
{
	@Autowired
	private SimpleDao dao;
	
	@Resource
	private UtilsContext utilsContext;
	
	@Before
	public void setUp() throws Exception 
	{
	}
	@Test
	public void test() throws Exception 
	{		

		SysProject sp = new SysProject();
		sp.setId("gyqx.platform");
		sp.setCname("系统设计平台");
		sp.setEname("gyqx.platform");
		sp.setCode("gyqx.platform");
		sp.setPid("gyqx");

		ImportModuleFunctionUtil util = new ImportModuleFunctionUtil(dao,sp);
		util.importData();		
		
		System.out.println("ok!");
	}
	
}
