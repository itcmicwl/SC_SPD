package gyqx.spdherp.stockout.service;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;
import gyqx.spdherp.stockout.vo.vo4In.OutBill4InVo;
import gyqx.spdherp.stockout.vo.vo4In.OutSub4InVo;

import java.util.List;

/**
 * 
 * @author ganwei
 *
 */

public interface OutBill4InService {
  OutBill4InVo getOutBillDetails(String id,String isPacket);
  QueryResult<OutBill4InVo> getBillList4Instock(QueryInfo<OutBill4InVo> queryInfo);
   int updateOutBillStatus(String billId,int status);
    QueryResult<Out4Settle> outBill4settle(QueryInfo<Out4Settle> queryInfo) throws Exception;

}
