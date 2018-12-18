package gyqx.spdhdi.baseData.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;
import java.util.List;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.po.HosKindcode;
import gyqx.spdhdi.baseData.dao.HosKindcodeDao;
import gyqx.spdhdi.baseData.service.IHosKindcodeService;

@Service
public class HosKindcodeService implements IHosKindcodeService {
	
	@Resource
	private HosKindcodeDao  dao;
	
	public HosKindcode get(String id)
	{
		return dao.get( id);	
	}	

	public HosKindcode add(HosKindcode hosKindcode) throws Exception 
	{
		return dao.insertAndGet(hosKindcode);
	}
		
	public HosKindcode update(HosKindcode hosKindcode) throws Exception {
		return dao.updateAndGet(hosKindcode);
	}
	public int updateFields(HosKindcode hosKindcode,String fieldNames) throws Exception
	{
		return dao.updateFields(hosKindcode, fieldNames);
	}

	public void delete(HosKindcode hosKindcode) throws Exception {
		dao.delete(hosKindcode);
	}
	
	public HosKindcode getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (HosKindcode) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<HosKindcode> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<HosKindcode>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<HosKindcode> list(HosKindcode bean) throws Exception
	{
		return (List<HosKindcode>) dao.list(bean);
	}	
	
	public QueryResult<HosKindcode> listByPage(QueryInfo<HosKindcode> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

}


