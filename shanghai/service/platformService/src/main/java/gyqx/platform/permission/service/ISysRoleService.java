package gyqx.platform.permission.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysRole;

public interface ISysRoleService 
{
	SysRole get(String id) throws Exception ;
	SysRole add(SysRole sysRole) throws Exception ;
	SysRole update(SysRole sysRole) throws Exception;
	
	List<SysRole> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<SysRole> list(SysRole bean) throws Exception;
	
	QueryResult<SysRole> listByPage(QueryInfo<SysRole> queryInfo) throws Exception;	

}


