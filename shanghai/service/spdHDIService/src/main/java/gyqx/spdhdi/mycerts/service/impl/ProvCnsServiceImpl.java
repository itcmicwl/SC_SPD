package gyqx.spdhdi.mycerts.service.impl;

import com.github.pagehelper.PageInfo;
import common.db.SimpleDao;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.UtilsContext;
import gyqx.spdhdi.mycerts.Constants;
import gyqx.spdhdi.mycerts.dao.ProvCnsDao;
import gyqx.spdhdi.mycerts.service.ProvCnsService;
import gyqx.spdhdi.mycerts.vo.ProvCnsVo;
import gyqx.spdhdi.po.ProvCns;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by liangwu on 17-6-25.
 */
@Service
public class ProvCnsServiceImpl implements ProvCnsService {
    @Resource
    private SimpleDao simpleDao;

    @Resource
    private UtilsContext utilsContext;

    @Resource
    private ProvCnsDao provCnsDao;

    @Override
    public String addCns(ProvCnsVo provCnsVo) throws Exception {
        String cnsId = utilsContext.getSysAtomUtil().newValue("prov_cns_id");

        ProvCns provCns = new ProvCns();
        Tx.transform(provCnsVo).to(provCns);

        provCns.setId(cnsId);
        provCns.setFilePathWts(provCnsVo.getFilePathCns());
        provCns.setUid(utilsContext.getUserStateUtils().getCurrent().getUserId());
        provCns.setFillDate(new Date());
        provCns.setLastUpdateDatetime(new Date());
        provCns.setVersion(0);
        simpleDao.insert(provCns);
        return cnsId;
    }

    @Override
    public QueryResult<ProvCnsVo> findCns(Date start, Date end, String hosName, int pageNum, int pageSize) {
        hosName = hosName.replaceAll("%", "\\\\%");
        QueryResult<ProvCnsVo> result = new QueryResult<>();
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageInfo<ProvCnsVo> page = new PageInfo<ProvCnsVo>(provCnsDao.getCns(provId, start, end, hosName, pageNum, pageSize));
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    public QueryResult<ProvCnsVo> getCnsListByProv(Date start, Date end, String aboutName,int pageNum, int pageSize){
        QueryResult<ProvCnsVo> result = new QueryResult<>();
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageInfo<ProvCnsVo> page = new PageInfo<ProvCnsVo>(provCnsDao.getCnsListByProv(provId, start, end, aboutName,pageNum, pageSize));
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    public QueryResult<ProvCnsVo> getCnsListByHos(Date start, Date end, String aboutName,int pageNum, int pageSize){
        QueryResult<ProvCnsVo> result = new QueryResult<>();
        String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageInfo<ProvCnsVo> page = new PageInfo<ProvCnsVo>(provCnsDao.getCnsListByHos(hosId, start, end, aboutName,pageNum, pageSize));
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    /**
     * 根据登录用户企业类型，获取相关的承诺书
     * @param compKind
     * @param start
     * @param end
     * @param pageNum
     * @param pageSize
     * @return
     */
    public QueryResult<ProvCnsVo> getCnsListByLoginComp(String compKind, Date start, Date end, String aboutName,int pageNum, int pageSize){
        if(Constants.COMP_KIND_HOS.equals(compKind)){
            return getCnsListByHos(start, end, aboutName,pageNum, pageSize);
        } else if(Constants.COMP_KIND_PROV.equals(compKind) || Constants.COMP_KIND_PROV_MFRS.equals(compKind)){
            return getCnsListByProv(start, end, aboutName,pageNum, pageSize);
        }
        return null;
    }

    @Override
    @Transactional
    public Integer updateCns(ProvCnsVo provCnsVo) throws Exception {
        ProvCns provCns = new ProvCns();
        Tx.transform(provCnsVo).to(provCns);
        provCns.setUid(utilsContext.getUserStateUtils().getCurrent().getUserId());
        provCns.setLastUpdateDatetime(new Date());
        provCns.setFilePathWts(provCnsVo.getFilePathCns());
        simpleDao.updateNotNullFields(provCns);
        return provCnsVo.getVersion() + 1;
    }

    @Override
    public String deleteCns(String id, int version) throws Exception {
        ProvCns provCns = new ProvCns();
        provCns.setId(id);
        provCns.setVersion(version);
        simpleDao.delete(provCns);
        return "OK";
    }
}
