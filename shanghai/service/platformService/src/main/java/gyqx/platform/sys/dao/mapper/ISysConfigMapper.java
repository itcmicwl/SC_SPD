package gyqx.platform.sys.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.platform.po.SysConfig;

public interface ISysConfigMapper {
	List<SysConfig> getSysConfigList(SysConfig sysConfig);
	SysConfig getSysConfigByid(@Param("id") String id);
	SysConfig getSysConfigByName(@Param("name") String name);
	int insertSysConfig(SysConfig sysConfig);
	int setStatus(SysConfig sysconfig);
	int setSysConfig(SysConfig sysconfig);
	Integer existSysConfig(SysConfig sysConfig);
}
