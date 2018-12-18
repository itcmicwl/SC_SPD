package gyqx.platform.sys.service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysConfig;

public interface ISysConfigService {
	QueryResult<SysConfig> getSysConfigList(QueryInfo<SysConfig> queryInfo) throws Exception;
	SysConfig getSysConfigByid(String id) throws Exception;
	SysConfig getSysConfigByName(String name) throws Exception;
	int insertSysConfig(SysConfig sysConfig) throws Exception;
	int setStatus(SysConfig sysconfig) throws Exception;
	int setSysConfig(SysConfig sysconfig) throws Exception;
	boolean existSysConfig(SysConfig sysconfig) throws Exception;
}
