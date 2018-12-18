package gyqx.platform.sys.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;

import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysMessageConfirm;
import gyqx.platform.sys.dao.SysMessageConfirmDao;
import gyqx.platform.sys.service.ISysMessageConfirmService;

@Service
public class SysMessageConfirmService implements ISysMessageConfirmService {
	
	@Resource
	private SysMessageConfirmDao  dao;
	
	public SysMessageConfirm get(String id)
	{
		return dao.get( id);	
	}	

	public SysMessageConfirm add(SysMessageConfirm sysMessageConfirm) throws Exception 
	{
		return dao.insertAndGet(sysMessageConfirm);
	}
		
	public SysMessageConfirm update(SysMessageConfirm sysMessageConfirm) throws Exception {
		return dao.updateAndGet(sysMessageConfirm);
	}
	public int updateFields(SysMessageConfirm sysMessageConfirm,String fieldNames) throws Exception
	{
		return dao.updateFields(sysMessageConfirm, fieldNames);
	}

	public void delete(SysMessageConfirm sysMessageConfirm) throws Exception {
		dao.delete(sysMessageConfirm);
	}
	
	public SysMessageConfirm getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (SysMessageConfirm) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<SysMessageConfirm> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<SysMessageConfirm>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<SysMessageConfirm> list(SysMessageConfirm bean) throws Exception
	{
		return (List<SysMessageConfirm>) dao.list(bean);
	}	
	
	public QueryResult<SysMessageConfirm> listByPage(QueryInfo<SysMessageConfirm> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

	@Override
	public int getProUserMsgCount(String projectCode,String userId) throws Exception{
		return dao.getProUserMsgCount(projectCode,userId);
	}

	@Override
	public int setMsgRead(SysMessageConfirm sysMessageConfirm) throws Exception {
		return dao.setMsgRead(sysMessageConfirm);
	}
}


