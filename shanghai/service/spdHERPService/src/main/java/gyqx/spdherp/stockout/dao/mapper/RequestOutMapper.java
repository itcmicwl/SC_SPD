package gyqx.spdherp.stockout.dao.mapper;

import gyqx.spdherp.po.OutStock;
import gyqx.spdherp.stockout.vo.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface RequestOutMapper {
    List<SysOrgVo> listSysOrgByCorpId(@Param("corpId") String corpId,
                                      @Param("stockId") String stockId,
                                      @Param("deptId") String deptId,
                                      @Param("userId") String userId);

    List<IdNameVo> listOwnStock(@Param("hosId") String hosId,
                                @Param("userId") String userId);

    List<DeptBuyMainVo> listDeptBuyRequest(@Param("beginDate") Date beginDate,
                                           @Param("endDate") Date endDate,
                                           @Param("billId") String billId,
                                           @Param("deptId") String deptId,
                                           @Param("stockId") String stockId,
                                           @Param("hosId") String hosId,
                                           @Param("userId") String userId);

    List<GoodsNoIdUniqueVo> listUniqueCode(@Param("hosId") String hosId,
                                           @Param("stockId") String stockId,
                                           @Param("buyKind") String buyKind,
                                           @Param("goodsIdList") List<String> goodsIdList);

    List<HosStockpileVo> listHosStockpile(@Param("stockTableId") String stockTableId,
                                        @Param("hosId") String hosId,
                                        @Param("stockId") String stockId,
                                        @Param("goodsId") String goodsId,
                                        @Param("batchNo") String batchNo,
                                        @Param("batchId") String batchId,
                                        @Param("uniqueCode") String uniqueCode,
                                        @Param("stocKind") Integer stocKind,
                                        @Param("isUnique") boolean isUnique);

    List<DeptBuySubVo> listDeptBuyRequestDetail(@Param("hosId") String hosId,
                                                @Param("billId") String billId,
                                                @Param("deptId") String deptId,
                                                @Param("stockId") String stockId);

    List<HosStockpileVo> listHosStockpileWithDs(@Param("sub") RequestStockGoodsVo requestStockGoodsVo,
                                              @Param("main") OutStock outStockBill);

    IdNameVo getDeptInfoByStockId(@Param("hosId") String hosId,
                                  @Param("stockId") String stockId);

    @Select("select count(0) from sicker_info where patient_in_hos_id = #{patientInHosId}")
    int countByPatientInHosId(@Param("patientInHosId") String patientInHosId);

    @Insert("insert into sicker_info (id, hos_id, patient_id, patient_in_hos_id, patient_name, patient_short_pinyin, filler, fill_date, last_update_datetime, version) values (#{id}, #{hosId}, #{patientId}, #{inHosId}, #{name}, #{shortPinyin}, #{filler}, now(), now(), 0)")
    void insertSickerInfo(@Param("id") String id,
                          @Param("hosId") String hosId,
                          @Param("patientId") String patientId,
                          @Param("inHosId") String inHosId,
                          @Param("name") String name,
                          @Param("shortPinyin") String shortPinyin,
                          @Param("filler") String filler);

    @Insert("insert into sicker_in_hospital_info (id, hos_id, patient_id, dept_code, bed_num, last_update_datetime, version) values (#{id}, #{hosId}, #{patientId}, #{deptCode}, #{bedNum}, now(), 0)")
    void insertSickerInHospitalInfo(@Param("id") String id,
                                    @Param("hosId") String hosId,
                                    @Param("patientId") String patientId,
                                    @Param("deptCode") String deptCode,
                                    @Param("bedNum") String bedNum);

    @Results({
            @Result(property = "patientName", column = "patient_name"),
            @Result(property = "patientInHosId", column = "patient_in_hos_id"),
            @Result(property = "bedNum", column = "bed_num"),
            @Result(property = "deptCode", column = "dept_code")
    })
    @Select("select si.patient_name, si.patient_in_hos_id, sihi.dept_code, sihi.bed_num from sicker_info si left outer join sicker_in_hospital_info sihi on (si.patient_id = sihi.patient_id and si.hos_id = sihi.hos_id) where si.hos_id = #{hosId} and (si.patient_in_hos_id like #{query} or si.patient_name like #{query} or si.patient_short_pinyin like #{query})")
    List<SickerInfoVo> listSickerInfo(@Param("query") String query, @Param("hosId") String hosId);
}