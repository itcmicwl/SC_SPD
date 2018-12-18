package gyqx.spdhdi.provAttach.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.provAttach.dao.mapper.IProvAttachMapper;
import gyqx.spdhdi.provAttach.vo.ProvAttachVo;

@Repository
public class ProvAttachDao {

	@Resource
	IProvAttachMapper mapper;
	
	public List<ProvAttachVo> getAttachList(ProvAttachVo attachVo){
		return mapper.getAttachList(attachVo);
	}
	
	public List<BasCompanyInfoJxsqsVo> getProvList(String provName) {
        return mapper.getProvList(provName, 1, 15);
    }
}
