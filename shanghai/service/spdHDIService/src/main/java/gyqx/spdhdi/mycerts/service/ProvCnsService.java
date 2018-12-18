package gyqx.spdhdi.mycerts.service;

import common.db.query.QueryResult;
import gyqx.spdhdi.mycerts.vo.ProvCnsVo;

import java.util.Date;

/**
 * Created by liangwu on 17-6-25.
 */
public interface ProvCnsService {
    String addCns(ProvCnsVo provCnsVo) throws Exception;

    QueryResult<ProvCnsVo> findCns(Date start, Date end, String hosName, int pageNum, int pageSize);

    QueryResult<ProvCnsVo> getCnsListByProv(Date start, Date end, String aboutName,int pageNum, int pageSize);

    QueryResult<ProvCnsVo> getCnsListByHos(Date start, Date end, String aboutName,int pageNum, int pageSize);

    QueryResult<ProvCnsVo> getCnsListByLoginComp(String compKind, Date start, Date end, String aboutName,int pageNum, int pageSize);

    Integer updateCns(ProvCnsVo provCnsVo) throws Exception;

    String deleteCns(String id, int version) throws Exception;
}
