package gyqx.platform.sys.service.impl;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import common.utils.UtilsContext;
import gyqx.platform.po.SysMessage;
import gyqx.platform.sys.dao.SysMessageDao;
import gyqx.platform.sys.service.ISysMessageService;
import gyqx.platform.sys.vo.SysMessageVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class SysMessageService implements ISysMessageService {

    @Resource
    private SysMessageDao dao;
    @Resource
    private SimpleDao sDao;
    @Resource
    private UtilsContext utilsContext;

    public SysMessage get(String id) {
        return dao.get(id);
    }

    public SysMessage add(SysMessage sysMessage) throws Exception {
        return dao.insertAndGet(sysMessage);
    }

    public SysMessage update(SysMessage sysMessage) throws Exception {
        return dao.updateAndGet(sysMessage);
    }

    public int updateFields(SysMessage sysMessage, String fieldNames) throws Exception {
        return dao.updateFields(sysMessage, fieldNames);
    }

    public void delete(SysMessage sysMessage) throws Exception {
        dao.delete(sysMessage);
    }

    public SysMessage getByFieldName(String fieldName, Object fieldValue) throws Exception {
        return (SysMessage) dao.getByFieldName(fieldName, fieldValue);
    }

    public List<SysMessage> query(String predicate, String orderBy, Object... fieldValue) throws Exception {
        return (List<SysMessage>) dao.query(predicate, orderBy, fieldValue);
    }

    public List<SysMessageVo> list(SysMessageVo bean) throws Exception {
        return (List<SysMessageVo>) dao.list(bean);
    }

    public QueryResult<SysMessageVo> listByPage(QueryInfo<SysMessageVo> queryInfo) throws Exception {

        PageUtils.startPage(queryInfo);
        return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
    }

    @Override
    public List<SysMessageVo> listUsersMsg(String projectCode, Date beginDate, Date endDate, boolean containsRead) {
        if (beginDate == null && endDate == null) {
            Instant now = Instant.now();
            endDate = Date.from(now);
            beginDate = Date.from(now.minus(30, ChronoUnit.DAYS));
        }
        return dao.listUsersMsg(projectCode, beginDate, endDate, containsRead);
    }

    @Override
    public boolean validateApp(String appId, String accessKey) {
        String ename = "app-id:" + appId;
        String val = utilsContext.getSysConfigUtil().getValue(ename);
        if(val == null){
            utilsContext.getSysConfigUtil().refresh();
            val = utilsContext.getSysConfigUtil().getValue(ename);
        }
        return StringUtils.hasText(val) && accessKey.equals(val);
    }

    @Override
    public List<SysMessageVo> getProUserMsgList(SysMessageVo sysMessageVo) throws Exception {
        return this.list(sysMessageVo);
    }

    @Override
    public List<SysMessage> getUserMsg(String projectCode, String userId) throws Exception {
        if (StringUtils.isEmpty(projectCode) || StringUtils.isEmpty(userId)) {
            throw new Exception("项目代码和用户不能为空");
        }
        String sql = "select sm.* from sys_message_confirm smc left join sys_message sm on smc.message_id=sm.id where smc.user_id=? and sm.project_code=? and smc.read_datetime is null";
        List<SysMessage> lst = sDao.queryForList(sql, SysMessage.class, userId, projectCode);
        return lst;
    }
}


