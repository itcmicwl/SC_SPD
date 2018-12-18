package gyqx.platform.sys.controller;


import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import common.web.BaseController;
import gyqx.platform.po.SysMessage;
import gyqx.platform.sys.service.ISysMessageService;
import gyqx.platform.sys.vo.SysMessageVo;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/sys/sysMessage")
public class SysMessageController extends BaseController {
    @Resource
    private ISysMessageService sysMessageService;

    @RequestMapping(value = "add")
    public AjaxResult add(@RequestBody @Valid SysMessage sysMessage, Errors error) throws Exception {
        AjaxResult result = new AjaxResult();
        filterErrors(error);
        SysMessage ret = sysMessageService.add(sysMessage);
        result.setData(ret);
        return result;
    }

    @RequestMapping(value = "get/{id}")
    public AjaxResult get(@PathVariable("id") String id, Errors error) throws Exception {
        AjaxResult result = new AjaxResult();
        filterErrors(error);
        SysMessage ret = sysMessageService.get(id);
        result.setData(ret);
        return result;
    }

    @RequestMapping(value = "update")
    public AjaxResult update(@RequestBody @Valid SysMessage sysMessage, Errors error) throws Exception {
        AjaxResult result = new AjaxResult();
        filterErrors(error);
        SysMessage ret = sysMessageService.update(sysMessage);
        result.setData(ret);
        return result;
    }

    @RequestMapping(value = "list")
    public AjaxResult list(@RequestBody @Valid SysMessageVo sysMessageVo, Errors error) throws Exception {
        AjaxResult result = new AjaxResult();
        filterErrors(error);
        List<SysMessageVo> ret = sysMessageService.list(sysMessageVo);
        result.setData(ret);
        return result;
    }

    @RequestMapping(value = "query")
    public AjaxResult query(@RequestBody @Valid QueryInfo<Map<String, String>> queryInfo, Errors error) throws Exception {
        AjaxResult result = new AjaxResult();
        filterErrors(error);
        List<SysMessage> ret = sysMessageService.query(queryInfo.getPredicate(), queryInfo.getOrderBy(), queryInfo.getQueryObject());
        result.setData(ret);
        return result;
    }

    @RequestMapping(value = "listByPage")
    public AjaxResult listByPage(@RequestBody @Valid QueryInfo<SysMessageVo> queryInfo, Errors error) throws Exception {
        AjaxResult result = new AjaxResult();
        filterErrors(error);
        QueryResult<SysMessageVo> ret = sysMessageService.listByPage(queryInfo);
        result.setData(ret);
        return result;
    }

    @RequestMapping(value = "getProUserMsgList")
    public AjaxResult<List<SysMessageVo>> getProUserMsgList(@RequestBody SysMessageVo sysMessageVo) throws Exception {
        AjaxResult<List<SysMessageVo>> result = new AjaxResult<>();
        List<SysMessageVo> ret = sysMessageService.getProUserMsgList(sysMessageVo);
        result.setData(ret);
        return result;
    }

    @GetMapping(value = "getUserMsgList/{projectCode}/{userId}")
    public AjaxResult<List<SysMessage>> getUserMsgList(@PathVariable("projectCode") String projectCode, @PathVariable("userId") String userID) throws Exception {
        AjaxResult<List<SysMessage>> result = new AjaxResult<>();
        List<SysMessage> ret = sysMessageService.getUserMsg(projectCode, userID);
        result.setData(ret);
        return result;
    }

    @GetMapping("/getUsersMsg/{projectCode}/")
    public AjaxResult listUsersMsg(@PathVariable("projectCode") String projectCode,
                                   @RequestParam(value = "beginDate", required = false) Date beginDate,
                                   @RequestParam(value = "endDate", required = false) Date endDate,
                                   @RequestParam(value = "containsRead", defaultValue = "false") boolean containsRead,
                                   @RequestParam("appId") String appId,
                                   @RequestParam("accessKey") String accessKey) {
        Assert.isTrue(sysMessageService.validateApp(appId, accessKey), "无效app key");
        AjaxResult<List<SysMessageVo>> result = new AjaxResult<>();
        result.setData(sysMessageService.listUsersMsg(projectCode, beginDate, endDate, containsRead));
        return result;
    }
}
