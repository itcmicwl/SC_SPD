package gyqx.platform.sys.service;

import gyqx.common.exception.NoUserException;
import gyqx.platform.po.*;

public interface ILoginService {
	SysUserLogin checkUserLoginCode(String userLoginCode) throws NoUserException;
	SysUser login(String userLoginCode, String hashCode) throws Exception;
	SysUser changePassword(String userLoginCode,String newWebSalt, String hash,String newHash) throws Exception;
	SysUserLogin newUserLogin(SysUser user) ;
	String initPassword(String userLoginCode) throws Exception;
	void exit() throws Exception;
}
