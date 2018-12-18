package gyqx.platform.sys.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.platform.po.SysProject;
import gyqx.platform.sys.vo.SysProjectVo;

public interface ISysProjectMapper {
	List<SysProjectVo> getSysProjectList(SysProject sysProject);
	List<SysProject> getParentSysProList(SysProject sysProject);
	SysProject getSysProjectByid(@Param("id") String id);
	int insertSysProject(SysProject sysProject);
	int setSysProject(SysProject sysProject);
	Integer existSysProject(SysProject sysProject);
	int delSysProjectByCode(@Param("code") String code);
}
