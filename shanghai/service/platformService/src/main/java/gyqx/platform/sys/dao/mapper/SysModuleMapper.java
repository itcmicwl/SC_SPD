package gyqx.platform.sys.dao.mapper;

import java.util.List;
import java.util.Map;

import gyqx.platform.sys.vo.SysModuleVo;




public interface SysModuleMapper {
	
	List query(Map<String,Object> map);

    List queryModuleVos(Map<String,Object> map);
    List queryModuleVoList(SysModuleVo mv);
}
