package gyqx.platform.sys.dao.mapper;

import gyqx.platform.po.SysMessage;
import gyqx.platform.sys.vo.SysMessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface ISysMessageMapper {
    int update(SysMessage bean);

    int insert(SysMessage bean);

    List<SysMessageVo> list(SysMessageVo queryInfo);

    List<SysMessageVo> listUsersMsg(@Param("projectCode") String projectCode,
                                  @Param("beginDate") Date beginDate,
                                  @Param("endDate") Date endDate,
                                  @Param("containsRead") boolean containsRead);
}

