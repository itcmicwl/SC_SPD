package gyqx.platform.sys.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.AtomItem;
import common.utils.SysAtomUtil;
import gyqx.platform.po.SysAtom;
import gyqx.platform.po.SysConfig;
import gyqx.platform.sys.dao.SysAtomDao;
import gyqx.platform.sys.service.ISysAtomService;
@Service
public class SysAtomService implements ISysAtomService {
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private SysAtomDao sysAtomDao;
	@Override
	public HashMap<String, AtomItem> getAtomItemMap() {
		atomUtil.refresh();
		return atomUtil.getAtomItemMap();
	}

	@Override
	public QueryResult<SysAtom> getSysAtomList(QueryInfo<SysAtom> queryInfo) {
		
		QueryResult<SysAtom> lstSysAtom = new QueryResult<SysAtom>();
		PageInfo<SysAtom> sysAtomPage = sysAtomDao.getSysAtomList(queryInfo);
		lstSysAtom.setData(sysAtomPage.getList());
		lstSysAtom.setPageNum(sysAtomPage.getPageNum());
		lstSysAtom.setPageSize(sysAtomPage.getPageSize());
		lstSysAtom.setTotal(sysAtomPage.getTotal());
		return lstSysAtom;
	}
}
