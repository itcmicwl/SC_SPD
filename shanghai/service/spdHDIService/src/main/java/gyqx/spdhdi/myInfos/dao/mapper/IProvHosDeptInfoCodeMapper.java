package gyqx.spdhdi.myInfos.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.myInfos.vo.ProvHosDeptInfoCodeVo;
import gyqx.spdhdi.myInfos.vo.SaleCodeInfoVo;
import gyqx.spdhdi.po.ProvHosDeptInfoCode;
 
public interface IProvHosDeptInfoCodeMapper {
	int update(ProvHosDeptInfoCode bean);
	List<ProvHosDeptInfoCode> list(ProvHosDeptInfoCode queryInfo) ;
    /**
     * 根据医院id，医院部门id，供应商id查询数据
     * @param queryInfo 
     * @return
     */
    List<ProvHosDeptInfoCode> getProvHosDeptInfoCode(ProvHosDeptInfoCode queryInfo) ;
	List<ProvHosDeptInfoCodeVo> list_PH_dept_code_vo(ProvHosDeptInfoCodeVo queryInfo) ;
	/**
	 * 查询业务员信息和部门
	 * @param vo
	 * @return
	 */
	SaleCodeInfoVo getSaleCodeInfo(SaleCodeInfoVo vo);
	
	SaleCodeInfoVo getSaleCodeInfoById(@Param("userId") String userId);
}

