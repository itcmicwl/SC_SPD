package gyqx.platform.permission.dao.mapper;

import java.util.List;

import gyqx.platform.permission.vo.MenuPageviewPermission;
import gyqx.platform.sys.vo.ProjectMenuVo;
import org.apache.ibatis.annotations.Param;

public interface IMenuPageviewConfigMapper 
{
	MenuPageviewPermission get(String menuId);
	List<ProjectMenuVo> getSuperAdminMenus(@Param("projectCode") String projectCode);
    List<ProjectMenuVo> getUserMenus(@Param("projectCode") String projectCode, @Param("userId") String userId);
}
