package gyqx.spdherp.deptMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.po.HosGoodsInfo;
import gyqx.spdherp.deptMgr.vo.DeptGoodsInfoVo;
import gyqx.spdherp.deptMgr.vo.DeptGoodsList4InStockVo;
import gyqx.spdherp.deptMgr.vo.SaleManVo;

public interface IdeptGoodsInfoMapper {

	List<DeptGoodsInfoVo> deptGoodsInfoList(DeptGoodsInfoVo deptGoodsInfoVo);

	List<DeptGoodsList4InStockVo> getDeptGoodsList4InStock(DeptGoodsList4InStockVo vo);

	List<HosGoodsInfo> deptGoodsInfoImportQuery(DeptGoodsInfoVo queryObject);

	/**
	 * 查询业务员
	 * 
	 * @param queryObject
	 * @return
	 */
	List<SaleManVo> getSaleManList(@Param("vo") SaleManVo queryObject);

}
