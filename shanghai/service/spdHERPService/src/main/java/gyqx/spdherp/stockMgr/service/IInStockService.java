package gyqx.spdherp.stockMgr.service;


import java.util.List;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdherp.stockMgr.vo.InStockVo;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;

public interface IInStockService 
{
	/**
	 * 根据ID获取入库单
	 * @param id
	 * @return
	 * @throws Exception
	 */
	InStockVo getById(String id) throws Exception ;
	InStockVo getByBillId(String billId) throws Exception ;

	/**
	 * 入库单审核
	 * @param inStock 应包含入库单ID，审核人，数据版本
	 * @return
	 * @throws Exception
	 */
	boolean audit(InStockVo inStock) throws  Exception;

	/**
	 * 取消审核
	 * @param inStock 应包含入库单ID，审核人，数据版本
	 * @return
	 * @throws Exception
	 */
	boolean unAudit(InStockVo inStock) throws  Exception;

	/**
	 * 记账（写库存表）
	 * @param inStock 应该包含入库单ID，记账人，数据版本
	 * @return
	 * @throws Exception
	 */
	boolean charge(InStockVo inStock) throws  Exception;
	InStockVo add(InStockVo inStock) throws Exception ;
	InStockVo update(InStockVo inStock) throws Exception;
	void  delete(String id) throws Exception;
	List<InStockVo> list(InStockVo bean) throws Exception;
	QueryResult<InStockVo> listByPage(QueryInfo<InStockVo> queryInfo) throws Exception;
	QueryResult<Out4Settle> inBill4settle(QueryInfo<Out4Settle> queryInfo) throws Exception;
	InStockVo getById4Instock(String id) throws Exception;
	
}


