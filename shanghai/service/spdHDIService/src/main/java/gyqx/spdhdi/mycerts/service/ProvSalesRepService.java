package gyqx.spdhdi.mycerts.service;

import common.db.query.QueryResult;
import gyqx.spdhdi.mycerts.vo.ProvSalesRepVo;

import java.util.Date; /**
 * @Auther Liangwu
 * @Date 17-11-1 上午10:10
 */
public interface ProvSalesRepService {
    QueryResult<ProvSalesRepVo> listSalesRep(Date start, Date end, String name, int pageNum, int pageSize);

    String addSalesReps(ProvSalesRepVo provSalesRepVo) throws Exception;

    Integer updateSalesRep(ProvSalesRepVo provSalesRepVo) throws Exception;

    String deleteSalesRep(String id, int version) throws Exception;

    public QueryResult<ProvSalesRepVo> listSalesRepByProv(Date start, Date end, String aboutName, int pageNum, int pageSize);

    public QueryResult<ProvSalesRepVo> listSalesRepByHos(Date start, Date end, String aboutName, int pageNum, int pageSize);

    /**
     * 根据登录用户企业类型，获取相关的销售代表
     * @param compKind
     * @param start
     * @param end
     * @param pageNum
     * @param pageSize
     * @return
     */
    public QueryResult<ProvSalesRepVo> getSalesRepListByLoginComp(String compKind, Date start, Date end, String aboutName, int pageNum, int pageSize);

}
