package gyqx.spdhdi.myGoods.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.myGoods.dao.mapper.IProvGoodsInfoMapper;
import gyqx.spdhdi.myGoods.vo.ProvGoodsHosVo;
import gyqx.spdhdi.myGoods.vo.ProvGoodsInfoVo;
import gyqx.spdhdi.po.ProvGoodsInfo;

@Repository
public class ProvGoodsInfoDao {
	@Resource
	private IProvGoodsInfoMapper mapper;
	public List<ProvGoodsInfoVo> getProvGoodsInfoList(ProvGoodsInfo good) throws Exception {
		return mapper.getProvGoodsInfoList(good);
		
	}
	public List<ProvGoodsInfoVo> getProvGoodsHos(ProvGoodsHosVo good) throws Exception {
		return mapper.getProvGoodsHos(good);
		
	}
	public List<ProvGoodsInfoVo> getProvGoodsInfoListByProvAndHos(gyqx.spdhdi.po.ProvGoodsHos pgh) throws Exception {
		return mapper.getProvGoodsInfoListByProvAndHos(pgh);
	}

	public int updateCertificateCodeByBatch(List<ProvGoodsInfo> lst)throws Exception {
		return mapper.updateCertificateCodeByBatch(lst);
	}
}
