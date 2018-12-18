package gyqx.platform.permission.service.impl;

import javax.annotation.Resource;

import gyqx.platform.permission.dao.SysRoleDao;
import gyqx.platform.permission.service.ISysRoleService;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;
import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysRole;

@Service
public class SysRoleService implements ISysRoleService {
	
	@Resource
	private SysRoleDao dao;
	
	public SysRole get(String id)
	{
		return dao.get( id);	
	}	

	public SysRole add(SysRole sysRole) throws Exception 
	{
		return dao.insertAndGet(sysRole);
	}
		
	public SysRole update(SysRole sysRole) throws Exception {
		return dao.updateAndGet(sysRole);
	}
	public int updateFields(SysRole sysRole,String fieldNames) throws Exception
	{
		return dao.updateFields(sysRole, fieldNames);
	}

	public void delete(SysRole sysRole) throws Exception {
		dao.delete(sysRole);
	}
	
	public SysRole getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (SysRole) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<SysRole> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<SysRole>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<SysRole> list(SysRole bean) throws Exception
	{
		return (List<SysRole>) dao.list(bean);
	}	
	
	public QueryResult<SysRole> listByPage(QueryInfo<SysRole> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

}


