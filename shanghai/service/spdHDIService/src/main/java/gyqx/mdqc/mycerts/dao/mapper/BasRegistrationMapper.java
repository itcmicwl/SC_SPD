package gyqx.mdqc.mycerts.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import gyqx.mdqc.mycerts.vo.BasGoodsInfoVo;
import gyqx.mdqc.mycerts.vo.BasRegistrationImageVo;
import gyqx.mdqc.mycerts.vo.BasRegistrationInfoVo;
import gyqx.mdqc.mycerts.vo.IdNameVo;

/**
 * @Author liangwu
 * @Date 7/31/18 11:46 AM
 */
public interface BasRegistrationMapper {
    List<BasRegistrationInfoVo> listCerts(@Param("input") String input,
                                          @Param("type") String type,
                                          @Param("mfrsId") String mfrsId);

    @Select("SELECT id, cname AS name FROM bas_company_info WHERE (kind = 3 OR kind = 4) AND (cname LIKE #{queryStr} OR short_pinyin LIKE #{queryStr})")
    List<IdNameVo> listMfrs(@Param("queryStr") String queryStr);

    @Select("SELECT id, cname AS name FROM bas_company_info WHERE kind = 1 AND (cname LIKE #{queryStr} OR short_pinyin LIKE #{queryStr})")
    List<IdNameVo> listHos(String queryStr);

    @Select("SELECT val AS id, ename AS name FROM sys_dict_value WHERE dict_id = #{dictName}")
    List<IdNameVo> listDictValues(@Param("dictName") String dictName);

    @Delete("DELETE FROM bas_registration_info WHERE id = #{id} AND version = #{version}")
    Integer delCert(@Param("id") String id, @Param("version") Integer version);

    @Select("SELECT COUNT(0) FROM bas_company_info WHERE id = #{corpId} AND kind = 3")
    Integer verifyMfrs(@Param("corpId") String corpId);

    List<BasGoodsInfoVo> getMfrsProduct(@Param("mfrsId") String mfrsId,
                                        @Param("input") String input);

    int insertCert(@Param("vo") BasRegistrationInfoVo basRegistrationInfoVo,
                   @Param("operId") String operId);

    int insertCertImg(@Param("vo") List<BasRegistrationImageVo> fileList,
                      @Param("operId") String operId);

    int insertCertGoods(@Param("vo") List<BasGoodsInfoVo> basGoodsInfoVoList,
                        @Param("operId") String operId);

    BasRegistrationInfoVo getCert(@Param("id") String id, @Param("version") Integer version);

    int delCertImg(@Param("vo") List<BasRegistrationImageVo> fileListDel);

    int delCertGoods(@Param("vo") List<BasGoodsInfoVo> productsDel);

    @Update("UPDATE bas_registration_info SET is_old_info = '1', uxid = #{operId}, version = version + 1, last_update_datetime = sysdate WHERE id = #{vo.id} AND mfrs_id = #{vo.mfrsId} AND version = #{vo.version} AND is_old_info = '0'")
    int changeCertToOld(@Param("vo") BasRegistrationInfoVo basRegistrationInfoVo, @Param("operId") String operId);

    @Update("UPDATE bas_goods_info goods " +
            "SET goods.certificate_code = (SELECT certificate_code FROM bas_registration_info WHERE id = #{certId}), uxid = #{operId}, goods.version = goods.version + 1, goods.last_update_datetime = SYSDATE " +
            "WHERE goods.id IN (SELECT goods_id FROM BAS_REGIST_GOODS WHERE certificate_id = #{certId})")
    int changeGoodsCertificateCode(@Param("certId") String certId,
                                   @Param("operId") String operId);
}
