package gyqx.spdhdi.mycerts.dao.mapper;

import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvFrwtsVo;
import gyqx.spdhdi.mycerts.vo.WtrUserInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by liangwu on 17-6-23.
 */
public interface ProvFrwtsMapper {
    List<BasCompanyInfoJxsqsVo> getHosList(@Param("hosName") String hosName,
                                           @Param("page") int pageNum,
                                           @Param("rows") int pageSize);

    List<WtrUserInfoVo> getWtrInfo(@Param("provId") String provId);

    List<ProvFrwtsVo> getFrwts(@Param("start") Date start,
                               @Param("end") Date end,
                               @Param("name") String name,
                               @Param("provName") String provName,
                               @Param("page") int pageNum,
                               @Param("rows") int pageSize,
                               @Param("corpType") String corpType,
                               @Param("corpId") String corpId);

    List<ProvFrwtsVo> getFrwtsByProv(@Param("compId") String provId,
                                     @Param("start") Date start,
                                     @Param("end") Date end,
                                     @Param("aboutName") String aboutName,
                                     @Param("page") int pageNum,
                                     @Param("rows") int pageSize);

    List<ProvFrwtsVo> getFrwtsByHos(@Param("compId") String hosId,
                                    @Param("start") Date start,
                                    @Param("end") Date end,
                                    @Param("aboutName") String aboutName,
                                    @Param("page") int pageNum,
                                    @Param("rows") int pageSize);

}
