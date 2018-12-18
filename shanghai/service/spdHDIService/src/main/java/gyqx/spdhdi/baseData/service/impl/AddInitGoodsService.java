package gyqx.spdhdi.baseData.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import common.base.PinYin;
import common.db.SimpleDao;
import common.exception.ValidateException;
import common.utils.DictionaryUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.baseData.dao.BasCompanyDao;
import gyqx.spdhdi.baseData.dao.BasGoodsInfoDao;
import gyqx.spdhdi.baseData.dao.BasMfrsInfoDao;
import gyqx.spdhdi.baseData.service.IAddInitGoodsService;
import gyqx.spdhdi.baseData.vo.InitGoodsVo;
import gyqx.spdhdi.collector4orderMgr.dao.HosCollectorDao;
import gyqx.spdhdi.collector4orderMgr.dao.HosCollectorProvDao;
import gyqx.spdhdi.collector4orderMgr.dao.ProvProvGoodsDao;
import gyqx.spdhdi.myGoods.dao.HosGoodsInfoDao;
import gyqx.spdhdi.myGoods.dao.ProvGoodsHosDao;
import gyqx.spdhdi.myGoods.dao.ProvGoodsInfoDao;
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
public class AddInitGoodsService implements IAddInitGoodsService {
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
	@Override
	public InitGoodsVo add4GoodsItems_v2(InitGoodsVo v, BasCompanyInfo mfrs4add, BasCompanyInfo prov4add, BasGoodsInfo bg,
			ProvGoodsInfo pg, HosGoodsInfo hg, ProvGoodsHos pgh, ProvHosInfoVo phi, HosCollector hc,
			HosCollectorProv hcp, ProvProvGoods ppg,String userId) throws Exception {
		//集配商 和医院需要先注册到系统才能导入
    	if(mfrs4add!=null){   //插入厂家
    		//int i = basMfrsInfoDao.insertBasMfrsInfo(mfrs4add);
			basCompanyDao.insert(mfrs4add);
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
            //hc =hosCollectorDao.insertAndGet(hc);
			simpleDao.insert(hc);
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
          //	hcp = hosCollectorProvDao.insertAndGet(hcp);
			simpleDao.insert(hcp);
        }
        
    	
    	
    	
    	
    	
    	//先查询BasGoodsInfo是否已有本行数据  good.getGoodsName(),good.getGoodsGg(),good.getMade(),good.getMfrsId(),good.getUnit()
	
	      List  bgTemps=	simpleDao.queryByFieldNames( BasGoodsInfo.class, "goodsName,goodsGg,made,mfrsId,unit", bg.getGoodsName(),bg.getGoodsGg(),bg.getMade(),bg.getMfrsId(),bg.getUnit());		
          if(bgTemps.size()>0){
        	  BasGoodsInfo  temp =(BasGoodsInfo)bgTemps.get(0);
        	  bg.setId(temp.getId());
        	  bg.setCode(temp.getCode());
          }
          else  {
				 long  num=	simpleDao.queryForObject("select count(*) from bas_Goods_info where goods_name=? and goods_gg=? and made=? and mfrs_id=? and unit=? ",
				                       Long.class, bg.getGoodsName(),bg.getGoodsGg(),bg.getMade(),bg.getMfrsId(),bg.getUnit());
				if(num>0){
				ValidateException.Throw("goodsName",  "您输入的商品："+bg.getGoodsName() + "及其规格、厂家、产地、单位属性已存在！",bg.getGoodsName() );
				}
				
				String id = atomUtil.newValue("bGoods_id");
				bg.setShortPinyin(PinYin.getShortPinyin(bg.getGoodsName()));
				bg.setId(id);
				bg.setCode(id.split("-")[1]);//code 由id流水生成
				//bg.setUid( userOnline.getCurrent().getUserId());
				bg.setUid(userId);
				bg.setFillDate(new Date());
				bg.setVersion(0);
				bg.setFlag("1");
				 simpleDao.insert(bg);
          }
      
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
	//	pg.setUid(userOnline.getCurrent()==null?this.userId:userOnline.getCurrent().getUserId());
		pg.setUid(userId);
		 simpleDao.insert(pg);
    }
	//处理医院产品信息  除了检查code 
    // add 2018-7-30 还需要检查五大要素，同一医院内不允许重复
	if (simpleDao.checkExists(HosGoodsInfo.class, "code,hosId", hg.getCode(),hg.getHosId()) > 0) {
		    log.info("第" + v.getRowIndex() + "医院商品编码：" + v.getHosCode() + "在医院商品表中有重复记录");
		    v.setErrorFlag("1");
		    v.setErrorDesc(v.getErrorDesc()+"|"+v.getHosCode() + "在医院商品表中已有重复记录");
		  return v;
	}
	//long x =simpleDao.checkExists(HosGoodsInfo.class, "hosId,goodsName,goodsGg,made,mfrsId,unit", hg.getHosId(),v.getGoodsName(),v.getGoodsGg(),v.getMade(),v.getMfrsName(),v.getUnit());
	 if(simpleDao.checkExists(HosGoodsInfo.class, "hosId,goodsName,goodsGg,made,mfrsId,unit", hg.getHosId(),v.getGoodsName(),v.getGoodsGg(),v.getMade(),v.getMfrsName(),v.getUnit()) > 0){
	    log.info("第" + v.getRowIndex() + "医院商品表" + "五大要素有重复记录");
	    v.setErrorFlag("1");
	    v.setErrorDesc(v.getErrorDesc()+"|"+v.getRowIndex() + "在医院商品表五大要素重复");
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
        hg.setUid(userId);
	    simpleDao.insert(hg);
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
		pgh.setUid(userId);
		 simpleDao.insert(pgh);
	 }   
	 
 	//ProvProvGoods ppg
	 ppg.setCollectorId(v.getCollectorName());
     ppg.setHosId(v.getHosName());
     ppg.setId(v.getHosName()+"|" + atomUtil.newValue("pp_goods_id"));
     ppg.setLastUpdateDatetime(new java.util.Date());
     ppg.setProvGoodsId(pg.getId());
     ppg.setProvId(v.getProvName());
     ppg.setVersion(0);
     //provProvGoodsDao.insert(ppg);
		simpleDao.insert(ppg);
	 
	 
	   
     
	return v;
	}

}
