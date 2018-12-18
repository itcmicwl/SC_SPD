package gyqx.platform.sys.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.db.query.QueryResult;
import gyqx.platform.po.SysModule;
import gyqx.platform.sys.dao.mapper.SysModuleMapper;
import gyqx.platform.sys.vo.SysModuleVo;


@Repository
public class SysModuleDao 
{
	@Resource
	private SysModuleMapper mapper;
	@Resource
	private SimpleDao dao;
	
	
private SqlSession session;
	
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	
	
	public List  queryModuleVos(SysModuleVo mv)  throws Exception{
		List<SysModuleVo> data = mapper.queryModuleVoList(mv);
		
		return data;
	}
	
	
	
	
	public QueryResult<SysModuleVo>  queryModuleVos(Map<String, Object> map)  throws Exception{
		List<SysModuleVo> data = mapper.queryModuleVos(map);
		QueryResult<SysModuleVo> result = new QueryResult<SysModuleVo>();		
		result.setData(data);
		return result;
	}
	
	public List<SysModule> listGroupModules(String groupId)
	{
		String sql = "select * from sys_module where group_id = ?";
		return dao.queryForList(sql, SysModule.class, groupId);
	}
  
	
}