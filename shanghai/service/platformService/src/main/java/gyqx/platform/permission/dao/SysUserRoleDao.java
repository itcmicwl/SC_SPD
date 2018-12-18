package gyqx.platform.permission.dao;


import java.util.List;
import javax.annotation.Resource;

import com.mysql.jdbc.StringUtils;
import gyqx.platform.permission.dao.mapper.ISysUserRoleMapper;
import gyqx.platform.permission.vo.UserRoleVo;
import org.springframework.stereotype.Repository;
import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;

import gyqx.platform.po.SysUserRole;


@Repository
public class SysUserRoleDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private ISysUserRoleMapper mapper;

	public SysUserRole get(String id)
	{
		return dao.get(SysUserRole.class, id);	
	}	

	public SysUserRole insertAndGet(SysUserRole sysUserRole) throws Exception 
	{
		return dao.insertAndGet(sysUserRole);
	}

	public SysUserRole updateAndGet(SysUserRole sysUserRole) throws Exception {
		return dao.updateAndGet(sysUserRole);
	}
	public int updateFields(SysUserRole sysUserRole,String fieldNames) throws Exception
	{
		return dao.updateFields(sysUserRole, fieldNames);
	}
	public int updateNotNullFields(SysUserRole sysUserRole) throws Exception
	{
		return dao.updateNotNullFields(sysUserRole);
	}

	public void delete(SysUserRole sysUserRole) throws Exception {
		dao.delete(sysUserRole);
	}
	
	public SysUserRole getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (SysUserRole) dao.getByFieldName(SysUserRole.class, fieldName, fieldValue);
	}
	
	public List<SysUserRole> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<SysUserRole>) dao.queryBeanList(SysUserRole.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(SysUserRole.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<SysUserRole> list(SysUserRole qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
	public List<UserRoleVo> userRoleLst (UserRoleVo ur){
		return mapper.userRoleLst(ur);
	}
	public int insertBatch(List<SysUserRole> lst){
		return mapper.insertBatch(lst);
	}
	public int delByUserId (String userId ) throws Exception{
		if(StringUtils.isNullOrEmpty(userId)){
			throw new Exception("用户ID不能为空");
		}
		return mapper.delByUserId(userId);
	}
}


