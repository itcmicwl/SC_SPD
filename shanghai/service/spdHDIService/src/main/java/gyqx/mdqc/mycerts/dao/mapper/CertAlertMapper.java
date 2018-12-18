package gyqx.mdqc.mycerts.dao.mapper;

import gyqx.mdqc.mycerts.vo.BasRegistrationInfoVo;
import gyqx.mdqc.mycerts.vo.IdNameVo;
import gyqx.mdqc.mycerts.vo.JxsqsData;
import gyqx.spdhdi.myInfos.vo.CompanyCertificateVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @Author liangwu
 * @Date 18-8-30 下午3:58
 */
public interface CertAlertMapper {
    List<BasRegistrationInfoVo> listRegCertByHos(
            @Param("start") Date start,
            @Param("end") Date end,
            @Param("isOld") String isOld,
            @Param("aboutName") String aboutName,
            @Param("hosId") String hosId,
            @Param("page") int pageNum,
            @Param("rows") int pageSize);

    List<BasRegistrationInfoVo> listRegCertByProv(@Param("start") Date start,
                                                  @Param("end") Date end,
                                                  @Param("isOld") String isOld,
                                                  @Param("aboutName") String aboutName,
                                                  @Param("provId") String provId,
                                                  @Param("page") int pageNum,
                                                  @Param("rows") int pageSize);

    List<BasRegistrationInfoVo> listRegCertByMfrs(@Param("start") Date start,
                                                  @Param("end") Date end,
                                                  @Param("isOld") String isOld,
                                                  @Param("aboutName") String aboutName,
                                                  @Param("mfrsId") String mfrsId,
                                                  @Param("page") int pageNum,
                                                  @Param("rows") int pageSize);

    List<JxsqsData> listSqsByProv(@Param("start") Date start,
                                  @Param("end") Date end,
                                  @Param("aboutName") String aboutName,
                                  @Param("provId") String provId);

    List<JxsqsData> listSqsByHos(@Param("start") Date start,
                                 @Param("end") Date end,
                                 @Param("aboutName") String aboutName,
                                 @Param("hosId") String hosId);

    List<JxsqsData> listSqsByMfrs(@Param("start") Date start,
                                  @Param("end") Date end,
                                  @Param("aboutName") String aboutName,
                                  @Param("mfrsId") String mfrsId);


    List<CompanyCertificateVo> listProvCertByProv(@Param("compId") String collectorId,
                                                  @Param("start") Date start,
                                                  @Param("end") Date end,
                                                  @Param("aboutName") String aboutName);

    List<CompanyCertificateVo> listProvCertByHos(@Param("hosId") String hosId,
                                                 @Param("start") Date start,
                                                 @Param("end") Date end,
                                                 @Param("aboutName") String aboutName);

    List<CompanyCertificateVo> listCompCertById(@Param("compId") String compId,
                                                @Param("start") Date start,
                                                @Param("end") Date end,
                                                @Param("aboutName") String aboutName);


    List<ProvMfrsCertificateVo> listMfrsCertByProv(@Param("start") Date start,
                                                   @Param("end") Date end,
                                                   @Param("aboutName") String aboutName,
                                                   @Param("provId") String provId);

}
