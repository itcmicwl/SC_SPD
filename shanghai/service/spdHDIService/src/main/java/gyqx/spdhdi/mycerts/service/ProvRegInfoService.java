package gyqx.spdhdi.mycerts.service;

import common.db.query.QueryResult;
import common.utils.DictionaryUtils;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.mycerts.vo.DlgBasRegInfoVo;
import gyqx.spdhdi.mycerts.vo.ProvGoodsInfo4CertVo;
import gyqx.spdhdi.mycerts.vo.ProvRegImageVo;
import gyqx.spdhdi.mycerts.vo.ProvRegInfoVo;
import gyqx.spdhdi.mycerts.vo.ProvRegistrationInfoVo;
import gyqx.spdhdi.po.ProvRegistrationImage;

import java.util.List;
import java.util.Map;

/**
 * Created by liangwu on 17-6-12.
 */
public interface ProvRegInfoService {
    QueryResult<ProvRegInfoVo> findItemsCerts(String certificateCode, String productName, int pageNum, int pageSize);

    List<BasMfrsInfoVo> findMfrs(String mfrsName);

    String deleteCert(String id, int version) throws Exception;

    List<DictionaryUtils.DictionaryValue> findDicts();

    String addCert(ProvRegInfoVo regInfo) throws Exception;

    List<DlgBasRegInfoVo> findBasRegInfo(String mfrsId, String certCode, String regKind);

    Integer updateCert(ProvRegInfoVo regInfo) throws Exception;

    List<ProvRegImageVo> findCertImg(String certId);

    String deleteCertImg(String id, String version) throws Exception;

    ProvRegImageVo addProvRegImage(ProvRegistrationImage provRegImage) throws Exception;

    List<ProvGoodsInfo4CertVo> findGoodsInfo(String mfrsId);

    String replaceCert(ProvRegInfoVo regInfo) throws Exception;

	void importRegCerts(Map map) throws Exception;

	List<ProvRegistrationInfoVo> getProvRegsBySpdGoodsCode(String spdGoodsCode) throws Exception;
}
