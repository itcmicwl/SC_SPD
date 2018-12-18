package gyqx.spdhdi.provManager.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.platform.po.SysOrg;

import gyqx.spdhdi.provManager.dao.mapper.IDistriRelationMapper;
import gyqx.spdhdi.provManager.vo.DistriRelationVo;
import gyqx.spdhdi.provManager.vo.HosCollectorProvVo;
import gyqx.spdhdi.provManager.vo.PartnerParam;
import gyqx.spdhdi.provManager.vo.ProvProvGoodsVo;

@Repository
public class DistriRelationDao {

	@Resource
	IDistriRelationMapper mapper;

	public List<DistriRelationVo> queryCollectorList(DistriRelationVo distriRelationVo) {
		return mapper.queryCollectorList(distriRelationVo);
	}

	public List<DistriRelationVo> queryPartnerList(PartnerParam partnerParam) {
		return mapper.queryPartnerList(partnerParam);
	}
	public List<HosCollectorProvVo> getHosColProvs(HosCollectorProvVo distriRelationVo){
		return  mapper.getHosColProvs(distriRelationVo);
	}

	public List<SysOrg> getCollectorList(String hosId){
		return mapper.getCollectorList(hosId);
	}


	public List<ProvProvGoodsVo> queryGoodsList(ProvProvGoodsVo queryObject) {
		return mapper.queryGoodsList(queryObject);
	}

	public List<ProvProvGoodsVo> getProvProvGoodsId(ProvProvGoodsVo vo) {
		return mapper.getProvProvGoodsId(vo);
	}
	

}
