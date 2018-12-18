package gyqx.platform.permission.dao.mapper;

import java.util.List;
import java.util.Map;

import common.db.query.QueryInfo;
import gyqx.platform.permission.vo.OrgAdminUser;
import gyqx.platform.permission.vo.UserRoleVo;

public interface IOrgAdminUserMapper {

	List<OrgAdminUser> list(Map param); //String corpId,String orgId) ;


	
	List<OrgAdminUser> listOrgUsers(Map param); 

}

