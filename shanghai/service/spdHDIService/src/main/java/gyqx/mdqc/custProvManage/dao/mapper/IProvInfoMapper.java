package gyqx.mdqc.custProvManage.dao.mapper;

import java.util.List;

import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;

public interface IProvInfoMapper {
	
	List<BasCompanyInfoVo> getProvInfoList(BasCompanyInfoVo queryObject);

}
