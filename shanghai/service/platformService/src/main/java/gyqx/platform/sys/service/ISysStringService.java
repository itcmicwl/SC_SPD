package gyqx.platform.sys.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysString;
import gyqx.platform.sys.vo.SysStringVo;

public interface ISysStringService {
	
	QueryResult<SysString> getSysStringList(QueryInfo<SysString> queryInfo) throws Exception;
	
	QueryResult<SysStringVo> getSysStringVoList(QueryInfo<SysStringVo> sysStringVo) throws Exception;
	
	SysString getSysStringByid(String id) throws Exception;
	List<SysString> getSysStringAll() throws Exception;
//	void insertSysString(SysString sysString) throws Exception;
	
	int insertSysString(SysString sysString) throws Exception;
	
	int setStatus(SysString sysString) throws Exception;
	
	int setSysString(SysString sysString) throws Exception;
	
	boolean existSysString(SysString sysString) throws Exception;
	
	void deleteSysString(SysString sysString) throws Exception;
	
}
