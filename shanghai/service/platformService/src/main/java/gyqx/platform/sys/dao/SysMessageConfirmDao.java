package gyqx.platform.sys.dao;


import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.platform.po.SysMessageConfirm;
import gyqx.platform.sys.dao.mapper.ISysMessageConfirmMapper;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;

@Repository
public class SysMessageConfirmDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private ISysMessageConfirmMapper mapper;

	public SysMessageConfirm get(String id)
	{
		return dao.get(SysMessageConfirm.class, id);	
	}	

	public SysMessageConfirm insertAndGet(SysMessageConfirm sysMessageConfirm) throws Exception 
	{
		return dao.insertAndGet(sysMessageConfirm);
	}

	public SysMessageConfirm updateAndGet(SysMessageConfirm sysMessageConfirm) throws Exception {
		return dao.updateAndGet(sysMessageConfirm);
	}
	public int updateFields(SysMessageConfirm sysMessageConfirm,String fieldNames) throws Exception
	{
		return dao.updateFields(sysMessageConfirm, fieldNames);
	}
	public int updateNotNullFields(SysMessageConfirm sysMessageConfirm) throws Exception
	{
		return dao.updateNotNullFields(sysMessageConfirm);
	}

	public void delete(SysMessageConfirm sysMessageConfirm) throws Exception {
		dao.delete(sysMessageConfirm);
	}
	
	public SysMessageConfirm getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (SysMessageConfirm) dao.getByFieldName(SysMessageConfirm.class, fieldName, fieldValue);
	}
	
	public List<SysMessageConfirm> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<SysMessageConfirm>) dao.queryBeanList(SysMessageConfirm.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(SysMessageConfirm.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<SysMessageConfirm> list(SysMessageConfirm qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
	public int getProUserMsgCount(String projectCode,String userId)throws Exception{
		return mapper.getProUserMsgCount(projectCode,userId);
}
	 public int setMsgRead(SysMessageConfirm sysMessageConfirm) throws Exception{
		return mapper.setMsgRead(sysMessageConfirm);
	 }
}


