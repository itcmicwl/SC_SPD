package gyqx.platform.sys.service;

import java.util.List;

import common.db.IBean;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysTable;


public interface ITypeTestService 
{
	<T>  T get(Class<? extends IBean> cls,String id);
	Class<? extends IBean> getBeanClassByName(String name) throws Exception;
	
	void doSomething(int v);
	String[] returnStrings();
	
	SysTable get(String id);
	
	SysTable insertAndGet(SysTable table);
	
	SysTable updateAndGet(SysTable table);
	
	int delete(SysTable table);
	
	List<SysTable> listSysTable(QueryInfo<SysTable> queryInfo);
	
	QueryResult<SysTable> listSysTableByPage(QueryInfo<SysTable> queryInfo);
	

}
