package gyqx.spdhdi.mycerts.service;

import common.db.query.QueryResult;
import gyqx.spdhdi.mycerts.vo.ProvIncorruptAgrmtVo;

import java.util.Date;

/**
 * @Auther Liangwu
 * @Date 17-10-31 下午8:26
 */
public interface ProvIncorruptAgrmtService {
    QueryResult<ProvIncorruptAgrmtVo> listIncorruptAgrmt(Date start, Date end, String hosName, int pageNum, int pageSize);

    String addIncorruptAgrmt(ProvIncorruptAgrmtVo vo) throws Exception;

    Integer updateIncorruptAgrmt(ProvIncorruptAgrmtVo provIncorruptAgrmtVo) throws Exception;

    String deleteIncorruptAgrmt(String id, int version) throws Exception;

    QueryResult<ProvIncorruptAgrmtVo> listIncorruptAgrmtByProv(Date start, Date end, String aboutName,int pageNum, int pageSize);

    QueryResult<ProvIncorruptAgrmtVo> listIncorruptAgrmtByHos(Date start, Date end, String aboutName,int pageNum, int pageSize);

    /**
     * 根据登录用户企业类型，获取相关的廉政协议书
     * @param compKind
     * @param start
     * @param end
     * @param pageNum
     * @param pageSize
     * @return
     */
    QueryResult<ProvIncorruptAgrmtVo> getIncorruptAgrmtListByLoginComp(String compKind, Date start, Date end, String aboutName,int pageNum, int pageSize);
}
