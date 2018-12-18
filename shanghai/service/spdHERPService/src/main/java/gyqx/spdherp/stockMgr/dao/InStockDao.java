package gyqx.spdherp.stockMgr.dao;


import java.util.List;


import javax.annotation.Resource;

import gyqx.spdherp.stockMgr.Constance;
import gyqx.spdherp.stockMgr.vo.InStockListVo;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;
import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import gyqx.spdherp.stockMgr.dao.mapper.IInStockMapper;
import gyqx.spdherp.stockMgr.vo.InStockVo;

@Repository
public class InStockDao 
{
	@Resource
	private SimpleDao dao;	

	@Resource
	private IInStockMapper mapper;

	public InStockVo getById(String id)
	{
		return mapper.getById(id);
	}
	public InStockVo getByBillId(String id)
	{
		return mapper.getByBillId(id);
	}

	public int insert(InStockVo inStock) throws Exception
	{
		return mapper.insert(inStock);
	}

	public int update(InStockVo inStock) throws Exception {
		return mapper.update(inStock);
	}

	public void delete(String id) throws Exception {
		mapper.delete(id);
	}
	
	/**
	 * 采购入库收货单列表
	 * @param qbean
	 * @return
	 * @throws Exception
	 */
	public List<InStockVo> purchaseList(InStockVo qbean) throws Exception{
		return mapper.purchaseList(qbean);
	}
	
	/**
	 * 请购入库单列表
	 * @param qbean
	 * @return
	 * @throws Exception
	 */
	public List<InStockVo> list(InStockVo qbean) throws Exception{
		return mapper.list(qbean);
	}
	public boolean auditAndunAudit(InStockVo inStock,boolean flag) throws  Exception{
		int status = flag? Constance.INSTOCK_STATUS_AUDIT:Constance.INSTOCK_STATUS_SAVE;
        inStock.setStatus(status);
		return  mapper.auditAndunAudit(inStock) > 0;
	}
	public boolean charge(InStockVo inStock) throws Exception{
	    int status = Constance.INSTOCK_STATUS_CHARGE;
        inStock.setStatus(status);
	    return mapper.charge(inStock) >= 0;
    }
	public List<Out4Settle> inBill4settle(Out4Settle bean) {
		return mapper.inBill4settle(bean);
	}

	public int updateDistrBill(InStockVo inStock, InStockListVo inStockListVo) {
		return mapper.updateDistrBill(inStock.getOutBillId(), inStockListVo.getOutBillRow(), inStockListVo.getReceiveDate(), inStockListVo.getReceiveTemprature(), inStockListVo.getVersion());
	}
}


