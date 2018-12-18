package gyqx.spdherp.barcodePrintingCenter.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.util.StringUtil;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdherp.barcodePrintingCenter.dao.HosPackageBatchDetailDao;
import gyqx.spdherp.barcodePrintingCenter.dao.HosPackageInfoDao;
import gyqx.spdherp.barcodePrintingCenter.service.IHosPackageInfoService;
import gyqx.spdherp.barcodePrintingCenter.vo.HosPackageInfoVo;
import gyqx.spdherp.po.HosPackageBatchDetail;
import gyqx.spdherp.po.HosPackageInfo;
import gyqx.spdherp.po.OutStockBatch;
import gyqx.spdherp.stockout.vo.vo4In.OutSub4InVo;

@Service
public class HosPackageInfoService implements IHosPackageInfoService {
	
	@Resource
	private HosPackageInfoDao  dao;
	@Resource
	private HosPackageBatchDetailDao  detailDao;
	@Resource
	UserOnlineStateUtils userOnlineState;
	@Resource
	private SysAtomUtil atomUtil;
	
	@Override
	public List<HosPackageInfoVo> addHosPackageInfos(Map<String, List<OutSub4InVo>> map)  throws Exception {
		List<HosPackageInfoVo> vos = new ArrayList<HosPackageInfoVo>();
		for (Map.Entry<String, List<OutSub4InVo>> entry : map.entrySet()) {
			List<OutSub4InVo> OutSub4InVos = entry.getValue();// 同一产品集合：总出库数量 / 包装数量 = 包数 （一包一码）
//			Map<String, OutSub4InVo> batchCodeMaps = new TreeMap<>();
			
			int totalQty = 0; // 同一产品总出库数量
//			int countBatchCode = 0;// 同一产品批号数
			
			Set<String> batchCodes = new TreeSet<String>();// 同一产品批号集合，使用Set集合去重
			
			for(OutSub4InVo o : OutSub4InVos){
				totalQty += o.getOutQty().intValue();
				// 批号不为空，则加入批号集合中
				String batchCode = o.getBatchCode()==null?"":o.getBatchCode();
				batchCodes.add(batchCode);
				
//				batchCodeMaps.put(batchCode, o);
			}
			
			// 如果同一产品都没批号，则countBatchCode设置为1，批号循环一次
//			countBatchCode = batchCodes.size()==0?1:batchCodes.size();
			// 包装数量
			int packageCode = OutSub4InVos.get(0).getPacketCode().intValue();
			// 包数
			int countPackage = totalQty / packageCode;
			
			OutSub4InVo o = OutSub4InVos.get(0);
			for(int i=1;i<=countPackage;i++){
				
				String packageId = atomUtil.newId("packageId").prefix(o.getGoodsId(),o.getPacketCode()).get();
				
				HosPackageInfo hosPackageInfo = new HosPackageInfo();
				hosPackageInfo.setId(atomUtil.newValue("hos_package_info_id"));
				hosPackageInfo.setPackageId(packageId);
//				hosPackageInfo.setRowId();
				hosPackageInfo.setHosId(userOnlineState.getCurrent().getCorpId());
				hosPackageInfo.setHosName(userOnlineState.getCurrent().getCorpName());
				
				hosPackageInfo.setBillId(o.getBillId());
				hosPackageInfo.setGoodsId(o.getGoodsId());
				hosPackageInfo.setGoodsName(o.getGoodsName());
				hosPackageInfo.setGoodsGg(o.getGoodsGg());
				hosPackageInfo.setUnit(o.getUnit());
				hosPackageInfo.setQty(o.getLstOutBatch().get(0).getQty());
				
				hosPackageInfo.setProvId(o.getProvId());
				hosPackageInfo.setProvName(o.getProvName());
				hosPackageInfo.setMfrsId(o.getMfrsId());
				hosPackageInfo.setMfrsName(o.getMfrsName());
				hosPackageInfo.setMade(o.getMade());
				hosPackageInfo.setPacketCode(o.getPacketCode());
				hosPackageInfo.setBatchCode(o.getBatchCode());
				hosPackageInfo.setSterilizationCode(o.getSterilizationCode());
				hosPackageInfo.setSterilizationDate(o.getSterilizationDate());
				hosPackageInfo.setSterilizationEndDate(o.getSterilizationEndDate());
				hosPackageInfo.setExpdtEndDate(o.getExpdtEndDate());
				// 整包数量
				BigDecimal packetQty = hosPackageInfo.getQty().multiply(hosPackageInfo.getPacketCode());
				hosPackageInfo.setPacketQty(packetQty);
				
				HosPackageInfo bean = dao.insertAndGet(hosPackageInfo);
				
				List<OutStockBatch> outStockBatchs = o.getLstOutBatch();
				for(int j=0;j<outStockBatchs.size();j++){
					HosPackageInfoVo vo = new HosPackageInfoVo();
					vo = Tx.transform(bean).to(vo);
					HosPackageBatchDetail packageDetail = new HosPackageBatchDetail();
					packageDetail.setId(atomUtil.newValue("hos_package_batch_detail_id"));
					packageDetail.setPPackageId(bean.getPackageId());
					packageDetail.setRowId(j+1);
					packageDetail.setGoodsId(bean.getGoodsId());
					packageDetail.setGoodsBatchId(outStockBatchs.get(j).getGoodsBatchId());
					packageDetail.setInPrice(outStockBatchs.get(j).getInPrice());
					packageDetail.setInTime(outStockBatchs.get(j).getInTime());
					packageDetail.setQty(outStockBatchs.get(j).getQty());
					packageDetail.setBigBatchCode(outStockBatchs.get(j).getBigBatchCode());
					HosPackageBatchDetail detail = detailDao.insertAndGet(packageDetail);
					vo.setHosPackageBatchDetails(detail);
					vos.add(vo);
				}
			}
		}
		return vos;
	}
	
	public List<HosPackageInfoVo> addHosPackageInfos1(Map<String, List<OutSub4InVo>> map)  throws Exception {
		List<HosPackageInfoVo> vos = new ArrayList<HosPackageInfoVo>();
		for (Map.Entry<String, List<OutSub4InVo>> entry : map.entrySet()) {
			List<OutSub4InVo> OutSub4InVos = entry.getValue();// 同一产品集合：总出库数量 / 包装数量 = 包数 （一包一码）
			int totalQty = 0; // 同一产品总出库数量
			for(OutSub4InVo o : OutSub4InVos){
				totalQty += o.getOutQty().intValue();
			}
			// 包装数量
			int packageCode = OutSub4InVos.get(0).getPacketCode().intValue();
			// 包数
			int countPackage = totalQty / packageCode;
			for(int i=1;i<=countPackage;i++){
				
				OutSub4InVo o = OutSub4InVos.get(0);
				String packageId = atomUtil.newId("packageId").prefix(o.getGoodsId(),o.getPacketCode()).get();
				List<OutStockBatch> outStockBatchs = o.getLstOutBatch();
				for(int j=0;j<outStockBatchs.size();j++){
					HosPackageInfo hosPackageInfo = new HosPackageInfo();
					hosPackageInfo.setId(atomUtil.newValue("hos_package_info_id"));
					hosPackageInfo.setPackageId(packageId);
					hosPackageInfo.setRowId(j+1);
					hosPackageInfo.setHosId(userOnlineState.getCurrent().getCorpId());
					hosPackageInfo.setHosName(userOnlineState.getCurrent().getCorpName());
					
					hosPackageInfo.setBillId(o.getBillId());
					hosPackageInfo.setGoodsId(o.getGoodsId());
					hosPackageInfo.setGoodsName(o.getGoodsName());
					hosPackageInfo.setGoodsGg(o.getGoodsGg());
					hosPackageInfo.setUnit(o.getUnit());
					hosPackageInfo.setQty(o.getLstOutBatch().get(0).getQty());
					
					hosPackageInfo.setProvId(o.getProvId());
					hosPackageInfo.setProvName(o.getProvName());
					hosPackageInfo.setMfrsId(o.getMfrsId());
					hosPackageInfo.setMfrsName(o.getMfrsName());
					hosPackageInfo.setMade(o.getMade());
					hosPackageInfo.setPacketCode(o.getPacketCode());
					hosPackageInfo.setBatchCode(o.getBatchCode());
					hosPackageInfo.setSterilizationCode(o.getSterilizationCode());
					hosPackageInfo.setSterilizationDate(o.getSterilizationDate());
					hosPackageInfo.setSterilizationEndDate(o.getSterilizationEndDate());
					hosPackageInfo.setExpdtEndDate(o.getExpdtEndDate());
					// 整包数量
					BigDecimal packetQty = hosPackageInfo.getQty().multiply(hosPackageInfo.getPacketCode());
					hosPackageInfo.setPacketQty(packetQty);
					
					HosPackageInfo bean = dao.insertAndGet(hosPackageInfo);
					
					HosPackageInfoVo vo = new HosPackageInfoVo();
					vo = Tx.transform(bean).to(vo);
					
					HosPackageBatchDetail packageDetail = new HosPackageBatchDetail();
					packageDetail.setId(atomUtil.newValue("hos_package_batch_detail_id"));
					packageDetail.setPPackageId(bean.getPackageId());
					packageDetail.setRowId(bean.getRowId());
					packageDetail.setGoodsId(bean.getGoodsId());
					packageDetail.setGoodsBatchId(outStockBatchs.get(j).getGoodsBatchId());
					packageDetail.setInPrice(outStockBatchs.get(j).getInPrice());
					packageDetail.setInTime(outStockBatchs.get(j).getInTime());
					packageDetail.setQty(outStockBatchs.get(j).getQty());
					packageDetail.setBigBatchCode(outStockBatchs.get(j).getBigBatchCode());
					HosPackageBatchDetail detail = detailDao.insertAndGet(packageDetail);
					vo.setHosPackageBatchDetails(detail);
					vos.add(vo);
				}
				
			}
		}
		return vos;
	}
	
	public HosPackageInfo get(String id)
	{
		return dao.get( id);	
	}	

	public HosPackageInfo add(HosPackageInfo hosPackageInfo) throws Exception 
	{
		return dao.insertAndGet(hosPackageInfo);
	}
		
	public HosPackageInfo update(HosPackageInfo hosPackageInfo) throws Exception {
		return dao.updateAndGet(hosPackageInfo);
	}
	public int updateFields(HosPackageInfo hosPackageInfo,String fieldNames) throws Exception
	{
		return dao.updateFields(hosPackageInfo, fieldNames);
	}

	public void delete(HosPackageInfo hosPackageInfo) throws Exception {
		dao.delete(hosPackageInfo);
	}
	
	public HosPackageInfo getByFieldName(String fieldName, Object fieldValue) throws Exception
	{
		return (HosPackageInfo) dao.getByFieldName(fieldName, fieldValue);
	}
	
	public List<HosPackageInfo> query(String predicate,String orderBy,Object... fieldValue) throws Exception
	{
		return (List<HosPackageInfo>) dao.query(predicate,orderBy,fieldValue);
	}	
	
	public List<HosPackageInfo> list(HosPackageInfo bean) throws Exception
	{
		return (List<HosPackageInfo>) dao.list(bean);
	}	
	
	public QueryResult<HosPackageInfo> listByPage(QueryInfo<HosPackageInfo> queryInfo) throws Exception{		
		
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

}


