package gyqx.spdherp.stockMgr.dao.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdherp.stockMgr.vo.InStockListVo;
import gyqx.spdherp.stockMgr.vo.InStockVo;
import org.apache.ibatis.annotations.Update;

import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;
 
public interface IInStockMapper {
	InStockVo getById(@Param("id") String id);
	InStockVo getByBillId(@Param("billId") String billId);
	int update(InStockVo bean);
	int insert(InStockVo bean);
	int delete(@Param("id") String id);
	int auditAndunAudit(InStockVo queryInfo);
	int charge(InStockVo inStockVo);
	
	/**
	 * 采购入库收货单列表
	 * @param queryInfo
	 * @return
	 */
	List<InStockVo> purchaseList(InStockVo queryInfo) ;
	
	/**
	 * 请购入库单列表
	 * @param queryInfo
	 * @return
	 */
	List<InStockVo> list(InStockVo queryInfo) ;
	List<Out4Settle> inBill4settle(Out4Settle bean);

    int updateDistrBill(@Param("outBillId") String outBillId,
                        @Param("outBillRow") int outBillRow,
                        @Param("receiveDate") Date receiveDate,
                        @Param("receiveTemprature") BigDecimal receiveTemprature,
                        @Param("version") int version);

	
	/**
	 * 更新配送单明细的到货温度和到货时间
	 * @param inStock
	 * @return
	 */
	int updateDistrlst(@Param("psId")String psId,@Param("receiveTemprature")BigDecimal bigDecimal,
			@Param("receiveDate")Date date,@Param("version")int i,@Param("goodsId")String goodsId);
}

