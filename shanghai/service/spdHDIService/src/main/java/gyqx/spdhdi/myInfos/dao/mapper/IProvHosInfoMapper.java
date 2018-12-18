package gyqx.spdhdi.myInfos.dao.mapper;

import java.util.List;

import gyqx.spdhdi.myInfos.vo.HosInfoVo;
import gyqx.spdhdi.myInfos.vo.HosProvVo;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import gyqx.spdhdi.po.BasCompanyInfo;

public interface IProvHosInfoMapper {

	List<ProvHosInfoVo> getProvHosInfoVoList(ProvHosInfoVo basMfrsInfo);
	
	List<HosInfoVo> getHosInfoVoList(HosInfoVo hosInfoVo);
	
	List<HosProvVo> getHosProvVoList(HosProvVo queryObject);

	int insertProvHosInfo(ProvHosInfoVo basMfrsInfo);
	
	int setStatus(ProvHosInfoVo basMfrsInfo);
	
	int setHosView(ProvHosInfoVo provHosInfoVo);

	int setProvHosInfo(ProvHosInfoVo basMfrsInfo);

	int deleteByHosId(String id);

	ProvHosInfoVo getProvHosInfoByid(String id);

	int existProvHosInfo(ProvHosInfoVo basMfrsInfo);

	List<ProvHosInfoVo> getHosProvCompanyInfoVoList(ProvHosInfoVo queryObject);

	int deleteById(String id);

	List<BasCompanyInfo> getNotRelatedCompanysByHosId(HosInfoVo queryObject);
}