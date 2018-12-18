package gyqx.mdqc.mycerts.dao.mapper;

import gyqx.mdqc.mycerts.vo.InspectionReportImageVo;
import gyqx.mdqc.mycerts.vo.InspectionReportSubVo;
import gyqx.mdqc.mycerts.vo.InspectionReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @Author liangwu
 * @Date 18-9-10 下午4:22
 */
public interface InspectionReportMapper {
    List<InspectionReportVo> listProvReport(@Param("params") HashMap<String, String> params,
                                            @Param("corpId") String corpId);

    List<InspectionReportVo> listHosReport(@Param("params") HashMap<String, String> params);

    List<String> listReportAttachment(@Param("id") String id,
                                      @Param("version") String version,
                                      @Param("corpId") String corpId);

    int delReport(@Param("id") String id,
                  @Param("version") String version,
                  @Param("corpId") String corpId);

    void delReportSub(@Param("id") String id);

    void delReportImg(@Param("id") String id);

    void addReport(@Param("vo") InspectionReportVo vo);

    void addReportSub(@Param("list") List<InspectionReportSubVo> reportSubVoList);

    void addReportImg(@Param("list") List<InspectionReportImageVo> reportImageVoList);
}
