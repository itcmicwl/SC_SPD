package gyqx.spdhdi.baseData.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.spdhdi.baseData.dao.mapper.BasMfrsInfoMapper;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.po.BasMfrsInfo;

@Repository
public class BasMfrsInfoDao {
	@Resource
	BasMfrsInfoMapper basMfrsInfoMapper;
	
	public List<BasMfrsInfoVo> getBasMfrsInfoList(BasMfrsInfo basMfrsInfo) {
		return basMfrsInfoMapper.getBasMfrsInfoList(basMfrsInfo);
	}
	
	public BasMfrsInfo getBasMfrsInfoByid(String id) {
		return basMfrsInfoMapper.getBasMfrsInfoByid(id);
	}

	public int insertBasMfrsInfo(BasMfrsInfo BasMfrsInfo) {
		return basMfrsInfoMapper.insertBasMfrsInfo(BasMfrsInfo);
	}

	public int setStatus(BasMfrsInfo BasMfrsInfo) {
		return basMfrsInfoMapper.setStatus(BasMfrsInfo);
	}

	public int setBasMfrsInfo(BasMfrsInfo BasMfrsInfo) {
		return basMfrsInfoMapper.setBasMfrsInfo(BasMfrsInfo);
	}

	public boolean existBasMfrsInfo(BasMfrsInfo BasMfrsInfo) {
		Integer count = basMfrsInfoMapper.existBasMfrsInfo(BasMfrsInfo);
		count = count == null ? 0 : count;
		return count > 0;
	}
	
	public int deleteByPrimaryKey(String id){
		return basMfrsInfoMapper.deleteByPrimaryKey(id);
	}
}
