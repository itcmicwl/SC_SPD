package permission;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import gyqx.platform.sys.vo.SysProjectMenuVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import common.base.LV;
import common.db.SimpleDao;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;
import gyqx.platform.permission.service.IAdminPermissionConfigService;
import gyqx.platform.permission.vo.AdminRoleConfig;
import gyqx.platform.permission.vo.CompanyInfo;
import gyqx.platform.permission.vo.MenuItem;
import gyqx.platform.permission.vo.OrgAdminUser;
import gyqx.platform.permission.vo.UserOrg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })

@Transactional(transactionManager="txManager")
//@Rollback
@Commit
public class AdminConfigTest 
{
	
	@Autowired
	private IAdminPermissionConfigService adminPermissionConfigService;	

	@Resource
	private UtilsContext utilsContext;
	
	@Before
	public void setUp() throws Exception 
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
	}

	@Test
	public void test() throws Exception 
	{
		List<CompanyInfo> listci = adminPermissionConfigService.listCompanies();
		String corpId="company-008";
		String orgId ="org-25";
		List<OrgAdminUser> listoau = adminPermissionConfigService.listOrgAdminUser(corpId,orgId);
		
		List<UserOrg> userOrgs = adminPermissionConfigService.listOrg(corpId);
		
		List<OrgAdminUser> list1 = adminPermissionConfigService.listCompanyAdmin(corpId);
		
		List<SysProjectMenuVo> 		list2 = adminPermissionConfigService.listMenu();
		String userId="user-00007";
		List<String> adclist =	adminPermissionConfigService.listAdminOrgsConfig(userId);
		
		List<AdminRoleConfig> list3=adminPermissionConfigService.listAdminRolesConfig( corpId, userId);
		String flag = "yes";
		
		//adminPermissionConfigService.setAdminFlag( corpId,  userId, flag);
		List<String> orgIds = LV.New("ss","ss1");
		adminPermissionConfigService.setAdminOrgsConfig(userId, orgIds);
		
		System.out.println("ok");

	}

}
