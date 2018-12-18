package gyqx.platform.sys.dao;


import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.platform.po.SysMessage;
import gyqx.platform.sys.dao.mapper.ISysMessageMapper;
import gyqx.platform.sys.vo.SysMessageVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class SysMessageDao {
    @Resource
    private SimpleDao dao;
    @Resource
    private SysConfigUtil configUtil;
    @Resource
    private SysAtomUtil atomUtil;
    @Resource
    private ISysMessageMapper mapper;

    public SysMessage get(String id) {
        return dao.get(SysMessage.class, id);
    }

    public SysMessage insertAndGet(SysMessage sysMessage) throws Exception {
        return dao.insertAndGet(sysMessage);
    }

    public SysMessage updateAndGet(SysMessage sysMessage) throws Exception {
        return dao.updateAndGet(sysMessage);
    }

    public int updateFields(SysMessage sysMessage, String fieldNames) throws Exception {
        return dao.updateFields(sysMessage, fieldNames);
    }

    public int updateNotNullFields(SysMessage sysMessage) throws Exception {
        return dao.updateNotNullFields(sysMessage);
    }

    public void delete(SysMessage sysMessage) throws Exception {
        dao.delete(sysMessage);
    }

    public SysMessage getByFieldName(String fieldName, Object fieldValue) throws Exception {
        return (SysMessage) dao.getByFieldName(SysMessage.class, fieldName, fieldValue);
    }

    public List<SysMessage> query(String predicate, String orderBy, Object... fieldValue) throws Exception {
        return (List<SysMessage>) dao.queryBeanList(SysMessage.class, predicate, orderBy, fieldValue);
    }

    public long count(String predicate, Object... fieldValue) throws Exception {
        return dao.count(SysMessage.class, predicate, fieldValue);
    }

    public int updateVersion(Object bean) throws Exception {
        return dao.updateVersion(bean);
    }

    public List<SysMessageVo> list(SysMessageVo qbean) throws Exception {

        return mapper.list(qbean);
    }

    public List<SysMessageVo> listUsersMsg(String projectCode, Date beginDate, Date endDate, boolean containsRead) {
        return mapper.listUsersMsg(projectCode, beginDate, endDate, containsRead);
    }
}


