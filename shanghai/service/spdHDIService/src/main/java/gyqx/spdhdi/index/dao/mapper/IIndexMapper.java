package gyqx.spdhdi.index.dao.mapper;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.index.vo.OverViewVo;

public interface IIndexMapper {
	OverViewVo getOverViewInfo(@Param("comId") String comId);
}
