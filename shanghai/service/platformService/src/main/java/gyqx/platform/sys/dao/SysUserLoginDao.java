package gyqx.platform.sys.dao;

import common.db.SimpleDao;
import gyqx.platform.po.SysUserLogin;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class SysUserLoginDao 
{
	@Resource
	private SimpleDao dao;
	public SysUserLogin get(String id)
	{
		return dao.get(SysUserLogin.class, id);	
	}
	
	public SysUserLogin getByUserLoginCode(String userLoginCode) throws Exception
	{
//		List<SysUserLogin> list = dao.queryByFieldName(SysUserLogin.class, "userLoginCode", userLoginCode);
        /* 过滤已停用的用户   2018-10-19 by 吴江云*/
        String sql = "select * from sys_user_login su where user_login_code = ? and exists " +
                "( " +
                " select * from sys_user_org uo where su.uxid = uo.user_id and uo.status ='1' " +
                " )";
        List<SysUserLogin> list = dao.queryForList(sql, SysUserLogin.class, userLoginCode);
		if(list.size() >0)
			return list.get(0);
		return null;
	}
	public SysUserLogin getByUserId(String userId) throws Exception
	{
		List<SysUserLogin> list = dao.queryByFieldName(SysUserLogin.class, "uxid", userId);
		
		if(list.size() >0)
			return list.get(0);
		return null;
	}
	public int updateLogin(SysUserLogin login,String flag) 
	{
		int ia = 0;
		if(flag.equalsIgnoreCase("ok"))
		{
			String sql = "update sys_user_login set error_count=0,last_login_datetime=?,flag= '1' where uxid=?";
			ia = dao.executeSql(sql, new Timestamp(System.currentTimeMillis()),login.getUxid());
			
		}else{
			String sql = "update sys_user_login set error_count=error_count +1,last_login_datetime=?,flag= '0' where uxid=?";
			ia= dao.executeSql(sql, new Timestamp(System.currentTimeMillis()),login.getUxid());
		}
		return ia;
		
	}
	public int update(SysUserLogin login) throws Exception
	{
		return dao.update(login);
	}
	public void insert(SysUserLogin login) throws Exception
	{
		dao.insert(login);
		
	}
	public void delete(SysUserLogin login) throws Exception
	{
		dao.insert(login);
	}
	public void deleteByUid(String uid) throws Exception
	{
		dao.deleteByWhere("SysUserLogin", "uxid",uid);
	}

}
