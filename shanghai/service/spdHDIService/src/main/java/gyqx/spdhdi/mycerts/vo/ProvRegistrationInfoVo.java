package gyqx.spdhdi.mycerts.vo;

import java.util.List;

import gyqx.spdhdi.po.ProvRegistrationImage;
import gyqx.spdhdi.po.ProvRegistrationInfo;

/**
 * 供应商注册证Vo
 * 
 * @author xuLiqiang
 *
 */
public class ProvRegistrationInfoVo extends ProvRegistrationInfo {

	private String cname;
	private List<ProvRegistrationImage> provRegImageVos;

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public List<ProvRegistrationImage> getProvRegImageVos() {
		return provRegImageVos;
	}

	public void setProvRegImageVos(List<ProvRegistrationImage> provRegImageVos) {
		this.provRegImageVos = provRegImageVos;
	}

}
