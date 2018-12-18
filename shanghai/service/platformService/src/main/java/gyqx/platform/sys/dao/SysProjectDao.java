package gyqx.platform.sys.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import gyqx.platform.po.SysProject;
import gyqx.platform.sys.dao.mapper.ISysProjectMapper;
import gyqx.platform.sys.vo.SysProjectVo;

@Repository
public class SysProjectDao 
{
	@Resource
	private SimpleDao dao;	
	
	@Resource
	ISysProjectMapper sysProjectMapper;

	public List<SysProjectVo> getSysProjectList(SysProject sysProject) {
		List<SysProjectVo> lstSysProject = sysProjectMapper.getSysProjectList(sysProject);
		return lstSysProject;
	}

	public List<SysProject> getParentSysProList(QueryInfo<SysProject> queryInfo) {
		List<SysProject> lstSysProject = sysProjectMapper.getParentSysProList(queryInfo.getQueryObject());
		return lstSysProject;
	}

	public SysProject getSysProjectByid(String id) {
		return sysProjectMapper.getSysProjectByid(id);
	}

	public int insertSysProject(SysProject sysConfig) {
		return sysProjectMapper.insertSysProject(sysConfig);
	}

	public int setSysProject(SysProject sysconfig) {
		return sysProjectMapper.setSysProject(sysconfig);
	}
	public int delSysProjectByCode(String code){
		return sysProjectMapper.delSysProjectByCode(code);
	}
	public boolean existSysProject(SysProject sysConfig) {
		Integer count = sysProjectMapper.existSysProject(sysConfig);
		count = count == null ? 0 : count;
		return count > 0;
	}
	public List<SysProject> listSubProjects()
	{
		String sql= "select * from sys_project where pid is not null and pid <>'' order by id";				
		return dao.queryForList(sql, SysProject.class);
	}
	
}
