package gyqx.spdhdi.orderMgr.dao.mapper;

import java.util.List;

import gyqx.spdhdi.orderMgr.vo.ReturnconfirmListVo;
import gyqx.spdhdi.po.ReturnconfirmList;
 
public interface IReturnconfirmListMapper {
	int update(ReturnconfirmList bean);
	int insert(ReturnconfirmList bean);
	List<ReturnconfirmList> list(ReturnconfirmList queryInfo) ;
	List<ReturnconfirmListVo> listVo(ReturnconfirmListVo queryInfo) ;

}

