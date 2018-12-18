package gyqx.spdhdi.mycerts.dao.mapper;

import gyqx.spdhdi.mycerts.vo.ProvSalesRepVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Auther Liangwu
 * @Date 17-11-1 上午11:22
 */
public interface ProvSalesRepMapper {
    List<ProvSalesRepVo> listSalesRep(@Param("provId") String provId,
                                      @Param("start") Date start,
                                      @Param("end") Date end,
                                      @Param("name") String name);

    List<ProvSalesRepVo> listSalesRepByProv(@Param("compId") String provId,
                                     @Param("start") Date start,
                                     @Param("end") Date end,
                                     @Param("aboutName") String aboutName,
                                     @Param("page") int pageNum,
                                     @Param("rows") int pageSize);

    List<ProvSalesRepVo> listSalesRepByHos(@Param("compId") String hosId,
                                    @Param("start") Date start,
                                    @Param("end") Date end,
                                    @Param("aboutName") String aboutName,
                                    @Param("page") int pageNum,
                                    @Param("rows") int pageSize);
}
