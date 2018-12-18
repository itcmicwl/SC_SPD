
package gyqx.mdqc.mycerts.service;

import common.db.query.QueryResult;
import gyqx.mdqc.mycerts.vo.BasRegistrationInfoVo;
import gyqx.mdqc.mycerts.vo.JxsqsData;
import gyqx.spdhdi.myInfos.vo.CompanyCertificateVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;

import java.util.Date;

public interface CertAlertService {

    QueryResult<BasRegistrationInfoVo> listRegCertByHos(Date start, Date end, String isOld, String aboutName, int pageNum, int pageSize);

    QueryResult<BasRegistrationInfoVo> listRegCertByProv(Date start, Date end, String isOld, String aboutName, int pageNum,int pageSize);

    QueryResult<BasRegistrationInfoVo> listRegCertByMfrs(Date start, Date end, String isOld, String aboutName, int pageNum,int pageSize);

    QueryResult<BasRegistrationInfoVo> listRegCertByCompId(Date start, Date end, String isOld, String aboutName, String compKind, int pageNum, int pageSize);

    QueryResult<JxsqsData> listSqsByLoginComp(Date start, Date end, String aboutName, String compKind, int pageNum, int pageSize);

    QueryResult<CompanyCertificateVo> listProvCertByLoginComp(Date start, Date end, String aboutName, String compKind, int pageNum, int pageSize);

    QueryResult<CompanyCertificateVo> listCompCertById(Date start, Date end, String aboutName, int pageNum, int pageSize);

    QueryResult<ProvMfrsCertificateVo> listMfrsCertByProv(Date start, Date end, String aboutName, String compKind, int pageNum, int pageSize);

}
