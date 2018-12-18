package gyqx.spdhdi.mycerts.dao.mapper;

import gyqx.spdhdi.mycerts.vo.ProvCnsVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by liangwu on 17-6-25.
 */
public interface ProvCnsMapper {
    List<ProvCnsVo> getCns(@Param("provId") String provId,
                           @Param("start") Date start,
                           @Param("end") Date end,
                           @Param("hosName") String hosName,
                           @Param("page") int pageNum,
                           @Param("rows") int pageSize);

    List<ProvCnsVo> getCnsListByProv(@Param("compId") String provId,
        @Param("start") Date start,
        @Param("end") Date end,
        @Param("page") int pageNum,
        @Param("aboutName") String aboutName,
        @Param("rows") int pageSize);

    List<ProvCnsVo> getCnsListByHos(@Param("compId") String hosId,
        @Param("start") Date start,
        @Param("end") Date end,
        @Param("aboutName") String aboutName,
        @Param("page") int pageNum,
        @Param("rows") int pageSize);
}
