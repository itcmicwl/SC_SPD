package gyqx.spdhdi.uploadData.dao.mapper;

import java.util.List;

import gyqx.spdhdi.uploadData.vo.ProvOrgInnerCodeVo;

public interface ProvOrgInnerCodeMapper {
	/**
	 * 查询配送机构内部标识
	 * @param vo
	 * @return
	 */
	List<ProvOrgInnerCodeVo> getProOrgInnerCode(ProvOrgInnerCodeVo vo);
}
