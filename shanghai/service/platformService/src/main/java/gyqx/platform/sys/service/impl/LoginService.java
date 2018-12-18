package gyqx.platform.sys.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.base.Hash;
import common.base.RandomCode;
import common.db.exception.SqlInjectException;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import common.web.UserOnlineInfo;
import gyqx.common.exception.LoginErrorException;
import gyqx.common.exception.LoginPasswordException;
import gyqx.common.exception.NoUserException;
import gyqx.platform.sys.dao.SysUserDao;
import gyqx.platform.sys.dao.SysUserLoginDao;
import gyqx.platform.po.*;
import gyqx.platform.sys.dao.*;
import gyqx.platform.sys.service.ILoginService;

@Service
public class LoginService implements ILoginService
{
	@Resource
	private SysUserLoginDao sysUserLoginDao;
	
	@Resource
	private SysUserDao sysUserDao;
	
	@Resource
	private SysConfigUtil configUtil;
	
	@Resource
	private UserOnlineStateUtils stateUtils;
	
	@Resource
	private SysAtomUtil atomUtil;
	
	public SysUserLogin checkUserLoginCode(String userLoginCode) throws NoUserException
	{		
		SysUserLogin userInfo= null;
		try {
			userInfo = sysUserLoginDao.getByUserLoginCode(userLoginCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(userInfo == null)
			throw new NoUserException("");
		return userInfo;
	}
	
	public SysUser login(String userLoginCode, String hashCode) throws Exception
	{
		SysUserLogin userInfo = sysUserLoginDao.getByUserLoginCode(userLoginCode);
		if(userInfo == null)
			throw new NoUserException("");
		
		if (userInfo.getFlag().equals("0")  && userInfo.getErrorCount() > 3 
				&& (new Date().getTime()) - userInfo.getLastErrorDatetime().getTime() < 10 *60 * 1000)
            throw new LoginErrorException("超过重试次数！");
		
		
		SysUser userBaseInfo = null;
		if(Hash.hash(hashCode,userInfo.getSalt()).equals( userInfo.getHash()))
		{
		    userBaseInfo = sysUserDao.get(userInfo.getUid());
		    
//		    userInfo.setErrorCount(0);
//            userInfo.setLastLoginDatetime(new java.sql.Date(new Date().getTime()));
//            userInfo.setFlag("1");
            
            //sysUserLoginDao.update(userInfo);
            if(sysUserLoginDao.updateLogin(userInfo, "ok")<1)
            	throw new Exception("更新用户登录信息时，影响行数小于1");
            appendDbLoginLog(userBaseInfo);
            
		}else{
//			userInfo.setErrorCount(userInfo.getErrorCount() + 1);
//            userInfo.setLastErrorDatetime(new java.sql.Date(new Date().getTime()));
//            userInfo.setFlag("0");
            //sysUserLoginDao.update(userInfo);
            if(sysUserLoginDao.updateLogin(userInfo, "false")<1)
            	throw new Exception("更新用户登录信息时，影响行数小于1");
			throw new LoginPasswordException("用户密码出错！");
		}

		return userBaseInfo;
	}
	
	private void appendDbLoginLog(SysUser userBaseInfo )
	{
		/// 写日志
	}

	@Override
	public String initPassword(String userLoginCode) throws Exception {
		SysUserLogin userInfo = sysUserLoginDao.getByUserLoginCode(userLoginCode);
		if(userInfo == null) throw new NoUserException("无此用户信息");
		String randomCode = RandomCode.getRandomCode();
		String webSalt = Hash.hash(randomCode);
		String salt = Hash.hash(RandomCode.getRandomCode());
		String defaultPassword = configUtil.getValue("user.default.password");
		String hash = Hash.hash(Hash.hash(defaultPassword, webSalt),salt);
		userInfo.setWebSalt(webSalt);
		userInfo.setSalt(salt);
		userInfo.setHash(hash);
		sysUserLoginDao.update(userInfo);
		return defaultPassword;
	}

	@Override
	public SysUserLogin newUserLogin(SysUser user) {
		
		SysUserLogin userLogin = new  SysUserLogin();
		String id = configUtil.getValue("sys.name")+":"+atomUtil.newValue("user.login");
		
		userLogin.setId(id);
		userLogin.setUid(user.getId());
		userLogin.setUserLoginCode(user.getUserCode());
		
		String randomCode = RandomCode.getRandomCode();
		
		String webSalt = Hash.hash(randomCode);
		String salt = Hash.hash(RandomCode.getRandomCode());
		String defaultPassword = configUtil.getValue("user.default.password");
		
		String hash = Hash.hash(Hash.hash(defaultPassword, webSalt),salt);
		
		userLogin.setWebSalt(webSalt);
		userLogin.setSalt(salt);
		userLogin.setHash(hash);
		userLogin.setFlag("0");
		
		return userLogin;
	}

	@Override
	public SysUser changePassword(String userLoginCode, String newWebSalt, String oldHash, String newHash)
			throws Exception {
		SysUser user = login(userLoginCode,oldHash);
		
		SysUserLogin userInfo = sysUserLoginDao.getByUserLoginCode(userLoginCode);
		
		userInfo.setWebSalt(newWebSalt);
		String newSalt = RandomCode.getRandomCode();
		String hash = Hash.hash(newHash,newSalt);
		userInfo.setSalt(newSalt);
		userInfo.setHash(hash);
		
		sysUserLoginDao.update(userInfo);
		
		return user;
	}

	@Override
	public void exit() throws Exception {
		
		UserOnlineInfo uo = stateUtils.getCurrent();  	
		if(uo == null)
			return;
		SysUserLogin userInfo =  sysUserLoginDao.getByUserId(uo.getUserId());
		
		// append logout log!
		
		stateUtils.clear(uo);
	}

}
