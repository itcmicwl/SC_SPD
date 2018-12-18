package gyqx.spdherp.deptMgr.dao.mapper;

import java.util.List;
import java.util.Map;

import gyqx.spdherp.deptMgr.vo.StocInfoVo;
import gyqx.spdherp.deptMgr.vo.UserStockVo;
import gyqx.spdherp.stocInfoMgr.vo.QueryVo;

public interface IStaffStorehouseMgrMapper {

	int deleteUserStoc(String userId);

	List<UserStockVo> getStocksByUser(Map vo);

	List<StocInfoVo> listStocInfo(QueryVo queryVo);
}
