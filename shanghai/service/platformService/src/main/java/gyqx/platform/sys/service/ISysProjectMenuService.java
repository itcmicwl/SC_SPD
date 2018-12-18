package gyqx.platform.sys.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysProjectMenuVo;

public interface ISysProjectMenuService {
	QueryResult<SysProjectMenuVo> getSysProMenuList(QueryInfo<SysProjectMenuVo> queryInfo) throws Exception;
	List<ElTreeVo> getParentSysProMenuList(SysProjectMenuVo SysProjectMenuVo) throws Exception;
	int insertProMenu(SysProjectMenuVo sysProjectMenuVo) throws Exception;
	int updateProMenu(SysProjectMenuVo sysProjectMenuVo) throws Exception;
	int deleteProMenu(String id) throws Exception;
	boolean existProMenuCode(SysProjectMenuVo sysProjectMenuVo) throws Exception;
	List<SysProjectMenuVo> getSysProjectMenuTree(SysProjectMenuVo sysProjectMenuVo) throws Exception;
	List<SysProjectMenuVo> sysMenuKindList(String projectCode) throws Exception;
}
