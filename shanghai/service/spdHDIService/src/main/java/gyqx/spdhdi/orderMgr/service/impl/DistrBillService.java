package gyqx.spdhdi.orderMgr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.mysql.jdbc.StringUtils;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.myGoods.dao.ProvGoodsHosDao;
import gyqx.spdhdi.myGoods.dao.ProvGoodsInfoDao;
import gyqx.spdhdi.myGoods.vo.ProvGoods4HosVo;
import gyqx.spdhdi.po.ProvGoodsInfo;
import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import gyqx.spdhdi.myGoods.dao.HosGoodsInfoDao;
import gyqx.spdhdi.myGoods.vo.HosGoodsInfoVo;
import gyqx.spdhdi.orderMgr.Constance;
import gyqx.spdhdi.orderMgr.dao.DistrBillDao;
import gyqx.spdhdi.orderMgr.dao.DistrBillHumitureListDao;
import gyqx.spdhdi.orderMgr.dao.DistrBillListDao;
import gyqx.spdhdi.orderMgr.dao.DistrBillUniqueListDao;
import gyqx.spdhdi.orderMgr.dao.PurconfirmListDao;
import gyqx.spdhdi.orderMgr.service.IDistrBillService;
import gyqx.spdhdi.orderMgr.service.IPurconfirmService;
import gyqx.spdhdi.orderMgr.vo.DistrBillHumitureListVo;
import gyqx.spdhdi.orderMgr.vo.DistrBillListVo;
import gyqx.spdhdi.orderMgr.vo.DistrBillUniqueListVo;
import gyqx.spdhdi.orderMgr.vo.DistrBillVo;
import gyqx.spdhdi.orderMgr.vo.PurconfirmListVo;
import gyqx.spdhdi.orderMgr.vo.PurconfirmVo;

@Service
public class DistrBillService implements IDistrBillService {

	@Resource
	private SimpleDao simpleDao;
	@Resource
	private DistrBillDao dao;
	@Resource
	private DistrBillListDao detailDao; // 明细表DAO
	@Resource
	private DistrBillHumitureListDao distrHumitureDao; // 明细表DAO
	@Resource
	private DistrBillUniqueListDao distrUniqueDao; // 条码表DAO
	@Resource
	private PurconfirmListDao purlistDao; // 确认单明细表DAO
	@Resource
	private IPurconfirmService purService;
	@Resource
	private SysAtomUtil atomUtil;
    @Resource
    private HosGoodsInfoDao hosGoodsInfoDao;

    @Resource
	private ProvGoodsHosDao provGoodsHosDao;
	@Resource
	private UserOnlineStateUtils userOnlineStateUtils;
	@Resource
	private ProvGoodsInfoDao provGoodsInfoDao;
	public DistrBillVo getByBillId(String billId) throws Exception {
		DistrBillVo db = dao.getByBillId(billId);
		if (db != null && !db.getId().equals("")) {
			DistrBillListVo dblVo = new DistrBillListVo();
			dblVo.setPid(db.getId());
			List<DistrBillListVo> lstDetail = detailDao.list(dblVo);
			if (lstDetail.size() > 0) {
				for (DistrBillListVo dbDetail : lstDetail) {
					DistrBillHumitureListVo dbhv = new DistrBillHumitureListVo();
					dbhv.setPid(dbDetail.getId());
					dbhv.setDistrRowNumber(dbDetail.getRowNumber());
					List<DistrBillHumitureListVo> lstDbhv = distrHumitureDao.list(dbhv);
					dbDetail.setDistrBillHumitureList(lstDbhv);

					DistrBillUniqueListVo dbuv = new DistrBillUniqueListVo();
					dbuv.setPid(dbDetail.getId());
					dbuv.setDistrRowNumber(dbDetail.getRowNumber());
					List<DistrBillUniqueListVo> lstDbuv = distrUniqueDao.list(dbuv);
					dbDetail.setDistrBillUniqueList(lstDbuv);
				}
			}
			db.setDistrBillList(lstDetail);
		}
		return db;
	}

	@Override
	public DistrBillVo getById(String id) throws Exception {
		DistrBillVo db = dao.getById(id);
		if (db != null && !db.getId().equals("")) {
			DistrBillListVo dblVo = new DistrBillListVo();
			dblVo.setPid(db.getId());
			db.setDistrBillList(detailDao.list(dblVo));
		}
		return db;
	}

	public DistrBillVo add(DistrBillVo distrBill) throws Exception {
		String billId = atomUtil.newId("disBillId").prefix(distrBill.getPurchaseCompanyId()).get();//atomUtil.newValue("disBillId");
		distrBill.setId(billId);
		distrBill.setBillId(billId);
		List<DistrBillListVo> lstDetail = distrBill.getDistrBillList();
		List<DistrBillHumitureListVo> lstdistrHumiture = new ArrayList<DistrBillHumitureListVo>();
		List<DistrBillUniqueListVo> lstdistrUnique = new ArrayList<DistrBillUniqueListVo>();
		List<HosGoodsInfoVo> lstHgi = new ArrayList<>();
		List<ProvGoodsInfo> lstProvGoods = new ArrayList<>();

		for (DistrBillListVo dbv : lstDetail) {
            if(StringUtils.isNullOrEmpty(dbv.getBatchCode())){
                ValidateException.Throw("batchCode", String.format("%s的批号不能为空", dbv.getProvGoodsErpName()), dbv.getBatchCode());
            }
			if(dbv.getExpdtEndDate() == null){
				ValidateException.Throw("expdtEndDate", String.format("%s的有效期不能为空", dbv.getProvGoodsErpName()), dbv.getExpdtEndDate());
			}
			if(dbv.getExpdtEndDate().getTime() < (new Date()).getTime()){
				ValidateException.Throw("expdtEndDate", String.format("%s的有效期不能在今天之前", dbv.getProvGoodsErpName()), dbv.getExpdtEndDate());
			}
			if (dbv.getSendQty().compareTo(new BigDecimal(0)) == 0) {
				ValidateException.Throw("SendQty", String.format("%s的发货数量不能为零", dbv.getProvGoodsErpName()), dbv.getSendQty().toString());
			}
			String detailId = atomUtil.newId("distrDetailId").prefix(distrBill.getPurchaseCompanyId()).get();//atomUtil.newValue("distrDetailId");
			dbv.setId(detailId);
			dbv.setPid(distrBill.getId());
			dbv.setBillId(distrBill.getBillId());
			//dbv.setSubSendPrice(dbv.getSendPrice());
			for (DistrBillHumitureListVo dbhv : dbv.getDistrBillHumitureList()) {
				dbhv.setId(atomUtil.newValue("distrBHumiture"));
				dbhv.setPid(distrBill.getId());
				dbhv.setDistrRowNumber(dbv.getRowNumber());
				lstdistrHumiture.add(dbhv);
			}
			for (DistrBillUniqueListVo dbuv : dbv.getDistrBillUniqueList()) {
				dbuv.setId(atomUtil.newValue("distrBUniqueCode"));
				dbuv.setPid(distrBill.getId());
				dbuv.setDistrRowNumber(dbv.getRowNumber());
				lstdistrUnique.add(dbuv);
			}
            HosGoodsInfoVo hgi = new HosGoodsInfoVo();
			hgi.setHitCode(dbv.getHitCode());
			hgi.setId(dbv.getHosGoodsId());
			lstHgi.add(hgi);
			ProvGoodsInfo pg = new ProvGoodsInfo();
			pg.setId(dbv.getProvGoodsId());
			pg.setCertificateCode(dbv.getCertificateCode());
			lstProvGoods.add(pg);
		//	simpleDao.executeSql("update prov_goods_info set  certificate_code =? where id =?" ,dbv.getCertificateCode(),dbv.getProvGoodsId());

		}
		if (distrBill.getStatus().equals(Constance.DISTRBILL_STATUS_SUBMIT)) {
			this.setPurconfirm(distrBill);
		}
        hosGoodsInfoDao.updateHitCodeByBatch(lstHgi);
		provGoodsInfoDao.updateCertificateCodeByBatch(lstProvGoods);
		detailDao.insertBatch(lstDetail);// 插入明细表
		if (lstdistrHumiture.size() > 0) {
			distrHumitureDao.insertBatch(lstdistrHumiture);// 插入温湿度表
		}
		if (lstdistrUnique.size() > 0) {
			distrUniqueDao.insertBatch(lstdistrUnique); // 插入条码表
		}
		dao.insert(distrBill);
		return distrBill;
	}

	/**
	 * 配送回写确认单
	 * 
	 * @param distrBill
	 *            配送单
	 * @throws Exception
	 */
	private void setPurconfirm(DistrBillVo distrBill) throws Exception {
		List<DistrBillListVo> dblLst = groupByDistrBillList(distrBill.getDistrBillList());
		List<PurconfirmListVo> purcList = new ArrayList<PurconfirmListVo>();
		List<PurconfirmVo> purcs = new ArrayList<PurconfirmVo>();
		Map<String, DistrBillListVo> purcmap = Tx.converList(dblLst).withKeyName("purconfirmBillId")
				.toMap(DistrBillListVo.class);
		for (String purBillId : purcmap.keySet()) {
			PurconfirmVo pur = purService.getByBillid(purBillId);
			Integer status = Constance.PURCONFIRM_STATUS_ALLSEND;
			for (PurconfirmListVo pcl : pur.getLstDetail()) {
				List<BigDecimal> lst = dblLst.stream().filter(o-> pcl.getBillId().equals(o.getPurconfirmBillId()) && pcl.getRowNumber().equals(o.getPurconfirmRowNumber())).map(DistrBillListVo::getSendQty).collect(Collectors.toList());
				BigDecimal sendQty =  lst.stream().reduce(new BigDecimal(0), BigDecimal::add);
				pcl.setSendQty(pcl.getSendQty().add(sendQty));
				int flag = pcl.getQty().compareTo(pcl.getSendQty());
				if (flag < 0) {
					ValidateException.Throw("SendQty", String.format("%s发货数量大于可发货数量", pcl.getProvGoodsErpName()), "");
				} else if (flag == 0) {
					pcl.setStatus(Constance.PURCONFIRM_LIST_STATUS_ALLSEND);
				} else if (flag == 1) {
					pcl.setStatus(Constance.PURCONFIRM_LIST_STATUS_SOMESEND);
					status = Constance.PURCONFIRM_STATUS_SOMESEND;
				}
				purcList.add(pcl);
			}
			pur.setStatus(status);
			purcs.add(pur);
		}
		purService.updateByBatch(purcs);
		purlistDao.updateByBatch(purcList);// 回写确认单明细表发货数量及状态

	}

	private List<DistrBillListVo> groupByDistrBillList(List<DistrBillListVo> lst) {
		List<DistrBillListVo> res = new ArrayList<DistrBillListVo>();
		if (null == lst || lst.isEmpty()) {
			return res;
		}
		for (DistrBillListVo dblv : lst) {
			if (res.contains(dblv)) {
				DistrBillListVo tmp = res.get(res.indexOf(dblv));
				tmp.setSendQty(tmp.getSendQty().add(dblv.getSendQty()));
			} else {
				res.add(dblv);
			}
		}
		return res;
	}

	public DistrBillVo updateState(DistrBillVo distrBill) throws Exception {
		dao.update(distrBill);
		return distrBill;
	}

	public DistrBillVo update(DistrBillVo distrBill) throws Exception {
		List<DistrBillListVo> lstDetail = distrBill.getDistrBillList();
		List<DistrBillHumitureListVo> lstdistrHumiture = new ArrayList<DistrBillHumitureListVo>();
		List<DistrBillUniqueListVo> lstdistrUnique = new ArrayList<DistrBillUniqueListVo>();
        List<HosGoodsInfoVo> lstHgi = new ArrayList<>();
		for (DistrBillListVo dbv : lstDetail) {
			if (dbv.getSendQty().compareTo(new BigDecimal(0)) == 0) {
				ValidateException.Throw("SendQty", String.format("%s的发货数量不能为零", dbv.getProvGoodsErpName()), "");
			}
			String detailId = atomUtil.newId("distrDetailId").prefix(distrBill.getPurchaseCompanyId()).get();//atomUtil.newValue("distrDetailId");
			dbv.setId(detailId);
			dbv.setPid(distrBill.getId());
			dbv.setBillId(distrBill.getBillId());
		//	dbv.setSubSendPrice(dbv.getSendPrice());
			for (DistrBillHumitureListVo dbhv : dbv.getDistrBillHumitureList()) {
				dbhv.setId(atomUtil.newValue("distrBHumiture"));
				dbhv.setPid(distrBill.getId());
				dbhv.setDistrRowNumber(dbv.getRowNumber());
				lstdistrHumiture.add(dbhv);
			}
			for (DistrBillUniqueListVo dbuv : dbv.getDistrBillUniqueList()) {
				dbuv.setId(atomUtil.newValue("distrBUniqueCode"));
				dbuv.setPid(distrBill.getId());
				dbuv.setDistrRowNumber(dbv.getRowNumber());
				lstdistrUnique.add(dbuv);
			}
            HosGoodsInfoVo hgi = new HosGoodsInfoVo();
            hgi.setHitCode(dbv.getHitCode());
            hgi.setId(dbv.getHosGoodsId());
            lstHgi.add(hgi);
		}
		if (distrBill.getStatus().equals(Constance.DISTRBILL_STATUS_SUBMIT)) {
			this.setPurconfirm(distrBill);
		}
		detailDao.delByBillId(distrBill.getBillId());
		distrHumitureDao.delById(distrBill.getId());
		distrUniqueDao.delByPid(distrBill.getId());
        hosGoodsInfoDao.updateHitCodeByBatch(lstHgi);
		detailDao.insertBatch(lstDetail);// 插入明细表
		if (lstdistrHumiture.size() > 0) {
			distrHumitureDao.insertBatch(lstdistrHumiture);// 插入温湿度表
		}
		if (lstdistrUnique.size() > 0) {
			distrUniqueDao.insertBatch(lstdistrUnique); // 插入条码表
		}
		dao.update(distrBill);
		return distrBill;
	}

	@Override
	public DistrBillVo updateStatus(DistrBillVo distrBill) throws Exception {
		dao.updateStatus(distrBill);
		return distrBill;
	}

	public List<DistrBillVo> list(DistrBillVo bean) throws Exception {
		return (List<DistrBillVo>) dao.list(bean);
	}

	@Override
	public List<DistrBillVo> seclist(DistrBillVo bean) throws Exception {
		return (List<DistrBillVo>) dao.seclist(bean);
	}

	public QueryResult<DistrBillVo> listByPage(QueryInfo<DistrBillVo> queryInfo) throws Exception {

		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.list(queryInfo.getQueryObject()));
	}

	@Override
	public QueryResult<DistrBillVo> secListByPage(QueryInfo<DistrBillVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(dao.seclist(queryInfo.getQueryObject()));
	}

	@Override
	public boolean updateConvertDistrBill(String billId, DistrBillVo distrBillVo) throws Exception {
		DistrBillVo db = this.getByBillId(billId);
		if(db.getIsConverted().equals(1)){
			throw new Exception("此配送单已转换");
		}

		/*
		for (DistrBillListVo dbl : db.getDistrBillList()) {
			if(dbl.getSendPrice() == null || dbl.getPurPrice() == null){
				throw new Exception("二级供应商配送价或集配商采购价为空，无转换");
			}
			if(dbl.getSubSendPrice().compareTo(dbl.getPurPrice()) == 0){
				//dbl.setSubSendPrice(dbl.getSendPrice());
				//dbl.setSendPrice(dbl.getSalePrice());
			}else{
				throw new Exception("二级供应商配送价和集配商采购价不一致，无法转换");
			}
		}

		db.setIsConverted(1);
		db.setStatus(Constance.DISTRBILL_STATUS_SUBMIT);
		this.dao.update(db);
		this.detailDao.updateByBatch(db.getDistrBillList());
		*/

		// 判断统一配送单，其配送明细是否存在商品相同而价格不同的情况
		for (int i =0;i<distrBillVo.getDistrBillList().size();i++) {
			DistrBillListVo frstDetail = distrBillVo.getDistrBillList().get(i);
			for(int j =i+1;j<distrBillVo.getDistrBillList().size();j++){
				DistrBillListVo nextDetail = distrBillVo.getDistrBillList().get(j);
				if(frstDetail.getProvGoodsId().equals(nextDetail.getProvGoodsId())){
					if(frstDetail.getSubSendPrice().compareTo(nextDetail.getSubSendPrice()) != 0
							|| frstDetail.getSendPrice().compareTo(nextDetail.getSendPrice()) != 0)
					{
						throw new Exception("同一商品价格不一致，不能转换！");
					}
				}
			}
		}

		List<ProvGoods4HosVo> provGoods4HosVoList = new ArrayList<>();
		for (DistrBillListVo dbl : distrBillVo.getDistrBillList()) {

			if(dbl.getSendPrice() == null || dbl.getSubSendPrice() == null){
				throw new Exception("二级供应商配送价或集配商配送价为空，无转换");
			}

			// 采购价或销售价与对码表不一致时，更新对码表
			if(dbl.getSubSendPrice().compareTo(dbl.getPurPrice()) != 0
					|| dbl.getSendPrice().compareTo(dbl.getSalePrice()) != 0 ){
				ProvGoods4HosVo provGoods4HosVo = new ProvGoods4HosVo();

				provGoods4HosVo.setUid(userOnlineStateUtils.getCurrent().getUserId());
				provGoods4HosVo.setPurPrice(dbl.getSubSendPrice());
				provGoods4HosVo.setSalePrice(dbl.getSendPrice());
				provGoods4HosVo.setProvGoodsid(dbl.getProvGoodsId());

				provGoods4HosVoList.add(provGoods4HosVo);
			}
		}

		distrBillVo.setIsConverted(1);
		distrBillVo.setStatus(Constance.DISTRBILL_STATUS_SUBMIT);
		this.dao.update(distrBillVo);
		this.detailDao.updateByBatch(distrBillVo.getDistrBillList());

		// 批量更新对码表
		if(provGoods4HosVoList != null && provGoods4HosVoList.size() > 0){
			this.provGoodsHosDao.updatePriceByGoodsList(provGoods4HosVoList);
		}

		return true;
	}

	@Override
	public boolean back4Price(String billId) throws Exception {
		DistrBillVo db = dao.getByBillId(billId);
		db.setStatus(Constance.DISTRBILL_STATUS_UPDATE_PRICE);
		return dao.updateStatus(db) > 0;
	}

    @Override
    public boolean updatePrice(DistrBillVo distrBill) throws Exception {
        distrBill.setStatus(Constance.DISTRBILL_STATUS_SUBMIT);
        dao.updateStatus(distrBill);
        detailDao.updateByBatch(distrBill.getDistrBillList());
        return true;

    }

    // -----------------------明细表----------------------------
	@Override
	public DistrBillListVo getDetail(String id) throws Exception {
		return detailDao.get(id);
	}

	@Override
	public int insertDetailByBatch(List<DistrBillListVo> distrBillList) throws Exception {
		return detailDao.insertBatch(distrBillList);
	}

	@Override
	public int updateDetail(DistrBillListVo distrBillList) throws Exception {
		return detailDao.update(distrBillList);
	}

	@Override
	public int updateDetailByBatch(List<DistrBillListVo> distrBillList) throws Exception {
		return detailDao.updateByBatch(distrBillList);
	}

	@Override
	public int delDistrBillDetailByPid(String pid) throws Exception {
		return detailDao.delByPid(pid);
	}

	@Override
	public int delDistrBillDetailByBillId(String billId) throws Exception {
		return detailDao.delByBillId(billId);
	}

	@Override
	public List<DistrBillListVo> detailList(DistrBillListVo bean) throws Exception {
		return (List<DistrBillListVo>) detailDao.list(bean);
	}

	// -------------------------温湿度----------------------------
	@Override
	public DistrBillHumitureListVo getDistrHumiture(String id) throws Exception {
		return distrHumitureDao.get(id);
	}

	@Override
	public int insertDistrHumiture(List<DistrBillHumitureListVo> lst) throws Exception {
		return distrHumitureDao.insertBatch(lst);
	}

	@Override
	public int updateDistrHumiture(DistrBillHumitureListVo distrBillHumitureList) throws Exception {
		return distrHumitureDao.update(distrBillHumitureList);
	}

	@Override
	public List<DistrBillHumitureListVo> listDistrHumiture(DistrBillHumitureListVo bean) throws Exception {
		return distrHumitureDao.list(bean);
	}

	// -------------------------条码----------------------------
	@Override
	public DistrBillUniqueListVo getDistrUnique(String id) throws Exception {
		return distrUniqueDao.get(id);
	}

	@Override
	public int insertDistrUnique(List<DistrBillUniqueListVo> distrBillUniqueList) throws Exception {
		return distrUniqueDao.insertBatch(distrBillUniqueList);
	}

	@Override
	public int updateDistrUnique(DistrBillUniqueListVo distrBillUniqueList) throws Exception {
		return distrUniqueDao.update(distrBillUniqueList);
	}

	@Override
	public List<DistrBillUniqueListVo> listDistrUnique(DistrBillUniqueListVo bean) throws Exception {
		return distrUniqueDao.list(bean);
	}

	@Override
	public boolean existUniqueCode(String uniqueCode) throws Exception {
		return distrUniqueDao.existUniqueCode(uniqueCode);
	}
	
	//获取收货地址
	public	String getRecAddress(String id) throws Exception {

	   String address=	simpleDao.queryForObject("SELECT p.rec_address from  purconfirm c LEFT JOIN purchase p on p.id = c.pur_bill_id where c.id = ? ", String.class, id);
	   return  address;
	}
}
