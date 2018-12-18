package gyqx.platform.permission.controller;

import common.base.KV;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.permission.service.IAdminPermissionConfigService;
import gyqx.platform.permission.service.ISysRoleService;
import gyqx.platform.permission.service.ISysUserRoleService;
import gyqx.platform.permission.vo.MenuConfig;
import gyqx.platform.permission.vo.UserRoleVo;
import gyqx.platform.po.SysRole;
import gyqx.platform.po.SysUserRole;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "permission/userRole")
public class UserRoleController extends BaseController
{
	@Resource
	private ISysUserRoleService userRoleService;
	@Resource
	private ISysRoleService sysRoleService;
	@RequestMapping(value="userRoleList")
	public AjaxResult getUserRoleList(@RequestBody UserRoleVo ur) throws Exception
	{
		AjaxResult result = new AjaxResult();
		if(ur.getHosId() == null){
			throw new Exception("公司ID不能为空");
		}
		result.setData(userRoleService.userRoleLst(ur));
		return result;
	}

	@RequestMapping(value="hosUserRoles/{hosId}")
	public AjaxResult gethosUserRoles(@PathVariable("hosId") String hosId) throws Exception
	{
		AjaxResult result = new AjaxResult();
		SysRole sr = new SysRole();
		sr.setCompanyId(hosId);
		result.setData(sysRoleService.list(sr));
		return result;
	}
	@RequestMapping(value = "setUserRoles/{userId}")
	public AjaxResult setUserRoles(@RequestBody List<String> lst,@PathVariable("userId") String userId) throws Exception{
		AjaxResult result = new AjaxResult();
		result.setData(userRoleService.addUserRoles(lst,userId));
		return result;
	}
}
