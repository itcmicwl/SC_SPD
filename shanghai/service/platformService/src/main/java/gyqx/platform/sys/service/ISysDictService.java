package gyqx.platform.sys.service;

import java.util.Map;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysDict;
import gyqx.platform.po.SysDictValue;
import gyqx.platform.sys.vo.SysDictValueVo;
import gyqx.platform.sys.vo.SysDictVo;

public interface ISysDictService 
{

    public QueryResult<SysDict>  query(Map<String, Object> map) throws Exception;
    public SysDict insert(SysDict dict) throws Exception;	
    public SysDict update(SysDict dict) throws Exception;		
	public void delete(String id) throws Exception;
	
	public QueryResult<SysDictValueVo>  queryDictValueVos (Map<String,Object> map) throws Exception;
	public  SysDictValue updateDictValue(SysDictValue dv) throws Exception;
	public SysDictValue insertDictValue(SysDictValue dv) throws Exception;
	public void  deleteDictValue(String id) throws Exception;
	
	/**
	 * 分页查询字典信息
	 * @return
	 * @throws Exception
	 */
	public QueryResult<SysDict> getDictList(QueryInfo<SysDictVo> queryInfo) throws Exception;
}
