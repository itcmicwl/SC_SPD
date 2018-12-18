package gyqx.spdherp.stockout.dao.mapper;

import java.util.List;

import gyqx.spdherp.po.OutStockList;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;
import org.apache.ibatis.annotations.Param;

import gyqx.spdherp.po.OutStockBatch;
import gyqx.spdherp.po.OutStockUniqueCode;
import gyqx.spdherp.stockout.vo.vo4In.OutBill4InVo;
import gyqx.spdherp.stockout.vo.vo4In.OutSub4InVo;

public interface OutBill4InMapper {

	List<OutSub4InVo> getSubs(@Param("billId") String billId, @Param("isPacket") String isPacket);

	List<Out4Settle> outBill4settle(Out4Settle bean);

	List<OutStockBatch> getBatchs(@Param("billId") String billId);
	List<OutStockBatch> getBatchsByPid(@Param("billId") String billId,@Param("pid") String pid);
	

	List<OutStockUniqueCode> getUniqueCodes(@Param("billId") String billId);
	List<OutStockUniqueCode> getUniqueCodesByPid(@Param("billId") String billId,@Param("pid") String pid);


	OutBill4InVo getOutBill(@Param("billId") String billId);

	List<OutBill4InVo> list4Instock(OutBill4InVo qb);

 int updateOutBillLstStatus(List<OutStockList> lst);
}
