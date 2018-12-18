package gyqx.spdhdi.baseData.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.utils.jms.JMSSendUtils;
import gyqx.spdhdi.baseData.vo.BasGoodsImportMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.base.PinYin;
import common.base.TimeoutCheckConcurrentMap;
import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.utils.DictionaryUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.baseData.dao.BasCompanyDao;
import gyqx.spdhdi.baseData.dao.BasGoodsInfoDao;
import gyqx.spdhdi.baseData.dao.BasMfrsInfoDao;
import gyqx.spdhdi.baseData.service.IAddInitGoodsService;
import gyqx.spdhdi.baseData.service.IBasGoodsInfoService;
import gyqx.spdhdi.baseData.vo.BasGoodsInfoVo;
import gyqx.spdhdi.baseData.vo.InitGoodsVo;
import gyqx.spdhdi.collector4orderMgr.dao.HosCollectorDao;
import gyqx.spdhdi.collector4orderMgr.dao.HosCollectorProvDao;
import gyqx.spdhdi.collector4orderMgr.dao.ProvProvGoodsDao;
import gyqx.spdhdi.myGoods.dao.HosGoodsInfoDao;
import gyqx.spdhdi.myGoods.dao.ProvGoodsHosDao;
import gyqx.spdhdi.myGoods.dao.ProvGoodsInfoDao;
import gyqx.spdhdi.myGoods.util.NumberValidationUtils;
import gyqx.spdhdi.myGoods.util.ReadExcle;
import gyqx.spdhdi.myInfos.dao.ProvHosInfoVoDao;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import gyqx.spdhdi.po.BasCompanyInfo;
import gyqx.spdhdi.po.BasGoodsInfo;
import gyqx.spdhdi.po.BasMfrsInfo;
import gyqx.spdhdi.po.HosGoodsInfo;
import gyqx.spdhdi.po.ProvGoodsHos;
import gyqx.spdhdi.po.ProvGoodsInfo;
import gyqx.spdhdi.po.ProvHosInfo;
import gyqx.spdherp.po.HosCollector;
import gyqx.spdherp.po.HosCollectorProv;
import gyqx.spdherp.po.ProvProvGoods;
@Service
@Slf4j
public class BasGoodsInfoService implements IBasGoodsInfoService {
	@Resource
	private BasCompanyDao basCompanyDao;
	@Resource
	private BasMfrsInfoDao basMfrsInfoDao;
	@Resource
	private ProvProvGoodsDao provProvGoodsDao;
	@Resource
	private HosCollectorProvDao hosCollectorProvDao;
	@Resource
	private ProvHosInfoVoDao provHosInfoVoDao;
	@Resource
	private HosCollectorDao hosCollectorDao;
	
	@Resource
	private SimpleDao simpleDao;
	@Resource
	private BasGoodsInfoDao basGoodsDao;
	@Resource
	private ProvGoodsInfoDao provGoodsDao;
	@Resource
	private HosGoodsInfoDao hosGoodsDao;
	@Resource
	private ProvGoodsHosDao pghDao;
	@Resource
	private SysConfigUtil configUtil;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private DictionaryUtils dictionaryUtils;	
	@Resource
	private UserOnlineStateUtils userOnline;
	@Resource
	private JMSSendUtils jmsSendUtils;
	
	@Resource
	private IAddInitGoodsService igService;
	@SuppressWarnings("rawtypes")
	TimeoutCheckConcurrentMap taskStatesMap = new TimeoutCheckConcurrentMap(3600);
	static String userId;
	public static class Task{
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getCur() {
			return cur;
		}
		public void setCur(int cur) {
			this.cur = cur;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public List<InitGoodsVo> getRows() {
			return rows;
		}
		public void setRows(List<InitGoodsVo> rows) {
			this.rows = rows;
		}
		public int getResultTotal() {
			return resultTotal;
		}
		public void setResultTotal(int resultTotal) {
			this.resultTotal = resultTotal;
		}

		volatile int resultTotal;
		volatile int total;
		volatile int cur;
		volatile int status;
		List<InitGoodsVo> rows;
	}
	
	@Override
	public BasGoodsInfo addBasGood(BasGoodsInfo good) throws Exception {
		// TODO Auto-generated method stub
		
		/*  long  num=	simpleDao.queryForObject("select count(*) from bas_Goods_info where code= ? ", Long.class, good.getCode());
		   if(num>0){
			   ValidateException.Throw("code", good.getCode()+ "已存在！",good.getCode() );
			   }*/
		   long  num=	simpleDao.queryForObject("select count(*) from bas_Goods_info where goods_name=? and goods_gg=? and made=? and mfrs_id=? and unit=? ",
				                                  Long.class, good.getGoodsName(),good.getGoodsGg(),good.getMade(),good.getMfrsId(),good.getUnit());
		   if(num>0){
			   ValidateException.Throw("goodsName",  "您输入的商品："+good.getGoodsName() + "及其规格、厂家、产地、单位属性已存在！",good.getGoodsName() );
			  }
		
		String id = atomUtil.newValue("bGoods_id");
		good.setShortPinyin(PinYin.getShortPinyin(good.getGoodsName()));
		good.setId(id);
		good.setCode(id.split("-")[1]);//code 由id流水生成
		//good.setUid( userOnline.getCurrent().getUserId());
			good.setUid(userOnline.getCurrent()==null?this.userId:userOnline.getCurrent().getUserId());
		good.setFillDate(new Date());
		good.setVersion(0);
		good.setFlag("1");
		return simpleDao.insertAndGet(good);
	}

	@Override
	public BasGoodsInfo updateBasGood(BasGoodsInfo good) throws Exception {
		// TODO Auto-generated method stub
		/*  long  num=	simpleDao.queryForObject("select count(*) from bas_Goods_info where code= ?   and id<>?", Long.class, good.getCode(),good.getId());
		   if(num>0){
			   ValidateException.Throw("code", good.getCode() + "已存在！",good.getCode() );
				 }*/
			good.setShortPinyin(PinYin.getShortPinyin(good.getGoodsName()));
			good.setUid( userOnline.getCurrent().getUserId());
			good.setLastUpdateDatetime(new Date());
			return simpleDao.updateAndGet(good);
		
	}

	@Override
	public QueryResult<BasGoodsInfoVo> listBasGoods(QueryInfo<BasGoodsInfoVo> queryInfo) throws Exception {
		// TODO Auto-generated method stub
		 QueryResult<BasGoodsInfoVo> re = new  QueryResult<BasGoodsInfoVo>();
			PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
			List<BasGoodsInfoVo> dataList = basGoodsDao.getBasGoodsInfoList(queryInfo.getQueryObject());
			PageInfo<BasGoodsInfoVo> sysConfigPage = new PageInfo<BasGoodsInfoVo>(dataList);
			re.setData(sysConfigPage.getList());
			re.setPageNum(sysConfigPage.getPageNum());
			re.setPageSize(sysConfigPage.getPageSize());
			re.setTotal(sysConfigPage.getTotal());
		   	return re; 
		
	}

	@Override
	public void deleteBasGood(BasGoodsInfo good) throws Exception {
  	//simpleDao.delete(good);  改为逻辑删除
		good.setFlag("0");
		simpleDao.update(good);
	}
	
	public List<BasGoodsInfo> addBaseGoodS4ImportExcel(List<BasGoodsInfo> goods) throws Exception{
		List<BasGoodsInfo> pgErrorList = new ArrayList();
		for (int i = 0; i < goods.size(); i++) {
			BasGoodsInfo good = goods.get(i);
			if (StringUtils.isEmpty(good.getGoodsName())) {
				log.info("第" + i + "行中 商品名称为空 ");
				good.setRemark("商品名称不能为空");
				pgErrorList.add(good);
				continue;
			}
			if (!StringUtils.isEmpty(good.getMfrsId()) ) {
				if (good.getMfrsId().indexOf("@@4mfrsName") > 0) {// 从Excel导入时附加的特殊字符@@4mfrsName
					// 从厂家表中查询：输入 厂家名 输出 厂家ID
					String mfrsId = good.getMfrsId().split("@@4mfrsName")[0];
					BasMfrsInfo mfr = (BasMfrsInfo) simpleDao.getByFieldName(BasMfrsInfo.class, "mfrsName", mfrsId);
					if (mfr == null) {
						pgErrorList.add(good);
						log.info("第" + i + "行中 厂家" + mfrsId + "在基础数据中 没有对应记录");
						good.setRemark("厂家"+ mfrsId + "在基础数据中 没有对应记录");
						continue;
					} else {
						good.setMfrsId(mfr.getId());
						System.out
								.println("成功匹配   " + mfrsId + "到基础数据 ---->" + mfr.getMfrsName() + "+  " + mfr.getId());
					}
				}
			} else {
				log.info("第" + i + "行中 厂家名称为空" );
				good.setRemark("厂家名称为空");
				pgErrorList.add(good);
				continue;
			}
			//5大要素查重处理
			long  num=	simpleDao.queryForObject("select count(*) from bas_Goods_info where goods_name=? and goods_gg=? and made=? and mfrs_id=? and unit=? ",
                    Long.class, good.getGoodsName(),good.getGoodsGg(),good.getMade(),good.getMfrsId(),good.getUnit());
			if(num>0){
				log.info("第" + i + "行中 五大要素已有记录存在" );
				good.setRemark("名称、规格、产地、厂家、单位已有匹配记录存在");
				pgErrorList.add(good);
				continue;
			}
			
		/* 不要处理code，因code 为id后段流水
		 * 	//检查编号是否为空，编号是否重复
			if (!StringUtils.isEmpty(good.getCode()) ) {
				if (simpleDao.checkExists(HosGoodsInfo.class, "code", good.getCode()) > 0){
					log.info("第" + i + "行中  编号与数据库中其他数据重复" );
					good.setRemark("编号与数据库中其他数据重复");
					pgErrorList.add(good);
					continue;
				}
				
			}
			else {
				 log.info("第" + i + "行中 编号为空" );
					good.setRemark("编号为空");
				 pgErrorList.add(good);
				 continue;
			}*/

			log.info("准备插入第" + i + "行");
			
			// simpleDao.insert(good);
			addBasGood(good);
			
		}
		return pgErrorList;
	}

	public List<InitGoodsVo> add4ImportExcelInit(List<InitGoodsVo> vList) throws Exception{
		for(InitGoodsVo v:vList){
				//先处理产家：通过产家名称查询产家Id
				BasMfrsInfo mfr = (BasMfrsInfo) simpleDao.getByFieldName(BasMfrsInfo.class, "mfrsName", v.getMfrsName());
				if (mfr == null) {
					log.info("第" + v.getRowIndex() + "行中 厂家" + v.getMfrsName() + "在基础数据中 没有对应记录");
				    v.setErrorFlag("1");
				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getMfrsName() + "在厂家表中没有对应记录");
				}
				else {
					  log.info("第" + v.getRowIndex() +"行成功匹配 产家   " + v.getMfrsName() + "到基础数据 ---->" + mfr.getMfrsName() + "+  " + mfr.getId());
					  v.setMfrsName(mfr.getId());  //将厂家id设置到name
					}			
			     //通过68分类名称查询具体的分类ID
//		        DictionaryUtils.DictionaryValue kind68=dictionaryUtils.getDictItem("68FL", v.getKind68Code());
//		        if(kind68 == null){
//		    	   log.info("第" + v.getRowIndex() + "68分类：" + v.getKind68Code() + "在68分类中没有对应记录");
//				    v.setErrorFlag("1");
//				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getKind68Code() + "在68分类中 没有对应记录");
//				}
//		        else{
//		        	 log.info("第" + v.getRowIndex()+"行成功匹配   " +v.getKind68Code() + "到基础数据 ---->" + kind68.getValue());
//		    	    v.setKind68Code(kind68.getValue());
//		    	   
//		        }
				
		        //通过供应商名称找供应商ID
				BasCompanyInfo prov = (BasCompanyInfo) simpleDao.getByFieldName(BasCompanyInfo.class, "cname", v.getProvName());
		        if(prov == null){
		    	   log.info("第" + v.getRowIndex() + "供应商：" + v.getProvName() + "没有对应记录");
				    v.setErrorFlag("1");
				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getProvName() + "供应商表中没有对应记录");
				}
		        else{
		        	 log.info("第" + v.getRowIndex()+"行成功匹配   " +v.getProvName() + "到基础数据 ---->" + prov.getCname());
		    	     v.setProvName(prov.getId());  
		        }
		        //通过医院名称找医院ID
 				BasCompanyInfo hos = (BasCompanyInfo) simpleDao.getByFieldName(BasCompanyInfo.class, "cname", v.getHosName());
 		        if(hos == null){
 		    	    log.info("第" + v.getRowIndex() + "医院：" + v.getHosName() + "医院表没有对应记录");
 				    v.setErrorFlag("1");
 				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getHosName() + "没有对应记录");
 				}
 		        else{
 		        	 log.info("第" + v.getRowIndex()+"行成功匹配   " +v.getHosName() + "到基础数据 ---->" + v.getHosName());
 		    	     v.setHosName(hos.getId());
 		        }
 		        if(!v.getErrorFlag().equals("1")){  //开始处理对象转换  异常数据不参与
 		        	BasGoodsInfo bg = new BasGoodsInfo();
 		        	bg = cvtInitVo2BasGoodsInfo(bg,v);
 		        	ProvGoodsInfo pg = new ProvGoodsInfo();
 		        	pg = cvtInitVo2ProvGoodsInfo(pg, v);
 		        	HosGoodsInfo hg = new HosGoodsInfo();
 		        	hg = cvt2HosGoodsInfo(hg, v);
 		        	ProvGoodsHos pgh = new ProvGoodsHos();
 		        	pgh = cvt2ProvGoodsHos(pgh,v);
 		        	v = add4GoodsItems(v,bg,pg,hg,pgh);
 		        	
 		        }
			
		}
	
		
		return vList;
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Deprecated
	public List<InitGoodsVo> add4ImportExcelInit_v2(List<InitGoodsVo> vList) throws Exception{
		for(InitGoodsVo v:vList){
				//先处理产家：通过产家名称查询产家Id
		
			BasMfrsInfo	 mfrs = (BasMfrsInfo) simpleDao.getByFieldName(BasMfrsInfo.class, "mfrsName", v.getMfrsName());
				if (mfrs == null) {
					/*log.info("第" + v.getRowIndex() + "行中 厂家" + v.getMfrsName() + "在基础数据中 没有对应记录");
				    v.setErrorFlag("1");
				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getMfrsName() + "在厂家表中没有对应记录");
				    */
					//没有厂家就自动添加
					BasMfrsInfo m = new BasMfrsInfo();
					 String id =atomUtil.newValue("mfrs_info_id");
				
					m.setMfrsName(v.getMfrsName());
					m.setId(id); 
					m.setVersion(0);
					m.setMfrsKind("u");
					int i = basMfrsInfoDao.insertBasMfrsInfo(m);
					v.setMfrsName(m.getId());
				}
				else {
					  log.info("第" + v.getRowIndex() +"行成功匹配 产家   " + v.getMfrsName() + "到基础数据 ---->" + mfrs.getMfrsName() + "+  " + mfrs.getId());
					  v.setMfrsName(mfrs.getId());  //将厂家id设置到name
					}			
			     //通过68分类名称查询具体的分类ID
//		        DictionaryUtils.DictionaryValue kind68=dictionaryUtils.getDictItem("68FL", v.getKind68Code());
//		        if(kind68 == null){
//		    	   log.info("第" + v.getRowIndex() + "68分类：" + v.getKind68Code() + "在68分类中没有对应记录");
//				    v.setErrorFlag("1");
//				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getKind68Code() + "在68分类中 没有对应记录");
//				}
//		        else{
//		        	 log.info("第" + v.getRowIndex()+"行成功匹配   " +v.getKind68Code() + "到基础数据 ---->" + kind68.getValue());
//		    	    v.setKind68Code(kind68.getValue());
//		    	   
//		        }
				
				//通过集配商名称找集配商ID
				BasCompanyInfo collector = (BasCompanyInfo) simpleDao.getByFieldName(BasCompanyInfo.class, "cname", v.getCollectorName());
		        if(collector == null){
		    	   log.info("第" + v.getRowIndex() + "集配商：" + v.getCollectorName() + "没有对应记录");
				    v.setErrorFlag("1");
				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getCollectorName() + "供应商表中没有集配商对应记录");
				}
		        else{
		        	 log.info("第" + v.getRowIndex()+"行成功匹配   " +v.getCollectorName() + "到基础数据 ---->" + collector.getCname());
		    	     v.setCollectorName(collector.getId());  
		        }
				
		        //通过供应商名称找供应商ID
				BasCompanyInfo prov = (BasCompanyInfo) simpleDao.getByFieldName(BasCompanyInfo.class, "cname", v.getProvName());
		        if(prov == null){
//		    	   log.info("第" + v.getRowIndex() + "供应商：" + v.getProvName() + "没有对应记录");
//				    v.setErrorFlag("1");
//				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getProvName() + "供应商表中没有对应记录");
		        	BasCompanyInfo sp = new BasCompanyInfo();
		        	sp.setId(	atomUtil.newValue("p_company_id"));
		        	sp.setKind("2");
		        	sp.setErpCode(v.getSubProvErpCode());
		        	sp.setCode(sp.getId());
		        	sp.setShortPinyin(PinYin.getShortPinyin(v.getProvName()));
		        	sp.setCname(v.getProvName());
		        	sp.setVersion(0);
		        	basCompanyDao.insert(sp);
		        	v.setProvName(sp.getId());
				}
		        else{
		        	 log.info("第" + v.getRowIndex()+"行成功匹配   " +v.getProvName() + "到基础数据 ---->" + prov.getCname());
		    	     v.setProvName(prov.getId());  
		        }
		        //通过医院名称找医院ID
 				BasCompanyInfo hos = (BasCompanyInfo) simpleDao.getByFieldName(BasCompanyInfo.class, "cname", v.getHosName());
 		        if(hos == null){
 		    	    log.info("第" + v.getRowIndex() + "医院：" + v.getHosName() + "医院表没有对应记录");
 				    v.setErrorFlag("1");
 				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getHosName() + "没有对应记录");
 				}
 		        else{
 		        	 log.info("第" + v.getRowIndex()+"行成功匹配   " +v.getHosName() + "到基础数据 ---->" + v.getHosName());
 		    	     v.setHosName(hos.getId());
 		        }
 		        if(!v.getErrorFlag().equals("1")){  //开始处理对象转换  异常数据不参与
 		        	BasGoodsInfo bg = new BasGoodsInfo();
 		        	bg = cvtInitVo2BasGoodsInfo(bg,v);
 		        	ProvGoodsInfo pg = new ProvGoodsInfo();
 		        	pg = cvtInitVo2ProvGoodsInfo(pg, v);
 		        	HosGoodsInfo hg = new HosGoodsInfo();
 		        	hg = cvt2HosGoodsInfo(hg, v);
 		        	ProvGoodsHos pgh = new ProvGoodsHos();
 		        	pgh = cvt2ProvGoodsHos(pgh,v);
 		        	//v = add4GoodsItems(v,bg,pg,hg,pgh);
 		        	
 		        	/*添加 prov_hos_info（医院与供应商供销关系）
 		             hos_collector（医院与集配商关系）
 		             hos_collector_prov（医院下集配商与二级供应商的关系）
 		             prov_prov_goods（医院下 集配商与二级供应商的商品关系）
 		             */
 		        	ProvHosInfoVo phi_vo = new ProvHosInfoVo();
 		        	phi_vo = cvt2ProvHosInfo(phi_vo,v);
 		        	HosCollector hc = new HosCollector();
 		        	HosCollectorProv hcp = new HosCollectorProv();
 		        	ProvProvGoods ppg = new ProvProvGoods();
 		        	
 		      //  	v = add4GoodsItems_v2(v,bg,pg,hg,pgh,phi_vo,hc,hcp,ppg);
 		        	
 		        }
			
		}
	
		
		return vList;
	}
	
	private ProvHosInfoVo cvt2ProvHosInfo(ProvHosInfoVo phi_vo,InitGoodsVo v){
		phi_vo.setHosId(v.getHosName());
		phi_vo.setProvId(v.getProvName());
		phi_vo.setStatus("2"); //直接审批通过
		phi_vo.setCollectId(v.getCollectorName());
		phi_vo.setIsHosView("1"); //医院可见
		return phi_vo;
	}
	private BasGoodsInfo cvtInitVo2BasGoodsInfo(BasGoodsInfo bg,InitGoodsVo v) {
		       bg.setFlag("1"); //激活状态
			   bg.setGoodsName(v.getGoodsName());
			   bg.setGoodsGg(v.getGoodsGg());
			   bg.setMade(v.getMade()); 
			   bg.setUnit(v.getUnit());    
			   bg.setMfrsId(v.getMfrsName());		  
		       bg.setBrand(v.getBrand());			  			 
		       bg.setPackeage(new BigDecimal(v.getPackeage()));
		       bg.setMasterCode(v.getMasterCode());
		       bg.setCertificateCode(v.getCertificateCode());	
               bg.setKind68code(v.getKind68Code());	

		    
		       return bg;
	}
	
    private ProvGoodsInfo cvtInitVo2ProvGoodsInfo(ProvGoodsInfo pg,InitGoodsVo v){
    	 pg.setFlag("1");
		 pg.setGoodsName(v.getGoodsName());	
		 pg.setGoodsGg(v.getGoodsGg());		  
		 pg.setMade(v.getMade());  	
		 pg.setUnit(v.getUnit()); 		    	 
    	 pg.setMfrsId(v.getMfrsName());			    
    	 pg.setBrand(v.getBrand());			    
    	 pg.setPackeage(new BigDecimal(v.getPackeage()));   	
    	 pg.setMasterCode(v.getMasterCode());			  
    	 pg.setMfrsCode(v.getMfrsCode());
    	 pg.setCertificateCode(v.getCertificateCode());
 	    //此处要修改，所有货都是集配商的     
    	// pg.setProvId(v.getProvName());
    	 pg.setProvId(v.getCollectorName());			    
    	 pg.setCode(v.getProvCode());
         pg.setErpCode(v.getProvCode());
    	 pg.setMasterCode(v.getMasterCode());			    
    	 pg.setKind68code(v.getKind68Code());
   
    	return pg;
    }
    
    private HosGoodsInfo cvt2HosGoodsInfo(HosGoodsInfo hg,InitGoodsVo v){
    	       hg.setFlag("1");
			   hg.setGoodsName(v.getGoodsName());	           
			   hg.setGoodsGg(v.getGoodsGg());
			   hg.setMade(v.getMade());  		    
		       hg.setMfrsId(v.getMfrsName());	    
		       hg.setBrand(v.getBrand());			    
		       hg.setPackeage(new BigDecimal(v.getPackeage()));
	    	   hg.setMasterCode(v.getMasterCode());			  
	    	   hg.setMfrsCode(v.getMfrsCode());  
	    	   hg.setMasterCode(v.getMasterCode());
	    	   hg.setHosId(v.getHosName());
	    	   hg.setCode(v.getHosCode());
	    	   hg.setGeneralName(v.getGeneralName());
		       hg.setPrice(new BigDecimal(v.getSalePrice()));
               hg.setHisPrice(new BigDecimal(v.getHisPrice()));
		   	   hg.setTaxRate(new BigDecimal(v.getTaxRate()));
		       hg.setMiCode(v.getMiCode());
		       hg.setCharging(v.getCharging());
			   hg.setFieldCode2(v.getHwDesc()); //件包
		      // hg.setLbsx(eRowData.get(21).toString()+"@@lbsxName");  // 类别属性需要深入处理
		    	 hg.setUnit(v.getHosUnit());
		    	 hg.setIsMetering(v.getIsMetering());
		    	 hg.setPurMode(v.getPurMode());
		    	 hg.setGoodsDesc(v.getGoodsDesc());     	    
		    	 hg.setKind68code(v.getKind68Code());
		    return hg;
    	
    }

    private ProvGoodsHos cvt2ProvGoodsHos(ProvGoodsHos pgh,InitGoodsVo v){
    	        pgh.setFlag("1");
    	        //此处要修改，所有货都是集配商的    
		    	//pgh.setProvId(v.getProvName()); 
    	        pgh.setProvId(v.getCollectorName()); 
		    	pgh.setHosId(v.getHosName());
		    	pgh.setPurPrice(new BigDecimal(v.getPurPrice()));//集配商向二级供应商采购价格
		    	pgh.setSalePrice(new BigDecimal(v.getSalePrice()));//集配商的配送给医院价格
                pgh.setUnitRate( new BigDecimal(v.getUnitRate()));
		    	pgh.setTaxRate(new BigDecimal(v.getTaxRate()));    	
		    return pgh;
		       
    }
    

    public InitGoodsVo add4GoodsItems_v2(InitGoodsVo v,BasMfrsInfo mfrs4add,BasCompanyInfo prov4add,BasGoodsInfo bg,ProvGoodsInfo pg,HosGoodsInfo hg,ProvGoodsHos pgh,ProvHosInfoVo phi,HosCollector hc,HosCollectorProv hcp,ProvProvGoods ppg  ) throws Exception{
	  
    	//集配商 和医院需要先注册到系统才能导入
    	if(mfrs4add!=null){   //插入厂家
    		int i = basMfrsInfoDao.insertBasMfrsInfo(mfrs4add);
    	}
    	if(prov4add!=null){ //插入二级供应商
    		basCompanyDao.insert(prov4add);
    	}
	
    	//先创建 ProvHosInfo phi, 医院与集配商的供销关系
    	List phiTemps = simpleDao.queryByFieldNames(ProvHosInfo.class,"provId,hosId",v.getCollectorName(),v.getHosName());
    	if(phiTemps.size()==0){  //集配商与医院没有建立供销关系，则加入关系
                    phi.setId(atomUtil.newValue("prov_hos_info_id"));
    		        phi.setIsHosView("1"); //医院可见       
    		        phi.setProvId(phi.getCollectId());
    	    		int re = provHosInfoVoDao.insertProvHosInfo(phi);   
    	    		   log.info("******************************************************:"+re +"xxxx"+phi.getProvId());
    	}
    	//医院与二级供应商是否创建供销关系
    	phiTemps =  simpleDao.queryByFieldNames(ProvHosInfo.class,"provId,hosId",v.getProvName(),v.getHosName());
    	if(phiTemps.size()==0){  //集配商与医院没有建立供销关系，则加入关系
                      phi.setId(atomUtil.newValue("prov_hos_info_id"));
                      phi.setProvId(v.getProvName());
	                  phi.setIsHosView("1"); //医院可见	        
    		        int  re = provHosInfoVoDao.insertProvHosInfo(phi);   
    		        
    		        log.info("******************************************************:"+re +"xxxx"+phi.getProvId());
        }
    	
    	//医院与集配商关系表是否有对应数据
    	List hcTemps =  simpleDao.queryByFieldNames(HosCollector.class,"provId,hosId",v.getCollectorName(),v.getHosName());
    	if(hcTemps.size()==0){
    		hc.setId(v.getHosName()+"|"+atomUtil.newValue("hos_collector_id"));
    		hc.setHosId(v.getHosName());
            hc.setLastUpdateDatetime(new java.util.Date());
            hc.setProvId(v.getCollectorName());
            hc.setVersion(0);
            hc =hosCollectorDao.insertAndGet(hc);
    	}
    	//医院下 集配商 二级供应商  的关系    	HosCollectorProv hcp
        List hcpTemps = simpleDao.queryByFieldNames(HosCollectorProv.class, "hosId,collectorId,provId", v.getHosName(),v.getCollectorName(),v.getProvName());
        if(hcpTemps.size()==0){
        	hcp.setHosId(v.getHosName());
        	hcp.setId(v.getHosName()+"|"+atomUtil.newValue("hos_collector_prov_id"));
        	hcp.setCollectorId(v.getCollectorName());
        	//hcp.setIsUseSubprov(1);  //是否启用二级供应商管理
        	hcp.setIsUseSubprov(Integer.valueOf(v.getIsSubprov())); 
        	hcp.setKind(0);
        	hcp.setLastUpdateDatetime(new java.util.Date());
        	hcp.setProvId(v.getProvName());
        	hcp.setVersion(0);
        	hcp = hosCollectorProvDao.insertAndGet(hcp);
        }
        
    	
    	
    	
    	
    	
    	//先查询BasGoodsInfo是否已有本行数据  good.getGoodsName(),good.getGoodsGg(),good.getMade(),good.getMfrsId(),good.getUnit()
	
	      List  bgTemps=	simpleDao.queryByFieldNames( BasGoodsInfo.class, "goodsName,goodsGg,made,mfrsId,unit", bg.getGoodsName(),bg.getGoodsGg(),bg.getMade(),bg.getMfrsId(),bg.getUnit());		
          if(bgTemps.size()>0){
        	  BasGoodsInfo  temp =(BasGoodsInfo)bgTemps.get(0);
        	  bg.setId(temp.getId());
          }
          else  
        	bg = addBasGood(bg);
      
    //处理供应商商品信息   
    if (simpleDao.checkExists(ProvGoodsInfo.class, "code,provId", pg.getCode(),pg.getProvId()) > 0) {
  			//ValidateException.Throw("code", pg.getCode()+ "已存在！", pg.getCode());
    	/*    log.info("第" + v.getRowIndex() + "供应商商品编码：" + v.getProvCode()+ "在供应商商品表中有重复记录");
		    v.setErrorFlag("1");
		    v.setErrorDesc(v.getErrorDesc()+"|"+v.getProvCode() + "在供应商商品表中有重复记录");
		  return v;*/
    	
    	//已有的数据查出来为后面逻辑使用
    /*	List<ProvGoodsInfoVo> pgList = provGoodsDao.getProvGoodsInfoList(pg);
    	if(pgList.size()==1){
    		pgList.get(0);
    	}*/
	    List<ProvGoodsInfo>	pgList =( List<ProvGoodsInfo>)simpleDao.queryByFieldNames(ProvGoodsInfo.class,"code,provId" ,pg.getCode(),pg.getProvId());
	    if(pgList.size()==1){
		pg=	pgList.get(0);
		}
	    else{
	    	  v.setErrorFlag("1");
			    v.setErrorDesc(v.getErrorDesc()+"|"+v.getProvCode() + "在供应商商品表中有重复记录,但");
			  return v;
	    }
    	
    }
    else{
		pg.setShortPinyin(PinYin.getShortPinyin(pg.getGoodsName()));
		pg.setId(atomUtil.newValue("prov_goods_info_id"));
		pg.setFlag("1");
		pg.setFillDate(new Date());
		pg.setLastUpdateDatetime(pg.getFillDate());
		pg.setVersion(0);
		pg.setSpdGoodsCode(bg.getId());
	//	pg.setUid(userOnline.getCurrent().getUserId());
		pg.setUid(userOnline.getCurrent()==null?this.userId:userOnline.getCurrent().getUserId());
		pg = simpleDao.insertAndGet(pg);
    }
	//处理医院产品信息
	if (simpleDao.checkExists(HosGoodsInfo.class, "code,hosId", hg.getCode(),hg.getHosId()) > 0) {
		    log.info("第" + v.getRowIndex() + "医院商品编码：" + v.getHosCode() + "在医院商品表中有重复记录");
		    v.setErrorFlag("1");
		    v.setErrorDesc(v.getErrorDesc()+"|"+v.getHosCode() + "在医院商品表中已有重复记录");
		  return v;
	}
	else{
		String id =hg.getHosId()+ atomUtil.newValue("hos_goods_info_id");
		hg.setShortPinyin(PinYin.getShortPinyin(hg.getGoodsName()));
		hg.setId(id);
		hg.setFillDate(new Date());
		hg.setLastUpdateDatetime(hg.getFillDate());
		hg.setVersion(0);
		hg.setFlag("1");
		hg.setProvId(pg.getProvId());
	//	hg.setHosMfrsName(hosMfrsName);
		hg.setSpdGoodsCode(bg.getCode());
//		if(StringUtils.isEmpty(hg.getPurMode())||hg.getPurMode().contains("10")){
//			hg.setPurMode("10");
//		}
		if(!StringUtils.isEmpty(hg.getPurMode())){
			hg.setPurMode(v.getPurMode());
		}
		 if(hg.getPurMode().contains("20")){ hg.setPurMode("20");}
		 if(hg.getPurMode().contains("30")){ hg.setPurMode("30");}
		
		if(StringUtils.isEmpty(hg.getIsMetering())||hg.getIsMetering().contains("1.0")||hg.getIsMetering().equals("1")){
			hg.setIsMetering("1");
		}
		else hg.setIsMetering("0");
		
		if(StringUtils.isEmpty(hg.getCharging())||hg.getCharging().contains("1.0")||hg.getCharging().equals("1")){
			hg.setCharging("1");
		}
		else hg.setCharging("0");
		
		
		hg.setPrice(hg.getPrice()!=null?hg.getPrice():new BigDecimal(0));
        hg.setUniqueCodeStrategy(hg.getUniqueCodeStrategy()!=null?hg.getUniqueCodeStrategy():"3");
        hg.setTaxRate(hg.getTaxRate()!=null?hg.getTaxRate():new BigDecimal(0.16));
		//hg.setUid(userOnline.getCurrent().getUserId());	
        hg.setUid(userOnline.getCurrent()==null?this.userId:userOnline.getCurrent().getUserId());
		hg = simpleDao.insertAndGet(hg);
	}
	//处理供货目录表
	//对码 查重处理
	 long  num=	simpleDao.queryForObject("select count(*) from prov_goods_hos where prov_id= ? and  hos_id=? and his_code=? and flag=1  and id<> ?", Long.class, pgh.getProvId(),pgh.getHosId(),pgh.getHisCode(),pgh.getId());
	 if(num>0){
	           //  ValidateException.Throw("hisCode", good.getHisCode() +  "该医院编码已与其他数据关联！",good.getHisCode() );
		        log.info("第" + v.getRowIndex() + "医院商品编码：" + v.getHosCode() + "在对码表中存在重复记录");
			    v.setErrorFlag("1");
		 	    v.setErrorDesc(v.getErrorDesc()+"|"+v.getHosCode() + "在对码表中存在重复记录");
		    return v;
	 }
	 else{
	    pgh.setProvGoodsid(pg.getId());
		pgh.setId(atomUtil.newValue("prov_goods_hos_id"));
		pgh.setHisCode(hg.getCode());
		pgh.setHisName(pg.getGoodsName());
		pgh.setHisUnit(hg.getUnit());
		//pgh.setPurPrice(purPrice); //集配商向二级供应商采购价
		pgh.setFillDate(new Date());
		pgh.setLastUpdateDatetime(pgh.getFillDate());
		pgh.setVersion(0);
		pgh.setFlag("1");
		//pgh.setUid(userOnline.getCurrent().getUserId());
		pgh.setUid(userOnline.getCurrent()==null?this.userId:userOnline.getCurrent().getUserId());
		pgh = simpleDao.insertAndGet(pgh);          
	 }   
	 
 	//ProvProvGoods ppg
	 ppg.setCollectorId(v.getCollectorName());
     ppg.setHosId(v.getHosName());
     ppg.setId(v.getHosName()+"|" + atomUtil.newValue("pp_goods_id"));
     ppg.setLastUpdateDatetime(new java.util.Date());
     ppg.setProvGoodsId(pg.getId());
     ppg.setProvId(v.getProvName());
     ppg.setVersion(0);
	 ppg =provProvGoodsDao.insertAndGet(ppg);
	 
	 
	   
     
	return v;
}
    
    private InitGoodsVo add4GoodsItems(InitGoodsVo v,BasGoodsInfo bg,ProvGoodsInfo pg,HosGoodsInfo hg,ProvGoodsHos pgh) throws Exception{
    	    //先查询BasGoodsInfo是否已有本行数据  good.getGoodsName(),good.getGoodsGg(),good.getMade(),good.getMfrsId(),good.getUnit()
    	
  	      List  bgTemps=	simpleDao.queryByFieldNames( BasGoodsInfo.class, "goodsName,goodsGg,made,mfrsId,unit", bg.getGoodsName(),bg.getGoodsGg(),bg.getMade(),bg.getMfrsId(),bg.getUnit());		
	          if(bgTemps.size()>0){
	        	  BasGoodsInfo  temp =(BasGoodsInfo)bgTemps.get(0);
	        	  bg.setId(temp.getId());
	          }
	          else  
	        	bg = addBasGood(bg);
          
        //处理供应商商品信息             (2018-4-11 待完善逻辑 供应商将货供给多个医院场景） 
	    if (simpleDao.checkExists(ProvGoodsInfo.class, "code", pg.getCode()) > 0) {
	  			//ValidateException.Throw("code", pg.getCode()+ "已存在！", pg.getCode());
	    	    log.info("第" + v.getRowIndex() + "供应商商品编码：" + v.getProvCode()+ "在供应商商品表中有重复记录");
			    v.setErrorFlag("1");
			    v.setErrorDesc(v.getErrorDesc()+"|"+v.getProvCode() + "在供应商商品表中有重复记录");
			  return v;
	    }
	    else{
			pg.setShortPinyin(PinYin.getShortPinyin(pg.getGoodsName()));
			pg.setId(atomUtil.newValue("prov_goods_info_id"));
			pg.setFlag("1");
			pg.setFillDate(new Date());
			pg.setLastUpdateDatetime(pg.getFillDate());
			pg.setVersion(0);
			pg.setSpdGoodsCode(bg.getId());
			pg.setUid(userOnline.getCurrent().getUserId());
			pg = simpleDao.insertAndGet(pg);
	    }
		//处理医院产品信息
		if (simpleDao.checkExists(HosGoodsInfo.class, "code,hosId", hg.getCode(),hg.getHosId()) > 0) {
			    log.info("第" + v.getRowIndex() + "医院商品编码：" + v.getHosCode() + "在医院商品表中有重复记录");
			    v.setErrorFlag("1");
			    v.setErrorDesc(v.getErrorDesc()+"|"+v.getHosCode() + "在医院商品表中已有重复记录");
			  return v;
		}
		else{
			String id =hg.getHosId()+ atomUtil.newValue("hos_goods_info_id");
			hg.setShortPinyin(PinYin.getShortPinyin(hg.getGoodsName()));
			hg.setId(id);
			hg.setFillDate(new Date());
			hg.setLastUpdateDatetime(hg.getFillDate());
			hg.setVersion(0);
			hg.setFlag("1");
			hg.setProvId(pg.getProvId());
		//	hg.setHosMfrsName(hosMfrsName);
			hg.setSpdGoodsCode(bg.getCode());
//			if(StringUtils.isEmpty(hg.getPurMode())||hg.getPurMode().contains("10")){
//				hg.setPurMode("10");
//			}
			if(!StringUtils.isEmpty(hg.getPurMode())){
				hg.setPurMode(v.getPurMode());
			}
			 if(hg.getPurMode().contains("20")){ hg.setPurMode("20");}
			 if(hg.getPurMode().contains("30")){ hg.setPurMode("30");}
			
			if(StringUtils.isEmpty(hg.getIsMetering())||hg.getIsMetering().contains("1.0")){
				hg.setIsMetering("1");
			}
			else hg.setIsMetering("0");
			
			if(StringUtils.isEmpty(hg.getCharging())||hg.getCharging().contains("1.0")){
				hg.setCharging("1");
			}
			else hg.setCharging("0");
			
			
			hg.setPrice(hg.getPrice()!=null?hg.getPrice():new BigDecimal(0));
	        hg.setUniqueCodeStrategy(hg.getUniqueCodeStrategy()!=null?hg.getUniqueCodeStrategy():"3");
	        hg.setTaxRate(hg.getTaxRate()!=null?hg.getTaxRate():new BigDecimal(0.17));
			hg.setUid(userOnline.getCurrent().getUserId());			
			hg = simpleDao.insertAndGet(hg);
		}
		//处理供货目录表
		//对码 查重处理
		 long  num=	simpleDao.queryForObject("select count(*) from prov_goods_hos where prov_id= ? and  hos_id=? and his_code=? and flag=1  and id<> ?", Long.class, pgh.getProvId(),pgh.getHosId(),pgh.getHisCode(),pgh.getId());
		 if(num>0){
		           //  ValidateException.Throw("hisCode", good.getHisCode() +  "该医院编码已与其他数据关联！",good.getHisCode() );
			        log.info("第" + v.getRowIndex() + "医院商品编码：" + v.getHosCode() + "在对码表中存在重复记录");
				    v.setErrorFlag("1");
			 	    v.setErrorDesc(v.getErrorDesc()+"|"+v.getHosCode() + "在对码表中存在重复记录");
			    return v;
		 }
		 else{
		    pgh.setProvGoodsid(pg.getId());
			pgh.setId(atomUtil.newValue("prov_goods_hos_id"));
			pgh.setHisCode(hg.getCode());
			pgh.setHisName(pg.getGoodsName());
			pgh.setHisUnit(hg.getUnit());
			//pgh.setPurPrice(purPrice); //集配商向二级供应商采购价
			pgh.setFillDate(new Date());
			pgh.setLastUpdateDatetime(pgh.getFillDate());
			pgh.setVersion(0);
			pgh.setFlag("1");
			pgh.setUid(userOnline.getCurrent().getUserId());
			pgh = simpleDao.insertAndGet(pgh);          
		 }    
    	return v;
    }

	@Override
	public Map startTask(Map map) throws Exception {
		String s_taksId = map.get("id").toString();
		Task oo = new Task();
		taskStatesMap.set(s_taksId, oo);
	
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public String add4DealImport(Map map,String userId) throws Exception {
	    String s_taksId = map.get("id").toString();
		Task oo =(Task) taskStatesMap.get(s_taksId);
		String  basePath = configUtil.getValue("upload.basepath");
		if(basePath.indexOf(";")>0){
			String[] ss = basePath.split(";");
		    	   if (System.getProperty("os.name").toLowerCase().indexOf("window") >= 0) {
		                basePath = ss[0];
		            } else {
		                basePath = ss[1];
		            }	      
		}
		String localFilePath =basePath+"/"+map.get("path") + "/" + map.get("id") + "." + map.get("ext");
		ReadExcle read = new ReadExcle();
		List<List<Object>> list = read.read2003Excel(new File(localFilePath));
		List<InitGoodsVo> vList = excleRow2InitGoodsVo(list);  //将Excel数据行转换为对象
        vList = add4ImportExcelInit_taks(vList,s_taksId,userId);

        map.put("vList", vList);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Task getBySid(String type,String s_taskId) throws Exception{
		Task now = (Task)taskStatesMap.get(s_taskId);
		Task now2 = new Task();
		if("1".equals(type)){// 状态为1则轮询，直到状态为2结束
			now2.setCur(now.getCur());
			now2.setStatus(now.getStatus());
	        now2.setRows(now.getRows());
			now2.setTotal(now.getTotal());
			now2.setResultTotal(now.getResultTotal());
			return now2;
		}
		return now;
	}

	public List<InitGoodsVo> excleRow2InitGoodsVo(List<List<Object>> list) throws Exception {
		DecimalFormat df = new DecimalFormat("0");
		DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字

		java.text.NumberFormat nf_new = java.text.NumberFormat.getInstance();//将科学计数转原始数据
		nf_new.setGroupingUsed(false);

		List<InitGoodsVo> vList = new ArrayList<InitGoodsVo>();
		for (int i = 1; i < list.size(); i++) {
			List eRowData = list.get(i);
			InitGoodsVo v = new InitGoodsVo();
			v.setRowIndex(String.valueOf(i));
			v.setErrorFlag("0");
			if (!StringUtils.isEmpty(eRowData.get(0)))
				v.setGoodsName(eRowData.get(0).toString().trim());
			if (!StringUtils.isEmpty(eRowData.get(1))){
				v.setGoodsGg(eRowData.get(1).toString().trim());
			}
			if (!StringUtils.isEmpty(eRowData.get(2)))
				v.setMade(eRowData.get(2).toString().trim());
			if (!StringUtils.isEmpty(eRowData.get(3))) // 供应商的计量单位
				v.setUnit(eRowData.get(3).toString());
			if (!StringUtils.isEmpty(eRowData.get(4)))
				v.setMfrsName(eRowData.get(4).toString().trim());
			if (!StringUtils.isEmpty(eRowData.get(5)))
				v.setBrand(eRowData.get(5).toString().trim());
			if (!StringUtils.isEmpty(eRowData.get(6))) {
				if (NumberValidationUtils.isRealNumber(eRowData.get(6).toString())) {
					v.setPackeage(eRowData.get(6).toString());
				} else {
					v.setErrorFlag("1");
					v.setErrorDesc(v.getErrorDesc() + "|包装必须为数字");
					log.info("包装必须为数字");
				}
			}
			if (!StringUtils.isEmpty(eRowData.get(7)))
				v.setMfrsCode(eRowData.get(7).toString().trim());
			if (!StringUtils.isEmpty(eRowData.get(8)))
				v.setCertificateCode(eRowData.get(8).toString().trim());
			if (!StringUtils.isEmpty(eRowData.get(9)))
				// v.setProvId(eRowData.get(9).toString()+"@@4provName");
				v.setProvName(eRowData.get(9).toString().trim());
			if (!StringUtils.isEmpty(eRowData.get(10))&& NumberValidationUtils.gwisNumeric(eRowData.get(10).toString())) {
				log.info(eRowData.get(10).toString());
				v.setProvCode(eRowData.get(10).toString());

			} else if (!StringUtils.isEmpty(eRowData.get(10))) {
				v.setProvCode(eRowData.get(10).toString());
				log.info("setProvCode 非数字");
			}


			if (!StringUtils.isEmpty(eRowData.get(11))) {
				v.setMasterCode(eRowData.get(11).toString().trim());
			}
			if (!StringUtils.isEmpty(eRowData.get(12)))
				// hsInfo.setHosId(eRowData.get(12).toString()+"@@4hosName");
				v.setHosName(eRowData.get(12).toString().trim());
			if (!StringUtils.isEmpty(eRowData.get(13)))
				v.setHosCode(eRowData.get(13).toString());
			if (!StringUtils.isEmpty(eRowData.get(14))) // 医院商品名 俗称
				v.setGeneralName(eRowData.get(14).toString().trim());
			if (!StringUtils.isEmpty(eRowData.get(15))) {
				if (NumberValidationUtils.isRealNumber(eRowData.get(15).toString())) {
					v.setSalePrice(eRowData.get(15).toString()); // 二级供应商卖给集配商的价（错误）
																	// 集配卖给医院的价格
				} else {
					v.setErrorFlag("1");
					v.setErrorDesc(v.getErrorDesc() + "|单价必须为数字");
					log.info("单价必须为数字");
				}
			}
			if (!StringUtils.isEmpty(eRowData.get(16))) {
				if (NumberValidationUtils.isRealNumber(eRowData.get(16).toString())) {
					v.setHisPrice(eRowData.get(16).toString());
				} else {
					v.setErrorFlag("1");
					v.setErrorDesc(v.getErrorDesc() + "|HIS销售价必须为数字");
					log.info("HIS销售价必须为数字");
				}
			} else {
				v.setHisPrice("0");
			}
			if (!StringUtils.isEmpty(eRowData.get(17))) {
				if (NumberValidationUtils.isRealNumber(eRowData.get(17).toString())) {
					v.setTaxRate(eRowData.get(17).toString());
				} else {
					v.setErrorFlag("1");
					v.setErrorDesc(v.getErrorDesc() + "|税率必须为数字");
					log.info("税率必须为数字");
				}
			}
			if (!StringUtils.isEmpty(eRowData.get(18))) // 医保编码
				v.setMiCode(eRowData.get(18).toString());
		/*	if (!StringUtils.isEmpty(eRowData.get(19))) // 是否收费商品
				// v.setCharging(df.format(eRowData.get(19).toString()));
				v.setCharging(eRowData.get(19).toString());*/
			if (!StringUtils.isEmpty(eRowData.get(19))){ // 是否收费商品
				v.setCharging(eRowData.get(19).toString());
				switch (eRowData.get(19).toString().trim()){
					case "是":
						v.setCharging("1");
						break;
					case "否":
						v.setCharging("0");
						break;
				}
			}
			if (!StringUtils.isEmpty(eRowData.get(20))) // 件包
				v.setHwDesc(eRowData.get(20).toString());
			if (!StringUtils.isEmpty(eRowData.get(21))) // 分类
				v.setHosLbsx(eRowData.get(21).toString());
			if (!StringUtils.isEmpty(eRowData.get(22))) // 医院计量单位
				v.setHosUnit(eRowData.get(22).toString());
			if (!StringUtils.isEmpty(eRowData.get(23))){ // 是否计量
				v.setIsMetering(eRowData.get(23).toString());
				switch (eRowData.get(23).toString().trim()){
					case "是":
						v.setIsMetering("1");
						break;
					case "否":
						v.setIsMetering("0");
						break;
				}
			}

	/*		if (!StringUtils.isEmpty(eRowData.get(24))) // 是否高值
				v.setPurMode(eRowData.get(24).toString());*/
			if (StringUtils.hasText(eRowData.get(24).toString())) {
				v.setPurMode(eRowData.get(24).toString().trim());
				switch (eRowData.get(24).toString().trim()){
					case "是":
						v.setPurMode("20");
						break;
					case "否":
						v.setPurMode("10");
						break;
				}

			}
			else {
				v.setErrorFlag("1");
				v.setErrorDesc(v.getErrorDesc() + "|是否高值不能为空");
				log.info("是否高值不能为空");
			}
			if (!StringUtils.isEmpty(eRowData.get(25))) // 产品描述
				v.setGoodsDesc(eRowData.get(25).toString());
			if (!StringUtils.isEmpty(eRowData.get(26))) {
				v.setKind68Code(NumberValidationUtils.delDotTails(eRowData.get(26).toString()));
			}
			if (!StringUtils.isEmpty(eRowData.get(27))) {
				if (NumberValidationUtils.isRealNumber(eRowData.get(27).toString())) {
					// v.setSalePrice(eRowData.get(27).toString());
					v.setPurPrice(eRowData.get(27).toString());// 对应到
																// 对码表的salePrice
																// 卖给医院的价 （之前错误）
																// 二级供应商卖国药的价
				} else {
					v.setErrorFlag("1");
					v.setErrorDesc(v.getErrorDesc() + "|配送价必须为数字");
					log.info("配送价必须为数字");
				}
			} else {
				v.setPurPrice("0");
			}

			if (!StringUtils.isEmpty(eRowData.get(28))) {
				if (NumberValidationUtils.isRealNumber(eRowData.get(28).toString())) {
					v.setUnitRate(eRowData.get(28).toString());
				} else {
					v.setErrorFlag("1");
					v.setErrorDesc(v.getErrorDesc() + "|转换比必须为数字");
					log.info("转换比必须为数字");
				}
			}
			if (!StringUtils.isEmpty(eRowData.get(29)))
				v.setCollectorName(eRowData.get(29).toString());
			if (!StringUtils.isEmpty(eRowData.get(30))) {
				v.setIsSubprov(eRowData.get(30).toString().trim());
				switch (eRowData.get(30).toString().trim()){
					case "是":
						v.setIsSubprov("1");
						break;
					case "否":
						v.setIsSubprov("0");
						break;
				}

			}
			else {
				v.setErrorFlag("1");
				v.setErrorDesc(v.getErrorDesc() + "|是否二级供应商必须为0或者1");
				log.info("是否二级供应商必须为0或者1");
			}
			if (!StringUtils.isEmpty(eRowData.get(31))){
					 v.setSubProvErpCode(eRowData.get(31).toString());
			}
			log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			log.info(JSONObject.toJSONString(v));
			vList.add(v);

		}
		return vList;
        		 
        		 
}
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<InitGoodsVo> add4ImportExcelInit_taks(List<InitGoodsVo> vList,String s_taksId,String userId) throws Exception{
		int countResult = 0;//当前执行总数
		this.userId = userId;
	    List<InitGoodsVo> resultList = new CopyOnWriteArrayList<InitGoodsVo>();
		Task oo =(Task) taskStatesMap.get(s_taksId);
		    oo.setTotal(vList.size());
		    oo.setRows(resultList);
		    oo.setStatus(1);
		for(InitGoodsVo v:vList){
			taskStatesMap.set(s_taksId, oo);
			oo.setCur(oo.getCur()+1);
				//先处理产家：通过产家名称查询产家Id
		
			BasMfrsInfo	 mfrs = (BasMfrsInfo) simpleDao.getByFieldName(BasMfrsInfo.class, "mfrsName", v.getMfrsName());
			//BasMfrsInfo  mfrs4add= null;
			BasCompanyInfo mfrs4add= null;
				if (mfrs == null) {
					/*BasMfrsInfo m = new BasMfrsInfo();
					 String id =atomUtil.newValue("mfrs_info_id");

					m.setMfrsName(v.getMfrsName());
					m.setId(id);
					m.setVersion(0);
					m.setMfrsKind("u");
					mfrs4add =m;
					v.setMfrsName(m.getId());*/  // 厂家合并到bas_company_info 表  kind=3 单独厂家，kind=4 厂供一体
					BasCompanyInfo  m = new BasCompanyInfo();
					m.setId(atomUtil.newValue("mfrs_info_id"));
					m.setCode(m.getId());
					m.setCname(v.getMfrsName());
					m.setKind("3");
					m.setFlag("1");
				//	m.setMfrsKind("1");
					mfrs4add =m;
					v.setMfrsName(m.getId());
				}
				else {
					  log.info("第" + v.getRowIndex() +"行成功匹配 产家   " + v.getMfrsName() + "到基础数据 ---->" + mfrs.getMfrsName() + "+  " + mfrs.getId());
					  v.setMfrsName(mfrs.getId());  //将厂家id设置到name
					}			
				//通过集配商名称找集配商ID
				BasCompanyInfo collector = (BasCompanyInfo) simpleDao.getByFieldName(BasCompanyInfo.class, "cname", v.getCollectorName());
		        if(collector == null){
		    	   log.info("第" + v.getRowIndex() + "集配商：" + v.getCollectorName() + "没有对应记录");
				    v.setErrorFlag("1");
				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getCollectorName() + "供应商表中没有集配商对应记录");
				}
		        else{
		        	 log.info("第" + v.getRowIndex()+"行成功匹配   " +v.getCollectorName() + "到基础数据 ---->" + collector.getCname());
		    	     v.setCollectorName(collector.getId());  
		        }
				
		        //通过供应商名称找供应商ID
				BasCompanyInfo prov = (BasCompanyInfo) simpleDao.getByFieldName(BasCompanyInfo.class, "cname", v.getProvName());
				BasCompanyInfo prov4add =null;
		        if(prov == null){
//		    	   log.info("第" + v.getRowIndex() + "供应商：" + v.getProvName() + "没有对应记录");
//				    v.setErrorFlag("1");
//				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getProvName() + "供应商表中没有对应记录");
		        	BasCompanyInfo sp = new BasCompanyInfo();
		        	sp.setId(	atomUtil.newValue("p_company_id"));
		        	sp.setKind("2");
		        	sp.setErpCode(v.getSubProvErpCode());
		        	sp.setCode(sp.getId());
		        	sp.setShortPinyin(PinYin.getShortPinyin(v.getProvName()));
		        	sp.setCname(v.getProvName());
		        	sp.setVersion(0);
		        	//basCompanyDao.insert(sp);
		        	prov4add=sp;
		        	v.setProvName(sp.getId());
				}
		        else{
		        	 log.info("第" + v.getRowIndex()+"行成功匹配   " +v.getProvName() + "到基础数据 ---->" + prov.getCname());
		    	     v.setProvName(prov.getId());  
		        }
		        //通过医院名称找医院ID
 				BasCompanyInfo hos = (BasCompanyInfo) simpleDao.getByFieldName(BasCompanyInfo.class, "cname", v.getHosName());
 			
 		        if(hos == null){
 		    	    log.info("第" + v.getRowIndex() + "医院：" + v.getHosName() + "医院表没有对应记录");
 				    v.setErrorFlag("1");
 				    v.setErrorDesc(v.getErrorDesc()+"|"+v.getHosName() + "没有对应记录");
 				}
 		        else{
 		        	 log.info("第" + v.getRowIndex()+"行成功匹配   " +v.getHosName() + "到基础数据 ---->" + v.getHosName());
 		    	     v.setHosName(hos.getId());
 		        }
 		        if(!v.getErrorFlag().equals("1")){  //开始处理对象转换  异常数据不参与
 		        	BasGoodsInfo bg = new BasGoodsInfo();
 		        	bg = cvtInitVo2BasGoodsInfo(bg,v);
 		        	ProvGoodsInfo pg = new ProvGoodsInfo();
 		        	pg = cvtInitVo2ProvGoodsInfo(pg, v);
 		        	HosGoodsInfo hg = new HosGoodsInfo();
 		        	hg = cvt2HosGoodsInfo(hg, v);
 		        	ProvGoodsHos pgh = new ProvGoodsHos();
 		        	pgh = cvt2ProvGoodsHos(pgh,v);
 		 		    ProvHosInfoVo phi_vo = new ProvHosInfoVo();
 		        	phi_vo = cvt2ProvHosInfo(phi_vo,v);
 		        	HosCollector hc = new HosCollector();
 		        	HosCollectorProv hcp = new HosCollectorProv();
 		        	ProvProvGoods ppg = new ProvProvGoods();
 		        	
 		        	try {
						v = igService.add4GoodsItems_v2(v,mfrs4add,prov4add,bg,pg,hg,pgh,phi_vo,hc,hcp,ppg, userId);
					} catch (Exception e) {
						//oo.setStatus(3);
					    v.setErrorFlag("1");
					    v.setErrorDesc(v.getErrorDesc()+"|"+"插入数据时后台错误");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
 		        	
 		        	
 		        }
 		       resultList.add(v);

			
		}
	
		oo.setResultTotal(resultList.size());
		oo.setStatus(2);

		// 发送消息至MQ，供应商基础数据导入
		resultList.stream()
				.filter(item -> StringUtils.hasText(item.getCollectorName()))
				.collect(Collectors.groupingBy(InitGoodsVo::getCollectorName))
				.forEach((key, value) -> {
					BasGoodsImportMsg basGoodsImportMsg = new BasGoodsImportMsg();
					basGoodsImportMsg.setProvId(key);
					basGoodsImportMsg.setProvList(value.stream().map(InitGoodsVo::getProvName).distinct().collect(Collectors.toList()));
					basGoodsImportMsg.setMfrsList(value.stream().map(InitGoodsVo::getMfrsName).distinct().collect(Collectors.toList()));
					try {
						ObjectMapper mapper = new ObjectMapper();
						String json = mapper.writeValueAsString(basGoodsImportMsg);
						jmsSendUtils.send("base.imported", json, null);
						log.info("Send msg to MQ base.imported");
					} catch (JsonProcessingException e) {
						log.error("transform to json error", e);
					}
		});

		return vList;
	}
}
