package gyqx.mdqc.mycerts.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.UtilsContext;
import gyqx.mdqc.mycerts.dao.BasRegistrationDao;
import gyqx.mdqc.mycerts.service.BasRegistrationService;
import gyqx.mdqc.mycerts.vo.BasGoodsInfoVo;
import gyqx.mdqc.mycerts.vo.BasRegistrationInfoVo;
import gyqx.mdqc.mycerts.vo.IdNameVo;
import gyqx.spdhdi.po.BasRegistrationInfo;

/**
 * @Author liangwu
 * @Date 7/31/18 11:25 AM
 */
@Service
public class BasRegistrationServiceImpl implements BasRegistrationService {
    @Autowired
    private BasRegistrationDao dao;

    @Autowired
    private SimpleDao simpleDao;

    @Autowired
    private UtilsContext utilsContext;

    @Override
    public QueryResult<BasRegistrationInfoVo> listCerts(String input, String type, String mfrsId, Integer page, Integer size) {
        if (verifyMfrs()) {
            mfrsId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        }
        PageUtils.startPage(page, size);
        return PageUtils.endPage(dao.listCerts(input, type, mfrsId));
    }

    @Override
    public List<IdNameVo> listDictValues(String dictName) {
        return dao.listDictValues(dictName);
    }

    @Override
    public List<IdNameVo> listMfrs(String queryStr) {
        queryStr = "%" + queryStr + "%";
        return dao.listMfrs(queryStr);
    }

    @Override
    public List<IdNameVo> listHos(String queryStr) {
        queryStr = "%" + queryStr + "%";
        return dao.listHos(queryStr);
    }

    @Override
    public Integer delCert(String id, Integer version) {
        return dao.delCert(id, version);
    }

    @Override
    public boolean verifyMfrs() {
        String corpId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return dao.verifyMfrs(corpId);
    }

    @Override
    public List<BasGoodsInfoVo> getMfrsProduct(String mfrsId, String input) {
        if (verifyMfrs() && mfrsId.equals(utilsContext.getUserStateUtils().getCurrent().getCorpId())) {
            throw new IllegalArgumentException("非法厂家。");
        }
        return dao.getMfrsProduct(mfrsId, input);
    }

    @Override
    public boolean addCert(BasRegistrationInfoVo basRegistrationInfoVo) throws SQLException {
        if (verifyMfrs() && basRegistrationInfoVo.getMfrsId().equals(utilsContext.getUserStateUtils().getCurrent().getCorpId())) {
            throw new IllegalArgumentException("非法厂家。");
        }
        basRegistrationInfoVo.setId(utilsContext.getSysAtomUtil().newValue("bas_reg_info_id"));

        transAdd(basRegistrationInfoVo);
        String operId = utilsContext.getUserStateUtils().getCurrent().getUserId();
        boolean result = dao.addCert(basRegistrationInfoVo, operId);
        dao.changeGoodsCertificateCode(basRegistrationInfoVo.getId(), operId);
        if (!result) {
            throw new SQLException("新增注册证失败");
        }
        return result;
    }

    private void transAdd(BasRegistrationInfoVo basRegistrationInfoVo) {
        basRegistrationInfoVo.getFileListAdd().forEach(file -> {
            file.setCertificateId(basRegistrationInfoVo.getId());
            file.setId(utilsContext.getSysAtomUtil().newValue("bas_reg_img_id"));
        });

        basRegistrationInfoVo.getProductsAdd().forEach(reg -> {
            reg.setId(utilsContext.getSysAtomUtil().newValue("bas_reg_goods_id"));
            reg.setCertificateId(basRegistrationInfoVo.getId());
            reg.setGoodsId(reg.getGoodsId());
        });
    }

    @Override
    public BasRegistrationInfoVo getCert(String id, Integer version, String mfrsId) {
        if (verifyMfrs() && mfrsId.equals(utilsContext.getUserStateUtils().getCurrent().getCorpId())) {
            throw new IllegalArgumentException("非法厂家。");
        }
        return dao.getCert(id, version);
    }

    @Override
    public boolean modCert(BasRegistrationInfoVo basRegistrationInfoVo) throws Exception {
        if (verifyMfrs() && basRegistrationInfoVo.getMfrsId().equals(utilsContext.getUserStateUtils().getCurrent().getCorpId())) {
            throw new IllegalArgumentException("非法厂家。");
        }
        BasRegistrationInfo basRegistrationInfo = new BasRegistrationInfo();
        Tx.transform(basRegistrationInfoVo).to(basRegistrationInfo);
        basRegistrationInfo.setLastUpdateDatetime(new Date());
        int certResult = simpleDao.updateNotNullFields(basRegistrationInfo);
        transAdd(basRegistrationInfoVo);
        String operId = utilsContext.getUserStateUtils().getCurrent().getUserId();
        boolean subResult = dao.updateCert(basRegistrationInfoVo, operId);
        dao.changeGoodsCertificateCode(basRegistrationInfoVo.getId(), operId);
        boolean result = certResult > 0 && subResult;
        if (!result) {
            throw new SQLException("更新注册证失败");
        }
        return result;
    }

    @Override
    public boolean replaceCert(BasRegistrationInfoVo basRegistrationInfoVo) throws SQLException {
        if (verifyMfrs() && basRegistrationInfoVo.getMfrsId().equals(utilsContext.getUserStateUtils().getCurrent().getCorpId())) {
            throw new IllegalArgumentException("非法厂家。");
        }
        String operId = utilsContext.getUserStateUtils().getCurrent().getUserId();
        int certNum = dao.changeCertToOld(basRegistrationInfoVo, operId);
        if (certNum != 1) {
            throw new SQLException("更新数据失败");
        }
        basRegistrationInfoVo.setId(utilsContext.getSysAtomUtil().newValue("bas_reg_info_id"));
        basRegistrationInfoVo.getProductsAdd().addAll(basRegistrationInfoVo.getProducts());
        if (basRegistrationInfoVo.getProductsDel().size() > 0) {
            basRegistrationInfoVo.getProductsAdd().removeIf(basGoodsInfoVo -> basRegistrationInfoVo.getProductsDel().stream().anyMatch(item -> item.getGoodsId().equals(basGoodsInfoVo.getGoodsId())));
        }
        basRegistrationInfoVo.getFileListAdd().addAll(basRegistrationInfoVo.getFileList());
        if (basRegistrationInfoVo.getFileListDel().size() > 0) {
            basRegistrationInfoVo.getFileListAdd().removeIf(imgVo -> basRegistrationInfoVo.getFileListDel().stream().anyMatch(item -> item.getId().equals(imgVo.getId())));
        }
        transAdd(basRegistrationInfoVo);
        boolean result = dao.addCert(basRegistrationInfoVo, operId);
        dao.changeGoodsCertificateCode(basRegistrationInfoVo.getId(), operId);
        if (!result) {
            throw new SQLException("更新注册证失败");
        }
        return result;
    }
}
