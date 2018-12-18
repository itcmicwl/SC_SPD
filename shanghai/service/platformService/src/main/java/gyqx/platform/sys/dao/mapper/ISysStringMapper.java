package gyqx.platform.sys.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.platform.po.SysConfig;
import gyqx.platform.po.SysString;
import gyqx.platform.sys.vo.SysStringVo;

public interface ISysStringMapper {
	
	List<SysString> getSysStringList(SysString sysString);
	List<SysString> getSysStringAll();
	
	List<SysStringVo> getSysStringVoList(SysStringVo sysStringVo);
	
	SysString getSysStringByid(@Param("id") String id);
	
	Integer existSysString(SysString sysString);
	
	int insertSysString(SysString sysString);
	
	int setStatus(SysString sysString);
	
	int setSysString(SysString sysString);
	
	
	
	SysConfig getSysConfigByName(@Param("name") String name);
	
}
