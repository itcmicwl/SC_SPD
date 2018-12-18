package gyqx.platform.sys.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.db.query.QueryResult;
import gyqx.platform.po.SysDict;
import gyqx.platform.po.SysDictValue;
import gyqx.platform.sys.dao.mapper.SysDictMapper;
import gyqx.platform.sys.vo.SysDictValueVo;
import gyqx.platform.sys.vo.SysDictVo;

@Repository
public class SysDictDao 
{
	@Resource
	private SysDictMapper mapper;
	@Resource
	private SimpleDao dao;
	
	
private SqlSession session;
	
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	
	public QueryResult<SysDict>  query(Map<String, Object> map)  throws Exception{
		List<SysDict> data = mapper.query(map);
		QueryResult<SysDict> result = new QueryResult<SysDict>();		
		result.setData(data);
		return result;
	}
	
	public SysDict insert(SysDict dict) throws Exception{
		return mapper.insert(dict);
	}
	
	public SysDict update(SysDict dict) throws Exception{
		return mapper.update(dict);
	}
	public void delete(String id) throws Exception{
		mapper.delete(id);
	}
	
	
	
	public QueryResult<SysDictValueVo>  queryDictValueVos(Map<String, Object> map)  throws Exception{
		List<SysDictValueVo> data = mapper.queryDictValueVos(map);
		QueryResult<SysDictValueVo> result = new QueryResult<SysDictValueVo>();		
		result.setData(data);
		return result;
	}

    public SysDictValue updateDictValue(SysDictValue dv) throws Exception{
    	return mapper.updateDictValue(dv);
    }
    public SysDictValue insertDictValue(SysDictValue dv) throws Exception{
    	return mapper.insertDictValue(dv);
    }
   
    public  void  deleteDictValue(String id) throws Exception{
	   mapper.deleteDictValue(id);
   }
	
	/**
	 * 分页查询字典信息
	 * @param vo
	 * @return
	 */
	public List<SysDict> getDictListPage(SysDictVo vo) {
		
		return mapper.getDictListPage(vo.getQueryParam());
	}
}