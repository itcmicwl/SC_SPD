package gyqx.spdhdi.mycerts.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import common.db.SimpleDao;
import common.utils.UtilsContext;
import gyqx.spdhdi.mycerts.dao.mapper.ProvJxsqsMapper;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvJxsqsHosVo;
import gyqx.spdhdi.mycerts.vo.ProvJxsqsNVo;
import gyqx.spdhdi.mycerts.vo.ProvJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvRegInfoJxsqsVo;
import gyqx.spdhdi.po.ProvJxsqs;
import gyqx.spdhdi.po.ProvJxsqsGoods;
import gyqx.spdhdi.po.ProvJxsqsHos;
import gyqx.spdhdi.po.ProvJxsqsRegist;
import gyqx.spdhdi.po.ProvJxsqsZjzj;

/**
 * Created by liangwu on 17-6-21.
 */
@Repository
public class ProvJxsqsDao {
    @Resource
    private ProvJxsqsMapper mapper;

    @Resource
    private SimpleDao simpleDao;

    @Resource
    private UtilsContext utilsContext;

    public List<ProvRegInfoJxsqsVo> getProvRegInfo(String provId, String mfrsId, String certName, String sqsId, int pageNum) {
        certName = certName.replaceAll("%", "\\\\%");
        return mapper.getProvRegInfo(provId, mfrsId, certName, sqsId, pageNum, 15);
    }

    public List<BasCompanyInfoJxsqsVo> getBasHos(String hosName, String sqsId, int pageNum) {
        return mapper.getBasHos(hosName, sqsId, pageNum, 5);
    }

    public void addJxsqs(ProvJxsqs provJxsqs, String sqsId, String uid) throws Exception {
        provJxsqs.setId(sqsId);
        provJxsqs.setUid(uid);
        provJxsqs.setFillDate(new Date());
        provJxsqs.setLastUpdateDatetime(new Date());
        provJxsqs.setVersion(0);
        simpleDao.insert(provJxsqs);
    }

    public void addProvJxsqsHos(Collection<BasCompanyInfoJxsqsVo> hosCollection, String sqsId, String provId, String uid) {
        List<ProvJxsqsHos> hosList = new ArrayList<>();
        for (BasCompanyInfoJxsqsVo item : hosCollection) {
            ProvJxsqsHos hos = new ProvJxsqsHos();
            hos.setId(utilsContext.getSysAtomUtil().newValue("prov_jxsqs_hos_id"));
            hos.setSqsId(sqsId);
            hos.setProvId(provId);
            hos.setHosId(item.getId());
            hos.setUid(uid);
            hos.setFillDate(new Date());
            hos.setLastUpdateDatetime(new Date());
            hos.setVersion(0);
            hosList.add(hos);
        }
        mapper.insertProvJxsqsHost(hosList);
    }

    public void addProvJxsqsRegist(Collection<ProvRegInfoJxsqsVo> registCollection, String sqsId, String provId, String mfrsId, String uid) {
        List<ProvJxsqsRegist> regists = new ArrayList<>();
        for (ProvRegInfoJxsqsVo item : registCollection) {
            ProvJxsqsRegist regist = new ProvJxsqsRegist();
            regist.setId(utilsContext.getSysAtomUtil().newValue("prov_jxsqs_regist_id"));
            regist.setSqsId(sqsId);
            regist.setProvId(provId);
            regist.setMfrsId(mfrsId);
            regist.setCertificateId(item.getId());
            regist.setUid(uid);
            regist.setFillDate(new Date());
            regist.setLastUpdateDatetime(new Date());
            regist.setVersion(0);
            regists.add(regist);
        }
        mapper.insertProvJxsqsRegist(regists);
    }

    public List<ProvJxsqsVo> getJxsqs(String provId, Date start, Date end, String mfrsName, int pageNum, int pageSize) {
        List<ProvJxsqsVo> provJxsqsVoList = mapper.getJxsqs(provId, start, end, mfrsName, pageNum, pageSize);
        return provJxsqsVoList;
    }

    public int modJxsqs(ProvJxsqs provJxsqs) throws Exception {
        provJxsqs.setLastUpdateDatetime(new Date());
        simpleDao.updateNotNullFields(provJxsqs);
        return simpleDao.getByFieldName(provJxsqs.getClass(), "id", provJxsqs.getId()).getVersion();
    }

    public void delProvJxsqsHos(Collection<BasCompanyInfoJxsqsVo> delHos, String sqsId, String provId) {
        mapper.delProvJxsqsHos(delHos, sqsId, provId);
    }

    public void delProvJxsqsRegist(Collection<ProvRegInfoJxsqsVo> delCerts, String sqsId, String provId, String mfrsId) {
        mapper.delProvJxsqsRegist(delCerts, sqsId, provId, mfrsId);
    }

    public String delProvJxsqs(String id) {
        mapper.delProvJxsqsHosBySqsId(id);
        mapper.delProvJxsqsRegistBySqsId(id);
        mapper.delProvJxsqsZjzjBySqsId(id);
        return "0";
    }

    public List<String> getZjzjCompanyLikeName(String name, int pageNum, int pageSize) {
        name = name.replaceAll("%", "\\\\%");
        return mapper.getZjzjCompanyLikeName(name, pageNum, pageSize);
    }

    public void addProvJxsqsZjzj(List<ProvJxsqsZjzj> zjzjList, String sqsId) {
        for (ProvJxsqsZjzj zjzj : zjzjList) {
            zjzj.setId(utilsContext.getSysAtomUtil().newValue("prov_jxsqs_zjzj_id"));
            zjzj.setPId(sqsId);
        }
        mapper.insertProvJxsqsZjzj(zjzjList);
    }

    public List<ProvJxsqsNVo> getJxsqsList(ProvJxsqsGoods query){
        return mapper.getJxsqsList(query);
    }
    public Integer saveSqsRelation(List<ProvJxsqsGoods> lst){
        return mapper.saveSqsRelation(lst);
    }
    public Integer saveSqsHos(List<ProvJxsqsHos> lst){
        return mapper.saveSqsHos(lst);
    }
    public ProvJxsqsNVo getSqsRelationById(String sqsId){
        List<ProvJxsqsNVo> lst = mapper.getSqsRelationById(sqsId);
        ProvJxsqsNVo res = lst.stream().filter(item->item.getId().equals(sqsId)).findFirst().get();
        List<ProvJxsqsNVo> children = this.getNodeChildren(lst,res);
        if(!CollectionUtils.isEmpty(children)){
            res.setChildren(children);
        }
        return res;
    }
    private List<ProvJxsqsNVo> getNodeChildren(List<ProvJxsqsNVo> lst,ProvJxsqsNVo node){
        Set<String> lastSqsIds = node.getLstDetail().stream().filter(item->!StringUtils.isEmpty(item.getSqsLastlevelId()))
                .map(o->{return o.getSqsLastlevelId();}).collect(Collectors.toSet());
        List<ProvJxsqsNVo> lstRes = lst.stream().filter(item->lastSqsIds.contains(item.getId())).collect(Collectors.toList());
        for (ProvJxsqsNVo child : lstRes) {
            List<ProvJxsqsNVo> children = this.getNodeChildren(lst,child);
            if(!CollectionUtils.isEmpty(children)){
                child.setChildren(children);
            }
        }
        return lstRes;
    }
    public Integer delSqsHos(ProvJxsqsHos provJxsqsHos) throws Exception{
        return mapper.delSqsHos(provJxsqsHos);
    }
    public List<ProvJxsqsHosVo> getSqsHosLst(String sqsId) throws Exception{
        return  mapper.getSqsHosLst(sqsId);
    }
}
