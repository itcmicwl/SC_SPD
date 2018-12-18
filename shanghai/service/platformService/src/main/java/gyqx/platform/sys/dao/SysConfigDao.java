package gyqx.platform.sys.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.query.QueryInfo;
import gyqx.platform.po.SysConfig;
import gyqx.platform.sys.dao.mapper.ISysConfigMapper;

@Repository
public class SysConfigDao {
	@Resource
	ISysConfigMapper sysConfigMapper;

	public PageInfo<SysConfig> getSysConfigList(QueryInfo<SysConfig> queryInfo) {
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<SysConfig> lstSysConfig = sysConfigMapper.getSysConfigList(queryInfo.getQueryObject());
		PageInfo<SysConfig> sysConfigPage = new PageInfo<SysConfig>(lstSysConfig);
		return sysConfigPage;
	}

	public SysConfig getSysConfigByid(String id) {
		return sysConfigMapper.getSysConfigByid(id);
	}

	public SysConfig getSysConfigByName(String name) {
		return sysConfigMapper.getSysConfigByName(name);
	}

	public int insertSysConfig(SysConfig sysConfig) {
		return sysConfigMapper.insertSysConfig(sysConfig);
	}

	public int setStatus(SysConfig sysconfig) {
		return sysConfigMapper.setStatus(sysconfig);
	}

	public int setSysConfig(SysConfig sysconfig) {
		return sysConfigMapper.setSysConfig(sysconfig);
	}

	public boolean existSysConfig(SysConfig sysConfig) {
		Integer count = sysConfigMapper.existSysConfig(sysConfig);
		count = count == null ? 0 : count;
		return count > 0;
	}
}
