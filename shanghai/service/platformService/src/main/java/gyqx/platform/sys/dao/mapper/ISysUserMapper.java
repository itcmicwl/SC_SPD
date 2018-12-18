package gyqx.platform.sys.dao.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gyqx.platform.sys.vo.UserBaseInfoVo;
public interface ISysUserMapper {
	List<UserBaseInfoVo> getUserList(UserBaseInfoVo userVo);
	void insertUserOrg(UserBaseInfoVo userVo);
	void setUserStatus(UserBaseInfoVo userVo);
	void updateUserBaseInfo(UserBaseInfoVo userVo);
	UserBaseInfoVo getUserByUid(String uid);
	//查询登录用户所在科室下所有的用户
	List<UserBaseInfoVo> getAllUser(Map map);
	List<UserBaseInfoVo> getUsers(Set<String> ids);
}
