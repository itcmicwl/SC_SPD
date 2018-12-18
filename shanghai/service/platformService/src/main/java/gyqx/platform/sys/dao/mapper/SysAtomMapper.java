package gyqx.platform.sys.dao.mapper;

import java.util.List;

import gyqx.platform.po.SysAtom;
import gyqx.platform.sys.vo.UserBaseInfoVo;
public interface SysAtomMapper {
	List<SysAtom> getSysAtomList(SysAtom sysAtom);
}
