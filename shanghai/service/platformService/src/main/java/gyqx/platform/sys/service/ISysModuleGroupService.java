package gyqx.platform.sys.service;

import java.util.Map;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysModuleGroup;
import gyqx.platform.po.SysProject;
import gyqx.platform.sys.vo.SysModuleGroupVo;

public interface ISysModuleGroupService {
	public  QueryResult<SysModuleGroupVo> queryModuleGroupVos(QueryInfo<SysModuleGroupVo> queryInfo) throws Exception;	


	public SysModuleGroup insert(SysModuleGroup mo)throws Exception;
	public SysModuleGroup update(SysModuleGroup mo)throws Exception;

	public int delModuleGroup(SysModuleGroup mo)throws Exception;
}
