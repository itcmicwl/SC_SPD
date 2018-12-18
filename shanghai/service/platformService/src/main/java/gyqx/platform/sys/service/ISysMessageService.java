package gyqx.platform.sys.service;


import java.util.Date;
import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysMessage;
import gyqx.platform.sys.vo.SysMessageVo;


public interface ISysMessageService 
{
	SysMessage get(String id) throws Exception ;
	SysMessage add(SysMessage sysMessage) throws Exception ;
	SysMessage update(SysMessage sysMessage) throws Exception;
	List<SysMessageVo> getProUserMsgList(SysMessageVo sysMessageVo) throws Exception;
	List<SysMessage> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
	
	List<SysMessageVo> list(SysMessageVo bean) throws Exception;
	List<SysMessage> getUserMsg(String projectCode,String userId) throws Exception;
	QueryResult<SysMessageVo> listByPage(QueryInfo<SysMessageVo> queryInfo) throws Exception;

    List<SysMessageVo> listUsersMsg(String projectCode, Date beginDate, Date endDate, boolean containsRead);

    boolean validateApp(String appId, String accessKey);
}


