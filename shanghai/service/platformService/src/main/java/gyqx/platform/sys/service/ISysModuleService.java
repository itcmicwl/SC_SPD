package gyqx.platform.sys.service;

import java.util.List;
import java.util.Map;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysModule;
import gyqx.platform.po.SysProject;
import gyqx.platform.sys.vo.ElTreeVo;
import gyqx.platform.sys.vo.SysModuleVo;

public interface ISysModuleService   
{   
	
	public  QueryResult<SysModuleVo> queryModuleVos(QueryInfo<SysModuleVo> queryInfo) throws Exception;	

	public  QueryResult<SysModuleVo> queryModuleVos(Map<String,Object> map) throws Exception;	
	public QueryResult<SysProject> getSysProjectOptions(Map<String,Object> map) throws Exception;
	public SysModule insert(SysModule mo)throws Exception;
	public SysModule update(SysModule mo)throws Exception;

	public int delModule(SysModule mo)throws Exception;


	List<ElTreeVo> getSysModuleTree() throws Exception;

}
