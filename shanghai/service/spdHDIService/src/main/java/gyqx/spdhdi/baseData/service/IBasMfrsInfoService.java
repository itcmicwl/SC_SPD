package gyqx.spdhdi.baseData.service;

import java.util.Map;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.po.BasMfrsInfo;

public interface IBasMfrsInfoService {
	
	QueryResult<BasMfrsInfoVo> getBasMfrsInfoList(QueryInfo<BasMfrsInfo> queryInfo) throws Exception;
	
	BasMfrsInfo getBasMfrsInfoByid(String id) throws Exception;

    int insertBasMfrsInfo(BasMfrsInfo record) throws Exception;
    
    int setStatus(BasMfrsInfo record) throws Exception;
    
    int setBasMfrsInfo(BasMfrsInfo record) throws Exception;
    
    boolean existBasMfrsInfo(BasMfrsInfo record) throws Exception;
    
    int deleteByPrimaryKey(String id) throws Exception;

	void saveMfrsCertsInfo(Map map) throws Exception;

}