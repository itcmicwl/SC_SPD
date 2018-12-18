package gyqx.spdhdi.provManager.dao.mapper;

import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;

import java.util.List;

public interface INewProvInfoMapper {

	List<ProvHosInfoVo> getNewProvInfoVoList(ProvHosInfoVo basMfrsInfo);

}