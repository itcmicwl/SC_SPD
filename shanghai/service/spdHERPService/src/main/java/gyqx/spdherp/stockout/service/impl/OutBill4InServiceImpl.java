package gyqx.spdherp.stockout.service.impl;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import gyqx.spdherp.po.OutStockBatch;
import gyqx.spdherp.po.OutStockUniqueCode;
import gyqx.spdherp.stockout.dao.OutBill4InDao;
import gyqx.spdherp.stockout.service.OutBill4InService;
import gyqx.spdherp.stockout.vo.vo4In.Out4Settle;
import gyqx.spdherp.stockout.vo.vo4In.OutBill4InVo;
import gyqx.spdherp.stockout.vo.vo4In.OutSub4InVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class OutBill4InServiceImpl implements OutBill4InService{
  @Resource
  private SimpleDao simpleDao;
  @Resource
  private OutBill4InDao o4iDao;
  public OutBill4InVo getOutBillDetails(String id, String isPacket){ //id 为billId
	 // o4iDao.getOutBill(billId);
	  OutBill4InVo main=  o4iDao.getOutBill(id);
	  List<OutSub4InVo> subList= o4iDao.getSubs(id,isPacket);
	  for(OutSub4InVo sub:subList){
/*		  //查询批次
		  sub.setLstOutBatch(o4iDao.getBatchsByPid(id, sub.getId())); 
		  //查询唯一码
		  sub.setLstOutUniqueCode(o4iDao.getUniqueCodesByPid(id, sub.getId()));
*/
          if (sub.getIsUnique().equals("3")) {
              sub.setLstOutBatch(o4iDao.getBatchsByPid(id, sub.getId()));
              sub.setLstOutUniqueCode(Collections.EMPTY_LIST);
          } else {
              sub.setLstOutUniqueCode(o4iDao.getUniqueCodesByPid(id, sub.getId()));
              List<OutStockBatch> lstOutBatch = new ArrayList<>();
              sub.getLstOutUniqueCode()
                      .stream()
                      .collect(Collectors.groupingBy(OutStockUniqueCode::getGoodsBatchId, Collectors.counting()))
                      .forEach((key, value) -> sub.getLstOutUniqueCode()
                              .stream()
                              .filter(item -> item.getGoodsBatchId().equals(key))
                              .findFirst()
                              .ifPresent(outStockUniqueCode -> lstOutBatch.add(transUniqueToBatch(value, outStockUniqueCode))));
              sub.setLstOutBatch(lstOutBatch);
          }
		  
	  }
	  main.setLstOutsubVo(subList);
	  return main;
  }

    private OutStockBatch transUniqueToBatch(Long qty, OutStockUniqueCode outStockUniqueCode) {
        OutStockBatch outStockBatch = new OutStockBatch();
        outStockBatch.setId(outStockUniqueCode.getId() + "fake");
        outStockBatch.setPid(outStockUniqueCode.getPid());
        outStockBatch.setBillId(outStockUniqueCode.getBillId());
        outStockBatch.setPRowId(outStockUniqueCode.getPRowId());
        outStockBatch.setProvId(outStockUniqueCode.getProvId());
        outStockBatch.setGoodsId(outStockUniqueCode.getGoodsId());
        outStockBatch.setGoodsBatchId(outStockUniqueCode.getGoodsBatchId());
        outStockBatch.setInPrice(outStockUniqueCode.getInPrice());
        outStockBatch.setInTime(outStockUniqueCode.getInTime());
        outStockBatch.setQty(new BigDecimal(qty));
        outStockBatch.setVersion(outStockUniqueCode.getVersion());
        outStockBatch.setLastUpdateDatetime(outStockUniqueCode.getLastUpdateDatetime());
        return outStockBatch;
    }

    public QueryResult<OutBill4InVo> getBillList4Instock(QueryInfo<OutBill4InVo> queryInfo){
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(o4iDao.list4Instock(queryInfo.getQueryObject()));
   }
  public int updateOutBillStatus(String billId,int status){
	  return  simpleDao.executeSql("update  out_stock set status=? where bill_id=? ", status,billId);
  }

	@Override
	public QueryResult<Out4Settle> outBill4settle(QueryInfo<Out4Settle> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(o4iDao.outBill4settle(queryInfo.getQueryObject()));
	}
}
