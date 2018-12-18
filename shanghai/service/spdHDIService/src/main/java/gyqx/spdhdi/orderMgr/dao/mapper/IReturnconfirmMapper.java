package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import gyqx.spdhdi.orderMgr.vo.ReturnconfirmVo;
import gyqx.spdhdi.po.Returnconfirm;
 
public interface IReturnconfirmMapper {
	int update(Returnconfirm bean);
	int insert(Returnconfirm bean);
	List<Returnconfirm> list(Returnconfirm queryInfo) ;
	List<ReturnconfirmVo> listVo(ReturnconfirmVo queryInfo) ;
	ReturnconfirmVo getByBillId(@Param("billid")String billid);

}

