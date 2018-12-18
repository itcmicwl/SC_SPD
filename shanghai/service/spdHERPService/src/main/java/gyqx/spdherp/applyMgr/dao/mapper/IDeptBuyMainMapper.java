package gyqx.spdherp.applyMgr.dao.mapper;

import java.util.List;

import gyqx.platform.sys.vo.SysOrgVo;
import gyqx.spdherp.applyMgr.vo.ApplyBillInfoVo;
import gyqx.spdherp.applyMgr.vo.DeptBuyBillVo;
import gyqx.spdherp.applyMgr.vo.ProvGoodsKindsVo;
import gyqx.spdherp.po.DeptBuyMain;
import org.apache.ibatis.annotations.Param;

public interface IDeptBuyMainMapper {
	int update(DeptBuyMain bean);
	List<DeptBuyMain> list(DeptBuyMain queryInfo) ;
	List<DeptBuyBillVo> getMainBillVoList(@Param("queryInfo") DeptBuyBillVo queryInfo,
										  @Param("myUserId") String myUserId,
										  @Param("myHosId") String myHosId) ;
	List<DeptBuyBillVo> getMainBillVo4pur(DeptBuyBillVo queryInfo) ;
	List<DeptBuyBillVo> getMainBillVo4merger(DeptBuyBillVo queryInfo) ;
	List<SysOrgVo> getBuyDeptList(DeptBuyBillVo queryInfo);
	List<ApplyBillInfoVo> getApplyPurInfos(ApplyBillInfoVo applyBillInfoVo);
	
}

