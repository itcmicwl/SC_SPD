package gyqx.platform.sys.dao;


import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import common.db.IBean;
import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.platform.po.SysUser;
import gyqx.platform.sys.dao.mapper.ISysUserMapper;
import gyqx.platform.sys.vo.UserBaseInfoVo;

@Repository
public class SysUserDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private ISysUserMapper mapper;
	public SysUser get(String id)
	{
		return dao.get(SysUser.class, id);	
	}	

	public SysUser addUser(SysUser user) throws Exception 
	{
		return dao.insertAndGet(user);
	}

	public SysUser updateUser(SysUser user) throws Exception {
		return dao.updateAndGet(user);

	}
	public int updateFields(IBean bean,String fieldNames) throws Exception
	{
		return dao.updateFields(bean, fieldNames);
	}
	public int updateNotNullFields(IBean bean) throws Exception
	{
		return dao.updateNotNullFields(bean);
	}
	public void deleteUser(SysUser user) throws Exception {
		dao.delete(user);
	}
	
	public SysUser getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (SysUser) dao.getByFieldName(SysUser.class, fieldName, fieldValue);
	}
	
	public List<SysUser> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<SysUser>) dao.queryBeanList(SysUser.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{		
		return dao.count(SysUser.class, predicate, fieldValue);
	}
	public int updateVersion(String id) throws Exception
	{
		return dao.updateVersion(id);
	}
	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	
	public List<UserBaseInfoVo> getUserList(QueryInfo<UserBaseInfoVo> queryInfo) throws Exception{		
		
		return mapper.getUserList(queryInfo.getQueryObject());
		//return lstUserVo;
//		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
//		List<UserBaseInfoVo> lstUserVo = mapper.getUserList(queryInfo.getQueryObject());
//		PageInfo<UserBaseInfoVo> userPageInfo = new PageInfo<UserBaseInfoVo>(lstUserVo);
//		return userPageInfo;
	}
	public void insertUserOrg(UserBaseInfoVo userVo){
		 mapper.insertUserOrg(userVo);
	}
	public void setUserStatus(UserBaseInfoVo userVo){
		mapper.setUserStatus(userVo);
	}
	public void updateUserBaseInfo(UserBaseInfoVo userVo){
		mapper.updateUserBaseInfo(userVo);
	}
	public UserBaseInfoVo getUserByUid(String uid){
		return mapper.getUserByUid(uid);
	}
	//获取登录人员科室下所有用户
	public List<UserBaseInfoVo> getAllUser(Map map) {
		// TODO Auto-generated method stub
		return mapper.getAllUser(map);
	}
	public  List<UserBaseInfoVo> getUsers(Set<String> ids) {
		// TODO Auto-generated method stub
		return mapper.getUsers(ids);
	}
}
