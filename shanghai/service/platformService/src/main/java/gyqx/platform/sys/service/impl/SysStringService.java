package gyqx.platform.sys.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.SysAtomUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.platform.po.SysString;
import gyqx.platform.sys.dao.SysConfigDao;
import gyqx.platform.sys.dao.SysStringDao;
import gyqx.platform.sys.service.ISysStringService;
import gyqx.platform.sys.vo.SysStringVo;
@Service
public class SysStringService implements ISysStringService {
	@Resource
	SysConfigDao sysConfigDao;
	@Resource
	SysStringDao sysStringDao;
	@Resource
	SimpleDao simpleDao;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private UserOnlineStateUtils  userOnlineStateUtils;
	
	@Override
	public QueryResult<SysString> getSysStringList(QueryInfo<SysString> queryInfo) throws Exception {
		QueryResult<SysString> lstSysString = new QueryResult<SysString>();
		PageInfo<SysString> sysStringPage = sysStringDao.getSysStringList(queryInfo);
		lstSysString.setData(sysStringPage.getList());
		lstSysString.setPageNum(sysStringPage.getPageNum());
		lstSysString.setPageSize(sysStringPage.getPageSize());
		lstSysString.setTotal(sysStringPage.getTotal());
		return lstSysString;
	}
	
	@Override
	public QueryResult<SysStringVo> getSysStringVoList(QueryInfo<SysStringVo> queryInfo) throws Exception {
		QueryResult<SysStringVo> lstSysString = new QueryResult<SysStringVo>();
		PageInfo<SysStringVo> sysStringPage = sysStringDao.getSysStringVoList(queryInfo);
		lstSysString.setData(sysStringPage.getList());
		lstSysString.setPageNum(sysStringPage.getPageNum());
		lstSysString.setPageSize(sysStringPage.getPageSize());
		lstSysString.setTotal(sysStringPage.getTotal());
		return lstSysString;
	}

	@Override
	public SysString getSysStringByid(String id) {
		return sysStringDao.getSysStringByid(id);
	}

	@Override
	public int insertSysString(SysString sysString) throws Exception {
		if(!StringUtils.hasText(sysString.getId()))
		{
			String id = atomUtil.newValue("sys.config");
			sysString.setId(id);
		}
		if(userOnlineStateUtils.getCurrent() != null)
		sysString.setUid(userOnlineStateUtils.getCurrent().getUserId());
		return sysStringDao.insertSysString(sysString);
	}

	@Override
	public int setStatus(SysString sysString) throws Exception {
		sysString.setMt(new Date());
		return sysStringDao.setStatus(sysString);
	}

	@Override
	public int setSysString(SysString sysString) throws Exception {
		sysString.setMt(new Date());
		sysString.setUid(userOnlineStateUtils.getCurrent().getUserId());
		return sysStringDao.setSysString(sysString);
	}

	@Override
	public boolean existSysString(SysString sysString) throws Exception {
		return sysStringDao.existSysString(sysString);
	}

	@Override
	public void deleteSysString(SysString sysString) throws Exception {
		simpleDao.delete(sysString);
	}

	@Override
	public List<SysString> getSysStringAll() throws Exception {

		return sysStringDao.getSysStringAll();
	}
	
	

}
