package gyqx.spdherp.barcodePrintingCenter.dao.mapper;

import java.util.List;

import gyqx.spdherp.po.HosPackageInfo;
 
public interface IHosPackageInfoMapper {
	int update(HosPackageInfo bean);
	int insert(HosPackageInfo bean);
	List<HosPackageInfo> list(HosPackageInfo queryInfo) ;
}

