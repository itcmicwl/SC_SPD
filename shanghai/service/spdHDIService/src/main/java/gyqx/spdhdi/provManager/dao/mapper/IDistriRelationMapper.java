package gyqx.spdhdi.provManager.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.platform.po.SysOrg;
import gyqx.spdhdi.provManager.vo.DistriRelationVo;
import gyqx.spdhdi.provManager.vo.HosCollectorProvVo;
import gyqx.spdhdi.provManager.vo.PartnerParam;
import gyqx.spdhdi.provManager.vo.ProvProvGoodsVo;

public interface IDistriRelationMapper {

	List<DistriRelationVo> queryCollectorList(DistriRelationVo distriRelationVo);

	List<SysOrg> getCollectorList(@Param("hosId") String hosId);

	List<DistriRelationVo> queryPartnerList(PartnerParam partnerParam);

	List<HosCollectorProvVo>getHosColProvs(HosCollectorProvVo distriRelationVo);

	/**
	 * 查询集配商配送商商品信息
	 * 
	 * @param queryObject
	 * @return
	 */
	List<ProvProvGoodsVo> queryGoodsList(ProvProvGoodsVo queryObject);

	/**
	 * 默认选中的产品
	 * 
	 * @param vo
	 * @return
	 */
	List<ProvProvGoodsVo> getProvProvGoodsId(ProvProvGoodsVo vo);
}
