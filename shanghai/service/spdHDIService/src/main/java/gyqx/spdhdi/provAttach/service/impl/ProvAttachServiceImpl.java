package gyqx.spdhdi.provAttach.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.UtilsContext;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.po.ProvAttach;
import gyqx.spdhdi.provAttach.dao.ProvAttachDao;
import gyqx.spdhdi.provAttach.service.ProvAttachService;
import gyqx.spdhdi.provAttach.vo.ProvAttachVo;

@Service
public class ProvAttachServiceImpl implements ProvAttachService {

	@Resource
	private ProvAttachDao dao;
	
	@Resource
    private SimpleDao simpleDao;

    @Resource
    private UtilsContext utilsContext;
	
	/**
	 * 查询供应商附件数据
	 */
	@Override
	public QueryResult<ProvAttachVo> getAttachList(QueryInfo<ProvAttachVo> queryInfo) {
		QueryResult<ProvAttachVo> re = new QueryResult<ProvAttachVo>();
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<ProvAttachVo> attachVos = dao.getAttachList(queryInfo.getQueryObject());
		PageInfo<ProvAttachVo> info = new PageInfo<ProvAttachVo>(attachVos);
		re.setData(info.getList());
		re.setPageNum(info.getPageNum());
		re.setPageSize(info.getPageSize());
		re.setTotal(info.getTotal());
		return re;
	}

	@Override
	public String insertAttach(ProvAttachVo attachVo) throws Exception {
		String id = utilsContext.getSysAtomUtil().newValue("prov-attach-id");
		ProvAttach attach = new ProvAttach();
		Tx.transform(attachVo).to(attach);
		attach.setId(id);
		attach.setFillUser(utilsContext.getUserStateUtils().getCurrent().getUserId());
		attach.setLastUpdateDatetime(new Date());
		attach.setVersion(0);
		attach.setFlag("1");
		simpleDao.insert(attach);
		return id;
	}

	@Override
	@Transactional
	public void updateAttach(List<ProvAttachVo> attachVo) throws Exception {
		for (ProvAttachVo provAttachVo : attachVo) {
			ProvAttach attach = new ProvAttach();
			Tx.transform(provAttachVo).to(attach);
//			attach.setFlag("0");
			attach.setLastUpdateDatetime(new Date());
			simpleDao.updateNotNullFields(attach);
		}
	}

	@Override
	public List<BasCompanyInfoJxsqsVo> findProvList(String provName) {
		provName = provName.replaceAll("%", "\\\\%");
        return dao.getProvList(provName);
	}

}
