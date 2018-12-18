package gyqx.spdhdi.mycerts.service.impl;

import com.github.pagehelper.PageInfo;
import common.db.SimpleDao;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;
import gyqx.spdhdi.mycerts.Constants;
import gyqx.spdhdi.mycerts.dao.ProvIncorruptAgrmtDao;
import gyqx.spdhdi.mycerts.service.ProvIncorruptAgrmtService;
import gyqx.spdhdi.mycerts.vo.ProvIncorruptAgrmtVo;
import gyqx.spdhdi.po.ProvIncorruptAgreement;
import gyqx.spdhdi.po.ProvSalesRep;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther Liangwu
 * @Date 17-10-31 下午8:26
 */
@Service
public class ProvIncorruptAgrmtServiceImpl implements ProvIncorruptAgrmtService {
    @Resource
    private UtilsContext utilsContext;

    @Resource
    private ProvIncorruptAgrmtDao provIncorruptAgrmtDao;

    @Resource
    private SimpleDao simpleDao;

    @Override
    public QueryResult<ProvIncorruptAgrmtVo> listIncorruptAgrmt(Date start, Date end, String hosName, int pageNum, int pageSize) {
        hosName = hosName.replaceAll("%", "\\\\%");
        QueryResult<ProvIncorruptAgrmtVo> result = new QueryResult<>();
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageUtils.startPage(pageNum, pageSize);
        result = PageUtils.endPage(provIncorruptAgrmtDao.listIncorruptAgrmt(provId, start, end, hosName));
        return result;
    }

    @Override
    public String addIncorruptAgrmt(ProvIncorruptAgrmtVo vo) throws Exception {
        String agrtId = utilsContext.getSysAtomUtil().newValue("prov_agrt_id");

        ProvIncorruptAgreement provIncorruptAgreement = new ProvIncorruptAgreement();
        Tx.transform(vo).to(provIncorruptAgreement);

        provIncorruptAgreement.setId(agrtId);
        provIncorruptAgreement.setUid(utilsContext.getUserStateUtils().getCurrent().getUserId());
        provIncorruptAgreement.setFillDate(new Date());
        provIncorruptAgreement.setLastUpdateDatetime(new Date());
        provIncorruptAgreement.setVersion(0);
        simpleDao.insert(provIncorruptAgreement);
        return agrtId;
    }

    @Override
    public Integer updateIncorruptAgrmt(ProvIncorruptAgrmtVo provIncorruptAgrmtVo) throws Exception {
        ProvIncorruptAgreement provIncorruptAgreement = new ProvIncorruptAgreement();

        Tx.transform(provIncorruptAgrmtVo).to(provIncorruptAgreement);
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        provIncorruptAgreement.setProvId(user.getCorpId());
        provIncorruptAgreement.setUid(user.getUserId());
        provIncorruptAgreement.setLastUpdateDatetime(new Date());

        simpleDao.updateNotNullFields(provIncorruptAgreement);
        return provIncorruptAgreement.getVersion() + 1;
    }

    @Override
    public String deleteIncorruptAgrmt(String id, int version) throws Exception {
        ProvIncorruptAgreement provIncorruptAgreement = new ProvIncorruptAgreement();
        provIncorruptAgreement.setId(id);
        provIncorruptAgreement.setVersion(version);
        simpleDao.delete(provIncorruptAgreement);
        return "OK";
    }

    public QueryResult<ProvIncorruptAgrmtVo> listIncorruptAgrmtByProv(Date start, Date end, String aboutName, int pageNum, int pageSize){
        QueryResult<ProvIncorruptAgrmtVo> result = new QueryResult<>();
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageInfo<ProvIncorruptAgrmtVo> page = new PageInfo<ProvIncorruptAgrmtVo>(provIncorruptAgrmtDao.listIncorruptAgrmtByProv(provId, start, end, aboutName, pageNum, pageSize));
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    public QueryResult<ProvIncorruptAgrmtVo> listIncorruptAgrmtByHos(Date start, Date end, String aboutName, int pageNum, int pageSize){
        QueryResult<ProvIncorruptAgrmtVo> result = new QueryResult<>();
        String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageInfo<ProvIncorruptAgrmtVo> page = new PageInfo<ProvIncorruptAgrmtVo>(provIncorruptAgrmtDao.listIncorruptAgrmtByHos(hosId, start, end, aboutName,pageNum, pageSize));
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    /**
     * 根据登录用户企业类型，获取相关的廉政协议书
     * @param compKind
     * @param start
     * @param end
     * @param pageNum
     * @param pageSize
     * @return
     */
    public QueryResult<ProvIncorruptAgrmtVo> getIncorruptAgrmtListByLoginComp(String compKind, Date start, Date end, String aboutName,int pageNum, int pageSize){
        if(Constants.COMP_KIND_HOS.equals(compKind)){
            return listIncorruptAgrmtByHos(start, end, aboutName,pageNum, pageSize);
        } else if(Constants.COMP_KIND_PROV.equals(compKind) || Constants.COMP_KIND_PROV_MFRS.equals(compKind)){
            return listIncorruptAgrmtByProv(start, end, aboutName,pageNum, pageSize);
        }
        return null;
    }
}
