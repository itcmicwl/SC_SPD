package gyqx.platform.permission.service.impl;

import javax.annotation.Resource;

import com.mysql.jdbc.StringUtils;
import common.utils.UserOnlineStateUtils;
import gyqx.platform.permission.dao.SysUserRoleDao;
import gyqx.platform.permission.service.ISysUserRoleService;
import gyqx.platform.permission.vo.UserRoleVo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;

import java.util.ArrayList;
import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysUserRole;

@Service
public class SysUserRoleService implements ISysUserRoleService {

    @Resource
    private SysUserRoleDao dao;
    @Resource
    private UserOnlineStateUtils userOnlineStateUtils;

    public SysUserRole get(String id) {
        return dao.get(id);
    }

    @CacheEvict(value = "permits", allEntries = true)
    public SysUserRole add(SysUserRole sysUserRole) throws Exception {
        return dao.insertAndGet(sysUserRole);
    }

    @CacheEvict(value = "permits", allEntries = true)
    public SysUserRole update(SysUserRole sysUserRole) throws Exception {
        return dao.updateAndGet(sysUserRole);
    }

    @CacheEvict(value = "permits", allEntries = true)
    public int updateFields(SysUserRole sysUserRole, String fieldNames) throws Exception {
        return dao.updateFields(sysUserRole, fieldNames);
    }

    @CacheEvict(value = "permits", allEntries = true)
    public void delete(SysUserRole sysUserRole) throws Exception {
        dao.delete(sysUserRole);
    }

    @CacheEvict(value = "permits", allEntries = true)
    public SysUserRole getByFieldName(String fieldName, Object fieldValue) throws Exception {
        return (SysUserRole) dao.getByFieldName(fieldName, fieldValue);
    }

    public List<SysUserRole> query(String predicate, String orderBy, Object... fieldValue) throws Exception {
        return (List<SysUserRole>) dao.query(predicate, orderBy, fieldValue);
    }

    public List<SysUserRole> list(SysUserRole bean) throws Exception {
        return (List<SysUserRole>) dao.list(bean);
    }

    public QueryResult<SysUserRole> listByPage(QueryInfo<SysUserRole> queryInfo) throws Exception {

        PageUtils.startPage(queryInfo);
        return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
    }

    @Override
    public List<UserRoleVo> userRoleLst(UserRoleVo ur) throws Exception {
        return dao.userRoleLst(ur);
    }

    @Override
    @CacheEvict(value="permits", allEntries=true)
    public int insertBatch(List<SysUserRole> lst) throws Exception {
        return dao.insertBatch(lst);
    }

    @Override
    @CacheEvict(value="permits", allEntries=true)
    public int delByUserId(String userId) throws Exception {
        return dao.delByUserId(userId);
    }

    @Override
    @CacheEvict(value = "permits", allEntries = true)
    public int addUserRoles(List<String> lst, String userId) throws Exception {
        if (StringUtils.isNullOrEmpty(userId)) {
            return 0;
        }
        int count = this.delByUserId(userId);
        if (lst.size() == 0) {
            return count;
        }
        List<SysUserRole> lstUr = new ArrayList<>();
        String curUserId = userOnlineStateUtils.getCurrent().getUserId();
        for (String s : lst) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(s);
            ur.setUxid(curUserId);
            lstUr.add(ur);
        }

        return this.insertBatch(lstUr);
    }
}


