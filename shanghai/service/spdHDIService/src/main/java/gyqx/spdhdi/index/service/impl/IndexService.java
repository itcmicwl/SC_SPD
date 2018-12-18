package gyqx.spdhdi.index.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import gyqx.spdhdi.index.dao.IndexDao;
import gyqx.spdhdi.index.service.IIndexService;
import gyqx.spdhdi.index.vo.OverViewVo;
@Service
public class IndexService implements IIndexService {
	@Resource
	private IndexDao dao;
	@Override
	public OverViewVo getOverViewInfo(String comId) throws Exception {
		return dao.getOverViewInfo(comId);
	}

}
