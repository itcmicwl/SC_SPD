package gyqx.mdqc.mycerts.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.mdqc.mycerts.dao.mapper.BasRegistrationMapper;
import gyqx.mdqc.mycerts.vo.BasGoodsInfoVo;
import gyqx.mdqc.mycerts.vo.BasRegistrationInfoVo;
import gyqx.mdqc.mycerts.vo.IdNameVo;

/**
 * @Author liangwu
 * @Date 7/31/18 11:31 AM
 */
@Repository
public class BasRegistrationDao {
    @Resource
    private BasRegistrationMapper mapper;

    public List<BasRegistrationInfoVo> listCerts(String input, String type, String mfrsId) {
        return mapper.listCerts(input, type, mfrsId);
    }

    public List<IdNameVo> listDictValues(String dictName) {
        return mapper.listDictValues(dictName);
    }

    public List<IdNameVo> listMfrs(String queryStr) {
        return mapper.listMfrs(queryStr);
    }

    public List<IdNameVo> listHos(String queryStr) {
        return mapper.listHos(queryStr);
    }

    public Integer delCert(String id, Integer version) {
        return mapper.delCert(id, version);
    }

    public boolean verifyMfrs(String corpId) {
        return mapper.verifyMfrs(corpId) > 0;
    }

    public List<BasGoodsInfoVo> getMfrsProduct(String mfrsId, String input) {
        return mapper.getMfrsProduct(mfrsId, input);
    }

    public boolean addCert(BasRegistrationInfoVo basRegistrationInfoVo, String operId) {
        int certRowNum = mapper.insertCert(basRegistrationInfoVo, operId);
        int imgRowNum = 0;
        if (basRegistrationInfoVo.getFileListAdd().size() > 0) {
            imgRowNum = mapper.insertCertImg(basRegistrationInfoVo.getFileListAdd(), operId);
        }
        int goodsRowNum = 0;
        if (basRegistrationInfoVo.getProductsAdd().size() > 0) {
            goodsRowNum = mapper.insertCertGoods(basRegistrationInfoVo.getProductsAdd(), operId);
        }
        return certRowNum > 0 &&
                imgRowNum == basRegistrationInfoVo.getFileListAdd().size() &&
                goodsRowNum == basRegistrationInfoVo.getProductsAdd().size();
    }

    public BasRegistrationInfoVo getCert(String id, Integer version) {
        return mapper.getCert(id, version);
    }

    public boolean updateCert(BasRegistrationInfoVo basRegistrationInfoVo, String operId) {
        int imgRowAddNum = 0;
        if (basRegistrationInfoVo.getFileListAdd().size() > 0) {
            imgRowAddNum = mapper.insertCertImg(basRegistrationInfoVo.getFileListAdd(), operId);
        }
        int goodsRowAddNum = 0;
        if (basRegistrationInfoVo.getProductsAdd().size() > 0) {
            goodsRowAddNum = mapper.insertCertGoods(basRegistrationInfoVo.getProductsAdd(), operId);
        }
        int imgRowDelNum = 0;
        if (basRegistrationInfoVo.getFileListDel().size() > 0) {
            imgRowDelNum = mapper.delCertImg(basRegistrationInfoVo.getFileListDel());
        }
        int goodsRowDelNum = 0;
        if (basRegistrationInfoVo.getProductsDel().size() > 0) {
            goodsRowDelNum = mapper.delCertGoods(basRegistrationInfoVo.getProductsDel());
        }
        return imgRowAddNum == basRegistrationInfoVo.getFileListAdd().size() &&
                imgRowDelNum == basRegistrationInfoVo.getFileListDel().size() &&
                goodsRowAddNum == basRegistrationInfoVo.getProductsAdd().size() &&
                goodsRowDelNum == basRegistrationInfoVo.getProductsDel().size();
    }

    public int changeCertToOld(BasRegistrationInfoVo basRegistrationInfoVo, String operId) {
        return mapper.changeCertToOld(basRegistrationInfoVo, operId);
    }

    public int changeGoodsCertificateCode(String certId, String operId) {
        return mapper.changeGoodsCertificateCode(certId, operId);
    }
}
