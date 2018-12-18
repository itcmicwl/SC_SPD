package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.spdhdi.orderMgr.vo.PurconfirmVo;
 
public interface IPurconfirmMapper {
	int insert(PurconfirmVo purconfirmVo);
	int update(PurconfirmVo purconfirmVo);
	int updateByBatch(List<PurconfirmVo> purconfirmVo);
	int delete(PurconfirmVo purconfirmVo);
	PurconfirmVo get(@Param("id") String id);
	PurconfirmVo getByBillid(@Param("billid") String billid);
	List<PurconfirmVo> list(PurconfirmVo purconfirmVo) ;
	List<PurconfirmVo> secList(PurconfirmVo purconfirmVo) ;
	// 查询未发送的单据
	List<PurconfirmVo> getAllBillsNotSend();
}

