package gyqx.spdhdi.mycerts.dao;

import gyqx.spdhdi.mycerts.dao.mapper.ProvSalesRepMapper;
import gyqx.spdhdi.mycerts.vo.ProvSalesRepVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-11-1 上午10:09
 */
@Repository
public class ProvSalesRepDao {
    @Resource
    private ProvSalesRepMapper mapper;

    public List<ProvSalesRepVo> listSalesRep(String provId, Date start, Date end, String name) {
        return mapper.listSalesRep(provId, start, end, name);
    }

    public List<ProvSalesRepVo> listSalesRepByProv(String provId, Date start, Date end, String aboutName,int pageNum, int pageSize){
        return mapper.listSalesRepByProv(provId, start, end, aboutName, pageNum, pageSize);
    }

    public List<ProvSalesRepVo> listSalesRepByHos(String hosId, Date start, Date end, String aboutName,int pageNum, int pageSize){
        return mapper.listSalesRepByHos(hosId, start, end, aboutName, pageNum, pageSize);
    }
}
