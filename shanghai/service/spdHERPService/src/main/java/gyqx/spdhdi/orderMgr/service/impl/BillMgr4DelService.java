package gyqx.spdhdi.orderMgr.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.base.KV;
import common.base.LV;
import common.db.SimpleDao;
import common.db.datatable.DataTable;
import common.utils.jms.JMSSendUtils;
import common.utils.json.Json;
import gyqx.spdhdi.orderMgr.service.IBillMgr4DelService;
import gyqx.spdhdi.orderMgr.vo.DbOperation;
import gyqx.spdhdi.orderMgr.vo.DistrBillListVo;
import gyqx.spdhdi.po.DistrBillList;
import gyqx.spdhdi.po.PurchaseList;
import gyqx.spdhdi.po.PurconfirmList;
import gyqx.spdherp.po.DeptBuySub;
import gyqx.spdherp.po.HosOutBalanceDetail;
import gyqx.spdherp.po.InStockBatch;
import gyqx.spdherp.po.OutStockBatch;
@Service
public class BillMgr4DelService implements IBillMgr4DelService {
    @Resource
	private SimpleDao simpleDao;
    @Resource
    private JMSSendUtils jmsUtil;
	@Override	
	@SuppressWarnings("rawtypes")
	public Map getContextBillByPSid(String psId) throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT in_stock_kind , bill_id FROM in_stock where bill_id in "
				 +"(SELECT  bill_id FROM in_stock_batch  where  goods_batch_id  =?)";
		
	   DataTable res=	simpleDao.queryDataTable(sql, LV.New(psId));
	   System.out.println(res.getRows());     
		return null;
	}
	@Override
	public int deleteBillRows(List<DistrBillListVo> details) throws Exception {
		List <DistrBillListVo> jsRows = new ArrayList();
		// TODO Auto-generated method stub
		 // 先检查明细行里是否有提交到结算单
		 //String sql = "SELECT * FROM  hos_out_balance_detail where batch_id =? and hos_goods_id=?";
		        for(DistrBillListVo d:details){
		        	if(simpleDao.checkExists(HosOutBalanceDetail.class, "batchId,hosGoodsId", d.getBillId(),d.getHosGoodsId())==0){
		        		//检查是否数量一致
		        		//确认单
		        		   String comfirm_sql = "SELECT * FROM purconfirm_list where bill_id = 'QRhs333201805300004' and hos_goods_id ='hs333|hosGood-05220';";
		        	          PurconfirmList confirmRow =	simpleDao.queryForList(comfirm_sql, PurconfirmList.class, d.getBillId(),d.getHosGoodsId()).get(0);
		        	          if(confirmRow.getQty().compareTo(d.getSendQty()) !=0){
		        	        	  
		        	          }
		        		
		        	}
		        	else 
		        		jsRows.add(d);
		        }
		 // 没提交到结算单的执行删除
		return 0;
	}
	@Override
	public int deleteBillRow(DistrBillListVo d) throws Exception {
		    //提交到结算单直接返回，不允许删除
		String sql ="";
		List<InStockBatch> inBatchLst = null;
		List<OutStockBatch> outBatchLst  = null;
		List<InStockBatch> inBatchLst4apply =null;
		    if(simpleDao.checkExists(HosOutBalanceDetail.class, "batchId,hosGoodsId", d.getBillId(),d.getHosGoodsId())==1)
		    	return -2;  
		    
		    //检查配送单 是不是有多行，可能有不同批号
		    sql="SELECT * FROM distr_bill_list where bill_id =? and hos_goods_id =?" ;
		    List<DistrBillList> disLst = simpleDao.queryForList(sql, DistrBillList.class, d.getBillId(),d.getHosGoodsId());
		    // 计算发货总数量
		    BigDecimal sendQtys = disLst.stream().map(DistrBillList::getSendQty).reduce(new BigDecimal(0), BigDecimal::add);
		    
		    //检查确认单
		 sql="SELECT * FROM purconfirm_list where bill_id =? and hos_goods_id =?" ;
		    PurconfirmList confirmRow =	simpleDao.queryForList(sql, PurconfirmList.class, d.getPurconfirmBillId(),d.getHosGoodsId()).get(0);
		    if(confirmRow.getQty().compareTo(sendQtys) !=0)
		    	return -1;
		    //检查采购单
		    sql ="SELECT * FROM purchase_list where bill_id =? and hos_goods_id =?";
		    PurchaseList purRow = simpleDao.queryForList(sql, PurchaseList.class, d.getPurBillId(),d.getHosGoodsId()).get(0);
		    if(purRow.getPurQty().compareTo(sendQtys) !=0)
		    	return -1;
		    //检查请购单
		    sql="SELECT * FROM dept_buy_sub where bill_id =? and goods_id =?";
		    DeptBuySub  deptBuyRow =  simpleDao.queryForList(sql, DeptBuySub.class, d.getApplyBillId(),d.getHosGoodsId()).get(0);
		    if(deptBuyRow.getApplyQty().compareTo(sendQtys) !=0)
		    	return -1;

	         sql ="SELECT b.* FROM in_stock_batch  b LEFT JOIN in_stock m on m.bill_id = b.bill_id where m.in_stock_kind=1 and b.goods_batch_id=? and b.goods_id=?";

	         inBatchLst = simpleDao.queryForList(sql, InStockBatch.class, d.getBillId(),d.getHosGoodsId());	         
	         if(inBatchLst.size()>0){ //已执行采购入库
	        	   BigDecimal inQty = inBatchLst.stream().map(InStockBatch::getQty).reduce(new BigDecimal(0), BigDecimal::add);
	  	           if(inQty.compareTo(sendQtys) !=0)
	  	        	  return -1;
	  	           else{
                                //检查是否有出库记录  ,ke能分几个出库单出库
	  	        	   sql="SELECT b.* FROM out_stock_batch  b LEFT JOIN out_stock m on m.bill_id = b.bill_id where  b.goods_batch_id=? and b.goods_id=?";
	  	        	   outBatchLst = simpleDao.queryForList(sql, OutStockBatch.class, d.getBillId(),d.getHosGoodsId());	   
	  	        	      if(outBatchLst.size()>0){
				        	   BigDecimal outQty = outBatchLst.stream().map(OutStockBatch::getQty).reduce(new BigDecimal(0), BigDecimal::add);
				        	   
				        	   if(outQty.compareTo(sendQtys) !=0)
				        		   return -1;
				        	   else{   //检查是否执行请购入库
				        		   sql ="SELECT b.* FROM in_stock_batch  b LEFT JOIN in_stock m on m.bill_id = b.bill_id where m.in_stock_kind=2 and b.goods_batch_id=? and b.goods_id=?";
				      	            inBatchLst4apply = simpleDao.queryForList(sql, InStockBatch.class, d.getBillId(),d.getHosGoodsId());
				      	                if(inBatchLst4apply.size()>0){
							      	           BigDecimal inQty4apply = inBatchLst4apply.stream().map(InStockBatch::getQty).reduce(new BigDecimal(0), BigDecimal::add);
									  	           if(inQty4apply.compareTo(sendQtys) !=0)
									  	        	  return -1;
				      	                }
				        	   }
	  	        	      }
	  	           }
	  	           
	         }
	          List<DbOperation> oplst = new ArrayList<DbOperation>();
           
	          // 删除配送单明细
	           simpleDao.deleteByWhere("DistrBillList", "billId,hosGoodsId", d.getBillId(),d.getHosGoodsId());
	           DbOperation psOp = new DbOperation();
	           psOp.setExpression("DELETE FROM distr_bill_list where  bill_id =? and hos_goods_id=?");
	           psOp.setVals(LV.New(d.getBillId(),d.getHosGoodsId()));
	           oplst.add(psOp);
	          //删除确认单明细
	           simpleDao.deleteByWhere("PurconfirmList", "billId,hosGoodsId", d.getPurconfirmBillId(),d.getHosGoodsId());
	           DbOperation qrOp = new DbOperation();
	           qrOp.setExpression("DELETE FROM purconfirm_list where  bill_id =? and hos_goods_id=?");
	           qrOp.setVals(LV.New(d.getPurconfirmBillId(),d.getHosGoodsId()));
	           oplst.add(qrOp);
	         //删除采购单明细
	           simpleDao.deleteByWhere("PurchaseList", "billId,hosGoodsId", d.getPurBillId(),d.getHosGoodsId());
	           DbOperation cgOp = new DbOperation();
	           cgOp.setExpression("DELETE FROM purchase_list where  bill_id =? and hos_goods_id=?");
	           cgOp.setVals(LV.New(d.getPurBillId(),d.getHosGoodsId()) );
	           oplst.add(cgOp);
	        //删除请购单明细
	           simpleDao.deleteByWhere("DeptBuySub", "billId,goodsId", d.getApplyBillId(),d.getHosGoodsId());
		    //删除采购入库及请购入库 批次-明细
	           simpleDao.deleteByWhere("InStockBatch", "goodsBatchId,goodsId", d.getBillId(),d.getHosGoodsId());
	          if(inBatchLst !=null){
		          for(InStockBatch inb:inBatchLst){
		        	   simpleDao.deleteByWhere("InStockList", "id", inb.getPid());
		          }
	          }
	          if(inBatchLst4apply!=null){
		          for(InStockBatch inb4:inBatchLst4apply){
		        	   simpleDao.deleteByWhere("InStockList", "id", inb4.getPid());
		          }
	          }
	          DbOperation rktzOp = new  DbOperation();  //入库台账
	          rktzOp.setExpression("DELETE FROM in_stock_account where  goods_batch_id =? and goods_id =?");
	          rktzOp.setVals(LV.New(d.getBillId(),d.getHosGoodsId()));
	          oplst.add(rktzOp);
	         //删除出库
	          simpleDao.deleteByWhere("OutStockBatch", "goodsBatchId,goodsId", d.getBillId(),d.getHosGoodsId());
	          if(outBatchLst!=null){
		          for(OutStockBatch ob: outBatchLst){
		        	  simpleDao.deleteByWhere("OutStockList", "id", ob.getPid());	  
		          }
	          }
	          //删除库存   库存占用
	          simpleDao.deleteByWhere("HosStockpile", "batchId,hosGoodsId", d.getBillId(),d.getHosGoodsId());
	          if(outBatchLst !=null){
	        	  for(OutStockBatch ob: outBatchLst){
		        	  simpleDao.deleteByWhere("HosTakingStock", "billId,hosGoodsId", ob.getBillId(),d.getHosGoodsId());	  
		          }
		          
	          }
	          DbOperation cktzOp = new  DbOperation();  //入库台账
	          cktzOp.setExpression("DELETE FROM out_stock_account where  goods_batch_id =? and goods_id =?");
	          cktzOp.setVals(LV.New(d.getBillId(),d.getHosGoodsId()));
	          oplst.add(cktzOp);
	          
	          System.out.println("*****************************************************************************");
	          System.out.println( Json.write(oplst));
	          //调用消息队列
	          jmsUtil.send("bill-lst-delete", Json.write(oplst),null);
	          
		return 1;
	}
	@Override
	@SuppressWarnings("rawtypes")
	public Map getBillContextIds(String id) throws Exception {
		
		Map<String,String> reMap = new HashMap<String,String>();
	   //List<Object> pa = new ArrayList();
		//pa.add(id);
       String sql ="";
       String qgIds="";
       String cgIds="";
 	   String qrIds="";
	   String psIds="";
	   String rkIds="";
	   String ckIds="";
	   List<String> qgIDLst= new ArrayList();
	   List<String> cgIDLst= new ArrayList();
	   List<String> qrIDLst= new ArrayList();
	   List<String> psIDLst= new ArrayList();
	   List<String> rkIDLst= new ArrayList();
	   List<String> ckIDLst= new ArrayList();
	   
       if(id.indexOf("b")==0){  // 请购单号
    	   qgIds=id;
    	   //查询采购单
    	   sql="SELECT DISTINCT  bill_id FROM purchase_list  where  apply_bill_id =? " ;    	   
    	 // List<String> cgIDLst=  (List<String>) simpleDao.query(sql, pa); 	
    	  cgIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
    	   cgIds= cgIDLst.stream().collect(Collectors.joining(","));
    	   for(String cgid:cgIDLst){
    		   sql = "SELECT  DISTINCT  bill_id from purconfirm_list where pur_bill_id =?";
    		   qrIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, cgid);
    		   qrIds= qrIds+","+qrIDLst.stream().collect(Collectors.joining(","));
    		  
    		   for(String qrid:qrIDLst){
    			   sql = "SELECT DISTINCT  bill_id  FROM distr_bill_list where purconfirm_bill_id =?";
    			   psIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, qrid);
    			   psIds =psIds+","+ psIDLst.stream().collect(Collectors.joining(","));
    			   for(String psId:psIDLst){
        			   sql = "SELECT DISTINCT  bill_id  FROM in_stock_batch where goods_batch_id =?";
        			   rkIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
        			   rkIds=rkIds+","+rkIDLst.stream().collect(Collectors.joining(","));
        			   sql = "SELECT DISTINCT  bill_id  FROM out_stock_batch where goods_batch_id =?";
        			   ckIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
        			   ckIds  =ckIds+","+ ckIDLst.stream().collect(Collectors.joining(","));
        		   }
    		   }  
    	   }
    	  
  
       }
       
       if(id.indexOf("CG")==0){ //采购单号
    	   cgIds=id;
    	   //找请购
    	   sql="SELECT DISTINCT  apply_bill_id FROM purchase_list  where  bill_id =? " ;    	
           qgIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
           qgIds= qgIds+","+qgIDLst.stream().collect(Collectors.joining(","));
           //找确认
           sql = "SELECT  DISTINCT  bill_id from purconfirm_list where pur_bill_id =?";
		   qrIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
		   qrIds= qrIds+","+qrIDLst.stream().collect(Collectors.joining(","));
		   //找配送
		   sql = "SELECT DISTINCT  bill_id  FROM distr_bill_list where pur_bill_id =?";
		   psIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
		   psIds =psIds+","+ psIDLst.stream().collect(Collectors.joining(","));
          //找入库，出库
		   for(String psId:psIDLst){
			   sql = "SELECT DISTINCT  bill_id  FROM in_stock_batch where goods_batch_id =?";
			   rkIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
			   rkIds=rkIds+","+rkIDLst.stream().collect(Collectors.joining(","));
			   sql = "SELECT DISTINCT  bill_id  FROM out_stock_batch where goods_batch_id =?";
			   ckIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
			   ckIds  =ckIds+","+ ckIDLst.stream().collect(Collectors.joining(","));
		   }
    	   
       }
       if(id.indexOf("QR")==0){ //确认单
    	   qrIds=id;
    	   //找请购
    	   sql="SELECT DISTINCT  apply_bill_id FROM purconfirm_list  where  bill_id =? " ;    	
           qgIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
           qgIds= qgIds+","+qgIDLst.stream().collect(Collectors.joining(","));
           //找采购
           sql = "SELECT  DISTINCT  pur_bill_id from purconfirm_list where bill_id =?";
		   cgIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
		   cgIds= cgIds+","+cgIDLst.stream().collect(Collectors.joining(","));
		   //找配送
		   sql = "SELECT  DISTINCT  bill_id from distr_bill_list where purconfirm_bill_id =?";
		   psIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
		   psIds= psIds+","+psIDLst.stream().collect(Collectors.joining(","));
		   //找入库，出库
		   for(String psId:psIDLst){
			   sql = "SELECT DISTINCT  bill_id  FROM in_stock_batch where goods_batch_id =?";
			   rkIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
			   rkIds=rkIds+","+rkIDLst.stream().collect(Collectors.joining(","));
			   sql = "SELECT DISTINCT  bill_id  FROM out_stock_batch where goods_batch_id =?";
			   ckIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
			   ckIds  =ckIds+","+ ckIDLst.stream().collect(Collectors.joining(","));
		   }
       }
       if(id.indexOf("PS")==0){ //配送单
    	   psIds=id;
    	   //找请购
    	   sql="SELECT DISTINCT  apply_bill_id FROM distr_bill_list  where  bill_id =? " ;    	
           qgIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
           qgIds= qgIds+","+qgIDLst.stream().collect(Collectors.joining(","));
    	   //采购
           sql = "SELECT  DISTINCT  pur_bill_id from distr_bill_list where bill_id =?";
		   cgIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
		   cgIds= cgIds+","+cgIDLst.stream().collect(Collectors.joining(","));
		   
		   //找确认
           sql = "SELECT  DISTINCT  purconfirm_bill_id from distr_bill_list where bill_id =?";
		   qrIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
		   qrIds= qrIds+","+qrIDLst.stream().collect(Collectors.joining(","));
    	   //入库 出库
		   sql = "SELECT DISTINCT  bill_id  FROM in_stock_batch where goods_batch_id =?";
		   rkIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
		   rkIds=rkIds+","+rkIDLst.stream().collect(Collectors.joining(","));
		   sql = "SELECT DISTINCT  bill_id  FROM out_stock_batch where goods_batch_id =?";
		   ckIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
		   ckIds  =ckIds+","+ ckIDLst.stream().collect(Collectors.joining(","));
       }
       if(id.indexOf("RK")==0){ //入库
    	   rkIds=id;
    	  //找配送单
    	   sql = "SELECT DISTINCT  goods_batch_id  FROM in_stock_batch where bill_id =?";
    	   psIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
		   psIds =psIds+","+ psIDLst.stream().collect(Collectors.joining(","));
    	   for(String psId:psIDLst){
    		   //找请购
        	   sql="SELECT DISTINCT  apply_bill_id FROM distr_bill_list  where  bill_id =? " ;    	
               qgIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
               qgIds= qgIds+","+qgIDLst.stream().collect(Collectors.joining(","));
               
               //采购
               sql = "SELECT  DISTINCT  pur_bill_id from distr_bill_list where bill_id =?";
    		   cgIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
    		   cgIds= cgIds+","+cgIDLst.stream().collect(Collectors.joining(","));
    		   
    		   //找确认
               sql = "SELECT  DISTINCT  purconfirm_bill_id from distr_bill_list where bill_id =?";
    		   qrIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
    		   qrIds= qrIds+","+qrIDLst.stream().collect(Collectors.joining(","));
    		   //找出库
    		   sql = "SELECT DISTINCT  bill_id  FROM out_stock_batch where goods_batch_id =?";
    		   ckIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
    		   ckIds  =ckIds+","+ ckIDLst.stream().collect(Collectors.joining(","));
		   }
       }
       if(id.indexOf("CK")==0){ //出库单
    	   ckIds=id;
    	   	  //找配送单
    	   sql = "SELECT DISTINCT  goods_batch_id  FROM out_stock_batch where bill_id =?";
    	   psIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, id);
		   psIds =psIds+","+ psIDLst.stream().collect(Collectors.joining(","));
    	   for(String psId:psIDLst){
    		   //找请购
        	   sql="SELECT DISTINCT  apply_bill_id FROM distr_bill_list  where  bill_id =? " ;    	
               qgIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
               qgIds= qgIds+","+qgIDLst.stream().collect(Collectors.joining(","));
               
               //采购
               sql = "SELECT  DISTINCT  pur_bill_id from distr_bill_list where bill_id =?";
    		   cgIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
    		   cgIds= cgIds+","+cgIDLst.stream().collect(Collectors.joining(","));
    		   
    		   //找确认
               sql = "SELECT  DISTINCT  purconfirm_bill_id from distr_bill_list where bill_id =?";
    		   qrIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
    		   qrIds= qrIds+","+qrIDLst.stream().collect(Collectors.joining(","));
    		   //找入库
    		   sql = "SELECT DISTINCT  bill_id  FROM in_stock where out_bill_id =?";
    		   rkIDLst=  (List<String>) simpleDao.queryValues(sql, String.class, psId);
    		   rkIds  =rkIds+","+ rkIDLst.stream().collect(Collectors.joining(","));
		   }
       }
       reMap.put("1", qgIds);
	   reMap.put("2", cgIds);
	   reMap.put("3", qrIds);
	   reMap.put("4", psIds);
	   reMap.put("5", rkIds);
	   reMap.put("6", ckIds);
	   return reMap;
		
	}

	
}
