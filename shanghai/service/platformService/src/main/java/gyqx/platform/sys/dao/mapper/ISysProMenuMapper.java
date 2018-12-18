package gyqx.platform.sys.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.platform.sys.vo.SysProjectMenuVo;

public interface ISysProMenuMapper {
	List<SysProjectMenuVo> getSysProMenuList(SysProjectMenuVo sysProjectMenuVo);
	List<SysProjectMenuVo> getParentSysProMenuList(SysProjectMenuVo sysProjectMenuVo);
	int insertProMenu(SysProjectMenuVo sysProjectMenuVo);
	int updateProMenu(SysProjectMenuVo sysProjectMenuVo);
	Integer existProMenuCode(SysProjectMenuVo sysProjectMenuVo);
	int deleteProMenu(@Param("id") String code);
	SysProjectMenuVo getById(@Param("id") String id);
	List<SysProjectMenuVo> sysMenuKindList(@Param("projectCode") String projectCode);
}
