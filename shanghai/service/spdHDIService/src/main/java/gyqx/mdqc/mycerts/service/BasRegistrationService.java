package gyqx.mdqc.mycerts.service;

import common.db.query.QueryResult;
import gyqx.mdqc.mycerts.vo.BasGoodsInfoVo;
import gyqx.mdqc.mycerts.vo.BasRegistrationInfoVo;
import gyqx.mdqc.mycerts.vo.IdNameVo;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author liangwu
 * @Date 7/31/18 11:24 AM
 */
public interface BasRegistrationService {
    QueryResult<BasRegistrationInfoVo> listCerts(String input, String type, String mfrsId, Integer page, Integer size);

    List<IdNameVo> listDictValues(String dictName);

    List<IdNameVo> listMfrs(String queryStr);

    Integer delCert(String id, Integer version);

    boolean verifyMfrs();

    List<BasGoodsInfoVo> getMfrsProduct(String mfrsId, String input);

    boolean addCert(BasRegistrationInfoVo basRegistrationInfoVo) throws SQLException;

    BasRegistrationInfoVo getCert(String id, Integer version, String mfrsId);

    boolean modCert(BasRegistrationInfoVo basRegistrationInfoVo) throws Exception;

    boolean replaceCert(BasRegistrationInfoVo basRegistrationInfoVo) throws SQLException;


    List<IdNameVo> listHos(String queryStr);
}
