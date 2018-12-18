package gyqx.spdhdi.provManager.dao.mapper;

import gyqx.spdhdi.myInfos.vo.HosInfoVo;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;

import java.util.List;

public interface IHosProvInfoMapper {

	List<ProvHosInfoVo> getHosProvInfoVoList(ProvHosInfoVo basMfrsInfo);
	
	List<HosInfoVo> getHosInfoVoList(HosInfoVo hosInfoVo);

	int insertProvHosInfo(ProvHosInfoVo basMfrsInfo);

	int setStatus(ProvHosInfoVo basMfrsInfo);

	int setProvHosInfo(ProvHosInfoVo basMfrsInfo);

	int deleteByPrimaryKey(String id);

	ProvHosInfoVo getProvHosInfoByid(String id);

	int existProvHosInfo(ProvHosInfoVo basMfrsInfo);

	int updateLogo(String logo);

}