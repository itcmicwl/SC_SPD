package gyqx.spdhdi.baseData.service.impl;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.utils.SysAtomUtil;
import gyqx.spdhdi.baseData.service.IProvOrgInnerCodeService;
import gyqx.spdhdi.baseData.vo.UUIDandPassword;
import gyqx.spdherp.po.ProvOrgInnerCode;

@Service
public class ProvOrgInnerCodeService implements IProvOrgInnerCodeService {

	@Resource
    private SimpleDao simpleDao;
	@Resource
	private SysAtomUtil atomUtil;
	
	@Override
	public UUIDandPassword getUUIDandPassword() throws Exception {
		UUIDandPassword object = new UUIDandPassword();
		object.setUuid(getUUID());
		object.setPassword(getPassword(16));
		return object;
	}
	
	private String getUUID(){
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	// 生成随机数字和字母,
	public String getPassword(int length) {
		String val = "";
		Random random = new Random();
		// 参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	@Override
	public void insertProvOrgInnerCode(ProvOrgInnerCode provOrgInnerCode) throws Exception {
		provOrgInnerCode.setId(atomUtil.newValue("prov_org_inner_code_id"));
		simpleDao.insert(provOrgInnerCode);
	}

	@Override
	public ProvOrgInnerCode queryProvOrgInnerCode(String provId) throws Exception {
//		String sql = "SELECT * FROM prov_org_inner_code WHERE prov_id = ?";
//		ProvOrgInnerCode result = simpleDao.queryForObject(sql, ProvOrgInnerCode.class, provId);
		ProvOrgInnerCode result = (ProvOrgInnerCode) simpleDao.getByFieldName(ProvOrgInnerCode.class, "provId", provId);
		return result == null ? new ProvOrgInnerCode():result;
	}

	@Override
	public void updateProvOrgInnerCode(ProvOrgInnerCode provOrgInnerCode) throws Exception {
//		provOrgInnerCode.setVersion(1+provOrgInnerCode.getVersion());
		simpleDao.update(provOrgInnerCode);
	}
	
	

}


