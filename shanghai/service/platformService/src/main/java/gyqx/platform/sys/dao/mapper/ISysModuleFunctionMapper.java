package gyqx.platform.sys.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.platform.sys.vo.SysModuleFunctionVo;

public interface ISysModuleFunctionMapper {
	List<SysModuleFunctionVo> getSysModuleFunctionVoList(SysModuleFunctionVo sysModuleFunctionVo);
	SysModuleFunctionVo getSysModuleFunctionVoByid(SysModuleFunctionVo sysModuleFunctionVo);
	Integer existCodeInSys(SysModuleFunctionVo sysModuleFunctionVo);
	Integer existNameInModule(SysModuleFunctionVo sysModuleFunctionVo);
	int inset(SysModuleFunctionVo sysModuleFunctionVo);
	int setSysModuleFunction(SysModuleFunctionVo sysModuleFunctionVo);
	int delSysModuleFunction(@Param("id") String id);
}
