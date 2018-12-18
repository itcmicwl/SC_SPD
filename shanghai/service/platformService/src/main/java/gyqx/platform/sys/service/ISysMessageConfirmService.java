package gyqx.platform.sys.service;


import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysMessageConfirm;


public interface ISysMessageConfirmService 
{
	SysMessageConfirm get(String id) throws Exception ;
	SysMessageConfirm add(SysMessageConfirm sysMessageConfirm) throws Exception ;
	SysMessageConfirm update(SysMessageConfirm sysMessageConfirm) throws Exception;
	int getProUserMsgCount(String userId,String projectCode) throws Exception;
	int setMsgRead(SysMessageConfirm sysMessageConfirm) throws Exception;
	List<SysMessageConfirm> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<SysMessageConfirm> list(SysMessageConfirm bean) throws Exception;
	
	QueryResult<SysMessageConfirm> listByPage(QueryInfo<SysMessageConfirm> queryInfo) throws Exception;	

}


