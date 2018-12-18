package gyqx.spdhdi.mycerts.service;

import common.db.query.QueryResult;
import common.utils.DictionaryUtils;
import gyqx.spdhdi.mycerts.vo.BasMfrsInfoMyCertsVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsInfoVo;
import gyqx.spdhdi.po.ProvMfrsCertificateImage;
import gyqx.spdhdi.po.ProvMfrsInfo;

import java.util.List;
import java.util.Set;

/**
 * Created by liangwu on 17-6-19.
 */
public interface ProvMfrsInfoService {
    List<DictionaryUtils.DictionaryValue> findCjlxDicts();

    QueryResult<ProvMfrsInfoVo> findMfrsInfo(int searchType, String searchText, int pageNum, int pageSize);

    List<BasMfrsInfoMyCertsVo> findBasMfrs(String mfrsName);

    String addMfrsInfo(ProvMfrsInfoVo provMfrsInfoVo) throws Exception;

    String deleteMfrsInfo(String id, int version) throws Exception;

    ProvMfrsInfo updateFlag(ProvMfrsInfo provMfrsInfo) throws Exception;

    List<DictionaryUtils.DictionaryValue> findZzlxDicts();

    List<ProvMfrsCertificateVo> findCertList(String id);

    String deleteMfrsCertImg(String id) throws Exception;

    Set<ProvMfrsCertificateImage> addPic(ProvMfrsCertificateVo cert) throws Exception;

    Integer updateMfrsInfo(ProvMfrsInfoVo provMfrsInfoVo) throws Exception;

    Integer deleteMfrsCert(String id, int version) throws Exception;
}
