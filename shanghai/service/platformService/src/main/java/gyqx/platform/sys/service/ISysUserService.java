package gyqx.platform.sys.service;



import java.util.List;
import java.util.Map;
import java.util.Set;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysUser;
import gyqx.platform.sys.vo.UserBaseInfoVo;

public interface ISysUserService 
{
	public UserBaseInfoVo addUser(UserBaseInfoVo user) throws Exception;
	
	public SysUser updateUser(SysUser user) throws Exception;
	
	public void setUserStatus(UserBaseInfoVo user) throws Exception;
	
	public QueryResult<UserBaseInfoVo> getUserList(QueryInfo<UserBaseInfoVo> queryInfo) throws Exception;
	public void updateUserBaseInfo(UserBaseInfoVo user) throws Exception;
	public UserBaseInfoVo getUserByUid(String uid) throws Exception;

	public List<UserBaseInfoVo> getAllUser(Map map);
	public List<UserBaseInfoVo> getUsers(Set<String> ids);
}
