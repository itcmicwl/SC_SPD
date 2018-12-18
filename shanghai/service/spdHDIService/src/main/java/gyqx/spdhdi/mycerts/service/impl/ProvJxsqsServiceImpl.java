package gyqx.spdhdi.mycerts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import gyqx.spdhdi.mycerts.vo.*;
import gyqx.spdhdi.po.ProvJxsqsGoods;
import gyqx.spdhdi.po.ProvJxsqsHos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;
import gyqx.spdhdi.baseData.dao.BasRegInfoDao;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.mycerts.dao.ProvJxsqsDao;
import gyqx.spdhdi.mycerts.service.ProvJxsqsService;
import gyqx.spdhdi.po.ProvJxsqs;
import gyqx.spdhdi.po.ProvJxsqsZjzj;
import org.springframework.util.StringUtils;
import sun.reflect.annotation.ExceptionProxy;

/**
 * Created by liangwu on 17-6-21.
 */
@Service
public class ProvJxsqsServiceImpl implements ProvJxsqsService {
    @Resource
    private BasRegInfoDao basRegInfoDao;

    @Resource
    private ProvJxsqsDao provJxsqsDao;

    @Resource
    private SimpleDao simpleDao;

    @Resource
    private UtilsContext utilsContext;

    @Override
    public List<BasMfrsInfoVo> findBasMfrs(String mfrsName) {
        return basRegInfoDao.getMfrsInfoLikeName(mfrsName, 0, 10);
    }

    @Override
    public QueryResult<ProvRegInfoJxsqsVo> findProvRegInfo(String mfrsId, String certName, String sqsId, int pageNum) {
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        QueryResult<ProvRegInfoJxsqsVo> result = new QueryResult<>();
        PageInfo<ProvRegInfoJxsqsVo> page = new PageInfo<>(provJxsqsDao.getProvRegInfo(provId, mfrsId, certName, sqsId, pageNum));
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    @Override
    public QueryResult<BasCompanyInfoJxsqsVo> findBasHos(String hosName, String sqsId, int pageNum) {
        QueryResult<BasCompanyInfoJxsqsVo> result = new QueryResult<>();
        hosName = hosName.replaceAll("%", "\\\\%");
        PageInfo<BasCompanyInfoJxsqsVo> page = new PageInfo<>(provJxsqsDao.getBasHos(hosName, sqsId, pageNum));
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    @Override
    @Transactional
    public String addJxsqs(ProvJxsqsVo provJxsqsVo) throws Exception {
        ProvJxsqs provJxsqs = new ProvJxsqs();
        Tx.transform(provJxsqsVo).to(provJxsqs);

        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        String uid = user.getUserId();
        String sqsId = utilsContext.getSysAtomUtil().newValue("prov_jxsqs_id");
        String provId = user.getCorpId();
        String mfrsId = provJxsqsVo.getMfrsId();

        if (provJxsqs.getIsValiding().equals("1"))
            provJxsqs.setExpdtEndDate(null);

        provJxsqsDao.addJxsqs(provJxsqs, sqsId, uid);
        if (provJxsqsVo.getForHospitals().size() > 0)
            provJxsqsDao.addProvJxsqsHos(provJxsqsVo.getForHospitals(), sqsId, provId, uid);
        if (provJxsqsVo.getRelatedCerts().size() > 0)
            provJxsqsDao.addProvJxsqsRegist(provJxsqsVo.getRelatedCerts(), sqsId, provId, mfrsId, uid);
        if (provJxsqsVo.getZjzjList().size() > 0)
            provJxsqsDao.addProvJxsqsZjzj(provJxsqsVo.getZjzjList(), sqsId);
        return sqsId;
    }

    @Override
    public QueryResult<ProvJxsqsVo> findJxsqs(Date start, Date end, String mfrsName, int pageNum, int pageSize) {
        mfrsName = mfrsName.replaceAll("%", "\\\\%");
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        QueryResult<ProvJxsqsVo> result = new QueryResult<>();
        PageInfo<ProvJxsqsVo> page = new PageInfo<>(provJxsqsDao.getJxsqs(provId, start, end, mfrsName, pageNum, pageSize));
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    @Override
    @Transactional
    public int updateJxsqs(ProvJxsqsVo provJxsqsVo) throws Exception {
        ProvJxsqs provJxsqs = new ProvJxsqs();
        Tx.transform(provJxsqsVo).to(provJxsqs);

        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        String sqsId = provJxsqsVo.getId();
        String uid = user.getUserId();
        String provId = user.getCorpId();
        String mfrsId = provJxsqsVo.getMfrsId();

        if (provJxsqsVo.getAddHos().size() > 0)
            provJxsqsDao.addProvJxsqsHos(provJxsqsVo.getAddHos(), sqsId, provId, uid);
        if (provJxsqsVo.getAddCerts().size() > 0)
            provJxsqsDao.addProvJxsqsRegist(provJxsqsVo.getAddCerts(), sqsId, provId, mfrsId, uid);

        if (provJxsqsVo.getDelHos().size() > 0)
            provJxsqsDao.delProvJxsqsHos(provJxsqsVo.getDelHos(), sqsId, provId);
        if (provJxsqsVo.getDelCerts().size() > 0)
            provJxsqsDao.delProvJxsqsRegist(provJxsqsVo.getDelCerts(), sqsId, provId, mfrsId);

        provJxsqs.setUid(uid);
        provJxsqs.setProvId(provId);
        int version = provJxsqsDao.modJxsqs(provJxsqs);
        return version;
    }

    @Override
    @Transactional
    public String deleteJxsqs(String id, int version) throws Exception {
        ProvJxsqs provJxsqs = new ProvJxsqs();
        provJxsqs.setId(id);
        provJxsqs.setVersion(version);

        String result = provJxsqsDao.delProvJxsqs(id);
        simpleDao.delete(provJxsqs);
        return result;
    }

    @Override
    public List<BasMfrsInfoVo> findBasCompany(String name) {
        return basRegInfoDao.getCompanyInfoLikeName(name, 0, 10);
    }

    @Override
    public List<String> findZjzjCompany(String name) {
        return provJxsqsDao.getZjzjCompanyLikeName(name, 0, 10);
    }

    @Override
    public String addProvJxsqsZjzj(ProvJxsqsZjzj provJxsqsZjzj) throws Exception {
        String id = utilsContext.getSysAtomUtil().newValue("prov_jxsqs_zjzj_id");
        provJxsqsZjzj.setId(id);
        simpleDao.insert(provJxsqsZjzj);
        return id;
    }

    @Override
    public String delProvJxsqsZjzj(String id, int version) throws Exception {
        ProvJxsqsZjzj provJxsqsZjzj = new ProvJxsqsZjzj();
        provJxsqsZjzj.setId(id);
        provJxsqsZjzj.setVersion(version);
        simpleDao.delete(provJxsqsZjzj);
        return "0";
    }

    @Override
    public List<ProvJxsqsNVo> getJxsqsList(ProvJxsqsGoods query) throws Exception {
        return provJxsqsDao.getJxsqsList(query);
    }

    @Override
    public Boolean saveJxsqsRelation(SaveJxsqsRelationVo saveJxsqsRelationVo) throws Exception {
        List<ProvJxsqsGoods> lstRelation = saveJxsqsRelationVo.getLstSqsRelation();
        List<ProvJxsqsHos> lstHos = saveJxsqsRelationVo.getLstSqsHos();
        this.saveSqsRelation(lstRelation);
        this.saveSqsHos(lstHos);
        return true;
    }
    private Boolean saveSqsRelation(List<ProvJxsqsGoods> lst) throws Exception{
        if(lst != null && lst.size()>0){
            List<ProvJxsqsGoods> unUpdatedRelation = new ArrayList<>();
            for (ProvJxsqsGoods item : lst) {
                if(!IsExistSqsRelation(item)){
                    unUpdatedRelation.add(item);
                }
            }
            if(unUpdatedRelation.size()>0){
                provJxsqsDao.saveSqsRelation(unUpdatedRelation);
            }
        }
        return true;
    }
    private Boolean IsExistSqsRelation(ProvJxsqsGoods provJxsqsGoods) throws Exception{
        if(StringUtils.isEmpty(provJxsqsGoods.getSqsLastlevelId())) return false;
        long res =simpleDao.checkExists(ProvJxsqsGoods.class,"sqsId,goodsId,sqsLastlevelId",provJxsqsGoods.getSqsId(),provJxsqsGoods.getGoodsId(),provJxsqsGoods.getSqsLastlevelId());
        return res >0;
    }
    private Boolean IsExistSqsHos(ProvJxsqsHos provJxsqsHos)throws Exception{
       return simpleDao.checkExists(ProvJxsqsHos.class,"sqsId,hosId",provJxsqsHos.getSqsId(),provJxsqsHos.getHosId()) > 0;
    }
    private Integer saveSqsHos(List<ProvJxsqsHos> lst)throws Exception{
        List<ProvJxsqsHos> lstUnInsert = new ArrayList<>();
        for (ProvJxsqsHos provJxsqsHos : lst) {
            if(StringUtils.isEmpty(provJxsqsHos.getSqsId()) || StringUtils.isEmpty(provJxsqsHos.getHosId())){
                throw new Exception("授权书或医院为空！");
            }
            if(!IsExistSqsHos(provJxsqsHos)){
                provJxsqsHos.setId(utilsContext.getSysAtomUtil().newValue("prov_jxsqs_hos_id"));
                lstUnInsert.add(provJxsqsHos);
            }
        }
        if(lstUnInsert.size()>0){
          return provJxsqsDao.saveSqsHos(lstUnInsert);
        }
        return 0;
    }

    @Override
    public ProvJxsqsNVo getSqsRelationById(String sqsId) throws Exception {
        return  provJxsqsDao.getSqsRelationById(sqsId);
    }

    @Override
    public ProvJxsqsNVo delRelationAndGet(List<ProvJxsqsGoods> lst,String curSqsId) throws Exception {
        provJxsqsDao.saveSqsRelation(lst);
        return this.getSqsRelationById(curSqsId);
    }

    @Override
    public ProvJxsqsNVo saveRelation(List<ProvJxsqsGoods> lst, String sqsId) throws Exception {
        provJxsqsDao.saveSqsRelation(lst);
        return this.getSqsRelationById(sqsId);
    }

    @Override
    public List<ProvJxsqsHosVo> getSqsHosLst(String sqsId) throws Exception {
        if(StringUtils.isEmpty(sqsId)){
            throw new Exception("授权书ID不能为空");
        }
        return provJxsqsDao.getSqsHosLst(sqsId);
    }

    @Override
    public Integer saveSqsHosLst(List<ProvJxsqsHos> lst) throws Exception {

        return this.saveSqsHos(lst);
    }
    @Override
    public Integer delSqsHos(ProvJxsqsHos provJxsqsHos) throws Exception{
        return provJxsqsDao.delSqsHos(provJxsqsHos);
    }
}
