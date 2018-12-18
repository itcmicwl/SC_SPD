package gyqx.spdhdi.baseData.service;

import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.DictionaryUtils;
import gyqx.spdhdi.baseData.vo.BasRegInfoVo;
import gyqx.spdhdi.baseData.vo.BasRegistrationInfoVo;

/**
 * Created by liangwu on 17-6-6.
 */
public interface BasRegInfoService {

    QueryResult<BasRegInfoVo> findCerts(String mfrsId, int pageNum, int pageSize);

    String addCert(BasRegistrationInfoVo basRegInfoVo) throws Exception;

    int updateCert(BasRegistrationInfoVo basRegInfoVo) throws Exception;

    String deleteCert(String mfrsId, Integer version) throws Exception;

    List<DictionaryUtils.DictionaryValue> findDicts();

	QueryResult<BasRegInfoVo> getCerts(QueryInfo<BasRegInfoVo> queryInfo) throws Exception;
}
