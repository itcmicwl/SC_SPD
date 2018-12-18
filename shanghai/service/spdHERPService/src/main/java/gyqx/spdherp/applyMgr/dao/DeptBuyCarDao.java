package gyqx.spdherp.applyMgr.dao;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.spdherp.applyMgr.dao.mapper.IDeptBuyCarMapper;
import gyqx.spdherp.applyMgr.vo.DeptCarGoodsStVo;
import gyqx.spdherp.po.DeptBuyCar;

@Repository
public class DeptBuyCarDao 
{
	@Resource
	private SimpleDao dao;	
	@Resource
	private SysConfigUtil configUtil;	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private IDeptBuyCarMapper mapper;

	public DeptBuyCar get(String id)
	{
		return dao.get(DeptBuyCar.class, id);	
	}	

	public DeptBuyCar insertAndGet(DeptBuyCar deptBuyCar) throws Exception 
	{
		deptBuyCar.setId(atomUtil.newValue("dept-buy-car"));
		deptBuyCar.setLastUpdateDatetime(new Date());
		return dao.insertAndGet(deptBuyCar);
	}

	public DeptBuyCar updateAndGet(DeptBuyCar deptBuyCar) throws Exception {
		deptBuyCar.setLastUpdateDatetime(new Date());
		return dao.updateAndGet(deptBuyCar);
	}
	public int updateFields(DeptBuyCar deptBuyCar,String fieldNames) throws Exception
	{
		return dao.updateFields(deptBuyCar, fieldNames);
	}
	public int updateNotNullFields(DeptBuyCar deptBuyCar) throws Exception
	{
		return dao.updateNotNullFields(deptBuyCar);
	}

	public void delete(DeptBuyCar deptBuyCar) throws Exception {
		dao.delete(deptBuyCar);
	}
	
	public DeptBuyCar getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (DeptBuyCar) dao.getByFieldName(DeptBuyCar.class, fieldName, fieldValue);
	}
	
	public List<DeptBuyCar> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<DeptBuyCar>) dao.queryBeanList(DeptBuyCar.class,predicate,orderBy,fieldValue);
	}
	public long count(String predicate,Object...fieldValue) throws Exception
	{
		return dao.count(DeptBuyCar.class, predicate, fieldValue);
	}

	public int updateVersion(Object bean) throws Exception
	{
		return dao.updateVersion(bean);
	}
	public List<DeptBuyCar> list(DeptBuyCar qbean) throws Exception{		
		
		return mapper.list(qbean);
	}
	
    public List<DeptCarGoodsStVo> deptCarGoodsStlist(DeptCarGoodsStVo qbean) throws Exception{		
		
		return mapper.deptCarGoodsStlist(qbean);
	}
}


