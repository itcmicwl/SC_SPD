package gyqx.spdhdi.mycerts.service;

import java.util.Date;
import java.util.List;

import common.db.query.QueryResult;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvJxsqsHosVo;
import gyqx.spdhdi.mycerts.vo.ProvJxsqsNVo;
import gyqx.spdhdi.mycerts.vo.ProvJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvRegInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.SaveJxsqsRelationVo;
import gyqx.spdhdi.po.ProvJxsqsGoods;
import gyqx.spdhdi.po.ProvJxsqsHos;
import gyqx.spdhdi.po.ProvJxsqsZjzj;

/**
 * Created by liangwu on 17-6-21.
 */
public interface ProvJxsqsService {
    List<BasMfrsInfoVo> findBasMfrs(String mfrsName);

    QueryResult<ProvRegInfoJxsqsVo> findProvRegInfo(String mfrsId, String certName, String sqsId, int pageNum);

    QueryResult<BasCompanyInfoJxsqsVo> findBasHos(String hosName, String sqsId, int pageNum);

    String addJxsqs(ProvJxsqsVo provJxsqsVo) throws Exception;

    QueryResult<ProvJxsqsVo> findJxsqs(Date start, Date end, String mfrsName, int pageNum, int pageSize);

    int updateJxsqs(ProvJxsqsVo provJxsqsVo) throws Exception;

    String deleteJxsqs(String id, int version) throws Exception;

    List<BasMfrsInfoVo> findBasCompany(String name);

    List<String> findZjzjCompany(String name);

    String addProvJxsqsZjzj(ProvJxsqsZjzj provJxsqsZjzj) throws Exception;

    String delProvJxsqsZjzj(String id, int version) throws Exception;

    List<ProvJxsqsNVo> getJxsqsList(ProvJxsqsGoods query) throws Exception;

    Boolean saveJxsqsRelation(SaveJxsqsRelationVo saveJxsqsRelationVo)throws Exception;
    ProvJxsqsNVo delRelationAndGet(List<ProvJxsqsGoods> lst,String sqsId) throws Exception;
    ProvJxsqsNVo getSqsRelationById(String sqsId) throws Exception;
    ProvJxsqsNVo saveRelation(List<ProvJxsqsGoods> lst,String sqsId) throws Exception;
    List<ProvJxsqsHosVo> getSqsHosLst(String sqsId) throws Exception;
    Integer saveSqsHosLst(List<ProvJxsqsHos> lst) throws Exception;
    Integer delSqsHos(ProvJxsqsHos provJxsqsHos) throws Exception;
}
