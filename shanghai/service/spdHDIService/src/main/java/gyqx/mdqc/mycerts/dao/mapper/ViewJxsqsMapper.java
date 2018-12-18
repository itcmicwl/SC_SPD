package gyqx.mdqc.mycerts.dao.mapper;

import gyqx.mdqc.mycerts.vo.GoodsInfoVo;
import gyqx.mdqc.mycerts.vo.IdNameVo;
import gyqx.mdqc.mycerts.vo.JxsqsData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @Author liangwu
 * @Date 18-8-22 上午11:31
 */
public interface ViewJxsqsMapper {
    @Select("SELECT kind FROM bas_company_info WHERE id = #{corpId}")
    String getCorpKind(@Param("corpId") String corpId);

    List<GoodsInfoVo> listProvGoodsInfo(@Param("input") String input,
                                        @Param("mfrsId") String mfrsId,
                                        @Param("provId") String provId,
                                        @Param("hosId") String hosId);

    List<GoodsInfoVo> listHosGoodsInfo(@Param("input") String input,
                                       @Param("mfrsId") String mfrsId,
                                       @Param("hosId") String hosId);

    Set<JxsqsData> getProvJxsqsDataList(@Param("corpId") String corpId,
                                        @Param("goodsId") String goodsId,
                                        @Param("hosId") String hosId);

    Set<JxsqsData> getHosJxsqsDataList(@Param("corpId") String corpId,
                                       @Param("goodsId") String goodsId,
                                       @Param("sqsIds") List<String> sqsIds);

    List<IdNameVo> getProvSqsHos(@Param("corpId") String corpId,
                                 @Param("sqsIds") Set<String> sqsIds);

    List<IdNameVo> getHosSqsHos(@Param("corpId") String corpId,
                                @Param("sqsIds") Set<String> sqsIds);

    @Select("SELECT sqs_id FROM prov_jxsqs_hos WHERE hos_id = #{corpId}")
    List<String> getSqsIdsByHosId(@Param("corpId") String corpId);
}
