package gyqx.platform.sys.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gyqx.platform.po.SysMessageConfirm;

public interface ISysMessageConfirmMapper {
	int update(SysMessageConfirm bean);
	int insert(SysMessageConfirm bean);
	List<SysMessageConfirm> list(SysMessageConfirm queryInfo);
	int getProUserMsgCount(@Param("projectCode")String projectCode, @Param("userId")String userId);
	int setMsgRead(SysMessageConfirm queryInfo);
}