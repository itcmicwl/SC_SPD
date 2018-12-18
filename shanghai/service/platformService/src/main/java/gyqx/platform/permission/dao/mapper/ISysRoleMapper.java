package gyqx.platform.permission.dao.mapper;

import java.util.List;

import gyqx.platform.po.SysRole;
 
public interface ISysRoleMapper {
	int update(SysRole bean);
	int insert(SysRole bean);
	List<SysRole> list(SysRole queryInfo) ;
}

