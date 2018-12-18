package gyqx.spdhdi.myGoods.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import common.exception.ParameterException;
import gyqx.spdhdi.po.HosGoodsInfo;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.myGoods.dao.ProvGoodsHosDao;
import gyqx.spdhdi.myGoods.service.IProvGoodsHosService;
import gyqx.spdhdi.myGoods.vo.ProHosGoodsInfoVo;
import gyqx.spdhdi.myGoods.vo.ProvGoods4HosVo;
import gyqx.spdhdi.myGoods.vo.ProvGoodsInfoVo;
import gyqx.spdhdi.po.ProvGoodsHos;


@Service
public class ProvGoodsHosService implements IProvGoodsHosService {
	@Resource
	private SimpleDao simpleDao;

	@Resource
	private SysConfigUtil configUtil;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private ProvGoodsHosDao provGoodsHosDao;
	@Resource
	private UserOnlineStateUtils userOnline;
	
	public ProvGoodsHos updateProvGoodsHos(ProvGoodsHos good) throws Exception{
		good.setUid(userOnline.getCurrent().getUserId());
		good.setLastUpdateDatetime(new Date());
		//对码 查重处理
		 long  num=	simpleDao.queryForObject("select count(*) from prov_goods_hos where prov_id= ? and  hos_id=? and his_code=? and flag=1  and id<> ?", Long.class, good.getProvId(),good.getHosId(),good.getHisCode(),good.getId());
		 if(num>0){
		           ValidateException.Throw("hisCode", good.getHisCode() +  "该医院编码已与其他数据关联！",good.getHisCode() );
			      }
		return simpleDao.updateAndGet(good);
	}
	public void delProvGoodsHos(ProvGoodsHos good) throws Exception{
		//simpleDao.delete(good);
		good.setFlag("0");
		simpleDao.update(good);
	}
	@Override
	public QueryResult<ProHosGoodsInfoVo> getProHosGoodsList(QueryInfo<ProHosGoodsInfoVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(provGoodsHosDao.getProHosGoodsList(queryInfo.getQueryObject()));
	}

	public QueryResult<ProvGoods4HosVo> getProGoods4HosList(QueryInfo<ProvGoods4HosVo> queryInfo )throws Exception{
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(provGoodsHosDao.getProGoods4HosList(queryInfo.getQueryObject()));
	}

	@Override
	public void updateYCodeAndYName(ProvGoods4HosVo good) throws Exception {
        List<HosGoodsInfo> hosGoodsInfoList = (List<HosGoodsInfo>)simpleDao.queryByFieldNames(HosGoodsInfo.class, "hosId,code,flag", good.getHosId(),good.getHisCode(),"1");
        if (hosGoodsInfoList.size() != 1){
            throw new ParameterException("采购目录不存在此商品或不唯一");
        }
        HosGoodsInfo hosGoodsInfo = hosGoodsInfoList.get(0);
        hosGoodsInfo.setMiCode(good.getMiCode());
        hosGoodsInfo.setMiName(good.getMiName());
        hosGoodsInfo.setUid(userOnline.getCurrent().getUserId());
        hosGoodsInfo.setLastUpdateDatetime(new Date());
        hosGoodsInfo.setUxid(null);
        simpleDao.updateNotNullFields(hosGoodsInfo);
    }

	public void addProvGoodsHosList(List<ProvGoodsHos> goods) throws Exception{
		for(ProvGoodsHos good:goods){
			String id = atomUtil.newValue("prov_goods_hos_id"); 
			good.setId(id);
			good.setFillDate(new Date());
			good.setVersion(0);
			good.setFlag("1");
			good.setUid(userOnline.getCurrent().getUserId());
			simpleDao.insert(good);
		}
	}

	public ProvGoodsHos convertProvGood2PGH(ProvGoodsInfoVo provGood,ProvGoodsHos provGoodhos){
		provGoodhos.setProvGoodsid(provGood.getId());
		if(StringUtil.isNotEmpty(provGood.getProvId())){
			provGoodhos.setProvId(provGood.getProvId());
		}else{
			provGoodhos.setProvId(userOnline.getCurrent().getCorpId());
		}
		return  provGoodhos;
	}
	public ProvGoodsHos getProvGoodsHosById(String id) throws Exception{
	 	ProvGoodsHos provGoodhos = new ProvGoodsHos();
	
		 provGoodhos = (ProvGoodsHos)simpleDao.queryByFieldName(provGoodhos.getClass(), "id", id).get(0);
		 return provGoodhos;
	}
	public void multDelProvGoodsHos(List<ProvGoodsHos> goods) throws Exception{
		for(ProvGoodsHos pg:goods){
			 pg= (ProvGoodsHos)simpleDao.queryByFieldName(pg.getClass(), "id", pg.getId()).get(0);
			// simpleDao.delete(pg);
			 pg.setFlag("0");
			 simpleDao.update(pg);
		}
	}
	/**
	 * 对码提交
	 */
	public void processSubmissionCode(ProvGoods4HosVo good) throws Exception {
		ProvGoodsHos pgh = new ProvGoodsHos();
		pgh = this.getProvGoodsHosById(good.getId());
		pgh.setSalePrice(good.getSalePrice());
		pgh.setPurPrice(good.getPurPrice());
		pgh.setTaxRate(good.getTaxRate());
		pgh.setUnitRate(good.getUnitRate());
		pgh.setHisCode(good.getHisCode());
		pgh.setHisUnit(good.getHisUnit());
		pgh.setHisName(good.getHisName());
		this.updateYCodeAndYName(good); // 医保编码和医保名称
		this.updateProvGoodsHos(pgh);
		//设置医院产品供应商id
		List<HosGoodsInfo> hosGoodList = (List<HosGoodsInfo>)simpleDao.queryByFieldNames(HosGoodsInfo.class, "code,hosId,flag", good.getHisCode(),good.getHosId(),"1");
		if(hosGoodList.size()>1){
			ValidateException.Throw("code", "医院商品重复，无法完成对码操作",good.getHisCode() );
		}
		HosGoodsInfo hosGood= hosGoodList.get(0);
		hosGood.setProvId(good.getProvId());
		hosGood.setLastUpdateDatetime(new Date());
		simpleDao.update(hosGood);
	}

}
