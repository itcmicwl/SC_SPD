package gyqx.platform.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.UserOnlineStateUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.po.SysUser;
import gyqx.platform.sys.dao.SysUserDao;
import gyqx.platform.sys.service.ISysUserService;
import gyqx.platform.sys.vo.UserBaseInfoVo;

@Controller
@RequestMapping(value = "sys/user")
public class UserController extends BaseController {
	@Resource
	ISysUserService sysUserService;
	@Resource
	SysUserDao udao;
	@Resource
	private UserOnlineStateUtils userOnline;
	
	/**
	 * 添加职员信息
	 * @param user
	 * @param error
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	public AjaxResult<UserBaseInfoVo> insert(@RequestBody @Valid UserBaseInfoVo user, Errors error) throws Exception {
		AjaxResult<UserBaseInfoVo> result = new AjaxResult<UserBaseInfoVo>();
		user = sysUserService.addUser(user);
		result.setData(user);
		return result;
	}

	/**
	 * 编辑职员信息
	 * @param user
	 * @param error
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public AjaxResult<UserBaseInfoVo> update(@RequestBody @Valid UserBaseInfoVo user,Errors error) throws Exception {
		AjaxResult<UserBaseInfoVo> result = new AjaxResult<UserBaseInfoVo>();
		filterErrors(error);
		sysUserService.updateUserBaseInfo(user);
		return result;
	}

	/**
	 * 修改职员状态
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "changeStatus")
	@ResponseBody
	public AjaxResult<UserBaseInfoVo> delete(@RequestBody UserBaseInfoVo user) throws Exception {
		AjaxResult<UserBaseInfoVo> result = new AjaxResult<UserBaseInfoVo>();		
		sysUserService.setUserStatus(user);
		return result;
	}

	@RequestMapping(value = "userlist")
	@ResponseBody
	public AjaxResult<QueryResult<UserBaseInfoVo>> userlist(@RequestBody @Valid QueryInfo<UserBaseInfoVo> queryInfo,
			Errors error) throws Exception {		
		AjaxResult<QueryResult<UserBaseInfoVo>> result = new AjaxResult<QueryResult<UserBaseInfoVo>>();
		//userOnline.getCurrent().getCorpId();
		QueryResult<UserBaseInfoVo> data = sysUserService.getUserList(queryInfo);
		result.setData(data);
		return result;
	}	
	
	/**
	 * 我的职员李彪查询
	 * @param queryInfo
	 * @param error
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userlistByUnit")
	@ResponseBody
	public AjaxResult<QueryResult<UserBaseInfoVo>> userlistByUnit(@RequestBody @Valid QueryInfo<UserBaseInfoVo> queryInfo,
			Errors error) throws Exception {		
		AjaxResult<QueryResult<UserBaseInfoVo>> result = new AjaxResult<QueryResult<UserBaseInfoVo>>();
		queryInfo.getQueryObject().setCorpId(userOnline.getCurrent().getCorpId());	
		QueryResult<UserBaseInfoVo> data = sysUserService.getUserList(queryInfo);
		result.setData(data);
		return result;
	}
	//获取当前登录用户下所有人员
	@RequestMapping(value = "getAllUser")
	@ResponseBody
	public AjaxResult<List<UserBaseInfoVo>> getAllUser() throws Exception {		
		AjaxResult<List<UserBaseInfoVo>> result = new AjaxResult<List<UserBaseInfoVo>>();
		String hosId=userOnline.getCurrent().getCorpId();
		String deptId=userOnline.getCurrent().getOrgId();
		Map map=new HashMap<String,Object>();
		map.put("hosId",hosId);
		map.put("deptId", deptId);
		List<UserBaseInfoVo> data =sysUserService.getAllUser(map);
		result.setData(data);
		return result;
	}
}
