package gyqx.platform.sys.service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.sys.vo.SysModuleFunctionVo;

public interface ISysModuleFunctionService {
	QueryResult<SysModuleFunctionVo> getSysModuleFunctionVoList(QueryInfo<SysModuleFunctionVo> queryInfo);
	SysModuleFunctionVo getSysModuleFunctionVoByid(SysModuleFunctionVo sysModuleFunctionVo);
	boolean existCodeInSys(SysModuleFunctionVo sysModuleFunctionVo);
	boolean existNameInModule(SysModuleFunctionVo sysModuleFunctionVo);
	int insertSysModuleFunctionVo(SysModuleFunctionVo sysModuleFunctionVo);
	int setSysModuleFunctionVo(SysModuleFunctionVo sysModuleFunctionVo);
	int delSysModuleFunction(String id);
}
