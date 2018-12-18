package gyqx.platform.permission.dao.mapper;

import java.util.List;

import gyqx.platform.permission.vo.UserRoleVo;
import gyqx.platform.po.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface ISysUserRoleMapper {
	int update(SysUserRole bean);
	int insert(SysUserRole bean);
	List<SysUserRole> list(SysUserRole queryInfo) ;
	List<UserRoleVo> userRoleLst (UserRoleVo ur);
	int insertBatch(List<SysUserRole> lst);
	int delByUserId (@Param("userId") String userId );
}

