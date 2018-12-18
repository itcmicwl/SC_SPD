package gyqx.spdherp.applyMgr.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import common.utils.PageUtils;
import java.util.List;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import gyqx.spdherp.po.DeptBuyCar;
import gyqx.spdherp.applyMgr.dao.DeptBuyCarDao;
import gyqx.spdherp.applyMgr.service.IDeptBuyCarService;
import gyqx.spdherp.applyMgr.vo.DeptCarGoodsStVo;

@Service
public class DeptBuyCarService implements IDeptBuyCarService {
	
	@Resource
	private DeptBuyCarDao  dao;
	@Resource
	private SimpleDao simpleDao;
	public DeptBuyCar get(String id)
	{
		return dao.get( id);	
	}	

	public DeptBuyCar add(DeptBuyCar deptBuyCar) throws Exception 
	{
		//如果 购物车里有记录  hosId deptId  goods_id  一致，则 将need_qty 累加 进行update
		//DeptBuyCar   temp=	simpleDao.queryForObject("select * from dept_buy_car where hos_id = ? and dept_id = ? and goods_id =?  ", DeptBuyCar.class, deptBuyCar.getHosId(),deptBuyCar.getDeptId(),deptBuyCar.getGoodsId());
	  List temps=	simpleDao.queryByFieldNames( DeptBuyCar.class, "hosId,deptId,goodsId", deptBuyCar.getHosId(),deptBuyCar.getDeptId(),deptBuyCar.getGoodsId());		
		if(temps.size()>0){			
			deptBuyCar.setNeedQty(deptBuyCar.getNeedQty().add(((DeptBuyCar)temps.get(0)).getNeedQty()));
			deptBuyCar.setId(((DeptBuyCar)temps.get(0)).getId());
			deptBuyCar.setVersion(((DeptBuyCar)temps.get(0)).getVersion());
		       return   dao.updateAndGet(deptBuyCar);
			}
		else return dao.insertAndGet(deptBuyCar);
	}
	public void addList(List<DeptBuyCar> cars) throws Exception {
		cars.forEach(c->{
			try {
				this.add(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

		
	public DeptBuyCar update(DeptBuyCar deptBuyCar) throws Exception {
		return dao.updateAndGet(deptBuyCar);
	}
	public int updateFields(DeptBuyCar deptBuyCar,String fieldNames) throws Exception
	{
		return dao.updateFields(deptBuyCar, fieldNames);
	}

	public void delete(DeptBuyCar deptBuyCar) throws Exception {
		dao.delete(deptBuyCar);
	}
	
	public void deleteCars(List<DeptBuyCar> cars) throws Exception {
		for(DeptBuyCar c:cars){
			c = dao.get(c.getId());
			dao.delete(c);
		}
	}
	
	
	public DeptBuyCar getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (DeptBuyCar) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<DeptBuyCar> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<DeptBuyCar>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<DeptBuyCar> list(DeptBuyCar bean) throws Exception
	{
		return (List<DeptBuyCar>) dao.list(bean);
	}	
	
	public QueryResult<DeptBuyCar> listByPage(QueryInfo<DeptBuyCar> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

	public QueryResult<DeptCarGoodsStVo> listByPage_deptCarGoodsStlist(QueryInfo<DeptCarGoodsStVo> queryInfo) throws Exception{
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.deptCarGoodsStlist(queryInfo.getQueryObject()));
	}
	
	public List<DeptCarGoodsStVo> list_deptCarGoodsStlist(DeptCarGoodsStVo bean) throws Exception{
		return  dao.deptCarGoodsStlist(bean);
		
	}
}


