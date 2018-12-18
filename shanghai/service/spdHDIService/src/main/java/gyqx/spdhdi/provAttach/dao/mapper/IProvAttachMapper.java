package gyqx.spdhdi.provAttach.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.provAttach.vo.ProvAttachVo;

public interface IProvAttachMapper {

	List<ProvAttachVo> getAttachList(ProvAttachVo attach);
	
	List<BasCompanyInfoJxsqsVo> getProvList(@Param("provName") String provName,
            @Param("page") int pageNum,
            @Param("rows") int pageSize);
	
}
