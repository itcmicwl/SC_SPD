package gyqx.mdqc.mycerts.service.impl;

import common.db.query.QueryResult;
import common.utils.PageUtils;
import common.utils.UtilsContext;
import gyqx.mdqc.mycerts.dao.CertAlertDao;
import gyqx.mdqc.mycerts.service.CertAlertService;
import gyqx.mdqc.mycerts.vo.BasRegistrationInfoVo;
import gyqx.mdqc.mycerts.vo.JxsqsData;
import gyqx.spdhdi.myInfos.vo.CompanyCertificateVo;
import gyqx.spdhdi.mycerts.Constants;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CertAlertServiceImpl implements CertAlertService {
    @Resource
    private UtilsContext utilsContext;

    @Resource
    private CertAlertDao certAlertDao;

    public QueryResult<BasRegistrationInfoVo> listRegCertByHos(Date start, Date end, String isOld, String aboutName, int pageNum, int pageSize){
        String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageUtils.startPage(pageNum, pageSize);
        return PageUtils.endPage(certAlertDao.listRegCertByHos(start, end, isOld, aboutName, hosId, pageNum, pageSize));
    }

    public QueryResult<BasRegistrationInfoVo> listRegCertByProv(Date start, Date end, String isOld, String aboutName, int pageNum,int pageSize){
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageUtils.startPage(pageNum, pageSize);
        return PageUtils.endPage(certAlertDao.listRegCertByProv(start, end, isOld, aboutName, provId, pageNum, pageSize));
    }

    public QueryResult<BasRegistrationInfoVo> listRegCertByMfrs(Date start,Date end, String isOld, String aboutName, int pageNum,int pageSize){
        String mfrsId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageUtils.startPage(pageNum, pageSize);
        return PageUtils.endPage(certAlertDao.listRegCertByMfrs(start, end, isOld, aboutName, mfrsId, pageNum, pageSize));
    }

    public QueryResult<BasRegistrationInfoVo> listRegCertByCompId(Date start,Date end, String isOld, String aboutName,String compKind, int pageNum,int pageSize){
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageUtils.startPage(pageNum, pageSize);
        if(Constants.COMP_KIND_PROV.equals(compKind)){
            return listRegCertByProv(start, end, isOld, aboutName, pageNum,pageSize);
        } else if(Constants.COMP_KIND_HOS.equals(compKind)) {
            return listRegCertByHos(start, end, isOld, aboutName, pageNum,pageSize);
        } else if(Constants.COMP_KIND_MFRS.equals(compKind)){
            return listRegCertByMfrs(start, end, isOld, aboutName, pageNum,pageSize);
        } else if (Constants.COMP_KIND_PROV_MFRS.equals(compKind)){
           /* List<BasRegistrationInfoVo> result = certAlertDao.listRegCertByProv(start, end, isOld, aboutName, provId, pageNum, pageSize);
            result.addAll(certAlertDao.listRegCertByMfrs(start, end, isOld, aboutName, provId, pageNum, pageSize));
            PageUtils.startPage(pageNum, pageSize);
            return PageUtils.endPage(result);*/
            // 删除重复的

            List<BasRegistrationInfoVo> result = new ArrayList<>();

            // 根据厂商id从bas表获取产品注册证
            List<BasRegistrationInfoVo> mfrsList = certAlertDao.listRegCertByMfrs(start, end, isOld, aboutName, provId, pageNum, pageSize);
            if(mfrsList != null && mfrsList.size() > 0){
                List<BasRegistrationInfoVo> uniqueMfrsList = mfrsList.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BasRegistrationInfoVo::getCertificateCode))), ArrayList::new));
                result.addAll(uniqueMfrsList);
            }

            // 从供应商注册证表获取注册证信息
            List<BasRegistrationInfoVo> provList = certAlertDao.listRegCertByProv(start, end, isOld, aboutName, provId, pageNum, pageSize);

            // 注册号重复的情况，以bas基本为准
            if(result != null && result.size() == 0){
                result.addAll(provList);
            } else if(provList != null && provList.size() > 0){
                for (BasRegistrationInfoVo provRegInfo : provList) {
                    if(hasReg(result, provRegInfo)){
                        continue;
                    } else {
                        result.add(provRegInfo);
                    }
                }
            }

            return PageUtils.endPage(result);

        }
        return null;
    }

    private boolean hasReg(List<BasRegistrationInfoVo> list, BasRegistrationInfoVo reg){
        if(list != null && reg != null){
            for (BasRegistrationInfoVo provRegInfo : list) {
                if(provRegInfo != null && reg != null && provRegInfo.getCertificateCode() != null && reg.getCertificateCode() != null){
                    if(provRegInfo.getCertificateCode().equals(reg.getCertificateCode())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public QueryResult<JxsqsData> listSqsByLoginComp(Date start, Date end, String aboutName, String compKind, int pageNum, int pageSize){
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return certAlertDao.listSqsByLoginComp(start, end, aboutName, corpId, compKind, pageNum, pageSize);
    }

    public QueryResult<CompanyCertificateVo> listProvCertByLoginComp(Date start, Date end, String aboutName, String compKind, int pageNum, int pageSize){
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return certAlertDao.listProvCertByLoginComp(start, end, aboutName, corpId, compKind, pageNum, pageSize);
    }

    public QueryResult<CompanyCertificateVo> listCompCertById(Date start, Date end, String aboutName, int pageNum, int pageSize){
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return certAlertDao.listCompCertById(start, end, aboutName, corpId, pageNum, pageSize);
    }

    public QueryResult<ProvMfrsCertificateVo> listMfrsCertByProv(Date start, Date end, String aboutName, String compKind, int pageNum, int pageSize){
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return certAlertDao.listMfrsCertByProv(start, end, aboutName, corpId, compKind, pageNum, pageSize);
    }
}
