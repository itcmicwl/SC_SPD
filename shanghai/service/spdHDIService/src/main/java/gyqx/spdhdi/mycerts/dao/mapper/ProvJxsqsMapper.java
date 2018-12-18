package gyqx.spdhdi.mycerts.dao.mapper;

import gyqx.spdhdi.mycerts.vo.*;
import gyqx.spdhdi.po.ProvJxsqsGoods;
import gyqx.spdhdi.po.ProvJxsqsHos;
import gyqx.spdhdi.po.ProvJxsqsRegist;
import gyqx.spdhdi.po.ProvJxsqsZjzj;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by liangwu on 17-6-21.
 */
public interface ProvJxsqsMapper {
    // TODO: 是否需要将有效期结束日期进行过滤，现只显示结束日期大于NOW，或为长期有效
    List<ProvRegInfoJxsqsVo> getProvRegInfo(@Param("provId") String provId,
                                            @Param("mfrsId") String mfrsId,
                                            @Param("certName") String certName,
                                            @Param("sqsId") String sqsId,
                                            @Param("page") int pageNum,
                                            @Param("rows") int pageSize);

    List<BasCompanyInfoJxsqsVo> getBasHos(@Param("hosName") String hosname,
                                          @Param("sqsId") String sqsId,
                                          @Param("page") int pageNum,
                                          @Param("rows") int pageSize);

    void insertProvJxsqsHost(@Param("hosList") Collection<ProvJxsqsHos> hosList);

    void insertProvJxsqsRegist(@Param("regists") Collection<ProvJxsqsRegist> regists);

    List<ProvJxsqsVo> getJxsqs(@Param("provId") String provId,
                               @Param("start") Date start,
                               @Param("end") Date end,
                               @Param("mfrsName") String mfrsName,
                               @Param("page") int pageNum, @Param("rows") int pageSize);

    List<ProvRegInfoJxsqsVo> getRelatedCerts(@Param("sqsId") String sqsId);

    List<BasCompanyInfoJxsqsVo> getForHospitals(@Param("sqsId") String sqsId);

    void delProvJxsqsHos(@Param("delHos") Collection<BasCompanyInfoJxsqsVo> delHos,
                         @Param("sqsId") String sqsId,
                         @Param("provId") String provId);

    void delProvJxsqsRegist(@Param("delCerts") Collection<ProvRegInfoJxsqsVo> delCerts,
                            @Param("sqsId") String sqsId,
                            @Param("provId") String provId,
                            @Param("mfrsId") String mfrsId);

    void delProvJxsqsHosBySqsId(@Param("id") String id);

    void delProvJxsqsRegistBySqsId(@Param("id") String id);

    List<String> getZjzjCompanyLikeName(@Param("name") String name,
                                        @Param("page") int pageNum,
                                        @Param("rows") int pageSize);

    void insertProvJxsqsZjzj(@Param("zjzjList") List<ProvJxsqsZjzj> zjzjList);

    void delProvJxsqsZjzjBySqsId(@Param("id") String id);
    List<ProvJxsqsNVo> getJxsqsList(ProvJxsqsGoods query);
    Integer saveSqsRelation(List<ProvJxsqsGoods> lst);
    Integer saveSqsHos(List<ProvJxsqsHos> lst);
    List<ProvJxsqsHosVo> getSqsHosLst(@Param("sqsId")String sqsId);
    List<ProvJxsqsNVo> getSqsRelationById(@Param("sqsId")String sqsId);
    Integer delSqsHos(ProvJxsqsHos provJxsqsHos);
}
