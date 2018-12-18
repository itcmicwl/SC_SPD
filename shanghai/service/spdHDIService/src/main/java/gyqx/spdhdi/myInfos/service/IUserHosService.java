package gyqx.spdhdi.myInfos.service;

import java.util.List;

import gyqx.spdhdi.po.UserHos;

public interface IUserHosService {

	public List<UserHos> getUserHosLists(String hosId) throws Exception;

	public int addUserHos(List<UserHos> lists) throws Exception;

	public List<UserHos> getHosLists(String userId) throws Exception;

}
