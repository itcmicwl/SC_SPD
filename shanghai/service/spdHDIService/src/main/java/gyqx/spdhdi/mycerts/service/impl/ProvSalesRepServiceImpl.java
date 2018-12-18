package gyqx.spdhdi.mycerts.service.impl;

import com.github.pagehelper.PageInfo;
import common.db.SimpleDao;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;
import gyqx.spdhdi.mycerts.Constants;
import gyqx.spdhdi.mycerts.dao.ProvSalesRepDao;
import gyqx.spdhdi.mycerts.service.ProvSalesRepService;
import gyqx.spdhdi.mycerts.vo.ProvSalesRepVo;
import gyqx.spdhdi.po.ProvFrwts;
import gyqx.spdhdi.po.ProvSalesRep;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther Liangwu
 * @Date 17-11-1 上午10:10
 */
@Service
public class ProvSalesRepServiceImpl implements ProvSalesRepService {
    @Resource
    private UtilsContext utilsContext;

    @Resource
    private ProvSalesRepDao provSalesRepDao;

    @Resource
    private SimpleDao simpleDao;

    @Override
    public QueryResult<ProvSalesRepVo> listSalesRep(Date start, Date end, String name, int pageNum, int pageSize) {
        name = name.replaceAll("%", "\\\\%");
        QueryResult<ProvSalesRepVo> result = new QueryResult<>();
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageUtils.startPage(pageNum, pageSize);
        result = PageUtils.endPage(provSalesRepDao.listSalesRep(provId, start, end, name));
        return result;
    }

    @Override
    public String addSalesReps(ProvSalesRepVo provSalesRepVo) throws Exception {
        ProvSalesRep provSalesRep = new ProvSalesRep();

        String salesRepId = utilsContext.getSysAtomUtil().newValue("prov_sales_rep_id");
        Tx.transform(provSalesRepVo).to(provSalesRep);

        provSalesRep.setId(salesRepId);
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        provSalesRep.setProvId(user.getCorpId());
        provSalesRep.setUid(user.getUserId());
        provSalesRep.setFillDate(new Date());
        provSalesRep.setLastUpdateDatetime(new Date());
        provSalesRep.setVersion(0);

        simpleDao.insert(provSalesRep);
        return salesRepId;
    }

    @Override
    public Integer updateSalesRep(ProvSalesRepVo provSalesRepVo) throws Exception {
        ProvSalesRep provSalesRep = new ProvSalesRep();

        Tx.transform(provSalesRepVo).to(provSalesRep);
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        provSalesRep.setProvId(user.getCorpId());
        provSalesRep.setUid(user.getUserId());
        provSalesRep.setLastUpdateDatetime(new Date());

        simpleDao.updateNotNullFields(provSalesRep);
        return provSalesRep.getVersion() + 1;
    }

    @Override
    public String deleteSalesRep(String id, int version) throws Exception {
        ProvSalesRep provSalesRep = new ProvSalesRep();
        provSalesRep.setId(id);
        provSalesRep.setVersion(version);
        simpleDao.delete(provSalesRep);
        return "OK";
    }

    public QueryResult<ProvSalesRepVo> listSalesRepByProv(Date start, Date end, String aboutName, int pageNum, int pageSize){
        QueryResult<ProvSalesRepVo> result = new QueryResult<>();
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageInfo<ProvSalesRepVo> page = new PageInfo<ProvSalesRepVo>(provSalesRepDao.listSalesRepByProv(provId, start, end, aboutName,pageNum, pageSize));
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    public QueryResult<ProvSalesRepVo> listSalesRepByHos(Date start, Date end, String aboutName, int pageNum, int pageSize){
        QueryResult<ProvSalesRepVo> result = new QueryResult<>();
        String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageInfo<ProvSalesRepVo> page = new PageInfo<ProvSalesRepVo>(provSalesRepDao.listSalesRepByHos(hosId, start, end, aboutName,pageNum, pageSize));
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    /**
     * 根据登录用户企业类型，获取相关的销售代表
     * @param compKind
     * @param start
     * @param end
     * @param pageNum
     * @param pageSize
     * @return
     */
    public QueryResult<ProvSalesRepVo> getSalesRepListByLoginComp(String compKind, Date start, Date end, String aboutName, int pageNum, int pageSize){
        if(Constants.COMP_KIND_HOS.equals(compKind)){
            return listSalesRepByHos(start, end, aboutName,pageNum, pageSize);
        } else if(Constants.COMP_KIND_PROV.equals(compKind) || Constants.COMP_KIND_PROV_MFRS.equals(compKind)){
            return listSalesRepByProv(start, end, aboutName,pageNum, pageSize);
        }
        return null;
    }
}
