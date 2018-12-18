package gyqx.spdhdi.baseData.dao.mapper;

import java.util.List;

import gyqx.spdhdi.po.HosKindcode;
 
public interface IHosKindcodeMapper {
	int update(HosKindcode bean);
	List<HosKindcode> list(HosKindcode queryInfo) ;
}

