package gyqx.platform.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.platform.po.SysOrg;
import gyqx.platform.po.SysUser;
import gyqx.platform.po.SysUserLogin;
import gyqx.platform.sys.dao.SysUserDao;
import gyqx.platform.sys.dao.SysUserLoginDao;
import gyqx.platform.sys.service.ILoginService;
import gyqx.platform.sys.service.ISysUserService;
import gyqx.platform.sys.vo.UserBaseInfoVo;

@Service
public class SysUserService implements ISysUserService {
	@Resource
	private SysUserDao sysUserDao;

	@Resource
	private SysUserLoginDao sysUserLoginDao;

	@Resource
	private SysConfigUtil configUtil;

	@Resource
	private SysAtomUtil atomUtil;

	@Resource
	private ILoginService loginService;
	@Resource
	private SimpleDao simpleDao;
	@Override
	public UserBaseInfoVo addUser(UserBaseInfoVo user) throws Exception {
		SysUser u = new SysUser();
		String id = configUtil.getValue("sys.name")+":"+atomUtil.newValue("user_id");
		u.setId(id);
		u.setKind(user.getKind());
		u.setUserCode(user.getUserCode());
		u.setLastUpdateDatetime(new java.sql.Date(new Date().getTime()));
		
		// 查重处理
		   long  num=	simpleDao.queryForObject("select count(*) from sys_user where user_code= ? ", Long.class, user.getUserCode());
		   if(num>0){
			        ValidateException.Throw("userCode", user.getUserCode() +  "已存在！",user.getUserCode() );
				   }
		 
		
		
		SysUserLogin userLogin = loginService.newUserLogin(u);
		sysUserLoginDao.insert(userLogin); // 添加到用户登陆信息表
		u = sysUserDao.addUser(u); // 添加到用户表
		String userOrgId = configUtil.getValue("sys.name")+":"+atomUtil.newValue("user_id");
		user.setId(userOrgId);
		user.setUserId(id);
		
		   //通过orgCode找到orgId ，必须通过orgId 关联机构
	      SysOrg org= (SysOrg) simpleDao.getByFieldName(SysOrg.class, "code", user.getOrgCode());
	      user.setOrgId(org.getId());
		  user.setOrgId(org.getId());
		
		sysUserDao.insertUserOrg(user);
		return user;
	}

	@Override
	public SysUser updateUser(SysUser user) throws Exception {
		user.setLastUpdateDatetime(new java.sql.Date(new Date().getTime()));
		return sysUserDao.updateUser(user);
	}

	@Override
	public void setUserStatus(UserBaseInfoVo user) throws Exception {
		user.setLastUpdateDatetime(new Date());
		sysUserDao.setUserStatus(user);
	}
	
	@Override
	public QueryResult<UserBaseInfoVo> getUserList(QueryInfo<UserBaseInfoVo> queryInfo) throws Exception {
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(sysUserDao.getUserList(queryInfo));
//		QueryResult<UserBaseInfoVo> lstUser = new QueryResult<UserBaseInfoVo>();
//		PageInfo<UserBaseInfoVo> userPage =sysUserDao.getUserList(queryInfo);
//		lstUser.setData(userPage.getList());
//		lstUser.setPageNum(userPage.getPageNum());
//		lstUser.setPageSize(userPage.getPageSize());
//		lstUser.setTotal(userPage.getTotal());
//		return lstUser;
	}
	
	public void updateUserBaseInfo(UserBaseInfoVo user) throws Exception {
		// 查重处理
		   long  num=	simpleDao.queryForObject("select count(*) from sys_user where user_code= ? and id<> ?", Long.class, user.getUserCode(),user.getUserId());
		   if(num>0){
			        ValidateException.Throw("userCode", user.getUserCode() +  "已存在！",user.getUserCode() );
				   }
		   //通过orgCode找到orgId ，必须通过orgId 关联机构
		      SysOrg org= (SysOrg) simpleDao.getByFieldName(SysOrg.class, "code", user.getOrgCode());
		      user.setOrgId(org.getId());
		      user.setLastUpdateDatetime(new Date());
		sysUserDao.updateUserBaseInfo(user);
	}

	@Override
	public UserBaseInfoVo getUserByUid(String uid) throws Exception {
		return sysUserDao.getUserByUid(uid);
	}
	
	//查询所有用户
	@Override
	public List<UserBaseInfoVo> getAllUser(Map map) {
		
		return sysUserDao.getAllUser(map);
	}

	@Override
	public List<UserBaseInfoVo> getUsers(Set<String> ids) {
		return sysUserDao.getUsers(ids);
	}
}
