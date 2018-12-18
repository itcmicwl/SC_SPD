package gyqx.spdhdi.collector4orderMgr.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdhdi.collector4orderMgr.dao.mapper.IHosCollectorProvMapper;
import gyqx.spdhdi.collector4orderMgr.vo.HosCollectorProvVo;
import gyqx.spdherp.po.HosCollectorProv;

@Repository
public class HosCollectorProvDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IHosCollectorProvMapper mapper;

	public HosCollectorProv get(String id)
	{
		return dao.get(HosCollectorProv.class, id);	
	}	

	public HosCollectorProv insertAndGet(HosCollectorProv hosCollectorProv) throws Exception 
	{
		return dao.insertAndGet(hosCollectorProv);
	}

	public HosCollectorProv updateAndGet(HosCollectorProv hosCollectorProv) throws Exception {
		return dao.updateAndGet(hosCollectorProv);
	}
	public int updateFields(HosCollectorProv hosCollectorProv,String fieldNames) throws Exception
	{
		return dao.updateFields(hosCollectorProv, fieldNames);
	}
	public int updateNotNullFields(HosCollectorProv hosCollectorProv) throws Exception
	{
		return dao.updateNotNullFields(hosCollectorProv);
	}

	public void delete(HosCollectorProv hosCollectorProv) throws Exception {
		dao.delete(hosCollectorProv);
	}
	
	public HosCollectorProv getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (HosCollectorProv) dao.getByFieldName(HosCollectorProv.class, fieldName, fieldValue);
	}
	
	public List<HosCollectorProv> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<HosCollectorProv>) dao.queryBeanList(HosCollectorProv.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(HosCollectorProv.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<HosCollectorProv> list(HosCollectorProv qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
    public List<HosCollectorProvVo> listVo(HosCollectorProvVo qbean) throws Exception{		
		
		return mapper.listVo(qbean);
	}
}


