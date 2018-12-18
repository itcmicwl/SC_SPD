package gyqx.spdhdi.mycerts.dao.mapper;

import gyqx.spdhdi.mycerts.vo.ProvIncorruptAgrmtVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-10-31 下午8:27
 */
public interface ProvIncorruptAgrmtMapper {
    List<ProvIncorruptAgrmtVo> listIncorruptAgrmt(@Param("provId") String provId,
                                                  @Param("start") Date start,
                                                  @Param("end") Date end,
                                                  @Param("hosName") String hosName);

    List<ProvIncorruptAgrmtVo> listIncorruptAgrmtByProv(@Param("compId") String provId,
                                     @Param("start") Date start,
                                     @Param("end") Date end,
                                     @Param("aboutName") String aboutName,
                                     @Param("page") int pageNum,
                                     @Param("rows") int pageSize);

    List<ProvIncorruptAgrmtVo> listIncorruptAgrmtByHos(@Param("compId") String hosId,
                                    @Param("start") Date start,
                                    @Param("end") Date end,
                                    @Param("aboutName") String aboutName,
                                    @Param("page") int pageNum,
                                    @Param("rows") int pageSize);

}
