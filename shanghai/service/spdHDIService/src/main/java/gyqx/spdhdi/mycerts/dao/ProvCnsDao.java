package gyqx.spdhdi.mycerts.dao;

import gyqx.spdhdi.mycerts.dao.mapper.ProvCnsMapper;
import gyqx.spdhdi.mycerts.vo.ProvCnsVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by liangwu on 17-6-25.
 */
@Repository
public class ProvCnsDao {
    @Resource
    private ProvCnsMapper mapper;

    public List<ProvCnsVo> getCns(String provId, Date start, Date end, String hosName, int pageNum, int pageSize) {
        return mapper.getCns(provId, start, end, hosName,pageNum, pageSize);
    }

    public List<ProvCnsVo> getCnsListByProv(String provId, Date start, Date end, String aboutName,int pageNum, int pageSize){
        return mapper.getCnsListByProv(provId, start, end, pageNum, aboutName,pageSize);
    }

    public List<ProvCnsVo> getCnsListByHos(String hosId, Date start, Date end, String aboutName,int pageNum, int pageSize){
        return mapper.getCnsListByHos(hosId, start, end, aboutName,pageNum, pageSize);
    }
}
