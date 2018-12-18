package gyqx.platform.permission.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.permission.vo.UserRoleVo;
import gyqx.platform.po.SysUserRole;

public interface ISysUserRoleService 
{
	SysUserRole get(String id) throws Exception ;
	SysUserRole add(SysUserRole sysUserRole) throws Exception ;
	SysUserRole update(SysUserRole sysUserRole) throws Exception;
	List<UserRoleVo> userRoleLst (UserRoleVo ur) throws Exception;
	List<SysUserRole> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<SysUserRole> list(SysUserRole bean) throws Exception;
	
	QueryResult<SysUserRole> listByPage(QueryInfo<SysUserRole> queryInfo) throws Exception;
	int insertBatch(List<SysUserRole> lst) throws Exception;
	int delByUserId (String userId ) throws Exception;
	int addUserRoles(List<String> lst,String userId) throws Exception;
}


