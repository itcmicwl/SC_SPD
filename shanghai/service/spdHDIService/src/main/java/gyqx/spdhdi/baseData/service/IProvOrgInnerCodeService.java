package gyqx.spdhdi.baseData.service;

import gyqx.spdhdi.baseData.vo.UUIDandPassword;
import gyqx.spdherp.po.ProvOrgInnerCode;

public interface IProvOrgInnerCodeService {

	UUIDandPassword getUUIDandPassword() throws Exception ;

	void insertProvOrgInnerCode(ProvOrgInnerCode provOrgInnerCode) throws Exception ;

	ProvOrgInnerCode queryProvOrgInnerCode(String provId) throws Exception ;

	void updateProvOrgInnerCode(ProvOrgInnerCode provOrgInnerCode) throws Exception ;
	
//	HosKindcode get(String id) throws Exception ;
//	HosKindcode add(HosKindcode hosKindcode) throws Exception ;
//	HosKindcode update(HosKindcode hosKindcode) throws Exception;
//	
//	List<HosKindcode> query(String predicate,String orderBy,Object... fieldValue) throws Exception;
//	
//	List<HosKindcode> list(HosKindcode bean) throws Exception;
//	
//	QueryResult<HosKindcode> listByPage(QueryInfo<HosKindcode> queryInfo) throws Exception;	

}
