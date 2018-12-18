package gyqx.platform.sys.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.SysAtomUtil;
import gyqx.platform.po.SysConfig;
import gyqx.platform.sys.dao.SysConfigDao;
import gyqx.platform.sys.service.ISysConfigService;
@Service
public class SysConfigService implements ISysConfigService {
	@Resource
	SysConfigDao sysConfigDao;
	@Resource
	private SysAtomUtil atomUtil;
	@Override
	public QueryResult<SysConfig> getSysConfigList(QueryInfo<SysConfig> queryInfo) {
		QueryResult<SysConfig> lstSysConfig = new QueryResult<SysConfig>();
		PageInfo<SysConfig> sysConfigPage = sysConfigDao.getSysConfigList(queryInfo);
		lstSysConfig.setData(sysConfigPage.getList());
		lstSysConfig.setPageNum(sysConfigPage.getPageNum());
		lstSysConfig.setPageSize(sysConfigPage.getPageSize());
		lstSysConfig.setTotal(sysConfigPage.getTotal());
		return lstSysConfig;
	}

	@Override
	public SysConfig getSysConfigByid(String id) {
		return sysConfigDao.getSysConfigByid(id);
	}

	@Override
	public SysConfig getSysConfigByName(String name) {
		return sysConfigDao.getSysConfigByName(name);
	}

	@Override
	public int insertSysConfig(SysConfig sysConfig) {
		String id =atomUtil.newValue("sys.config");
		sysConfig.setId(id);
		return sysConfigDao.insertSysConfig(sysConfig);

	}

	@Override
	public int setStatus(SysConfig sysconfig) {
		sysconfig.setMt(new Date());
		return sysConfigDao.setStatus(sysconfig);
	}

	@Override
	public int setSysConfig(SysConfig sysconfig) {
		sysconfig.setMt(new Date());
		return sysConfigDao.setSysConfig(sysconfig);
	}

	@Override
	public boolean existSysConfig(SysConfig sysConfig) throws Exception {
		return sysConfigDao.existSysConfig(sysConfig);
	}

}
