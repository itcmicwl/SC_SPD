package gyqx.spdhdi.myInfos.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.myInfos.service.IUserHosService;
import gyqx.spdhdi.po.UserHos;

@Service
public class UserHosService implements IUserHosService {

	@Resource
	SimpleDao simpleDao;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private UserOnlineStateUtils userOnlineStateUtils;

	@Override
	public List<UserHos> getUserHosLists(String hosId) throws Exception {
		String sql = "SELECT * from user_hos WHERE hos_id = ?";
		List<UserHos> list = simpleDao.queryForList(sql, UserHos.class, hosId);
		return list;
	}

	@Override
	public int addUserHos(List<UserHos> lists) throws Exception {
		String corpId = userOnlineStateUtils.getCurrent().getCorpId();
		int result = 0;
		String sql = "delete from user_hos WHERE hos_id = ? and corp_id = ? ";
		simpleDao.executeSql(sql, lists.get(0).getHosId(), corpId);
		String userId = lists.get(0).getUserId();
		if (userId == null || userId == "") {// 如果lists里没有userId，则表示清空所有关联关系
			return 0;
		}
		for (UserHos u : lists) {
			UserHos userHos = new UserHos();
			String id = atomUtil.newValue("user_hos_id");
			userHos.setId(id);
			userHos.setUserId(u.getUserId());
			userHos.setHosId(u.getHosId());
			userHos.setCorpId(corpId);
			userHos.setLastUpdateDatetime(new Date());
			userHos.setVersion(0);
			UserHos obj = simpleDao.insertAndGet(userHos);
			if (obj == null) {
				result = -1;
				return result;
			}
		}
		return result;
	}

	@Override
	public List<UserHos> getHosLists(String userId) throws Exception {
		String sql = "SELECT * from user_hos WHERE user_id = ?";
		List<UserHos> hosList = simpleDao.queryForList(sql, UserHos.class, userId);
		return hosList;
	}

}
