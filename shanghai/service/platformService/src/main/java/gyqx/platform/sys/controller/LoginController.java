package gyqx.platform.sys.controller;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import common.base.RandomCode;
import common.exception.ValidateException;
import common.transform.Tx;
import common.utils.UserOnlineStateUtils;
import common.web.AjaxResult;
import common.web.BaseController;
import common.web.UserOnlineInfo;
import gyqx.common.exception.NoUserException;
import gyqx.platform.permission.service.IRoleConfigService;
import gyqx.platform.po.SysRole;
import gyqx.platform.po.SysUser;
import gyqx.platform.po.SysUserLogin;
import gyqx.platform.sys.service.ILoginService;
import gyqx.platform.sys.service.ISysUserService;
import gyqx.platform.sys.vo.ChangePasswordParam;
import gyqx.platform.sys.vo.CheckUserLoginCode;
import gyqx.platform.sys.vo.LoginParam;
import gyqx.platform.sys.vo.UserBaseInfoVo;

@Controller
@RequestMapping(value = "sys/login")
public class LoginController extends BaseController {
    @Resource
    private ILoginService loginService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private UserOnlineStateUtils stateUtils;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "checkUserLoginCode")
    @ResponseBody
    public AjaxResult checkUserLoginCode(HttpServletRequest request, HttpServletResponse response,
                                         @RequestBody @Valid CheckUserLoginCode loginCode, Errors error) throws ValidateException, NoUserException {
        HashMap<String, Object> map = new HashMap<>();
        AjaxResult result = new AjaxResult();

        System.out.println("getRealPath:" + request.getServletContext().getRealPath("/"));// .getContextPath());
        filterErrors(error);

        SysUserLogin userInfo = loginService.checkUserLoginCode(loginCode.getUserLoginCode());

        String randomCode = RandomCode.getRandomCode();
        String newWebSalt = RandomCode.getRandomCode();

        request.getSession().setAttribute("randomCode", randomCode);
        request.getSession().setAttribute("newWebSalt", newWebSalt);

        map.put("salt", userInfo.getWebSalt());
        map.put("newSalt", newWebSalt);
        map.put("randomCode", randomCode);

        result.setData(map);

        return result;

    }

    @GetMapping(value = "checkLoginCode/{userLoginCode}")
    @ResponseBody
    public AjaxResult checkLoginCode(@PathVariable("userLoginCode") String userLoginCode) throws ValidateException, NoUserException {
        HashMap<String, Object> map = new HashMap<>();
        AjaxResult result = new AjaxResult();
        SysUserLogin userInfo = loginService.checkUserLoginCode(userLoginCode);
        String randomCode = RandomCode.getRandomCode();
        String newWebSalt = RandomCode.getRandomCode();
        request.getSession().setAttribute("randomCode", randomCode);
        request.getSession().setAttribute("newWebSalt", newWebSalt);
        map.put("salt", userInfo.getWebSalt());
        map.put("newSalt", newWebSalt);
        map.put("randomCode", randomCode);
        result.setData(map);
        return result;
    }

    @GetMapping(value = "userLogin/{usrLoginCode}/{rCode}/{hashCode}")
    @ResponseBody
    public AjaxResult<UserBaseInfoVo> userlogin(@PathVariable("usrLoginCode") String usrLoginCode, @PathVariable("rCode") String rCode, @PathVariable("hashCode") String hashCode) throws Exception {
        AjaxResult<UserBaseInfoVo> result = new AjaxResult<UserBaseInfoVo>();
        if (!Objects.equals(request.getSession().getAttribute("randomCode"), rCode))
            throw new Exception("验证码出错");
        SysUser userBaseInfo = loginService.login(usrLoginCode, hashCode);
        UserBaseInfoVo userVo = sysUserService.getUserByUid(userBaseInfo.getId());
        request.getSession().invalidate();
        request.getSession(true);
        String sessionId = request.getSession().getId();
        UserOnlineInfo uo = stateUtils.getCurrent();
        if (uo == null) // 还未登录
        {
            uo = new UserOnlineInfo();
            Tx.transform(userVo).to(uo).setSessionId(sessionId);
            uo.setCacheId(UUID.randomUUID().toString().replace("-", ""));
            String addr = InetAddress.getLocalHost().getHostAddress();
            uo.setServerIp(addr);
        } else {  // 重新登录
            stateUtils.clear(uo);
            uo.setSessionId(sessionId);
            if (uo.getUserId().equals(userVo.getUserId())) {
                Tx.transform(userVo).to(uo);
                uo.setLastIP(request.getAttribute("remote.ip").toString());
            } else {
                Tx.transform(userVo).to(uo);

                uo.setCacheId(UUID.randomUUID().toString().replace("-", ""));
                uo.setLastIP(request.getAttribute("remote.ip").toString());
            }
        }

        stateUtils.set(uo);

        result.setData(userVo);
        result.setTag(sessionId);

        return result;
    }

    @RequestMapping(value = "login")
    @ResponseBody
    public AjaxResult<UserBaseInfoVo> login(HttpServletRequest request, HttpServletResponse response,
                                            @RequestBody @Valid LoginParam param, Errors error) throws Exception {
        AjaxResult<UserBaseInfoVo> result = new AjaxResult<UserBaseInfoVo>();

        filterErrors(error);

        if (!Objects.equals(request.getSession().getAttribute("randomCode"), param.getRcode()))
            throw new Exception("验证码出错");

        SysUser userBaseInfo = loginService.login(param.getUserLoginCode(), param.getHash());
        UserBaseInfoVo userVo = sysUserService.getUserByUid(userBaseInfo.getId());


        request.getSession().invalidate();
        request.getSession(true);

        String sessionId = request.getSession().getId();
        UserOnlineInfo uo = stateUtils.getCurrent();


        if (uo == null) // 还未登录
        {
            uo = new UserOnlineInfo();
            Tx.transform(userVo).to(uo).setSessionId(sessionId);
            uo.setCacheId(UUID.randomUUID().toString().replace("-", ""));

            String addr = InetAddress.getLocalHost().getHostAddress();
            uo.setServerIp(addr);
        } else {  // 重新登录
            stateUtils.clear(uo);
            uo.setSessionId(sessionId);
            if (uo.getUserId().equals(userVo.getUserId())) {
                Tx.transform(userVo).to(uo);
                uo.setLastIP(request.getAttribute("remote.ip").toString());
            } else {
                Tx.transform(userVo).to(uo);

                uo.setCacheId(UUID.randomUUID().toString().replace("-", ""));
                uo.setLastIP(request.getAttribute("remote.ip").toString());
            }
        }

        stateUtils.set(uo);

        result.setData(userVo);
        result.setTag(sessionId);

        return result;
    }

    @RequestMapping(value = "exit")
    @ResponseBody
    public AjaxResult exit() throws Exception {
        AjaxResult result = new AjaxResult<>();
        loginService.exit();
        return result;
    }

    @RequestMapping(value = "changePassword")
    @ResponseBody
    public AjaxResult<SysUser> changePassword(HttpServletRequest request, HttpServletResponse response,
                                              @RequestBody @Valid ChangePasswordParam param, Errors error) throws Exception {
        AjaxResult<SysUser> result = new AjaxResult<>();

        filterErrors(error);
        if (!Objects.equals(request.getSession().getAttribute("randomCode"), param.getRcode()))
            throw new Exception("验证码出错");

        if (!Objects.equals(request.getSession().getAttribute("newWebSalt"), param.getNewSalt()))
            throw new Exception("数据验证出错");


        SysUser userBaseInfo = loginService.changePassword(param.getUserLoginCode(), param.getNewSalt(),
                param.getHash(), param.getNewHash());


        request.getSession().invalidate();
        request.getSession(true);

        String sessionId = request.getSession().getId();
        UserOnlineInfo uo = stateUtils.getCurrent();

        stateUtils.clear(uo);
        uo.setSessionId(sessionId);
        stateUtils.set(uo);

        result.setData(userBaseInfo);

        result.setTag(sessionId);

        return result;
    }


    @RequestMapping(value = "login.page")
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response,
                                  String userLoginCode, String hash, String rcode) {
        ModelAndView mv = new ModelAndView();
        try {
            if (!Objects.equals(request.getSession().getAttribute("randomCode"), rcode))
                throw new Exception("验证码出错");
            SysUser userBaseInfo = loginService.login(userLoginCode, hash);

            request.getSession().invalidate();
            request.getSession(true);

            request.getSession().setAttribute("userInfo", userBaseInfo);


            mv.setViewName("login_ok");
            mv.addObject(userBaseInfo);

        } catch (Exception ex) {
            mv.setViewName("login_error");
        }
        return mv;
    }

    @RequestMapping(value = "initPassword/{userLoginCode}")
    @ResponseBody
    public AjaxResult<String> initPassword(@PathVariable("userLoginCode") String userLoginCode) throws Exception {
        AjaxResult<String> result = new AjaxResult<>();
        String ret = loginService.initPassword(userLoginCode);
        result.setData(ret);
        return result;
    }
}
