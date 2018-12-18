package gyqx.platform.sys.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.query.QueryInfo;
import gyqx.platform.po.SysAtom;
import gyqx.platform.sys.dao.mapper.ISysAtomMapper;

@Repository
public class SysAtomDao 
{
	@Resource
	private ISysAtomMapper mapper;
	public PageInfo<SysAtom> getSysAtomList(QueryInfo<SysAtom> queryInfo){
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<SysAtom> lstSysAtom = mapper.getSysAtomList(queryInfo.getQueryObject());
		PageInfo<SysAtom> sysAtomPage = new PageInfo<SysAtom>(lstSysAtom);
		return sysAtomPage;
	}
}
