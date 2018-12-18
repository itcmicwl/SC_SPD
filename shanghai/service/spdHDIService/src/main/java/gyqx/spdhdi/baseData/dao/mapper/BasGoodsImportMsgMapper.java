package gyqx.spdhdi.baseData.dao.mapper;

import gyqx.mdqc.po.CollectorProvCert;
import gyqx.spdhdi.po.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author liangwu
 * @Date 12/14/18 3:09 PM
 */
@Mapper
public interface BasGoodsImportMsgMapper {
    @Select("SELECT * FROM PROV_MFRS_INFO WHERE prov_id = #{provId} AND mfrs_id = #{mfrsId} AND ROWNUM = 1 ORDER BY last_update_datetime DESC")
    ProvMfrsInfo selectProvMfrs(@Param("provId") String provId, @Param("mfrsId") String mfrsId);

    @Select("SELECT * FROM COLLECTOR_PROV_CERT WHERE collector_id = #{collectorId} AND prov_id = #{provId} AND ROWNUM = 1 ORDER BY last_update_datetime DESC")
    CollectorProvCert selectProvProv(@Param("collectorId") String collectorId, @Param("provId") String provId);

    @Select("SELECT * FROM BAS_COMPANY_CERTIFICATE WHERE company_id = #{id}")
    List<BasCompanyCertificate> listCompanyCert(@Param("id") String id);

    @Select("SELECT * FROM BAS_COMPANY_CERTIFICATE_IMAGE WHERE certificate_id = #{id}")
    List<BasCompanyCertificateImage> listCompanyCertImg(@Param("id") String id);

    int insertProvMfrsInfo(ProvMfrsInfo provMfrsInfo);

    int insertProvMfrsCert(ProvMfrsCertificate provMfrsCert);

    int insertProvMfrsCertImg(ProvMfrsCertificateImage provMfrsCertImg);

    int insertProvProvInfo(CollectorProvCert provProvInfo);

    int insertProvProvCert(CollectorProvCertificate provProvCert);

    int insertProvProvCertImg(ProvCertificateImage provProvCertImg);
}
