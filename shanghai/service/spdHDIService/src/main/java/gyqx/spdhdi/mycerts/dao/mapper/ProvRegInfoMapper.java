package gyqx.spdhdi.mycerts.dao.mapper;

import gyqx.spdhdi.mycerts.vo.DlgBasRegInfoVo;
import gyqx.spdhdi.mycerts.vo.ProvGoodsInfo4CertVo;
import gyqx.spdhdi.mycerts.vo.ProvRegImageVo;
import gyqx.spdhdi.mycerts.vo.ProvRegInfoVo;
import gyqx.spdhdi.po.ProvRegistGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by liangwu on 17-6-12.
 */
public interface ProvRegInfoMapper {
    List<ProvRegInfoVo> getItemsCerts(@Param("id") String id,
                                      @Param("certificateCode") String certificateCode,
                                      @Param("productName") String productName);

    List<DlgBasRegInfoVo> getMfrsCerts(@Param("mfrsId") String mfrsId,
                                       @Param("certCode") String certCode,
                                       @Param("regKind") String regKind,
                                       @Param("page") int pageNum, @Param("rows") int pageSize);

    List<ProvRegImageVo> getCertImg(@Param("provId") String provId, @Param("certId") String certId);

    List<ProvGoodsInfo4CertVo> getGoodsInfo(@Param("provId") String provId, @Param("mfrsId") String mfrsId);

    void addGoods(@Param("goodsList") List<ProvRegistGoods> goodsList);

    void delAllRelatedGoods(@Param("provId") String provId, @Param("certId") String certId);

    void delGoods(@Param("provId") String provId, @Param("certId") String certId, @Param("goodsIdSet") Set<String> delGoods);

    Set<String> listProvRegistGoodsId(@Param("provId") String provId, @Param("certId") String certId);
}
