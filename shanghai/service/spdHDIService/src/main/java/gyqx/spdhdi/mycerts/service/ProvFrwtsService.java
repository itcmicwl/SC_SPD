package gyqx.spdhdi.mycerts.service;

import common.db.query.QueryResult;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvFrwtsVo;
import gyqx.spdhdi.mycerts.vo.WtrUserInfoVo;

import java.util.Date;
import java.util.List;

/**
 * Created by liangwu on 17-6-23.
 */
public interface ProvFrwtsService {
	List<BasCompanyInfoJxsqsVo> findHosList(String hosName);

	List<WtrUserInfoVo> findWtrInfo();

	String addFrwts(ProvFrwtsVo provFrwtsVo) throws Exception;

	QueryResult<ProvFrwtsVo> findFrwts(Date start, Date end, String name, String provName, int pageNum, int pageSize,
			String corpType,String corpId);

	Integer updateFrwts(ProvFrwtsVo provFrwtsVo) throws Exception;

	String deleteFrwts(String id, int version) throws Exception;

	QueryResult<ProvFrwtsVo> getFrwtsByProv(Date start, Date end, String aboutName, int pageNum, int pageSize);

	QueryResult<ProvFrwtsVo> getFrwtsByHos(Date start, Date end, String aboutName, int pageNum, int pageSize);

	QueryResult<ProvFrwtsVo> getFrwtsListByLoginComp(String compKind, Date start, Date end, String aboutName,
			int pageNum, int pageSize);

}
