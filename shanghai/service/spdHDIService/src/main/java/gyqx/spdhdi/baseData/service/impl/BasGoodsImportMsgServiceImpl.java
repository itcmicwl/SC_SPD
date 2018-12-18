package gyqx.spdhdi.baseData.service.impl;

import common.utils.UtilsContext;
import gyqx.mdqc.po.CollectorProvCert;
import gyqx.spdhdi.baseData.dao.mapper.BasGoodsImportMsgMapper;
import gyqx.spdhdi.baseData.service.BasGoodsImportMsgService;
import gyqx.spdhdi.baseData.vo.BasGoodsImportMsg;
import gyqx.spdhdi.po.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author liangwu
 * @Date 12/14/18 2:42 PM
 */
@Service
@Slf4j
public class BasGoodsImportMsgServiceImpl implements BasGoodsImportMsgService {
    private static final int BATCH_SIZE = 100;
    private static final String UXID = "import";

    @Resource
    private UtilsContext utilsContext;

    @Resource
    private BasGoodsImportMsgMapper mapper;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Transactional
    @Override
    public void handleMsg(BasGoodsImportMsg basGoodsImportMsg) {
        String provId = basGoodsImportMsg.getProvId();
        List<String> mfrsIdList = basGoodsImportMsg.getMfrsList();
        List<String> provIdList = basGoodsImportMsg.getProvList();

        // 未关联厂家ID列表 已关联的生产厂家不更新，更新由JDE部分完成。只新增未关联的
        ProvMfrsInfoDto provMfrsInfoDto = genProvMfrsInfoDto(provId, mfrsIdList);

        // 未关联供应商ID列表 已关联的供应商不更新，更新由JDE部分完成。只新增未关联的
        ProvProvInfoDto provProvInfoDto = genProvProvIndoDto(provId, provIdList);

        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            saveBatch(sqlSession, "gyqx.spdhdi.baseData.dao.mapper.BasGoodsImportMsgMapper.insertProvMfrsInfo", provMfrsInfoDto.getProvMfrsInfoList());
            saveBatch(sqlSession, "gyqx.spdhdi.baseData.dao.mapper.BasGoodsImportMsgMapper.insertProvMfrsCert", provMfrsInfoDto.getProvMfrsCertList());
            saveBatch(sqlSession, "gyqx.spdhdi.baseData.dao.mapper.BasGoodsImportMsgMapper.insertProvMfrsCertImg", provMfrsInfoDto.getProvMfrsCertImgList());
            saveBatch(sqlSession, "gyqx.spdhdi.baseData.dao.mapper.BasGoodsImportMsgMapper.insertProvProvInfo", provProvInfoDto.getCollectorProvCertList());
            saveBatch(sqlSession, "gyqx.spdhdi.baseData.dao.mapper.BasGoodsImportMsgMapper.insertProvProvCert", provProvInfoDto.getCollectorProvCertificateList());
            saveBatch(sqlSession, "gyqx.spdhdi.baseData.dao.mapper.BasGoodsImportMsgMapper.insertProvProvCertImg", provProvInfoDto.getProvCertificateImageList());
        }
    }

    private boolean saveBatch(SqlSession sqlSession, String sqlMethod, Collection<?> collection) {
        int i = 0;
        for (Object param : collection) {
            sqlSession.insert(sqlMethod, param);
            if (i >= BATCH_SIZE) {
                sqlSession.flushStatements();
                i = 0;
            }
            i++;
        }
        sqlSession.flushStatements();
        return true;
    }

    /**
     * 供应商关联关系，及企业资质和附件信息
     *
     * @param provId     集配商ID
     * @param provIdList 供应商Id列表
     * @return 返回dto包括三个表的列表
     */
    private ProvProvInfoDto genProvProvIndoDto(String provId, List<String> provIdList) {
        ProvProvInfoDto provProvInfoDto = new ProvProvInfoDto();
        List<String> provIdUnrelList = getUnrelProvIdList(provId, provIdList);
        int provIdUnrelSize = provIdUnrelList.size();
        List<String> provGenIds = utilsContext.getSysAtomUtil().newId("collector_prov_cert_id").getIds(provIdUnrelSize);
        for (int i = 0; i < provIdUnrelSize; i++) {
            String provIdUnrel = provIdUnrelList.get(i);
            CollectorProvCert collectorProvCert = new CollectorProvCert();
            collectorProvCert.setId(provGenIds.get(i));
            collectorProvCert.setCollectorId(provId);
            collectorProvCert.setProvId(provIdUnrel);
            collectorProvCert.setFlag("1");
            collectorProvCert.setRemark(UXID);
            collectorProvCert.setUxid(UXID);
            provProvInfoDto.getCollectorProvCertList().add(collectorProvCert);

            List<BasCompanyCertificate> basProvCertList = mapper.listCompanyCert(provIdUnrel);
            int basProvCertSize = basProvCertList.size();
            List<String> provProvCertIds = utilsContext.getSysAtomUtil().newId("collector_prov_certificate_id").getIds(basProvCertSize);
            for (int j = 0; j < basProvCertSize; j++) {
                String provProvCertId = provProvCertIds.get(j);
                BasCompanyCertificate provCert = basProvCertList.get(j);
                CollectorProvCertificate provProvCert = new CollectorProvCertificate();
                BeanUtils.copyProperties(provCert, provProvCert);
                provProvCert.setId(provProvCertId);
                provProvCert.setPid(collectorProvCert.getId());
                provProvCert.setProvId(provCert.getCompanyId());
                provProvCert.setUxid(UXID);
                provProvInfoDto.getCollectorProvCertificateList().add(provProvCert);

                List<BasCompanyCertificateImage> basProvCertImgList = mapper.listCompanyCertImg(provCert.getId());
                int basProvCertImgSize = basProvCertImgList.size();
                List<String> provProvCertImgIds = utilsContext.getSysAtomUtil().newId("prov_certificate_image_id").getIds(basProvCertImgSize);
                for (int k = 0; k < basProvCertImgSize; k++) {
                    String provProvCertImgId = provProvCertImgIds.get(k);
                    BasCompanyCertificateImage provCertImg = basProvCertImgList.get(k);
                    ProvCertificateImage provProvCertImg = new ProvCertificateImage();
                    BeanUtils.copyProperties(provCertImg, provProvCertImg);
                    provProvCertImg.setId(provProvCertImgId);
                    provProvCertImg.setCertificateId(provProvCert.getId());
                    provProvCertImg.setUxid(UXID);
                    provProvInfoDto.getProvCertificateImageList().add(provProvCertImg);
                }
            }
        }
        return provProvInfoDto;
    }

    /**
     * 生成供应商厂家关联关系，及企业资质和附件信息
     *
     * @param provId     集配商ID
     * @param mfrsIdList 生产厂商Id列表
     * @return 返回dto包括三个表的列表
     */
    private ProvMfrsInfoDto genProvMfrsInfoDto(String provId, List<String> mfrsIdList) {
        ProvMfrsInfoDto provMfrsInfoDto = new ProvMfrsInfoDto();
        List<String> mfrsIdUnrelList = getUnrelMfrsIdList(provId, mfrsIdList);
        int mfrsIdUnrelSize = mfrsIdUnrelList.size();
        List<String> provGenIds = utilsContext.getSysAtomUtil().newId("prov_mfrs_info_id").getIds(mfrsIdUnrelSize);
        // 主表
        for (int i = 0; i < mfrsIdUnrelSize; i++) {
            String mfrsIdUnrel = mfrsIdUnrelList.get(i);
            ProvMfrsInfo provMfrsInfo = new ProvMfrsInfo();
            provMfrsInfo.setId(provGenIds.get(i));
            provMfrsInfo.setProvId(provId);
            provMfrsInfo.setMfrsId(mfrsIdUnrel);
            provMfrsInfo.setFlag("1");
            provMfrsInfo.setUxid(UXID);
            provMfrsInfoDto.getProvMfrsInfoList().add(provMfrsInfo);

            List<BasCompanyCertificate> basMfrsCertList = mapper.listCompanyCert(mfrsIdUnrel);
            int basMfrsCertSize = basMfrsCertList.size();
            List<String> provMfrsCertIds = utilsContext.getSysAtomUtil().newId("prov_mfrs_cert_id").getIds(basMfrsCertSize);
            // 子表
            for (int j = 0; j < basMfrsCertSize; j++) {
                String provMfrsCertId = provMfrsCertIds.get(j);
                BasCompanyCertificate mfrsCert = basMfrsCertList.get(j);
                ProvMfrsCertificate provMfrsCert = new ProvMfrsCertificate();
                BeanUtils.copyProperties(mfrsCert, provMfrsCert);
                provMfrsCert.setPId(provMfrsInfo.getId());
                provMfrsCert.setId(provMfrsCertId);
                provMfrsCert.setMfrsId(mfrsCert.getCompanyId());
                provMfrsCert.setUxid(UXID);
                provMfrsInfoDto.getProvMfrsCertList().add(provMfrsCert);

                List<BasCompanyCertificateImage> basMfrsCertImgList = mapper.listCompanyCertImg(mfrsCert.getId());
                int basMfrsCertImgSize = basMfrsCertImgList.size();
                List<String> provMfrsCertImgIds = utilsContext.getSysAtomUtil().newId("prov_mfrs_cert_image_id").getIds(basMfrsCertImgSize);
                // 附件表
                for (int k = 0; k < basMfrsCertImgSize; k++) {
                    String provMfrsCertImgId = provMfrsCertImgIds.get(k);
                    BasCompanyCertificateImage mfrsCertImg = basMfrsCertImgList.get(k);
                    ProvMfrsCertificateImage provMfrsCertImg = new ProvMfrsCertificateImage();
                    BeanUtils.copyProperties(mfrsCertImg, provMfrsCertImg);
                    provMfrsCertImg.setId(provMfrsCertImgId);
                    provMfrsCertImg.setCertificateId(provMfrsCert.getId());
                    provMfrsCertImg.setUxid(UXID);
                    provMfrsInfoDto.getProvMfrsCertImgList().add(provMfrsCertImg);
                }
            }
        }
        return provMfrsInfoDto;
    }

    private List<String> getUnrelProvIdList(String provId, List<String> provIdList) {
        List<String> provIdUnrelList = new ArrayList<>(provIdList.size());
        for (String prov : provIdList) {
            CollectorProvCert collectorProvCert = mapper.selectProvProv(provId, prov);
            if (collectorProvCert == null) {
                provIdUnrelList.add(prov);
            }
        }
        return provIdUnrelList;
    }

    private List<String> getUnrelMfrsIdList(String provId, List<String> mfrsIdList) {
        List<String> mfrsIdUnrelList = new ArrayList<>(mfrsIdList.size());
        for (String mfrsId : mfrsIdList) {
            ProvMfrsInfo provMfrsInfo = mapper.selectProvMfrs(provId, mfrsId);
            if (provMfrsInfo == null) {
                mfrsIdUnrelList.add(mfrsId);
            }
        }
        return mfrsIdUnrelList;
    }

    @Data
    private class ProvMfrsInfoDto {
        private List<ProvMfrsInfo> provMfrsInfoList;
        private List<ProvMfrsCertificate> provMfrsCertList;
        private List<ProvMfrsCertificateImage> provMfrsCertImgList;

        ProvMfrsInfoDto() {
            provMfrsInfoList = new ArrayList<>();
            provMfrsCertList = new ArrayList<>();
            provMfrsCertImgList = new ArrayList<>();
        }
    }

    @Data
    private class ProvProvInfoDto {
        private List<CollectorProvCert> collectorProvCertList;
        private List<CollectorProvCertificate> collectorProvCertificateList;
        private List<ProvCertificateImage> provCertificateImageList;

        public ProvProvInfoDto() {
            collectorProvCertList = new ArrayList<>();
            collectorProvCertificateList = new ArrayList<>();
            provCertificateImageList = new ArrayList<>();
        }
    }
}
