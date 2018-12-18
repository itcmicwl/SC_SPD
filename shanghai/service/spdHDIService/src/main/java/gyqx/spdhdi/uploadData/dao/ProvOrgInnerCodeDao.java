package gyqx.spdhdi.uploadData.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.uploadData.dao.mapper.ProvOrgInnerCodeMapper;
import gyqx.spdhdi.uploadData.vo.ProvOrgInnerCodeVo;

@Repository
public class ProvOrgInnerCodeDao {
	
	@Resource
	private ProvOrgInnerCodeMapper mapper;

	public ProvOrgInnerCodeVo getProOrgInnerCode(ProvOrgInnerCodeVo vo) {
		List<ProvOrgInnerCodeVo> vs = mapper.getProOrgInnerCode(vo);
		if(vs != null && vs.size() == 1){
			return vs.get(0);
		}
		return null;
	}
	
	public ProvOrgInnerCodeVo getProOrgInnerCode(String provSystemId, String provId){
		ProvOrgInnerCodeVo vo = new ProvOrgInnerCodeVo();
		vo.setSysFlag(provSystemId);
		vo.setProvId(provId);
		List<ProvOrgInnerCodeVo> vs = mapper.getProOrgInnerCode(vo);
		if(vs != null && vs.size() == 1){
			return vs.get(0);
		}
		return null;
	}

}
