package gyqx.platform.permission.dao;


import java.util.List;
import javax.annotation.Resource;

import gyqx.platform.permission.dao.mapper.ISysRoleMapper;
import org.springframework.stereotype.Repository;
import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;

import gyqx.platform.po.SysRole;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;

@Repository
public class SysRoleDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private ISysRoleMapper mapper;

	public SysRole get(String id)
	{
		return dao.get(SysRole.class, id);	
	}	

	public SysRole insertAndGet(SysRole sysRole) throws Exception 
	{
		return dao.insertAndGet(sysRole);
	}

	public SysRole updateAndGet(SysRole sysRole) throws Exception {
		return dao.updateAndGet(sysRole);
	}
	public int updateFields(SysRole sysRole,String fieldNames) throws Exception
	{
		return dao.updateFields(sysRole, fieldNames);
	}
	public int updateNotNullFields(SysRole sysRole) throws Exception
	{
		return dao.updateNotNullFields(sysRole);
	}

	public void delete(SysRole sysRole) throws Exception {
		dao.delete(sysRole);
	}
	
	public SysRole getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (SysRole) dao.getByFieldName(SysRole.class, fieldName, fieldValue);
	}
	
	public List<SysRole> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<SysRole>) dao.queryBeanList(SysRole.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(SysRole.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<SysRole> list(SysRole qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
}


