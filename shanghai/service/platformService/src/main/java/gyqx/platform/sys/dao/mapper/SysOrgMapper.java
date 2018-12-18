package gyqx.platform.sys.dao.mapper;

import java.util.List;
import java.util.Map;




public interface SysOrgMapper {
	List getAll();
	List query(Map<String,Object> map);
	List getOrgVo(Map<String,Object> map);
	void stateDeleteOrgs(Map<String,Object> map);
	void updateAllSonCode(Map<String,Object> map);
}
