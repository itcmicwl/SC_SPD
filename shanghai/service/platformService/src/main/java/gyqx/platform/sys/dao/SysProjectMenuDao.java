package gyqx.platform.sys.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.platform.sys.dao.mapper.ISysProMenuMapper;
import gyqx.platform.sys.vo.SysProjectMenuVo;

@Repository
public class SysProjectMenuDao {
	@Resource
	ISysProMenuMapper sysProMenuMapper;

	public List<SysProjectMenuVo> getSysProMenuList(SysProjectMenuVo sysProjectMenuVo) {
		List<SysProjectMenuVo> lstSysProject = sysProMenuMapper.getSysProMenuList(sysProjectMenuVo);
		return lstSysProject;
	}
	public int insertProMenu(SysProjectMenuVo sysProjectMenuVo) {
		return sysProMenuMapper.insertProMenu(sysProjectMenuVo);
	}

	public int updateProMenu(SysProjectMenuVo sysProjectMenuVo) {
		return sysProMenuMapper.updateProMenu(sysProjectMenuVo);
	}
	public int deleteProMenu(String id){
		return sysProMenuMapper.deleteProMenu(id);
	}
	public boolean existSysProject(SysProjectMenuVo sysProjectMenuVo) {
		Integer count = sysProMenuMapper.existProMenuCode(sysProjectMenuVo);
		count = count == null ? 0 : count;
		return count > 0;
	}
	public List<SysProjectMenuVo> getParentSysProMenuList(SysProjectMenuVo sysProjectMenuVo) {
		List<SysProjectMenuVo> lstSysProject = sysProMenuMapper.getParentSysProMenuList(sysProjectMenuVo);
		return lstSysProject;
	}
	public List<SysProjectMenuVo> sysMenuKindList(String projectCode){
		return sysProMenuMapper.sysMenuKindList(projectCode);
	}
	public SysProjectMenuVo getById(String id){
		return sysProMenuMapper.getById(id);
	}
}
