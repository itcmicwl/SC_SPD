package gyqx.spdhdi.mycerts.service.impl;

import com.github.pagehelper.PageInfo;
import common.db.SimpleDao;
import common.db.query.QueryResult;
import common.exception.ParameterException;
import common.transform.Tx;
import common.utils.DictionaryUtils;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;
import gyqx.spdhdi.mycerts.dao.ProvMfrsInfoDao;
import gyqx.spdhdi.mycerts.service.ProvMfrsInfoService;
import gyqx.spdhdi.mycerts.vo.BasMfrsInfoMyCertsVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsInfoVo;
import gyqx.spdhdi.po.ProvMfrsCertificate;
import gyqx.spdhdi.po.ProvMfrsCertificateImage;
import gyqx.spdhdi.po.ProvMfrsInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liangwu on 17-6-19.
 */
@Service
public class ProvMfrsInfoServiceImpl implements ProvMfrsInfoService {
    @Resource
    private ProvMfrsInfoDao provMfrsInfoDao;

    @Resource
    private SimpleDao simpleDao;

    @Resource
    private UtilsContext utilsContext;

    @Override
    public List<DictionaryUtils.DictionaryValue> findCjlxDicts() {
        List<DictionaryUtils.DictionaryValue> dicts = utilsContext.getDictUtils().getDictItems("CJLX");
        return dicts;
    }

    @Override
    public QueryResult<ProvMfrsInfoVo> findMfrsInfo(int searchType, String searchText, int pageNum, int pageSize) {
        pageSize = pageSize > 30 ? 30 : (pageSize < 10 ? 10 : pageSize);
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        searchText = searchText.replaceAll("%", "\\\\%");
        PageInfo<ProvMfrsInfoVo> page = new PageInfo<>(provMfrsInfoDao.getMfrsInfo(provId, searchType, searchText, pageNum, pageSize));
        QueryResult<ProvMfrsInfoVo> result = new QueryResult<>();
        result.setData(page.getList());
        result.setTotal(page.getTotal());
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        return result;
    }

    @Override
    public List<BasMfrsInfoMyCertsVo> findBasMfrs(String mfrsName) {
        int pageNum = 1;
        int pageSize = 10;
        mfrsName = mfrsName.replaceAll("%", "\\\\%");
        return provMfrsInfoDao.getBasMfrs(mfrsName, pageNum, pageSize);
    }

    @Override
    @Transactional
    public String addMfrsInfo(ProvMfrsInfoVo provMfrsInfoVo) throws Exception {
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        String pId = utilsContext.getSysAtomUtil().newValue("prov_mfrs_info_id");
        String provId = user.getCorpId();
        String mfrsId = provMfrsInfoVo.getMfrsId();
        String uId = user.getUserId();

        if (simpleDao.checkExists(ProvMfrsInfo.class, "provId,mfrsId", provId, mfrsId) > 0) {
            throw new ParameterException("已关联厂家，请选择其他厂家或重新启用厂家。");
        }

        ProvMfrsInfo provMfrsInfo = new ProvMfrsInfo();
        Tx.transform(provMfrsInfoVo).to(provMfrsInfo);
        provMfrsInfo.setId(pId);
        provMfrsInfo.setProvId(user.getCorpId());
        provMfrsInfo.setUid(uId);
        provMfrsInfo.setFillDate(new Date());
        provMfrsInfo.setLastUpdateDatetime(new Date());

        provMfrsInfoDao.insertCertList(provMfrsInfoVo.getCertList(), pId, mfrsId, uId);

        simpleDao.insert(provMfrsInfo);

        return pId;
    }

    @Override
    public String deleteMfrsInfo(String id, int version) throws Exception {
        ProvMfrsInfo provMfrsInfo = new ProvMfrsInfo();
        provMfrsInfo.setId(id);
        provMfrsInfo.setVersion(version);
        simpleDao.delete(provMfrsInfo);
        return "OK";
    }

    @Override
    public ProvMfrsInfo updateFlag(ProvMfrsInfo provMfrsInfo) throws Exception {
        provMfrsInfo.setFlag(provMfrsInfo.getFlag().equals("1") ? "1" : "0");
        provMfrsInfo.setLastUpdateDatetime(new Date());
        simpleDao.updateNotNullFields(provMfrsInfo);
        return simpleDao.get(provMfrsInfo.getClass(), provMfrsInfo.getId());
    }

    @Override
    public List<DictionaryUtils.DictionaryValue> findZzlxDicts() {
        List<DictionaryUtils.DictionaryValue> dicts = utilsContext.getDictUtils().getDictItems("ZZLX");
        return dicts;
    }

    @Override
    public List<ProvMfrsCertificateVo> findCertList(String id) {
        return provMfrsInfoDao.getCertList(id);
    }

    @Override
    public String deleteMfrsCertImg(String id) throws Exception {
        ProvMfrsCertificateImage provMfrsCertificateImage = new ProvMfrsCertificateImage();
        provMfrsCertificateImage.setId(id);
        provMfrsCertificateImage.setVersion(0);
        simpleDao.delete(provMfrsCertificateImage);
        return "0";
    }

    @Override
    public Set<ProvMfrsCertificateImage> addPic(ProvMfrsCertificateVo cert) throws Exception {
        return provMfrsInfoDao.insertImageList(cert.getImageList(), cert.getId(), utilsContext.getUserStateUtils().getCurrent().getUserId());
    }

    @Override
    public Integer updateMfrsInfo(ProvMfrsInfoVo provMfrsInfoVo) throws Exception {
        Set<ProvMfrsCertificateVo> modCertList = new HashSet<>();
        Set<ProvMfrsCertificateVo> addCertList = new HashSet<>();
        for (ProvMfrsCertificateVo cert : provMfrsInfoVo.getCertList()) {
            if (cert.getImageList().size() > 0) {
                addCertList.add(cert);
            } else {
                modCertList.add(cert);
            }
        }

        String uId = utilsContext.getUserStateUtils().getCurrent().getUserId();
        if (addCertList.size() > 0)
            provMfrsInfoDao.insertCertList(addCertList, provMfrsInfoVo.getId(), provMfrsInfoVo.getMfrsId(), uId);

        if (modCertList.size() > 0) {
            for (ProvMfrsCertificateVo provMfrsCertificateVo : modCertList) {
                ProvMfrsCertificate cert = new ProvMfrsCertificate();
                Tx.transform(provMfrsCertificateVo).to(cert);
                cert.setUid(uId);
                cert.setLastUpdateDatetime(new Date());
                simpleDao.updateNotNullFields(cert);
            }
        }
        return 0;
    }

    @Override
    public Integer deleteMfrsCert(String id, int version) throws Exception {
        ProvMfrsCertificate provMfrsCertificate = new ProvMfrsCertificate();
        provMfrsCertificate.setId(id);
        provMfrsCertificate.setVersion(version);
        simpleDao.delete(provMfrsCertificate);

        provMfrsInfoDao.delMfrsCertImg(id);
        return 0;
    }
}
