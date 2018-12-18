package gyqx.spdhdi.mycerts.dao;

import gyqx.spdhdi.mycerts.dao.mapper.ProvIncorruptAgrmtMapper;
import gyqx.spdhdi.mycerts.vo.ProvIncorruptAgrmtVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-10-31 下午8:27
 */
@Repository
public class ProvIncorruptAgrmtDao {
    @Resource
    private ProvIncorruptAgrmtMapper mapper;

    public List<ProvIncorruptAgrmtVo> listIncorruptAgrmt(String provId, Date start, Date end, String hosName) {
        return mapper.listIncorruptAgrmt(provId, start, end, hosName);
    }

    public List<ProvIncorruptAgrmtVo> listIncorruptAgrmtByProv(String provId, Date start, Date end, String aboutName,int pageNum, int pageSize){
        return mapper.listIncorruptAgrmtByProv(provId, start, end, aboutName,pageNum, pageSize);
    }

    public List<ProvIncorruptAgrmtVo> listIncorruptAgrmtByHos(String hosId, Date start, Date end, String aboutName,int pageNum, int pageSize){
        return mapper.listIncorruptAgrmtByHos(hosId, start, end, aboutName,pageNum, pageSize);
    }
}
