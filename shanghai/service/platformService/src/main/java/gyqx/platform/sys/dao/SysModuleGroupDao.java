package gyqx.platform.sys.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.db.query.QueryResult;
import gyqx.platform.po.SysModuleGroup;
import gyqx.platform.sys.dao.mapper.SysModuleGroupMapper;
import gyqx.platform.sys.vo.SysModuleGroupVo;
import gyqx.platform.sys.vo.SysModuleVo;


@Repository
public class SysModuleGroupDao 
{
	@Resource
	private SysModuleGroupMapper mapper;
	@Resource
	private SimpleDao dao;
	
	
	public List  queryModuleGroupVos(SysModuleGroupVo mv)  throws Exception{
		List<SysModuleGroupVo> data = mapper.queryModuleGroupVoList(mv);
		
		return data;
	}
	
	public List<SysModuleGroup> listSubProjectModuleGroups(String projectId)
	{
		String sql = "select * from sys_module_group where project_id = ?";
		return dao.queryForList(sql, SysModuleGroup.class, projectId);
	}
	
	
	
	
  
	
}