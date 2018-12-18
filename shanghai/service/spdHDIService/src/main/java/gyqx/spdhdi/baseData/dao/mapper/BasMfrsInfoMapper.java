package gyqx.spdhdi.baseData.dao.mapper;

import java.util.List;

import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.po.BasMfrsInfo;

public interface BasMfrsInfoMapper {
	
	List<BasMfrsInfoVo> getBasMfrsInfoList(BasMfrsInfo basMfrsInfo);
	
	BasMfrsInfo getBasMfrsInfoByid(String id);

    int insertBasMfrsInfo(BasMfrsInfo record);
    
    int setStatus(BasMfrsInfo record);
    
    int setBasMfrsInfo(BasMfrsInfo record);
    
    int existBasMfrsInfo(BasMfrsInfo record);
    
    int deleteByPrimaryKey(String id);
    
    int insertSelective(BasMfrsInfo record);

    int updateByPrimaryKeySelective(BasMfrsInfo record);

}