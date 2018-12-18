package gyqx.spdhdi.mycerts.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import common.web.UserOnlineInfo;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;
import gyqx.spdhdi.baseData.vo.BasGoodsInfoVo;
import gyqx.spdhdi.mycerts.dao.JxsqsDao;
import gyqx.spdhdi.mycerts.service.IJxsqsService;
import gyqx.spdhdi.mycerts.vo.JxsqsGoodsVo;
import gyqx.spdhdi.mycerts.vo.JxsqsToHosVo;
import gyqx.spdhdi.mycerts.vo.JxsqsVo;
import gyqx.spdhdi.po.ProvJxsqs;
import gyqx.spdhdi.po.ProvJxsqsGoods;
import gyqx.spdhdi.po.ProvJxsqsHos;
import gyqx.spdhdi.po.ProvJxsqsRegist;
@Service
public class JxsqsService implements IJxsqsService {
	@Resource
	private JxsqsDao jxsqsDao;
	
	@Resource
	private SimpleDao simpleDao;
	
	@Resource
	private SysConfigUtil configUtil;
	@Resource
	private SysAtomUtil atomUtil;
		
	@Resource
	private UserOnlineStateUtils userOnline;

	@Override
	public JxsqsToHosVo getForHospitals(String sqsId) {
		return jxsqsDao.getForHospitals(sqsId);
	}
	
	@Override
	public JxsqsVo getJxsqsById(String id) {
		return jxsqsDao.getJxsqsById(id);
	}
	
	@Override
	public QueryResult<JxsqsVo> getJxsqsList(QueryInfo<JxsqsVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(jxsqsDao.getJxsqsList(queryInfo.getQueryObject()));
	}
	
	@Override
	public QueryResult<JxsqsGoodsVo> getJxsqsGoodsInfo(QueryInfo<JxsqsGoodsVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(jxsqsDao.getJxsqsGoodsInfo(queryInfo.getQueryObject()));
	}

	@Override
	public JxsqsVo addJxsqs(JxsqsVo jxsqsVo) throws Exception {
		ProvJxsqs provJxsqs = new ProvJxsqs();
		UserOnlineInfo currentUser = userOnline.getCurrent();
		provJxsqs.setId(atomUtil.newValue("prov_jxsqs_id"));
		provJxsqs.setChiefId(jxsqsVo.getChiefId());
		provJxsqs.setProvId(jxsqsVo.getProvId());
		provJxsqs.setIsMfrs(jxsqsVo.getIsMfrs());
		provJxsqs.setMfrsId(jxsqsVo.getIsMfrs().equals("1")?provJxsqs.getChiefId():"");
		provJxsqs.setExpdtBeginDate(jxsqsVo.getExpdtBeginDate());
		provJxsqs.setExpdtEndDate(jxsqsVo.getExpdtEndDate());
		provJxsqs.setFilePath(jxsqsVo.getFilePath());
		provJxsqs.setIsValiding("0");
		provJxsqs.setSaleArea(jxsqsVo.getSaleArea());
		provJxsqs.setCorpId(currentUser.getCorpId());
		provJxsqs.setFillDate(new Date());
		provJxsqs.setLastUpdateDatetime(new Date());
		provJxsqs.setUxid(currentUser.getUserId());
		provJxsqs.setVersion(0);
		provJxsqs = simpleDao.insertAndGet(provJxsqs);
		// 保存 经销授权书适用医院信息
		for(BasCompanyInfoVo hos:jxsqsVo.getBasHosInfos()) {
			ProvJxsqsHos provJxsqsHos = new ProvJxsqsHos();
			provJxsqsHos.setId(atomUtil.newValue("prov_jxsqs_hos_id"));
			provJxsqsHos.setHosId(this.getIdByname(hos.getCname()));
			provJxsqsHos.setProvId(jxsqsVo.getProvId());
			provJxsqsHos.setSqsId(provJxsqs.getId());
			
			provJxsqsHos.setFillDate(new Date());
			provJxsqsHos.setLastUpdateDatetime(new Date());
			provJxsqsHos.setUxid(currentUser.getUserId());
			provJxsqsHos.setVersion(0);
			simpleDao.insertAndGet(provJxsqsHos);
		}
		// 保存 经销授权书授权品种信息
		for(BasGoodsInfoVo goods:jxsqsVo.getBasGoodsInfos()) {
			ProvJxsqsGoods provJxsqsGoods = new ProvJxsqsGoods();
			provJxsqsGoods.setId(atomUtil.newValue("prov_jxsqs_goods_id"));
			provJxsqsGoods.setSqsId(provJxsqs.getId());
			provJxsqsGoods.setProvId(provJxsqs.getProvId());
			provJxsqsGoods.setChiefId(provJxsqs.getChiefId());
			provJxsqsGoods.setMfrsId(provJxsqs.getMfrsId());
			provJxsqsGoods.setSqsLastlevelId("");
			provJxsqsGoods.setGoodsId(goods.getId());
			
			provJxsqsGoods.setFillDate(new Date());
			provJxsqsGoods.setLastUpdateDatetime(new Date());
			provJxsqsGoods.setUxid(currentUser.getUserId());
			provJxsqsGoods.setVersion(0);
			simpleDao.insertAndGet(provJxsqsGoods);
		}
		
		return null;
	}
	
	@Override
	public void addJxsqsGoods(JxsqsVo jxsqsVo) throws Exception {
		// 保存 经销授权书授权品种信息
		for(BasGoodsInfoVo goods:jxsqsVo.getBasGoodsInfos()) {
			ProvJxsqsGoods provJxsqsGoods = new ProvJxsqsGoods();
			provJxsqsGoods.setId(atomUtil.newValue("prov_jxsqs_goods_id"));
			provJxsqsGoods.setSqsId(jxsqsVo.getId());
			provJxsqsGoods.setProvId(jxsqsVo.getProvId());
			provJxsqsGoods.setChiefId(jxsqsVo.getChiefId());
			provJxsqsGoods.setMfrsId(jxsqsVo.getMfrsId());
			provJxsqsGoods.setSqsLastlevelId("");
			provJxsqsGoods.setGoodsId(goods.getId());
			
			provJxsqsGoods.setFillDate(new Date());
			provJxsqsGoods.setLastUpdateDatetime(new Date());
			provJxsqsGoods.setUxid(userOnline.getCurrent().getUserId());
			provJxsqsGoods.setVersion(0);
			simpleDao.insertAndGet(provJxsqsGoods);
			
		}
	}
	
	@Override
	public JxsqsVo updateJxsqs(JxsqsVo jxsqsVo) throws Exception {
		ProvJxsqs provJxsqs = new ProvJxsqs();
        Tx.transform(jxsqsVo).to(provJxsqs);
		UserOnlineInfo currentUser = userOnline.getCurrent();
		provJxsqs.setChiefId(jxsqsVo.getChiefId());
		provJxsqs.setProvId(jxsqsVo.getProvId());
		provJxsqs.setMfrsId(jxsqsVo.getIsMfrs().equals("1")?provJxsqs.getChiefId():"");
		provJxsqs.setExpdtBeginDate(jxsqsVo.getExpdtBeginDate());
		provJxsqs.setExpdtEndDate(jxsqsVo.getExpdtEndDate());
		provJxsqs.setIsMfrs(jxsqsVo.getIsMfrs());
		provJxsqs.setCorpId(currentUser.getCorpId());
		provJxsqs.setSaleArea(jxsqsVo.getSaleArea());
		provJxsqs.setLastUpdateDatetime(new Date());
		provJxsqs.setUxid(currentUser.getUserId());
		simpleDao.updateNotNullFields(provJxsqs);
		
		String sql = "";
		String sqsId = jxsqsVo.getId();
		long num = simpleDao.checkExists(ProvJxsqsGoods.class, "sqsId",sqsId);
		if(num>0) {
			sql = "update prov_jxsqs_goods pjg set pjg.chief_id = ?,pjg.prov_id = ? where pjg.sqs_id = ?";
			simpleDao.executeSql(sql, jxsqsVo.getChiefId(),jxsqsVo.getProvId(),sqsId);
		}
		num = simpleDao.checkExists(ProvJxsqsHos.class, "sqsId",sqsId);
		if(num>0) {
			sql = "update prov_jxsqs_hos pjh set pjh.prov_id = ? where pjh.sqs_id = ?";
			simpleDao.executeSql(sql,jxsqsVo.getProvId(),sqsId);
		}
		
		return null;
	}
	
	@SuppressWarnings("unused")
	private boolean checkRegExits(String pjRegId) {
		long res = 0;
		try {
			res = simpleDao.checkExists(ProvJxsqsRegist.class, "certificate_id", pjRegId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res > 0;
	}
	
	/**
	 * 通过产品名称获取产品ID
	 * @param goodsName 产品名称
	 * @return
	 */
	private String getIdByname(String goodsName) {
		String id = simpleDao.queryForObject("select id from bas_company_info where cname = ?", String.class,goodsName);
		return id;
	}
	
	/**
	 * 通过注册证号获取注册证ID
	 * @param regCode 注册证号
	 * @return
	 */
	private String getregIdByregCode(String regCode) {
		String sql = "select id from bas_registration_info where is_old_info='0' and certificate_code = ?";
		String id = simpleDao.queryForObject(sql, String.class,regCode);
		return id;
	}

	@Override
	public void deleteJxsqsToHos(ProvJxsqsHos jxsqsHos) throws Exception {
		String sql = "delete from prov_jxsqs_hos where id = ?";
		simpleDao.executeSql(sql, jxsqsHos.getId());
	}

	@Override
	public void addJxsqsToHos(List<BasCompanyInfoVo> hosInfos,String sqsId,String provId) throws Exception {
		for(BasCompanyInfoVo hos:hosInfos) {
			ProvJxsqsHos provJxsqsHos = new ProvJxsqsHos();
			provJxsqsHos.setId(atomUtil.newValue("prov_jxsqs_hos_id"));
			provJxsqsHos.setHosId(hos.getId());
			provJxsqsHos.setProvId(provId);
			provJxsqsHos.setSqsId(sqsId);
			provJxsqsHos.setFillDate(new Date());
			provJxsqsHos.setLastUpdateDatetime(new Date());
			provJxsqsHos.setUxid(userOnline.getCurrent().getUserId());
			provJxsqsHos.setVersion(0);
			simpleDao.insertAndGet(provJxsqsHos);
		}
		
	}

	@Override
	public void deleteJxsqsCert(ProvJxsqs jxsqs) throws Exception {
		String sql = "update prov_jxsqs set file_path = '' where id = ?";
		simpleDao.executeSql(sql, jxsqs.getId());
	}

	@Override
	public void updateJxsqsCert(ProvJxsqs jxsqs) throws Exception {
		String sql = "update prov_jxsqs set file_path = ? where id = ?";
		simpleDao.executeSql(sql, jxsqs.getFilePath(),jxsqs.getId());
	}

	@Override
	public void deleteJxsqsGoods(ProvJxsqsGoods jxsqsGoods) throws Exception {
		String sql = "delete from prov_jxsqs_goods where id = ?";
		simpleDao.executeSql(sql, jxsqsGoods.getId());
	}

	@Override
	public void deleteJxsqs(JxsqsVo jxsqsVo) throws Exception {
		String sql = "delete from prov_jxsqs_goods where sqs_id = ?";
		simpleDao.executeSql(sql, jxsqsVo.getId());
		sql = "delete from prov_jxsqs_hos where sqs_id = ?";
		simpleDao.executeSql(sql, jxsqsVo.getId());
		sql = "delete from prov_jxsqs where id = ?";
		simpleDao.executeSql(sql, jxsqsVo.getId());
	}

}
