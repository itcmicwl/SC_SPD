package gyqx.spdhdi.myInfos.dao.mapper;

import java.util.List;

import gyqx.spdhdi.myInfos.vo.BasMfrsInfoVo;
import gyqx.spdhdi.myInfos.vo.OutdateCertsVo;
import gyqx.spdhdi.myInfos.vo.ProvFrwtsVoR;
import gyqx.spdhdi.myInfos.vo.ProvJxsqsVo;
import gyqx.spdhdi.myInfos.vo.ProvRegistInfoPushVo;
import gyqx.spdhdi.mycerts.vo.ProvIncorruptAgrmtVo;
import gyqx.spdhdi.mycerts.vo.ProvSalesRepVo;
import gyqx.spdhdi.po.ProvCns;
import gyqx.spdhdi.po.ProvRegistrationImage;

public interface IProvCertsMapper {

	List<OutdateCertsVo> queryProvOutdateCerts(OutdateCertsVo outdateCertsVo);

	List<ProvRegistInfoPushVo> getPushedRegCertsList(ProvRegistInfoPushVo provRegistInfoPushVo);

	List<ProvRegistrationImage> getPushedRegCertsImgList(ProvRegistInfoPushVo provRegistInfoPushVo);

	List<BasMfrsInfoVo> getBasMfrsList(ProvRegistInfoPushVo provRegistInfoPushVo);
	
	List<ProvJxsqsVo> jxsqsList(ProvRegistInfoPushVo provRegistInfoPushVo);
	
	List<ProvFrwtsVoR> wtsList(ProvRegistInfoPushVo queryInfo);
	
	List<ProvCns> cnsList(ProvRegistInfoPushVo queryInfo);
	
	List<ProvSalesRepVo> salesRepList(ProvRegistInfoPushVo queryInfo);
	
	List<ProvIncorruptAgrmtVo> listIncorruptAgrmt(ProvRegistInfoPushVo queryInfo);

}
