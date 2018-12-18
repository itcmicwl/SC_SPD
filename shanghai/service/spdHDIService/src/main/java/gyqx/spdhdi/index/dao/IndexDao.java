package gyqx.spdhdi.index.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.index.dao.mapper.IIndexMapper;
import gyqx.spdhdi.index.vo.OverViewVo;
@Repository
public class IndexDao {
	@Resource
	private IIndexMapper mapper;

	public OverViewVo getOverViewInfo(String comId) throws Exception{
		return mapper.getOverViewInfo(comId);
	}
}
