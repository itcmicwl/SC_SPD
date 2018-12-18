package gyqx.spdhdi.mycerts.dao;

import gyqx.spdhdi.mycerts.dao.mapper.ProvFrwtsMapper;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvFrwtsVo;
import gyqx.spdhdi.mycerts.vo.WtrUserInfoVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by liangwu on 17-6-23.
 */
@Repository
public class ProvFrwtsDao {
    @Resource
    private ProvFrwtsMapper mapper;

    public List<BasCompanyInfoJxsqsVo> getHosList(String hosName) {
        return mapper.getHosList(hosName, 1, 15);
    }

    public List<WtrUserInfoVo> getWtrInfo(String provId) {
        return mapper.getWtrInfo(provId);
    }

    public List<ProvFrwtsVo> getFrwts(Date start, Date end, String name,String provName, int pageNum, int pageSize,String corpType,String corpId) {
        return mapper.getFrwts(start, end, name,provName, pageNum, pageSize,corpType,corpId);
    }

    public List<ProvFrwtsVo> getFrwtsByProv( String provId, Date start, Date end, String aboutName,int pageNum, int pageSize){
        return mapper.getFrwtsByProv(provId, start, end, aboutName,pageNum, pageSize);
    }

    public List<ProvFrwtsVo> getFrwtsByHos(String hosId, Date start, Date end, String aboutName,int pageNum, int pageSize){
        return mapper.getFrwtsByHos(hosId, start, end, aboutName,pageNum, pageSize);
    }

}
