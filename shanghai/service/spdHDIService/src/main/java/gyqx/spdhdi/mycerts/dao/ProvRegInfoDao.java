package gyqx.spdhdi.mycerts.dao;

import common.utils.UtilsContext;
import gyqx.spdhdi.mycerts.dao.mapper.ProvRegInfoMapper;
import gyqx.spdhdi.mycerts.vo.DlgBasRegInfoVo;
import gyqx.spdhdi.mycerts.vo.ProvGoodsInfo4CertVo;
import gyqx.spdhdi.mycerts.vo.ProvRegImageVo;
import gyqx.spdhdi.mycerts.vo.ProvRegInfoVo;
import gyqx.spdhdi.po.ProvRegistGoods;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by liangwu on 17-6-12.
 */
@Repository
public class ProvRegInfoDao {
    @Resource
    private ProvRegInfoMapper mapper;

    @Resource
    private UtilsContext utilsContext;

    public List<ProvRegInfoVo> getItemsCerts(String id, String certificateCode, String productName) {
        return mapper.getItemsCerts(id, certificateCode, productName);
    }

    public List<DlgBasRegInfoVo> getBasRegInfo(String mfrsId, String certCode, String regKind) {
        int pageNum = 1;
        int pageSize = 10;
        return mapper.getMfrsCerts(mfrsId, certCode, regKind, pageNum, pageSize);
    }

    public List<ProvRegImageVo> getCertImg(String provId, String certId) {
        return mapper.getCertImg(provId, certId);
    }

    public List<ProvGoodsInfo4CertVo> getGoodsInfo(String provId, String mfrsId) {
        return mapper.getGoodsInfo(provId, mfrsId);
    }

    public void addGoods(String provId, String regId, Set<String> addGoods, String uid) {
        List<ProvRegistGoods> goodsList = new ArrayList<>();
        for (String goodId : addGoods) {
            ProvRegistGoods provRegistGoods = new ProvRegistGoods();
            provRegistGoods.setId(utilsContext.getSysAtomUtil().newValue("prov_reg_goods_id"));
            provRegistGoods.setProvId(provId);
            provRegistGoods.setCertificateId(regId);
            provRegistGoods.setGoodsId(goodId);
            provRegistGoods.setUid(uid);
            goodsList.add(provRegistGoods);
        }
        mapper.addGoods(goodsList);
    }

    public void delAllRelatedGoods(String provId, String certId) {
        mapper.delAllRelatedGoods(provId, certId);
    }

    public void delGoods(String provId, String certId, Set<String> delGoods) {
        mapper.delGoods(provId, certId, delGoods);
    }

    public Set<String> listProvRegistGoodsId(String provId, String certId) {
        return mapper.listProvRegistGoodsId(provId, certId);
    }
}
