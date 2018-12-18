package gyqx.platform.sys.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.query.QueryInfo;
import gyqx.platform.po.SysConfig;
import gyqx.platform.po.SysString;
import gyqx.platform.sys.dao.mapper.ISysConfigMapper;
import gyqx.platform.sys.dao.mapper.ISysStringMapper;
import gyqx.platform.sys.vo.SysStringVo;

@Repository
public class SysStringDao {
	@Resource
	ISysConfigMapper sysConfigMapper;
	@Resource
	ISysStringMapper sysStringMapper;
	
	public PageInfo<SysString> getSysStringList(QueryInfo<SysString> queryInfo) {
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<SysString> lstSysString = sysStringMapper.getSysStringList(queryInfo.getQueryObject());
		PageInfo<SysString> sysStringPage = new PageInfo<SysString>(lstSysString);
		return sysStringPage;
	}
	public PageInfo<SysStringVo> getSysStringVoList(QueryInfo<SysStringVo> queryInfoVo) {
		PageHelper.startPage(queryInfoVo.getPage(), queryInfoVo.getRows());
		List<SysStringVo> lstSysStringVo = sysStringMapper.getSysStringVoList(queryInfoVo.getQueryObject());
		PageInfo<SysStringVo> sysStringVoPage = new PageInfo<SysStringVo>(lstSysStringVo);
		return sysStringVoPage;
	}
	
	public List<SysString> getSysStringAll(){
		return sysStringMapper.getSysStringAll();
	}
	
	public SysString getSysStringByid(String id) {
		return sysStringMapper.getSysStringByid(id);
	}

	public SysConfig getSysConfigByName(String name) {
		return sysConfigMapper.getSysConfigByName(name);
	}

	public int insertSysString(SysString sysString) {
		return sysStringMapper.insertSysString(sysString);
	}

	public int setStatus(SysString sysString) {
		return sysStringMapper.setStatus(sysString);
	}

	public int setSysString(SysString sysString) {
		return sysStringMapper.setSysString(sysString);
	}

	public boolean existSysString(SysString sysString) {
		Integer count = sysStringMapper.existSysString(sysString);
		count = count == null ? 0 : count;
		return count > 0;
	}
}
