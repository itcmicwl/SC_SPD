package gyqx.platform.sys.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysProject;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysProjectVo;

public interface ISysProjectService {
	QueryResult<SysProjectVo> getSysProjectList(QueryInfo<SysProject> queryInfo) throws Exception;
	List<SysProject> getParentSysProList(QueryInfo<SysProject> queryInfo) throws Exception;
	SysProject getSysProjectByid(String id) throws Exception;
	int insertSysProject(SysProject sysProject) throws Exception;
	int setSysProject(SysProject sysconfig) throws Exception;
	int delSysProjectByCode(String code);
	boolean existSysProject(SysProject sysconfig) throws Exception;
	List<ElTreeVo> getSysProjectTree() throws Exception;
}
