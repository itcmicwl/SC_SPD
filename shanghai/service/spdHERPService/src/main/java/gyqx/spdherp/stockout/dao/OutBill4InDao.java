package gyqx.spdherp.stockout.dao;

import java.util.List;

import javax.annotation.Resource;

import gyqx.spdherp.po.OutStockList;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;
import org.springframework.stereotype.Repository;

import gyqx.spdherp.po.OutStockBatch;
import gyqx.spdherp.po.OutStockUniqueCode;
import gyqx.spdherp.stockout.dao.mapper.BillMgrMapper;
import gyqx.spdherp.stockout.dao.mapper.OutBill4InMapper;
import gyqx.spdherp.stockout.vo.vo4In.OutBill4InVo;
import gyqx.spdherp.stockout.vo.vo4In.OutSub4InVo;

@Repository
public class OutBill4InDao {
	@Resource
	private BillMgrMapper mapper;
	@Resource
	private OutBill4InMapper o4iMapper;

	public List<OutSub4InVo> getSubs(String billId, String isPacket) {
		return o4iMapper.getSubs(billId, isPacket);
	}

	public List<OutStockBatch> getBatchs(String billId) {
		return o4iMapper.getBatchs(billId);
	}
	
	public List<OutStockBatch> getBatchsByPid(String billId,String pid) {
		return o4iMapper.getBatchsByPid(billId,pid);
	}

	public List<OutStockUniqueCode> getUniqueCodes(String billId) {
		return o4iMapper.getUniqueCodes(billId);
	}
	public List<OutStockUniqueCode> getUniqueCodesByPid(String billId,String pid) {
		return o4iMapper.getUniqueCodesByPid(billId,pid);
	}

	public OutBill4InVo getOutBill(String billId) {
		return o4iMapper.getOutBill(billId);
	}

	public List<Out4Settle> outBill4settle(Out4Settle bean) {
		return o4iMapper.outBill4settle(bean);
	}

	public List<OutBill4InVo> list4Instock(OutBill4InVo qb) {
		return o4iMapper.list4Instock(qb);
	}
	public  int updateOutBillLstStatus(List<OutStockList> lst){

		return o4iMapper.updateOutBillLstStatus(lst);
	}
}
