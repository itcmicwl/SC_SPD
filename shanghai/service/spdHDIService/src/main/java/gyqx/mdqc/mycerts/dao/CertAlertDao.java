package gyqx.mdqc.mycerts.dao;

import common.db.query.QueryResult;
import common.utils.PageUtils;
import gyqx.mdqc.mycerts.Constance;
import gyqx.mdqc.mycerts.dao.mapper.CertAlertMapper;
import gyqx.mdqc.mycerts.vo.BasRegistrationInfoVo;
import gyqx.mdqc.mycerts.vo.JxsqsData;
import gyqx.spdhdi.myInfos.vo.CompanyCertificateVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CertAlertDao {
    @Resource
    private CertAlertMapper mapper;

    public List<BasRegistrationInfoVo> listRegCertByHos(Date Start, Date end, String isOld, String aboutName, String hosId, int pageNum, int pageSize){
        return mapper.listRegCertByHos(Start, end, isOld,aboutName, hosId, pageNum, pageSize);
    }

    public List<BasRegistrationInfoVo> listRegCertByProv(Date Start, Date end, String isOld,String aboutName, String provId, int pageNum,int pageSize){
        return mapper.listRegCertByProv(Start, end, isOld,aboutName, provId, pageNum,pageSize);
    }

    public List<BasRegistrationInfoVo> listRegCertByMfrs(Date Start, Date end, String isOld,String aboutName, String hosId, int pageNum, int pageSize){
        return mapper.listRegCertByMfrs(Start, end, isOld,aboutName, hosId, pageNum,pageSize);
    }

    public QueryResult<JxsqsData> listSqsByLoginComp(Date start, Date end, String aboutName, String corpId, String compKind, int pageNum, int pageSize){
        QueryResult<JxsqsData> result = null;

        switch (compKind){
            case Constance.PROV:
                PageUtils.startPage(pageNum, pageSize);
                result = PageUtils.endPage(mapper.listSqsByProv(start, end, aboutName, corpId));
                break;
            case Constance.MFRS_PROV:
                List<JxsqsData> jxsqsList = new ArrayList<>();
                List<JxsqsData> provSqsList = mapper.listSqsByProv(start, end, aboutName, corpId);
                List<JxsqsData> mfrsSqsList = mapper.listSqsByMfrs(start, end, aboutName, corpId);
                jxsqsList.addAll(provSqsList);
                jxsqsList.addAll(mfrsSqsList);

                List<JxsqsData> unique = jxsqsList.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(JxsqsData::getId))), ArrayList::new));

                PageUtils.startPage(pageNum, pageSize);
                result = PageUtils.endPage(unique);

                break;
            case Constance.HOS:
                PageUtils.startPage(pageNum, pageSize);
                result = PageUtils.endPage(mapper.listSqsByHos(start, end, aboutName, corpId));
                break;
            case Constance.MFRS:
                PageUtils.startPage(pageNum, pageSize);
                result = PageUtils.endPage(mapper.listSqsByMfrs(start, end, aboutName, corpId));
                break;
            default:
                throw new IllegalArgumentException("非法查询");
        }
        return result;
    }

    public QueryResult<CompanyCertificateVo> listProvCertByLoginComp(Date start, Date end, String aboutName, String corpId, String compKind, int pageNum, int pageSize){
        QueryResult<CompanyCertificateVo> result = null;
        switch (compKind){
            case Constance.PROV:
            case Constance.MFRS_PROV:
                PageUtils.startPage(pageNum, pageSize);
                result = PageUtils.endPage(mapper.listProvCertByProv(corpId, start, end, aboutName));
                break;
            case Constance.HOS:
                PageUtils.startPage(pageNum, pageSize);
                result = PageUtils.endPage(mapper.listProvCertByHos(corpId,start, end, aboutName));
                break;
            default:
                throw new IllegalArgumentException("非法查询");
        }
        return result;
    }

    public QueryResult<CompanyCertificateVo> listCompCertById(Date start, Date end, String aboutName, String corpId, int pageNum, int pageSize){
        PageUtils.startPage(pageNum, pageSize);
        return PageUtils.endPage(mapper.listCompCertById(corpId, start, end, aboutName));
    }

    public QueryResult<ProvMfrsCertificateVo> listMfrsCertByProv(Date start, Date end, String aboutName, String corpId, String compKind, int pageNum, int pageSize){
        QueryResult<ProvMfrsCertificateVo> result = null;
        switch (compKind){
            case Constance.PROV:
            case Constance.MFRS_PROV:
                PageUtils.startPage(pageNum, pageSize);
                result = PageUtils.endPage(mapper.listMfrsCertByProv(start, end, aboutName, corpId));
                break;
            default:
                throw new IllegalArgumentException("非法查询");
        }
        return result;
    }

}
