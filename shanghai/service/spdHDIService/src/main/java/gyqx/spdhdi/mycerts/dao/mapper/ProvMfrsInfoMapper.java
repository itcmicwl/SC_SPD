package gyqx.spdhdi.mycerts.dao.mapper;

import gyqx.spdhdi.mycerts.vo.BasMfrsInfoMyCertsVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsInfoVo;
import gyqx.spdhdi.po.ProvMfrsCertificate;
import gyqx.spdhdi.po.ProvMfrsCertificateImage;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface ProvMfrsInfoMapper {

    List<ProvMfrsInfoVo> getMfrsInfo(@Param("provId") String provId, @Param("searchType") int searchType,
                                     @Param("searchText") String searchText, @Param("page") int pageNum,
                                     @Param("rows") int pageSize);

    List<BasMfrsInfoMyCertsVo> getBasMfrs(@Param("mfrsName") String mfrsName, @Param("page") int pageNum,
                                          @Param("rows") int pageSize);

    void insertCertList(@Param("certList") Collection<ProvMfrsCertificate> certList,
                        @Param("pId") String pId,
                        @Param("mfrsId") String mfrsId,
                        @Param("uId") String uId);

    void insertImageList(@Param("imageList") Collection<ProvMfrsCertificateImage> imageList,
                         @Param("certId") String certId,
                         @Param("uId") String uId);

    List<ProvMfrsCertificateVo> getCertList(@Param("id") String id);

    void delMfrsCertImg(@Param("certId") String certId);
}